package org.elaya.page.defaulttheme;


import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.elaya.page.DocumentType;
import org.elaya.page.Writer;

public class PageThemeItem extends org.elaya.page.PageThemeItem {

	@Override
	public void pageHeader(Writer pwriter,DocumentType pdocumentType,Set<String> pjs,Set<String> pcss) throws IOException{
		Iterator<String> iter=pjs.iterator();
		String header="";
		String htmlTag="<html>";
		String contentType="text/html";
		switch(pdocumentType){
			case DT_HTML4_STRICT:
					header="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
					break;	
			case DT_HTML4_LOOSE:
					header="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
					break;
			case DT_HTML4_FRAMESET:
				header="<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">";
				break;
			case DT_XHTML:
				header=
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n"+
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""+
					" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
			 		htmlTag="<html xmlns=\"http://www.w3.org/1999/xhtml\">";
			 		contentType="application/xhtml+xml";
			 	break;
		}
		pwriter.setContentType(contentType);
		pwriter.print(header+"\n"+htmlTag+"\n<head>\n");
		while(iter.hasNext()){
			pwriter.jsInclude(pwriter.getJsPath(iter.next()));
		}
		iter=pcss.iterator();
		while(iter.hasNext()){
			pwriter.cssInclude(pwriter.getCssPath(iter.next()));
		}
		pwriter.print("</head>\n<body><div id='pageContainer'>\n");
	}

	@Override
	public void pageFooter(Writer pwriter)throws IOException  {
		pwriter.print("</div></body></html>");

	}

}
