package org.elaya.page.widget.layout;

import org.elaya.page.HorizontalAlign;
import org.elaya.page.VerticalAlign;
import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Element;

public class GridLayout extends Layout {

	private Integer columns;
	
	public int getColumns(){
		return columns;
	}
	
	public void setColumns(Integer pcolumns){
		columns=pcolumns;
	}
	
	@Override
	public void displayChildElements(int id,Writer pwriter,Data data) throws DisplayException  
	{
		try{
			int col=0;
			boolean hasEnd=true;
			for(Element<?> element:getElements()){
				if(col==0){
					themeItem.gridRowHeader(pwriter);
					hasEnd=false;
				}
				themeItem.gridItemHeader(pwriter,getClassPrefix(),element.getHorizontalAlign(),element.getVerticalAlign(),element.getLayoutWidth(),element.getLayoutHeight());
				if(element.checkCondition(data)){
					element.display(pwriter,data);
				}
				themeItem.gridItemFooter(pwriter);
				col++;
				if(col>=columns){
					col=0;
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
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException {
		try{
			themeItem.gridHeader(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override 
	public void displayElementFooter(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException 
	{
		try{
			themeItem.gridFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}

	}


}
