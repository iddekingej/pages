package org.elaya.page.reciever;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONResult {
	private JSONObject result;
	private JSONObject data;
	private JSONArray errors=null;
	
	public void put(String p_field,String p_value)
	{
		data.put(p_field, p_value);
	}
	
	public void put(String p_field,Integer p_value)
	{
		data.put(p_field, p_value);
	}
	
	public void addError(String p_field,String p_error)
	{
		if(errors==null){
			errors=new JSONArray();
			result.put("errors",errors);
		}
		errors.put(new JSONObject().put("field",p_field).put("msg",p_error));
	}
	
	public JSONResult() {
		data=new JSONObject();
		result=new JSONObject();
		result.put("data", data);
	}
	
	public String toString()
	{
		return result.toString();
	}
	
}
