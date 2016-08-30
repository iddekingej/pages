package org.elaya.page.table;

public class StaticElement extends BuildInElement {

	public StaticElement() {
		super();
	}



	@Override
	public void display(Object p_string) throws Exception {
		themeItem.staticItem(getDomId(),p_string);
	}



}
