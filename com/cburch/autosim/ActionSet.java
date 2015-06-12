/* Copyright (c) 2015, Neeraj Pai.  */

package com.cburch.autosim;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

class ActionSet {
    //private Automaton automaton;
    private LinkedList<Action> actions = new LinkedList<Action>();

   /* public StateSet(Automaton automaton) {
        this.automaton = automaton;
    }*/
    
    public int size() {
        return actions.size();
    }
    public Iterator iterator() {
        return actions.iterator();
    }
    public boolean contains(Action what) {
        return actions.contains(what);
    }
    public void remove(Action what) {
    	actions.remove(what);
    }
    public void add(Action action) {
        if(!actions.contains(action)) {
        	actions.add(action);
        }
    }
    public void expose(Graphics g) {
        for(Iterator it = iterator(); it.hasNext(); ) {
            Action action = (Action) it.next();
            action.expose(g);
        }
    }
}
