package org.elaya.page.filter;

import org.elaya.page.core.PageSession;
import org.elaya.page.data.MapData;
import org.elaya.page.view.PageView;


public class ViewAction extends Action {

	
	private String pageFile;
	private boolean cache=true;

	public void setPageFile(String ppageFile)
	{
		pageFile=ppageFile;
	}
	
	public String getPageFile()
	{
		return pageFile;
	}
	
	public void setCache(Boolean pcache){
		cache=pcache;
	}
	
	boolean getCache()
	{
		return cache;
	}
	@Override
	public ActionResult execute(PageSession psession) throws ActionException{
		try{
			PageView view=new PageView(pageFile,getApplication());
			view.setCache(cache);
			MapData data=new MapData();
			view.render(data, psession);		
			return ActionResult.NONEXTFILTER;
		}catch(Exception e){
			throw new ActionException(e);
		}
	}


}
