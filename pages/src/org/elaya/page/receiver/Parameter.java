package org.elaya.page.receiver;

import org.elaya.page.NamedObject;
import org.elaya.page.core.DynamicMethod;
import org.json.JSONException;

public class Parameter extends DynamicMethod implements NamedObject {
	private String name;
	private ParameterType type;
	private boolean isMandatory=false;
	private long maxLength=-1;
	
	public void validate(Result result,Object value) throws JSONException
	{
		if(getIsMandatory() && (value==null || value.toString().isEmpty())){
			result.addError(name, "Is mandatory");			
		}
		if(value != null){
			if(maxLength>-1 && value.toString().length()>maxLength){
				result.addError(name,"Value is too long, maximum length is "+maxLength);
			}
		}
	}
	
	public void setMaxLength(long pmaxLength)
	{
		maxLength=pmaxLength;
	}
	
	public long getMaxLength()
	{
		return maxLength;
	}
	
	public void setIsMandatory(Boolean flag)
	{
		isMandatory=flag;
	}
	
	public boolean getIsMandatory()
	{
		return isMandatory;
	}
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getName()
	{
		return name;
	}

	public void setType(ParameterType ptype)
	{
		type=ptype;
	}
	
	public ParameterType getType()
	{
		return type;
	}

	@Override
	public String getFullName() {
		return getName();
	}
}
