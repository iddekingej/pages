package org.elaya.page.xml;

import java.lang.reflect.InvocationTargetException;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;

public abstract  class XMLAppParser extends XMLParser {
	
	private Application application;
	

	public XMLAppParser(Application papplication) {
		super();
		application=papplication;
	}
	

	public Application getApplication()
	{
		return application;
	}

	@Override
	protected void initializeObject(Object object) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, LoadingAliasFailed, org.elaya.page.xml.XMLParserBase.XMLLoadException 
	{
		if(object instanceof PageApplicationAware){
			((PageApplicationAware)object).setApplication(application);
		}
		super.initializeObject(object);
	}
	
	public abstract String getAliasNamespace();
	@Override
	protected String normalizeClassName(String pname) throws NormalizeClassNameException  {
		return application.normalizeClassName(pname,getAliasNamespace());
	}



}
