package org.elaya.page.application;

import java.io.InputStream;
import java.util.Map;

import org.elaya.page.xml.XmlConfig;
import org.elaya.page.xml.XmlParser;

public class ApplicationXmlParser extends XmlParser {

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
	protected XmlParser createParser() {
		return new ApplicationXmlParser(getNameIndex());
	}

	@Override
	protected void addConfig() {
		addConfig("application",new XmlConfig(Application.class,null,false,"",false));

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
