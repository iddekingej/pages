package org.elaya.page.reciever;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.Dynamic;

public abstract class PostReciever<T extends Dynamic> extends Reciever<T> {
	@Override
	public RecieverData convertRequestToData(HttpServletRequest request,HttpServletResponse response) throws Exception   
	{
		T object=getObject();

		for(Parameter parameter :getParameters()){			
			object.put(parameter.getName(), request.getParameter(parameter.getName()));
		}

		return new RecieverData(object,"");
	}
	
}
