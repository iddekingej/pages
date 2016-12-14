package org.elaya.page;

public enum SubmitType {
	GET("get"),POST("post"),JSON("json");
	private String value;
	
	SubmitType(String pvalue){
		value=pvalue;
	}
	
	public String getValue()
	{
		return value;
	}
}
