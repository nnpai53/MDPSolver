/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrimalAffine;

import Jama.Matrix;
import UtilityFunctions.PseudoInverse;
import UtilityFunctions.Utility;
import gnuplotutils.PrintDataFile;
import input.MDPData;
import input.SeedPoint;
import input.StateData;
import java.util.ArrayList;

/**
 *
 * @author vinit
 */
public class PrimalAffine_Algorithm {
    
    private MDPData mdp;
    
    private double[] fluxvector;
    
    private double prevoiusvalue;
   
    private PrintDataFile pdf = new PrintDataFile("Primal_Affine");

    private SeedPoint seed;
    
    private ArrayList<Integer> optimalpoint;
    
    public PrimalAffine_Algorithm(MDPData mdpdata)
    {
        mdp = mdpdata;
        seed =  new SeedPoint();
        optimalpoint =  new ArrayList<Integer>();
    }
    
    private boolean isoptimal(int index)
    {
        boolean ret;
        
        int state = mdp.getActionStateMapList().get(index).getStateIndex();
        
        StateData sd = mdp.getStateList().get(state);
        
        for(int i=0;i<sd.getNoOfActions();i++)
        {
            if(sd.getActionList().get(i).getProbDist()>.95)
            {
                return false;
            }
        }
        
        return true;
    }
    
    private Matrix D_KMatrixGenerator()
    {
        double[][] diagonal_X = new double[fluxvector.length][fluxvector.length];
        
        for(int i=0;i<fluxvector.length;i++)
        {
            for(int j=0;j<fluxvector.length;j++)
            {
                if(j==i)
                {
                    diagonal_X[i][j]=fluxvector[i];
                }
                else
                    diagonal_X[i][j]=0.0;
            }
        }

        return new Matrix(diagonal_X);
    }
    
    private Matrix calculateDirection(Matrix D_k){
        
        
        Matrix  dualCoefficientMatrix = new Matrix(mdp.getDualCoeffMatrix());
        
        Matrix rewars = Utility.vectorToMatrix(mdp.getRewardVector()).times(-1.0);
        
        Matrix  transformedDualCoefficientMatrix = dualCoefficientMatrix.times(D_k);
        
        Matrix transformedRewars = D_k.times(rewars);
        
        Matrix tempdata1 = transformedDualCoefficientMatrix.transpose();
        
        Matrix tempdata2 = transformedDualCoefficientMatrix.times(tempdata1);
        
        tempdata2 = tempdata2.solve(transformedDualCoefficientMatrix);
        
        tempdata2 = tempdata1.times(tempdata2);
        
        tempdata2 = Utility.identityMatrix(mdp.getNoOfActions()).minus(tempdata2);
        
        Matrix direction = tempdata2.times(transformedRewars).times(-1.0);
        
        return direction;
        
    }
    
    private double calculateAlpha(Matrix direction)
    {
        double d[][]=direction.getArray();
        double gamma=.90;   //then can be set less than 1 but greater than 0.
        double alpha_max=0.0;
        for(int i=0;i<d.length;i++){
            if(d[i][0]<0){
                if(-1*d[i][0]>alpha_max)
                    alpha_max= -1*d[i][0];
            }
        }
        if(alpha_max==0.0)
        {
            alpha_max=1;
        }
        return gamma/alpha_max;
    }

    private double[] newImprovedPoint(Matrix direction, double alpha, Matrix D_K){
        
        double ret[] = new double[mdp.getNoOfActions()];
        
        System.out.println("============================direction matrix===============================");
        direction.print(3, 16);
        
        Matrix newPointInNewSpace = Utility.vectorToMatrix(Utility.unitVector(mdp.getNoOfActions())).plus(direction.times(alpha));
        
/*        for(int i=0;i<mdp.getNoOfActions();i++){
            
            if(this.optimalpoint.contains(i)){
                newPointInNewSpace.set(i, 0,newPointInNewSpace.get(i, 0)-direction.get(i, 0) );
            }
            else{
                if(this.isoptimal(i)){
                    optimalpoint.add(i);
                    newPointInNewSpace.set(i, 0,newPointInNewSpace.get(i, 0)-direction.get(i, 0) );
                }
            }
            
        }
  */      
        Matrix newPointInOriginalSpace = D_K.times(newPointInNewSpace);
        
        for(int i=0;i<newPointInOriginalSpace.getRowDimension();i++)
        {
            ret[i]=newPointInOriginalSpace.get(i, 0);
        }
        
        return ret;
    }
    
