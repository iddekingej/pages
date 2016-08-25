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
	public void pageHeader(Set<String> p_js) throws IOException{
		Iterator<String> l_iter=p_js.iterator();
		print("<html>\n<head>\n<link "+property("href","/Gallerie/resources/form.css") +" rel=\"stylesheet\" type=\"text/css\">");
		while(l_iter.hasNext()){
			jsInclude(l_iter.next());
		}
		print("</head>\n<body>\n");
	}

	@Override
	public void pageFooter()throws IOException  {
		print("</body></html>");

	}

}
