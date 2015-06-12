/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import MixPolicyAlgoFirst.MixStrategy_Algorithm;
import PrimalAffine.PrimalAffine_Algorithm;
import Simplex.Simplex_Algorithm;
import gnuplotutils.Gnu_Script_Writer;
import input.MDPData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import policyiteration.LeastIndexPivotModifiedPolicyIterationImpl;
import policyiteration.LeastIndexPivotPolicyIterationImpl;
import policyiteration.ModifiedPolicyIterationImpl;
import policyiteration.PolicyIterationImpl;
import policyiteration.SinglePivotModifiedPolicyIterationImpl;
import policyiteration.SinglePivotPolicyIterationImpl;
import valueiteration.ValueIterationImpl;

/**
 *
 * @author vinit
 */
public class GUI_Algo_Connector {
    
    private String algoname;
    
    private MDPData mdp;
    
    
    
    
    public void connector(MDPData mdpdata, String algo)throws Exception{
        
        mdp =  mdpdata;
        
        algoname =  algo;
        
        String path = "";
        if(algo.equals("simplex")){
            System.out.println("IN GUI CONNECTOR");
            MDPData simplex_mdp = new MDPData(mdp);
            new Simplex_Algorithm(simplex_mdp).runSimplexAlgorithm(0);
        
            path = "Simplex";
        
            Gnu_Script_Writer gnuImager = new Gnu_Script_Writer(simplex_mdp.getNoOfStates(), simplex_mdp.getNumOfActionsOnEachState(), path);

        }
        
        else if(algo.equals("primal affine")){
            MDPData primal_mdp = new MDPData(mdp);
            new PrimalAffine_Algorithm(primal_mdp).runPrimalAffine();
        
            path = "Primal_Affine";
        
            new Gnu_Script_Writer(primal_mdp.getNoOfStates(), primal_mdp.getNumOfActionsOnEachState(), path);

        }
        
        else if(algo.equals("cute algo")){
            path = "MixStrategy";
            MDPData cute_mdp = new MDPData(mdp);        
            new MixStrategy_Algorithm(cute_mdp).runMixStrategy();
        
            new Gnu_Script_Writer(cute_mdp.getNoOfStates(), cute_mdp.getNumOfActionsOnEachState(), path);

        }
        
        else if(algo.equals("value iteration")){
            path = "ValueIteration";
            MDPData value_mdp = new MDPData(mdp);                    
            new ValueIterationImpl(value_mdp).run();

        }
        
        else if(algo.equals("policy iteration")){
            
            path = "PolicyIteration";
            MDPData policy_mdp = new MDPData(mdp);                                
            new PolicyIterationImpl(policy_mdp).run();

        }
        else if(algo.equals("Modified policy iteration")){
            path = "ModifiedPolicyIteration";
            MDPData mod_policy_mdp = new MDPData(mdp);                                            
            new ModifiedPolicyIterationImpl(mod_policy_mdp).run();

        }
        else if(algo.equals("Bland simplex on dual")){
            path = "Simplex_Bland";
            MDPData bland_simplex_mdp = new MDPData(mdp);                                                        
            new Simplex_Algorithm(bland_simplex_mdp).runSimplexAlgorithm(1);
            new Gnu_Script_Writer(bland_simplex_mdp.getNoOfStates(), bland_simplex_mdp.getNumOfActionsOnEachState(), path);

        }
        else if(algo.equals("Single pivot policy iteration")){
            path = "SinglePivotPolicyIteration";
            MDPData single_pivot_policy_mdp = new MDPData(mdp);                                                                    
            new SinglePivotPolicyIterationImpl(single_pivot_policy_mdp).run();

        }
        else if(algo.equals("Single pivot modified policy iteration")){
            path = "SinglePivotModifiedPolicyIteration";
            MDPData single_pivot_modified_policy_mdp = new MDPData(mdp);                                                                                
            new SinglePivotModifiedPolicyIterationImpl(single_pivot_modified_policy_mdp).run();

        }
        else if(algo.equals("Least index policy iteration")){
            path = "LastIndexPivotPolicyIteration";
            MDPData least_index_policy_mdp = new MDPData(mdp);                                                                                            
            new LeastIndexPivotPolicyIterationImpl(least_index_policy_mdp).run();

        }
        else if(algo.equals("Least index modified policy iteration")){
            path = "LastIndexPivotModifiedPolicyIteration";
            MDPData least_index_modified_policy_mdp = new MDPData(mdp);                                                                                                        
            new LeastIndexPivotModifiedPolicyIterationImpl(least_index_modified_policy_mdp).run();

        }

        
    }
    
}
