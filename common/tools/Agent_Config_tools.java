package common.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import common.share;

public class Agent_Config_tools {

	/**
	 * @param args
	 */
	//this is for extracting inforamtion from main_properties file
	public String createConfigFragment(){
        String validStyle = null;
		try {
	        BufferedReader in = new BufferedReader(new FileReader(share.MAIN_PROPERTIES_FILE_PATH));
	        String str;
	        String style = null;
	        String styleName = null;

	        int count = 0;
	        while ((str = in.readLine())!= null) {
	        	str.trim();
        		if (str.indexOf("#")==0) {
        			style = str.substring(1);
        			styleName = str;
        		}
        		
	        	if (str.indexOf("agents=")==0){
	        		validStyle = styleName;
	        		str = str.substring(str.indexOf('=')+1);
	        		str.trim();
	        		Scanner scanner = new Scanner(str);
	        		scanner.useDelimiter(";");
	        	    while ( scanner.hasNext() ){
	        	      String str2 = scanner.next();
	        	      Scanner scanner2 = new Scanner(str2);
        	    	  scanner2.useDelimiter(":");
	        	      String name = scanner2.next();
	        	      String value = scanner2.next();
	        	      count++;
	        	      System.out.println("<agent name=\""+name.trim()+"\" "+"className=\""+ value.trim()+"\"/>");
	        	    }
	        	}
	        }
	        System.out.println("Total number of agents is: "+count);
       
	        
	        in.close();
	    } catch (IOException e) {
	    	System.out.println("Share: IOException: in the catch"+e.getMessage()+e.getCause());
	    }
	    return validStyle;
	}
	public static void main(String[] args) {
		Agent_Config_tools t = new Agent_Config_tools();
		System.out.println("Style Name is: "+	t.createConfigFragment());
		System.out.println("Style Code is: "+share.getWorkflowStyle());
	}

}
