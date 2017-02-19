package org.elaya.page.widget.quickform;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Element;

public class ElementContainer extends BuildInFormElement {
	public enum LabelPosition{ABOVE,LEFT}
	private String label;
	private LabelPosition labelPosition=LabelPosition.LEFT;
	
	public ElementContainer()
	{
		super();
		setIsWidgetParent(false);
	}
	
	public String getLabel(){ return label;}
	public void setLabel(String plabel){ label=plabel;}
	public LabelPosition getLabelPoisition(){ return labelPosition;}
	
	public void setLabelPosition(LabelPosition plabelPosition){
		labelPosition=plabelPosition;
	}
	
	@Override
	public void displayElement(int id,Writer pwriter, Data pdata) throws org.elaya.page.widget.Element.DisplayException {
		try{			
			if(labelPosition==LabelPosition.LEFT){
				themeItem.elementBegin(getDomId(id),pwriter,label);
			} else {
				themeItem.elementBeginTop(getDomId(id),pwriter, label);
			}
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public void displayElementFooter(int id,Writer pwriter, Data pdata) throws org.elaya.page.widget.Element.DisplayException {
	{	
		try{
			themeItem.elementEnd(pwriter);
		}catch(Exception e){
			throw new DisplayException(e);
		}
	}		
	}
	
	@Override
	public boolean checkElement(Element<?> pelement){
		return true;
	}

}
