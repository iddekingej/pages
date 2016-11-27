package org.elaya.page.spring;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.Filter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringSecurityFilter implements Filter,ApplicationContextAware{

	public SpringSecurityFilter()
	{
		super();
	}
	
	private ApplicationContext applicationContext=null;
	private SpringSecurityFilterBackend backend=new SpringSecurityFilterBackend(); 
	
	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	

	public void setApplicationContext(ApplicationContext p_applicationContext)  {
		applicationContext=p_applicationContext;
	}
	
	@Override
	public void init(FilterConfig p_config) throws ServletException{
		backend.setApplicationContext(applicationContext);
		backend.init(p_config,p_config.getInitParameter("securityConfigFile"));
	}

	@Override
	public void doFilter(ServletRequest p_request, ServletResponse p_response, FilterChain p_chain)
			throws IOException, ServletException {
		
		backend.doFilter(p_request, p_response, p_chain); 
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	


}
