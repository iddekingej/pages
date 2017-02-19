package org.elaya.page.receiver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedList;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.DynamicMethod;
import org.elaya.page.core.PageSession;
import org.json.JSONException;


public abstract  class Receiver extends DynamicMethod implements PageApplicationAware {
	
	public static class ReceiverException extends Exception{
		private static final long serialVersionUID = -8412859746278588435L;
		public ReceiverException(String message){
			super(message);
		}
		public ReceiverException(Throwable cause){
			super(cause);
		}
	}
	

	private Application application;
	private LinkedList<Command> commands=new LinkedList<>();
	private String cmdField="cmd";
	
	protected abstract void sendFailure(PageSession psession,Exception e) throws JSONException, IOException;
	protected abstract void handleData(PageSession psession) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, DynamicException, JSONException, InstantiationException, InvalidObjectType, ReceiverException, DefaultDBConnectionNotSet, SQLException;
	
	public String getCmdField(){
		return cmdField;
	}
	
	public void setCmdField(String pcmdField){
		cmdField=pcmdField;
	}
	
	@Override
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	@Override
	public Application getApplication()
	{
			return application;
	}
	
	

	public final void failure(PageSession psession ,Exception e) throws ReceiverException
	{
		try{
			sendFailure(psession,e);
		} catch(Exception f){
			throw new ReceiverException(f);
		}
	}
	
	public void addCommand(Command command)
	{
		commands.add(command);
	}
	
	protected Command getCommand(String cmdString)
	{
		for(Command cmd:commands){
			if(cmd.isCmd(cmdString)){
				return cmd;
			}
		}
		return null;
	}

	public final void handleRequest(PageSession psession) throws ReceiverException {
		 try{
			 handleData(psession);
		 } catch(Exception e){
			 failure(psession,e);
		 }
	 }
		
}
