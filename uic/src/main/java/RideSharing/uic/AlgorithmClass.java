package RideSharing.uic;

import java.awt.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import  org.json.*;
public class AlgorithmClass {

	static int[][] rv_requests_matrix;
	static int[][] rv_request_vehicle_matrix;
	int index=0;
	int count=0;
	int vehicle_count=100;
	ArrayList<String> vehicleLocations;
	DbConnector db_obj=new DbConnector();
	ApiAdapter apiAdapter_obj=new ApiAdapter();

	AlgorithmClass(){

	}
	AlgorithmClass(int size){
		rv_requests_matrix=new int[size][size];
		vehicleLocations=new ArrayList<String>(vehicle_count);
	}

	public void initialVehicleLocationGenerator() throws SQLException{

		vehicleLocations=new ArrayList<String>(vehicle_count*2);
		ArrayList<String> denseAreas=db_obj.getDenseAreas();
		System.out.println("denseAreas"+denseAreas);

		int denseAreasCount=denseAreas.size()/2;
		int x=vehicle_count/denseAreasCount;
		System.out.println(x);
		for (int i = 0; i < denseAreasCount*2; i=i+2) {
			for (int j = 0; j < x; j++) {
				vehicleLocations.add(denseAreas.get(i));
				vehicleLocations.add(denseAreas.get(i+1));

			}

		}
		System.out.println(vehicleLocations);

	}

	public void RVGraphPart1(String distanceJson){

		//	       distanceJson = "{ \"name\": \"Alice\", \"age\": 20 }";

		JSONObject json_obj = new JSONObject(distanceJson);

		//        JSONArray array = (JSONArray)obj;
		//	       .getJSONObject("result").getJSONObject("map").getJSONArray("entry");

		JSONObject secObject = (JSONObject)json_obj.getJSONArray("rows").get(0);
		for (int i = 0; i < rv_requests_matrix.length; i++) {

			JSONObject elementObject =(JSONObject)secObject.getJSONArray("elements").get(i);
			JSONObject durationObject=null;
			try{
				durationObject =(JSONObject)elementObject.get("duration_in_traffic");
			}
			catch(Exception e){
				durationObject =(JSONObject)elementObject.get("duration");
			}
			int time=Integer.parseInt(((String) durationObject.get("text")).replaceAll("[\\D]", ""));

			System.out.println("time "+time);
			if(i==index)
				rv_requests_matrix[index][i]=0;	
			else if(time<=20)
			{
				rv_requests_matrix[index][i]=time;
				count++;
			}
			else rv_requests_matrix[index][i]=0;

			//        System.out.println(json_obj.get("rows"));
		}
		index++;

	}


	public void RVGraphPart2(ArrayList<String> user_points) throws ClientProtocolException, IOException, InterruptedException{

		rv_request_vehicle_matrix= new int[vehicle_count][user_points.size()/2];
		//rv_request_vehicle_matrix size is y-axis-100(vehicle), x-axis-59(user)
		ArrayList<String> vehicle_req_json=apiAdapter_obj.GoogleMapsMatrixAPI_vehicle_req(vehicleLocations, user_points);

		//		System.out.println("vehicle_req_json.size() "+vehicle_req_json.size());
		//		System.out.println("user_points.size() "+user_points.size());
		//
		for (int i = 0; i < vehicle_req_json.size(); i++) {
			//59 times, 59 json, from each user as source to 100 vehicle locations as destinations	

			//			System.out.println(vehicle_req_json.get(i));
			//			break;

			JSONObject json_obj = new JSONObject(vehicle_req_json.get(i));
			////
			JSONObject secObject = (JSONObject)json_obj.getJSONArray("rows").get(0);


			////
			//			JSONObject secObject = (JSONObject)json_obj.getJSONArray("rows").get(0);
			for (int j = 0; j < vehicle_count; j++) {
				//100 times

				JSONObject elementObject =(JSONObject)secObject.getJSONArray("elements").get(j);
				JSONObject durationObject =(JSONObject)elementObject.get("duration_in_traffic");
				//
				int time=Integer.parseInt(((String) durationObject.get("text")).replaceAll("[\\D]", ""));
				//
				if(time<=20)
				{
					rv_request_vehicle_matrix[j][i]=time;
					count++;
				}
				else rv_request_vehicle_matrix[j][i]=0;

				//        System.out.println(json_obj.get("rows"));
			}


		}

	}



	public void RTVGraph(){


		//				for (int i = 0; i < rv_request_vehicle_matrix.length; i++) {
		//				System.out.println();
		//				for (int j = 0; j < rv_request_vehicle_matrix[0].length; j++) {
		//					System.out.print(rv_request_vehicle_matrix[i][j]+" ");	
		//				}
		//			}
		//
		//
		//				
		//				for (int i = 0; i < rv_requests_matrix.length; i++) {
		//					System.out.println();
		//					for (int j = 0; j < rv_requests_matrix[0].length; j++) {
		//						System.out.print(rv_requests_matrix[i][j]+" ");	
		//					}
		//				}


		ArrayList<String> possible_trips_for_every_vehicle=new ArrayList<String>();
		for (int i = 0; i < rv_request_vehicle_matrix.length; i++) {
			possible_trips_for_every_vehicle.add("");
			
			//each vehicle
			System.out.println();


					for(int user1=0;user1<rv_request_vehicle_matrix[0].length;user1++)
					{
						if(rv_request_vehicle_matrix[i][user1]!=0 )
						{
						for (int user2 = 0; user2 < rv_requests_matrix.length; user2++) {

							System.out.print("Vehicle:"+i+",User1:"+user1+","+",User2:"+user2+";");
							System.out.println(rv_requests_matrix[user1][user2]);
						}
					}

					//					if(possible_trips_for_every_vehicle.get(i).equals(""))
					//					{
					//						possible_trips_for_every_vehicle.set(i,"vehicle"+i+"user"+j);	
					//					}
					//					else
					//					{
					//						possible_trips_for_every_vehicle.set(i,
					//								possible_trips_for_every_vehicle.get(i)+";vehicle"+i+"user"+j);	
					//					}
				}

			}
//			System.out.println(possible_trips_for_every_vehicle.get(i));

		}



	



	public void print(){
		System.out.println();
		for (int i = 0; i < rv_requests_matrix.length; i++) {
			for (int j = 0; j < rv_requests_matrix.length; j++) {
				System.out.print(rv_requests_matrix[i][j]+" ");
			}
			System.out.println();

		}
		System.out.println(count);

	}
}
