package org.elaya.page.table;

import org.elaya.page.data.LinkData;

public class LinkElement extends BuildInElement {
	public static class ValueNotLinkData extends Exception{

		private static final long serialVersionUID = 4564823645576745916L;

		public ValueNotLinkData(){
			super("Value is not a descended of LinkData");
		}
	}
	@Override
	public void display(Object p_value) throws Exception {
		if(p_value instanceof LinkData){
			LinkData l_value=(LinkData)p_value;
			themeItem.linkItem(l_value.getUrlText(), l_value.getText());
		} else {
			throw new ValueNotLinkData();
		}
		
	}



}
