package org.elaya.page.receiver;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.data.Dynamic.DynamicException;

public abstract class Action implements PageApplicationAware {
	private Application application;

	@Override
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	@Override
	public Application getApplication()
	{
		return application;
	}
	
	public abstract void execute(Object object,HttpServletRequest request,HttpServletResponse response,String cmd,ReceiverData data,Result result) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, DefaultDBConnectionNotSet, SQLException, DynamicException;
}
