package org.elaya.page.widget.table;

import org.elaya.page.core.Data;
import org.elaya.page.core.LinkData;
import org.elaya.page.core.Writer;

public class LinkElement extends BuildInElement {
	public static class ValueNotLinkData extends Exception{

		private static final long serialVersionUID = 4564823645576745916L;

		public ValueNotLinkData(){
			super("Value is not a descended of LinkData");
		}
	}
	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException  {
		try{			
			Object value=getValueByName(data);		
			if(value instanceof LinkData){
				LinkData linkData=(LinkData)value;
				themeItem.linkItem(pwriter,pwriter.processUrl(data,linkData.getUrlText()), pwriter.replaceVariables(data,linkData.getText()));
			} else {
				throw new ValueNotLinkData();
			}
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}



}
