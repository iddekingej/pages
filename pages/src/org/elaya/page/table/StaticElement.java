package org.elaya.page.table;



import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class StaticElement extends BuildInElement {

	public StaticElement() {
		super();
	}

	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Object l_value=getValueByName(p_data);
		themeItem.staticItem(p_writer,getDomId(),l_value);
	}

}
