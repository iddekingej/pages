package org.elaya.page;

import java.io.IOException;
import java.util.Set;


public abstract class PageThemeItem extends ThemeItemBase {
	abstract public void pageHeader(Writer p_writer,DocumentType p_documentType,Set<String> p_js,Set<String> p_css) throws IOException;
	abstract public void pageFooter(Writer p_writer) throws IOException ;
}
