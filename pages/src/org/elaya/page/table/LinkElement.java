package org.elaya.page.table;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.data.LinkData;

public class LinkElement extends BuildInElement {
	public static class ValueNotLinkData extends Exception{

		private static final long serialVersionUID = 4564823645576745916L;

		public ValueNotLinkData(){
			super("Value is not a descended of LinkData");
		}
	}
	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);
		Object l_value=getValueByName(p_data);
		if(l_value instanceof LinkData){
			LinkData l_linkData=(LinkData)l_value;
			themeItem.linkItem(p_writer,replaceVariables(l_data,l_linkData.getUrlText()), replaceVariables(l_data,l_linkData.getText()));
		} else {
			throw new ValueNotLinkData();
		}
		
	}



}
