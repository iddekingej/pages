package org.elaya.page;


public class HorizontalLayout extends Layout{

	public HorizontalLayout()
	{
		super();
	}
	public void display() throws Exception
	{
		themeItem.HorizontalHeader();
		for(Element<?> l_element:getElements()){
			themeItem.HorizontalItemHeader(l_element.getHorizontalAlign(),l_element.getVerticalAlign(),l_element.getLayoutWidth(),l_element.geLayoutHeight());
			l_element.display();
			themeItem.HorizontalItemFooter();
		}
		themeItem.HorizontalFooter();
	}
}
