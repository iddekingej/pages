package org.elaya.page.quickform;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class ElementContainer extends BuildInFormElement {
	public enum LabelPosition{ABOVE,LEFT}
	private String label;
	private LabelPosition labelPosition=LabelPosition.LEFT;
	
	public String getLabel(){ return label;}
	public void setLabel(String plabel){ label=plabel;}
	public LabelPosition getLabelPoisition(){ return labelPosition;}
	
	public void setLabelPosition(LabelPosition plabelPosition){
		labelPosition=plabelPosition;
	}
	
	@Override
	public Element<?> getFirstWidget()
	{
		return this.getParent().getFirstWidget();
	}
	
	@Override
	public void display(Writer pwriter, Data pdata) throws Exception {
		if(labelPosition==LabelPosition.LEFT){
			themeItem.elementBegin(getDomId(),pwriter,label);
		} else {
			themeItem.elementBeginTop(getDomId(),pwriter, label);
		}
		displaySubElements(pwriter,pdata);
		themeItem.elementEnd(pwriter);
	}
	
	@Override
	public boolean checkElement(Element<?> pelement){
		return true;
	}

}
