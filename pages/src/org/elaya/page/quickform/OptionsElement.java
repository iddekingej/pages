package org.elaya.page.quickform;

import java.util.LinkedList;

import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.Errors.ValueNotFound;
import org.elaya.page.data.Data;

abstract public class OptionsElement extends BuildInFormElement {
	private String optionVar;
	private LinkedList<OptionItem> items=new LinkedList<OptionItem>();
	
	public void addOption(String p_value,String p_text){
		items.add(new OptionItem(p_value,p_text));
	}

	public void setOptions(LinkedList<OptionItem> p_options){
		items.clear();
		items.addAll(p_options);
	}
	
	public LinkedList<OptionItem> getItems(){ return items;}

	
	public void setOptionVar(String p_listVar){
		optionVar=p_listVar;
	}

	public String getListVar(){
		return optionVar;
	}
	
	
	
	public LinkedList<OptionItem> getOptions(Data p_data) throws ValueNotFound, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvalidObjectType{
		LinkedList<OptionItem> l_items=items;
		if(optionVar.length()>0){			
			l_items=new LinkedList<OptionItem>();
			if(!p_data.containsKey(optionVar)) throw new Errors.ValueNotFound(optionVar);
			Object l_object=p_data.get(optionVar);
			if(l_object instanceof  Iterable){				
				for(Object l_item:(Iterable<?>)l_object){ 
					if((l_item instanceof OptionItem)){
						l_items.add((OptionItem)l_item);
					} else {
						throw new Errors.InvalidObjectType(l_item,"OptionItem");
					}
				}
				l_items.addAll(items);
			} else {
				new Errors.InvalidObjectType(l_object,"Iterable");
			}
		} else {
			l_items=items;
		}
		return l_items;
	}

}
