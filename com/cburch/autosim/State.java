/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

class State extends AutomatonComponent {
    public static final int RADIUS = 25;
    public static final double INITARROW_LEN = 1.5 * Transition.ARROW_LEN;

    private String name;
    private int x = 0;
    private int y = 0;
    private boolean is_initial = false;
    private boolean is_final = false;

    public State(Automaton automaton, String name) {
        super(automaton);
        this.name = name;
    }

    public void remove() { getAutomaton().removeState(this); }

    public boolean isCurrent() { return getAutomaton().getCurrentStates().contains(this); }
    public int getX() { return x; }
    public int getY() { return y; }
    public State move(int x, int y) {
        this.x = x;
        this.y = y;
        getAutomaton().invalidateBounds();
        return this;
    }

    public Rectangle getBounds(Rectangle rect, Graphics g) {
        rect.setBounds(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        rect.grow(2, 2);
        return rect;
    }

    public boolean isIn(int x0, int y0, Graphics g) {
        return (x - x0) * (x - x0) + (y - y0) * (y - y0) < RADIUS * RADIUS;
    }


    public void showMenu(int x, int y) {
        JPopupMenu menu = new JPopupMenu();
        createMenu(menu);
        menu.show(automaton.getCanvas(), x, y);
    }
    
    public void createMenu(JPopupMenu menu) {
       // menu.add(new InitialItem());
       // menu.add(new FinalItem());
        super.createMenu(menu);
    }

    public void draw(Graphics g) {
        /*if(isInitial()) {
            double dx = x - RADIUS / Math.sqrt(2.0);
            double dy = y + RADIUS / Math.sqrt(2.0);
            double th = 0.75 * Math.PI;
            int[] xp = {
                (int) (dx + Transition.ARROW_LEN
                            * Math.cos(th + Transition.ARROW_THETA)),
                (int) dx,
                (int) (dx + Transition.ARROW_LEN
                            * Math.cos(th - Transition.ARROW_THETA)),
            };
            int[] yp = {
                (int) (dy + Transition.ARROW_LEN
                            * Math.sin(th + Transition.ARROW_THETA)),
                (int) dy,
                (int) (dy + Transition.ARROW_LEN
                            * Math.sin(th - Transition.ARROW_THETA)),
            }; */

            //GraphicsUtil.switchToWidth(g, 3);
           // g.setColor(Color.blue);
            //g.drawPolyline(xp, yp, 3);
            //g.drawLine((int) (dx - INITARROW_LEN),
            //        (int) (dy + INITARROW_LEN),
            //        (int) dx, (int) dy);
        //}

        //Color bg = getAutomaton().getCurrentDrawStates().contains(this)
        //    ? Color.green : Color.red;
        Color bg = Color.red;
        GraphicsUtil.switchToWidth(g, 3);
        /*if(isFinal()) {
            g.setColor(Color.white);
            g.fillOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            g.setColor(bg);
            g.fillOval(x - RADIUS + 6, y - RADIUS + 6,
                2 * RADIUS - 12, 2 * RADIUS - 12);
            g.setColor(Color.black);
            g.drawOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            g.drawOval(x - RADIUS + 5, y - RADIUS + 5,
                2 * RADIUS - 10, 2 * RADIUS - 10);
            g.drawString(getName(), x-8, y+4);
        } else { */
            g.setColor(bg);
            g.fillOval(x - RADIUS + 1, y - RADIUS + 1,
                2 * RADIUS - 2, 2 * RADIUS - 2);
            g.setColor(Color.black);
            g.drawOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            g.drawString(getName(), x-8, y+4);
       // }
    }

    public void print(GroupedWriter fout) {
        super.print(fout);
        fout.print("name "); fout.printlnGroup(name);
        /*if(isInitial()) {
            fout.print("initial "); fout.printlnGroup("yes");
        }
        if(isFinal()) {
            fout.print("final "); fout.printlnGroup("yes");
        } */
        fout.print("coord "); fout.printlnGroup(x + " " + y);
    }
    public boolean setKey(String key, GroupedReader fin) throws IOException {
        if(key.equals("name")){
        	String what = fin.readGroup();
            setName(what);
            return true;
        }
        else if(key.equals("initial")) {
            String what = fin.readGroup();
            //if(what.equals("yes")) setInitial(true);
            return true;
        } else if(key.equals("final")) {
            String what = fin.readGroup();
            //if(what.equals("yes")) setFinal(true);
            return true;
        } else if(key.equals("coord")) {
            String value = fin.readGroup();
            int sep = value.indexOf(' ');
            if(sep < 0) {
                throw new IOException("Missing argument");
            }
            try {
                x = Integer.parseInt(value.substring(0, sep));
                y = Integer.parseInt(value.substring(sep + 1));
            } catch(NumberFormatException e) {
                throw new IOException("Nonnumeric argument");
            }
            return true;
        } else {
            return super.setKey(key, fin);
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		//System.out.println("Inside setName for string");
		this.name = name;
	}

	@Override
	public String toString() {
		return "State [name= "+name+"]\n";
	}
}
