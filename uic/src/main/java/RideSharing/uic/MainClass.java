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
	public static void main( String[] args ) throws ClientProtocolException, SQLException, IOException, InterruptedException{
		String delay="6";
		String pool_size="360";
		initiator(delay,pool_size);
	}
	public static void initiator( String delay, String pool_size ) throws SQLException, ClientProtocolException, IOException, InterruptedException
	{

//		System.out.println(args[0]+" "+args[1]);
		
			
		System.out.println(new Date().toString());


		ArrayList<ArrayList<String>> trips_interval = new ArrayList<ArrayList<String>>();

		DbConnector db_obj=new DbConnector();
		AlgorithmClass algorithm_obj=new AlgorithmClass(delay);
		ApiAdapter gh_obj=new ApiAdapter();


		algorithm_obj.initialVehicleLocationGenerator();

		trips_interval=db_obj.getTripsPerInterval(pool_size);
		ArrayList<String> source_points=new ArrayList<String>();
		ArrayList<String> destination_points=new ArrayList<String>();
		for (int i = 0; i < trips_interval.size(); i++) {
			//
			source_points.add(trips_interval.get(i).get(3));
			source_points.add(trips_interval.get(i).get(2));
			destination_points.add(trips_interval.get(i).get(5).replaceAll("\\s", "").replaceAll("\\n", ""));
			destination_points.add(trips_interval.get(i).get(4));
			//			System.out.println("Printing trips");
			//			System.out.println("\""+trips_interval.get(i).get(5).replaceAll("\\s", "").replaceAll("\\n", "")+"\"");
			//			System.out.println("\""+trips_interval.get(i).get(4)+"\"");

			//latitude=3,longitude=2 //indexes of trips_interval.get(i)
		}
		//		    
		//	    System.out.println(new Date().toString());
		//
		//

		//		gh_obj.GoogleMaps_single_source_single_dest("40.779613","-73.955498","40.741043","-73.988235");

		//
		////		gh_obj.BatchMatrixAPI(points);
		//		

		algorithm_obj.user_individual_time(source_points,destination_points);
		gh_obj.GoogleMapsMatrixAPI_req(source_points);

		algorithm_obj.RVGraphPart2(source_points);

		algorithm_obj.RTVGraph();
		algorithm_obj.find_optimal_assignment();
		System.out.println(new Date().toString());


	}
	
	
}
