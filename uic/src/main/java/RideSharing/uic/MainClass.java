package RideSharing.uic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

/**
 * Hello world!
 *
 */
public class MainClass 
{
    public static void main( String[] args ) throws SQLException, ClientProtocolException, IOException
    {
        System.out.println( "This is me signing in!" );
        System.out.println( "Hii" );
        
//        System.out.println(new Date().toString());
//        DbConnector db_obj=new DbConnector();
//        db_obj.getTripsPerInterval();
//        System.out.println(new Date().toString());
        
        ApiAdapter gh_obj=new ApiAdapter();
        gh_obj.method();

    }
}
