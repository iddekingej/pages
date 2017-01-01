package org.elaya.page.reciever;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.elaya.page.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XmlConfig;
import org.elaya.page.xml.XmlParser;

public class RecieverParser extends XmlAppParser {

	public RecieverParser(Application application,Map<String, Object> nameIndex) {
		super(application,nameIndex);
	}

	public RecieverParser(Application application) {
		super(application);
	}

	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {
		return getApplication().getConfigStream(pfileName);
	}

	@Override
	protected XmlParser createParser() {
		return new RecieverParser(getApplication(),getNameIndex());
	}

	@Override
	protected void addConfig() {
		addConfig("reciever",new XmlConfig(Reciever.class,null,false,"",false));
		addConfig("data",new XmlConfig(Parameter.class,Parameter.class,false,"addParameter",true));
		addConfig("validator",new XmlConfig(Validator.class,null,false,"addValidator",true));
	}

@Override
	protected String getName(Object pobject) {
		return null;
	}

@Override
public String getAliasNamespace() {

	return AliasData.ALIAS_RECIEVER;
}

}
