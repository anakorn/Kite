package kite.uci;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kite.uci.event.query.FBEventTable;
import kite.uci.event.query.FbEventAttendingFilterQuery;
import kite.uci.event.query.FbEventDescriptionFilterQUery;
import kite.uci.event.query.FbEventFilterQueryException;
import kite.uci.event.query.FbEventHostFilterQuery;
import kite.uci.event.query.FbEventLocationFilterQuery;
import kite.uci.event.query.FbEventNameFilterQuery;
import kite.uci.event.query.FbEventTimeFilterQuery;
import kite.uci.event.query.FbNestedQueryBuilder;
import kite.uci.event.query.FbQueryBuilder;
import kite.uci.event.query.FbQueryBuilder.WhereClause;
import kite.uci.event.query.OpCode;
import kite.uci.model.FacebookAppData;






@SuppressWarnings("serial")
public class DefaultFbEventServlet extends HttpServlet 
{

	public static final String FB_WEB_API_HOST = "graph.facebook.com";
	public static final String FB_FQL_PATH = "/fql";
	public static final String FB_WEB_API_CALL_SHEME = "https";
	public static final String FB_FQL_QUERY_REQUEST_KEY = "q";


	public static final String UAT_SESSION_ATTR = "user_acces_token";
	public static final String UAT_EXPIRE_DATE_SESSION_ATTR = "user_access_token_expire_date";
	
	public String LONG_LIVED_ACCESS_CODE = "AAAGW7OHVGK0BACF50aQ4xtVqclZArbyE6RZCo3t8oYnQI0tMRFeDotaq8FQI9N7e41iaKZAayOyxE7wVafVqjUokCfUu9yeXbmWZCWEtIwZDZD";


