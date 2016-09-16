package org.elaya.page;

import org.elaya.page.data.Data;

public class GridLayout extends Layout {

	private Integer columns;
	
	public int getColumns(){
		return columns;
	}
	
	public void setColumns(Integer p_columns){
		columns=p_columns;
	}
	
	public GridLayout() {
		super();
	}

	@Override
	public void display(Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.gridHeader();
		int l_col=0;
		boolean l_hasEnd=true;
		for(Element<?> l_element:getElements()){
			if(l_col==0){
				themeItem.gridRowHeader();
				l_hasEnd=false;
			}
			if(l_element.checkCondition(l_data)){
				themeItem.gridItemHeader(getClassPrefix(),l_element.getHorizontalAlign(),l_element.getVerticalAlign(),l_element.getLayoutWidth(),l_element.geLayoutHeight());
				l_element.display(l_data);
				themeItem.gridItemFooter();
			}
			l_col++;
			if(l_col>=columns) l_col=0;
			if(l_col==0){
				themeItem.gridRowFooter();
				l_hasEnd=true;
			}
		}
		if(!l_hasEnd){
			while(l_col<columns){
				themeItem.gridItemHeader(getClassPrefix(),HorizontalAlign.left,VerticalAlign.top,"","");
				themeItem.gridItemFooter();
				l_col++;
			}
			themeItem.gridRowFooter();
		}		
		themeItem.gridFooter();
	}

}
