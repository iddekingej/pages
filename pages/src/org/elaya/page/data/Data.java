package org.elaya.page.data;

public abstract class Data {
	private Data parent;
	
	public class ReadOnlyException extends Exception{

		private static final long serialVersionUID = 6196924322445759029L;

		public ReadOnlyException(){
			super("'Data' object is read only");			
		}
	}
	
	public Data getParent(){ return parent;}
	
		public Data(Data p_parent) {
		parent=p_parent;
	}

	
	public abstract void makeData();
	public Object get(String p_name){
		return parent.get(p_name);
	}
	public boolean contains(String p_name) throws Exception{
		return parent.contains(p_name);
	}
	public void put(String p_name,Object p_value) throws ReadOnlyException{
		throw new ReadOnlyException();
	}
}
