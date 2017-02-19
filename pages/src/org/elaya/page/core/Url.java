package org.elaya.page.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Url {
	private String urlText;
	private String query;
	
	public Url(String url)
	{
		urlText=url;
		query="";
	}
	
	public Url(String url,String pquery)
	{
		urlText=url;
		query=pquery;
	}
	
	void addPath(String padd){
		if(!urlText.endsWith("/")){
			urlText +="/";
		}
		urlText += padd;
	}
	
	public Url parameter(String pname,Integer pvalue) throws UnsupportedEncodingException 
	{
		return parameter(pname,String.valueOf(pvalue));
	}
	public Url parameter(String pname,String pvalue) throws UnsupportedEncodingException{
		if(query.length()>0){
			query += "&";
		} else {
			query = "?";
		}
		query=query+URLEncoder.encode(pname,"UTF-8")+"="+URLEncoder.encode(pvalue,"UTF-8");
		return this;
	}	

	public Url copy()
	{
		return new Url(urlText,query);
	}
	   
	public Url copy(String pname,Integer pvalue) throws UnsupportedEncodingException{
		return copy(pname,pvalue.toString());
	}
	public Url copy(String pname,String pvalue) throws UnsupportedEncodingException
	{
		Url newUrl=new Url(urlText,query);
		newUrl.parameter(pname, pvalue);
		return newUrl;
	}
	
	public String getUrlText()
	{
		return urlText+query;
	}

	@Override
	public String toString()
	{
		return getUrlText();
	}
}
