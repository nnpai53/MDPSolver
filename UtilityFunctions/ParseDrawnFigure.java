package UtilityFunctions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import GUI.ContactEditorUI;
import input.MDPData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cburch.autosim.*;

/**
 *
 * @author neeraj
 */
public class ParseDrawnFigure {


    public void parseFigure(Automaton automaton, ContactEditorUI gui){
        int noOfState=0;
        String action="";
        String state="";
        String actionsoneachstate="";
        String rewards="";
        String tt="";
        String someLine = "";
        int noOfAction =0;
        LinkedList<AutomatonComponent> states = automaton.states;
    	LinkedList<AutomatonComponent> sActions = automaton.sActions;
    	LinkedList<AutomatonComponent> transitions = automaton.transitions;
    	LinkedList<AutomatonComponent> stateActionLinks = automaton.stateActionLinks;
    	int noOfStates = states.size();
    	int noOfActions = sActions.size();
    	int noOfSALinks = stateActionLinks.size();
	    int noOfTransitions = transitions.size();
        int actions[] = new int[1];
        int noOfActionsOnState[] = new int[noOfStates];
        double transitionTable[][] = new double[noOfActions][noOfStates];
        for(int i=0;i<noOfSALinks;i++){
        	StateActionLink saLink = (StateActionLink) stateActionLinks.get(i);
		    int k = states.indexOf(saLink.getSource());
		    noOfActionsOnState[k]++; 
        }
        double reward[] = new double[1];
        double gamma=0.5;
        double transitionMatrix[][]=new double[1][1];
        String val[];
        BufferedReader br=null;
        boolean is_correct=true;
        try{
      
            state = Integer.toString(noOfStates);
            action = Integer.toString(noOfActions);
            for(int i=0;i<noOfStates;i++){
            	actionsoneachstate += Integer.toString(noOfActionsOnState[i]) + " ";
            }
            for(int i=0;i<noOfSALinks;i++){
            	rewards += Double.toString(((StateActionLink)stateActionLinks.get(i)).getReward()) + " ";
            }
            for(int i=0;i<noOfActions;i++){
            	Action action1 =  (Action) (sActions.get(i));
            	for(int j=0;j<noOfTransitions;j++){
            		Transition trans1 = (Transition) transitions.get(j);
            	    if(action1 == trans1.getSource()){
                       int k = states.indexOf(trans1.getDest());
                       System.out.println("The value of k is now" + k);
                       transitionTable[i][k] = trans1.getProbability();
            	    }
            	}
            }
            
            for(int i=0;i<noOfActions;i++){
            	for(int j=0; j<noOfStates;j++){
            		tt += Double.toString(transitionTable[i][j]);
            		tt += " ";
            	}
            	tt += "\n";
            }
            
            if(is_correct=true){
                gui.getNoofstate().setText(state);
                gui.getNoofactions().setText(action);
                gui.getNoofactiononeachstate().setText(actionsoneachstate);
                gui.getRewards().setText(rewards);
                gui.getTransitiontable().setText(tt.substring(0, tt.length()-1));
            }
            else{
                gui.getTransitiontable().setText("Either MDP DATA IS NOT CORRECT OR DATA FORMAT IS NOT CORRECT");
            }
            
           // MDPData mdp = new MDPData(noOfState, noOfAction, reward, transitionMatrix, actions, gamma);
           // mdp.displayMDP();
        }
        catch(Exception ex){
            Logger.getLogger(ParseDrawnFigure.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}