package org.elaya.page.spring;

import org.elaya.page.security.SecurityFilterBackend;
import org.elaya.page.security.XmlSecurityParser;
import org.springframework.context.ApplicationContext;

public class SpringSecurityFilterBackend extends SecurityFilterBackend {
	private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext p_applicationContext){
		applicationContext=p_applicationContext;
	}
	
	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	public SpringSecurityFilterBackend() {
		super();
	}
	protected XmlSecurityParser newParser()
	{
		SpringXmlSecurityParser l_parser=new SpringXmlSecurityParser();
		l_parser.setApplicationContext(applicationContext);
		return l_parser;
	}	

}
