package org.elaya.page.spring;

import java.util.HashMap;

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
	
	
	public SpringXmlSecurityParser() {
	}

	public SpringXmlSecurityParser(HashMap<String, Object> p_nameIndex) {
		super(p_nameIndex);

	}

	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
	}
	

	
	protected void AfterCreate(Object p_parent) throws Exception
	{	
			if(p_parent instanceof ApplicationContextAware){
				 ((ApplicationContextAware)p_parent).setApplicationContext(applicationContext);
			}
			super.AfterCreate(p_parent);
	}

}
