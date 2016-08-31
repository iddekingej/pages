package org.elaya.page.table;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.PageElement;
import org.elaya.page.data.DynamicData;

public class Table extends PageElement<TableThemeItem> {

	public static class DataIsNotIterable extends Exception{

		private static final long serialVersionUID = 1L;
		
		public DataIsNotIterable(String p_name){
			super("Data '"+p_name+"' is not iterable");
		}
	}

	public static class NotADynamicData extends Exception{

		private static final long serialVersionUID = 6677143410727926090L;

		public NotADynamicData(String p_name){
			super("Class('"+p_name+" doesn't implement DynamicData");
		}
	}
	
	private String fieldName;
	
	public void setFieldName(String p_fieldName){
		fieldName=p_fieldName;
	}
	
	public String getFieldName(){
		return fieldName;
	}
	
	public void displayRow(DynamicData p_row) throws Exception
	{
		Object     l_value;
		themeItem.rowHeader();
		for(Element<?> l_element:getElements()){
			if(!p_row.containsKey(l_element.getName())){
				throw new Errors.ValueNotFound(fieldName+"."+l_element.getName());
			}
			l_value=p_row.get(l_element.getName());
			themeItem.itemHeader();
			l_element.display(l_value);
			themeItem.itemFooter();
		}
		themeItem.rowFooter();	
	}
	@Override
	public void display() throws Exception {
		
		Iterable<?> l_list;
		Object      l_abstractList;
		DynamicData l_row;
		if(fieldName.length()==0){
			throw new Errors.PropertyNotSet("fieldName");
		}
		if(!getData().contains(fieldName)){
			throw new Errors.ValueNotFound(fieldName);
		}
		l_abstractList=getData().get(fieldName);
		if(l_abstractList instanceof Iterable){
			l_list=(Iterable<?>)l_abstractList;
			themeItem.tableHeader("");
			themeItem.titleHeader();
			for(Element <?>l_element:getElements()){
				String l_title;
				if(l_element instanceof TableElement){
					l_title=((TableElement<?>)l_element).getTitle();
				} else {
					l_title="";
				}
				themeItem.title(l_title);
			}
			themeItem.titleFooter();
			for(Object l_abstractRow:l_list){
				if(l_abstractRow instanceof DynamicData){
					l_row=(DynamicData)l_abstractRow;
					displayRow(l_row);
				} else {
					throw new NotADynamicData(l_abstractRow.getClass().getName());
				}
			}
			themeItem.tableFooter();
		} else {
			throw new DataIsNotIterable(fieldName);
		}
		
	}

	@Override
	public String getThemeName() {		
		return "TableThemeItem";
	}

}
