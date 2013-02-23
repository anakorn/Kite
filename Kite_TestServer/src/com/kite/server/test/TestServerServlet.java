package com.kite.server.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class TestServerServlet extends HttpServlet {
	
	static final String test_file0 = "data/testdata0.json";	
	static final String test_file1 = "data/testdata1.json";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String type = req.getParameter("type");
		resp.setContentType("application/json");
		if(type != null) {
			if(type.equals("0")) {
				resp.getWriter().print(readTestFile(test_file0));
			} else if(type.equals("1")) {
				resp.getWriter().print(readTestFile(test_file1));
			}
		}
		
			
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
	
	private String readTestFile(String file) {
		File f = new File(file);
		FileInputStream fis = null;
		byte[] b = new byte[(int)f.length()];
		char[] str = new char[(int) f.length()]; 

		try {
			fis = new FileInputStream(f);
			fis.read(b);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		for (int i = 0; i < b.length; i++) str[i] = (char) b[i]; 
		return String.valueOf(str);
	}
}


