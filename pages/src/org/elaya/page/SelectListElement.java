package org.elaya.page;
import java.util.LinkedList;

public class SelectListElement extends BuildInFormElement {
	private LinkedList<OptionItem> items=new LinkedList<OptionItem>();
	private boolean isHorizontal=false;
	
	public LinkedList<OptionItem> getitems(){ return items;}
	
	public void addOption(String p_value,String p_text){
		items.add(new OptionItem(p_value,p_text));
	}

	public void addOptions(LinkedList<OptionItem> p_options){
		items.addAll(p_options);
	}
	
	@Override
	void display(String p_value) throws Exception {
		
		themeItem.selectElement(getName(), items,p_value);
	}

}