/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

class ToolState extends Tool {
    private State current = null;
    
    public ToolState() {							// public ToolState(Canvas canvas) {
        //super(canvas);							uncomment
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    public void mousePressed(Graphics g, MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        current = MainFrame.getCurrentCanvas().getAutomaton().findState(x, y, g);
        if(current != null) {
            current.expose(g);
            current.move(x, y);
            current.expose(g);
        } else {
            current = MainFrame.getCurrentCanvas().getAutomaton().addState().move(x, y);
            /*if(MainFrame.getCurrentCanvas().getAutomaton().getInitialStates().size() == 0) {
                //current.setInitial(true);
            }*/
            current.expose(g);
        }
        MainFrame.getCurrentCanvas().commitTransaction(true);
    }
    public void mouseDragged(Graphics g, MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x < 0 || y < 0) return;
        if(current != null) {
            current.expose(g);
            MainFrame.getCurrentCanvas().getAutomaton().exposeConnectionsState(g, current);
            current.move(x, y);
            current.expose(g);
            MainFrame.getCurrentCanvas().getAutomaton().exposeConnectionsState(g, current);
            MainFrame.getCurrentCanvas().commitTransaction(false);
        }
    }
    public void mouseReleased(Graphics g, MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(x < 0 || y < 0) {
            if(current != null) {
                current.remove();
                current.expose(g);
            }
        }
        mouseDragged(g, e);
    }
}
