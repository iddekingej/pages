package org.elaya.page.spring;

import org.elaya.page.core.AuthorizationData;
import org.elaya.page.security.AuthenticateAction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringAuthenticateAction extends AuthenticateAction implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	protected void afterCreateSession(AuthorizationData pauthorisationData)
	{
		if(pauthorisationData instanceof ApplicationContextAware){
			((ApplicationContextAware)pauthorisationData).setApplicationContext(applicationContext);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext papplicationContext) throws BeansException {
		applicationContext=papplicationContext; 		
	}	
	
}
