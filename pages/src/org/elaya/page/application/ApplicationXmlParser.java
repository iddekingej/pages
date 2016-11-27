package org.elaya.page.application;

import java.io.InputStream;
import java.util.HashMap;

import org.elaya.page.Application;
import org.elaya.page.security.SecurityManager;
import org.elaya.page.xml.XmlConfig;
import org.elaya.page.xml.XmlParser;

public class ApplicationXmlParser extends XmlParser {

	public ApplicationXmlParser() {
		super();
	}

	public ApplicationXmlParser(HashMap<String, Object> p_nameIndex) {
		super(p_nameIndex);	
	}

	@Override
	protected InputStream openFile(String p_fileName) {
		return getClass().getClassLoader().getResourceAsStream("../pages/"+p_fileName);
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
	protected String normalizeClassName(String p_name) throws Exception {
		return p_name;
	}

	@Override
	protected String getName(Object p_object) {
		return "";
	}

}
