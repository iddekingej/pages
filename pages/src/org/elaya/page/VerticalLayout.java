package org.elaya.page;

public class VerticalLayout extends Layout {

	public VerticalLayout() {
		super();
	}

	@Override
	public void display() throws Exception {
		themeItem.verticalHeader();
		for(Element<?> l_element:getElements()){
			themeItem.verticalItemHeader(l_element.getHorizontalAlign(),l_element.getVerticalAlign(),l_element.getLayoutWidth(),l_element.geLayoutHeight());
			l_element.display();
			themeItem.verticalItemFooter();
		}
		themeItem.verticalFooter();
	}

}
