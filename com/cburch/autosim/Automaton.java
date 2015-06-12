/* Copyright (c) 2006, Carl Burch. License information is located in the
 * com.cburch.autosim.Main source code and at www.cburch.com/proj/autosim/. */

package com.cburch.autosim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;


public class Automaton {
    public static final int NUM_FRAMES = 15;
    int stateCount = 0;
    int actionCount = 0;
    
    public LinkedList<AutomatonComponent> states = new LinkedList<AutomatonComponent>();
    public LinkedList<AutomatonComponent> sActions = new LinkedList<AutomatonComponent>();
    public LinkedList<AutomatonComponent> transitions = new LinkedList<AutomatonComponent>();
    public LinkedList<AutomatonComponent> stateActionLinks = new LinkedList<AutomatonComponent>();
    public LinkedList<AutomatonComponent> components = new LinkedList<AutomatonComponent>();
    //private Alphabet alphabet = new Alphabet("abcd" + Alphabet.ELSE);
    protected Set<String> actions = new HashSet<String>();

    private char autoStateName;
    private char autoActionName;
    public char getAutoStateName() {
		return autoStateName;
	}

    public char getAutoActionName() {
    	return autoActionName;
    }
    
	private StateSet currentStates = new StateSet();
	private ActionSet currentActions = new ActionSet();
    private StateSet current_draw_states = currentStates;
    private ActionSet current_draw_actions = currentActions;
    private Canvas canvas = null;
    private Rectangle bounding = null;

   // private LinkedList<?> history = new LinkedList<Object>();
        // for storing StateSets previously stepped through

    public Automaton() {
        
    }

    //
    // ABSTRACT METHODS
    //
    //public abstract State createState();
    //public abstract Transition createTransition(State src, State dst);
    
    //public abstract Action createAction();

    //
    // ACCESS METHODS
    //
    //public Alphabet getAlphabet() { return alphabet; }
    public Canvas getCanvas() { 
    	if(canvas == null)
    		return new Canvas();
    	return canvas;
    }
    //public List<?> getHistory() { return history; }

    public Iterator<AutomatonComponent> getStates() {
        return states.iterator();
    }
    
    public Iterator<AutomatonComponent> getActions(){
    	return sActions.iterator();
    }
    
    /*public StateSet getInitialStates() {
        StateSet ret = new StateSet();
        for(Iterator<AutomatonComponent> it = getStates(); it.hasNext(); ) {
            State state = (State) it.next();
            //if(state.isInitial()) ret.add(state);
        }
        return ret;
    } */
    
    public Iterator<AutomatonComponent> getTransitions() {
        return transitions.iterator();
    }
    
    public Iterator<AutomatonComponent> getStateActionLinks(){
    	return stateActionLinks.iterator();
    }
    
    public Iterator<AutomatonComponent> getComponents() {
        return components.iterator();
    }
    public Iterator<AutomatonComponent> getAllComponents() {
        return Iterators.join(getTransitions(),Iterators.join(getActions(),
        		Iterators.join(getStateActionLinks(), 
            Iterators.join(getStates(), getComponents()))));
    }
    public Iterator<AutomatonComponent> getAllComponentsReverse() {
        return Iterators.join(Iterators.reverse(components),
            Iterators.join(Iterators.reverse(states),
            Iterators.join(Iterators.reverse(sActions),
            Iterators.join(Iterators.reverse(stateActionLinks), 
            		Iterators.reverse(transitions)))));
    }

    //
    // CONFIGURATION METHODS
    //
    public void setCanvas(Canvas canvas) { this.canvas = canvas; }

