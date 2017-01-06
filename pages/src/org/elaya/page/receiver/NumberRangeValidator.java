package org.elaya.page.receiver;
import org.elaya.page.data.Dynamic;
import org.json.JSONException;

public class NumberRangeValidator<T extends Dynamic> extends VarValidator<T> {
	private boolean   hasLowerRange=false;
	private long      lowerRange=0;
	private boolean   hasUpperRange=false;
	private long      upperRange=0;
	
	public void setLowerRange(long value)
	{
		lowerRange=value;
		hasLowerRange=true;
	}
	
	public void setUpperRanger(long value)
	{
		upperRange=value;
		hasUpperRange=true;
	}
	
	public boolean hasLowerRange()
	{
		return hasLowerRange;
	}
	
	public long getLowerRange()
	{
		return lowerRange;
	}
	
	
	public boolean hasUpperRange()
	{
		return hasUpperRange;
	}
	
	public long getUpperRange()
	{
		return upperRange;
	}
	
	@Override
	void validateValue(Result result,ReceiverData<T> data, Object value) throws JSONException{
		String name=getVarName();		
		if(value instanceof Number ){
			long number=((Number)value).longValue();
			if(hasLowerRange &&(number < lowerRange)){
				result.addError(name, "Value must be equal or bigger than "+lowerRange);
			
			}
			if(hasUpperRange && (number > upperRange)){
					result.addError(name,"Value must be equal or less than "+lowerRange);			
			}
		} else {
			result.addError(getVarName(), "Value is not a number");
		}
	}

}
