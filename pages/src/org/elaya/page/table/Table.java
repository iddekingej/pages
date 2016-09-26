package org.elaya.page.table;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
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
	
	public Table()
	{
		super();
		setIsNamespace(true);
	}
	
	private String fieldName;
	
	public void setFieldName(String p_fieldName){
		fieldName=p_fieldName;
	}
	
	public String getFieldName(){
		return fieldName;
	}
	
	@Override
	public void preElement(Writer p_writer,Element<?> p_element) throws IOException
	{
		themeItem.itemHeader(p_writer);
	}
	
	@Override
	public void postElement(Writer p_writer,Element<?> p_element) throws IOException
	{
		themeItem.itemFooter(p_writer);
	}
	
	public void displayRow(Writer p_writer,Data p_data) throws Exception
	{
		
		themeItem.rowHeader(p_writer);
		displaySubElements(p_writer,p_data);
		themeItem.rowFooter(p_writer);	
	}
	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		
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
			themeItem.tableHeader(p_writer,"");
			themeItem.titleHeader(p_writer);
			for(Element <?>l_element:getElements()){
				String l_title;
				if(l_element instanceof TableElement){
					l_title=replaceVariables(l_data,((TableElement<?>)l_element).getTitle());
				} else {
					l_title="";
				}
				themeItem.title(p_writer,l_title);
			}
			themeItem.titleFooter(p_writer);
			for(Object l_abstractRow:l_list){
				if(l_abstractRow instanceof Data){
					l_row=(Data)l_abstractRow;
					displayRow(p_writer,l_row);
				} else {
					throw new NotADynamicData(l_abstractRow.getClass().getName());
				}
			}
			themeItem.tableFooter(p_writer);
		} else {
			throw new DataIsNotIterable(fieldName);
		}
		
	}

	@Override
	public String getThemeName() {		
		return "TableThemeItem";
	}

}
