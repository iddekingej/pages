package org.elaya.page;

import java.util.HashMap;
import java.util.List;

import org.elaya.page.application.Application;

public class PageLoader {

	private HashMap<String,Page> pageCache=new HashMap<>(); 

/**
 * Called after parser is created. This routine can be used to set for example initializers
 * 
 * @param pparser
 */
	protected void initUiParser(UiXmlParser pparser)
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
 * @throws Exception  
 */
	
	
	
	public synchronized Page loadPage(Application application,String pfileName,boolean pcache) throws Exception
	{
		if(pcache && pageCache.containsKey(pfileName)){
			return pageCache.get(pfileName);
		}
		UiXmlParser parser=new UiXmlParser(application,getClass().getClassLoader());
		initUiParser(parser);
		Object object=parser.parse(pfileName);

		
		List<String> errors=parser.getErrors();
		if(!errors.isEmpty()){
			StringBuilder text=new StringBuilder();
			for(String error:errors){
				text.append(pfileName+":"+error+"\n");
			}
			throw new Errors.LoadingPageFailed(text.toString());
		}
		Page page;
		if(object instanceof Page){
			page=(Page)object;	
		}   else {
			throw new Errors.LoadingPageFailed("File "+pfileName+" contains not a Page but a '"+object.getClass().getName()+"'");
		}
		if(pcache){
			pageCache.put(pfileName, page);
		}
		return page;
	}

}
