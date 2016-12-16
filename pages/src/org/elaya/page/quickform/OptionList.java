package org.elaya.page.quickform;

import java.util.LinkedList;

import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;

public class OptionList extends LinkedList<OptionItem> {
	private static final long serialVersionUID = -3808433199300497798L;
	
	public void addOption(String value,String text)
	{
		this.add(new OptionItem(value,text));
	}
	
	public void addIterable(Iterable<?> iterable) throws InvalidObjectType{
		for(Object item:(Iterable<?>)iterable){ 
			if(item instanceof OptionItem){
				this.add((OptionItem)item);
			} else {
				throw new Errors.InvalidObjectType(item,"OptionItem");
			}
		}	
	}
}
