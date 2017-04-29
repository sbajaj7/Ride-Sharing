package RideSharing.uic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

/**
 * Hello world!
 *
 */
public class MainClass 
{
	public static void main( String[] args ) throws SQLException, ClientProtocolException, IOException, InterruptedException
	{

		System.out.println( "This is me signing in!" );

		System.out.println(new Date().toString());

		
		ArrayList<ArrayList<String>> trips_interval = new ArrayList<ArrayList<String>>();

		        DbConnector db_obj=new DbConnector();
		        AlgorithmClass algorithm_obj=new AlgorithmClass();
				ApiAdapter gh_obj=new ApiAdapter();

		        
		        algorithm_obj.initialVehicleLocationGenerator();
		        
		        trips_interval=db_obj.getTripsPerInterval();
		        ArrayList<String> points=new ArrayList<String>();
		        
		    	for (int i = 0; i < trips_interval.size(); i++) {
//
					points.add(trips_interval.get(i).get(3));
					points.add(trips_interval.get(i).get(2));

		//latitude=3,longitude=2 //indexes of trips_interval.get(i)
				}
//		    
//	    System.out.println(new Date().toString());
//
//		        
////		gh_obj.RoutingAPI("40.779613","-73.955498","40.741043","-73.988235");
//
////		gh_obj.BatchMatrixAPI(points);
//		
		gh_obj.GoogleMapsMatrixAPI_req(points);

		        algorithm_obj.RVGraphPart2(points);

		        algorithm_obj.RTVGraph();

		        System.out.println(new Date().toString());

	}
}
