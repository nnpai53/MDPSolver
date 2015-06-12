/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import MixPolicyAlgoFirst.MixStrategy_Algorithm;
import PrimalAffine.PrimalAffine_Algorithm;
import Simplex.Simplex_Algorithm;
import gnuplotutils.Gnu_Script_Writer;
import gnuplotutils.PrintDataFile;

import java.util.ArrayList;
import java.util.HashMap;
import policyiteration.PolicyIterationImpl;
import policyiteration.SinglePivotPolicyIterationImpl;
import valueiteration.ValueIterationImpl;

/**
 *
 * @author vinit
 */

public class MDPData{
    
    public MDPData(MDPData m){
        this.noOfActions = m.getNoOfActions();
        this.noOfStates = m.getNoOfStates();
        this.gamma = m.getGamma();
        this.stateList = new ArrayList<StateData>();
        ArrayList<StateData> tempList = m.getStateList();
        for(int i=0; i<m.getNoOfStates(); i++){
            this.stateList.add(new StateData(tempList.get(i)));
        }
        init();
        //TODO J-MATRIX
        //TODO TransitionMatrix
    }
    
    public MDPData(){
        stateList = new ArrayList<StateData>();        
    }
    
    public MDPData(int numOfStates, int numOfActions, double[] rewards, double[][] transitionArr, int[] numOfActionsOnEachState, double gamma){
        this.noOfActions = numOfActions;
        this.noOfStates = numOfStates;
        this.gamma = gamma;
        
        HashMap<Integer, ArrayList<Double>> tempTransitionMap = new HashMap<Integer, ArrayList<Double>>();
        ArrayList<Double> temp;
        for(int i=0; i<noOfActions; i++){
            temp = new ArrayList<Double>();
            for(int j=0; j<noOfStates; j++){
                temp.add(transitionArr[i][j]);                
            }
            tempTransitionMap.put(i, temp);
        }
        
        stateList = new ArrayList<StateData>();
        StateData sd;
        
        int actionCounter = 0;
        for(int i=0;i<getNoOfStates();i++){
            sd = new StateData();
            sd.setNoOfActions(numOfActionsOnEachState[i]);
            ActionData ad = null;
            ArrayList<ActionData> actionList = new ArrayList<ActionData>();
            
            for(int j=0;j<numOfActionsOnEachState[i];j++){
                ad = new ActionData(numOfActionsOnEachState[i]);
                ad.setReward(rewards[actionCounter]);
                ad.setTransitionFunc(tempTransitionMap.get(actionCounter));
                actionList.add(j,ad);
                actionCounter++;
            }
            sd.setActionList(actionList);
            stateList.add(i,sd);
        }
        
        state_action_mapping = new int[getNoOfStates()][getNoOfActions()];
        int upto=0;
        for(int index=0;index<getNoOfStates();index++){
            int action = numOfActionsOnEachState[index];
            for(int jndex=0;jndex<getNoOfActions();jndex++){
                if(jndex>=upto && jndex<(upto+action)){
                    state_action_mapping[index][jndex]=1;
                }
            }
            upto=upto+action;
        }
        transitionMatrix = transitionArr;
        
        init();
    }
    
    public void init(){
        makeCoeffMatrices();
        makeStateActionMapping(); //YAJI        
        populateActionStateMapList();
        makeTransitionMatrix(); //YAJI        
    }
    
      public void makeTransitionMatrix(){
        if(transitionMatrix != null){
            return;
        }
        transitionMatrix = new double[getNoOfActions()][getNoOfStates()];
        int actionCounter = 0, numOfActionsInState;
        ArrayList<Double> transMat = new ArrayList<Double>();
        double[] tranFunc;
        for(int i=0; i<noOfStates; i++){
            numOfActionsInState = getStateList().get(i).getNoOfActions();
            for(int j=0; j<numOfActionsInState; j++){
                tranFunc = new double[getNoOfStates()];
                transMat = getStateList().get(i).getActionList().get(j).getTransitionFunc();
                for(int k=0; k<getNoOfStates(); k++){
                    tranFunc[k] = transMat.get(k);
                }
                transitionMatrix[actionCounter] = tranFunc;
                actionCounter++;
            }
        }
    }
    