    private double[] calculateValueEachState(double actioncontribution[])
    {
        double ret[] =  new double[mdp.getNoOfStates()];
        
        int actiononeachstate[] = mdp.getNumOfActionsOnEachState();
        
        int count=0;
        
        int state=0;
        
        for(int i=0;i<actioncontribution.length;i++)
        {
            if(count<actiononeachstate[state])
            {
                count++;
                ret[state]+=actioncontribution[i];
            }
            else
            {
                count=1;
                state++;
                ret[state]+=actioncontribution[i];
                
            }
        }
        return ret;
    }
    
    private double[] calcualtePolicy(double actioncontribution[], double[] valueoneachstate)
    {
        double ret[] = new double[mdp.getNoOfActions()];
        
        int actionOnEachState[] = mdp.getNumOfActionsOnEachState();
        
        int state = 0;
        
        int count=0;
        
        for(int i=0;i<mdp.getNoOfActions();i++)
        {
            
            if(count<actionOnEachState[state])
            {
                ret[i] = actioncontribution[i]/valueoneachstate[state];
                count++;
            }
            else
            {
                count=1;
                state++;
                ret[i] = actioncontribution[i]/valueoneachstate[state];                
            }
        }
        return ret;

    }
    
    
    
    
    
    private double[] calculateContributionOfEachAction()
    {
        double ret[] = new double[mdp.getNoOfActions()];
        
        double rewards[] =  mdp.getRewardVector();
        
        int actionOnEachState[] = mdp.getNumOfActionsOnEachState();
        
        for(int i=0;i<mdp.getNoOfActions();i++)
        {
            ret[i]= rewards[i]*fluxvector[i];
        }
        
        return ret;
    }
    
    public void setMDPData(){
    
        double contibutionofeachAction[] = this.calculateContributionOfEachAction();
        
        double valueoneachState[]= this.calculateValueEachState(contibutionofeachAction);
        
        double policy[] = this.calcualtePolicy(contibutionofeachAction, valueoneachState);
        
        mdp.setPolicy(policy);
        mdp.setValue(valueoneachState);
        
    }
    
    private void primalAffineAlgorithm(double epsilon)
    {
        setMDPData();
        fillSeed();
        mdp.PrintMDP(pdf);
        prevoiusvalue =0.0;
        
        double presentvalue = mdp.getSumOfAllValues();
        
        int count=0;
        
        Matrix direction=null;
        
        PseudoInverse pi = new PseudoInverse();
        
        while((count==0)|| (-1*((prevoiusvalue - presentvalue))>epsilon)&&(pi.euclidienDistance(direction)/mdp.getNoOfActions()>0.1)) 
        {
            count =1;
            
            Matrix D_K = this.D_KMatrixGenerator();
        
            direction = this.calculateDirection(D_K);
        
            double steplength = this.calculateAlpha(direction);
            
            fluxvector = this.newImprovedPoint(direction, steplength, D_K);
            
            setMDPData();
            
            prevoiusvalue = presentvalue;
            presentvalue = mdp.getSumOfAllValues();
            mdp.PrintMDP(pdf);

        }
        
    }

    public SeedPoint getSeed() {
        return seed;
    }
    
    public double[] getFlux(){
        double ret[]= new double[fluxvector.length];
        for(int i=0;i<fluxvector.length;i++){
            ret[i]=fluxvector[i];
        }
        return ret;
    }
    
    public void fillSeed()
    {
        seed.setFlux(getFlux());
        seed.setPolicy(mdp.getPolicy());
        seed.setValue(mdp.getValue());
    }
    
    public void runPrimalAffine(double[]...seed)
    {
        if(seed.length == 0)
        {
            Matrix primal_cofficient =  new Matrix(mdp.getDualCoeffMatrix());   //
            
            Matrix b = Utility.vectorToMatrix(Utility.unitVector(mdp.getNoOfStates()));
            
            Matrix x_Initial = PseudoInverse.pinv(primal_cofficient).times(b);
            
            fluxvector = new double[mdp.getNoOfActions()];
            
            for(int i=0;i<x_Initial.getRowDimension();i++){
                fluxvector[i]=x_Initial.get(i, 0);
            }
        }
        
        else
        {
            fluxvector = seed[0];
        }
        
        primalAffineAlgorithm(0.1);  //primalAffineAlgorithm(double epsilon) set epsilon for specified precision
    }
    
}
