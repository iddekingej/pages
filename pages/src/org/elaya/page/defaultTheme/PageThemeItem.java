package org.elaya.page.defaultTheme;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.elaya.page.DocumentType;
import org.elaya.page.Writer;

public class PageThemeItem extends org.elaya.page.PageThemeItem {

	@Override
	public void pageHeader(Writer p_writer,DocumentType p_documentType,Set<String> p_js,Set<String> p_css) throws IOException{
		Iterator<String> l_iter=p_js.iterator();
		String l_header="";
		String l_htmlTag="<html>";
		String l_contentType="text/html";
		switch(p_documentType){
			case DT_HTML4_STRICT:l_header="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";break;	
			case DT_HTML4_LOOSE:l_header="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";break;
			case DT_HTML4_FRAMESET:l_header="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">";break;
			case DT_XHTML:l_header=
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n"+
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""+
					" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
			 		l_htmlTag="<html xmlns=\"http://www.w3.org/1999/xhtml\">";
			 		l_contentType="application/xhtml+xml";
			break;
				
		}
		p_writer.setContentType(l_contentType);
		p_writer.print(l_header+"\n"+l_htmlTag+"\n<head>\n");
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
