package common.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//this file is for grouping variable messaging files...
public class ResponseTimeThroughputGrouping {
	
	
	private String baseSrcPath = "";
	private String baseDstPath = "";
	
	public static void main(String[] args) throws IOException{
		ResponseTimeThroughputGrouping g;		
		int numberOfMachines = 16;
		for(int counter=1; counter<=numberOfMachines; counter++){
			g = new ResponseTimeThroughputGrouping();
			g.baseSrcPath = "C:/Safi/Experiments/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final/Mode=2/Mode=2-Server="+counter;
//			g.baseSrcPath = "C:/Safi/Experiments/BoundaryConditionExperiments_finished_17_Aug_2010/ResponseTime-throughput/n=1";
			g.baseDstPath = g.baseSrcPath+"/Analysis";
			g.visitAllFiles(new File(g.baseSrcPath));
		}
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
		dest += "/"+nf.getType(file)+"/"+file.getName();
//		dest += "/"+nf.getNumberOfmachine(file)+"machine";
//		dest += "/"+messageMode+"/"+file.getName();
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
}
