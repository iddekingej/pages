package org.elaya.page.jsplug;

import java.io.IOException;

import org.elaya.page.Writer;

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
	public void display(Writer pwriter) throws IOException
	{
		pwriter.print("this.on("+pwriter.js_toString(event)+",function(){"+js+"});");
	}
}
