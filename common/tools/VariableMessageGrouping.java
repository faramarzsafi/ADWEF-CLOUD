package common.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//this file is for grouping variable messaging files...
public class VariableMessageGrouping {
	
//	private String baseSrcPath = "C:/Safi/Experiment-VariableMessaging_finished_13_July_2010/Type-1/250kb- 50, 500";
//	private String baseDstPath = "C:/Safi/Experiment-VariableMessaging_finished_13_July_2010/Type-1/Analysis";
	private String baseSrcPath = "C:/Safi/Experiments/Experiment-VariableMessaging_finished_13_July_2010_Corrected_23_Aug_2010/Type-1/50kb- 50, 500";
	private String baseDstPath = "C:/Safi/Experiments/Experiment-VariableMessaging_finished_13_July_2010_Corrected_23_Aug_2010/Type-1/Analysis";
		
	private String messageMode = "050kb";
	
	public static void main(String[] args) throws IOException{
		
		VariableMessageGrouping g = new VariableMessageGrouping();
		g.visitAllFiles(new File(g.baseSrcPath));
	}
	
	// Process only files under dir
	public void visitAllFiles(File dir) throws IOException {
	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            visitAllFiles(new File(dir, children[i]));
	        }
	    } else {
	        process(dir);
	    }
	}

	private void process(File file) throws IOException {
		System.out.println("Processing..."+file.getName());
		String dest = baseDstPath;
		NameFilter nf = new NameFilter();
		dest += "/"+getType(file);
		dest += "/"+nf.getNumberOfmachine(file)+"machine";
		dest += "/"+messageMode+"/"+file.getName();
		copy( file, new File(dest));
	}
	
	// Copies src file to dst file.
	// If the dst file does not exist, it is created
	void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    new File(dst.getParent()).mkdirs();
	    dst.createNewFile();
	    OutputStream out = new FileOutputStream(dst);
	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
		
	void moveTo(File dest, File file) throws IOException{
		
//		return f.renameTo((new File(d+"/"+f.getName())));
		copy(dest, file);
	}
	
	String getType(File f){
	
		if (f.getName().indexOf("LOAN")>=0) return "Loan";
		else if (f.getName().indexOf("IF")>=0) return "If";
		else if (f.getName().indexOf("WHILE")>=0) return "While";
		else if (f.getName().indexOf("FLOW")>=0) return "Flow";
		else return "";
		
	}
	

}
