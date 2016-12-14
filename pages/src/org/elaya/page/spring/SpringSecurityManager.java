package org.elaya.page.spring;

import org.elaya.page.security.AuthorisationData;
import org.elaya.page.security.SecurityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringSecurityManager extends SecurityManager implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	protected void afterCreateSession(AuthorisationData pauthorisationData)
	{
		if(pauthorisationData instanceof ApplicationContextAware){
			((ApplicationContextAware)pauthorisationData).setApplicationContext(applicationContext);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext papplicationContext) {
		applicationContext=papplicationContext;
	}
	
}
