package org.elaya.page.jsplug;
import org.elaya.page.JSWriter;
import org.elaya.page.data.Data;

public abstract class EventJSPlug extends JSPlug {
	private String event;

	public void setEvent(String pevent)
	{
		event=pevent;
	}
	
	public String getEvent(){
		return event;
	}	
	
	public abstract String getJs(); 
	
	@Override
	public void display(JSWriter pwriter,Data data) 
	{
		pwriter.print("this.on("+pwriter.toJsString(getEvent())+",function(){"+getJs()+"});");
	}
}
