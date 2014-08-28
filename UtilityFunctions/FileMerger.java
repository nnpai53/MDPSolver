/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityFunctions;

import gnuplotutils.OsUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vinit
 */
public class FileMerger {
    
    private String file1name;
    private String file2name;
    
    public FileMerger(String file1, String file2)
    {
        this.file1name = file1;
        this.file2name = file2;
    }
    
    public void merger()
    {
        String line1="", line2="";
        BufferedReader reader1=null, reader2=null;
        String content="";
        File output =null;
        String prevline1="",prevline2="";
        try {
        	String filePath1 = file1name + OsUtils.getPathSeparator() + "ValueSum.dat";
        	String filePath2 = file2name + OsUtils.getPathSeparator() + "ValueSum.dat";
            reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath1)));
            reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath2)));
            File directory = new File("compare Algorithm");

            if(!directory.exists())
            {
                directory.mkdir();;
            }
            String pathSeparator = OsUtils.getPathSeparator();
            String imageFileExtension = OsUtils.getImageFileExtension();
            String gnuplotPathExtension = OsUtils.getImageFileExtension();
            String outputFilePath = "compare Algorithm" + pathSeparator + "comparedata.dat";
            output = new File(outputFilePath);
            String outputImagePath = "compare Algorithm" + pathSeparator + "compareval." + imageFileExtension ;
            File output2 =  new File(outputImagePath);
            String outputGnuPlotPath = "compare Algorithm" + pathSeparator + "compare_Script." + gnuplotPathExtension;
            File ouput3 = new File(outputGnuPlotPath);
            if(output.exists()){
                output.delete();;
            }
            output.createNewFile();
            int count=0;
            
            while ((((line1 = reader1.readLine()) != null) | ((line2 = reader2.readLine()) != null))&&count<15)
            {
                if(line1 == null ){
                    content+=prevline1+" ";
                    count++;
                }
                else{
                    content+=line1+" ";
                    prevline1 =  line1;
                }
                if(line2 ==  null){
                    content+=prevline2+"\n";
                    count++;
                }
                else{
                    content+=line2;
                    prevline2 =line2;
                    content+="\n";
                }
            }
            FileWriter fw = new FileWriter(output.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileMerger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileMerger.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
    public static void main(String a[]){
        FileMerger fm = new FileMerger("Simplex\\ValueSum.dat","MixStrategy\\ValueSum.dat");
        fm.merger();
    }
}
