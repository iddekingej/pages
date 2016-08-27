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
	
}
