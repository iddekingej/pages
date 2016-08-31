package org.elaya.page.table;

public interface DynamicData {
	public Object get(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	public void put(String p_name,Object p_value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	public boolean containsKey(String p_name);
}
