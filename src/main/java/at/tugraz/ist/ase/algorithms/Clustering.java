package at.tugraz.ist.ase.algorithms;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.tools.weka.WekaClusterer;
import weka.clusterers.CLOPE;
import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.clusterers.FilteredClusterer;
import weka.clusterers.XMeans;

public class Clustering {

	public int[][] cluster(ClusteringAlgorithmID cid, String dataSetFile, String outputFolder, int numberOfvars, int numberOfClusters){
		
		try {
	        /* Load a dataset */
	        Dataset data;
	        
			data = FileHandler.loadDataset(new File(dataSetFile), numberOfvars, ",");
			Dataset[] clusters = null;
			
	        switch(cid){
		        case kmeans:
		        	KMeans km = new KMeans(numberOfClusters);
		        	clusters = km.cluster(data);
		        	break;
		        case xmeans:
		        	XMeans xm = new XMeans();
		        	WekaClusterer jmlxm = new WekaClusterer(xm);
		        	clusters = jmlxm.cluster(data);
		        	break;
		        case em:
		        	EM emc = new EM();
		        	WekaClusterer jmlem = new WekaClusterer(emc);
		        	clusters = jmlem.cluster(data);
		        	break;
		        case clope:
		        	CLOPE clope = new CLOPE();
		        	WekaClusterer jmlclope = new WekaClusterer(clope);
		        	clusters = jmlclope.cluster(data);
		        	break;
		        case farthestFirst:
		        	FarthestFirst ff = new FarthestFirst();
		        	WekaClusterer jmlff = new WekaClusterer(ff);
		        	clusters = jmlff.cluster(data);
		        	break;
		        case filteredClusterer:
		        	FilteredClusterer fc = new FilteredClusterer();
		        	WekaClusterer jmlfc = new WekaClusterer(fc);
		        	clusters = jmlfc.cluster(data);
		        	break;
		        default:
		        	KMeans km2 = new KMeans(numberOfClusters);
		        	clusters = km2.cluster(data);
		        	break;
	        }
			numberOfClusters = clusters.length;
	       
	        for(int i=0;i<clusters.length;i++){
	        	
	        	boolean dir = new File(outputFolder).mkdir();
	        	File file = new File(outputFolder+"/Cluster"+i+".txt");
	        	
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

	        	FileHandler.exportDataset(clusters[i],file);
	        }
	        
	        
	 	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getClusters(outputFolder, numberOfClusters);
	}

	private int[][] getClusters(String outputFolder, int numberOfClusters){
		 
		 int [][] clusters = new int [numberOfClusters][];
		 
		 for (int i=0;i<numberOfClusters;i++){
			 List<Integer> indexes = new ArrayList<Integer>();
			 
			 try {
				 BufferedReader br = new BufferedReader(new FileReader(outputFolder+"/Cluster"+i+".txt"));
			     StringBuilder sb = new StringBuilder();
			     
			     String line = br.readLine();
			     while (line != null) {
			         sb.append(line);
			         sb.append(System.lineSeparator());
			         int val = Integer.valueOf(line.split("\t")[0].trim());
			         indexes.add(val);
			         line = br.readLine();
			     }
			     clusters[i]= new int[indexes.size()];
			     for(int m=0;m<indexes.size();m++){
			    	 clusters[i][m]=indexes.get(m);
			     }
			     br.close();
			 }
			 catch(Exception e){
				// TODO Auto-generated catch block
				 e.printStackTrace();
			 }
		 }
		 return clusters;
	 }

}
