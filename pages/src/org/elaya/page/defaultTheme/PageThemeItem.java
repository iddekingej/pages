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
		//print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"> ");
		print("<html>\n<head>\n");
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
