package org.elaya.page.widget.element;

import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.Attribute;
import org.elaya.page.core.AttributeDecl;
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
	@AttributeDecl(type=Float.class)
	private Attribute minLat;
	@AttributeDecl(type=Float.class)
	private Attribute maxLat;
	@AttributeDecl(type=Float.class)
	private Attribute minLon;
	@AttributeDecl(type=Float.class)
	private Attribute maxLon;
	
	public void setGpxUrl(String pgpxUrl)
	{
		gpxUrl=pgpxUrl;
	}
	
	public String setGpxUrl()
	{
		return gpxUrl;
	}
	
	public void setMinLat(Attribute pminLat)
	{
		minLat=pminLat;
	}
	
	public Attribute getMinLat()
	{
		return minLat;
	}
	
	public void setMaxLat(Attribute pmaxLat)
	{
		maxLat=pmaxLat;
	}
	
	public Attribute getMaxLat()
	{
		return maxLat;
	}
	
	public void setMinLon(Attribute pminLon)
	{
		minLon=pminLon;
	}
	
	public Attribute getMinLon()
	{
		return minLon;
	}
	
	public void setMaxLon(Attribute pmaxLon)
	{
		maxLon=pmaxLon;
	}
	
	public Attribute getMaxLon()
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
	protected void makeSetupJs(JSWriter pwriter,Data pdata) throws  ReplaceVarException, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, JSONException 
	{
		float minLatVal;
		float maxLatVal;
		float minLonVal;
		float maxLonVal;
		try{
			minLatVal=(Float)minLat.getValue(pdata);
			maxLatVal=(Float)maxLat.getValue(pdata);
			minLonVal=(Float)minLon.getValue(pdata);
			maxLonVal=(Float)maxLon.getValue(pdata);
		}catch(Exception e)
		{
			throw new ReplaceVarException(e.getMessage(),e); 
		}

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
