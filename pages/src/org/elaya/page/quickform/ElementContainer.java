package org.elaya.page.quickform;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class ElementContainer extends BuildInFormElement {
	static public enum LabelPosition{above,left}
	private String label;
	private LabelPosition labelPosition=LabelPosition.left;
	
	public String getLabel(){ return label;}
	public void setLabel(String plabel){ label=plabel;}
	public LabelPosition getLabelPoisition(){ return labelPosition;}
	public void setLabelPosition(LabelPosition plabelPosition){
		labelPosition=plabelPosition;
	}
	
	public Element<?> getFirstWidget()
	{
		return this.getParent().getFirstWidget();
	}
		
	public ElementContainer() {
		super();
	}

	@Override
	public void display(Writer pwriter, Data pdata) throws Exception {
		if(labelPosition==LabelPosition.left){
			themeItem.elementBegin(getDomId(),pwriter,label);
		} else {
			themeItem.elementBeginTop(getDomId(),pwriter, label);
		}
		displaySubElements(pwriter,pdata);
		themeItem.elementEnd(pwriter);
	}
	
	public boolean checkElement(Element<?> pelement){
		return true;
	}

}
