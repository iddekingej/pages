package org.elaya.page.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.ElementVariant;
import org.elaya.page.ElementVariantList;
import org.elaya.page.ElementVariantParser;
import org.elaya.page.Errors;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.core.DataException;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.Url;
import org.elaya.page.data.XMLBaseDataItem.XMLDataException;
import org.elaya.page.formula.FormulaException;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.PageLoader;
import org.elaya.page.UniqueNamedObjectList.DuplicateItemName;
import org.elaya.page.application.Route.InvalidRouteTypeException;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.widget.Page;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;
/**
 * Application object
 * 
 * This object is the glue of the system
 */
public class Application{

	private String themeBase="org.elaya.page.defaultheme";	
	private String aliasFiles;
	private String elementVariantFiles;
	private String routerFiles;
	private LinkedList<Router> routers=new LinkedList<>();
	private ElementVariantList elementVariants;
	private DatabaseConnections databaseConnections=new DatabaseConnections(); 
	private HashMap<String,AliasData> aliases;
	private String xmlPath="../pages/"; 
	private String defaultDBConnection;
	private String classBase="";
	private PageLoader pageLoader;
	private String imageBaseURL="resources/images/";
	private String jsBaseUrl="resources/js/";
	private String cssBaseUrl="resources/css/"; 
	/**
	 *  If XML files are inside the WAR or on a external path 
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
	
	/**
	 * Setup Application after the application object is created
	 * In this method the alias, router, element variant and router files are read.
	 */
	public void setup() throws LoadingAliasFailed, XMLLoadException, IllegalAccessException
	{
		loadAliasFiles();
		processElementVariantFiles();
		initPageLoader();
		loadRouter();
	}
	
	public void setRouterFiles(String prouterFiles)
	{
		routerFiles=prouterFiles;
	}
	
	public String getRouterFiles()
	{
		return routerFiles;
	}
	
	//--(JDBC DB )-----------------------------------//
	
	/**
	 * Add JDBC connection to list.
	 * List is indexed by the name, and name must be unique.
	 * Connections can be configured in the application xml file  with
	 * the "databaseconnection"  node.
	 * 
	 * @param pconnection
	 * @throws DuplicateItemName
	 */
	public void addDatabaseConnection(DatabaseConnection pconnection) throws DuplicateItemName
	{
		databaseConnections.addConnection(pconnection);
	}
	
	
	//TODO Exception when key not found.
	/**
	 * Connect to a named jdbc connection.
	 * 
	 * @param pname Name of conenction. Null for default connection.
	 * @return JDBC connection 
	 */
	
	public Connection connectToDB(String pname) throws SQLException, ClassNotFoundException
	{
		if(pname==null){
			return connectToDefaultDB();
		}  
		DatabaseConnection db=databaseConnections.getConnection(pname);
		if(db==null){
			return null;
		} 
		return db.connect();
	}
	
	//TODO Check if defaultDBConnection is set
	
	public Connection connectToDefaultDB() throws ClassNotFoundException, SQLException
	{
		return connectToDB(this.defaultDBConnection);
	}
	
	//--(Router )-------------------------------//
	
	public void loadRouter() throws XMLLoadException
	{
		if(routerFiles != null){
			String[] files=routerFiles.split(",");
			for(String file:files){
				RouterParser routerParser=new RouterParser();
				routerParser.setApplication(this);
				routers.add(routerParser.parse(file,Router.class));			
			}
		}
	}
	
	public void routeRequest(HttpServletRequest prequest,HttpServletResponse presponse) throws InvalidRouteTypeException, IOException, DisplayException, SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, XMLLoadException, ReceiverException, DataException, ClassNotFoundException, FormulaException, XMLDataException 
	{
		presponse.setCharacterEncoding("utf-8");
		for(Router router:routers){
			if(router.handleRoute(new PageSession(prequest,presponse))){
				return;
			}
		}
	}
	
	public void setJSBaseUrl(String pjsPath)
	{
		jsBaseUrl=pjsPath;
	}
	
	public String getJSBaseUrl()
	{
		return jsBaseUrl;
	}
	
	public void setCSSBaseUrl(String pcssPath)
	{
		cssBaseUrl=pcssPath;
		
	}
	
	public String getCSSBaseUrl()
	{
		return cssBaseUrl;
	}
	
	/**
	 * Base Url of images
	 * 
	 */
	
	public void setImageBaseUrl(String url)
	{
		imageBaseURL=url;		
	}
	
	public String getImageBaseUrl()
	{
		return imageBaseURL;
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
		if(name.isEmpty()){
			return name;
		}else if(name.charAt(0)=='.'){
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
	
	/**
	 * Get last modification date of a application xml configuration file
	 *  
	 * @param pfileName name of file
	 * @return Time (unix time stamp)
	 */
	
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
		if(elementVariantFiles != null){
			String[] files=elementVariantFiles.split(",");
			for(String variantFile:files){
				ElementVariantParser variantParser=new ElementVariantParser();
				variantParser.setApplication(this);
				elementVariants=variantParser.parse(variantFile,ElementVariantList.class);
			}
		}
	}
	
	public ElementVariant getVariantByName(String name) throws XMLLoadException
	{
		if(elementVariants != null){
			return elementVariants.get(name,null);
		}
		return null;
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
