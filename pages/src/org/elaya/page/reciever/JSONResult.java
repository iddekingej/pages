package org.elaya.page.reciever;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONResult {
	private JSONObject result;
	private JSONObject data;
	private JSONArray errors=null;
	
	public boolean hasErrors()
	{
		return errors != null;
	}
	public void put(String p_field,String p_value) throws JSONException
	{
		data.put(p_field, p_value);
	}
	
	public void put(String p_field,Integer p_value) throws JSONException
	{
		data.put(p_field, p_value);
	}
	
	public void addError(String p_field,String p_error) throws JSONException
	{
		if(errors==null){
			errors=new JSONArray();
			result.put("errors",errors);
		}
		errors.put(new JSONObject().put("field",p_field).put("msg",p_error));
	}
	
	public JSONResult() throws JSONException {
		data=new JSONObject();
		result=new JSONObject();
		result.put("data", data);
	}
	
	public String toString()
	{
		return result.toString();
	}
	
}
