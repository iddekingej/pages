package org.elaya.page.defaultTheme;


import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.UrlResource;

public class PageThemeItem extends org.elaya.page.PageThemeItem {

	public PageThemeItem(HttpServletResponse p_response) throws IOException {
		super(p_response);
	}
	
	@Override
	public void pageHeader() throws IOException{
		print("<html>\n<head>\n<link "+property("href","/Gallerie/resources/form.css") +" rel=\"stylesheet\" type=\"text/css\"></head>\n<body>\n");
	}

	@Override
	public void pageFooter()throws IOException  {
		print("</body></html>");

	}

}
