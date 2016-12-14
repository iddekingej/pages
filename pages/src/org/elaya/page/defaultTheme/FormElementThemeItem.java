package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.elaya.page.*;
import org.elaya.page.quickform.OptionItem;

public class FormElementThemeItem extends org.elaya.page.quickform.FormElementThemeItem {

	@Override
	public void textElement(Writer pwriter,String pidDom,String pname,Object pvalue,int pmaxLength) throws IOException
	{
		pwriter.print("<input type='text' style='width:100%' "+property("id",pidDom)+property("name",pname)+property("value",pvalue)+propertyF("maxlength",(pmaxLength>0?String.valueOf(pmaxLength):""))+"/>");
	}
	@Override
	public void passwordElement(Writer pwriter,String pidDom,String pname,Object pvalue,int pmaxLength) throws IOException
	{
		pwriter.print("<input type='password' style='width:100%' "+property("id",pidDom)+property("name",pname)+property("value",pvalue)+propertyF("maxlength",(pmaxLength>0?String.valueOf(pmaxLength):""))+"/>");
	}
	
	
	public void selectElementHeader(Writer pwriter,String pidDom,String pname) throws IOException
	{
		pwriter.print("<select  "+property("id",pidDom)+property("name",pname)+">");
	}
	
	public void selectElementOption(Writer pwriter,Object pvalue,String ptext,boolean pselected) throws IOException
	{
		pwriter.print("<option "+property("value",pvalue)+(pselected?"selected='1'":"")+">"+escape(ptext)+"</option>");
	}
	
	public void selectElementFooter(Writer pwriter) throws IOException
	{
		pwriter.print("</select>");
	}
	public void selectElement(Writer pwriter,String pidDom,String pname,LinkedList<OptionItem> pitems,Object pvalue) throws IOException
	{		
		selectElementHeader(pwriter,pidDom,pname);
		for(OptionItem item:pitems){
			selectElementOption(pwriter,item.getValue(),item.getText(),item.getValue().equals(pvalue));
		}
		selectElementFooter(pwriter);
	}

	
	public void radioOption(Writer pwriter,String pidDom,String pname,Object pvalue,String ptext,boolean pselected) throws IOException{
		pwriter.print("<input "+property("id",pidDom)+property("type","radio")+property("name",pname)+property("value",pvalue)+(pselected?"checked='1'":"")+"/>");
		pwriter.print("<label "+property("for",pidDom)+">"+escape(ptext)+"</label>");
	}
	
	
	public void radioElement(Writer pwriter,String pidDom,String pname,LinkedList<OptionItem> pitems,boolean pisHorizontal,Object pvalue) throws IOException
	{
		Iterator<OptionItem> iter=pitems.iterator();
		OptionItem item;
		int cnt=0;
		while(iter.hasNext()){
			item=iter.next();
			radioOption(pwriter,pidDom+"_"+cnt,pname,item.getValue(),item.getText(),item.getValue().equals(pvalue));
			if(!pisHorizontal){
				pwriter.print("<br/>");
			}
			cnt++;
		}		
	}
	@Override

	public void checkBoxElement(Writer pwriter,String pidDom,String pname,Object pvalue) throws IOException
	{
		pwriter.print("<input "+property("id",pidDom)+property("name",pname)+property("type","checkbox")+((pvalue.toString().length()>0)?"checked='1'":"")+"/>");
	}
	
	
	@Override
	public void textArea(Writer pwriter,String pidDom, String pname, String pheight, String pwidth, Object pvalue)
			throws IOException {
		String css="";
		if(pwidth.length()>0){
			css += "width:"+pwidth+";";
		}
		if(pheight.length()>0){
			css += "height:"+pheight+";";
		}
		pwriter.print("<textarea "+property("id",pidDom)+property("name",pname)+property("style",css)+">"+escape(pvalue)+"</textarea>");
	}
	
	@Override
	public void dateElement(Writer pwriter,String pdomId,String pname,Object pvalue) throws IOException
	{
		pwriter.print("<input type='text' "+property("id",pdomId)+property("value",str(pvalue))+"/>");
	}

	@Override
	public void elementBegin(String pdomId,Writer pwriter,String plabel) throws Exception
	{
		pwriter.print("<tr "+property("id",pdomId)+"><td "+property("class","pages_elementLabel")+">"+escape(plabel)+"</td><td class=\"pages_elementValue\">");
		
	}

	@Override
	public void elementBeginTop(String pdomId,Writer pwriter,String plabel) throws Exception
	{
		pwriter.print("<tr "+property("id",pdomId)+"><td "+property("class","pages_elementLabel")+">"+escape(plabel)+"</td colspan='2'></tr><tr><td class=\"pages_elementValue\" colspan='2'>");
	}
	
	@Override
	public void elementEnd(Writer pwriter) throws Exception
	{
		pwriter.print("</td></tr>\n");
	}
	@Override
	public void radioElement(Writer pwriter, String pidDom, String pname, List<OptionItem> pitems,
			boolean pisHorizontal, Object pvalue) throws IOException {
		Iterator<OptionItem> iter=pitems.iterator();
		OptionItem item;
		int cnt=0;
		while(iter.hasNext()){
			item=iter.next();
			radioOption(pwriter,pidDom+"_"+cnt,pname,item.getValue(),item.getText(),item.getValue().equals(pvalue));
			if(!pisHorizontal){
				pwriter.print("<br/>");
			}
			cnt++;
} 		
	}
	@Override
	public void staticElement(Writer pwriter, String pidDom, String pname, boolean pisHtml, Object pvalue)
			throws IOException {
		pwriter.print("<div "+property("id",pidDom)+">");
		if(pisHtml){
			pwriter.print(pvalue.toString());
		} else {
			pwriter.print(escape(pvalue.toString()));
		}
		pwriter.print("</div>");		
	}
	@Override
	public void selectElement(Writer writer, String idDom, String name, List<OptionItem> items, Object value)
			throws IOException {
		selectElementHeader(writer,idDom,name);
		for(OptionItem l_item:items){
			selectElementOption(writer,l_item.getValue(),l_item.getText(),l_item.getValue().equals(value));
		}
		selectElementFooter(writer);		
	}

	
}
