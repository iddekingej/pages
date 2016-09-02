package org.elaya.page;

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
	public void display() throws Exception {
		themeItem.gridHeader();
		int l_col=0;
		boolean l_hasEnd=true;
		for(Element<?> l_element:getElements()){
			if(l_col==0){
				themeItem.gridRowHeader();
				l_hasEnd=false;
			}
			themeItem.gridItemHeader();
			l_element.display();
			themeItem.gridItemFooter();
			l_col++;
			if(l_col>=columns) l_col=0;
			if(l_col==0){
				themeItem.gridRowFooter();
				l_hasEnd=true;
			}
		}
		if(!l_hasEnd){
			while(l_col<columns){
				themeItem.gridItemHeader();
				themeItem.gridItemFooter();
				l_col++;
			}
			themeItem.gridRowFooter();
		}		
		themeItem.gridFooter();
	}

}
