package common.tools;

import java.io.File;
//this is to remove files which are matched with a pattern from a directory
public class RemoveMistakeCases {
	static int removeCounter = 0;

	public static void main(String[] args) {
		
		RemoveMistakeCases rmc = new RemoveMistakeCases();
		String src = "C:/Safi/Experiments/Experiment-VariableMessaging_finished_13_July_2010_Corrected_23_Aug_2010/Type-1";
		rmc.visitAllFiles(new File(src));
		System.out.println("removed files = "+removeCounter);
		System.out.println("Finished....");
	}
	
	// Process only files under dir
	public  void visitAllFiles(File dir) {
		NameFilter nf = new NameFilter();
	    if (dir.isDirectory()) {
			String[] children = nf.sortArray(dir.list());
	        for (int i=0; i<children.length; i++) {
	            visitAllFiles(new File(dir, children[i]));
	        }
	    } else processFile(dir);
	}

	private void processFile(File dir) {
		
		NameFilter nf = new NameFilter();
		if(nf.checkMatching(nf.mistakeCases, dir.getName())>=0){
			removeCounter++;
			dir.delete();
		}
	}

}
