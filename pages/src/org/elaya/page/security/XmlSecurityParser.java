package org.elaya.page.security;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import org.elaya.page.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLParser;

public class XmlSecurityParser extends XmlAppParser {

	
	public XmlSecurityParser(Application papplication) {
		super(papplication);
	}

	public XmlSecurityParser(Application papplication,Map<String, Object> pnameIndex) {
		super(papplication,pnameIndex);
	}

	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {
		return getApplication().getConfigStream(pfileName);
	}

	@Override
	protected XMLParser createParser() {
		return new XmlSecurityParser(getApplication(),getNameIndex());
	}

	@Override
	protected void addConfig() {
		addConfig("security",new XMLConfig(SecurityManager.class,SecurityManager.class,false,"",false));
		addConfig("match",new XMLConfig(RequestMatcher.class,null,false,"addRequestMatcher",false));
		addConfig("action",new XMLConfig(Action.class,null,false,"addAction",false));
		addConfig("authenticator",new XMLConfig(Authenticator.class,null,false,"setAuthenticator",false));
	}

	@Override
	protected String getName(Object pobject) {
		return "";
		
	}

	@Override
	public String getAliasNamespace() {
		return AliasData.ALIAS_SECURITY;
	}

}
