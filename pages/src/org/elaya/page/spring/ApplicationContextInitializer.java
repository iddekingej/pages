package org.elaya.page.spring;

import org.elaya.page.xml.Initializer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextInitializer implements Initializer , ApplicationContextAware {
	
	ApplicationContext applicationContext;
	public ApplicationContextInitializer(ApplicationContext papplicationContext){
		applicationContext=papplicationContext;
	}

	public ApplicationContextInitializer()
	{
		applicationContext=null;
	}
	
	@Override
	public void processObject(Object pobject) {
		if(pobject instanceof ApplicationContextAware){
			((ApplicationContextAware)pobject).setApplicationContext(applicationContext);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext papplicationContext) throws BeansException {
		applicationContext=papplicationContext;		
	}

}
