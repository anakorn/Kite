package kite.uci.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class FacebookAppData 
{
	private static final String APP_ID = "447419121997997";
	private static final String APP_SECRET = "736708a96ca4dd3048056d754dce9b16";
	private String appAccessToken;
	
	private static final String APP_ACCESS_TOKEN_REQUEST_HOST = 
			"https://graph.facebook.com/oauth/access_token?";
	
	private String accessTokenRequestUrl;
	
	// use for parsing access_token
	private static final String ACCESS_TOKEN_EQUAL = "access_token=";
	
	public FacebookAppData()
	{
		StringBuilder builder = new StringBuilder(APP_ACCESS_TOKEN_REQUEST_HOST);
		builder.append("client_id=").append(APP_ID)
				.append("&client_secret=").append(APP_SECRET)
				.append("&grant_type=client_credentials");	
		
		accessTokenRequestUrl = builder.toString();
	}
	
	public String appId()
	{
		return APP_ID;
	}
	
	public String appSecret()
	{
		return APP_SECRET;
	}
	
	public String appAccessToken() throws MalformedURLException, IOException
	{
		if (appAccessToken != null)
			return appAccessToken;
		
		return refreshAppAccessToken();
	}
	
	public String refreshAppAccessToken() throws MalformedURLException, IOException
	{
		URLConnection connection = new URL(accessTokenRequestUrl).openConnection();
		Scanner in = new Scanner(connection.getInputStream());
		String accessToken = null;
		
		if (in.hasNextLine())
			accessToken = in.nextLine();
		
		accessToken = accessToken.substring(ACCESS_TOKEN_EQUAL.length());
		System.out.println(accessToken);
		
		in.close();
		
		return accessToken;
	}
	

	
}
