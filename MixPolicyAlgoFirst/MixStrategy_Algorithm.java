/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MixPolicyAlgoFirst;

import gnuplotutils.PrintDataFile;
import input.ActionData;
import input.MDPData;
import input.SeedPoint;
import java.util.ArrayList;

/**
 *
 * @author vinit
 */
public class MixStrategy_Algorithm {
    
    MDPData mdp;
  
    private double[] policy;
    
    private double valueoneachstate[];
    
    private double currentVal;
    
    private double previousVal;
    
    private SeedPoint seed;
    
    private PrintDataFile pdf = new PrintDataFile("MixStrategy");
    public MixStrategy_Algorithm(MDPData mdp){
        this.mdp =mdp;
        seed = new SeedPoint();
    }
    
    private void gettUniformPolicy()
    {
        policy = new double[mdp.getNoOfActions()];
        
        int noofActioneachstate[] =  mdp.getNumOfActionsOnEachState();
        
        int count=0;
        
        int state=0;
        
        for(int i=0;i<mdp.getNoOfActions();i++)
        {
            if(count<noofActioneachstate[state]){
                policy[i]=(1.0)/noofActioneachstate[state];
                count++;
            }
            else
            {
                count=1;
                state++;
                policy[i]=(1.0)/noofActioneachstate[state];

            }
        }
    }
    
    
    private double actionContribution(int state, int action)
    {
        double ret=0.0;
        ActionData ad = mdp.getStateList().get(state).getActionList().get(action);

        ret += ad.getReward();
        
        ArrayList<Double> transitionvector= ad.getTransitionFunc();
        
        for(int i=0;i<valueoneachstate.length;i++){
            ret+= mdp.getGamma()*transitionvector.get(i)*valueoneachstate[i];
        }
        
        ret = ret*this.policy[mdp.action_Number(state, action)];
        
        return ret;
        
    }
    
    
    
    private ArrayList<Double> reVisedPolicyForState(int state)
    {
        
        int action = mdp.getStateList().get(state).getNoOfActions();
        
        ArrayList<Double> ret =  new ArrayList<Double>();
        
        ArrayList<Double> pret =  new ArrayList<Double>();
        
        double totalVal = 0;
        
        for(int i=0;i<action;i++)
        {
            double temp = this.actionContribution(state,i);
            ret.add(temp);
            totalVal += temp;
        }
        
        for(int i=0;i<action;i++)
        {
            double temp = ret.get(i)/(totalVal*1.0);
            pret.add(temp);
        }
        return pret;
    }
    
    private double[] reVisedPolicy()
    {
        double[] ret = new double[mdp.getNoOfActions()];
        
        ArrayList<Double> retList = new ArrayList<Double>();
        int count=0;
        
        for(int i=0; i<mdp.getNoOfStates();i++)
        {
            ArrayList<Double> temp = this.reVisedPolicyForState(i);
            for(int j=0;j<temp.size();j++)
            {
                ret[count] = temp.get(j);
                count++;
                
            }
            
        }
        return ret;
    }
    
    
    
    private double[] valueAtEachState()
    {
        double ret[] = new double[mdp.getNoOfStates()];
        
        int actionOneachState[] = mdp.getNumOfActionsOnEachState();
        
        int count=0;
        
        int state=0;
        
        double val=0.0;
        
        for(int i=0;i<mdp.getNoOfActions();i++)
        {
            
            if(count<actionOneachState[state])
            {
                val = val+ actionContribution(state, count);
                count++;
            }
            else
            {
                count=0;
                ret[state]=val;
                val =0;
                state++;
                val = val+ actionContribution(state, count);
                count++;
            }
            
        }
        ret[state]=val;
        return ret;
    } 
    
    
    private void setMDPData(double[] values)
    {
        mdp.setPolicy(policy);
        mdp.setValue(values);
        
    }
    
    public void fillSeed()
    {
        seed.setPolicy(mdp.getPolicy());
        seed.setValue(mdp.getValue());
    }
    private void mixAlgorithm()
    {
        int count=0;
        setMDPData(valueoneachstate);
        fillSeed();
        mdp.PrintMDP(pdf);
        previousVal =0.0;
        currentVal = mdp.getSumOfAllValues();
        
        while(count==0 ||(currentVal-previousVal)>0.0001){
            //for precision can change to desired value
            count=1;
            previousVal = currentVal;
            double temp[] = this.valueAtEachState();
            policy = reVisedPolicy();
            valueoneachstate = temp;
            setMDPData(valueoneachstate);
            currentVal = mdp.getSumOfAllValues();
            mdp.PrintMDP(pdf);            
        }
                        
    }

    public SeedPoint getSeed() {
        return seed;
    }
    
    public void runMixStrategy(double[]...seed)
    {
        if(seed.length==0){
            this.gettUniformPolicy();
            mdp.setPolicy(policy);
            valueoneachstate =new double[mdp.getNoOfStates()];
            mixAlgorithm();
        }
        else
        {
        
        }
    }
    
    
}
