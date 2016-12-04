package org.elaya.page.spring;

import org.elaya.page.xml.Initializer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextInitializer extends Initializer implements ApplicationContextAware {
	
	ApplicationContext applicationContext;
	public ApplicationContextInitializer(ApplicationContext p_applicationContext){
		applicationContext=p_applicationContext;
	}

	public ApplicationContextInitializer()
	{
		applicationContext=null;
	}
	
	@Override
	public void processObject(Object p_object) {
		if(p_object instanceof ApplicationContextAware){
			((ApplicationContextAware)p_object).setApplicationContext(applicationContext);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;		
	}

}
