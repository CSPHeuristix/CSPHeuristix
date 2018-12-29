package at.tugraz.ist.ase.algorithms;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import at.tugraz.ist.ase.util.CopyFile;
import at.tugraz.ist.ase.util.WriteToFile;


public class CollaborativeFiltering {

		String userRatingsFile;
		List<RecommendedItem> recommendations=null;
		int domainValues[][];
		
		public List<RecommendedItem> applyCollaborativeFiltering(String userRatingsFile, int KNN,  int numberOfRecommendations, int [] reqs, int domains [][], int methodID){
			
			this.userRatingsFile = userRatingsFile; 
			this.domainValues = domains.clone();
			
			int userID = newProblemFile(reqs);
			
			try {
				// load the data from the file with format "userID,itemID,value"
				DataModel model = new FileDataModel(new File(userRatingsFile));
				UserSimilarity similarity;
				
				switch(methodID){
					case 0:
						similarity = new EuclideanDistanceSimilarity(model);
						break;				
					case 1:
						similarity = new PearsonCorrelationSimilarity(model);
						break;		
					case 2:
						similarity = new UncenteredCosineSimilarity(model);
						break;		
					case 3:
						similarity = new TanimotoCoefficientSimilarity(model);
						break;		
					default:
						similarity = new EuclideanDistanceSimilarity(model);
						break;		
						
				}
				//  compute the correlation coefficient between their interactions
				//UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
				// UserSimilarity similarity = new EuclideanDistanceSimilarity(model);
				
				// we'll use all that have a similarity greater than 0.1
				
			
				//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(KNN, similarity, model);
				
				// all the pieces to create our recommender
				UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
				
				//  get n items recommended for the user with userID 
				recommendations = recommender.recommend(userID, numberOfRecommendations);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return recommendations;
		}
		
		private int newProblemFile(int []reqs){
			
			String newUserFile =userRatingsFile+"_"+Math.random();
			CopyFile.CopyFileToAnother(userRatingsFile, newUserFile);
			
			int itemIndex=0;
			for(int i=0;i<reqs.length;i++){
				
				if(reqs[i]!=-1){
					int size = domainValues[i].length;
					for(int d=0;d<size;d++){
						if(reqs[i] == domainValues[i][d])
							WriteToFile.appendToBottomOfFile(20+","+itemIndex+",1.0",newUserFile);
						else
							WriteToFile.appendToBottomOfFile(20+","+itemIndex+",0.0",newUserFile);
						itemIndex++;
					}
				}
				else{
					int size = domainValues[i].length;
					itemIndex += size;
				}
			}
				
			return itemIndex;
		}
		
			
}


