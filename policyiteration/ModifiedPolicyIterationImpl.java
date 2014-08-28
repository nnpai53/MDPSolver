package policyiteration;

import gnuplotutils.Gnu_Script_Writer;
import gnuplotutils.PrintDataFile;
import input.MDPData;
import input.RandomizeMDP;
import input.StateData;

import java.util.ArrayList;

public class ModifiedPolicyIterationImpl
{
    private void init()
    {
        //graphPlotter = new ValueGraphPlotter("policyIteration", "policyIteration", "policyIteration", input.getNoOfStates());
        //mdpData = new MDPData();
        pdf = new PrintDataFile(MODIFIEDPOLICYITERATIONFOLDER);
        pdf.delete_Files();
        prevPolicy = new double[mdpData.getNoOfActions()];
    }
    
    public ModifiedPolicyIterationImpl()
    {
        init();
    }
    
    public ModifiedPolicyIterationImpl(MDPData mdpData)
    {
        this.mdpData = mdpData;
        init();
    }
    
    public ModifiedPolicyIterationImpl(MDPData mdpData, double epsilonForValue)
    {
        this.mdpData = mdpData;
        this.epsilon = epsilonForValue;
        init();
    }
    
    public ModifiedPolicyIterationImpl(MDPData mdpData, boolean isSeedProvided)
    {
        this.mdpData = mdpData;
        this.isSeedProvided = isSeedProvided;
        init();
    }
    
    public ModifiedPolicyIterationImpl(MDPData mdpData, boolean isSeedProvided, double epsilonForValue)
    {
        this.mdpData = mdpData;
        this.isSeedProvided = isSeedProvided;
        this.epsilon = epsilonForValue;
        init();
    }
    
    private MDPData mdpData;
    private PrintDataFile pdf;
    public static String MODIFIEDPOLICYITERATIONFOLDER = "ModifiedPolicyIteration";
    private double[] prevPolicy;
    private boolean isSeedProvided = false;
    private double epsilon = 0.0001;
    
    public boolean checkInput()
    {
        return (mdpData != null);
    }
    
    public boolean checkTermination()
    {
        double[] curPolicy = mdpData.getPolicy();
        for(int i=0; i<mdpData.getNoOfActions(); i++)
        {
            if(curPolicy[i] != prevPolicy[i])
            {
                return false;
            }
        }
        return true;
    }
    
    public void makeInitialPolicy()
    {
        for(int i=0; i<mdpData.getNoOfStates(); i++)
        {
            mdpData.getStateList().get(i).getActionList().get(0).setProbDist(1.0);            
        }
    }
    
    public boolean checkTerminationForValueIteration(double prevVal[], double curVal[])
    {
        if(prevVal == null)
        {
            return false;
        }
        double limit = epsilon * (1 - mdpData.getGamma())/mdpData.getGamma();
        for(int i=0; i<mdpData.getNoOfStates(); i++)
        {
            double diff = curVal[i] - prevVal[i];
            if(diff < 0)
            {
                diff = -1 * diff;
            }
            if(diff >= limit)
            {
                //System.out.println("ValueIterationImpl : checkInput :: checkTermination() Ending with FALSE");
                return false;
            }
        }
        //System.out.println("ValueIterationImpl : checkInput :: checkTermination() Ending with TRUE");
        return true;
    }
    
