package org.elaya.page.listmenu;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;
import org.xml.sax.SAXException;


public class DynamicLinkMenuItem extends BuildinListMenuItem {
	
	
	
	public DynamicLinkMenuItem() {
		super();
	}
	
	
	@Override
	public String getJsClassName() {
		return "TDynamicLinkListMenuItem";
	}

	private void displayItem(String domId,Writer writer,Object selectedValue,Object value,Data data) throws IOException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, ReplaceVarException
	{
		int cnt=0;
		for(Object object:(Iterable<?>)value){
			if(object instanceof LinkMenuData){
				LinkMenuData menuData=(LinkMenuData)object;
				if( (menuData.getId() != null)? menuData.getId().equals(selectedValue):false){
					themeItem.preItemSelected(writer,domId+"_"+cnt);
				} else {
					themeItem.preItem(writer,domId+"_"+cnt);
				}
				themeItem.linkItem(writer,menuData.getText(),writer.processUrl(data,menuData.getUrlText()),writer.processUrl(data,menuData.getDelUrl()),writer.processUrl(data,menuData.getEditUrl()),menuData.getId());
				themeItem.postItem(writer);
				cnt++;
			}
		}
	}
	@Override
	public void displayElement(int id,Writer writer, Data data) throws org.elaya.page.Element.DisplayException  {
		try{
			Object value=this.getValueByName(data);
			Object selectedValue=null;
			if(getParent() instanceof ListMenu){
				String selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
				selectedValue=data.get(selectionVariable);

			}
			if(value instanceof Iterable){
				displayItem(getDomId(id),writer,selectedValue,value,data);
			}
		
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
}
