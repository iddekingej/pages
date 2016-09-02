package org.elaya.page;

import java.io.IOException;

public abstract  class LayoutThemeItem extends ThemeItemBase {

	public LayoutThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	public abstract void HorizontalHeader() throws IOException;
	public abstract void HorizontalItemHeader() throws IOException;
	public abstract void HorizontalItemFooter() throws IOException;
	public abstract void HorizontalFooter() throws IOException;
	public abstract void verticalHeader() throws IOException;
	public abstract void verticalItemHeader()throws IOException;
	public abstract void verticalItemFooter() throws IOException;
	public abstract void verticalFooter() throws IOException;
	public abstract void gridHeader() throws IOException;
	public abstract void gridRowHeader() throws IOException;
	public abstract void gridItemHeader() throws IOException;
	public abstract void gridItemFooter() throws IOException;
	public abstract void gridRowFooter() throws IOException;
	public abstract void gridFooter() throws IOException;

}
