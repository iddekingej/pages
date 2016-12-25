package org.elaya.page.spring;

import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.elaya.page.security.AuthorizationData;
import org.elaya.page.security.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringSession extends Session  {

	ApplicationContext applicationContext;
	public SpringSession(ServletRequest prequest, ServletResponse presponse,ApplicationContext papplicationContext )
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
