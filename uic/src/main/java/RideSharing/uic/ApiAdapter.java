package RideSharing.uic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class ApiAdapter {


	HttpUriRequest request;
	HttpResponse httpResponse;
	HttpEntity entity ;
	ArrayList<String> vehicle_req_json=new ArrayList<String>();
	
	public String RoutingAPI(String point1_lat,String point1_long,String point2_lat,String point2_long) throws ClientProtocolException, IOException{
		request = new HttpGet( "https://graphhopper.com/api/1/route?point="+
				point1_lat+"%2C"+point1_long+ "&point="+
				point2_lat+"%2C"+point2_long+"&vehicle=car&locale=en&key=4bb8694e-ce97-4d7e-b5cc-a2ed5410f92d&calc_points=false"  );		

		httpResponse = HttpClientBuilder.create().build().execute( request );
		entity = httpResponse.getEntity();

		String json=EntityUtils.toString(entity);

		System.out.println(json);
		return json;
	}


	public void MatrixAPI(ArrayList<String> points) throws ClientProtocolException, IOException{

		String api_request="https://graphhopper.com/api/1/matrix?";


		for (int i = 0; i < points.size(); i++) {
			if(i%2==0)
			{
				api_request+="point=";
			}
			api_request+=points.get(i);
			if(i%2==0)
			{
				api_request+="%2C";
			}
			else if(i%2==1)
			{
				api_request+="&";
			}

		}

		api_request+="type=json&vehicle=car&debug=true&out_array=times&out_array=distances&key=4bb8694e-ce97-4d7e-b5cc-a2ed5410f92d";

		System.out.println(api_request);
		HttpUriRequest request = new HttpGet( api_request );		

		httpResponse = HttpClientBuilder.create().build().execute( request );
		entity = httpResponse.getEntity();

		System.out.println(EntityUtils.toString(entity));
	}


	public void BatchMatrixAPI(ArrayList<String> points) throws ClientProtocolException, IOException{

		String api_request="curl -X POST -H \"Content-Type: application/json\" \"https://graphhopper.com/api/1/matrix/calculate?key=4bb8694e-ce97-4d7e-b5cc-a2ed5410f92d&out_array=distances\" -d '{\"points\":[";


		for (int i = 0; i < points.size(); i++) {
			if(i%2==0)
			{
				api_request+="[";
			}
			api_request+=points.get(i);
			if(i%2==0)
			{
				api_request+=",";
			}
			else if(i%2==1)
			{
				api_request+="],";
			}

		}
		api_request=api_request.substring(0, api_request.length()-1);
		api_request+="]}'";

		System.out.println(api_request);
		//				HttpUriRequest request = new HttpGet( api_request );		
		//			
		//				 httpResponse = HttpClientBuilder.create().build().execute( request );
		//			     entity = httpResponse.getEntity();
		//		
		//				System.out.println(EntityUtils.toString(entity));
	}
	
	
	public String GoogleMaps_single_source_single_dest(String point1_lat,String point1_long,String point2_lat,String point2_long) throws ClientProtocolException, IOException{

		https://maps.googleapis.com/maps/api/directions/json?origin=40.779613%2C-73.955498&destination=40.741043%2C-73.988235&departure_time=1498374182&key=AIzaSyAwsg9mLuI2ddpqk_eoIaxJdQScAVE0Uq4

		
		request = new HttpGet( "https://maps.googleapis.com/maps/api/directions/json?origin="+
				point1_lat+"%2C"+point1_long+ "&destination="+
				point2_lat+"%2C"+point2_long+"&departure_time=1498374182&key=AIzaSyAwsg9mLuI2ddpqk_eoIaxJdQScAVE0Uq4"  );		

		httpResponse = HttpClientBuilder.create().build().execute( request );
		entity = httpResponse.getEntity();

		String json=EntityUtils.toString(entity);

//		System.out.println(json);
		return json;
	}

	public void GoogleMapsMatrixAPI_req(ArrayList<String> source_points) throws ClientProtocolException, IOException, InterruptedException{

		AlgorithmClass algorithmClass_obj=new AlgorithmClass(source_points.size()/2);


		System.out.println(source_points.size());
		String all_points="";
		for (int i = 0; i < source_points.size(); i=i+2) {
			all_points+=source_points.get(i)+"%2C";
			all_points+=source_points.get(i+1)+"%7C";



		}
		all_points=all_points.substring(0,all_points.length()-3);


		for (int i = 0; i < source_points.size(); i=i+2) {
			//from one user to all users
			String api_request="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";

			//origin
			api_request+=source_points.get(i)+"%2C";
			api_request+=source_points.get(i+1)+"&destinations=";

			//destination
			api_request+=all_points;
			api_request+="&departure_time=1498374182&key=AIzaSyAwsg9mLuI2ddpqk_eoIaxJdQScAVE0Uq4";
			System.out.println(api_request);
			System.out.println("///////////////EXECUTION NUMBER "+i);


			HttpUriRequest request = new HttpGet( api_request );		

			httpResponse = HttpClientBuilder.create().build().execute( request );
			entity = httpResponse.getEntity();
			String json=EntityUtils.toString(entity);
			System.out.println(json);

			algorithmClass_obj.RVGraphPart1(json);

			//			System.out.println(EntityUtils.toString(entity));
			Thread.sleep(1000);

			//		algorithmClass_obj.RVGraphPart1("");

			//
		}
	}

	public ArrayList<String> GoogleMapsMatrixAPI_vehicle_req(ArrayList<String> Vehicle_points,ArrayList<String> req_points) throws ClientProtocolException, IOException, InterruptedException{

		int[][] rv_request_vehicle_matrix= new int[req_points.size()/2][Vehicle_points.size()/2];

		String all_vehicle_points="";

		for (int i = 0; i < Vehicle_points.size(); i=i+2) {
			//100 times (100 vehciles)
			all_vehicle_points+=Vehicle_points.get(i)+"%2C";
			all_vehicle_points+=Vehicle_points.get(i+1)+"%7C";
		}
		all_vehicle_points=all_vehicle_points.substring(0,all_vehicle_points.length()-3);

		System.out.println(all_vehicle_points);
	
	for (int i = 0; i < req_points.size(); i=i+2) {
		//59 times
		
		String api_request="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";

		//origin
		api_request+=req_points.get(i)+"%2C";
		api_request+=req_points.get(i+1)+"&destinations=";

		//destination
		api_request+=all_vehicle_points;
		api_request+="&departure_time=1498374182&key=AIzaSyAwsg9mLuI2ddpqk_eoIaxJdQScAVE0Uq4";
		System.out.println("///////////////EXECUTION NUMBER "+i);

		HttpUriRequest request = new HttpGet( api_request );		

		httpResponse = HttpClientBuilder.create().build().execute( request );
		entity = httpResponse.getEntity();
		String json=EntityUtils.toString(entity);
		vehicle_req_json.add(json);
		System.out.println(json);
//		break;
		
		Thread.sleep(1000);


		}

	return vehicle_req_json;//

}
}
