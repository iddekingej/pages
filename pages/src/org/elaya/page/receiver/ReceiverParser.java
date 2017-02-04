package org.elaya.page.receiver;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.elaya.page.application.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XMLAppParser;
import org.elaya.page.xml.XMLConfig;

public class ReceiverParser extends XMLAppParser {

	public ReceiverParser(Application application) {
		super(application);
	}

	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {
		return getApplication().getConfigStream(pfileName);
	}

	@Override
	protected void setupConfig() {
		addConfig("reciever",new XMLConfig(Receiver.class,null,"",null));
		addConfig("command",new XMLConfig(Command.class,Command.class,"addCommand",Receiver.class));		
		addConfig("parameter",new XMLConfig(Parameter.class,Parameter.class,"addParameter",Command.class));
		addConfig("validator",new XMLConfig(Validator.class,null,"addValidator",Command.class));
		addConfig("action",new XMLConfig(Action.class,null,"addAction",Command.class));
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
