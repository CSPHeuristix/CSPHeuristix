package at.tugraz.ist.ase.algorithms;

/** Holds various Multiple Attribute Utility Theory functions
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class MAUT {
	
	
	// utility = interest(user,dimension) x value(item,dimension)
	public static double basic(double[] user, double []item){
		double utility=0;
		for(int i=0;i<user.length;i++)
			utility += user[i]*item[i];
		return utility;
	}

}
