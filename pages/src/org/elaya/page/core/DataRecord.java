package org.elaya.page.core;


public class DataRecord extends Data{
	class IndexOutOfRangeException extends DataException{

		private static final long serialVersionUID = 145409992701000837L;

		public IndexOutOfRangeException(int pno,int pmax)
		{
			super("Index "+pno+" out of range, minimum is 0 maximum is "+pmax);
		}
	}
	
	private DataRecordList list;
	private Object[] data;
	
	public DataRecord(DataRecordList plist)
	{
		list=plist;		
		data=new Object[plist.getNumberOfFields()];
	}
	
	@Override
	public void put(String pname,Object pvalue) throws KeyNotFoundException
	{
		Integer index=list.getFieldPosition(pname);
		if(index==null){
			throw new KeyNotFoundException(pname);
		}
		data[index]=pvalue;
	}
	
	public void put(int pindex,Object pvalue) throws IndexOutOfRangeException
	{
		if(pindex<0 || pindex>=list.getNumberOfFields()){
			throw new IndexOutOfRangeException(pindex,list.getNumberOfFields()-1);
		}
		data[pindex]=pvalue;
	}
	
	@Override
	public Object get(String pname) throws KeyNotFoundException
	{
		Integer index=list.getFieldPosition(pname);
		if(index==null){
			throw new KeyNotFoundException("name of key is '"+pname+"'");
		}
		return data[index];
	}
	
	@SuppressWarnings("unchecked")
	public <T>T get(String pname,Class<T> ptype) throws DataException
	{
		Object object=get(pname);
		if(!ptype.isInstance(object)){
			throw new InvalidTypeException(ptype,object);
		}
		return (T)object;
	}

	@Override
	public Object getId() {
		return null;
	}

	@Override
	public Data getChild(Object pobject) {
		return null;
	}

	@Override
	public Data getParent() {
		return list.getParent();
	}

	/**
	 * Checks if record as a certain field
	 * 
	 * @param key  name of field
	 * @return true The record as a field given by parameter key
	 *         false The record doesn't has such field
	 */
	@Override
	public boolean containsKey(String key) {
		return list.constainsField(key);
	}

	@Override
	public int getSize() {
		return list.getSize();
	}
}
