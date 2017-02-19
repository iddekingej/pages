package org.elaya.page.widget.table;

import java.io.IOException;

import org.elaya.page.Errors;
import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Element;
import org.elaya.page.widget.PageElement;

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
	public void preElement(int id,Writer pwriter,Data data,Element<?> pelement) throws IOException
	{
		themeItem.itemHeader(pwriter);
	}
	
	@Override
	public void postElement(int id,Writer pwriter,Data data,Element<?> pelement) throws IOException
	{
		themeItem.itemFooter(pwriter);
	}
	
		
	@Override
	public void displayChildElements(int id,Writer pwriter,Data data) throws DisplayException  
	{
		try{
			Object abstractList;

			abstractList=data.get(fieldName);
			if(abstractList instanceof Iterable){
				@SuppressWarnings("unchecked")
				Iterable<Object> dataList=(Iterable<Object>)abstractList;
				for(Object abstractRow:dataList){
					if(abstractRow instanceof Data){
						themeItem.rowHeader(pwriter);
						super.displayChildElements(id,pwriter,(Data)abstractRow);
						themeItem.rowFooter(pwriter);	

					}
				}
			} else {
				throw new DataIsNotIterable(fieldName);
			}
		}catch(Exception e){
			throw new DisplayException(e);
		}				
	}
	
	@Override
	public void displayElement(int id,Writer writer,Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{

			if(fieldName.isEmpty()){
				throw new Errors.PropertyNotSet("fieldName");
			}
			if(!data.containsKey(fieldName)){
				throw new Errors.ValueNotFound(fieldName);
			}
			themeItem.tableHeader(writer,"");
			themeItem.titleHeader(writer);
			for(Element <?>element:getElements()){
				String title;
				if(element instanceof TableElement){
					title=writer.replaceVariables(data,((TableElement<?>)element).getTitle());
				} else {
					title="";
				}
				themeItem.title(writer,title);
			}
			themeItem.titleFooter(writer);
			
		}catch(Exception e){
			throw new DisplayException(e);
		}
		
	} 

	@Override
	public void displayElementFooter(int id,Writer writer,Data data) throws org.elaya.page.widget.Element.DisplayException 
	{
		try{
			themeItem.tableFooter(writer);
		}catch(Exception e){
			throw new DisplayException(e);
		}
	}
	
	@Override
	public String getThemeName() {		
		return "TableThemeItem";
	}

}
