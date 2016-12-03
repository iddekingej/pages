package org.elaya.page.defaultTheme;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.elaya.page.Theme;
import org.elaya.page.Writer;

public class PageThemeItem extends org.elaya.page.PageThemeItem {

	@Override
	public void pageHeader(Writer p_writer,Set<String> p_js,Set<String> p_css) throws IOException{
		Iterator<String> l_iter=p_js.iterator();
		//print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\"> ");
		p_writer.print("<html>\n<head>\n");
		while(l_iter.hasNext()){
			p_writer.jsInclude(p_writer.getJsPath(l_iter.next()));
		}
		l_iter=p_css.iterator();
		while(l_iter.hasNext()){
			p_writer.cssInclude(p_writer.getCssPath(l_iter.next()));
		}
		p_writer.print("</head>\n<body><div id='pageContainer'>\n");
	}

	@Override
	public void pageFooter(Writer p_writer)throws IOException  {
		p_writer.print("</div></body></html>");

	}

}
