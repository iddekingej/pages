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
	public void display(Writer pwriter,Data pdata) throws Exception {
		Data data=getData(pdata);
		Object value=getValueByName(pdata);		
		if(value instanceof LinkData){
			LinkData linkData=(LinkData)value;
			themeItem.linkItem(pwriter,replaceVariables(data,linkData.getUrlText()), replaceVariables(data,linkData.getText()));
		} else {
			throw new ValueNotLinkData();
		}
		
	}



}
