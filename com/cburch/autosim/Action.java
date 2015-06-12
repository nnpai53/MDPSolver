/* Copyright (c) 2015, Neeraj Pai.  */

package com.cburch.autosim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

public class Action extends AutomatonComponent {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final double INITARROW_LEN = 1.5 * Transition.ARROW_LEN;

    private String name;
    private State ownerState = null;
    private int x = 0;
    private int y = 0;

    public Action(Automaton automaton, String name){
    	super(automaton);
    //	System.out.println("Inside the action component");
        this.name = name;
    }
    
    public Action(Automaton automaton, String name, State state) {
        this(automaton,name);
        System.out.println("Inside the action component");
        this.ownerState = state;
    }

    public State getOwner(){
    	return this.ownerState;
    }
    
    public void setOwner(State state){
    	this.ownerState = state;
    }
    
    public void remove() { getAutomaton().removeAction(this); }

    public boolean isCurrent() { return getAutomaton().getCurrentActions().contains(this); }
    public int getX() { return x; }
    public int getY() { return y; }
    public Action move(int x, int y) {
        this.x = x;
        this.y = y;
        getAutomaton().invalidateBounds();
        return this;
    }

    public Rectangle getBounds(Rectangle rect, Graphics g) {
        rect.setBounds(x - WIDTH, y - HEIGHT, 2*WIDTH, 2*HEIGHT);
        rect.grow(2, 2);
        return rect;
    }

    public boolean isIn(int x0, int y0, Graphics g) {
    	double a = 10.0;
    	double b = 10.0;
    	int xabs = Math.abs(x - x0);
    	int yabs = Math.abs(y - y0);
        return ((xabs/a + yabs/b) <= 1.0);
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
        //Color bg = getAutomaton().getCurrentDrawActions().contains(this)
        //    ? Color.green : Color.red;
    	Color bg = Color.green;
        GraphicsUtil.switchToWidth(g, 3);
        g.setColor(bg);
        int a[] = {x-20,x,x+20,x,x-20};
        int b[] = {y,y-20,y,y+20,y};
        int npoints = 5;
        g.fillPolygon(a,b,npoints);
        g.setColor(Color.black);
        g.drawPolygon(a,b,npoints);
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
        else if(key.equals("coord")) {
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
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Action [name= "+name+"]\n";
	}
}
