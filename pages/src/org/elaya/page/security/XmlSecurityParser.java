package org.elaya.page.security;

import java.io.InputStream;
import java.util.HashMap;

import org.elaya.page.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XmlConfig;
import org.elaya.page.xml.XmlParser;

public class XmlSecurityParser extends XmlParser {

	Application application;
	
	public XmlSecurityParser(Application p_application) {
		super();
		application=p_application;
	}

	public XmlSecurityParser(Application p_application,HashMap<String, Object> p_nameIndex) {
		super(p_nameIndex);
		application=p_application;
	}

	@Override
	protected InputStream openFile(String p_fileName) {
		return getClass().getClassLoader().getResourceAsStream("../pages/"+p_fileName);
	}

	@Override
	protected XmlParser createParser() {
		return new XmlSecurityParser(application,getNameIndex());
	}

	@Override
	protected void addConfig() {
		addConfig("security",new XmlConfig(SecurityManager.class,SecurityManager.class,false,"",false));
		addConfig("match",new XmlConfig(RequestMatcher.class,null,false,"addRequestMatcher",false));
		addConfig("action",new XmlConfig(Action.class,null,false,"addAction",false));
		addConfig("authenticator",new XmlConfig(Authenticator.class,null,false,"setAuthenticator",false));
	}

	@Override
	protected String normalizeClassName(String p_name) throws Exception {
		if(p_name.charAt(0)=='@'){
			return application.getAlias(p_name.substring(1), AliasData.alias_security);
		} 
		return p_name;
	}

	@Override
	protected String getName(Object p_object) {
		return "";
	}

}
