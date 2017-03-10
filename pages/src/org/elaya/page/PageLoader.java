package org.elaya.page;

import java.util.HashMap;
import java.io.IOException;
import org.elaya.page.application.Application;
import org.elaya.page.widget.Page;
import org.elaya.page.widget.ValidateError;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;

public class PageLoader {

	private Application application;
	private HashMap<String,CacheEntry<Page>> pageCache=new HashMap<>(); 

	public PageLoader(Application papplication)
	{
		application=papplication;
	}
	
/**
 * Called after parser is created. This routine can be used to set for example initializers
 * 
 * @param pparser
 */
	protected void initUiParser(ElementParser pparser)
	{
		
	}
	
	
	private Page getPageFromCache(String pfileName) throws IOException
	{
		long modificationTime;
		if(pageCache.containsKey(pfileName)){
			CacheEntry<Page> cacheItem=pageCache.get(pfileName);
			for(FileModification depFil:cacheItem.getFiles()){
				modificationTime=application.getConfigLastModified(depFil.getFileName());				
				if(modificationTime>depFil.getModificationTime()){
					return null;
				}
			}
			return cacheItem.getItem();
		}
		return null;
	}
	
/**
 * Load new page from xml definition.
 * When page needs to be cached create new cache entry 
 * 	
 * @param pfileName
 * @param pcache
 * @return Loaded page from xml ui file
 * @throws ValidateError 
 */
	private Page loadNewPage(String pfileName,boolean pcache) throws XMLLoadException, IOException
	{
		Page page;
		ElementParser parser=new ElementParser();
		parser.setApplication(application);
		initUiParser(parser);		
		page=parser.parse(pfileName,Page.class);	
		try{
			page.validate();
		}catch(Exception e){
			throw new XMLLoadException(e.getMessage(),e,null);
		}
		if(pcache){
			CacheEntry<Page> entry=new CacheEntry<>(page);
			for(String depFile:parser.getFiles()){
				entry.addFile(depFile,application.getConfigLastModified(depFile));
			}
			pageCache.put(pfileName, entry);
		}
		return page;
	}
	
/**
 * Parse UI definition file in pfileName and returns the Page Object
 * The XML ui definition must describe a page.
 * When pcache=true the page object is cached. When this function is called
 * again with the same filename and pcache=true than a cached copy is returned.
 * 
 * @param pfileName  XML describing the page
 * @param pcache     Cache Page object 
 * @return            Page object representing page
 * @throws XMLLoadException 
 * @throws IOException 
 * @throws Exception  
 */
	

	
	public synchronized Page loadPage(String pfileName,boolean pcache) throws XMLLoadException, IOException 
	{
		Page page;
		
		if(pcache && pageCache.containsKey(pfileName)){
			page=getPageFromCache(pfileName);
			if(page != null){
				return page;
			}
		
		}
	
		return loadNewPage(pfileName,pcache);
	}

}
