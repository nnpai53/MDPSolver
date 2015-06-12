/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

class AutomatonLabel extends AutomatonComponent {
    private class Owner implements LabelOwner {
        public int getLabelX(Label which) { return x; }
        public int getLabelY(Label which) { return y; }
        public int getLabelHAlign(Label which) { return halign; }
        public int getLabelVAlign(Label which) { return valign; }
    }

    private Label label;
    private int x;
    private int y;
    private int halign = GraphicsUtil.H_LEFT;
    private int valign = GraphicsUtil.V_BASELINE;

    public AutomatonLabel(Automaton automaton) {
        super(automaton);
        this.label = new Label(new Owner());
    }

    public AutomatonLabel move(int x, int y) {
        this.x = x;
        this.y = y;
        getAutomaton().invalidateBounds();
        return this;
    }

    public AutomatonLabel setAlignment(int halign, int valign) {
        this.halign = halign;
        this.valign = valign;
        return this;
    }

    public Label getLabel() { return label; }
    public Rectangle getBounds(Rectangle rect, Graphics g) {
        return label.getBounds(rect, g);
    }
    public boolean isIn(int x, int y, Graphics g) {
        return label.getBounds(g).contains(x, y);
    }
    public void remove() {
        getAutomaton().removeComponent(this);
    }
    public void draw(Graphics g) {
        label.draw(g);
    }

    public void print(GroupedWriter fout) {
        super.print(fout);
        label.print(fout);
        fout.print("coord "); fout.printlnGroup(x + " " + y);
        if(halign != GraphicsUtil.H_LEFT) {
            fout.print("halign");
            switch(halign) {
            case GraphicsUtil.H_RIGHT:
                fout.printlnGroup("right");
                break;
            case GraphicsUtil.H_CENTER:
                fout.printlnGroup("center");
                break;
            default: fout.printlnGroup("??");
            }
        }
        if(valign != GraphicsUtil.V_BASELINE) {
            fout.print("valign");
            switch(halign) {
            case GraphicsUtil.V_BOTTOM:
                fout.printlnGroup("bottom");
                break;
            case GraphicsUtil.V_CENTER:
                fout.printlnGroup("center");
                break;
            case GraphicsUtil.V_TOP:
                fout.printlnGroup("top");
                break;
            default: fout.printlnGroup("??");
            }
        }
    }
    public boolean setKey(String key, GroupedReader fin)
            throws IOException {
        if(key.equals("coord")) {
            String value = fin.readGroup();
            int sep = value.indexOf(' ');
            if(sep < 0) {
                throw new IOException("Missing argument");
            }
            try {
                move(Integer.parseInt(value.substring(0, sep)),
                    Integer.parseInt(value.substring(sep + 1)));
            } catch(NumberFormatException e) {
                throw new IOException("Nonnumeric argument");
            }
            return true;
        } else if(key.equals("halign")) {
            String val = fin.readGroup();
            if(val.equals("right")) halign = GraphicsUtil.H_RIGHT;
            if(val.equals("center")) halign = GraphicsUtil.H_CENTER;
            return true;
        } else if(key.equals("valign")) {
            String val = fin.readGroup();
            if(val.equals("bottom")) halign = GraphicsUtil.V_BOTTOM;
            if(val.equals("center")) halign = GraphicsUtil.V_CENTER;
            if(val.equals("top")) halign = GraphicsUtil.V_TOP;
            return true;
        } else if(label.setKey(key, fin)) {
            return true;
        } else {
            return super.setKey(key, fin);
        }
    }
}
