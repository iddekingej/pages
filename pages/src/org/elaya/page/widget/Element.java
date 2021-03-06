package org.elaya.page.widget;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors;
import org.elaya.page.HorizontalAlign;
import org.elaya.page.NamedObject;
import org.elaya.page.Theme;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.UniqueNamedObjectList;
import org.elaya.page.VerticalAlign;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.DuplicateElementOnPage;
import org.elaya.page.Errors.InvalidElement;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.UniqueNamedObjectList.DuplicateItemName;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.AttributeDecl;
import org.elaya.page.core.Data;
import org.elaya.page.core.DynamicMethod;
import org.elaya.page.core.JSWriter;
import org.elaya.page.core.KeyNotFoundException;
import org.elaya.page.core.Writer;
import org.elaya.page.data.*;
import org.elaya.page.data.XMLBaseDataItem.XMLDataException;
import org.elaya.page.formula.FormulaException;
import org.elaya.page.formula.FormulaNode;
import org.elaya.page.formula.FormulaParser;
import org.elaya.page.widget.jsplug.JSPlug;
import org.elaya.page.widget.jsplug.JSPlug.InvalidJsPlugType;
import org.elaya.page.xml.AfterSetup;
import org.json.JSONException;
import org.xml.sax.SAXException;



public abstract class Element<T extends ThemeItemBase> extends DynamicMethod implements NamedObject,AfterSetup{
	
	public static class InvalidVariableNameException extends Exception{
		private static final long serialVersionUID = -5605663946752398380L;
		public InvalidVariableNameException(String message){
			super(message);
		}
	}
		
	public static class DisplayException extends Exception{
		private static final long serialVersionUID=2L;
		public DisplayException(String message,Throwable cause){
			super(message,cause);
		}
		public DisplayException(Throwable cause){
			super(cause);
		}
	}
	
	protected T themeItem;
	protected Theme theme;
	private LinkedList<Element<?>> elements=new LinkedList<>();
	private LinkedList<JSPlug> jsPlugs=new LinkedList<>();
	private Element<?> parent=null;
	private String name="";
	private HorizontalAlign horizontalAlign=HorizontalAlign.LEFT;
	private VerticalAlign  verticalAlign=VerticalAlign.TOP;
	private String layoutWidth;
	private String layoutHeight;
	private DataLayer dataModel;
	private String condition="";
	private FormulaNode conditionNode;
	private String jsCondition="";
	private boolean isNamespace=false; 
	private UniqueNamedObjectList<Element<?>> byName=null;
	private Element<?> namespaceParent=null;
	private boolean isWidgetParent=true;

	public Element()
	{
	}
	
	public void validateElement()
	{
		
	}
	
	private void validateAnnotation() throws ValidateError 
	{
		try{
			Field[] fields=getClass().getDeclaredFields();

			for(Field field:fields){
				field.setAccessible(true);
				AttributeDecl attr=field.getAnnotation(AttributeDecl.class);
				if(attr!=null){
					if(attr.mandatory()){
						if(field.get(this)==null){
							throw new ValidateError(field.getName()+" is mandatory,but not set");
						}
					}
				}
				field.setAccessible(false);
			}
		}catch(ValidateError e){
			throw e;
		}catch(Exception e){
			throw new ValidateError(e.getMessage(),e);
		}
	}
	public void validate() throws ValidateError 
	{
		validateElement();
		validateAnnotation();
		for(Element<?> element:elements){
			element.validate();
		}
	}
	
