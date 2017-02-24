package org.elaya.page.application;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class PageContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent pevent) {

	}

	@Override
	public void contextInitialized(ServletContextEvent pevent) {
		Application application;
		ServletContext 	servletContext=pevent.getServletContext();
		ApplicationXmlParser parser=new ApplicationXmlParser();
		try{
			System.out.print("Servlet init");
			application=parser.parse(servletContext.getInitParameter("applicationSetup"),Application.class);
			application.setup();
			servletContext.setAttribute("application", application);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
