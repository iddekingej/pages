package org.elaya.page;

import java.io.IOException;

import org.elaya.page.data.Data;

public class HorizontalLayout extends Layout{

	public HorizontalLayout()
	{
		super();
	}
	
	public void preElement(Element<?> p_element) throws IOException
	{
			themeItem.HorizontalItemHeader(p_element.getHorizontalAlign(),p_element.getVerticalAlign(),p_element.getLayoutWidth(),p_element.geLayoutHeight());
	}
	
	public void postElement(Element<?> p_element) throws IOException{
		themeItem.HorizontalItemFooter();
	}
	public void display(Data p_data) throws Exception
	{
		Data l_data=getData(p_data);
		themeItem.HorizontalHeader();
		displaySubElements(l_data);
		themeItem.HorizontalFooter();
	}
}
