package org.elaya.page.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;

public abstract  class XmlAppParser extends XmlParser {
	
	private Application application;
	
	
	public XmlAppParser(Application papplication, Map<String, Object> pnameIndex) {
		super(pnameIndex);
		application=papplication;
	}

	public XmlAppParser(Application papplication) {
		super();
		application=papplication;
	}
	

	public Application getApplication()
	{
		return application;
	}

	@Override
	protected void setupObject(Object object) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		if(object instanceof PageApplicationAware){
			((PageApplicationAware)object).setApplication(application);
		}
		super.setupObject(object);
	}
	
	public abstract String getAliasNamespace();
	@Override
	protected String normalizeClassName(String pname) throws NormalizeClassNameException  {
		return application.normalizeClassName(pname,getAliasNamespace());
	}



}