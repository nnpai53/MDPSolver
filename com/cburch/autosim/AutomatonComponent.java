/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public abstract class AutomatonComponent {
    protected Automaton automaton;

    class DeleteItem extends JMenuItem 
            implements ActionListener {
        public DeleteItem() {
            super("Delete");
            addActionListener(this);
        }
        public void actionPerformed(ActionEvent evt) {
            AutomatonComponent.this.expose(automaton.getCanvas().getGraphics());
            AutomatonComponent.this.remove();
            automaton.getCanvas().commitTransaction(true);
        }
    }

    public AutomatonComponent(Automaton automaton) {
        this.automaton = automaton;
    }
    
    JMenuItem createDeleteItem() {
        return new DeleteItem();
    }
    
    Automaton getAutomaton() {
        return automaton;
    }

    public abstract Rectangle getBounds(Rectangle rect, Graphics g);
    public abstract boolean isIn(int x, int y, Graphics g);
    public abstract void remove();
    public abstract void draw(Graphics g);

    public void expose(Graphics g) {
        Rectangle rect = new Rectangle();
        automaton.getCanvas().expose(getBounds(rect, g));
    }

    public void createMenu(JPopupMenu menu) {
        menu.add(new DeleteItem());
    }
    public void showMenu(int x, int y) {
        /*JPopupMenu menu = new JPopupMenu();
        createMenu(menu);
        menu.show(automaton.getCanvas(), x, y);*/
    }

    public void print(GroupedWriter fout) { }
    public boolean setKey(String key, GroupedReader fin)
            throws IOException {
        return false;
    }
    public void read(GroupedReader fin) throws IOException {
        while(!fin.atGroupEnd()) {
            String key = fin.readLine().trim();
            if(key != null && key.length() > 0) {
                if(!setKey(key, fin)) {
                    fin.readGroup();
                }
            }
        }
    }
}