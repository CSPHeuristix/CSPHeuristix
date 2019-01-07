package at.tugraz.ist.ase.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/** Represents a Copy File Operation
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class CopyFile {

	public static void CopyFileToAnother(String in, String out){
		 
		try {
			FileChannel src = new FileInputStream(in).getChannel();
			FileChannel dest = new FileOutputStream(out).getChannel();
			dest.transferFrom(src, 0, src.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
