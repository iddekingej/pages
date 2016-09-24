package org.elaya.page.quickform;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class ElementContainer extends BuildInFormElement {
	static public enum LabelPosition{above,left}
	private String label;
	private LabelPosition labelPosition=LabelPosition.left;
	
	public String getLabel(){ return label;}
	public void setLabel(String p_label){ label=p_label;}
	public LabelPosition getLabelPoisition(){ return labelPosition;}
	public void setLabelPosition(LabelPosition p_labelPosition){
		labelPosition=p_labelPosition;
	}
	
	public Element<?> getFirstWidget()
	{
		return this.getParent().getFirstWidget();
	}
		
	public ElementContainer() {
		super();
	}

	@Override
	public void display(Writer p_writer, Data p_data) throws Exception {
		if(labelPosition==LabelPosition.left){
			themeItem.elementBegin(getDomId(),p_writer,label);
		} else {
			themeItem.elementBeginTop(getDomId(),p_writer, label);
		}
		displaySubElements(p_writer,p_data);
		themeItem.elementEnd(p_writer);
	}
	
	public boolean checkElement(Element<?> p_element){
		return true;
	}

}