    //YAJI
    public void makeStateActionMapping(){
        if(state_action_mapping != null){
            return;
        }
        state_action_mapping = new int[getNoOfStates()][getNoOfActions()];
        
        int upto = 0, numOfActionsInState;
        
        for(int i=0; i<noOfStates; i++){
            numOfActionsInState = getStateList().get(i).getNoOfActions();
            for(int j=0; j<numOfActionsInState; j++){
                state_action_mapping[i][upto] = 1;
                upto++;
            }
        }
    }
  
   
    public void populateActionStateMapList(){
        actionStateMapList = new ArrayList<ActionStateMap>();
        for(int i=0; i<noOfActions; i++){
            int state=0;
            int action=0;
            ActionStateMap actionStateMap = new ActionStateMap();
            actionStateMap.setMainActionIndex(i);
            
            for(int j=0;j<state_action_mapping.length;j++){
                if(state_action_mapping[j][i]==1){
                    state=j;
                    break;
                }
            }
            
            for(int j=0;j<=i;j++){
                if(state_action_mapping[state][j]==1){
                    action++;
                }
            }
            
            actionStateMap.setStateIndex(state);
            actionStateMap.setActionIndex(action-1);
            actionStateMapList.add(actionStateMap);
                       
        }
    }
    
    public void makeCoeffMatrices(){
        PrimalClass primal = new PrimalClass(this);
        primal.setPrimalCoefficientsDimensions();
        primal.fillPrimalCoefficients();
        primalCoeffMatrix = primal.getPrimalCoefficients();
        
        dualCoeffMatrix = new double[noOfStates][noOfActions];
        double tempArray[][] = primalCoeffMatrix;
        for(int i=0;i<getNoOfStates();i++){
            for(int j=0;j<getNoOfActions();j++){
                dualCoeffMatrix[i][j]= tempArray[j][i];
            }
        }
    }
    
    protected int noOfStates;
    protected int noOfActions;
    protected double gamma = 0.5;
    protected ArrayList<StateData> stateList;
    //J-Matrix
    protected int state_action_mapping[][];
    
    protected double[][] primalCoeffMatrix;
    protected double[][] dualCoeffMatrix;
    protected ArrayList<ActionStateMap> actionStateMapList;
    protected double transitionMatrix[][];
    
    
    public void setPolicy(double[] policyArr){
        int actionCounter = 0;
        for(int i=0; i<noOfStates; i++){
            for(int j=0; j<getStateList().get(i).getNoOfActions(); j++){
                this.getStateList().get(i).getActionList().get(j).setProbDist(policyArr[actionCounter]);
                actionCounter++;
            }
        }
    }
    
    public double[] getPolicy(){
        double[] ret = new double[noOfActions];
        int actionCounter=0;
        for(int i=0; i<noOfStates; i++){
            for(int j=0; j<getStateList().get(i).getNoOfActions(); j++){
                ret[actionCounter] = this.getStateList().get(i).getActionList().get(j).getProbDist();
                actionCounter++;
            }
        }
        return ret;
    }
    
    public void clearPolicy(){
        for(int i=0;i<this.noOfStates;i++){
            for(int j=0;j<this.getStateList().get(i).getNoOfActions();j++){
                this.getStateList().get(i).getActionList().get(j).setProbDist(0.0);
            }
        }
    }
    
    
    public int[] getNumOfActionsOnEachState(){
        int ret[] = new int[noOfStates];
        
        for(int i=0; i<noOfStates; i++){
            ret[i] = stateList.get(i).getNoOfActions();
        }
        
        return ret;
    }
    
    
    public double[] getRewardVector(){
        double ret[] = new double[noOfActions];
        
        int actionCounter = 0;
        for(int i=0; i<noOfStates; i++){
            for(int j=0; j<getStateList().get(i).getNoOfActions(); j++){
                ret[actionCounter] = stateList.get(i).getActionList().get(j).getReward();
                actionCounter++;
            }
        }

        return ret;
    }
    
