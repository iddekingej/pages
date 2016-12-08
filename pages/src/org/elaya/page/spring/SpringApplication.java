package org.elaya.page.spring;

import javax.servlet.ServletContext;

import org.elaya.page.UiXmlParser;
import org.elaya.page.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.ServletContextAware;

public class SpringApplication extends Application implements ServletContextAware,ApplicationContextAware {
	private ServletContext servletContext;
	private ApplicationContext applicationContext;
	
	public SpringApplication() {
		super();
	}

	@Override
	protected void initUiParser(UiXmlParser p_parser)
	{
		p_parser.addInitializer(new ApplicationContextInitializer(applicationContext));
	}
	
	@Override
	public void setServletContext(ServletContext p_servletContext) {
		servletContext=p_servletContext;
		servletContext.setAttribute("application",this);
	}

	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
	}

	@Override
	public DriverManagerDataSource getDB(String p_name) {
		// TODO Auto-generated method stub
		return applicationContext.getBean(p_name,DriverManagerDataSource.class);
	}

}
