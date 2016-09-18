package org.elaya.page.quickform;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class DateElement extends BuildInFormElement {
	public enum ShowMode{
		focus("focus"),button("button"),both("both");
		
		private String value;
			
		
		ShowMode(String p_value){
			value=p_value;
		}
		
		public String getValue()
		{
			return value;
		}
	}
	
	private ShowMode showMode=ShowMode.focus;
	private String buttonText;
	
	public void setButtonText(String p_buttonText)
	{
		buttonText=p_buttonText;
	}
	
	public String getButtonText()
	{
		return buttonText;
	}
	
	public void setShowMode(ShowMode p_showMode)
	{
		showMode=p_showMode;
	}
	
	public ShowMode getShowMode()
	{
		return showMode;
	}
	
	public DateElement() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getJsType() {
		return null;
	}

	@Override
	public String getJsClassName() {
		return "TDateElement";
	}

	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);
		Object l_value=getValueByName(l_data);
		themeItem.dateElement(p_writer,getDomId(),getName(),l_value);
	}

	@Override
	protected void makeSetupJs(Writer p_writer,Data p_data) throws Exception
	{
		p_writer.print("this.showOn='"+showMode.getValue()+"';\n");
		if(buttonText != null && buttonText.length() >0){
			p_writer.print("this.buttonText="+themeItem.js_toString(buttonText)+";");
		}
	}

}
