package org.elaya.page.receiver;

import org.elaya.page.data.Dynamic;

public class ReceiverData<T extends Dynamic>{
	private final T data;
	private final String cmd;
	
	public ReceiverData(T pdata,String pcmd)
	{
		data=pdata;
		cmd=pcmd;
	}
	
	public T getData(){ return data;}
	public String getCmd(){ return cmd;}

}
