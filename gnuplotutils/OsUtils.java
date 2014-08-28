package gnuplotutils;

import java.io.*;

/**
*
* @author neeraj
*/

public class OsUtils{

	   private static String OS = null;
	   private InputStream is = null;
       private ByteArrayOutputStream baos = null;

	   public static String getOsName(){
	      if(OS == null){ 
	          OS = System.getProperty("os.name"); 
	      }
	      return OS;
	   }
	   
	   public static boolean isWindows(){
	      return getOsName().startsWith("Windows");
	   }

	   public static boolean isLinux(){
	       return getOsName().startsWith("Linux");
	   }
	   
	   public static String getGnuPlotPath() throws Exception{
		   String path = "";
		   String s = "";
		   if(isWindows()){
			   Process p = Runtime.getRuntime().exec("where wgnuplot");
			   BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			   while ((s = stdInput.readLine()) != null) {
	                //System.out.println(s);
	                path = path + s;
	            }
		   }
		   else if(isLinux()){
	           Process p = Runtime.getRuntime().exec("which gnuplot");
	           BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	           while ((s = stdInput.readLine()) != null) {
	                //System.out.println(s);
	        	    path = path + s;
	            }
		   }
		   return path;
	   }
	   
	   public static String getPathSeparator(){
		   String pathSeparator = "";
		   if(isWindows()){
			    pathSeparator = "\\"; 
		   }
		   else if(isLinux()){
			   pathSeparator = "/";
		   }
		   return pathSeparator;
	   }
	   
	   public static String getGnuPlotFileExtension(){
		   String fileExtension = "";
		   if(isWindows()){
			    fileExtension = "gnu"; 
		   }
		   else if(isLinux()){
			   fileExtension = "p";
		   }
		   return fileExtension;
	   }
	   
	   public static String getImageFileExtension(){
		   String fileExtension = "";
		   if(isWindows()){
			    fileExtension = "jpeg"; 
		   }
		   else if(isLinux()){
			   fileExtension = "jpeg";
		   }
		   return fileExtension;
	   }
	   
	  /* public static void main(String args[]){
		   OsUtils osu = new OsUtils();
		   getGnuPlotPath();
	   } */
}

