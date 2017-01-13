package org.elaya.page.jsplug;

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
