package org.elaya.page.receiver;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.Dynamic.DynamicException;
import org.elaya.page.application.PageApplicationAware;

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
	
	public abstract void execute(Object object,PageSession psession,String cmd,ReceiverData data,Result result) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, DefaultDBConnectionNotSet, SQLException, DynamicException;
}
