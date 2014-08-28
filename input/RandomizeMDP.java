/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package input;

import GUI.ContactEditorUI;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author vinit
 */
public class RandomizeMDP {
    private ArrayList<Integer> action_Per_State;
    
    private ArrayList<Double> rewards;
    
    private ArrayList<ArrayList<Double>> transition_Table;
    
    private int maxStates = 10;
    private int maxActionsPerState = 5;
    private int maxReward = 20;
    
    private int noOfStates;
    private int noOfActions;
    
    public int[] getAction_Per_State_As_Array()
    {
        int ret[] = new int[noOfStates];
        for(int i=0; i<noOfStates; i++)
        {
            ret[i] = action_Per_State.get(i);
        }
        return ret;
    }
    
    public double[] getRewards_As_Array()
    {
        double ret[] = new double[noOfActions];
        for(int i=0; i<noOfActions; i++)
        {
            ret[i] = rewards.get(i);
        }
        return ret;
    }
    
    public double[][] getTransition_Table_As_Array()
    {
        double[][] ret = new double[noOfActions][noOfStates];
        for(int i=0; i<noOfActions; i++)
        {
            for(int j=0; j<noOfStates; j++)
            {
                ret[i][j] = transition_Table.get(i).get(j);
            }
        }
        return ret;
    }
    
    public void create_MDP(ContactEditorUI gui){
        
        System.out.println("NO OF ACTION="+ gui.getNoofstate().getText());
        Random randomGenerator = new Random();
        int no_of_state=0;
        if(gui!= null){
            if(gui.getNoofstate().getText().equals("")){
                no_of_state = randomGenerator.nextInt(maxStates)+1;  // here max state will be 10 and min will be 1
            }
            else
                no_of_state = Integer.parseInt(gui.getNoofstate().getText());
        }
        else
                no_of_state = randomGenerator.nextInt(maxStates)+1;
            
        int total_no_action = 0 ;
        
        for(int i=0; i< no_of_state; i++){
            int temp = randomGenerator.nextInt(maxActionsPerState)+1; //Here Max action on each state will be 5 and min 1
            total_no_action = total_no_action+ temp;
            action_Per_State.add(temp);
        }
        
        for(int i=0; i< total_no_action; i++){
            
            double val = randomGenerator.nextInt(maxReward) + randomGenerator.nextDouble()+1;
            
            rewards.add(val);
        }
        
        for(int i=0;i<total_no_action; i++){
            ArrayList<Double> transition =  new ArrayList<Double>();
            for(int j=0; j<no_of_state; j++){
                transition.add(0.0);
            }
            double count = 1.0;
            int state_count=0;
            while(count > 0.05  && state_count < no_of_state){
                state_count++;
                int pos = randomGenerator.nextInt(no_of_state);
                double diff = .25 - 0.0;
                double val = randomGenerator.nextDouble()*diff;
               // System.out.println("position "+pos + " value"+ val);
                if(count >= val){
                    if(transition.get(pos)== 0.0){
                        transition.set(pos,val);
                        count =count - val;
                    }
                    else{
                       state_count--; 
                    }
                }
                else{
                   transition.set(pos, transition.get(pos)+count);
                   count =0.0;
                }
            }
            int pos =randomGenerator.nextInt(no_of_state);
            transition.set(pos, transition.get(pos)+count);
            transition_Table.add(transition);
            
            noOfActions = transition_Table.size();
            noOfStates = action_Per_State.size();
        }
        
        
    }
    
    public RandomizeMDP(ContactEditorUI gui){
        action_Per_State = new ArrayList<Integer>();
        rewards = new ArrayList<Double>();
        transition_Table = new ArrayList<ArrayList<Double>>();
        this.create_MDP(gui);
    }
    
    public RandomizeMDP(int maxNumOfStates, int maxNumOfActionsPerState, int maxReward, ContactEditorUI gui)
    {
        maxActionsPerState = maxNumOfActionsPerState;
        maxStates = maxNumOfStates;
        this.maxReward = maxReward;
        action_Per_State = new ArrayList<Integer>();
        rewards = new ArrayList<Double>();
        transition_Table = new ArrayList<ArrayList<Double>>();
        this.create_MDP(gui);
    }

    public ArrayList<Integer> getAction_Per_State() {
        return action_Per_State;
    }

    public ArrayList<Double> getRewards() {
        return rewards;
    }

    public ArrayList<ArrayList<Double>> getTransition_Table() {
        return transition_Table;
    }

    public int getMaxStates()
    {
        return maxStates;
    }

    public void setMaxStates(int maxStates)
    {
        this.maxStates = maxStates;
    }

    public int getMaxActionsPerState()
    {
        return maxActionsPerState;
    }

    public void setMaxActionsPerState(int maxActionsPerState)
    {
        this.maxActionsPerState = maxActionsPerState;
    }

    public void setAction_Per_State(ArrayList<Integer> actionPerState)
    {
        action_Per_State = actionPerState;
    }

    public void setRewards(ArrayList<Double> rewards)
    {
        this.rewards = rewards;
    }

    public void setTransition_Table(ArrayList<ArrayList<Double>> transitionTable)
    {
        transition_Table = transitionTable;
    }

    public int getNoOfStates()
    {
        return noOfStates;
    }

    public void setNoOfStates(int noOfStates)
    {
        this.noOfStates = noOfStates;
    }

    public int getNoOfActions()
    {
        return noOfActions;
    }

    public void setNoOfActions(int noOfActions)
    {
        this.noOfActions = noOfActions;
    }
}