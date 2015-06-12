/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

class Canvas extends JPanel implements Printable, Scrollable {

	private static final long serialVersionUID = 1L;

	private static final int EXTRA_SPACE = 0;

    private JScrollPane parent = null;
    private ToolBox toolbox = null;
    private Tool cur_tool = null;
    private boolean mouseDown = false;
    private Automaton automaton = null;
    private boolean dirty = false;
    private boolean suppress_repaint = false;

    public Canvas() {
        setBackground(Color.white);
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseMotionListener());
        addKeyListener(new MyKeyListener());
        //setAutomaton(new DisTA());
        setAutomaton(new Automaton());
    }

    public void setTool(Tool what) {
        if(what == null) return;
        if(cur_tool != null) cur_tool.deselect(getGraphics());
        cur_tool = what;
        if(cur_tool != null) cur_tool.select(getGraphics());
    }

    public Automaton getAutomaton() { return automaton; }
    public ToolBox getToolBox() { return toolbox; }

    public void setAutomaton(Automaton automaton) {
        automaton.setCanvas(null);
        this.automaton = automaton;
        automaton.setCanvas(this);
    }
    public void setToolBox(ToolBox what) {
        toolbox = what;
    }

    //
    // graphics methods
    //
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dirty = false;
        automaton.draw(g);
        if(cur_tool != null) cur_tool.draw(g);
    }
    public void commitTransaction(boolean dirty) {
    	computeSize();
    }
    public void setSuppressRepaint(boolean flag) {
        suppress_repaint = flag;
        if(!flag && dirty) repaint();
    }
    public void exposeAll() {
        expose(0, 0, getWidth(), getHeight());
    }
    public void expose(int x, int y, int width, int height) {
        if(!dirty) {
            dirty = true;
            if(!suppress_repaint) repaint(20);
        }
    }
    public void expose(Rectangle rect) {
        expose(rect.x, rect.y, rect.width, rect.height);
    }
    public void setScrollPane(JScrollPane scroll_pane) {
        this.parent = scroll_pane;
        computeSize();
    }
    public void computeSize() {
        Dimension ret = automaton.getDimensions(getGraphics());
        ret.setSize(ret.width + EXTRA_SPACE, ret.height + EXTRA_SPACE);
        if(parent != null) dimAdd(ret, parent.getViewport().getSize());
        setPreferredSize(ret);
        revalidate();
    }
    private void dimAdd(Dimension x, Dimension y) {
    	if(y.width > x.width) x.setSize(y.width, x.height);
        if(y.height > x.height) x.setSize(x.width, y.height);
    }

    //
    // Scrollable methods
    //
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }
    
    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        if(direction == SwingConstants.VERTICAL) {
            return visibleRect.height / 10 * 10;
        } else {
            return visibleRect.width / 10 * 10;
        }
    }
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }
    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        return 10;
    }

    //
    // Printable methods
    //
    public int getNumberOfPages(PageFormat format) {
        return 1;
    }
    public int print(Graphics g, PageFormat format, int pageIndex) {
        if(pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }

        Rectangle bounds = automaton.getBounds(g);

        if(g instanceof Graphics2D) {
            double scale = 1.0;
            if(format.getImageableWidth() < bounds.width) {
                double t = (double) format.getImageableWidth() / bounds.width;
                if(t < scale) scale = t;
            }
            if(format.getImageableHeight() < bounds.height) {
                double t = (double) format.getImageableHeight() / bounds.height;
                if(t < scale) scale = t;
            }

            AffineTransform xform = ((Graphics2D) g).getTransform();
            xform.scale(scale, scale);
            xform.translate(-bounds.x, -bounds.y);
            ((Graphics2D) g).setTransform(xform);
        }

        Rectangle clip = g.getClipBounds();
        clip.add(bounds.x, bounds.y);
        clip.add(bounds.x + bounds.width, bounds.y + bounds.height);
        g.setClip(clip);

        g.translate((int) Math.floor(format.getImageableX()),
            (int) Math.floor(format.getImageableY()));

        automaton.draw(g);
        return Printable.PAGE_EXISTS;
    }

    private class MyMouseMotionListener
            implements MouseMotionListener {
        public void mouseMoved(MouseEvent e) {
            if(cur_tool == null) return;
            Graphics g = getGraphics();
            cur_tool.mouseMoved(g, e);
        }
        public void mouseDragged(MouseEvent e) {
            if(cur_tool == null) return;
            if(mouseDown) {
                Graphics g = getGraphics();
                cur_tool.mouseDragged(g, e);
            }
        }
    }
    private class MyMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseExited(MouseEvent e) {
            if(cur_tool == null) return;
            Graphics g = getGraphics();
            cur_tool.mouseExited(g, e);
        }
        public void mousePressed(MouseEvent e) {
            if(cur_tool == null) return;
            int mask = InputEvent.BUTTON2_MASK | InputEvent.BUTTON3_MASK;
            if(e.isPopupTrigger() || (e.getModifiers() & mask) != 0) {
                // mouse-press requests component drop-down menu
                mouseDown = false;
                int x = e.getX();
                int y = e.getY();
                Graphics g = getGraphics();
                AutomatonComponent comp = automaton.find(x, y, g);
                if(comp != null) comp.showMenu(e.getX(), e.getY());
            } else {
                // mouse-press represents tool use
                mouseDown = true;
                cur_tool.mousePressed(getGraphics(), e);
            }
            grabFocus();
        }
        public void mouseReleased(MouseEvent e) {
            if(cur_tool == null) {
                JOptionPane.showMessageDialog(null, "You must first select a tool.");
                return;
            }
            if(mouseDown) {
                mouseDown = false;
                cur_tool.mouseReleased(getGraphics(), e);
                grabFocus();
            }
        }
    }
    private class MyKeyListener implements KeyListener {
        public void keyPressed(KeyEvent e) { }
        public void keyReleased(KeyEvent e) { }
        public void keyTyped(KeyEvent e) {
            if(cur_tool == null) return;

            char c = e.getKeyChar();
            if(c != KeyEvent.CHAR_UNDEFINED) {
                Graphics g = getGraphics();
                cur_tool.keyTyped(g, c);
            }
        }
    }
}
