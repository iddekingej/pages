package org.elaya.page.filter;

import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.elaya.page.core.PageSession;

public class SendResourceAction extends Action {
	private String baseFolder;
	private int    fromPosition=0;
	
	public String getBaseFolder()
	{
		return baseFolder;
	}
	
	public void setBaseFolder(String pbaseFolder)
	{
		baseFolder=pbaseFolder;
	}
	
	public void setFromPosition(Integer pfromPosition)
	{
		fromPosition=pfromPosition;
	}
	
	public int getFromPosition()
	{
		return fromPosition;
	}
	
	@Override
	public ActionResult execute(PageSession session) throws ActionException {
		try{
			int cnt=fromPosition;
			String uri=session.getURIPath();
			String path=baseFolder;
			while(cnt<=uri.length() && uri.charAt(cnt)=='/'){
				cnt++;
			}
			if(cnt<=uri.length()){
				path += uri.substring(cnt);
			}
			InputStream res=getClass().getClassLoader().getResourceAsStream(path);
			if(res==null){
				session.setNotFound();
			} else {
				IOUtils.copy(res,session.getOutputStream());
				res.close();
			}
		} catch(Exception e){
			throw new ActionException(e);
		}
		return ActionResult.NONEXTFILTER;
	}

}
