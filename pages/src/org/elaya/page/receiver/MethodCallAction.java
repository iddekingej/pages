package org.elaya.page.receiver;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.DynamicObject;

public class MethodCallAction extends Action {
	private String method;
	
	public void setMethod(String pmethod){
		method=pmethod;
	}
	
	public String getMethod(){
		return method;
	}
	
	@Override
	public void execute(Object object, HttpServletRequest request, HttpServletResponse response, String cmd, ReceiverData data,Result result) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		DynamicObject.call(object, method, new Class<?>[]{HttpServletRequest.class,HttpServletResponse.class,String.class,data.getData().getClass(),result.getClass()}, new Object[]{request,response,data.getCmd(),data.getData(),result});
	}

}
