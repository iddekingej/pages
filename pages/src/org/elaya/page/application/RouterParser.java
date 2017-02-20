package org.elaya.page.application;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.elaya.page.xml.XMLAppParser;
import org.elaya.page.xml.XMLConfig;

/**
 * This class is a parser,that parses a xml router definition
 * to a router object
 */

public class RouterParser extends XMLAppParser {

	@Override
	public AliasNamespace getAliasNamespace() {
		return AliasNamespace.ROUTER;
	}

	/**
	 * Define how the xml is parsed
	 * There are 2 element :routes and route
	 */
	
	@Override
	protected void setupConfig() {
		addConfig("routes",new XMLConfig(Router.class,Router.class,"",null));
		addConfig("route",new XMLConfig(Route.class,Route.class,"addRoute",Router.class));
	}

	@Override
	protected String getName(Object pobject) {
		return null;
	}

	@Override
	protected InputStream openFile(String fileName) throws FileNotFoundException {
		return getApplication().getConfigStream(fileName);
	}

}
