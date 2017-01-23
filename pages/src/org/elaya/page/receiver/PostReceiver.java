package org.elaya.page.receiver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.data.Dynamic;
import org.json.JSONException;

public abstract class PostReceiver extends Receiver {
	
	@Override
	protected final  void handleData(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, DynamicException, JSONException, InstantiationException, InvalidObjectType, ReceiverException 
	{
		
		String cmd=request.getParameter("cmd");
		if(cmd==null){
			cmd="";
		}
		Command command=getCommand(cmd);
		if(command==null){
			throw new ReceiverException("Invalid command '"+cmd+"'");
		}
		Dynamic data=convertRequestToData(command,request);
		ReceiverData recieverData=new ReceiverData(data,cmd);
		POSTResult result=new POSTResult();
		command.handleRequest(this, request, response, recieverData, result);
	}
	
	protected Dynamic convertRequestToData(Command command,HttpServletRequest request) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidObjectType, DynamicException
	{
		Dynamic object=command.getObject();
		String name;
		for(Map.Entry<String,Parameter> paramEnt :command.getParameters()){
			name=paramEnt.getKey();
			object.put(name, request.getParameter(name));
		}

		return object;
	}
	
}
