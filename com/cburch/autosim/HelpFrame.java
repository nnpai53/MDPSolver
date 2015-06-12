/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

class HelpFrame extends JFrame implements HyperlinkListener {
    private class RangeLinkedList extends LinkedList {
        public void removeRange(int x, int y) {
            super.removeRange(x, y);
        }
    }

    private class History {
        private RangeLinkedList urls = new RangeLinkedList();
        private int pos = -1;

        public void init(URL first) {
            pos = 0;
            urls.add(first);
            getURL(first);
        }
        public URL getCurrent() {
            return (URL) urls.get(pos);
        }
        public void back() {
            if(pos - 1 >= 0) {
                --pos;
                getURL((URL) urls.get(pos));
            }
        }
        public void forward() {
            if(pos + 1 < urls.size()) {
                ++pos;
                getURL((URL) urls.get(pos));
            }
        }
        public void addURL(URL url) {
            if(getURL(url)) {
                urls.removeRange(pos + 1, urls.size());
                urls.add(url);
                pos = urls.size() - 1;
            }
        }
    }

    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if(src == close) setVisible(false);
            else if(src == back) history.back();
            else if(src == forward) history.forward();
            else {
                URL url = (URL) urls.get(src);
                if(url != null) load(url);
            }
        }
    }

    private JEditorPane editor;
    private JScrollPane scroll_pane;
    private History history = new History();
    private URL base_url = null;
    private HashMap urls = new HashMap(); // JMenuItem -> URL

    private JMenu goMenu;
    private JMenuItem close;
    private JMenuItem back;
    private JMenuItem forward;

    public HelpFrame(String location) {
        super("Help");
        setSize(500, 400);

        Listener l = new Listener();

        JMenu fileMenu = new JMenu("File");
        close = create(fileMenu, "Close", l);
        fileMenu.add(close);

        goMenu = new JMenu("Go");
        back = create(goMenu, "Back", l, KeyEvent.VK_B);
        forward = create(goMenu, "Forward", l, KeyEvent.VK_B);
        goMenu.addSeparator();

        JMenuBar menubar = new JMenuBar();
        menubar.add(fileMenu);
        menubar.add(goMenu);
        setJMenuBar(menubar);

        editor = new JEditorPane();
        editor.setEditable(false);
        editor.setContentType("text/html");
        editor.addHyperlinkListener(this);

        scroll_pane = new JScrollPane(editor);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_pane.setMinimumSize(new Dimension(10, 10));
        getContentPane().add(scroll_pane);

        try {
            computeBaseURL();
            history.init(new URL(base_url, location));
        } catch(Exception e) {
            showError("Initialization error: " + e.getMessage());
        }
    }
    private JMenuItem create(JMenu menu, String text, ActionListener l) {
        JMenuItem ret = new JMenuItem(text);
        ret.addActionListener(l);
        menu.add(ret);
        return ret;
    }

    private JMenuItem create(JMenu dest, String title, ActionListener l, int accel) {
        JMenuItem ret = create(dest, title, l);
        int mask = ret.getToolkit().getMenuShortcutKeyMask();
        ret.setAccelerator(KeyStroke.getKeyStroke(accel, mask));
        return ret;
    }

    public void addContentsItem(String title, URL url) {
        JMenuItem item = create(goMenu, title, new Listener());
        urls.put(item, url);
    }

    private void computeBaseURL() throws Exception {
        String basePath = System.getProperty("java.class.path");
        if(basePath == null) {
            throw new Exception("can't determine help base location");
        }

        File base;
        String sep = System.getProperty("path.separator");
        int loc = basePath.indexOf(sep);
        if(loc >= 0) {
            base = new File(basePath.substring(0, loc));
            if(!base.exists()) {
                base = new File(basePath);
            }
        } else {
            base = new File(basePath);
        }

        if(base.isDirectory()) {
            base_url = new URL("file:" + base + "/index.html");
        } else if(base.exists()) {
            // assume it's a JAR file
            base_url = new URL("jar:file:" + base + "!/index.html");
        } else {
            throw new Exception("can't find help base location");
        }
    }

    private void showError(String message) {
        editor.setText("<h1>Error</h1>\n"
            + "<p>" + message + "</p>\n");
    }

    public URL getCurrent() {
        return history.getCurrent();
    }

    public void load(String filename) {
        try {
            load(new URL(base_url, filename));
        } catch(IOException e) {
            showError("Could not find file: " + e.getMessage());
        }
    }
    public void load(URL url) {
        history.addURL(url);
    }

    private boolean getURL(URL url) {
        try {
            try {
                HTMLDocument doc = (HTMLDocument) editor.getDocument();
                doc.setBase(url);
                load(url.openStream());
            } catch(IOException e) {
                throw new Exception("Couldn't open URL " + url
                    + " (protocol " + url.getProtocol() + "): "
                    + e.getMessage());
            }
            return true;
        } catch(Throwable e) {
            showError(e.getMessage());
            return false;
        }
    }

    private void load(InputStream stream) {
        // slurp up the file into a StringBuffer
        StringBuffer file_contents = new StringBuffer();
        Reader reader = new InputStreamReader(stream);
        BufferedReader fin = new BufferedReader(reader);
        try {
            while(true) {
                String line = fin.readLine();
                if(line == null) break;
                file_contents.append(line);
            }
        } catch(IOException e) { }
        try {
            stream.close();
        } catch(IOException e) { }

        // now display the file
        editor.getEditorKit().createDefaultDocument();
        editor.setText(file_contents.toString());
        editor.setCaretPosition(0);
    }

    public void hyperlinkUpdate(HyperlinkEvent e) {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            JEditorPane pane = (JEditorPane) e.getSource();
            if(e instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
                HTMLDocument doc = (HTMLDocument) pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                try {
                    URL url = e.getURL();
                    if(url == null) {
                        url = new URL(history.getCurrent(), e.getDescription());
                    }
                    load(url);
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}
