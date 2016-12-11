package org.elaya.page.xml;

import java.util.Map;

import org.elaya.page.application.Application;

public abstract  class XmlAppParser extends XmlParser {
	
	private Application application;
	
	
	public XmlAppParser(Application p_application, Map<String, Object> p_nameIndex) {
		super(p_nameIndex);
		application=p_application;
	}

	public XmlAppParser(Application p_application) {
		super();
		application=p_application;
	}
	

	public Application getApplication()
	{
		return application;
	}

	public abstract String getAliasNamespace();
	@Override
	protected String normalizeClassName(String p_name) throws Exception {
		return application.normalizeClassName(p_name,getAliasNamespace());
	}



}
