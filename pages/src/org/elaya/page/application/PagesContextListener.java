package org.elaya.page.application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class PagesContextListener implements ServletContextListener {

	public PagesContextListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void contextDestroyed(ServletContextEvent p_event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent p_event) {
		ApplicationXmlParser l_parser=new ApplicationXmlParser();
		Object l_object;
		try {
			l_object=l_parser.parse("application.xml");
			((Application)l_object).setServletContext(p_event.getServletContext());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if(l_object instanceof Application){
			p_event.getServletContext().setAttribute("application", l_object);
		} else {
			//TODO handeling when setting up application failes
			System.out.println("Setting application failed");
		}
	}

}
