package org.elaya.page.quickform;

import java.io.IOException;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class DateElement extends BuildInFormElement {
	public enum ShowMode{
		FOCUS("focus"),BUTTON("button"),BOTH("both");
		
		private String value;
			
		
		ShowMode(String pvalue){
			value=pvalue;
		}
		
		public String getValue()
		{
			return value;
		}
	}
	
	private ShowMode showMode=ShowMode.FOCUS;
	private String buttonText;
	
	public void setButtonText(String pbuttonText)
	{
		buttonText=pbuttonText;
	}
	
	public String getButtonText()
	{
		return buttonText;
	}
	
	public void setShowMode(ShowMode pshowMode)
	{
		showMode=pshowMode;
	}
	
	public ShowMode getShowMode()
	{
		return showMode;
	}

	@Override
	public String getJsClassName() {
		return "TDateElement";
	}

	@Override
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException{
		try{
			Data data=getData(pdata);
			Object value=getValueByName(data);
			themeItem.dateElement(pwriter,getDomId(),getName(),value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	protected void makeSetupJs(Writer pwriter,Data pdata) throws IOException 
	{
		pwriter.print("this.showOn='"+showMode.getValue()+"';\n");
		if(buttonText != null && buttonText.length() >0){
			pwriter.print("this.buttonText="+pwriter.js_toString(buttonText)+";");
		}
	}

}
