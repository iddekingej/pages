package org.elaya.page.data;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.elaya.page.application.AliasNamespace;
import org.elaya.page.xml.XMLAppParser;
import org.elaya.page.xml.XMLConfig;
/**
 * Define a data layer by a XML File
 * This class parses a XML file to a DataLayer class.
 *
 */

public class XMLDataLayerParser extends XMLAppParser {

	@Override
	public AliasNamespace getAliasNamespace() {
		return AliasNamespace.DATALAYER;
	}

	@Override
	protected void setupConfig() {
		addConfig("datalayer",new XMLConfig(XMLDataLayer.class,XMLDataLayer.class,"",null));
		addConfig("dataitem",new XMLConfig(XMLDataItem.class,null,"addDataItem",XMLDataLayer.class));
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
