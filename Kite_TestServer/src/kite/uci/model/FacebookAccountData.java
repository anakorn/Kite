package kite.uci.model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;




public class FacebookAccountData 
{

	private String longLivedAccessToken;

	private static final String HOST  = "graph.facebook.com";
	private static final String PATH = "/oauth/access_token";
	private static final String GRANT_TYPE = "grant_type";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String FB_EXCHANGE_TOKEN = "fb_exchange_token";

	private Map<String, String> requestParameterMap;

	private static final String EQUAL = "=";	// used for parsing access_token returns from Facebook
	private static final String AMPERSAND = "&";	// used for parsing access_token returns from Facebook

	private static final int HTTP_RESPONSE_OK = 200;

	public FacebookAccountData()
	{
		requestParameterMap = new HashMap<String, String>();
	}

	public String refreshLongLivedToken(String shortLivedAccessToken, 
			FacebookAppData appData) throws FbUserAccessTokenRequestException
			{
		requestParameterMap.put(GRANT_TYPE, FB_EXCHANGE_TOKEN);	
		requestParameterMap.put(CLIENT_ID, appData.appId());
		requestParameterMap.put(CLIENT_SECRET, appData.appSecret());
		requestParameterMap.put(FB_EXCHANGE_TOKEN, shortLivedAccessToken);


		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost(HOST).setPath(PATH);

		for (Map.Entry<String, String> parameter : requestParameterMap.entrySet())
			builder.addParameter(parameter.getKey(), parameter.getValue());


		URI uri;
		URL url;
		Scanner scannerIn = null;

		try 
		{
			uri = builder.build();
			url = new URL(uri.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			scannerIn = new Scanner(connection.getInputStream());
			String longLivedToken = null;
			longLivedToken = scannerIn.nextLine();
			System.out.println(longLivedToken);
			int beginIndex = longLivedToken.indexOf(EQUAL) + 1;
			int endIndex = longLivedToken.indexOf(AMPERSAND);
			longLivedToken = longLivedToken.substring(beginIndex, endIndex);
			System.out.println(longLivedToken);
			return longLivedToken;
			/*
			switch (response.getStatusLine().getStatusCode())
			{

			case HTTP_RESPONSE_OK:

				String longLivedToken = null;
				longLivedToken = scannerIn.nextLine();
				System.out.println(longLivedToken);
				int beginIndex = longLivedToken.indexOf(EQUAL) + 1;
				int endIndex = longLivedToken.indexOf(AMPERSAND);
				longLivedToken = longLivedToken.substring(beginIndex, endIndex);
				System.out.println(longLivedToken);
				return longLivedToken;
			default:
				StringBuilder message = new StringBuilder("");
				while (scannerIn.hasNextLine())
					message.append(scannerIn.nextLine()).append("\n");
				System.out.println(message.toString());
				throw new FbUserAccessTokenRequestException
				(String.format("An error has occurred while requesting user_acces_token \n %s", message.toString()));
			}*/
		} 
		catch (URISyntaxException e) 
		{
			throw new FbUserAccessTokenRequestException
			(String.format("An error has occurred while requesting user_acces_token \n %s", e.getMessage()));
		}
		catch (ClientProtocolException e) 
		{
			throw new FbUserAccessTokenRequestException
			(String.format("An error has occurred while requesting user_acces_token \n %s", e.getMessage()));
		} 
		catch (IOException e) 
		{
			throw new FbUserAccessTokenRequestException
			(String.format("An error has occurred while requesting user_acces_token \n %s", e.getMessage()));
		}
		finally
		{
			if (scannerIn != null)
				scannerIn.close();
		}
	}


	public String longLivedAccessToken()
	{
		return longLivedAccessToken;
	}


	public class FbUserAccessTokenRequestException extends Exception
	{

		private static final long serialVersionUID = 1L;

		public FbUserAccessTokenRequestException(String message)
		{
			super(message);
		}

	}

}
