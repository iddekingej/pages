package org.elaya.page.application;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.elaya.page.xml.XMLAppParser;
import org.elaya.page.xml.XMLConfig;

public class RouterParser extends XMLAppParser {

	public RouterParser(Application papplication) {
		super(papplication);
	}

	@Override
	public String getAliasNamespace() {
		return null;
	}

	@Override
	protected void setupConfig() {
		addConfig("routes",new XMLConfig(Router.class,Router.class,"",null));
		addConfig("route",new XMLConfig(Route.class,Route.class,"addRoute",Router.class));
	}

	@Override
	protected String getName(Object pobject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected InputStream openFile(String fileName) throws FileNotFoundException {
		return getApplication().getConfigStream(fileName);
	}

}
