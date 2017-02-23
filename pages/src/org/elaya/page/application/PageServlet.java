package org.elaya.page.application;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	Application application;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		ApplicationXmlParser parser=new ApplicationXmlParser();
		try{
			application=parser.parse(config.getInitParameter("applicationSetup"),Application.class);
			application.setup();
		}catch(Exception e){
			throw new ServletException(e);
		}
	}
	
	@Override
	public void service(HttpServletRequest prequest,HttpServletResponse presponse) throws IOException {
		try{
			application.routeRequest(prequest, presponse);
		}catch(Exception e){
			throw new IOException(e);
		}
	}

}
