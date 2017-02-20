package org.elaya.page.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.ElementVariant;
import org.elaya.page.ElementVariantList;
import org.elaya.page.ElementVariantParser;
import org.elaya.page.Errors;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.core.Url;
import org.elaya.page.PageLoader;
import org.elaya.page.widget.Page;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.SAXException;

public abstract class Application{

	private String themeBase="org.elaya.page.defaultheme";	
	private String aliasFiles;
	private String elementVariantFiles;
	private ElementVariantList elementVariants;
	private HashMap<String,AliasData> aliases;
	private String xmlPath="../pages/"; 
	private String defaultDBConnection;
	private String classBase="";
	private PageLoader pageLoader;
	private String imageURL="resources/pages/images/";
	private String jsPath="resources/pages/js/";
	private String cssPath="resources/pages/css/"; 
	/**
	 *  If XML files are inside the WAR or on a extenral path 
	 *  true=internal false=external
	 */
	private boolean externalXML=false;
	
	public class DefaultDBConnectionNotSet extends Exception
	{
		private static final long serialVersionUID = 7128805697742441199L;
		public DefaultDBConnectionNotSet()
		{
			super("Default DB connection(defaultDBConnection) not set");
		}
	}
	
	public class InvalidAliasType extends Exception
	{

		private static final long serialVersionUID = -7739276897593055425L;

		public InvalidAliasType(String pname,AliasNamespace ptypeReq,AliasNamespace ptypeGot)
		{
			super("Invalid alias '"+pname+" type, '"+ptypeReq+"' expected but '"+ptypeGot+"' found");
		}
	}
	
	
	public void setExternalXML(Boolean pexternalXML)
	{
		externalXML=pexternalXML;
	}
	
	public Boolean getExternalXML()
	{
		return externalXML;
	}
	
	public void setup() throws LoadingAliasFailed, XMLLoadException, IllegalAccessException
	{
		loadAliasFiles();
		processElementVariantFiles();
		initPageLoader();

	}
	
	public void setJSPath(String pjsPath)
	{
		jsPath=pjsPath;
	}
	
	public String getJSPath()
	{
		return jsPath;
	}
	
	public void setCSSPath(String pcssPath)
	{
		cssPath=pcssPath;
	}
	
	public String getCSSPath()
	{
		return cssPath;
	}
	
	/**
	 * Base Url of iamges
	 * 
	 */
	
	public void setImageUrl(String url)
	{
		imageURL=url;		
	}
	
	public String getImageUrl()
	{
		return imageURL;
	}
	

	/**
	 * When loading a xml file, "classBase" is added to the classname when classname is relative
	 * ("start with a ".")
	 *  
	 * @param pclassBase
	 */
	public void setClassBase(String pclassBase)
	{
		classBase=pclassBase;
	}
	
	public String getClassBase()
	{
		return classBase;
	}
	public void setDefaultDBConnection(String pdefaultDBConnection)
	{
		defaultDBConnection=pdefaultDBConnection;
	}
	
	public String getDefaultDBConnection()
	{
		return defaultDBConnection;
	}
	
	public void setXmlPath(String ppath)
	{
		xmlPath=ppath;
	}
	
	public String getXmlPath()
	{
		return xmlPath;
	}
	//--(xml parsing )-------------------
	
	public String normalizeClassName(String name,AliasNamespace ptype) throws NormalizeClassNameException 
	{
		if(name.charAt(0)=='.'){
			return classBase+name;
		} else if(name.charAt(0)=='@'){
			try{
				return getAlias(name.substring(1), ptype);
			}catch(LoadingAliasFailed|InvalidAliasType   e){
				throw new Errors.NormalizeClassNameException("Normalizing class name "+name+"failed",e);
			}
		} 
		return name;
	}
	
	//--(db)------------------------------------------------------------------------
	
	public abstract DriverManagerDataSource getDB(String pname);
	
	public DriverManagerDataSource getDefaultDB() throws DefaultDBConnectionNotSet 
	{
		if(defaultDBConnection == null ||defaultDBConnection==""){
			throw new DefaultDBConnectionNotSet();
		}
		return getDB(defaultDBConnection);
	}
	
	//--( PageLoader )-------------------------------------------
	
	protected void setPageLoader(PageLoader ppageLoader)
	{
		pageLoader=ppageLoader;
	}
	
	protected PageLoader getPageLoader()
	{
		return pageLoader;
	}
	
	protected void initPageLoader()
	{
		pageLoader=new PageLoader(this);
	}
/**
 * Parse UI definition file in pfileName and returns the Page Object
 * The XML ui definition must describe a page.
 * When pcache=true the page object is cached. When this function is called
 * again with the same filename and pcache=true than a cached copy is returned.
 * 
 * @param pfileName  XML describing the page
 * @param pcache     Cache Page object 
 * @return            Page object representing page
 * @throws XMLLoadException 
 * @throws IOException 
 * @throws Exception  
 */
	
	
	public synchronized Page loadPage(String pfileName,boolean pcache) throws XMLLoadException, IOException 
	{	
		return pageLoader.loadPage( pfileName, pcache);

	}
	
