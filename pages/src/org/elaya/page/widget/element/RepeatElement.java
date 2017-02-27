package org.elaya.page.widget.element;

import org.elaya.page.core.Data;
import org.elaya.page.core.InvalidTypeException;
import org.elaya.page.core.KeyNotFoundException;
import org.elaya.page.core.Writer;
import java.io.IOException;
import org.elaya.page.widget.Element;
import org.elaya.page.widget.PageElement;

public class RepeatElement extends PageElement<ElementThemeItem> {
	private String dataVariableName;
	
	public RepeatElement()
	{
		super();
		setIsNamespace(true);
		
	}
	
	@Override
	public void displayElement(int id,Writer writer, Data pdata) throws org.elaya.page.widget.Element.DisplayException {
//Nothing to do for repeat element
	}
	
	@Override
	protected void preElement(int id,Writer writer,Data data,Element<?> element) throws IOException, KeyNotFoundException 
	{
		writer.getJSWriter().setFromOther("widgetParent", "widgetParent.newItem()");
	}
	
	@Override
	protected void postElement(int id,Writer writer,Data data,Element<?> element) throws IOException
	{		
		writer.getJSWriter().setFromOther("widgetParent","widgetParent.parent");
	}
	
	@Override
	public void displayChildElements(int id,Writer pwriter,Data pdata) throws DisplayException  
	{
		try{
			Data data=getData(pdata);
			Object dataValue=data.get(dataVariableName);
			if(dataValue instanceof Iterable){					
				Iterable<?> iter=(Iterable<?>)dataValue;
				for(Object item:iter){
					if(item instanceof Data){
						super.displayChildElements(id,pwriter, (Data)item);
					}else {
						throw new InvalidTypeException(Data.class,item);
					}
				}
			} else {
				throw new InvalidTypeException(Iterable.class,dataValue);
			}
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	@Override
	public String getJsClassName()
	{
		return "TRepeatElement";
	}
	
	public void setDataVariableName(String variableName)
	{
		dataVariableName=variableName;
	}
	
	public String getDataVariableName(){
		return dataVariableName;
	}
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
