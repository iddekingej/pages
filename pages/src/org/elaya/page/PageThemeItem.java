package org.elaya.page;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.core.Writer;
/**
 * Theme for displaying HTML page (header and footer)
 *
 */

public abstract class PageThemeItem extends ThemeItemBase {
	/**
	 * Display page header
	 * 
	 * @param writer       Output writer
	 * @param documentType Document type (html4/xhtml etc...)
	 * @param js           List of js files
	 * @param css          List of css files
	 * 
	 */
	public abstract void pageHeader(Writer writer,DocumentType documentType,Set<String> js,Set<String> css) throws IOException;
	/**
	 * Display page footer
	 * @param writer       Output writer
	 */
	public abstract void pageFooter(Writer writer) throws IOException ;
}
