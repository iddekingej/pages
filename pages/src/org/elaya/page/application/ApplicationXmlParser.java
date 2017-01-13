package org.elaya.page.application;

import java.io.InputStream;
import java.util.Map;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLParser;

public class ApplicationXmlParser extends XMLParser {

	public ApplicationXmlParser() {
		super();
	}

	public ApplicationXmlParser(Map<String, Object> pnameIndex) {
		super(pnameIndex);	
	}

	@Override
	protected InputStream openFile(String pfileName) {
		return getClass().getClassLoader().getResourceAsStream("../pages/"+pfileName);
	}

	@Override
	protected XMLParser createParser() {
		return new ApplicationXmlParser(getNameIndex());
	}

	@Override
	protected void setupConfig() {
		addConfig("application",new XMLConfig(Application.class,null,false,"",false));

	}

	@Override
	protected String normalizeClassName(String name) {
		return name;
	}

	@Override
	protected String getName(Object pobject) {
		return "";
	}


}