    //Returns a reward vector corresponding to Pure Policy. Takes Input as mixed policy.
    //Hence works only for Pure Policy.
    //policy must be of the form {1.0, 0.0, 1.0, 0.0}
    public double[] getRewardForPolicy(double[] policy){
        double ret[] = new double[noOfStates];
        int stateIndex, actionIndex;
        for(int i=0; i<policy.length; i++){
            stateIndex = actionStateMapList.get(i).getStateIndex();
            actionIndex = actionStateMapList.get(i).getActionIndex();
            if(policy[i] >= 0.99999){
                ret[stateIndex] = getStateList().get(stateIndex).getActionList().get(actionIndex).getReward();                                
            }
            else if(policy[i] >= 0.00001){
                System.out.println("MDPData :: getRewardForPolicy() : WRONG INPUT FOR THIS METHOD!! NO MIXED POLICY ALLOWED");
                return null;
            }
        }
        return ret;
    }
    
    //Returns a transition matrix corresponding to Pure Policy. Takes Input as mixed policy.
    //Hence works only for Pure Policy.
    //policy must be of the form {1.0, 0.0, 1.0, 0.0}
    public double[][] getTransitionMatrixForPurePolicy(double purePolicy[]){
        double ret[][] = new double[noOfStates][noOfStates];
        int stateCounter = 0;
        
        for(int i=0; i<purePolicy.length; i++){
            if(purePolicy[i] >= 0.99999){
                
                ret[stateCounter] = transitionMatrix[i];
                stateCounter++;
            }
        }
        
        return ret;
    }
    
    
    public void setValue(double valArr[])
    {
        if(valArr.length != noOfStates)
        {
            System.out.println("MDPData :: setValue() : Tumhaara Value Vector ka length sahi nahi hai bhau.. Theek se daal.. ");
            return;
        }
        for(int i=0; i<noOfStates; i++)
        {
            getStateList().get(i).setValue(valArr[i]);
        }
    }
    
    public double[] getValue()
    {
        double ret[] = new double[noOfStates];
        for(int i=0; i<noOfStates; i++)
        {
            ret[i] = getStateList().get(i).getValue();
        }
        return ret;
    }
 
    
    //summation of values of all states.
    public double getSumOfAllValues()
    {
        double ret = 0.0;
        for(int i=0; i<noOfStates; i++)
        {
            ret = ret + getStateList().get(i).getValue();            
        }
        return ret;
    }
    
    
    public void calculateValueContributionsOfActions()
    {
        for(int i=0; i<noOfStates; i++)
        {
            StateData state = stateList.get(i);
            int numOfActionsInState = state.getNoOfActions();
            ArrayList<ActionData> actionList = state.getActionList();
            for(int j=0; j<numOfActionsInState; j++)
            {
                ActionData action = actionList.get(j);
                action.calculateValueContribution(stateList, gamma);
                actionList.set(j, action);
            }
            state.setActionList(actionList);
            stateList.set(i, state);
        }
    }
    
    public void setValueContributionsOfActions(MDPData m)
    {
        for(int i=0; i<noOfStates; i++)
        {
            StateData state = stateList.get(i);
            StateData mState = m.getStateList().get(i);
            int numOfActionsInState = state.getNoOfActions();
            ArrayList<ActionData> actionList = state.getActionList();
            ArrayList<ActionData> mActionList = mState.getActionList();
            for(int j=0; j<numOfActionsInState; j++)
            {
                ActionData action = actionList.get(j);
                action.setValueContri(mActionList.get(j).getValueContri());
                actionList.set(j, action);
            }
            state.setActionList(actionList);
            state.setValue(mState.getValue());
            stateList.set(i, state);
        }
    }
    
    public void calculateNewPolicyAfterTransformation()
    {
        for(int i=0; i<noOfStates; i++)
        {
            StateData state = stateList.get(i);
            int noOfActionsInState = state.getNoOfActions();
            ArrayList<ActionData> actionList = state.getActionList();
            for(int j=0; j<noOfActionsInState; j++)
            {
                ActionData action = actionList.get(j);
                action.calculateNewProbDistAfterTransformation(stateList, gamma);
                actionList.set(j, action);
            }
            state.setActionList(actionList);
            stateList.set(i, state);
        }
    }


