package org.elaya.page.spring;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.PageMode;
import org.elaya.page.application.Application;
import org.elaya.page.core.PageSession;
import org.elaya.page.data.MapData;
import org.elaya.page.view.PageView;
import org.springframework.web.servlet.view.AbstractView;

public class SpringPageView extends AbstractView {

	private PageView pageView;
	
	public SpringPageView(PageMode mode,String file,Application application) {
		super(); 
		pageView=new PageView(mode,file,application);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> pmap, HttpServletRequest prequest, HttpServletResponse presponse)
			throws Exception {
		MapData md=new MapData("___TOP",null);
		md.setByMap(pmap);
		
		pageView.render(md,new PageSession(prequest,presponse));	
	}

}