	public static URI queryURI;
	static
	{

		/*queryURI = new URIBuilder();
		queryURI.setHost(FB_WEB_API_HOST);
		queryURI.setPath(FB_FQL_PATH);
		queryURI.setScheme(FB_WEB_API_CALL_SHEME);*/
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{

		StringBuilder builder = new StringBuilder();
		builder.append("https://graph.facebook.com/fql?q=");
		try 
		{
			builder.append(URLEncoder.encode(getQuery(req), "UTF-8"));
			builder.append("&access_token=").append(LONG_LIVED_ACCESS_CODE);
			System.out.println(String.format("query built: %s", builder.toString()));
			URLConnection connection = new URL(builder.toString()).openConnection();
			resp.setContentType("application/json");
			
			Scanner in = new Scanner (connection.getInputStream());
			while (in.hasNextLine())
				resp.getWriter().print(in.nextLine());
			
			in.close();
		}
		catch (FbEventFilterQueryException | UnsupportedFbEventReqParam e) 
		{
			try 
			{
				resp.getWriter().print(e.getMessage());
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}	// bad request
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) 
		{
			// TODO Auto-generated catch block
			resp.getWriter().print(e.getMessage());
		} 
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*
		try 
		{
			writer = resp.getWriter();
			if (req.getSession().getAttribute(UAT_SESSION_ATTR) != null)
			{
				writer.append(String.format("attribute in session: %s", req.getSession().getAttribute(UAT_SESSION_ATTR)));
				// retrieve events 
			}
			else if (req.getParameter("code")  == null || req.getParameter("code").isEmpty())
				resp.sendRedirect(FBLoginRedirectUriBuilder.getFbLoginUri().toString());
			else 
			{
				String code = req.getParameter("code");
				writer.append(String.format("code: %s \n", code));
				URI codeForUAT = null;
				codeForUAT = FbCodeForUATUriBuilder.getFbUATExchangeUri(code);
				URL url = new URL (codeForUAT.toString());
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				Scanner in = new Scanner(connection.getInputStream());
				if (in.hasNextLine())
					req.getSession().setAttribute(UAT_SESSION_ATTR, in.nextLine());
				else
		
				in.close();
				//saveUAT(code, req.getSession());
				//resp.sendRedirect("http://kite-uci.appspot.com/index.html");
			}
		} 
		catch (IOException e) 
		{
			writer.append(String.format("An error has occured. \n Error Message:", e.getMessage()));
		} 
		catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
	}

	public String retrieveUAT(HttpServletRequest req) throws FbEventServletException
	{
		HttpSession session = req.getSession(false);
		if (session == null)
			throw new FbEventServletException(String.format("No session associated with request: %s", 
					req.getRequestURI().toString()));
		else if (session.getAttribute(UAT_SESSION_ATTR) == null)
			throw new FbEventServletException(String.format("Could not retrieve UAT from session which is associated"
					+ " with the request: ", 
					req.getRequestURI().toString()));
		return (String) session.getAttribute(UAT_SESSION_ATTR);

	}


	public String getQuery(HttpServletRequest req) throws FbEventFilterQueryException, UnsupportedFbEventReqParam
	{
		FbQueryBuilder qBuilder = new FbQueryBuilder(FBEventTable.TABLE_NAME);
		qBuilder.appendSelectColumn(FBEventTable.NAME);
		qBuilder.appendSelectColumn(FBEventTable.HOST);
		qBuilder.appendSelectColumn(FBEventTable.DESCRIPTION);
		qBuilder.appendSelectColumn(FBEventTable.LOCATION);
		qBuilder.appendSelectColumn(FBEventTable.START_TIME);
		qBuilder.appendSelectColumn(FBEventTable.END_TIME);
		qBuilder.appendSelectColumn(FBEventTable.ATTENDING_COUNT);
		qBuilder.appendSelectColumn(FBEventTable.PICTURE);
		qBuilder.appendSelectColumn(FBEventTable.ALL_MEMBER_COUNT);

		FbQueryBuilder eIdQueryBuilder = new FbNestedQueryBuilder("event_member");
		eIdQueryBuilder.appendSelectColumn(FBEventTable.ID);
		eIdQueryBuilder.appendWhereClause(eIdQueryBuilder.new WhereClause("uid", OpCode.EQUAL, "me()"));

		WhereClause defaultWClause = qBuilder.new 
				WhereClause(FBEventTable.ID, OpCode.IN, 
						eIdQueryBuilder.buildQuery());
		qBuilder.appendWhereClause(defaultWClause);

		if (req.getParameterMap().size() > 0)
		{
			for (WhereClause wClause : parseWhereClauses(req, qBuilder))
				qBuilder.appendWhereClause(wClause);
		}


		return qBuilder.buildQuery();

	}

	public List<WhereClause> parseWhereClauses(HttpServletRequest request, FbQueryBuilder qBuilder) 
			throws FbEventFilterQueryException, UnsupportedFbEventReqParam
			{

		List<WhereClause> wClauses = new ArrayList<WhereClause>();

		for (Object param : request.getParameterMap().keySet())
		{
			String value = request.getParameter((String) param);
			if (value.isEmpty())
				continue;
			
			switch ((String) param)
			{
			case FbEventReqSupportedParam.ATTENDING_COUNT:
				String equality = request.getParameter(FbEventReqSupportedParam.ATTENDING_EQUALITY);
				FbEventAttendingFilterQuery aFq = new FbEventAttendingFilterQuery();
				aFq.setAttendingEquality(aFq.new FbEventAttendingEquality(equality));
				wClauses.add(qBuilder.new WhereClause(aFq.getFilteredString(value)));
				break;

			case FbEventReqSupportedParam.NAME:
				wClauses.add(
						qBuilder.new WhereClause(
								new FbEventNameFilterQuery().getFilteredString(value)));
				break;
			case FbEventReqSupportedParam.DESCRIPTION:
				wClauses.add(
						qBuilder.new WhereClause(
								new FbEventDescriptionFilterQUery().getFilteredString(value)));
				break;
			case FbEventReqSupportedParam.LOCATION:
				wClauses.add(
						qBuilder.new WhereClause(
								new FbEventLocationFilterQuery().getFilteredString(value)));
				break;
			case FbEventReqSupportedParam.TIME:
				wClauses.add(
						qBuilder.new WhereClause(
								new FbEventTimeFilterQuery().getFilteredString(value)));
				break;
			case FbEventReqSupportedParam.HOST:
				wClauses.add(
						qBuilder.new WhereClause(
								new FbEventHostFilterQuery().getFilteredString(value)));
				break;
			case FbEventReqSupportedParam.ATTENDING_EQUALITY:
				break;
			default:
				throw new UnsupportedFbEventReqParam(String.format("Parameter: %s is unsupported", param));
			}
		}

		return wClauses;
			}


	public boolean hasAccessToken(HttpServletRequest req) throws FbEventServletException
	{
		String code = req.getParameter("code");
		if (code != null)
		{
			saveUAT(code, req.getSession());
			return true;
		}

		return false;
	}

	public void saveUAT(String code, HttpSession session) throws FbEventServletException
	{
		try
		{	
			URI codeForUAT = null;
			codeForUAT = FbCodeForUATUriBuilder.getFbUATExchangeUri(code);
			URL url = new URL (codeForUAT.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			Scanner in = new Scanner(connection.getInputStream());
			if (in.hasNextLine())
			{
				session.setAttribute(UAT_SESSION_ATTR, in.nextLine());
				/*StringBuilder uTokenBuilder = new StringBuilder();
				uTokenBuilder.append(in.nextLine());

				String uat = uTokenBuilder
						.substring(uTokenBuilder.indexOf("=" + 1),
						uTokenBuilder.indexOf("&"));

				String expireDate = uat.substring
						(uTokenBuilder.lastIndexOf("=" + 1), uTokenBuilder.length()); 
				session.setAttribute(UAT_SESSION_ATTR, uat);
				session.setAttribute(UAT_EXPIRE_DATE_SESSION_ATTR, expireDate);*/
			}
			in.close();
		} 
		catch (URISyntaxException | IOException e)
		{
			throw new FbEventServletException(String.format
					("An eror occurs while processing Facebook user_access_token \n"
							+ "Error message: %s", e.getMessage())); 
		}

	}




	private static class FBLoginRedirectUriBuilder
	{
		public static final String FB_LOGIN_HOST = "facebook.com";
		public static final String FB_LOGIN_PATH = "/dialog/oauth";
		private static URI redirectUri;
		private static final String REDIRECT_HOST = "kite-uci.appspot.com";
		private static final String REDIRECT_PATH = "/DefaultFbEventServlet	";

		static
		{
			try 
			{
				redirectUri = new URI("http://kite-uci.appspot.com/DefaultFbEventServlet");
		
			} 
			catch (URISyntaxException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*	static
		{

			URIBuilder builder = new URIBuilder();
			builder.setScheme("http");
			builder.setHost(REDIRECT_HOST);
			builder.setPath(REDIRECT_PATH);
			try 
			{
				redirectUri = builder.build();
			} 
			catch (URISyntaxException e) 
			{
				e.printStackTrace();
			}
		}*/

		public static URI getFbLoginUri() throws 
		UnsupportedEncodingException,
		URISyntaxException
		{
			FacebookAppData appData = new FacebookAppData();

			/*URIBuilder builder = new URIBuilder();
			try {
				builder.setScheme("https")
				.setHost(FB_LOGIN_HOST)
				.setPath(FB_LOGIN_PATH)
				.addParameter("client_id", appData.appId())
				.addParameter("redirect_uri", redirectUri.toString())
				.addParameter("state", getMD5HashedState());
			} 
			catch (NoSuchAlgorithmException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 */
			try 
			{
				return new URI(String.format("https://facebook.com/dialog/oauth?"
						+ "client_id=%s"
						+ "&redirect_uri=%s"
						+ "&state=&s"
						+ "&scope=&s",

						appData.appId(), redirectUri.toString(),
						getMD5HashedState(), "user_events"));
			} 
			catch (NoSuchAlgorithmException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

			return null;
		}


		private static String getMD5HashedState()
				throws UnsupportedEncodingException, NoSuchAlgorithmException
				{
			Random r = new Random();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < 9; i++)
				builder.append(r.nextInt(10));

			byte[] bytesOfMessage = builder.toString().getBytes("UTF-8");

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);


			return new String(thedigest);
				}
	}

	private static class FbCodeForUATUriBuilder
	{
		public static final String FB_CODE_UAT_ECHANGE_HOST = "graph.facebook.com";
		public static final String FB_CODE_UAT_EXCHANGE_PATH = "/oauth/access_token";
		private static URI redirectUri;
		private static final String REDIRECT_HOST = "kite-uci.appspot.com";
		private static final String REDIRECT_PATH = "/kite";

		static
		{
			try 
			{
				redirectUri = new URI("http://kite-uci.appspot.com/DefaultFbEventServlet");
			} 
			catch (URISyntaxException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*URIBuilder builder = new URIBuilder();
			builder.setScheme("http");
			builder.setHost(REDIRECT_HOST);
			builder.setPath(REDIRECT_PATH);
			try 
			{
				redirectUri = builder.build();
			} 
			catch (URISyntaxException e) 
			{
				e.printStackTrace();
			}*/
		}


		public static URI getFbUATExchangeUri(String code) throws 
		UnsupportedEncodingException,
		URISyntaxException
		{
			FacebookAppData appData = new FacebookAppData();
			
			String parameter = String.format("client_id=%s"
					+ "&client_secret=%s"
					+ "&redirect_uri=%s"
					+ "&code=%s", 
					appData.appId(),
					appData.appSecret(),
					redirectUri.toString(),
					code);
			
			return new URI(String.format("https://graph.facebook.com/oauth/access_token?"
					+ URLEncoder.encode(parameter, "UTF-8")));
			/*URIBuilder builder = new URIBuilder();
			builder.setScheme("https")
			.setHost(FB_CODE_UAT_ECHANGE_HOST)
			.setPath(FB_CODE_UAT_EXCHANGE_PATH)
			.addParameter("client_id", appData.appId())
			.addParameter("client_secret", appData.toString())
			.addParameter("redirect_uri", redirectUri.toString())
			.addParameter("code", code);*/


		}
	}

	public static class FbEventReqSupportedParam
	{
		//----------------------- supported parameters-----------------------
		public static final String NAME = "name";
		public static final String DESCRIPTION = "desc";
		public static final String LOCATION = "loc";
		public static final String TIME = "time";
		public static final String HOST = "host";
		public static final String ATTENDING_COUNT = "attn_count";
		public static final String ATTENDING_EQUALITY = "attn";

		//----------------------support values-------------------------------
		public static final String CURRENT = "now";
		public static final String FUTURE = "future";
		public static final String PAST = "past";
		public static final String GREATER = "gt";
		public static final String LESS = "lt";
		public static final String EQUAL = "eq";
		public static final String GREATER_EQUAL = "gte";
		public static final String LESS_EQUAL = "lte";
	}

	public class UnsupportedFbEventReqParam extends Exception
	{
		public UnsupportedFbEventReqParam(String message)
		{
			super (message);
		}
	}

	public class FbEventServletException extends Exception
	{
		public FbEventServletException(String message)
		{
			super(message);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	{
	//	resp.setContentType("application/json");
		
		
	}
}
