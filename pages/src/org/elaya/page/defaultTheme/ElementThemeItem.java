package org.elaya.page.defaultTheme;

import java.io.IOException;

import org.elaya.page.Theme;

public class ElementThemeItem extends org.elaya.page.element.ElementThemeItem {

	public ElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	@Override
	public void staticElement(String p_text, boolean p_isHtml, String p_class, String p_css) throws IOException {
		print("<div "+propertyF("class",p_class)+propertyF("style",p_css)+">");
		if(p_isHtml){
			print(p_text);
		} else {
			print(escape(p_text));
		}
		print("</div>");

	}

	@Override
	public void image(String p_url, String p_class, String p_css) throws IOException {
		print("<img "+property("src",p_url)+propertyF("class",p_class)+propertyF("style",p_css)+">");

	}

	@Override
	public void panelHeader(String p_class, String p_css) throws IOException {
		print("<div "+propertyF("class",p_class)+propertyF("style",p_css)+">");

	}

	@Override
	public void panelFooter() throws IOException {
		print("</div>");
	}

	@Override
	public void link(String p_url, String p_text, String p_class, String p_css) throws IOException {
		print("<a "+property("href",p_url)+propertyF("class",p_class)+propertyF("style",p_css)+">"+escape(p_text)+"</a>");
	}

	@Override
	public void verticalSpacer() throws IOException {
		print("<div style='width:100%'>&nbsp;</div>");
	}

}
