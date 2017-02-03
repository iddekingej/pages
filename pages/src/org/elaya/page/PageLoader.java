package org.elaya.page;

import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;

public class PageLoader {

	private static final String InputStream = null;
	private Application application;
	private HashMap<String,PageCacheEntry> pageCache=new HashMap<>(); 

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
		long lastModificationTime=application.getConfigLastModified(pfileName);
		if(pcache && pageCache.containsKey(pfileName)){
			PageCacheEntry cacheItem=pageCache.get(pfileName);
			if(cacheItem.getLastModificationTime()>=lastModificationTime){
				return cacheItem.getPage();
			}
			
		}
		ElementParser parser=new ElementParser(application);
		initUiParser(parser);
		Page page;
		page=parser.parse(pfileName,Page.class);
		if(pcache){
			pageCache.put(pfileName, new PageCacheEntry(page,lastModificationTime));
		}
		return page;
	}

}
