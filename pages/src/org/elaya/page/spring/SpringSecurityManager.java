package org.elaya.page.spring;

import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.elaya.page.security.SecurityManager;
import org.elaya.page.security.Session;
import org.elaya.page.security.Session.InvalidSessionData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringSecurityManager extends SecurityManager implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	protected Session createSession(ServletRequest servletRequest,ServletResponse servletResponse) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NotSerializableException, InvalidSessionData
	{
		SpringSession session=new SpringSession(servletRequest,servletResponse,applicationContext);
		session.initSession();
		return session;
	}

	@Override
	public void setApplicationContext(ApplicationContext papplicationContext) {
		applicationContext=papplicationContext;
	}
	
}
