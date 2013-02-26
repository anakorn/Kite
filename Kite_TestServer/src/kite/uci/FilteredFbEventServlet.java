package kite.uci;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilteredFbEventServlet extends HttpServlet 
{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	
			{
				PrintWriter writer = resp.getWriter();
				writer.append("Hi, there. name is FilteredFbEventServlet");
			}
}
