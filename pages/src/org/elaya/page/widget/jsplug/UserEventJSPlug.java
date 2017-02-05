package org.elaya.page.widget.jsplug;

public class UserEventJSPlug extends EventJSPlug {

	private String js;
	
	public void setJs(String pjs)
	{
		js=pjs;
	}
	
	@Override
	public String getJs()
	{
		return js;
	}

}
