package org.elaya.page;

import org.elaya.page.data.Data;

public class VerticalLayout extends Layout {

	public VerticalLayout() {
		super();
	}

	@Override
	public void display(Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.verticalHeader();
		for(Element<?> l_element:getElements()){
			themeItem.verticalItemHeader(l_element.getHorizontalAlign(),l_element.getVerticalAlign(),l_element.getLayoutWidth(),l_element.geLayoutHeight());
			l_element.display(l_data);
			themeItem.verticalItemFooter();
		}
		themeItem.verticalFooter();
	}

}
