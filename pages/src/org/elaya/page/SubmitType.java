package org.elaya.page;

public enum SubmitType {
	get("get"),post("post"),json("json");
	private String value;
	
	SubmitType(String p_value){
		value=p_value;
	}
	
	public String getValue()
	{
		return value;
	}
}
