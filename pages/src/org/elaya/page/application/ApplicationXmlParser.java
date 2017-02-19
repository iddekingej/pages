package org.elaya.page.application;

import java.io.InputStream;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLParser;

public class ApplicationXmlParser extends XMLParser {

	public ApplicationXmlParser() {
		super();
	}



	@Override
	protected InputStream openFile(String pfileName) {
		return getClass().getClassLoader().getResourceAsStream("../pages/"+pfileName);
	}

	@Override
	protected void setupConfig() {
		addConfig("application",new XMLConfig(Application.class,null,"",null));
	}

	@Override
	protected String normalizeClassName(String name) {
		return name;
	}

	@Override
	protected String getName(Object pobject) {
		return "";
	}
	
	@Override
	protected void afterCreate(Object object) throws LoadingAliasFailed, org.elaya.page.xml.XMLParserBase.XMLLoadException, IllegalArgumentException, IllegalAccessException
	{
		if(object instanceof Application){
			((Application) object).setup();
		}
	}


}
