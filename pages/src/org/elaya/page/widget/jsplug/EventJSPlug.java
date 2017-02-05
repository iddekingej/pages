package org.elaya.page.widget.jsplug;
import org.elaya.page.core.JSWriter;
import org.elaya.page.data.Data;
import org.json.JSONException;

public abstract class EventJSPlug extends JSPlug {
	private String event;

	public void setEvent(String pevent)
	{
		event=pevent;
	}
	
	public String getEvent(){
		return event;
	}	
	
	public abstract String getJs() throws JSONException; 
	
	@Override
	public void display(JSWriter pwriter,Data data) throws JSONException 
	{
		pwriter.print("this.on("+pwriter.toJsString(getEvent())+",function(){"+getJs()+"});");
	}
}
