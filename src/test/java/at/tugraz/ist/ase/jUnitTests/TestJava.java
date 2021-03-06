package at.tugraz.ist.ase.jUnitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** Tests for Standard Java
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/
public  class TestJava {
	
	@Test
     void testArrayShallowCopy(){
	
		int [] array= {1,2,3};
		int [] newarray = array;
		
		array[2]= 5;
		
		assertTrue(newarray[0]==array[0]);
		assertTrue(newarray[1]==array[1]);
		assertTrue(newarray[2]==array[2]);
		
	}
	
	@Test
	public void testArrayCopy(){
	
		int [] array= {1,2,3};
		int [] newarray = new int[array.length];
		for(int i=0;i<array.length;i++)
			newarray[i] = array[i];
		
		array[2]= 5;
		
		assertTrue(newarray[0]==array[0]);
		assertTrue(newarray[1]==array[1]);
		assertTrue(newarray[2]!=array[2]);
		
	}
	
	@Test
	public  void testArrayClone(){
	
		int [] array= {1,2,3};
		int [] newarray = array.clone();
		
		array[2]= 5;
		
		assertTrue(newarray[0]==array[0]);
		assertTrue(newarray[1]==array[1]);
		assertTrue(newarray[2]!=array[2]);
		
	}

}
