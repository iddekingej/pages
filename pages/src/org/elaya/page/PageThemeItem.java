package org.elaya.page;

import java.io.IOException;
import java.util.Set;


public abstract class PageThemeItem extends ThemeItemBase {
	public abstract void pageHeader(Writer pwriter,DocumentType pdocumentType,Set<String> pjs,Set<String> pcss) throws IOException;
	public abstract void pageFooter(Writer pwriter) throws IOException ;
}
