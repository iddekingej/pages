package org.elaya.page.quickform;

import java.util.LinkedList;
import java.util.List;

import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.Errors.ValueNotFound;
import org.elaya.page.data.Data;

public abstract class OptionsElement extends BuildInFormElement {
	private String optionVar;
	private LinkedList<OptionItem> items=new LinkedList<>();
	
	public void addOption(String pvalue,String ptext){
		items.add(new OptionItem(pvalue,ptext));
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
				for(Object item:(Iterable<?>)object){ 
					if(item instanceof OptionItem){
						newItems.add((OptionItem)item);
					} else {
						throw new Errors.InvalidObjectType(item,"OptionItem");
					}
				}
				newItems.addAll(items);
			} else {
				new Errors.InvalidObjectType(object,"Iterable");
			}
		} else {
			newItems=items;
		}
		return newItems;
	}

}
