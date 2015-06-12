/* Copyright (c) 2015, Neeraj Pai.  */

package com.cburch.autosim;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

class ToolAction extends Tool {
    private Action current = null;
    
    public ToolAction() {							// public ToolState(Canvas canvas) {
        //super(canvas);							uncomment
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    public void mousePressed(Graphics g, MouseEvent e) {
    	//System.out.println("Inside mouse pressed for tool action");
        int x = e.getX();
        int y = e.getY();
        current = MainFrame.getCurrentCanvas().getAutomaton().findAction(x, y, g);
        if(current != null) {
            current.expose(g);
            current.move(x, y);
            current.expose(g);
        } else {
            current = MainFrame.getCurrentCanvas().getAutomaton().addAction().move(x, y);
            /*if(MainFrame.getCurrentCanvas().getAutomaton().getInitialStates().size() == 0) {
                //current.setInitial(true);
            } */
            current.expose(g);
        }
        MainFrame.getCurrentCanvas().commitTransaction(true);
    }
    public void mouseDragged(Graphics g, MouseEvent e) {
    //	System.out.println("Inside mouse dragged for tool action");
        int x = e.getX();
        int y = e.getY();
        if(x < 0 || y < 0) return;
        if(current != null) {
            current.expose(g);
            MainFrame.getCurrentCanvas().getAutomaton().exposeConnectionsActions(g, current);
            current.move(x, y);
            current.expose(g);
            MainFrame.getCurrentCanvas().getAutomaton().exposeConnectionsActions(g, current);
            MainFrame.getCurrentCanvas().commitTransaction(false);
        }
    }
    public void mouseReleased(Graphics g, MouseEvent e) {
    	//System.out.println("Inside mouse released for tool action");
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
