package org.elaya.page.receiver;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.data.Dynamic;

public abstract class PostReceiver<T extends Dynamic> extends Receiver<T> {
	@Override
	public ReceiverData<T> convertRequestToData(HttpServletRequest request,HttpServletResponse response) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidObjectType, DynamicException    
	{
		T object=getObject();
		String name;
		for(Map.Entry<String,Parameter> paramEnt :getParameters().entrySet()){
			name=paramEnt.getKey();
			object.put(name, request.getParameter(name));
		}

		return new ReceiverData<>(object,"");
	}
	
}
