package org.elaya.page.receiver;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.elaya.page.application.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLParser;

public class ReceiverParser extends XmlAppParser {

	public ReceiverParser(Application application,Map<String, Object> nameIndex) {
		super(application,nameIndex);
	}

	public ReceiverParser(Application application) {
		super(application);
	}

	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {
		return getApplication().getConfigStream(pfileName);
	}

	@Override
	protected XMLParser createParser() {
		return new ReceiverParser(getApplication(),getNameIndex());
	}

	@Override
	protected void setupConfig() {
		addConfig("reciever",new XMLConfig(Receiver.class,null,"",null));
		addConfig("data",new XMLConfig(Parameter.class,Parameter.class,"addParameter",Receiver.class));
		addConfig("validator",new XMLConfig(Validator.class,null,"addValidator",Receiver.class));
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
