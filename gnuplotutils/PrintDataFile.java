/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnuplotutils;

import input.MDPData;
import input.StateData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinit
 */
public class PrintDataFile {
    //DualClass dc;
    public PrintDataFile(){init();}
    
    
    //Give Relative Path
    public PrintDataFile(String path)
    {
        this.path = path;
        init();
    }
    
    private void init()
    {
    	String pathSeparator = OsUtils.getPathSeparator();
        try
        {        
            String currentDir = new File(".").getCanonicalPath();
            this.path = currentDir + pathSeparator + path + pathSeparator;
            File file = new File(path);
            if(!file.exists())
            {
                file.mkdirs();
            }
            this.delete_Files();
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }        
    }
    
    
    String path = "Result";

    public  void delete_Files(String folder_Name){
    File file = new File(folder_Name);        
    String[] myFiles;      
        if(file.isDirectory()){  
            myFiles = file.list();  
            for (int i=0; i<myFiles.length; i++) {  
                File myFile = new File(file, myFiles[i]);   
                myFile.delete();  
            }  
         }  
    }

    public  void delete_Files(){
        delete_Files(path);  
    }
        
        
    public void printData(MDPData mdp)
    {
        printStateDataFile(mdp);
        printSumOfValues(mdp);
    }
        
    public void printStateDataFile(MDPData mdp){
        //mdp.displayMDP();
        ArrayList<StateData> statedata = mdp.getStateList();
        PrintWriter []in =  new PrintWriter[mdp.getNoOfStates()];
        String pathSeparator = OsUtils.getPathSeparator();
        for(int index=0;index<mdp.getNoOfStates();index++){
            String file_Name = path+ pathSeparator + "State"+(index+1)+".dat";
            try {
                in[index]= new PrintWriter(new PrintWriter(new FileWriter(file_Name, true)));
            } catch (IOException ex) {
                Logger.getLogger(PrintDataFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int k=0;k<statedata.get(index).getNoOfActions();k++){
                    in[index].append(statedata.get(index).getActionList().get(k).getProbDist()+" ");
            }
            in[index].append(""+statedata.get(index).getValue());
            in[index].println();
                
            in[index].close();
        }
    }
    
    public void printSumOfValues(MDPData mdp)
    {
    	String pathSeparator = OsUtils.getPathSeparator();
        String file_Name = path+ pathSeparator + "ValueSum.dat";
        try {
            PrintWriter pw= new PrintWriter(new FileWriter(file_Name, true));
            pw.append(""+mdp.getSumOfAllValues());
            pw.println();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(PrintDataFile.class.getName()).log(Level.SEVERE, null, ex);
        }               
    }
    
}
