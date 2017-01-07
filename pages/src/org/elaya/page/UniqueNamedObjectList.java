package org.elaya.page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UniqueNamedObjectList<T extends NamedObject> implements Iterable<Map.Entry<String,T>>  {
	public static class DuplicateItemName extends Exception
	{
		private static final long serialVersionUID = -1181845088854983923L;
		public DuplicateItemName(String itemName){
			super("Duplicate item name '"+itemName+"'");
		}
	}
	
	private HashMap<String,T> items=new HashMap<>();

	public void put(T item) throws DuplicateItemName
	{
		if(items.containsKey(item)){
			throw new DuplicateItemName(item.getFullName());
		}
		items.put(item.getFullName(), item);
	}
	
	public T get(String key){
		return items.get(key);
	}
	
	public T get(String key,T defaultValue)
	{
		if(items.containsKey(key)){
			return get(key);
		}
		return defaultValue;
	}
	
	public boolean containsKey(String key)
	{
		return items.containsKey(key);
	}
	
	@Override
	public Iterator<Map.Entry<String,T>> iterator() {
		return items.entrySet().iterator();
	}
	
	
	
}
