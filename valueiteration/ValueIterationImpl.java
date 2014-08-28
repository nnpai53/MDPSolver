package valueiteration;

import gnuplotutils.Gnu_Script_Writer;
import gnuplotutils.PrintDataFile;
import input.MDPData;
import input.RandomizeMDP;
import input.StateData;

import java.util.ArrayList;

public class ValueIterationImpl{
    private MDPData mdpData;
    private ArrayList<Double> prevValueList;
    private PrintDataFile pdf;
    private double epsilon = 0.0001;
    public static String VALUEITERATIONFOLDER = "ValueIteration";
    
    private void init(){
        //graphPlotter = new ValueGraphPlotter("ValueIteration", "ValueIteration", "ValueIteration", input.getNoOfStates());
        //mdpData = new MDPData();
        prevValueList = new ArrayList<Double>();
        pdf = new PrintDataFile(VALUEITERATIONFOLDER);
        pdf.delete_Files(VALUEITERATIONFOLDER);
    }
    
    public ValueIterationImpl(){
        init();
    }
    
    public ValueIterationImpl(MDPData input){
        this.mdpData = input;
        init();
    }
    
    public ValueIterationImpl(MDPData input, double epsilon){
        this.mdpData = input;
        this.epsilon = epsilon;
        init();
    }
    
    public ValueIterationImpl(MDPData input, double[] valueArr){
        this.mdpData = input;
        setInitialValue(valueArr);
        init();
    }
    
    public ValueIterationImpl(MDPData input, double epsilon, double[] valueArr){
        this.mdpData = input;
        this.epsilon = epsilon;
        setInitialValue(valueArr);
        init();
    }
    
    // FOR SEED POLICY
    public void initialSeedPolicy(double[] policy, double[] valueArr){
        if(policy.length != mdpData.getNoOfActions()){
            System.out.println("ValueIterationImpl :: initialSeedPolicy() : Seed Policy length is faulty - not equal to the number of actions in mdp");
        }
        if(valueArr.length != mdpData.getNoOfStates()){
            System.out.println("ValueIterationImpl :: initialSeedPolicy() : ValueArr length is not equal to the length of number of states");
        }
        mdpData.setPolicy(policy);
        mdpData.setValue(valueArr);
    }
    
    public void setInitialValue(double[] valueArr){
        if(valueArr.length != mdpData.getNoOfStates()){
            System.out.println("ValueIterationImpl :: initialSeedPolicy() : ValueArr length is not equal to the length of number of states");
        }
        mdpData.setValue(valueArr);
    }
    
    
    public boolean checkInput(){
        //System.out.println("ValueIterationImpl : checkInput :: checkInput() Starting");
        if(mdpData == null){
            System.out.println("ValueIterationImpl : checkInput() :: MDPData is NULL");
        }
        return true;
    }
    
    public boolean checkTermination(){
        //System.out.println("ValueIterationImpl : checkInput :: checkTermination() Starting");
        double limit = epsilon * (1 - mdpData.getGamma())/mdpData.getGamma();
        for(int i=0; i<mdpData.getNoOfStates(); i++){
            StateData tempState = mdpData.getStateList().get(i);
            double diff = tempState.getValue() - prevValueList.get(i);
            if(diff < 0){
                diff = -1 * diff;
            }
            if(diff >= limit){
                //System.out.println("ValueIterationImpl : checkInput :: checkTermination() Ending with FALSE");
                return false;
            }
        }
        //System.out.println("ValueIterationImpl : checkInput :: checkTermination() Ending with TRUE");
        return true;
    }
    
    private ArrayList<Double> fillValues(){
        if(mdpData == null){
            return null;
        }
        ArrayList<Double> values = new ArrayList<Double>();
        for(int i=0; i<mdpData.getNoOfStates(); i++){
            values.add(mdpData.getStateList().get(i).getValue());                        
        }
        return values;
    }
    
