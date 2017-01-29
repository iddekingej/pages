package org.elaya.page;

import java.io.IOException;
import java.util.Set;


public abstract class PageThemeItem extends ThemeItemBase {
	public abstract void pageHeader(Writer writer,DocumentType documentType,Set<String> js,Set<String> css) throws IOException;
	public abstract void pageFooter(Writer writer) throws IOException ;
}
