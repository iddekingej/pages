package org.elaya.page.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Url {
	private String url;
	private String query;
	
	public Url(String p_url)
	{
		url=p_url;
		query="";
	}
	
	public Url(String p_url,String p_query)
	{
		url=p_url;
		query=p_query;
	}
	
	void addPath(String p_add){
		if(!url.endsWith("/")){
			url +="/";
		}
		url += p_add;
	}
	
	public Url parameter(String p_name,String p_value) throws UnsupportedEncodingException{
		if(query.length()>0){
			query += "&";
		} else {
			query = "?";
		}
		query=query+URLEncoder.encode(p_name,"UTF-8")+"="+URLEncoder.encode(p_value,"UTF-8");
		return this;
	}	

	public Url copy()
	{
		return new Url(url,query);
	}
	   
	public Url copy(String p_name,Integer p_value) throws UnsupportedEncodingException{
		return copy(p_name,p_value.toString());
	}
	public Url copy(String p_name,String p_value) throws UnsupportedEncodingException
	{
		Url l_url=new Url(url,query);
		l_url.parameter(p_name, p_value);
		return l_url;
	}
	
	String getUrlText()
	{
		return url+query;
	}

}
