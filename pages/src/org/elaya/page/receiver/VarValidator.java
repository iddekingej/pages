package org.elaya.page.receiver;

import org.json.JSONException;

public abstract class VarValidator extends Validator {
	private String varName;
	
	public void setVarName(String pvarName)
	{
		varName=pvarName;
	}
	
	public String getVarName()
	{
		return varName;
	}

	abstract void validateValue(Result result,ReceiverData data,Object value) throws JSONException;
	
	@Override
	void validate(Result result,ReceiverData data) throws DynamicException, JSONException
	{
		Object value=data.getData().get(getVarName());
		validateValue(result,data,value);
	}

}
