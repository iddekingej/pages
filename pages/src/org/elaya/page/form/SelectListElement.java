package org.elaya.page.form;
import java.util.LinkedList;

public class SelectListElement extends BuildInFormElement {
	private LinkedList<OptionItem> items=new LinkedList<OptionItem>();
	public LinkedList<OptionItem> getitems(){ return items;}

	public SelectListElement()
	{
		super();
	}
	
	public void addOption(String p_value,String p_text){
		items.add(new OptionItem(p_value,p_text));
	}

	public void setOptions(LinkedList<OptionItem> p_options){
		items.clear();
		items.addAll(p_options);
	}
	
	@Override
	public void display(Object p_value) throws Exception {
		
		themeItem.selectElement(getDomId(),getName(), items,p_value);
	}

	@Override
	public String getJsType() {
		return "select";
	}

	@Override
	public String getJsClassName() {
		return "TSelectListElement";
	}
}