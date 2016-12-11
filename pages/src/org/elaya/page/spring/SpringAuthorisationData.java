package org.elaya.page.spring;

import org.elaya.page.security.AuthorisationData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class SpringAuthorisationData implements AuthorisationData ,ApplicationContextAware
{
	private ApplicationContext applicationContext;
	
	public SpringAuthorisationData() {
		super();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext){
		applicationContext=p_applicationContext;
	}
	
	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	


}
