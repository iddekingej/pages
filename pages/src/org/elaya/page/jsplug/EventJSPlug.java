package org.elaya.page.jsplug;

import java.io.IOException;

import org.elaya.page.Writer;

public class EventJSPlug extends JSPlug {

	private String event;
	private String js;
	
	public void setEvent(String p_event)
	{
		event=p_event;
	}
	
	public String getEvent(){
		return event;
	}

	public void setJs(String p_js)
	{
		js=p_js;
	}
	
	public String getJs()
	{
		return js;
	}
	
	public void display(Writer p_writer) throws IOException
	{
		p_writer.print("this.on("+p_writer.js_toString(event)+",function(){"+js+"});");
	}
}
