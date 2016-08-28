package org.elaya.page.data;

import java.util.Iterator;

public abstract class Data {
	private Data parent;
	private Context context;

	public class ReadOnlyException extends Exception{

		private static final long serialVersionUID = 6196924322445759029L;

		public ReadOnlyException(){
			super("'Data' object is read only");			
		}
	}
	
	public class NoIterator extends Exception{

		private static final long serialVersionUID = -7306836576316888906L;

		public NoIterator(){
			super("No iterator available");
		}
	}
	
	public class ContextIsNull extends Exception{

		private static final long serialVersionUID = -479172590960358659L;

		public ContextIsNull(){
			super("Context of data is null");
		}
	}
	
	public Context getContext(){ return context;}
	
	public void setContext(Context p_context){ 
		context=p_context;
	}
	
	
	public Data(Data p_parent) {
		parent=p_parent;
		if(p_parent !=null)	context=p_parent.getContext();
	}
	
	public void init() throws Exception
	{
		if(getContext()==null) throw new ContextIsNull();
		makeData();
	}

	public Data getParent(){ return parent;}

	public abstract void makeData() throws Exception;
	
	public Object get(String p_name){
		if(parent !=null){
			return parent.get(p_name);
		}
		return null;
	}
	public boolean contains(String p_name){
		if(parent==null) return false;
		return parent.contains(p_name);
	}
	public void put(String p_name,Object p_value) throws ReadOnlyException{
		throw new ReadOnlyException();
	}
	public Iterator<?> getIterator() throws NoIterator
	{
		throw new NoIterator();
	}
	
	public String getString(String p_name){
		Object l_data=get(p_name);
		if(l_data==null) return null;
		return l_data.toString();
	}
	
	public Integer getInteger(String p_name){
		String l_result=getString(p_name);
		if(l_result==null) return null;
		return Integer.parseInt(l_result);
 	}
}
