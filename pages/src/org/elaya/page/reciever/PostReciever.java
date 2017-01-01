package org.elaya.page.reciever;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.Dynamic;

public abstract class PostReciever<T extends Dynamic> extends Reciever<T> {
	@Override
	public RecieverData<T> convertRequestToData(HttpServletRequest request,HttpServletResponse response) throws Exception   
	{
		T object=getObject();
		String name;
		for(Map.Entry<String,Parameter> paramEnt :getParameters().entrySet()){
			name=paramEnt.getKey();
			object.put(name, request.getParameter(name));
		}

		return new RecieverData<>(object,"");
	}
	
}
