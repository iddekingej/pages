package org.elaya.page.reciever;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.DynamicData;

abstract public class PostReciever<T extends DynamicData> extends Reciever<T> {

	abstract public void handleRequest(T p_data);
	
	@SuppressWarnings("unchecked")
	public void handleRequest(HttpServletRequest p_request,HttpServletResponse p_response ) throws Exception
	{
		DynamicData l_object=getObject();
		//TODO fail when mandatory and parameter is not given				

		for(Parameter l_parameter:getParameters()){			
			l_object.put(l_parameter.getName(), p_request.getParameter(l_parameter.getName()));
		}

		T l_information;
		l_information=(T)l_object;
		handleRequest(l_information);
	}
}
