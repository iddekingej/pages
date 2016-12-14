package org.elaya.page.xml;

import java.util.Map;

import org.elaya.page.application.Application;

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

	public abstract String getAliasNamespace();
	@Override
	protected String normalizeClassName(String pname) throws Exception {
		return application.normalizeClassName(pname,getAliasNamespace());
	}



}
