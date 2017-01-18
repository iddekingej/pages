package org.elaya.page.receiver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONResult implements Result{
	private JSONObject result;
	private JSONObject data;
	private JSONArray errors=null;
	
	public JSONResult() throws JSONException {
		data=new JSONObject();
		result=new JSONObject();
		result.put("data", data);		
	}
	@Override
	public boolean hasErrors()
	{
		return errors != null;
	}
	
	@Override
	public void put(String pfield,String pvalue) throws JSONException
	{
		data.put(pfield, pvalue);
	}
	
	
	@Override
	public void put(String pfield,Integer pvalue) throws JSONException
	{
		data.put(pfield, pvalue);
	}
	
	@Override
	public void addError(String pfield,String perror) throws JSONException
	{
		if(errors==null){
			errors=new JSONArray();
			result.put("errors",errors);
		}
		errors.put(new JSONObject().put("field",pfield).put("msg",perror));
	}
	
	

	@Override
	public String toString()
	{
		return result.toString();
	}
	
}
