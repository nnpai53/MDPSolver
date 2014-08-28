/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

/**
 *
 * @author vinit
 */

public class PrimalClass{
    MDPData mdpData;
    double primalCoefficients[][];
    public PrimalClass(){
        mdpData = new MDPData();
//        primalCoefficients = new double[mdpData.getNoOfActions()][mdpData.getNoOfStates()];
    }
    
    public PrimalClass(MDPData m)
    {
        mdpData = m;
    }
    
    public void setPrimalCoefficientsDimensions(){
    	primalCoefficients = new double[mdpData.getNoOfActions()][mdpData.getNoOfStates()];
    }
    public void fillPrimalCoefficients(){
    	setPrimalCoefficientsDimensions();
    	int currentState = 0;
    	int currentAction = 0;
    	int count = mdpData.getStateList().get(currentState).getNoOfActions();
    	for(int i=0;i<mdpData.getNoOfActions();i++){
                if(count==0){
                    currentState++;
            		count = mdpData.getStateList().get(currentState).getNoOfActions();
            		currentAction = 0;
                }

                for(int j=0;j<mdpData.getNoOfStates();j++){
              //      System.out.println("The value of currentAction is : " + currentAction);
              //      System.out.println("The value of currentstate is : " + currentState);
              //      System.out.println("The value of count is : " + count);

//                    System.out.println("The value of i is : " + i);
  //                  System.out.println("The value of j is : " +j);
                	if(j==currentState){
                		primalCoefficients[i][j]= 1 - mdpData.getGamma()*(mdpData.getStateList().get(currentState).getActionList().get(currentAction).getTransitionFunc().get(j));
                	}
                	else
                		primalCoefficients[i][j]= (-1)* mdpData.getGamma()*(mdpData.getStateList().get(currentState).getActionList().get(currentAction).getTransitionFunc().get(j));
                }
            	currentAction++;
                count--;
    	}
    }
    public void displayPrimalCoefficients(){
    	System.out.println("The primal coefficient matrix is as follows: ");
    	for(int i=0;i<mdpData.getNoOfActions();i++){
    		for(int j=0;j<mdpData.getNoOfStates();j++){
    			System.out.print(primalCoefficients[i][j]+" ");
    		}
    		System.out.println();
    	}
    }

    public MDPData getMDPData(){
    	return mdpData;
    }

    public double[][] getPrimalCoefficients(){
    	return primalCoefficients;
    }

}