package org.elaya.page.spring;

import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.core.AuthorizationData;
import org.elaya.page.core.PageSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringSession extends PageSession  {

	ApplicationContext applicationContext;
	public SpringSession(HttpServletRequest prequest, HttpServletResponse presponse,ApplicationContext papplicationContext )
			throws NotSerializableException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, ClassNotFoundException, InvalidSessionData {
		super(prequest, presponse);
		Objects.requireNonNull(papplicationContext);
		applicationContext=papplicationContext;
	}

	@Override
	protected void afterCreateSession(AuthorizationData authorisationData){
		if(authorisationData instanceof ApplicationContextAware){
			((ApplicationContextAware) authorisationData).setApplicationContext(applicationContext);
		}
	}
}
