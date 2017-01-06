package org.elaya.page.reciever;

import org.elaya.page.data.Dynamic;

public class RecieverData<T extends Dynamic>{
	private final T data;
	private final String cmd;
	
	public RecieverData(T pdata,String pcmd)
	{
		data=pdata;
		cmd=pcmd;
	}
	
	public T getData(){ return data;}
	public String getCmd(){ return cmd;}

}
