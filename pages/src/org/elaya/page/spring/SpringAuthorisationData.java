package org.elaya.page.spring;

import org.elaya.page.security.AuthorisationData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

abstract public class SpringAuthorisationData extends AuthorisationData implements ApplicationContextAware
{
	private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext p_applicationContext){
		applicationContext=p_applicationContext;
	}
	
	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public SpringAuthorisationData() {
		super();
	}

}
