package org.elaya.page.receiver;

import org.elaya.page.core.Dynamic;
import org.elaya.page.core.InvalidTypeException;

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
			throw new InvalidTypeException(type,data);
		}
	}
	public String getCmd(){ return cmd;}

}
