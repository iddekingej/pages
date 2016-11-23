package org.elaya.page.spring;

import org.elaya.page.security.AuthorisationData;
import org.elaya.page.security.SecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringSecurityManager extends SecurityManager implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	public SpringSecurityManager() {
		// TODO Auto-generated constructor stub
	}

	protected void afterCreateSession(AuthorisationData p_authorisationData)
	{
		if(p_authorisationData instanceof ApplicationContextAware){
			((ApplicationContextAware)p_authorisationData).setApplicationContext(applicationContext);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
	}
	
}
