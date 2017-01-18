package org.elaya.page.spring;

import javax.servlet.ServletContext;
import org.elaya.page.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.ServletContextAware;

public class SpringApplication extends Application implements ServletContextAware,ApplicationContextAware,InitializingBean {
	private ServletContext servletContext;
	private ApplicationContext applicationContext;
	
	
	
	@Override
	protected void initPageLoader()
	{
		setPageLoader(new SpringPageLoader(applicationContext));
	}
	
	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	@Override
	public void setServletContext(ServletContext pservletContext) {
		servletContext=pservletContext;
		servletContext.setAttribute("application",this);
	}
	
	public ServletContext getServletContext(){
		return servletContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext papplicationContext) throws BeansException {
		applicationContext=papplicationContext;
	}

	@Override
	public DriverManagerDataSource getDB(String pname) {
		return applicationContext.getBean(pname,DriverManagerDataSource.class);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setup();
	}

}