    public void calculateNewValues(){
        double val[] = new double[mdpData.getNoOfStates()];
        
        if(prevValueList.size() != mdpData.getNoOfStates()){
            System.out.println("ValueIterationImpl : calculateNewValues :: prevVal array size != no of states");
        }
        double tempActionVal, maxStateVal;
        int stateCounter, maxActionIndex;
        ArrayList<Double> transitionList;
        for(int i=0; i<mdpData.getNoOfStates(); i++){
            maxStateVal = 0.0;
            maxActionIndex = -1;
            tempActionVal = 0.0;
            for(int j=0; j<mdpData.getStateList().get(i).getNoOfActions(); j++){
                tempActionVal =  mdpData.getStateList().get(i).getActionList().get(j).getReward();
                transitionList = mdpData.getStateList().get(i).getActionList().get(j).getTransitionFunc();
                for(stateCounter=0; stateCounter<mdpData.getNoOfStates(); stateCounter++){
                    tempActionVal = tempActionVal + mdpData.getGamma() * (transitionList.get(stateCounter) * mdpData.getStateList().get(stateCounter).getValue());                    
                }
                if(tempActionVal > maxStateVal){
                    maxStateVal = tempActionVal;
                    maxActionIndex = j;
                }
            }
            val[i] = maxStateVal;
            for(int k=0; k<mdpData.getStateList().get(i).getNoOfActions(); k++){
                if(k==maxActionIndex){
                    mdpData.getStateList().get(i).getActionList().get(k).setProbDist(1.0);
                }
                else{
                    mdpData.getStateList().get(i).getActionList().get(k).setProbDist(0.0);
                }
            }
        }
        mdpData.setValue(val);
    }    
    
    public void run()throws Exception{
        if(!checkInput()){
            return;
        }
        boolean terminate = false;
        //double[] prevValue = mdpData.getValue();
        int iterationNum = 0;
        while(!terminate){
            prevValueList = fillValues();
            iterationNum++;
            calculateNewValues();
            pdf.printData(mdpData);
            //epsi = epsi/(2.0*lambda)*(1.0-lambda);
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-");
            mdpData.displayMDP();
            System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-");
            //putValueToGraph(iterationNum);
            terminate = checkTermination();
        }
        //graphPlotter.generateGraph();
        Gnu_Script_Writer imager = new Gnu_Script_Writer(mdpData.getNoOfStates(), mdpData.getNumOfActionsOnEachState(), VALUEITERATIONFOLDER);
    }
    
   /* public static void main(String args[])
    {
        RandomizeMDP randomizer = new RandomizeMDP(7, 4, 20);
        MDPData mdpData = new MDPData(randomizer.getNoOfStates(), randomizer.getNoOfActions(), randomizer.getRewards_As_Array(), randomizer.getTransition_Table_As_Array(), randomizer.getAction_Per_State_As_Array(), 0.5);
        System.out.println("===========Displaying MDP============");
        mdpData.displayMDP();
        System.out.println("===========End MDP============");
        ValueIterationImpl val = new ValueIterationImpl(mdpData);
        val.run();
        System.out.println("===========Displaying FINAL MDP============");
        mdpData.displayMDP();
        System.out.println("===========End FINAL MDP============");
        
    }*/
    
    public static void makeMdp(){
        
    }
}

/*
ArrayList<Double> ret = new ArrayList<Double>();
ArrayList<Double> tempToStoreValOfAllActionsOfAState;// = new ArrayList<Double>();
ArrayList<ArrayList<Double>> transitionsOfThisStateList;
ArrayList<Integer> actionIndicesList;
double[] rewardList = mdpData.getRewardVector();
for(int stateNo=0; stateNo<input.getNoOfStates(); stateNo++)
{
    tempToStoreValOfAllActionsOfAState = new ArrayList<Double>();
    double val = 0;
    transitionsOfThisStateList = input.getTransition().get(stateNo);
    actionIndicesList = input.getActionsOfEachState().get(stateNo);
    for(int actionNoOfThisState=0; actionNoOfThisState<transitionsOfThisStateList.size(); actionNoOfThisState++)
    {
        ArrayList<Double> transitionVector = transitionsOfThisStateList.get(actionNoOfThisState);
        val = rewardList.get(actionIndicesList.get(actionNoOfThisState)) + input.getLambda() * cumulativeTransitionSum(prevVal, transitionVector);
        tempToStoreValOfAllActionsOfAState.add(val);
    }
    int maxIndex = returnMaxElementIndex(tempToStoreValOfAllActionsOfAState);
    ret.add(tempToStoreValOfAllActionsOfAState.get(maxIndex));
    ArrayList<Integer> policy = input.getPolicy();
    policy.remove(stateNo);
    policy.add(stateNo, actionIndicesList.get(maxIndex));
    input.setPolicy(policy);
}
return ret;
*/
