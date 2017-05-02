package RideSharing.uic;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.Properties;

public class DbConnector {

	MysqlDataSource mysqlDS ; 
	Connection con;
	Statement stmt ;
	ResultSet rs;

	DbConnector(){



		mysqlDS = new MysqlDataSource(); 
		mysqlDS.setURL("jdbc:mysql://localhost:3306/ridesharing");
		mysqlDS.setUser("581User");
		mysqlDS.setPassword("password");

		try {
			con = mysqlDS.getConnection();
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	public ArrayList<ArrayList<String>> getTripsPerInterval(String pool_size) throws SQLException{


		int range_end=30+((Integer.parseInt(pool_size)-30)/6);
		System.out.println("range_end "+range_end);
		rs = stmt.executeQuery("select medallion,pickup_datetime,pickup_longitude , pickup_latitude , dropoff_longitude , dropoff_latitude,medallion"+
				"  from nytrips_firstweek_manhattan"+
				" where pickup_datetime >= \"2013-01-01 09:01:00\""+
				" and pickup_datetime < \"2013-01-01 09:02:00\""); 
//		+"limit 5");
		ResultSetMetaData rsmd = rs.getMetaData();
		ArrayList<ArrayList<String>> trips_interval = new ArrayList<ArrayList<String>>();
		int columnNumber=rsmd.getColumnCount();
		int index=0;
		while(rs.next()){
			ArrayList<String> newArray = new ArrayList<String>(6);

			for (int i = 1; i <= columnNumber; i++) {
				String columnValue = rs.getString(i);
				//				System.out.print(columnValue+"   ");
				newArray.add(i-1,columnValue);
			}
			trips_interval.add(index, newArray);
			index++;
		}

		//		for (int i = 0; i < trips_interval.size(); i++) {
		//			ArrayList<String> newArray = trips_interval.get(i);
		//
		//			for (int j = 0; j < 6; j++) {
		//				System.out.println(newArray.get(j)+" ");
		//				
		//			}
		//		}
		return trips_interval;

	}
		public ArrayList<String> getDenseAreas() throws SQLException{


		rs = stmt.executeQuery("select  count(*) as count, (TRUNCATE(pickup_longitude,2)-0.005555)  as   pickup_longitude "+
				", (TRUNCATE(pickup_latitude,2)+0.005555) as pickup_latitude "+
				"from nytrips_firstweek_manhattan  "+
				" "+
				"where pickup_datetime >= \"2013-01-01 09:00:00\" "+
				"and pickup_datetime < \"2013-01-01 09:20:00\" "+
				"group by TRUNCATE(pickup_longitude,2)   "+
				", TRUNCATE(pickup_latitude,2) "+
				" "+
				"order by count desc "+
				"limit 50; ");

		ArrayList<String> denseAreas = new ArrayList<String>(5);

		while(rs.next()){

			
			denseAreas.add(rs.getString(3));
			denseAreas.add(rs.getString(2));
		}

		//		for (int i = 0; i < trips_interval.size(); i++) {
		//			ArrayList<String> newArray = trips_interval.get(i);
		//
		//			for (int j = 0; j < 6; j++) {
		//				System.out.println(newArray.get(j)+" ");
		//				
		//			}
		//		}

	return denseAreas;	
	}




}



