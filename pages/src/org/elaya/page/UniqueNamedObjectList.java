package org.elaya.page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Many objects have a name and must be unique
 * The object is added to a hash list. The name is derived by the getFullName method.
 * This method is defined in NamedObject  class
 * 
 * @param <T> type of object
 */
public class UniqueNamedObjectList<T extends NamedObject> implements Iterable<Map.Entry<String,T>>  {
	public static class DuplicateItemName extends Exception
	{
		private static final long serialVersionUID = -1181845088854983923L;
		public DuplicateItemName(String itemName){
			super("Duplicate item name '"+itemName+"'");
		}
	}
	
	private HashMap<String,T> items=new HashMap<>();

	/**
	 * Put item in hashmap.
	 * The key of the hashmap is the name of object (object.getFullName())
	 * When there is allready a object with the same name in the class, a 
	 * exception is raised.
	 * 
	 * @param item
	 * @throws DuplicateItemName
	 */
	
	public void put(T item) throws DuplicateItemName
	{
		if(items.containsKey(item)){
			throw new DuplicateItemName(item.getFullName());
		}
		items.put(item.getFullName(), item);
	}
	/**
	 * Get object by name
	 * 
	 * @param key
	 * @return
	 */
	
	public T get(String key){
		return items.get(key);
	}
	
	/**
	 * Get object by name, if the object doesn't exists
	 * the default value is returned.
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public T get(String key,T defaultValue)
	{
		if(items.containsKey(key)){
			return get(key);
		}
		return defaultValue;
	}
	
	/**
	 * Check is there is a object with a certain name. 
	 * 
	 * @param pname name to check
	 * @return true: There is an object with this name false: There not a object with this name 
	 */
	public boolean containsKey(String pname)
	{
		return items.containsKey(pname);
	}
	
	/**
	 * Iterates through the objects
	 */
	@Override
	public Iterator<Map.Entry<String,T>> iterator() {
		return items.entrySet().iterator();
	}
	
	
	
}
