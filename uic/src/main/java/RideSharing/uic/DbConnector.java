package RideSharing.uic;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void getTripsPerInterval() throws SQLException{

		rs = stmt.executeQuery("select *  from nytrips_firstweek_manhattan"+
				" where pickup_datetime >= \"2013-01-01 09:00:00\""+
				" and pickup_datetime < \"2013-01-01 09:02:00\"");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnNumber=rsmd.getColumnCount();
		while(rs.next()){
			for (int i = 1; i <= columnNumber; i++) {
				String columnValue = rs.getString(i);
				System.out.print(columnValue+"   ");
			}		
		}


	}




}



