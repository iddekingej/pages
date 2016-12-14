package org.elaya.page.reciever;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.Dynamic;

public abstract class PostReciever<T extends Dynamic> extends Reciever<T> {

	public abstract void handleRequest(T pdata);
	
	@Override
	@SuppressWarnings("unchecked")
	public void handleRequest(HttpServletRequest prequest,HttpServletResponse presponse ) throws Throwable
	{
		Dynamic object=getObject();
		//TODO fail when mandatory and parameter is not given				

		for(Parameter parameter:getParameters()){			
			object.put(parameter.getName(), prequest.getParameter(parameter.getName()));
		}

		T information;
		information=(T)object;
		handleRequest(information);
	}
}