	public String getRealConfigPath(String pfileName)
	{
		String fileName=pfileName;
		if(fileName.charAt(0)!='/'){
			fileName=xmlPath+fileName;
		} 
		return fileName;
	}
	
	private long getInternalModificationTime(String pfileName) throws IOException
	{
		URL url=getClass().getResource(getRealConfigPath(pfileName));
		return url.openConnection().getLastModified();
	}
	
	private long getExternalModificationTime(String pfileName)
	{
		
		return new File(getRealConfigPath(pfileName)).lastModified();
	}
	
	public long getConfigLastModified(String pfileName) throws IOException
	{
		if(!externalXML){
			return getInternalModificationTime(pfileName);
		} else if(pfileName.startsWith("internal:")){
			return getInternalModificationTime(pfileName.substring(9));
		}
		return getExternalModificationTime(pfileName);
	}

	private InputStream getInternalConfigStream(String pfileName) throws FileNotFoundException
	{
		String fileName=getRealConfigPath(pfileName);
		InputStream stream=getClass().getResourceAsStream(fileName);
		if(stream==null){
			throw new FileNotFoundException(pfileName+"("+fileName+")");
		}
		return stream;
	}
	
	private InputStream getExternalConfigStream(String pfileName) throws FileNotFoundException
	{
		return new FileInputStream(getRealConfigPath(pfileName));
	}
	
	public InputStream getConfigStream(String pfileName) throws FileNotFoundException
	{
		if(!externalXML){
			return getInternalConfigStream(pfileName);
		} else if(pfileName.startsWith("internal:")){
			return getInternalConfigStream(pfileName.substring(9));
		}
		return getExternalConfigStream(pfileName);
	}
	
	public void setElementVariantFiles(String files)
	{
		elementVariantFiles=files;
	}
	
	public String getElementVariantFiles()
	{
		return elementVariantFiles;
	}
	
	private void processElementVariantFiles() throws XMLLoadException{
		//elementVariants=new ElementVariantList();
		String[] files=elementVariantFiles.split(",");
		for(String variantFile:files){
			ElementVariantParser variantParser=new ElementVariantParser();
			variantParser.setApplication(this);
			elementVariants=variantParser.parse(variantFile,ElementVariantList.class);
		}
	}
	
	public ElementVariant getVariantByName(String name) throws XMLLoadException
	{
		return elementVariants.get(name,null);
	}
/**
 * Load aliases in fileName and add them to global alias list
 * 
 * @param fileName add aliases in xml file
 * @throws LoadingAliasFailed 
 * @throws IllegalAccessException 
 * @throws IllegalArgumentException 
 */
	private void loadAliasFile(String fileName) throws LoadingAliasFailed,  IllegalAccessException    
	{
		AliasParser parser=new AliasParser(this,aliases);
		try{		
			parser.parse(fileName);
		} catch(Exception e){
			throw new Errors.LoadingAliasFailed("Loading alias file "+fileName+" failed",e);
		}

	}
	
	public void setAliasFiles(String paliasFiles) 
	{
		aliasFiles=paliasFiles;
	}
	
	/**
	 * aliasFiles is a comma delimited list of files to load.
	 * This routine iterates through this list and loads each file.
	 * 	
	 * @throws LoadingAliasFailed
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private void loadAliasFiles() throws LoadingAliasFailed,  IllegalAccessException 
	{
		aliases=new HashMap<>();
		String[] fileNames=aliasFiles.split(",");
		for(String fileName:fileNames){
			loadAliasFile(fileName);
		}
	}
	
	public Map<String,AliasData> getAliases() throws LoadingAliasFailed  
	{
		return aliases;
	}
	 
	public String getAlias(String pname,AliasNamespace ptype) throws LoadingAliasFailed, InvalidAliasType  
	{
		AliasData data=aliases.get(pname);
		if(data != null){			
			if(data.getType() != ptype){
				throw new InvalidAliasType(pname,ptype,data.getType());
			}
			return data.getValue();
		}
		return null;
	}
	
	public String getAlias(String pname,AliasNamespace ptype,boolean pmandatory) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, Errors.AliasNotFound, LoadingAliasFailed 
	{
		String returnValue=getAlias(pname,ptype);
		if((returnValue==null) && pmandatory){
			throw new Errors.AliasNotFound(pname);
		}
		return returnValue;
	}

	public Url getUrlByAlias(String pname) throws ParserConfigurationException, SAXException, IOException,  InvalidAliasType, Errors.AliasNotFound, LoadingAliasFailed 
	{
		return new Url(getAlias(pname,AliasNamespace.URL,true));
	}
	
	public String getThemeBase(){
		return themeBase;
	}
	
	public void setThemeBase(String pbase)
	{
		themeBase=pbase;
	}

}
