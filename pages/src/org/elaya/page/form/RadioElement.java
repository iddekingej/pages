package org.elaya.page.form;

import java.util.LinkedList;

import org.elaya.page.form.FormExceptions.InvalidPropertyValue;

public class RadioElement extends BuildInFormElement {
	private LinkedList<OptionItem> items=new LinkedList<OptionItem>();
	private boolean isHorizontal=false;
	
	public RadioElement()
	{
		super();
	}
	
	public LinkedList<OptionItem> getitems(){ return items;}
	
	public void addOption(String p_value,String p_text){
		items.add(new OptionItem(p_value,p_text));
	}

	public void setOptions(LinkedList<OptionItem> p_options){
		items.clear();
		items.addAll(p_options);
	}
	
	public boolean getIsHorizontal(){ return isHorizontal;}
	public void setIsHorizontal(boolean p_horizontal){ isHorizontal=p_horizontal;}
	public void setIsHorizontal(String p_horizontal) throws InvalidPropertyValue{
		if(p_horizontal.equals("true")){
			setIsHorizontal(true);
		} else if(p_horizontal.equals("false")){
			setIsHorizontal(false);
		} else {
			throw new FormExceptions.InvalidPropertyValue("Invalid property values: RadioElement.SetIsHorizontal('"+p_horizontal+"')");
		}
	}
	@Override
	public void display(Object p_value) throws Exception {
		
		themeItem.radioElement(getName(), items, isHorizontal,p_value);
	}

	@Override
	public String getJsType() {
		return "radio";
	}

	
}
