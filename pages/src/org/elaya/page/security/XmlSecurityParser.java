package org.elaya.page.security;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.elaya.page.application.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XMLAppParser;
import org.elaya.page.xml.XMLConfig;

public class XmlSecurityParser extends XMLAppParser {

	
	public XmlSecurityParser(Application papplication) {
		super(papplication);
	}

	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {
		return getApplication().getConfigStream(pfileName);
	}

	@Override
	protected void setupConfig() {
		addConfig("security",new XMLConfig(SecurityManager.class,SecurityManager.class,"",null));
		addConfig("group",new XMLConfig(RequestMatcherGroup.class,RequestMatcherGroup.class,"addRequestMatcherGroup",null));
		addConfig("match",new XMLConfig(RequestMatcher.class,null,"addRequestMatcher",HasRequestMatchers.class));
		addConfig("action",new XMLConfig(Action.class,null,"addAction",null));
		addConfig("authenticator",new XMLConfig(Authenticator.class,null,"setAuthenticator",null));
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
