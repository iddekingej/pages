package org.elaya.page.security;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import org.elaya.page.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XmlConfig;
import org.elaya.page.xml.XmlParser;

public class XmlSecurityParser extends XmlAppParser {

	
	public XmlSecurityParser(Application p_application) {
		super(p_application);
	}

	public XmlSecurityParser(Application p_application,Map<String, Object> p_nameIndex) {
		super(p_application,p_nameIndex);
	}

	@Override
	protected InputStream openFile(String p_fileName) throws FileNotFoundException {
		return getApplication().getConfigStream(p_fileName);
	}

	@Override
	protected XmlParser createParser() {
		return new XmlSecurityParser(getApplication(),getNameIndex());
	}

	@Override
	protected void addConfig() {
		addConfig("security",new XmlConfig(SecurityManager.class,SecurityManager.class,false,"",false));
		addConfig("match",new XmlConfig(RequestMatcher.class,null,false,"addRequestMatcher",false));
		addConfig("action",new XmlConfig(Action.class,null,false,"addAction",false));
		addConfig("authenticator",new XmlConfig(Authenticator.class,null,false,"setAuthenticator",false));
	}

	@Override
	protected String getName(Object p_object) {
		return "";
		
	}

	@Override
	public String getAliasNamespace() {
		return AliasData.alias_security;
	}

}
