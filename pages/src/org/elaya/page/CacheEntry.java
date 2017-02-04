package org.elaya.page;

import java.util.LinkedList;
import java.util.List;

/**
 *  This class is used for caching items and 
 *  detecting changed file by last modification time
 */
public class CacheEntry<T> {
	
/**
 *  Information about files on which the item depends
 */
	
	LinkedList<FileModification> files=new LinkedList<>();  
	/**
	 * Object to cache
	 */
	private T item;
	
		
	public CacheEntry(T pitem)
	{
		item=pitem;
	}		
		
	public  List<FileModification> getFiles()
	{
		return files;
	}
	


	
	public void addFile(String pfileName,long pmodificationTime)
	{
		files.add(new FileModification(pfileName,pmodificationTime));
	}
	
	/**
	 * Getting the cached item
	 * 
	 * @return returns the cached item
	 */
	public T getItem()
	{
		return item;
	}
}
