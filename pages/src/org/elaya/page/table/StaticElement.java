package org.elaya.page.table;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class StaticElement extends BuildInElement {

	public StaticElement() {
		super();
	}

	@Override
	public void display(Writer pwriter,Data pdata) throws Exception {
		Object value=getValueByName(pdata);
		themeItem.staticItem(pwriter,getDomId(),value);
	}

}
