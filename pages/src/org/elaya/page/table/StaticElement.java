package org.elaya.page.table;

import org.elaya.page.data.Data;

public class StaticElement extends BuildInElement {

	public StaticElement() {
		super();
	}



	@Override
	public void display(Data p_data) throws Exception {
		Object l_value=getValueByName(p_data);
		themeItem.staticItem(getDomId(),l_value);
	}



}
