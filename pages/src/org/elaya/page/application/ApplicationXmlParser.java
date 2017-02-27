package org.elaya.page.application;

import java.io.InputStream;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLParser;

public class ApplicationXmlParser extends XMLParser {

	public ApplicationXmlParser() {
		super();
	}


	/**
	 * Open the file.
	 * The application configuration XML must always places in the jar file.
	 */
	@Override
	protected InputStream openFile(String pfileName) {
		return getClass().getClassLoader().getResourceAsStream(pfileName);
	}

	/**
	 * Setup the parser
	 */
	@Override
	protected void setupConfig() {
		addConfig("application",new XMLConfig(Application.class,Application.class,"",null));
		addConfig("databaseconnection",new XMLConfig(DatabaseConnection.class,DatabaseConnection.class,"addDatabaseConnection",Application.class));
	}

	@Override
	protected String normalizeClassName(String name) {
		return name;
	}

	@Override
	protected String getName(Object pobject) {
		return "";
	}
	
	/**
	 * After the application is created call Application.setup
	 */
	@Override
	protected void afterCreate(Object object) throws LoadingAliasFailed, org.elaya.page.xml.XMLParserBase.XMLLoadException, IllegalArgumentException, IllegalAccessException
	{
		if(object instanceof Application){
			((Application) object).setup();
		}
	}


}
