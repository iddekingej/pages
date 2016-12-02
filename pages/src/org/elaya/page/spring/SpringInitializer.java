package org.elaya.page.spring;

import org.elaya.page.Initializer;
import org.elaya.page.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringInitializer extends Initializer implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	public SpringInitializer() 
	{
	}

	@Override
	public void process(Object p_object) {
		if(p_object instanceof ApplicationContextAware){
			((ApplicationContextAware)p_object).setApplicationContext(applicationContext);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
		Application.addInitializerStatic(this);
	}

}
