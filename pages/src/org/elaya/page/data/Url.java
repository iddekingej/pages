package org.elaya.page.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Url {
	private String url;
	private String query;
	
	public Url(String purl)
	{
		url=purl;
		query="";
	}
	
	public Url(String purl,String pquery)
	{
		url=purl;
		query=pquery;
	}
	
	void addPath(String padd){
		if(!url.endsWith("/")){
			url +="/";
		}
		url += padd;
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
		return new Url(url,query);
	}
	   
	public Url copy(String pname,Integer pvalue) throws UnsupportedEncodingException{
		return copy(pname,pvalue.toString());
	}
	public Url copy(String pname,String pvalue) throws UnsupportedEncodingException
	{
		Url newUrl=new Url(url,query);
		newUrl.parameter(pname, pvalue);
		return newUrl;
	}
	
	String getUrlText()
	{
		return url+query;
	}

}
