package kite.uci;

public class FbAccessToken 
{
	// String representation of FB access token
	private String m_Token;
	
	public FbAccessToken()
	{
		m_Token = "UNDEFINED";
	}
	
	public FbAccessToken(String token)
	{
		m_Token = token;
	}
	
	public void setToken(String token)
	{
		m_Token = token; 
	}
	
	public String getToken()
	{
		return m_Token;
	}
}
