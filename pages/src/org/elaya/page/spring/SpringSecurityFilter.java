package org.elaya.page.spring;

import org.elaya.page.filter.SecurityFilter;
import org.elaya.page.filter.XmlSecurityParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringSecurityFilter extends SecurityFilter implements ApplicationContextAware{

	private ApplicationContext applicationContext=null;

	@Override
	public void setApplicationContext(ApplicationContext papplicationContext)  {
		applicationContext=papplicationContext;
	}
	
	@Override
	public void initParser(XmlSecurityParser pparser)
	{
		pparser.addInitializer(new ApplicationContextInitializer(applicationContext));
	}
	@Override
	public void destroy() {
		
	}





}
