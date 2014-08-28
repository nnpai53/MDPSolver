package policyiteration;

import gnuplotutils.Gnu_Script_Writer;
import gnuplotutils.PrintDataFile;
import input.MDPData;
import input.StateData;

import java.util.ArrayList;

public class SinglePivotModifiedPolicyIterationImpl
{
    private void init()
    {
        //graphPlotter = new ValueGraphPlotter("policyIteration", "policyIteration", "policyIteration", input.getNoOfStates());
        //mdpData = new MDPData();
        pdf = new PrintDataFile(SINGLEPIVOTMODIFIEDPOLICYITERATIONFOLDER);
        pdf.delete_Files();
        prevPolicy = new double[mdpData.getNoOfActions()];
    }
    
    public SinglePivotModifiedPolicyIterationImpl()
    {
        init();
    }
    
    public SinglePivotModifiedPolicyIterationImpl(MDPData mdpData)
    {
        this.mdpData = mdpData;
        init();
    }
    
    public SinglePivotModifiedPolicyIterationImpl(MDPData mdpData, double epsilonForValue)
    {
        this.mdpData = mdpData;
        this.epsilon = epsilonForValue;
        init();
    }
    
    public SinglePivotModifiedPolicyIterationImpl(MDPData mdpData, boolean isSeedProvided)
    {
        this.mdpData = mdpData;
        this.isSeedProvided = isSeedProvided;
        init();
    }
    
    public SinglePivotModifiedPolicyIterationImpl(MDPData mdpData, boolean isSeedProvided, double epsilonForValue)
    {
        this.mdpData = mdpData;
        this.isSeedProvided = isSeedProvided;
        this.epsilon = epsilonForValue;
        init();
    }
    
    private MDPData mdpData;
    private PrintDataFile pdf;
    public static String SINGLEPIVOTMODIFIEDPOLICYITERATIONFOLDER = "SinglePivotModifiedPolicyIteration";
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
        double maxValArr[] = new double[mdpData.getNoOfStates()];
        int maxActionIndexArr[] = new int[mdpData.getNoOfStates()];
        double preMaxValArr[] = mdpData.getValue();
        //double diffArr[] = new double[mdpData.getNoOfStates()];
        //int preMaxActionIndexArr[] = new int[mdpData.getNoOfStates()];
        double tempActionVal, maxStateVal, maxDiff=0.0, diff=0.0;
        
        double decisionVect[] = new double[mdpData.getNoOfActions()];
        int decisionVectCount = 0;
        
        int maxActionIndex, maxDiffIndex = -1;
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
                
                decisionVect[decisionVectCount] = tempActionVal - preMaxValArr[i];
                decisionVectCount++;
                
                if(tempActionVal > maxStateVal)
                {
                    maxStateVal = tempActionVal;
                    maxActionIndex = j;
                }
            }
            maxValArr[i] = maxStateVal;
            maxActionIndexArr[i] = maxActionIndex;
        }
        
        for(int i=0; i<maxValArr.length; i++)
        {
            diff = maxValArr[i] - preMaxValArr[i];
            if(diff > maxDiff)
            {
                if(mdpData.getStateList().get(i).getActionList().get(maxActionIndexArr[i]).getProbDist() != 1.0)
                {
                    maxDiff = diff;
                    maxDiffIndex = i;                    
                }
            }
        }
        
        if(maxDiffIndex != -1)
        {
            for(int k=0; k<mdpData.getStateList().get(maxDiffIndex).getNoOfActions(); k++)
            {
                if(k==maxActionIndexArr[maxDiffIndex])
                {
                    mdpData.getStateList().get(maxDiffIndex).getActionList().get(k).setProbDist(1.0);
                }
                else
                {
                    mdpData.getStateList().get(maxDiffIndex).getActionList().get(k).setProbDist(0.0);
                }
            }
            mdpData.getStateList().get(maxDiffIndex).setValue(maxValArr[maxDiffIndex]);
        }
        else
        {
            //TODO
            //POLICY FOUND!! NO NEED FOR FURTHER ITERATIONS.
        }
        
        for(int i=0; i<mdpData.getNoOfActions(); i++)
        {
            System.out.println("SinglePivotModifiedPolicyIteration :: calculateAndSetPolicy() : DECISION VECTOR Action "+i+" : "+decisionVect[i]);
        }
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
        Gnu_Script_Writer imager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), SINGLEPIVOTMODIFIEDPOLICYITERATIONFOLDER);
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
        Gnu_Script_Writer imager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), SINGLEPIVOTMODIFIEDPOLICYITERATIONFOLDER);
    }
}
