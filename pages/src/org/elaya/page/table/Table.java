package org.elaya.page.table;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.PageElement;
import org.elaya.page.data.Data;

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
	
	public void preElement(Element<?> p_element) throws IOException
	{
		themeItem.itemHeader();
	}
	public void postElement(Element<?> p_element) throws IOException
	{
		themeItem.itemFooter();
	}
	
	public void displayRow(Data p_data) throws Exception
	{
		
		themeItem.rowHeader();
		displaySubElements(p_data);
		themeItem.rowFooter();	
	}
	@Override
	public void display(Data p_data) throws Exception {
		
		Iterable<?> l_list;
		Object      l_abstractList;
		Data l_row;
		
		if(fieldName.length()==0){
			throw new Errors.PropertyNotSet("fieldName");
		}
		Data l_data=getData(p_data);
		if(!l_data.containsKey(fieldName)){
			throw new Errors.ValueNotFound(fieldName);
		}
		l_abstractList=l_data.get(fieldName);
		if(l_abstractList instanceof Iterable){
			l_list=(Iterable<?>)l_abstractList;
			themeItem.tableHeader("");
			themeItem.titleHeader();
			for(Element <?>l_element:getElements()){
				String l_title;
				if(l_element instanceof TableElement){
					l_title=replaceVariables(l_data,((TableElement<?>)l_element).getTitle());
				} else {
					l_title="";
				}
				themeItem.title(l_title);
			}
			themeItem.titleFooter();
			for(Object l_abstractRow:l_list){
				if(l_abstractRow instanceof Data){
					l_row=(Data)l_abstractRow;
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
