package org.elaya.page.reciever;

import org.elaya.page.data.Dynamic;
import org.json.JSONException;

public abstract class VarValidator<T extends Dynamic> extends Validator<T> {
	private String varName;
	
	public void setVarName(String pvarName)
	{
		varName=pvarName;
	}
	
	public String getVarName()
	{
		return varName;
	}

	abstract void validateValue(Result result,RecieverData<T> data,Object value) throws JSONException;
	
	@Override
	void validate(Result result,RecieverData<T> data) throws DynamicException, JSONException
	{
		Object value=data.getData().get(getVarName());
		validateValue(result,data,value);
	}

}
