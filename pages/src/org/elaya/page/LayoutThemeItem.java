package org.elaya.page;

import java.io.IOException;

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

}
