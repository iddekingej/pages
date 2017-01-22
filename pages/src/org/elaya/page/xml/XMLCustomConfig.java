package org.elaya.page.xml;

public class XMLCustomConfig extends XMLConfig {

	public XMLCustomConfig(String pdefaultSetMethod,
			Class<?> pparentClass) {
		super(null, null,  pdefaultSetMethod, pparentClass);
		setCustom(true);
	}

}
