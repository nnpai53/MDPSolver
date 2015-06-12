/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

class StateSet {
    //private Automaton automaton;
    private LinkedList<State> states = new LinkedList<State>();

   /* public StateSet(Automaton automaton) {
        this.automaton = automaton;
    }*/
    
    public int size() {
        return states.size();
    }
    public Iterator iterator() {
        return states.iterator();
    }
    public boolean contains(State what) {
        return states.contains(what);
    }
    public void remove(State what) {
        states.remove(what);
    }
    public void add(State state) {
        if(!states.contains(state)) {
            states.add(state);
        }
    }
    public void expose(Graphics g) {
        for(Iterator it = iterator(); it.hasNext(); ) {
            State state = (State) it.next();
            state.expose(g);
        }
    }
    /*public Object[] advance(char what) {
        StateSet ret = new StateSet(automaton);
        StateSet used = new StateSet(automaton);
        LinkedList traversed = new LinkedList();

        if(what == Alphabet.EPSILON) {
            for(Iterator it = iterator(); it.hasNext(); ) {
                ret.add((State) it.next());
            }
        } else {
            // find transitions for selected character
            for(Iterator it = automaton.getTransitions(); it.hasNext(); ) {
                Transition transition = (Transition) it.next();
                if(transition.transitsOn(what)
                        && contains(transition.getSource())) {
                    ret.add(transition.getDest());
                    used.add(transition.getSource());
                    traversed.add(transition);
                }
            }

            // handle ELSE transitions
            for(Iterator it = automaton.getTransitions(); it.hasNext(); ) {
                Transition transition = (Transition) it.next();
                if(transition.transitsOn(Alphabet.ELSE)
                        && contains(transition.getSource())
                        && !used.contains(transition.getSource())) {
                    ret.add(transition.getDest());
                    traversed.add(transition);
                }
            }
        }

        // closure on EPSILON transitions
        boolean changed = true;
        while(changed) {
            changed = false;
            for(Iterator it = automaton.getTransitions(); it.hasNext(); ) {
                Transition transition = (Transition) it.next();
                if(transition.transitsOn(Alphabet.EPSILON)
                        && ret.contains(transition.getSource())) {
                    if(!ret.contains(transition.getDest())) {
                        ret.add(transition.getDest());
                        changed = true;
                    }
                    if(!traversed.contains(transition)) {
                        traversed.add(transition);
                    }
                }
            }
        }
        return new Object[] { ret, traversed };
    }*/
}
