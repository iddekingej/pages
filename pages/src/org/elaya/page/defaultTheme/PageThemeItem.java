package org.elaya.page.defaultTheme;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.elaya.page.Theme;

public class PageThemeItem extends org.elaya.page.PageThemeItem {

	public PageThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}
	
	@Override
	public void pageHeader(Set<String> p_js,Set<String> p_css) throws IOException{
		Iterator<String> l_iter=p_js.iterator();
		print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		print("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n");
		while(l_iter.hasNext()){
			jsInclude(l_iter.next());
		}
		l_iter=p_css.iterator();
		while(l_iter.hasNext()){
			cssInclude(l_iter.next());
		}
		print("</head>\n<body><div id='pageContainer'>\n");
	}

	@Override
	public void pageFooter()throws IOException  {
		print("</div></body></html>");

	}

}
