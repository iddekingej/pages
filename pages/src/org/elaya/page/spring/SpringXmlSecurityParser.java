package org.elaya.page.spring;

import java.util.HashMap;

import org.elaya.page.application.Application;
import org.elaya.page.security.XmlSecurityParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringXmlSecurityParser extends XmlSecurityParser implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	
	public SpringXmlSecurityParser(Application p_application) {
		super(p_application);
	}

	public SpringXmlSecurityParser(Application p_application,HashMap<String, Object> p_nameIndex) {
		super(p_application,p_nameIndex);

	}

	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
	}

}
