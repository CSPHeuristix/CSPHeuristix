package at.tugraz.ist.ase.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {
	
	
	 public void writeALineToAFile(String line, String filename){
		 
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

//	 public int appendToBottomOfFile(String line,String filename) {
//			
//			line = "\n"+line;
//		    int lineNumber=0;
//			try {
//				
//				Files.write(Paths.get(filename), line.getBytes(), StandardOpenOption.APPEND);
//				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return lineNumber-1;
//			
//		}
}
