package org.elaya.page.widget.element;

import java.io.IOException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.Data;
import org.elaya.page.core.JSWriter;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.PageElement;
import org.json.JSONException;
import org.xml.sax.SAXException;
public class OLMap extends PageElement<ElementThemeItem> {
	private String gpxUrl;
	private String lineColor;
	private String divWidth="300";
	private String divHeight="300";
	private String minLat;
	private String maxLat;
	private String minLon;
	private String maxLon;
	
	public void setGpxUrl(String pgpxUrl)
	{
		gpxUrl=pgpxUrl;
	}
	
	public String setGpxUrl()
	{
		return gpxUrl;
	}
	
	public void setMinLat(String pminLat)
	{
		minLat=pminLat;
	}
	
	public String getMinLat()
	{
		return minLat;
	}
	
	public void setMaxLat(String pmaxLat)
	{
		maxLat=pmaxLat;
	}
	
	public String getMaxLat()
	{
		return maxLat;
	}
	
	public void setMinLon(String pminLon)
	{
		minLon=pminLon;
	}
	
	public String getMinLon()
	{
		return minLon;
	}
	
	public void setMaxLon(String pmaxLon)
	{
		maxLon=pmaxLon;
	}
	
	public String getMaxLon()
	{
		return maxLon;
	}	
	
	public void setLineColor(String plineColor)
	{
		lineColor=plineColor;
	}
	
	public String getLineColor()
	{
		return lineColor;
	}
	
	public void setDivWidth(String pdivWidth)
	{
		divWidth=pdivWidth;
	}
	
	public String getDivWidth()
	{
		return divWidth;
		
	}
	
	public void setDivHeight(String pdivHeight)
	{
		divHeight=pdivHeight;
	}
	
	public String getDivHeight()
	{
		return divHeight;
	}
	
	@Override
	public void addJsFile(Set<String> pfiles)
	{
		pfiles.add("ol.js");
	}
	
	@Override
	public void displayElement(int pid, Writer pwriter, Data pdata) throws DisplayException {
		try{
			int divWidthVal=Integer.parseInt(pwriter.replaceVariables(pdata,divWidth));
			int divHeightVal=Integer.parseInt(pwriter.replaceVariables(pdata,divHeight));
			themeItem.olmapdiv(pwriter,getDomId(pid),divWidthVal,divHeightVal);
		}catch(Exception e){
			throw new DisplayException(e.getMessage(),e);
		}
	}
	
	@Override
	protected void makeSetupJs(JSWriter pwriter,Data pdata) throws IOException, JSONException, ReplaceVarException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed
	{
		float minLatVal=Float.parseFloat(pwriter.replaceVariables(pdata,minLat));
		float maxLatVal=Float.parseFloat(pwriter.replaceVariables(pdata,maxLat));
		float minLonVal=Float.parseFloat(pwriter.replaceVariables(pdata,minLon));
		float maxLonVal=Float.parseFloat(pwriter.replaceVariables(pdata,maxLon));
		String gpxUrlVal=pwriter.processUrl(pdata, gpxUrl);
		pwriter.printNl("this.setSize("+minLatVal+","+maxLatVal+","+minLonVal+","+maxLonVal+");\n");
		if(gpxUrl != null && gpxUrl.length()>0){
			pwriter.printNl("this.setGpxRoute("+gpxUrlVal+");");
		}
		if(lineColor != null && lineColor.length() > 0){
			pwriter.objVar("lineColor", lineColor);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}
	
	@Override
	public String getJsClassName() {
		return "TOLMap";
	}	

}
