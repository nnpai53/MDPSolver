/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityFunctions;

import GUI.ContactEditorUI;
import input.MDPData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinit
 */
public class UploadFile {


    public void uploadFile(String filepath, ContactEditorUI gui){
        File file = new File(filepath);
        int noOfState=0;
        int noOfAction =0;
        int actions[] = new int[1];
        double reward[] = new double[1];
        double gamma=0.5;
        double transitionMatrix[][]=new double[1][1];
        String val[];
        BufferedReader br=null;
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
            String line=null;
            line = br.readLine();
            if(line!=null){
                if(line.toUpperCase().equals("NO OF STATE")){
                	line = br.readLine();
                    if(line!=null){
                        noOfState = Integer.parseInt(line);
                        actions = new int[noOfState];
                    }
                }
            }
            line = br.readLine();
            if(line!=null){
                if(line.toUpperCase().equals("NO OF ACTION")){
                	line = br.readLine();
                    if(line!=null){
                    	noOfAction = Integer.parseInt(line);
                        reward = new double[noOfAction];
                    }
                }
            }
            line = br.readLine();
            if(line!= null){
                if(line.toUpperCase().equals("ACTION ON EACH STATE")){
                	    val = br.readLine().trim().split(" ");
                	    if(val.length==noOfState){
                	    	    for(int i=0;i<val.length;i++){
                	    	    	try{
                	    		        actions[i]=Integer.parseInt(val[i]);
                	    	    	}
                	                catch(Exception ex){
                	        	        System.out.println("Exception caught " + ex);
                	                }
                	    	    }
                	    }
                }
            }
            line = br.readLine();
            if(line !=null){
            	if(line.toUpperCase().equals("REWARD OF EACH ACTION")){
                    val = br.readLine().trim().split(" ");
                    if(val.length==noOfAction){
                    	for(int i=0;i<val.length;i++){
        	    	    	try{
        	    	    		reward[i]=Double.parseDouble(val[i]);
        	    	    	}
        	                catch(Exception ex){
        	        	        System.out.println("Exception caught " + ex);
        	                }
        	    	    }
                    }
            	}
            }
            line = br.readLine();
            if(line!=null){
            	if(line.toUpperCase().equals("TRANSITION TABLE")){
            		try{
                        for(int i=0;i<noOfAction;i++){
                        	val = br.readLine().trim().split(" ");
                        	if(val.length == noOfState){
                        	    for(int j=0;j<noOfState;j++){
                        	    	transitionMatrix[i][j]=Double.parseDouble(val[j]);
                        	    }
                        	}
                        }
            		}
            		catch(Exception ex){
            			System.out.println("Exception caught"+ex);
            		}
            	}
            }
            MDPData mdp = new MDPData(noOfState, noOfAction, reward, transitionMatrix, actions, gamma);
        }
        catch(FileNotFoundException ex){
        } catch (IOException ex) {
            Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}