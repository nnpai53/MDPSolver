/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vinit
 */
package Simplex;
import Jama.Matrix;
import input.*;
import UtilityFunctions.*;
import gnuplotutils.PrintDataFile;

public class Simplex_Algorithm {
    
    protected double improved_Cost[];
    protected double policy[];
    private MDPData mdp;
    private SeedPoint seed;
    private String path="Simplex";
    private PrintDataFile pdf=null;
    public Simplex_Algorithm(MDPData mdpdata){
        mdp = mdpdata;        
        seed =  new SeedPoint();
    }
    private void initial_Policy(){
        policy =  new double[mdp.getNoOfActions()];
        for(int i=0; i<mdp.getNoOfStates();i++){
            int action_index = mdp.absoluteActionNumber(i, 0);
            policy[action_index]=1.0;
        }
    }
    
    private Pivot_Index gredientDecentPivotRule(double[] increaseVector){
        Pivot_Index ret = new Pivot_Index();
        double max = increaseVector[0];
        ret.setIncominIndex(0);
        for(int i=0; i<increaseVector.length;i++){
            if(max<increaseVector[i]){
                max=increaseVector[i];
                ret.setIncominIndex(i);
            }
        }
        
        int state = mdp.getActionStateMapList().get(ret.getIncominIndex()).getStateIndex();
        int actionOnEachState[] = mdp.getNumOfActionsOnEachState();
        int totaPrevAction=0;
        for(int i=0;i<state;i++){
            totaPrevAction+= actionOnEachState[i];
        }
        for(int i= totaPrevAction;i<mdp.getNoOfActions();i++){
            if(policy[i]>.9999){
                ret.setOutgoingIndex(i);
                break;
            }
        }
        return ret;
    }
    
    private Pivot_Index blandPivotRule(double[] increaseVector){
        Pivot_Index ret = new Pivot_Index();
        for(int i=(0);i<increaseVector.length;i++){
            if(increaseVector[i]>0){
                ret.setIncominIndex(i);
                break;
            }
        }
        int state = mdp.getActionStateMapList().get(ret.getIncominIndex()).getStateIndex();
        int actionOnEachState[] = mdp.getNumOfActionsOnEachState();
        int totaPrevAction=0;
        for(int i=0;i<state;i++){
            totaPrevAction+= actionOnEachState[i];
        }
        for(int i= totaPrevAction;i<mdp.getNoOfActions();i++){
            if(policy[i]>.9999){
                ret.setOutgoingIndex(i);
                break;
            }
        }
        return ret;
        
    }
    
    private double[] calculateIncreaseVector(){
        double [] ret = new double[mdp.getNoOfActions()];
        Matrix A = new Matrix(mdp.getPrimalCoeffMatrix());
        Matrix C = Utility.vectorToMatrix(mdp.getRewardVector());
        Matrix C_B = Utility.vectorToMatrix(mdp.getRewardForPolicy(policy));
        Matrix A_B = new Matrix(calculate_A_AccordingToB());
        Matrix temp= C.minus(A.times(A_B.inverse().times(C_B)));

        for(int i=0;i<temp.getRowDimension();i++){
            if(temp.get(i, 0)>0.00000001 || temp.get(i, 0)<-0.00001)    //for precision upto 5 decimal places
                ret[i]=temp.get(i, 0);
            else
                ret[i]=0.0;
        }
        
        return ret;
    }
    
    public void print_Val(double [] in){
        System.out.println("=============================================INCREASE VECTOR=================================");
        for(int i=0;i<in.length;i++){
            System.out.print(in[i]+"    ");
        }
        System.out.println();
    }
    
       
    public double[][] calculate_A_AccordingToB(){
        double[][]primalCoeffMatrix = mdp.getPrimalCoeffMatrix();
        double [][] ret = new double[mdp.getNoOfStates()][mdp.getNoOfStates()];
        int count=0;
        System.out.println();
        //System.out.println(primalCoeffMatrix.length+"=="+mdp.getNoOfActions()+"   "+primalCoeffMatrix[0].length+"=="+mdp.getNoOfStates());
        for(int i=0;i<mdp.getNoOfActions();i++){
            if(policy[i]>=0.9999){
                for(int j=0;j<mdp.getNoOfStates();j++){
                    ret[count][j]= primalCoeffMatrix[i][j];
                }
                count++;

            }
        }
        return ret;
    }
    
    private double[] fluxVector(){   //this give Xb only not Xn
        double[] ret = new double[mdp.getNoOfStates()];
        Matrix A_B = new Matrix(calculate_A_AccordingToB());
        Matrix b = Utility.vectorToMatrix(Utility.unitVector(A_B.getRowDimension()));
        Matrix temp = (A_B.transpose()).inverse().times(b);
        for(int i=0;i<mdp.getNoOfStates();i++){
            ret[i]=temp.get(i, 0);
        }
        
        return ret;
    }
    
    private double[] X_fluxVector(){
        double ret[] =  new double[mdp.getNoOfActions()];
        double flux[]= this.fluxVector();
        int count=0;
        for(int i=0;i<mdp.getNoOfActions();i++){
            if(policy[i]>.999){
                ret[i]=flux[count];
                count++;
            }
            else{
                ret[i]=0;
            }
        }
        return ret;
    }
    
    
    private double[] getValueOnEachState(){
        double ret[]= new double[mdp.getNoOfStates()];
        double fluxvector[] = this.fluxVector();
        double[] rewards = mdp.getRewardForPolicy(policy);
        for(int i=0;i<mdp.getNoOfStates();i++){
            ret[i]=fluxvector[i]*rewards[i];
        }
        return ret;
    }
    
    
    private void simplexAlgorithm(int mode){
        double[] increasevecor = calculateIncreaseVector();
        this.print_Val(increasevecor);
        mdp.setValue(this.getValueOnEachState());
        fillSeed();
        mdp.PrintMDP(pdf);
        Pivot_Index pv =null;
        while(!Utility.compareVectorUptoAprecision(increasevecor,0.00001)){//here for precision
            if(mode==0)
                pv = this.gredientDecentPivotRule(increasevecor);
            else
                pv = this.blandPivotRule(increasevecor);
            
            policy[pv.getIncominIndex()]=1.0;
            policy[pv.getOutgoingIndex()]=0.0;
            mdp.setPolicy(policy);
            increasevecor=calculateIncreaseVector();
            this.print_Val(increasevecor);
            mdp.setValue(this.getValueOnEachState());
            mdp.PrintMDP(pdf);
        }        
    }

    public SeedPoint getSeed() {
        return seed;
    }
    
    private void fillSeed(){
        seed.setFlux(this.X_fluxVector());
        seed.setValue(this.getValueOnEachState());
        seed.setPolicy(mdp.getPolicy());
    }
    
    public void runSimplexAlgorithm(int mode, double[]...policies){
        if(policies.length==0){
            System.out.println("IN SIMPLEX ALGORITHM");
            initial_Policy();
            mdp.setPolicy(policy);
            mdp.displayMDP();
            if(mode==0){
                pdf = new PrintDataFile(path);                                
                simplexAlgorithm(0);
            }
            else{
                this.path = "Simplex_Bland";
                pdf = new PrintDataFile(path);
                simplexAlgorithm(1);
            }
        }
        else{
            policy=policies[0];
            mdp.setPolicy(policy);
            if(mode==0){
                pdf = new PrintDataFile(path);                
                simplexAlgorithm(0);
            }
            else{
                this.path = "Simplex_Bland";
                pdf = new PrintDataFile(path);                
                simplexAlgorithm(1);
                
            }
            
        }
    }
        
}
