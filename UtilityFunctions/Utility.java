/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityFunctions;

import Jama.Matrix;

/**
 *
 * @author vinit
 */
public class Utility {
    
    public static boolean compareVectorUptoAprecision(double[] in, double precision){
        
        for(int i=0;i<in.length;i++){
            if(in[i]>precision){
                return false;
            }
        }
        return true;
    }
    
    public static Matrix vectorToMatrix(double in[])
    {
        double ret[][]=new double[in.length][1];
        
        for(int i=0;i<in.length;i++){
            ret[i][0]=in[i];
        }
        
        return new Matrix(ret);
    }
    
    public static double[] unitVector(int length){
        double [] ret = new double[length];
        for(int i=0;i<length;i++){
            ret[i]=1.0;
        }
        return ret;
    }
 
    public static Matrix identityMatrix(int dim){
        
        double[][] ret = new double[dim][dim];
        
        for(int i=0;i<dim;i++)
        {
            ret[i][i]=1.0;
        }
        
        return new Matrix(ret);
    }
}
