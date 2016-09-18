package org.elaya.page;

import java.io.IOException;

public abstract  class LayoutThemeItem extends ThemeItemBase {

	public LayoutThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	public abstract void HorizontalHeader(Writer p_writer) throws IOException;
	public abstract void HorizontalItemHeader(Writer p_writer,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException;
	public abstract void HorizontalItemFooter(Writer p_writer) throws IOException;
	public abstract void HorizontalFooter(Writer p_writer) throws IOException;
	public abstract void verticalHeader(Writer p_writer) throws IOException;
	public abstract void verticalItemHeader(Writer p_writer,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight)throws IOException;
	public abstract void verticalItemFooter(Writer p_writer) throws IOException;
	public abstract void verticalFooter(Writer p_writer) throws IOException;
	public abstract void gridHeader(Writer p_writer) throws IOException;
	public abstract void gridRowHeader(Writer p_writer) throws IOException;
	public abstract void gridItemHeader(Writer p_writer,String p_classPrefix,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException;
	public abstract void gridItemFooter(Writer p_writer) throws IOException;
	public abstract void gridRowFooter(Writer p_writer) throws IOException;
	public abstract void gridFooter(Writer p_writer) throws IOException;

}
