package org.elaya.page;

import java.io.IOException;

import org.elaya.page.data.Data;

public class VerticalLayout extends Layout {

	public VerticalLayout() {
		super();
	}

	protected void preElement(Element<?> p_element) throws IOException
	{
			themeItem.verticalItemHeader(p_element.getHorizontalAlign(),p_element.getVerticalAlign(),p_element.getLayoutWidth(),p_element.geLayoutHeight());		
	}
	
	protected void postElement(Element<?> p_element) throws IOException
	{
		themeItem.verticalItemFooter();
	}
	@Override
	public void display(Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.verticalHeader();
		displaySubElements(l_data);
		themeItem.verticalFooter();
	}

}
