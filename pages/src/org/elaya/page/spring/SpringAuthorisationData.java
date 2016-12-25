package org.elaya.page.spring;

import org.elaya.page.security.AuthorizationData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class SpringAuthorisationData implements AuthorizationData ,ApplicationContextAware
{
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext papplicationContext){
		applicationContext=papplicationContext;
	}
	
	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	


}
