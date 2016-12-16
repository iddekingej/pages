package org.elaya.page.quickform;

import java.util.LinkedList;
import java.util.List;

import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.Errors.ValueNotFound;
import org.elaya.page.data.Data;

public abstract class OptionsElement extends BuildInFormElement {
	private String optionVar;
	private OptionList items=new OptionList();
	
	public void addOption(String value,String text){
		items.addOption(value, text);
	}

	public void setOptions(List<OptionItem> poptions){
		items.clear();
		items.addAll(poptions);
	}
	
	public List<OptionItem> getItems(){ return items;}

	
	public void setOptionVar(String plistVar){
		optionVar=plistVar;
	}

	public String getListVar(){
		return optionVar;
	}
		
	public List<OptionItem> getOptions(Data pdata) throws ValueNotFound, NoSuchFieldException, IllegalAccessException, InvalidObjectType{
		LinkedList<OptionItem> newItems;
		if(optionVar.length()>0){			
			newItems=new LinkedList<>();
			if(!pdata.containsKey(optionVar)){
				throw new Errors.ValueNotFound(optionVar);
			}
			Object object=pdata.get(optionVar);
			if(object instanceof  Iterable){		
				items.addIterable((Iterable<?>)object);
			} else {
				new Errors.InvalidObjectType(object,"Iterable");
			}
			newItems.addAll(items);
		} else {
			newItems=items;
		}
		return newItems;
	}

}
