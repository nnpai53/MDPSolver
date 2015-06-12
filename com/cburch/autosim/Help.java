/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

class LogisimHelp extends JFrame {
    JEditorPane     editor;
    JScrollPane     scroll_pane;
    
    public LogisimHelp(InputStream stream) {
        setSize(500, 400);

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

        editor = new JEditorPane();
        editor.setEditable(false);
        editor.setContentType("text/html");
        editor.setText(file_contents.toString());

        scroll_pane = new JScrollPane(editor);
        scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_pane.setMinimumSize(new Dimension(10, 10));
        getContentPane().add(scroll_pane);

        setTitle("MDP Editor Help");
    }
}