	public void afterSetup() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		
	}
	
	public void setIsWidgetParent(boolean flag)
	{
		isWidgetParent=flag;
	}
	
	public boolean getIsWidgetParent()
	{
		return isWidgetParent;
	}
	
	
	public void setIsNamespace(boolean pisNamespace)
	{
		isNamespace=pisNamespace;
	}
	
	public boolean getIsNamespace()
	{
		return isNamespace;
	}
	
	void setNamespaceParent(Element<?> pnamespaceParent)
	{
		namespaceParent=pnamespaceParent;
	}
	
	public Element<?> getNamespaceParent()
	{
		return namespaceParent;
	}
	
	public void addByName(Element<?> pelement) throws DuplicateItemName
	{
		if(byName==null){
			byName=new UniqueNamedObjectList<>();
		}
		pelement.setNamespaceParent(this);
		byName.put(pelement);
	}
	
	
	public boolean hasByName(Element<?> element){
		if(byName==null){
			return false;
		}
		String elementName=element.getName();
		if(!elementName.isEmpty()){
			return byName.containsKey(elementName);
		}
		return false;
	}
	public Element<?> getByName(String pname)
	{
		if(byName==null){
			return null;
		}
		return byName.get(pname);
	}
	
	public void setJsCondition(String pcondition)
	{		
		jsCondition=pcondition;
	}
	
	public String getJsCondition()
	{
		return jsCondition;
	}
	
	public void setClassPrefix(String pclassPrefix)
	{
		themeItem.setClassPrefix(pclassPrefix);		
	}
	
	public String getClassPrefix()
	{
		return themeItem.getClassPrefix();
	}
	
	public void setCondition(String pcondition)
	{
		condition=pcondition;
		conditionNode=null;
	}
	
	public String getConidition()
	{
		return condition;
	}
	
	public boolean checkCondition(Data pdata) throws KeyNotFoundException, FormulaException 
	{ 
		if(condition.length()==0){
			return true;
		}
		if(conditionNode == null){
			FormulaParser parser=new FormulaParser(condition);
			conditionNode=parser.parseFormula();
		}
		Object value=conditionNode.calculate(pdata);
		return value != null && value.equals(true);
	}
	
	public void calculateData(MapData pdata) throws XMLDataException, KeyNotFoundException, FormulaException {
		MapData data=pdata;
		if(dataModel != null){
			data=dataModel.processData(pdata);
		}
		for(Element<?>element:elements){
			if(element.checkCondition(data)){
				element.calculateData(data);
			}
		}
	}
	
	public void setDataModel(DataLayer pdataModel)
	{
		dataModel=pdataModel;
	}
	
	public DataLayer getDataModel()
	{
		return dataModel;
	}
	
	public Data getData(Data pdata)
	{
		Data data=null;
		if(dataModel != null){
			data=pdata.getChild(dataModel);				
		}
		if(data != null){
			return data;
		}
		return pdata;
	}
	
	public Object getValueByName(Data pdata) throws KeyNotFoundException  
	{
		return pdata.get(name);
	}
	
	public void setLayoutWidth(String pwidth)
	{
		layoutWidth=pwidth;
	}
	
	public String getLayoutWidth()
	{
		return layoutWidth;
	}
	
	
	public void setLayoutHeight(String pheight)
	{
		layoutHeight=pheight;
	}
	
	public String getLayoutHeight()
	{
		return layoutHeight;
	}
	
	public HorizontalAlign getHorizontalAlign()
	{
		return horizontalAlign;
	}
	
	public VerticalAlign getVerticalAlign()
	{
		return verticalAlign;
	}
	
	public void setHorizontalAlign(HorizontalAlign palign)
	{
		horizontalAlign=palign;
	}
	
	public void setVerticalAlign(VerticalAlign palign)
	{
		verticalAlign=palign;
	}
	

	public String getDomId(int id)
	{
		return "element_"+id;
	}
		
	public void setName(String pname)
	{
		name=pname;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getFullName()
	{
		return name;
	}
	
	public String getJsName(int id)
	{
		if(name.length()==0){
			return "E"+id;
		}
		return name;
	}

	public String getRefName()
	{
		if(parent==null){
			return name;
		} else{
			return parent.getRefName()+"."+name;
		}
	}
	
	public void getAllCssFiles(Set<String> pfiles)
	{
		addCssFile(pfiles);
		for(Element<?> element:elements){
			element.getAllCssFiles(pfiles);
		}
	}
	
	public void getAllJsFiles(Set<String> pfiles)
	{
		addJsFile(pfiles);
		for(Element<?> element:elements){
			element.getAllJsFiles(pfiles);
		}		
	}
	public void addJsFile(Set<String> pset)
	{ 
		
	}
	public void addCssFile(Set<String> pfiles)
	{
			themeItem.getCssFiles(pfiles);
	}
	
	public Element<?> getParent(){ return parent;}
	
	void setParent(Element<?> pparent){
		Objects.requireNonNull(pparent);
		parent=pparent;
	}
	
	public final Element<?> getWidgetParent()
	{
		if(getIsWidgetParent()){
			return this;
		}
			
		if(parent != null){
			return parent.getWidgetParent();
		}
		return null;
	}
	
	protected void preElement(int id,Writer writer,Data data,Element<?> element) throws IOException, KeyNotFoundException 
	{
		
	}
	protected void postElement(int id,Writer writer,Data data,Element<?> element) throws IOException
	{		
	}
	/**
	 * Display all child elements from this element.
	 * 
	 * @param id        Element unique ID
	 * @param pwriter   Output writer
	 * @param pdata     Data from data model
	 * 
	 */
	public void displayChildElements(int id,Writer pwriter,Data pdata) throws DisplayException  
	{
		try{
			for(Element<?> element:elements)
			{
				if(element.checkCondition(pdata)){
					preElement(id,pwriter,pdata,element);
					element.display(pwriter,pdata);
					postElement(id,pwriter,pdata,element);
				}
			}
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	/** 
	 * Generate the element HTML and JS. JS is not displayed directly, 
	 * but buffered in jswriter. Javascript is displayed at the end of the page.
	 * 
	 * @param writer output writer for displaying html
	 * @param parentData data container from the parent
	 */
	public final void display(Writer writer,Data parentData) throws DisplayException{
		try{
			int id=writer.newId();
			Data data=getData(parentData);			
			displayElement(id,writer,data);
			generateElementJs(id,writer.getJSWriter(),data);
			displayChildElements(id,writer,data);
			displayElementFooter(id,writer,data);
			generateElementFooterJs(id,writer.getJSWriter(),data);
		} catch(Exception e){
			throw new DisplayException(e);
		}
	}
	
	protected String getJsClassName()
	{
		return "TElement";
	}
	
	protected void makeSetupJs(JSWriter writer,Data data) throws ReplaceVarException, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, JSONException  
	{
		
	}
	
	protected void preSubJs(int id,JSWriter writer,Data data)  
	{
		
	}
	
	protected void postSubJs(JSWriter writer,Data data)  
	{
		
	}	
	
	protected void generateElementJs(int id,JSWriter writer,Data data) throws ReplaceVarException, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, JSONException, KeyNotFoundException
	{
		writer.print("element=new "+getJsClassName()+"(widgetParent,"+writer.toJsString(getJsName(id))+","+writer.toJsString(name)+","+writer.toJsString(getDomId(id))+");\n");
		if(isNamespace){
			writer.setVar("element.isNamespace","true");			
		}

		writer.print("element.config=function(){");
		makeSetupJs(writer,data); 
		for(JSPlug plug:jsPlugs){
			plug.display(writer,data);
		}		
		writer.print("}\n");
		if(!jsCondition.isEmpty()){
			writer.printNl("element.checkCondition=function(data){\n");
			writer.printNl("this.display("+jsCondition+");");
			writer.printNl("}");
		}
		writer.print("element.setup();\n");		
		if(!elements.isEmpty()){
			if(isWidgetParent){				
				writer.setFromOther("widgetParent","element");
			}
			writer.printNl("{");
			writer.printNl("let element;\n");		
		}
	}
	
	protected void generateElementFooterJs(int id,JSWriter writer,Data data) 
	{
		if(!elements.isEmpty()){
			writer.printNl("}");
			if(isWidgetParent){
				writer.printNl("element.afterSetup();\n");
				writer.setFromOther("widgetParent","widgetParent.parent");
			}
		}
	}
	
	
	public abstract void displayElement(int id,Writer writer,Data data) throws DisplayException;
	public void displayElementFooter(int id,Writer writer,Data data) throws DisplayException
	{
		
	}
	
	public abstract String getThemeName();
	
	/**
	 * Setup theme handling object 
	 * 
	 * @param ptheme Theme object.
	 */
	@SuppressWarnings("unchecked")
	public final void setTheme(Theme ptheme) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		Objects.requireNonNull(ptheme);
		theme=ptheme;
		ThemeItemBase newThemeItem=ptheme.getThemeItem(getThemeName());
		Objects.requireNonNull(newThemeItem,"themeItem=>setTheme");
		themeItem=(T) newThemeItem;
	}
	
	protected void checkSubElement(Element<ThemeItemBase> pelement)
	{
	}
	
	public final  void addJsPlug(JSPlug pplug) throws InvalidJsPlugType{
		Objects.requireNonNull(pplug);
		pplug.setParent(this);
		jsPlugs.add(pplug);
	}
	public final void addElement(Element<?> pelement) throws InvalidElement, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, DuplicateElementOnPage, DuplicateItemName 
	{
		Objects.requireNonNull(theme,"In element "+getClass().getName());
		Objects.requireNonNull(pelement,"addElement(pelement)");
		if(!checkElement(pelement)){
			throw new Errors.InvalidElement(pelement,this);
		}
		

		pelement.setTheme(theme);
		pelement.setParent(this);
		elements.add(pelement);
		Element<?> namespace;
		if(isNamespace){
			namespace=this;			
		} else {
			namespace=namespaceParent;			
		}
		pelement.setNamespaceParent(namespace);
		if(!pelement.getName().isEmpty()){	
			namespace.addByName(pelement);
		}
	} 
	
	public final  List<Element<?>> getElements()
	{
		return elements;
	} 
	
	protected boolean checkElement(Element<?> element){
		return false;
	}
}
