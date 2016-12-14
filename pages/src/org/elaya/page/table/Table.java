package org.elaya.page.table;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Table extends PageElement<TableThemeItem> {
	private String fieldName;

	public static class DataIsNotIterable extends Exception{

		private static final long serialVersionUID = 1L;
		
		public DataIsNotIterable(String pname){
			super("Data '"+pname+"' is not iterable");
		}
	}

	public static class NotADynamicData extends Exception{

		private static final long serialVersionUID = 6677143410727926090L;

		public NotADynamicData(String pname){
			super("Class('"+pname+" doesn't implement DynamicData");
		}
	}
	
	public Table()
	{
		super();
		setIsNamespace(true);
	}
		
	public void setFieldName(String pfieldName){
		fieldName=pfieldName;
	}
	
	public String getFieldName(){
		return fieldName;
	}
	
	@Override
	public void preElement(Writer pwriter,Element<?> pelement) throws IOException
	{
		themeItem.itemHeader(pwriter);
	}
	
	@Override
	public void postElement(Writer pwriter,Element<?> pelement) throws IOException
	{
		themeItem.itemFooter(pwriter);
	}
	
	public void displayRow(Writer pwriter,Data pdata) throws Exception
	{
		
		themeItem.rowHeader(pwriter);
		displaySubElements(pwriter,pdata);
		themeItem.rowFooter(pwriter);	
	}
	
	private void displayList(Iterable<?> dataList,Writer writer,Data data) throws Exception
	{
		Data row;
		
		themeItem.tableHeader(writer,"");
		themeItem.titleHeader(writer);
		for(Element <?>element:getElements()){
			String title;
			if(element instanceof TableElement){
				title=replaceVariables(data,((TableElement<?>)element).getTitle());
			} else {
				title="";
			}
			themeItem.title(writer,title);
		}
		themeItem.titleFooter(writer);
		for(Object abstractRow:dataList){
			if(abstractRow instanceof Data){
				row=(Data)abstractRow;
				displayRow(writer,row);
			} else {
				throw new NotADynamicData(abstractRow.getClass().getName());
			}
		}
		themeItem.tableFooter(writer);		
	}
	
	@Override
	public void display(Writer writer,Data pdata) throws Exception {
		
		Object      abstractList;
		
		if(fieldName.isEmpty()){
			throw new Errors.PropertyNotSet("fieldName");
		}
		Data data=getData(pdata);
		if(!data.containsKey(fieldName)){
			throw new Errors.ValueNotFound(fieldName);
		}
		abstractList=data.get(fieldName);
		if(abstractList instanceof Iterable){
			displayList((Iterable<?>)abstractList,writer,data);
		} else {
			throw new DataIsNotIterable(fieldName);
		}
		
	} 

	@Override
	public String getThemeName() {		
		return "TableThemeItem";
	}

}
