package at.tugraz.ist.ase.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/** Represents File Operations
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/
public class FileOperations {
	
	public static List<String> readFile(String filename){
		
		List<String> lines = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println(sCurrentLine);
				lines.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

	public static void writeALineToAFile(String line, String filename){
		 
		try {
		
			File file = new File(filename);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(line+"\n");
			
			bw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	
 }
	
	public static void cleanOutputFolder(String folderName){
			File folder = new File(folderName);
		 	File[] files = folder.listFiles();
		    if(files!=null) { //some JVMs return null for empty dirs
		        for(File f: files) {
		           f.delete();
		        }
		    }
		    //folder.delete();
    }

}
