package org.elaya.page;

import org.elaya.page.data.Data;

public class GridLayout extends Layout {

	private Integer columns;
	
	public GridLayout() {
		super();
	}

	public int getColumns(){
		return columns;
	}
	
	public void setColumns(Integer pcolumns){
		columns=pcolumns;
	}
	

	@Override
	public void display(Writer pwriter,Data pdata) throws Exception {
		Data data=getData(pdata);
		themeItem.gridHeader(pwriter);
		int col=0;
		boolean hasEnd=true;
		for(Element<?> element:getElements()){
			if(col==0){
				themeItem.gridRowHeader(pwriter);
				hasEnd=false;
			}
			if(element.checkCondition(data)){
				themeItem.gridItemHeader(pwriter,getClassPrefix(),element.getHorizontalAlign(),element.getVerticalAlign(),element.getLayoutWidth(),element.getLayoutHeight());
				element.display(pwriter,data);
				themeItem.gridItemFooter(pwriter);
			}
			col++;
			if(col>=columns){
				col=0;
			}
			if(col==0){
				themeItem.gridRowFooter(pwriter);
				hasEnd=true;
			}
		}
		if(!hasEnd){
			while(col<columns){
				themeItem.gridItemHeader(pwriter,getClassPrefix(),HorizontalAlign.LEFT,VerticalAlign.TOP,"","");
				themeItem.gridItemFooter(pwriter);
				col++;
			}
			themeItem.gridRowFooter(pwriter);
		}		
		themeItem.gridFooter(pwriter);
	}

}
