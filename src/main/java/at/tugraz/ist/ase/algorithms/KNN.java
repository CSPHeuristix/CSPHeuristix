package at.tugraz.ist.ase.algorithms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class KNN {
	public KNN(){}
	
	public int[] getKNN(int k, int []req, int [][]users, int [][]vars){
		
		double [] reqs_normalized= new MinMaxNormalization().normalize(req, vars);
		HashMap<Double, Integer> hmap = new HashMap<Double, Integer>();
		
		for (int i=0; i<users.length;i++){
			double [] user_normalized= new MinMaxNormalization().normalize(users[i], vars);
			
			// DISTANCE
			double dist = new EuclideanDistance().distanceOfArrays(reqs_normalized,user_normalized);
			hmap.put(dist, i);
			
		}
		int[] similarIndexes = new int[k];
		Map<Double, Integer> map = new TreeMap<Double, Integer>(hmap); 
        //System.out.println("After Sorting:");
        Set set2 = map.entrySet();
        Iterator iterator2 = set2.iterator();
        int count =0;
        while(count<k) {
        	similarIndexes[count]= (int) ((Map.Entry)iterator2.next()).getValue();
        	count++;
        }
		
		return similarIndexes;			
	}

	public int findClosestCluster(int [][] clusteredItems, int []reqs){
		int index = 0;
		
		// CALCULATE MEANS OF THE CLUSTERS
        int [][] meanValues = new int [clusteredItems.length][reqs.length];
        for(int c=0;c<clusteredItems.length;c++){
        	 for(int v=0;v<reqs.length;v++){
        		int sum_of_v = 0;
             	for(int e=0;e<clusteredItems[c].length;e++){
             		// get Vth value of the CSP in the cluster
             		sum_of_v += clusteredItems[c][e];
             	}
             	int avg_of_v = 0;
             	
             	if (clusteredItems[c].length!=0)
             		avg_of_v = sum_of_v/clusteredItems[c].length;
             	
             	meanValues[c][v] = avg_of_v;
             }
        }
        
       // USE EUCLIDEAN DISTANCE FORMULA
		double [] distances = new double [clusteredItems.length];
        for(int c=0;c<clusteredItems.length;c++){
        	 int sum_of_sqr_subtract = 0;
        	 for(int v=0;v<reqs.length;v++){
        		 int subtract_v = reqs[v] - meanValues[c][v];
        		 int sqr_subtract_v = subtract_v*subtract_v;
        		 sum_of_sqr_subtract += sqr_subtract_v;
             }
        	 distances[c] = Math.sqrt(sum_of_sqr_subtract);
        }
        
        double min = distances[0];
        for(int k=1;k<clusteredItems.length-1;k++){
        	if(min>distances[k]){
        		min = distances[k];
        		index= k;
        	}
        }
        return index;
	}
	
	
//	private int[] aggregateAvg (int[][] similars){
//		int size = similars[0].length;
//		int[] aggr = new int [size];
//		for(int i=0;i<size;i++){
//			aggr[i]=0;
//			for(int j=0;j<similars.length;j++){
//				if(j==0)
//					aggr[i]=0;
//				aggr[i] += similars[j][i];
//			}
//			aggr[i] = aggr[i] / similars.length;
//		}
//		
//		return aggr;
//	}
//	
//	private double[] aggregateAvgDouble (double[][] similars){
//		int size = similars[0].length;
//		double[] aggr = new double [size];
//		for(int i=0;i<size;i++){
//			
//			for(int j=0;j<similars.length;j++){
//				if(j==0)
//					aggr[i]=0;
//				aggr[i] += similars[j][i];
//			}
//			aggr[i] = (double)(aggr[i] / similars.length);
//		}
//		
//		return aggr;
//	}
//	
//	private int aggregateProductID (int[] similarIndexes){
//		int size = similarIndexes.length; 
//		HashMap<Integer,Integer> products = new HashMap<Integer,Integer>();
//		
//		for(int i=0;i<similarIndexes.length;i++){
//			if(products.get(similarIndexes[i])==null)
//				products.put(similarIndexes[i], 1);
//			else
//				products.put(similarIndexes[i], products.get(similarIndexes[i])+1);
//		}
//		
//		List<Integer> mapValues = new ArrayList<>(products.values());
//		Collections.sort(mapValues);
//		Iterator<Integer> valueIt = mapValues.iterator();
//		Integer val = valueIt.next();
//		 
//		return products.get(val);
//	}
//	
	
}