    public void calculateAndSetValues()
    {
        double val[] = mdpData.getValue();
        double prevVal[] = null;;
        
        int actionIndicesOfPolicy[] = new int[mdpData.getNoOfStates()];
        StateData tempState;
        for(int i=0; i<mdpData.getNoOfStates(); i++)
        {
            tempState = mdpData.getStateList().get(i);
            for(int j=0; j<tempState.getNoOfActions(); j++)
            {
                if(tempState.getActionList().get(j).getProbDist() == 1.0)
                {
                    actionIndicesOfPolicy[i] = j;
                    break;
                }
            }
        }
        
        while(!checkTerminationForValueIteration(prevVal, val))
        {
            prevVal = val;
            val = new double[mdpData.getNoOfStates()];
            double tempActionVal;
            int stateCounter;
            ArrayList<Double> transitionList;
            for(int i=0; i<mdpData.getNoOfStates(); i++)
            {
                tempActionVal =  mdpData.getStateList().get(i).getActionList().get(actionIndicesOfPolicy[i]).getReward();
                transitionList = mdpData.getStateList().get(i).getActionList().get(actionIndicesOfPolicy[i]).getTransitionFunc();
                for(stateCounter=0; stateCounter<mdpData.getNoOfStates(); stateCounter++)
                {
                    tempActionVal = tempActionVal + mdpData.getGamma() * (transitionList.get(stateCounter) * prevVal[stateCounter]);                    
                }
                val[i] = tempActionVal;
            }
        }
        mdpData.setValue(val);
    }
    
    
    public void calculateAndSetPolicy()
    {
        prevPolicy = mdpData.getPolicy();
        double val[] = new double[mdpData.getNoOfStates()];
        double tempActionVal, maxStateVal;
        int maxActionIndex;
        ArrayList<Double> transitionList;
        for(int i=0; i<mdpData.getNoOfStates(); i++)
        {
            maxStateVal = 0.0; //mdpData.getStateList().get(i).getValue();
            maxActionIndex = -1;
            tempActionVal = 0.0;
            for(int j=0; j<mdpData.getStateList().get(i).getNoOfActions(); j++)
            {
                tempActionVal =  mdpData.getStateList().get(i).getActionList().get(j).getReward();
                transitionList = mdpData.getStateList().get(i).getActionList().get(j).getTransitionFunc();
                for(int stateCounter=0; stateCounter<mdpData.getNoOfStates(); stateCounter++)
                {
                    tempActionVal = tempActionVal + mdpData.getGamma() * transitionList.get(stateCounter) * mdpData.getStateList().get(stateCounter).getValue();                    
                }
                if(tempActionVal > maxStateVal)
                {
                    maxStateVal = tempActionVal;
                    maxActionIndex = j;
                }
            }
            val[i] = maxStateVal;
            for(int k=0; k<mdpData.getStateList().get(i).getNoOfActions(); k++)
            {
                if(k==maxActionIndex)
                {
                    mdpData.getStateList().get(i).getActionList().get(k).setProbDist(1.0);
                }
                else
                {
                    mdpData.getStateList().get(i).getActionList().get(k).setProbDist(0.0);
                }
            }
        }
        //mdpData.setValue(val);
    }
    
    public void run()throws Exception
    {
        if(!checkInput())
        {
            System.out.println("PolicyIterationImpl :: run() : Input not proper");
        }
        if(!isSeedProvided)
        {
            makeInitialPolicy();
        }
        while(!checkTermination())
        {
            prevPolicy = mdpData.getPolicy();
            calculateAndSetValues();
            System.out.println("PolicyIteration :: run() : displaying mdp values");
            mdpData.displayPolicy();
            mdpData.displayRewards();
            mdpData.displayValue();
            System.out.println("");
            pdf.printData(mdpData);
            calculateAndSetPolicy();
        }
        calculateAndSetValues();
        Gnu_Script_Writer imager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), MODIFIEDPOLICYITERATIONFOLDER);
    }
    
    //Give only pure policy.
    public void runWithSeedPoint(double policy[])throws Exception
    {
        if(!checkInput())
        {
            System.out.println("PolicyIterationImpl :: runWithSeedPoint() : Input not proper");
        }
        if(policy.length != mdpData.getNoOfActions())
        {
            System.out.println("PolicyIterationImpl :: runWithSeedPoint() : policy length is not equal to number of actions in mdp");
        }
        mdpData.setPolicy(policy);
        while(!checkTermination())
        {
            prevPolicy = mdpData.getPolicy();
            calculateAndSetValues();
            pdf.printData(mdpData);
            calculateAndSetPolicy();
        }
        calculateAndSetValues();
        Gnu_Script_Writer imager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), MODIFIEDPOLICYITERATIONFOLDER);
    }
    
    
   /* public static void main(String args[])
    {
        RandomizeMDP randomizer = new RandomizeMDP(7, 4, 20);
        MDPData mdpData = new MDPData(randomizer.getNoOfStates(), randomizer.getNoOfActions(), randomizer.getRewards_As_Array(), randomizer.getTransition_Table_As_Array(), randomizer.getAction_Per_State_As_Array(), 0.5);
        System.out.println("===========Displaying MDP============");
        mdpData.displayMDP();
        System.out.println("===========End MDP============");
        PolicyIterationImpl pol = new PolicyIterationImpl(mdpData);
        pol.run();
        System.out.println("===========Displaying FINAL MDP============");
        mdpData.displayMDP();
        System.out.println("===========End FINAL MDP============");
        
    }*/
}
