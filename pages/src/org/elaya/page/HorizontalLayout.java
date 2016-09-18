package org.elaya.page;

import java.io.IOException;

import org.elaya.page.data.Data;

public class HorizontalLayout extends Layout{

	public HorizontalLayout()
	{
		super();
	}
	
	@Override
	public void preElement(Writer p_writer,Element<?> p_element) throws IOException
	{
			themeItem.HorizontalItemHeader(p_writer,p_element.getHorizontalAlign(),p_element.getVerticalAlign(),p_element.getLayoutWidth(),p_element.geLayoutHeight());
	}
	
	@Override
	public void postElement(Writer p_writer,Element<?> p_element) throws IOException{
		themeItem.HorizontalItemFooter(p_writer);
	}
	public void display(Writer p_writer,Data p_data) throws Exception
	{
		Data l_data=getData(p_data);
		themeItem.HorizontalHeader(p_writer);
		displaySubElements(p_writer,l_data);
		themeItem.HorizontalFooter(p_writer);
	}
}
