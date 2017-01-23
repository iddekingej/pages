package org.elaya.page.receiver;

import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidTypeException;
import org.elaya.page.data.Dynamic;

public class ReceiverData{
	private final Dynamic data;
	private final String cmd;
	
	public ReceiverData(Dynamic pdata,String pcmd)
	{
		data=pdata;
		cmd=pcmd;
	}
	
	public Dynamic getData()
	{
		return data;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Dynamic>T getData(Class<T> type) throws InvalidTypeException{
		if(type.isInstance(data)){
			return (T)data;
		} else {
			throw new Errors.InvalidTypeException("data element");
		}
	}
	public String getCmd(){ return cmd;}

}
