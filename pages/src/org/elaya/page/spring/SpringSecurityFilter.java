package org.elaya.page.spring;

import org.elaya.page.security.SecurityFilter;
import org.elaya.page.security.XmlSecurityParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringSecurityFilter extends SecurityFilter implements ApplicationContextAware{

	public SpringSecurityFilter()
	{
		super();
	}
	
	private ApplicationContext applicationContext=null;
	
	
	public void setApplicationContext(ApplicationContext p_applicationContext)  {
		applicationContext=p_applicationContext;
	}
	
	public void initParser(XmlSecurityParser p_parser)
	{
		p_parser.addInitializer(new ApplicationContextInitializer(applicationContext));
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}





}
