package org.elaya.page.jsplug;

import org.elaya.page.JSWriter;

public class EventJSPlug extends JSPlug {

	private String event;
	private String js;
	
	public void setEvent(String pevent)
	{
		event=pevent;
	}
	
	public String getEvent(){
		return event;
	}

	public void setJs(String pjs)
	{
		js=pjs;
	}
	
	public String getJs()
	{
		return js;
	}
	
	@Override
	public void display(JSWriter pwriter) 
	{
		pwriter.print("this.on("+pwriter.toJsString(event)+",function(){"+js+"});");
	}
}
