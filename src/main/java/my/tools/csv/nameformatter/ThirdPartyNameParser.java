package my.tools.csv.nameformatter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONArray;
import org.json.JSONObject;



public class ThirdPartyNameParser implements NameParser{
	
	private Client client;
	private static final String APIKEY = "";
	
	public ThirdPartyNameParser() {
		
		ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.READ_TIMEOUT, 20000);
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 20000);
        PoolingHttpClientConnectionManager connectionManager =
            new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(50);
        connectionManager.setDefaultMaxPerRoute(20);
        clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
       // clientConfig.connectorProvider(new ApacheConnectorProvider());

				
        client = ClientBuilder.newClient(clientConfig);
		
		
	}
	
	
	public Name parse(String text) {
		
		
		
		String uri = "https://v2.parse.name/api/v2/names/parse?q="+encode(text);
		
		Name formattedName=null;
		JSONObject jobj=null;
		try {			
		
			formattedName =new Name();
			 formattedName.originalText=text;
			 
			WebTarget target = client.target(uri);
			Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.accept(MediaType.APPLICATION_JSON)
					                             .header("Authorization",APIKEY)
					                             .get(Response.class);
		
		if (response.getStatus() != 200) {
			  
			return null;
			}
		
		
		String output = response.readEntity(String.class);
		

		
		
		jobj = new JSONObject(output);
		
		JSONArray names= jobj.getJSONArray("name");
		
	
	    JSONObject name = names.getJSONObject(0);
		String given_name=name.getString("given_name");
		formattedName.firstName=given_name;
		String surname = name.getString("surname");
		formattedName.lastName=surname;
		String secName=name.getString("secondary_name");
		formattedName.middleName=secName;
	//	System.out.println(given_name +"  "+secName+" "+surname);    
		
		
		if(names.length()>1)  //there are 2 names
		{
			    name = names.getJSONObject(1);
				given_name=name.getString("given_name");
				formattedName.firstName_2=given_name;
				surname = name.getString("surname");
				formattedName.lastName_2=surname;
				secName=name.getString("secondary_name");
				formattedName.middleName_2=secName;		
			    System.out.println("Found 2 names!!!:"+formattedName.toString());
			
		}		
			
				
		return formattedName;

	  } catch (Exception e) {

		//e.printStackTrace();
		 // System.out.println(formattedName.firstName +"  "+formattedName.middleName+" "+formattedName.lastName);
		  
		  formattedName.organizationName=getOrganization(jobj);
		  
		  if(formattedName.isEmpty())
			  return null;
		  else		  
		     return formattedName;

	  }
	}
		
		private String getOrganization(JSONObject jobj) {
			
			
			try {
			JSONArray orgs= jobj.getJSONArray("organization");			 
			JSONObject org = orgs.getJSONObject(0);
			String orgName=org.getString("name");			
			return orgName;
			}
			catch(Exception e) {
				//e.printStackTrace();
				return null;
				
			}
			
		
		
		
		
	}

	
	 private static String encode(String url)  
     {  
               try {  
                    String encodeURL=URLEncoder.encode( url, "UTF-8" );  
                    return encodeURL;  
               } catch (UnsupportedEncodingException e) {  
                    return null;  
               }  
     }  
}
