package org.elaya.page.widget.quickform;

import java.io.IOException;

import org.elaya.page.core.Data;
import org.elaya.page.core.JSWriter;
import org.elaya.page.core.Writer;
import org.json.JSONException;

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
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException{
		try{
			Object value=getValueByName(data);
			themeItem.dateElement(pwriter,getDomId(id),getName(),value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	protected void makeSetupJs(JSWriter pwriter,Data pdata) throws IOException, JSONException 
	{
		pwriter.print("this.showOn='"+showMode.getValue()+"';\n");
		if(buttonText != null && buttonText.length() >0){
			pwriter.print("this.buttonText="+pwriter.toJsString(buttonText)+";");
		}
	}

}
