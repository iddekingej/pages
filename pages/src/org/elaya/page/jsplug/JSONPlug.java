package org.elaya.page.jsplug;

import java.util.Map;
import org.elaya.page.JSWriter;
import org.elaya.page.data.Data;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.data.Parameterized;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONPlug extends JSPlug implements Parameterized{
	private Map<String,Object> parameters=null;
	private String nextUrl;
	private String postUrl="";
	private String parameterName;
	private boolean async=true;
	private String event="";

	public void setEvent(String pevent)
	{
		event=pevent;
	}
	
	public String getEvent(){
		return event;
	}	
	
	public void setAsync(Boolean pasync){
		async=pasync;
	}
	
	public boolean getAsync()
	{
		return async;
	}
	
	public void setNextUrl(String pnextUrl){
		nextUrl=pnextUrl;
	}
	
	public String getNextUrl(){
		return nextUrl;
	}
	
	public void setPostUrl(String ppostUrl){
		postUrl=ppostUrl;
	}
	
	public String getPostUrl(){
		return postUrl;
	}
	
	public void setParameterName(String pparameterName){
		parameterName=pparameterName;
	}
	
	public String getParameterName()
	{
		return parameterName;
	}
	
	public Map<String, Object>getParameters(){
		return parameters;
	}
	@Override
	public void setParameters(Map<String, Object> data) {
		parameters=data;
	}

	@Override
	public void display(JSWriter pwriter,Data data)  throws JSONException, KeyNotFoundException 
	{
		JSONObject json=new JSONObject(parameters);
		StringBuilder js=new StringBuilder();
		js.append("core.ajaxJSON('post',");
		js.append(pwriter.toJsString(postUrl));
		js.append(",");
		if(parameterName != null){
			json.put(parameterName,data.get(parameterName));
		}
		js.append(json.toString());
		JSONObject config=new JSONObject();
		config.put("async", async);
		if(postUrl!=null){
			config.put("nextUrl",postUrl);
		}
		js.append(",");
		js.append(config.toString());
		js.append(")");
		pwriter.print("this.on("+pwriter.toJsString(getEvent())+",function(){"+js.toString()+"});");
	}
	
}
