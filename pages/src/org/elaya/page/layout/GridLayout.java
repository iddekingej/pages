package org.elaya.page.layout;

import org.elaya.page.Element;
import org.elaya.page.HorizontalAlign;
import org.elaya.page.VerticalAlign;
import org.elaya.page.Writer;
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
	public void displaySubElements(int id,Writer pwriter,Data data) throws DisplayException  
	{
		try{
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
				System.out.println(col+" "+columns);
				if(col>=columns){
					col=0;
				}
				if(col==0){
					System.out.println("Grid end");
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
		} catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	@Override
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			themeItem.gridHeader(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override 
	public void displayElementFooter(int id,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException 
	{
		try{
			themeItem.gridFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}

	}


}