    public void exposeConnectionsState(Graphics g, State what){
    	
    	for(Iterator<AutomatonComponent> it = getStateActionLinks(); it.hasNext(); ) {
            StateActionLink saLink = (StateActionLink) it.next();
            if(saLink.getSource() == what) {
                saLink.expose(g);
            }
        }
    	
        for(Iterator<AutomatonComponent> it = getTransitions(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(transition.getDest() == what) {
                transition.expose(g);
            }
        }
    }

    public void exposeConnectionsActions(Graphics g, Action what){
    	for(Iterator<AutomatonComponent> it = getStateActionLinks(); it.hasNext(); ) {
            StateActionLink saLink = (StateActionLink) it.next();
            if(saLink.getDest() == what) {
                saLink.expose(g);
            }
        }
    	
    	for(Iterator<AutomatonComponent> it = getTransitions(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(transition.getSource() == what) {
                transition.expose(g);
            }
        }
    }
    
    public AutomatonComponent addComponent(AutomatonComponent what) {
        components.add(what);
        invalidateBounds();
        return what;
    }
    public void removeComponent(AutomatonComponent what) {
        components.remove(what);
    }

    public State createState() {
    	stateCount++;
        State newState = new State(this,getAutoStateName()+Integer.toString(stateCount));
        return newState;
    }
    
    public State addState() {
        State q = createState();
        if(q != null) {
            states.add(q);
            invalidateBounds();
        }
        return q;
    }
    public void removeState(State what) {
    	
        currentStates.remove(what);
        current_draw_states.remove(what);
        states.remove(what);

        Graphics g = null;
        if(canvas != null) g = canvas.getGraphics();

        LinkedList<Action> to_remove_action = new LinkedList<Action>();
        for(Iterator<AutomatonComponent> it = getActions(); it.hasNext(); ) {
            Action action = (Action) it.next();
            if(action.getOwner() == what) {
                to_remove_action.add(action);
            }
        }
        
        for(Iterator<Action> it = to_remove_action.iterator(); it.hasNext(); ) {
            Action action = (Action) it.next();
            if(g != null) action.expose(g);
            sActions.remove(action);
        }
        
        LinkedList<Transition> to_remove_transition = new LinkedList<Transition>();
        for(Iterator<AutomatonComponent> it = getTransitions(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(transition.getDest() == what){
                to_remove_transition.add(transition);
            }
        }
        for(Iterator<Transition> it = to_remove_transition.iterator(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(g != null) transition.expose(g);
            transitions.remove(transition);
        }
        
        LinkedList<StateActionLink> to_remove_salink = new LinkedList<StateActionLink>();
        for(Iterator<AutomatonComponent> it = getStateActionLinks(); it.hasNext(); ) {
            StateActionLink saLink = (StateActionLink) it.next();
            if(saLink.getSource() == what){
                to_remove_salink.add(saLink);
            }
        }
        
        for(Iterator<StateActionLink> it = to_remove_salink.iterator(); it.hasNext(); ) {
            StateActionLink saLink = (StateActionLink) it.next();
            if(g != null) saLink.expose(g);
            stateActionLinks.remove(saLink);
        }
        
    }
    
    public Action createAction(){
    	actionCount++;
    	Action newAction = new Action(this,getAutoActionName()+Integer.toString(actionCount));
        return newAction;
    }
    
    public Action addAction() {
        Action q = createAction();
        if(q != null) {
            sActions.add(q);
            invalidateBounds();
        }
        return q;
    }
    
    public void removeAction(Action what){
    	currentActions.remove(what);
        current_draw_actions.remove(what);
        sActions.remove(what);

        Graphics g = null;
        if(canvas != null) g = canvas.getGraphics();
        
       
        LinkedList<StateActionLink> to_remove_salink = new LinkedList<StateActionLink>();
        for(Iterator<AutomatonComponent> it = getStateActionLinks(); it.hasNext(); ) {
            StateActionLink saLink = (StateActionLink) it.next();
            if(saLink.getDest() == what){
                to_remove_salink.add(saLink);
            }
        }
        
        for(Iterator<StateActionLink> it = to_remove_salink.iterator(); it.hasNext(); ) {
            StateActionLink saLink = (StateActionLink) it.next();
            if(g != null) saLink.expose(g);
            stateActionLinks.remove(saLink);
        }
        
        LinkedList<Transition> to_remove_transition = new LinkedList<Transition>();
        for(Iterator<AutomatonComponent> it = getTransitions(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(transition.getSource() == what){
                to_remove_transition.add(transition);
            }
        }
        
        for(Iterator<Transition> it = to_remove_transition.iterator(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(g != null) transition.expose(g);
            transitions.remove(transition);
        }

    }

    public Transition createTransition(Action src, State dst) {
        for(Iterator it = getTransitions(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            if(transition.getSource() == src
                    && transition.getDest() == dst) {
                return null;
            }
        }
        return (new Transition(this,src,dst));
    }
    
    public Transition addTransition(Action src, State dst) {
        Transition delta = createTransition(src, dst);
        if(delta != null) {
            transitions.add(delta);
            invalidateBounds();
        }
        return delta;
    }
    
    public StateActionLink createStateActionLink(State src, Action dst){
    	for(Iterator it = getStateActionLinks(); it.hasNext(); ) {
            StateActionLink stateActionLink = (StateActionLink) it.next();
            if(stateActionLink.getSource() == src
                    && stateActionLink.getDest() == dst) {
                return null;
            }
        }
        return (new StateActionLink(this,src,dst));
    }
    public StateActionLink addStateActionLink(State src, Action dst) {
        StateActionLink delta = createStateActionLink(src, dst);
        if(delta != null) {
            stateActionLinks.add(delta);
            invalidateBounds();
        }
        return delta;
    }
    
    public void removeTransition(Transition what) {
        transitions.remove(what);
    }

    public void removeStateActionLink(StateActionLink what){
    	stateActionLinks.remove(what);
    }
    
    public void remove(AutomatonComponent comp) {
        if(comp instanceof State) {
            removeState((State) comp);
        }else if(comp instanceof Action){
        	removeAction((Action)comp);
        }else if(comp instanceof Transition) {
            removeTransition((Transition) comp);
        }else if(comp instanceof StateActionLink){
        	removeStateActionLink((StateActionLink) comp);
        }else {
            removeComponent(comp);
        }
    }

    public StateSet getCurrentStates() {
        return currentStates;
    }
    public StateSet getCurrentDrawStates() {
        return current_draw_states;
    }
    
    public ActionSet getCurrentActions(){
        return currentActions;	
    }
    
    public ActionSet getCurrentDrawActions(){
        return current_draw_actions;	
    }
    
    public void setCurrent(StateSet data) {
        if(data == null) data = new StateSet();
        StateSet old_draw = current_draw_states;
        currentStates = data;
        current_draw_states = data;

        Graphics g = canvas.getGraphics();
        current_draw_states.expose(g);
        old_draw.expose(g);
    }

    //
    // GUI METHODS
    //
    public AutomatonComponent find(int x, int y, Graphics g) {
        for(Iterator<AutomatonComponent> it = getAllComponentsReverse(); it.hasNext(); ) {
            AutomatonComponent comp = (AutomatonComponent) it.next();
            if(comp.isIn(x, y, g)) return comp;
        }
        return null;
    }
    
    public State findState(int x, int y, Graphics g) {
        for(Iterator<AutomatonComponent> it = Iterators.reverse(states); it.hasNext(); ) {
            State state = (State) it.next();
            if(state.isIn(x, y, g)) return state;
        }
        return null;
    }
    
    public Action findAction(int x, int y, Graphics g){
    	for(Iterator<AutomatonComponent> it = Iterators.reverse(sActions); it.hasNext(); ) {
            Action action = (Action) it.next();
            if(action.isIn(x, y, g)) return action;
        }
        return null;
    }
    public void draw(Graphics g) {
        for(Iterator<AutomatonComponent> it = getAllComponents(); it.hasNext(); ) {
            ((AutomatonComponent) it.next()).draw(g);
        }
    }

    //
    // BOUNDING BOX METHODS
    //
    public Dimension getDimensions(Graphics g) {
        if(bounding == null) computeBoundingBox(g);
        int width = bounding.width;
        if(bounding.x > 0) width = bounding.x + bounding.width;
        int height = bounding.height;
        if(bounding.y > 0) height = bounding.y + bounding.height;
        return new Dimension(width, height);
    }
    public Rectangle getBounds(Graphics g) {
        if(bounding == null) computeBoundingBox(g);
        return new Rectangle(bounding);
    }
    public void invalidateBounds() { bounding = null; }
    private void computeBoundingBox(Graphics g) {
        bounding = null;
        Rectangle box = new Rectangle();
        for(Iterator<AutomatonComponent> it = getAllComponents(); it.hasNext(); ) {
            AutomatonComponent comp = (AutomatonComponent) it.next();
            comp.getBounds(box, g);
            if(bounding == null) {
                bounding = new Rectangle(box);
            } else {
                bounding.add(box);
            }
        }
        if(bounding == null) bounding = new Rectangle();
        bounding.grow(5, 5);
    }

    //
    // FILE METHODS
    //
    public void print(GroupedWriter fout) {
        if(this instanceof Automaton) fout.print("Automaton: "+Character.toString(autoStateName).toUpperCase());
        else fout.print("??");
        fout.print(" "); fout.beginGroup(); fout.println();
        printAutomaton(fout);
        fout.endGroup(); fout.println();
    }
    private void printAutomaton(GroupedWriter fout) {
      //  fout.print("alphabet ");
      // fout.printlnGroup(alphabet.toString());

        for(Iterator<AutomatonComponent> it = getStates(); it.hasNext(); ) {
            State state = (State) it.next();
            fout.print("state "); fout.beginGroup(); fout.println();
            state.print(fout);
            fout.endGroup(); fout.println();
        }
        
        for(Iterator<AutomatonComponent> it = getActions(); it.hasNext(); ) {
            Action action = (Action) it.next();
            fout.print("action "); fout.beginGroup(); fout.println();
            action.print(fout);
            fout.endGroup(); fout.println();
        }

        for(Iterator<AutomatonComponent> it = getTransitions(); it.hasNext(); ) {
            Transition transition = (Transition) it.next();
            int i = sActions.indexOf(transition.getSource());
            int j = states.indexOf(transition.getDest());
            fout.print("edge " + i + " " + j + " ");
            fout.beginGroup(); fout.println();
            transition.print(fout);
            fout.endGroup(); fout.println();
        }
        
        for(Iterator<AutomatonComponent> it = getStateActionLinks(); it.hasNext(); ) {
            StateActionLink saLink = (StateActionLink) it.next();
            int i = states.indexOf(saLink.getSource());
            int j = sActions.indexOf(saLink.getDest());
            fout.print("saLink " + i + " " + j + " ");
            fout.beginGroup(); fout.println();
            saLink.print(fout);
            fout.endGroup(); fout.println();
        }
        
        /*for(Iterator<AutomatonComponent> it = getComponents(); it.hasNext(); ) {
            AutomatonComponent comp = (AutomatonComponent) it.next();
            if(comp instanceof AutomatonLabel) {
                fout.print("label "); fout.beginGroup(); fout.println();
                comp.print(fout);
                fout.endGroup(); fout.println();
            }
        }*/

    }
    public static void read(Automaton ret, GroupedReader fin) throws IOException {
    	fin.beginGroup();
        while(!fin.atGroupEnd()) {
            String key = fin.readLine().trim();
            if(key != null && key.length() > 0) {
                if(!ret.setKey(key, fin)) {
                    fin.readGroup();
                }
            }
        }
        fin.endGroup();
    }
    public boolean setKey(String key, GroupedReader fin) throws IOException {

    	if(key.equals("state")) {
            fin.beginGroup();
            State state = addState();
            state.read(fin);
            fin.endGroup();
            return true;
        } else if(key.equals("action")){
        	fin.beginGroup();
            Action action = addAction();
            action.read(fin);
            fin.endGroup();
            return true;
        } else if(key.startsWith("edge ")) {
            StringTokenizer tokens = new StringTokenizer(key);
            try {
                tokens.nextToken();
                int src_i = Integer.parseInt(tokens.nextToken());
                Action src = (Action) sActions.get(src_i);
                if(src == null) {
                    throw new IOException("source " + src_i + " not defined");
                }

                int dst_i = Integer.parseInt(tokens.nextToken());
                State dst = (State) states.get(dst_i);
                if(dst == null) {
                    throw new IOException("dest " + dst_i + " not defined");
                }

                Transition transition = addTransition(src, dst);
                fin.beginGroup();
                transition.read(fin);
                fin.endGroup();
                return true;
            } catch(NumberFormatException e) {
                throw new IOException("ill-formatted edge (" + key + ")");
            } catch(IndexOutOfBoundsException e) {
                throw new IOException("ill-formatted edge ("
                    + key + ")");
            }
        } else if(key.startsWith("saLink ")) {
            StringTokenizer tokens = new StringTokenizer(key);
            try {
                tokens.nextToken();
                int src_i = Integer.parseInt(tokens.nextToken());
                State src = (State) states.get(src_i);
                if(src == null) {
                    throw new IOException("source " + src_i + " not defined");
                }

                int dst_i = Integer.parseInt(tokens.nextToken());
                Action dst = (Action) sActions.get(dst_i);
                if(dst == null) {
                    throw new IOException("dest " + dst_i + " not defined");
                }

                StateActionLink saLink = addStateActionLink(src, dst);
                fin.beginGroup();
                saLink.read(fin);
                fin.endGroup();
                return true;
            } catch(NumberFormatException e) {
                throw new IOException("ill-formatted saLink (" + key + ")");
            } catch(IndexOutOfBoundsException e) {
                throw new IOException("ill-formatted saLink ("
                    + key + ")");
            }
        } else {
            return false;
        }
    }
        
	public void setAutoStateName(char c) {
		autoStateName = c;
	}
	
	public void setAutoActionName(char c) {
		autoActionName = c;
	}
	
	public String toString() {
		String str = "Automaton:\nStates=";
		for (AutomatonComponent stateI : states) {
			State state = (State)stateI;
			str += state;
		}
		str += "Actions=";
		for(AutomatonComponent actionI : sActions){
			Action action = (Action)actionI;
			str += action;
		}
		str += "Links=";
		for (AutomatonComponent sActI : stateActionLinks) {
			StateActionLink sAct = (StateActionLink)sActI;
			str += sAct;
		}
		str += "Transitions=";
		for (AutomatonComponent tranI : transitions) {
			Transition tran = (Transition)tranI;
			str += tran;
		}
		return str;
	}
}
