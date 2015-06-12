/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

//
// DRAWING TOOLS
//
class ToolStateActionLink extends Tool {
    private StateActionLink current = null;
    private State start = null;
    private int cur_x;
    private int cur_y;
    private Rectangle to_expose = null;

    public ToolStateActionLink() {						//  public ToolTransition(Canvas canvas) {
        //super(canvas);										Uncomment
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void select(Graphics g) {
        super.select(g);
        start = null;
    }

    public void mousePressed(Graphics g, MouseEvent e) {
        start = MainFrame.getCurrentCanvas().getAutomaton().findState(e.getX(), e.getY(), g);
        if(start == null) {
            AutomatonComponent found = MainFrame.getCurrentCanvas().getAutomaton().find(e.getX(), e.getY(), g);
            if(found instanceof StateActionLink) {
                current = (StateActionLink) found;
            } else {
                current = null;
            }
        }
        mouseDragged(g, e);
    }

    public void mouseDragged(Graphics g, MouseEvent e) {
    //	System.out.println("Inside mouse dragged for tool transition");
        int x = e.getX();
        int y = e.getY();
        if(x < 0 || y < 0) return;
        if(start != null) {
            if(to_expose != null) MainFrame.getCurrentCanvas().expose(to_expose);

            cur_x = x;
            cur_y = y;
            draw(g);
            if(to_expose != null) MainFrame.getCurrentCanvas().expose(to_expose);
        }
        if(current != null) {
            current.expose(g);
            current.move(x, y);
            current.expose(g);
        }
        MainFrame.getCurrentCanvas().commitTransaction(false);
    }

    public void mouseReleased(Graphics g, MouseEvent e) {
    	System.out.println("Inside mouse released for tool stateLinkAction");
        mouseDragged(g, e);
        StateActionLink saLink = null;
        if(to_expose != null) MainFrame.getCurrentCanvas().expose(to_expose);

        if(start != null) {
            Action dst = MainFrame.getCurrentCanvas().getAutomaton().findAction(e.getX(), e.getY(), g);
            if(start == null || dst == null) {
                ; // do nothing
            } else {
            	boolean flag = true;
            	Automaton automaton = MainFrame.getCurrentCanvas().getAutomaton();
            	LinkedList<AutomatonComponent> saLinks = automaton.stateActionLinks;
            	int noOfIterations = saLinks.size();
            	for(int i=0;i<noOfIterations;i++){
            		StateActionLink saLink1 = (StateActionLink)saLinks.get(i);
            		if(saLink1.getDest() == dst){
            			flag = false;
            			break;
            		}
            	}
            	if(flag){
               saLink
                = MainFrame.getCurrentCanvas().getAutomaton().addStateActionLink(start, dst);
            	}
            if(saLink != null) {
                saLink.expose(g);
                MainFrame.getCurrentCanvas().commitTransaction(true);
            }
            else{
            	JOptionPane.showMessageDialog(MainFrame.getCurrentCanvas(), 
            			"An action can be associated with only one state.\n " +
            			"If you want to change, please delete the existing link and try");
            	to_expose = new Rectangle(e.getX(), e.getY(), 0, 0);
                to_expose.add(start.getX(), start.getY());
                MainFrame.getCurrentCanvas().expose(to_expose);
            	//exposeAll();
            	//g = null;
                //if(MainFrame.getCurrentCanvas() != null) g = MainFrame.getCurrentCanvas().getGraphics();
            	//MainFrame.getCurrentCanvas().commitTransaction(true);
            	
            }
        }
        }   
        to_expose = null;
        current = null;
        start = null;
    }

    public void draw(Graphics g) {
        if(start == null) return;

        int x = cur_x;
        int y = cur_y;
        Action dst = MainFrame.getCurrentCanvas().getAutomaton().findAction(x, y, g);
        /*if(start == dst) {
            to_expose = null;
            return;
        } */

        if(dst != null) {
            x = dst.getX();
            y = dst.getY();
        }

        double th = Math.atan2(start.getY() - y, start.getX() - x);
        int[] xp = {
            (int) (x + Transition.ARROW_LEN
                        * Math.cos(th + Transition.ARROW_THETA)),
            (int) x,
            (int) (x + Transition.ARROW_LEN
                        * Math.cos(th - Transition.ARROW_THETA)),
        };
        int[] yp = {
            (int) (y + Transition.ARROW_LEN
                        * Math.sin(th + Transition.ARROW_THETA)),
            (int) y,
            (int) (y + Transition.ARROW_LEN
                        * Math.sin(th - Transition.ARROW_THETA)),
        };

        GraphicsUtil.switchToWidth(g, 3);
        g.setColor(Color.magenta);
        //g.drawPolyline(xp, yp, 3);
        g.drawLine(start.getX(), start.getY(), x, y);

        to_expose = new Rectangle(x, y, 0, 0);
        to_expose.add(start.getX(), start.getY());
        to_expose.add(xp[0], yp[0]);
        to_expose.add(xp[2], yp[2]);
        to_expose.grow(3, 3);
    }
}
