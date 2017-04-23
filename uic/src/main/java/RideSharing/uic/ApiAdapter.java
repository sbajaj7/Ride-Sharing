package RideSharing.uic;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class ApiAdapter {
	
	public void method() throws ClientProtocolException, IOException{
		HttpUriRequest request = new HttpGet( "https://graphhopper.com/api/1/route?point=41.874856%2C-87.656141&point=41.870362%2C-87.652953&vehicle=car&locale=en&key=4bb8694e-ce97-4d7e-b5cc-a2ed5410f92d&calc_points=false"  );		
	
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
	    HttpEntity entity = httpResponse.getEntity();

		System.out.println(EntityUtils.toString(entity));
	}

}
	