    public void normalizeProbabilities()
    {
        StateData state;
        for(int i=0; i<noOfStates; i++)
        {
            state = stateList.get(i);
            state.normalizeProb();
            stateList.set(i, state);
        }
    }
    
    
    public void displayValue()
    {
        for(int i=0; i<stateList.size(); i++)
        {
            System.out.println("Value at State "+i+" : "+stateList.get(i).getValue());
        }
    }
    public void displayPolicy()
    {
        for(int i=0; i<stateList.size(); i++)
        {
            ArrayList<ActionData> actionList = stateList.get(i).getActionList();
            System.out.print("Policy at State "+i+" : ");
            for(int j=0; j<actionList.size(); j++)
            {
                System.out.print(""+actionList.get(j).getProbDist()+" ; ");
            }
            System.out.println();
        }
    }
    
    public void displayTransitFunc()
    {
        for(int i=0; i<noOfStates; i++)
        {
            ArrayList<ActionData> actionList = stateList.get(i).getActionList();
            System.out.println("Transition Functions at State "+i+" : ");
            for(int j=0; j<actionList.size(); j++)
            {
                System.out.print("Action "+j +" : ");
                for(int k=0; k<noOfStates; k++)
                {
                    System.out.print(""+actionList.get(j).getTransitionFunc().get(k)+" ; ");                    
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    
    public void displayRewards()
    {
        for(int i=0; i<stateList.size(); i++)
        {
            ArrayList<ActionData> actionList = stateList.get(i).getActionList();
            System.out.print("Rewards of actions at State "+i+" : ");
            for(int j=0; j<actionList.size(); j++)
            {
                System.out.print(""+actionList.get(j).getReward()+" ; ");
            }
            System.out.println();
        }        
    }
 
    public ArrayList<Double> getRewards(){
        ArrayList<Double> ret= new ArrayList<Double>();
        for(int i=0; i<stateList.size(); i++)
        {
            ArrayList<ActionData> actionList = stateList.get(i).getActionList();
            for(int j=0; j<actionList.size(); j++)
            {
                ret.add(actionList.get(j).getReward());
            }
        }
        return ret;
    }
    
    public double[][] getTransitFunc(){
        double ret[][] = new double[this.noOfActions][this.noOfStates];
        int count=0;
        for(int i=0; i<noOfStates; i++){
            ArrayList<ActionData> actionList = stateList.get(i).getActionList();
            //System.out.println("Transition Functions at State "+i+" : ");
            for(int j=0; j<actionList.size(); j++)
            {
                //System.out.print("Action "+j +" : ");
                for(int k=0; k<noOfStates; k++)
                {
                    ret[count][k]=actionList.get(j).getTransitionFunc().get(k);
                    count++;
                    //System.out.print(""+actionList.get(j).getTransitionFunc().get(k)+" ; ");                    
                }
                //System.out.println();
            }
            //System.out.println();
        }
        return ret;
    }
    
    public void displayMDP()
    {
        /*
        for(int i=0;i<getNoOfStates();i++){
            System.out.println("In state S"+i+" "+"total no of action are "+getStateList().get(i).getNoOfActions());
          for(int j=0;j<getStateList().get(i).getNoOfActions();j++){
              ActionData temp = (ActionData)getStateList().get(i).getActionList().get(j);
              System.out.println(temp.getProbDist() + " "+ temp.getReward());
              for(int k=0;k<getNoOfStates();k++){
                  System.out.print(temp.getTransitionFunc().get(k)+"  ");
              }
              System.out.println();
          }
        }
        */
        displayPolicy();
        displayRewards();
        displayValue();
        displayTransitFunc();
    }
    
    public void appendStateList(StateData state)
    {
        this.stateList.add(state);
    }
    
    
    public void PrintMDP(PrintDataFile printer)
    {
        //PrintDataFile printer = new PrintDataFile(path);
        printer.printData(this);
        //Gnu_Script_Writer gnuImager = new Gnu_Script_Writer(getNoOfStates(), getNumOfActionsOnEachState(), path);
    }
    
    public int getNoOfStates()
    {
        return noOfStates;
    }

    public void setNoOfStates(int noOfStates)
    {
        this.noOfStates = noOfStates;
    }

    public double getGamma()
    {
        return gamma;
    }

    public void setGamma(double lambda)
    {
        this.gamma = lambda;
    }

    public int getNoOfActions()
    {
        return noOfActions;
    }

    public void setNoOfActions(int noOfActions)
    {
        this.noOfActions = noOfActions;
    }

    public ArrayList<StateData> getStateList()
    {
        return stateList;
    }

    public void setStateList(ArrayList<StateData> stateList)
    {
        this.stateList = stateList;
    }
    
    public void setaction_State_Mapping(int in[][]){
        this.state_action_mapping=in;
    }
    
    public int[][] getState_action_mapping(){
        return state_action_mapping;
    }

    public void setState_action_mapping(int[][] stateActionMapping){
        state_action_mapping = stateActionMapping;
    }

    public double[][] getPrimalCoeffMatrix(){
        return primalCoeffMatrix;
    }

    public void setPrimalCoeffMatrix(double[][] primalCoeffMatrix){
        this.primalCoeffMatrix = primalCoeffMatrix;
    }

    public double[][] getDualCoeffMatrix(){
        return dualCoeffMatrix;
    }

    public void setDualCoeffMatrix(double[][] dualCoeffMatrix){
        this.dualCoeffMatrix = dualCoeffMatrix;
    }

    public ArrayList<ActionStateMap> getActionStateMapList(){
        return actionStateMapList;
    }

    public void setActionStateMapList(ArrayList<ActionStateMap> actionStateMapList){
        this.actionStateMapList = actionStateMapList;
    }

    public double[][] getTransitionMatrix(){
        return transitionMatrix;
    }

    public void setTransitionMatrix(double[][] transitionMatrix){
        this.transitionMatrix = transitionMatrix;
    }

    public void print_action_State_Mapping(){
        for(int i=0;i<state_action_mapping.length;i++){
            for(int j=0;j<state_action_mapping[0].length;j++){
                System.out.println(state_action_mapping[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public int[] get_Action_State_Mapping(int index){
        int ret[]= new int[2];
        int state=0;
        int action=0;
        for(int i=0;i<state_action_mapping.length;i++){
            if(state_action_mapping[i][index]==1){
                state=i;
                break;
            }
        }
        
        for(int i=0;i<=index;i++){
            if(state_action_mapping[state][i]==1){
                action++;
            }
        }
        ret[0]=state;
        ret[1]=action-1;
        return ret;
    }
    public void displayPrimalCoefficients(){
        System.out.println("The primal coefficient matrix is as follows: ");
        for(int i=0;i<getNoOfActions();i++){
            for(int j=0;j<getNoOfStates();j++){
                System.out.print(primalCoeffMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public int absoluteActionNumber(int state, int actionOnState){
        int ret=0;
        int actionOnEachState[]=this.getNumOfActionsOnEachState();
        
        for(int i=0;i<state;i++){
            ret= ret +actionOnEachState[i];
        }
        ret+=actionOnState;
        return ret;
    }

    
    /*public static void main(String args[])
    {
        RandomizeMDP randomizer = new RandomizeMDP(7, 4,20);
        MDPData mdpData = new MDPData(randomizer.getNoOfStates(), randomizer.getNoOfActions(), randomizer.getRewards_As_Array(), randomizer.getTransition_Table_As_Array(), randomizer.getAction_Per_State_As_Array(), 0.5);
        mdpData.displayMDP();
        
        //mdpData.displayPrimalCoefficients();
        Simplex_Algorithm s = new Simplex_Algorithm(new MDPData(mdpData));
        s.runSimplexAlgorithm(0);
        
        String Path = "Simplex";
        
        Gnu_Script_Writer gnuImager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), Path);
        
        new PrimalAffine_Algorithm(new MDPData(mdpData)).runPrimalAffine();
        
        Path = "Primal_Affine";
        
        new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), Path);

        Path = "MixStrategy";
        
        new MixStrategy_Algorithm(new MDPData(mdpData)).runMixStrategy();
        
        new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), Path);
        
        
        new ValueIterationImpl(new MDPData(mdpData)).run();
        
        //mdpData.init();
 
        new PolicyIterationImpl(new MDPData(mdpData)).run();
        
        new SinglePivotPolicyIterationImpl(new MDPData(mdpData)).run();
        }
    */
}
