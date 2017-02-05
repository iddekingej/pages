package org.elaya.page.widget.quickform;

import java.io.IOException;
import java.util.List;

import org.elaya.page.ThemeItemBase;
import org.elaya.page.core.Writer;

public abstract class FormElementThemeItem extends ThemeItemBase {

	public abstract void textElement(Writer pwriter,String pidDom,String pname,Object pvalue,int pmaxLength) throws IOException;
	public abstract void passwordElement(Writer pwriter,String pidDom,String pname,Object pvalue,int pmaxLength) throws IOException;
	public abstract void radioElement(Writer pwriter,String pidDom,String pname,List<OptionItem> pitems,boolean pisHorizontal,Object pvalue) throws IOException;
	public abstract void checkBoxElement(Writer pwriter,String pidDom,String pname,Object pvalue) throws IOException;
	public abstract void staticElement(Writer pwriter,String pidDom,String pname,boolean pisHtml,Object pvalue) throws IOException;
	public abstract void selectElement(Writer pwriter,String pidDom,String pname,List<OptionItem> pitems,Object pvalue) throws IOException;
	public abstract void textArea(Writer pwriter,String pidDom,String pname,String pheight,String pwidth,Object pvalue) throws IOException;
	public abstract void dateElement(Writer pwriter,String pdomId,String pname,Object value) throws IOException;
	public abstract void elementBegin(String pdomId,Writer pwriter,String plabel) throws IOException ;
	public abstract void elementBeginTop(String pdomId,Writer pwriter,String plabel) throws IOException;
	public abstract void elementEnd(Writer pwriter) throws IOException ;

}
