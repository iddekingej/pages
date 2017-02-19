package org.elaya.page.spring;

import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.core.PageSession;
import org.elaya.page.core.PageSession.InvalidSessionData;
import org.elaya.page.filter.FilterManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringSecurityManager extends FilterManager implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	protected PageSession createSession(HttpServletRequest servletRequest,HttpServletResponse servletResponse) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NotSerializableException, InvalidSessionData
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
