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


    public void upoloadFile(File file, ContactEditorUI gui){
        int noOfState=0;
        String action="";
        String state="";
        String actionsoneachstate="";
        String rewards="";
        String tt="";
        int noOfAction =0;
        int actions[] = new int[1];
        double reward[] = new double[1];
        double gamma=0.5;
        double transitionMatrix[][]=new double[1][1];
        String val[];
        BufferedReader br=null;
        boolean is_correct=true;
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line=null;
            line = br.readLine();
            if(line!=null){
                if(line.toUpperCase().equals("NO OF STATE")){
                	line = br.readLine();
                    if(line!=null){
                        noOfState = Integer.parseInt(line);
                        actions = new int[noOfState];
                        //gui.getNoofstate().setText(line);
                        state=line;
                    }
                    else{
                        is_correct =false;
                    }
                    
                }
                else{
                    is_correct =false;
                }
            }
            else{
                is_correct = false;
            }
            line = br.readLine();
            if(line!=null){
                if(line.toUpperCase().equals("NO OF ACTION")){
                	line = br.readLine();
                    if(line!=null){
                    	noOfAction = Integer.parseInt(line);
                        reward = new double[noOfAction];
                        action = line;
                    }
                    else{
                        is_correct =false;
                    }
                }
                else{
                    is_correct = false;
                }
            }
            else{
                is_correct= false;
            }
            line = br.readLine();
            if(line!= null){
                if(line.toUpperCase().equals("ACTION ON EACH STATE")){
                    line =  br.readLine();
                    if(line== null)
                        is_correct = false;
                    else{
                        val = line.trim().split(" ");
                        if(val.length==noOfState){
                            actionsoneachstate=line;
                        }
                        else{ is_correct =false;}
                    }
                }
                else{is_correct= false;}
            }
            else{is_correct=false;}
            line = br.readLine();
            if(line !=null){
            	if(line.toUpperCase().equals("REWARD OF EACH ACTION")){
                    line =  br.readLine();
                    if(line == null) is_correct = false;
                    else{
                        val = line.trim().split(" ");
                        if(val.length==noOfAction){
                            rewards = line;
        	    	}
                        else{is_correct=false;}
                    }
            	}
                else{is_correct=false;}
            }
            else{is_correct=false;}
            line = br.readLine();
            if(line!=null){
            	if(line.toUpperCase().equals("TRANSITION TABLE")){
                    int countline =0;
                    line =br.readLine();
                    while(line!= null){
                        countline++;
                        val = line.trim().split(" ");
                        
                        if(val.length!= noOfState){
                            is_correct = false;
                            break;
                        }
                        else{
                            tt=tt+line+"\n";
                            line = br.readLine();
                        }
                    }
                    if(countline!=noOfState)
                        is_correct = false;
            	}
                else{is_correct=false;}
            }
            else{is_correct=false;}
            
            if(is_correct=true){
                gui.getNoofstate().setText(state);
                gui.getNoofactions().setText(action);
                gui.getNoofactiononeachstate().setText(actionsoneachstate);
                gui.getRewards().setText(rewards);
                gui.getTransitiontable().setText(tt.substring(0, tt.length()-1));
            }
            else{
                gui.getTransitiontable().setText("Either MDP DATA IS NOT CORRECT OR DATA FORMAT IS NOT CORRECT");
            }
            
           // MDPData mdp = new MDPData(noOfState, noOfAction, reward, transitionMatrix, actions, gamma);
           // mdp.displayMDP();
        }
        catch(FileNotFoundException ex){
        } catch (IOException ex) {
            Logger.getLogger(UploadFile.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}