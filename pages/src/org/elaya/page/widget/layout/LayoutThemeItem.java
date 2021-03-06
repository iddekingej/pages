package org.elaya.page.widget.layout;

import java.io.IOException;

import org.elaya.page.HorizontalAlign;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.VerticalAlign;
import org.elaya.page.core.Writer;

public abstract  class LayoutThemeItem extends ThemeItemBase {

	public LayoutThemeItem(){
		super();
	}

	public abstract void horizontalHeader(Writer pwriter) throws IOException;
	public abstract void horizontalItemHeader(Writer pwriter,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight) throws IOException;
	public abstract void horizontalItemFooter(Writer pwriter) throws IOException;
	public abstract void horizontalFooter(Writer pwriter) throws IOException;
	public abstract void verticalHeader(Writer pwriter) throws IOException;
	public abstract void verticalItemHeader(Writer pwriter,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight)throws IOException;
	public abstract void verticalItemFooter(Writer pwriter) throws IOException;
	public abstract void verticalFooter(Writer pwriter) throws IOException;
	public abstract void gridHeader(Writer pwriter) throws IOException;
	public abstract void gridRowHeader(Writer pwriter) throws IOException;
	public abstract void gridItemHeader(Writer pwriter,String pclassPrefix,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight) throws IOException;
	public abstract void gridItemFooter(Writer pwriter) throws IOException;
	public abstract void gridRowFooter(Writer pwriter) throws IOException;
	public abstract void gridFooter(Writer pwriter) throws IOException;
	public abstract void tableHeader(Writer writer,String classNamePrefix) throws IOException;
	public abstract void tableRowHeader(Writer writer) throws IOException;
	public abstract void tableCellHeader(Writer writer,String classNamePrefix,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight) throws IOException;
	public abstract void tableCellFooter(Writer writer) throws IOException;
	public abstract void tableRowFooter(Writer writer) throws IOException;
	public abstract void tableFooter(Writer writer) throws IOException;
}
