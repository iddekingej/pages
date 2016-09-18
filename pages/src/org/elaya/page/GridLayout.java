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
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.gridHeader(p_writer);
		int l_col=0;
		boolean l_hasEnd=true;
		for(Element<?> l_element:getElements()){
			if(l_col==0){
				themeItem.gridRowHeader(p_writer);
				l_hasEnd=false;
			}
			if(l_element.checkCondition(l_data)){
				themeItem.gridItemHeader(p_writer,getClassPrefix(),l_element.getHorizontalAlign(),l_element.getVerticalAlign(),l_element.getLayoutWidth(),l_element.geLayoutHeight());
				l_element.display(p_writer,l_data);
				themeItem.gridItemFooter(p_writer);
			}
			l_col++;
			if(l_col>=columns) l_col=0;
			if(l_col==0){
				themeItem.gridRowFooter(p_writer);
				l_hasEnd=true;
			}
		}
		if(!l_hasEnd){
			while(l_col<columns){
				themeItem.gridItemHeader(p_writer,getClassPrefix(),HorizontalAlign.left,VerticalAlign.top,"","");
				themeItem.gridItemFooter(p_writer);
				l_col++;
			}
			themeItem.gridRowFooter(p_writer);
		}		
		themeItem.gridFooter(p_writer);
	}

}
