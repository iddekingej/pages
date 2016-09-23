package org.elaya.page;

import java.io.IOException;

import org.elaya.page.data.Data;

public class VerticalLayout extends Layout {

	public VerticalLayout() {
		super();
	}

	@Override
	protected void preElement(Writer p_writer,Element<?> p_element) throws IOException
	{
			themeItem.verticalItemHeader(p_writer,p_element.getHorizontalAlign(),p_element.getVerticalAlign(),p_element.getLayoutWidth(),p_element.getLayoutHeight());		
	}
	
	@Override
	protected void postElement(Writer p_writer,Element<?> p_element) throws IOException
	{
		themeItem.verticalItemFooter(p_writer);
	}
	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.verticalHeader(p_writer);
		displaySubElements(p_writer,l_data);
		themeItem.verticalFooter(p_writer);
	}

}
