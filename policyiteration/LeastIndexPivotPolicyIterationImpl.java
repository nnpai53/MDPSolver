package policyiteration;

import gnuplotutils.Gnu_Script_Writer;
import gnuplotutils.PrintDataFile;
import input.MDPData;

import java.util.ArrayList;

import Jama.Matrix;

public class LeastIndexPivotPolicyIterationImpl
{
    private void init()
    {
        //graphPlotter = new ValueGraphPlotter("policyIteration", "policyIteration", "policyIteration", input.getNoOfStates());
        //mdpData = new MDPData();
        pdf = new PrintDataFile(LASTINDEXPIVOTPOLICYITERATIONFOLDER);
        pdf.delete_Files();
        prevPolicy = new double[mdpData.getNoOfActions()];
    }
    
    public LeastIndexPivotPolicyIterationImpl()
    {
        init();
    }
    
    public LeastIndexPivotPolicyIterationImpl(MDPData mdpData)
    {
        this.mdpData = mdpData;
        init();
    }
    
    public LeastIndexPivotPolicyIterationImpl(MDPData mdpData, boolean isSeedProvided)
    {
        this.mdpData = mdpData;
        this.isSeedProvided = isSeedProvided;
        init();
    }
    
    private MDPData mdpData;
    private PrintDataFile pdf;
    public static String LASTINDEXPIVOTPOLICYITERATIONFOLDER = "LastIndexPivotPolicyIteration";
    private double[] prevPolicy;
    private boolean isSeedProvided = false;
    
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
    
    private double[][] makeIdentityMatrix(int order)
    {
        double ret[][] = new double[order][order];
        for(int i=0; i<order; i++)
        {
            for(int j=0; j<order; j++)
            {
                if(i==j)
                {
                    ret[i][j] = 1.0;                    
                }
                else
                {
                    ret[i][j] = 0.0;                    
                }
            }
        }
        return ret;
    }
    
    
    public void calculateAndSetValues()
    {
        //mdpData.makeCoeffMatrices();
        //double[][] A = mdpData.getPrimalCoeffMatrix();
        double[][] P_pi = mdpData.getTransitionMatrixForPurePolicy(mdpData.getPolicy());
        double[][] I = makeIdentityMatrix(mdpData.getNoOfStates());
        
        Matrix Ppi = new Matrix(P_pi);
        Matrix Identity = new Matrix(I);
        Matrix A = Identity.minus(Ppi.times(mdpData.getGamma()));
        
        double[][] b = new double[mdpData.getNoOfStates()][1];
        double rew[] = mdpData.getRewardForPolicy(mdpData.getPolicy());
        for(int i=0; i<mdpData.getNoOfStates(); i++)
        {
            b[i][0] = rew[i];
        }
        Matrix V = A.solve(new Matrix(b));
        double val[] = new double[mdpData.getNoOfStates()];
        //System.out.println("PolicyIterationImpl :: calculateValues() : Displaying values : ");
        for(int i=0; i<mdpData.getNoOfStates(); i++)
        {
            val[i] = V.get(i, 0);
            //System.out.println("State "+i+" : "+val[i]);
        }
        //System.out.println("");
        mdpData.setValue(val);
    }
    
    public void calculateAndSetPolicy()
    {
        double preMaxValArr[] = mdpData.getValue();
        //double diffArr[] = new double[mdpData.getNoOfStates()];
        //int preMaxActionIndexArr[] = new int[mdpData.getNoOfStates()];
        double tempActionVal;
        int actionIndex=-1, leastIndex = -1;
        ArrayList<Double> transitionList;
        
        double cbar = 0;
        
        for(int i=0; i<mdpData.getNoOfStates(); i++)
        {
            actionIndex = -1;
            tempActionVal = 0.0;
            for(int j=0; j<mdpData.getStateList().get(i).getNoOfActions(); j++)
            {
                tempActionVal =  mdpData.getStateList().get(i).getActionList().get(j).getReward();
                transitionList = mdpData.getStateList().get(i).getActionList().get(j).getTransitionFunc();
                for(int stateCounter=0; stateCounter<mdpData.getNoOfStates(); stateCounter++)
                {
                    tempActionVal = tempActionVal + mdpData.getGamma() * transitionList.get(stateCounter) * mdpData.getStateList().get(stateCounter).getValue();                    
                }
                
                cbar = tempActionVal - preMaxValArr[i];
                
                if(cbar > 0.0001)
                {
                    if(mdpData.getStateList().get(i).getActionList().get(j).getProbDist() != 1.0)
                    {
                        leastIndex = i;
                        actionIndex = j;
                        break;
                    }
                }
            }
            if(leastIndex != -1)
            {
                break;
            }
        }
        
        if(leastIndex != -1 && actionIndex != -1)
        {
            for(int k=0; k<mdpData.getStateList().get(leastIndex).getNoOfActions(); k++)
            {
                if(k==actionIndex)
                {
                    mdpData.getStateList().get(leastIndex).getActionList().get(k).setProbDist(1.0);
                }
                else
                {
                    mdpData.getStateList().get(leastIndex).getActionList().get(k).setProbDist(0.0);
                }
            }
            //mdpData.getStateList().get(maxDiffIndex).setValue(maxValArr[maxDiffIndex]);
        }
        else
        {
            System.out.println("LeastIndexPivotPolicyIteration :: calculateAndSetPolicy() : ACTION-INDEX "+actionIndex+" , STATE-INDEX "+leastIndex);
            //TODO
            //POLICY FOUND!! NO NEED FOR FURTHER ITERATIONS.
        }
    }
    
    public void run() throws Exception
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
            //System.out.println("PolicyIteration :: run() : displaying mdp values");
            //mdpData.displayPolicy();
            //mdpData.displayRewards();
            //mdpData.displayValue();
            //System.out.println("");
            pdf.printData(mdpData);
            calculateAndSetPolicy();
        }
        calculateAndSetValues();
        Gnu_Script_Writer imager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), LASTINDEXPIVOTPOLICYITERATIONFOLDER);
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
        Gnu_Script_Writer imager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), LASTINDEXPIVOTPOLICYITERATIONFOLDER);
    }
}
