package org.elaya.page;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.elaya.page.Errors;
import org.elaya.page.Errors.ValueNotFound;
import org.elaya.page.data.*;
import org.elaya.page.jsplug.JSPlug;
import org.elaya.page.jsplug.JSPlug.InvalidJsPlugType;

public abstract class Element<T extends ThemeItemBase> extends DynamicMethod {
	protected T themeItem;
	protected Theme theme;
	protected LinkedList<Element<?>> elements=new LinkedList<>();
	private LinkedList<JSPlug> jsPlugs=new LinkedList<>();
	private   Element<?> parent=null;
	private int id=-1;
	private String name="";
	private HorizontalAlign horizontalAlign=HorizontalAlign.LEFT;
	private VerticalAlign  verticalAlign=VerticalAlign.TOP;
	private String layoutWidth;
	private String layoutHeight;
	private DataModel dataModel;
	private String condition="";
	private String jsCondition="";
	private boolean isNamespace=false; 
	private HashMap<String,Element<?>> byName=null;
	private Element<?> namespaceParent=null;

	public Element()
	{
	}
	
	void setId(int pid)
	{
		id=pid;
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
	
	public void addByName(Element<?> pelement)
	{
		if(byName==null){
			byName=new HashMap<>();
		}
		pelement.setNamespaceParent(this);
		byName.put(pelement.getJsName(), pelement);
	}
	
	
	public boolean hasByName(Element<?> pelement){
		if(byName==null){
			return false;
		}
		return byName.containsKey(pelement.getJsName());
	}
	
	public Element<?> getByName(String pname)
	{
		if(byName==null){
			return null;
		}
		return byName.get(pname);
	}
	
	public void setJSCondition(String pcondition)
	{
		jsCondition=pcondition;
	}
	
	public String getJSCondition()
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
	}
	
	public String getConidition()
	{
		return condition;
	}
	
	public boolean checkCondition(Data pdata) throws ValueNotFound, NoSuchFieldException, IllegalAccessException
	{ 
		if(condition.length()==0){
			return true;
		}
		if(!pdata.containsKey(condition)){
			throw new Errors.ValueNotFound(condition);
		}
		Object value=pdata.get(condition);
		return value.equals(true);
	}
	
	public void calculateData(MapData pdata) throws Exception{
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
	
	protected String replaceVariables(Data pdata,String pstring) throws Exception
	{
		int pos=0;
		int newPos;
		StringBuilder returnValue=new StringBuilder();
		String varName;
		Object value;
		String string=pstring==null?"":pstring;		
		while(true){
			newPos=string.indexOf("${",pos);
			if(newPos==-1){
				returnValue.append(string.substring(pos));
				break;
			}
			returnValue.append(string.substring(pos,newPos));
			pos=string.indexOf('}',newPos);
			if(pos==-1){
				throw new Exception("Missing end }"); //TODO: Error Location				
			}
			varName=string.substring(newPos+2,pos);	
			if(!pdata.containsKey(varName)){
				throw new Exception("Variable '"+varName+"' not found in data:"+pdata.getClass().getName());
			} else {
				value=pdata.get(varName);				
				if(value != null){
					returnValue.append(value.toString());
				}
			}
			pos++;
		}
		return returnValue.toString();
	}
	
	public void setDataModel(DataModel pdataModel)
	{
		dataModel=pdataModel;
	}
	
	public DataModel getDataModel()
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
	
	public Object getValueByName(Data pdata) throws ValueNotFound, NoSuchFieldException,  IllegalAccessException
	{
		if(pdata.containsKey(name)){
			return pdata.get(name);
		} else {
			throw new Errors.ValueNotFound(name);
		}
	}
	
	public void process()
	{		
		for(Element<?> element:elements){
			element.process();
		}
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
	
	public void setVerticalalign(VerticalAlign palign)
	{
		verticalAlign=palign;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getDomId()
	{
		return "element_"+id;
	}
	

	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getJsName()
	{
		if(name.length()==0){
			return "E"+id;
		}
		return name;
	}

	public String getNamespaceName()
	{				
		if(namespaceParent==null){
			return getJsName();
		}
		String jsName=namespaceParent.getNamespaceName();
		if(isNamespace){
			jsName=jsName+".names."+getJsName();
		}
		return jsName;
	}
	public String getJsFullname() throws IOException
	{
		
		if(parent==null){
			return getJsName();
		}
		String widgetParent=getWidgetParent().getJsFullname();
		if(widgetParent.length()==0){
			return getJsName();
		}
		return widgetParent+".elements."+getJsName();
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
	
	//TODO used?
	public String getObjectName()
	{
		return "object_"+Integer.toString(id);
	}
	//TODO used? 
	public String getVarName()
	{
		return "var_"+Integer.toString(id);
	}
	
	public Element<?> getParent(){ return parent;}
	
	void setParent(Element<?> pparent) throws IOException{
		Objects.requireNonNull(pparent);
		parent=pparent;
	}
	public Element<?> getFirstWidget()
	{
		return this;
	}
	
	public Element<?> getWidgetParent()
	{
		if(parent != null){
			return parent.getFirstWidget();
		}
		return null;
	}
	
	public Page getPage()
	{
		Element<?> current=this;
		while(current != null){
			if(current instanceof Page){
				return (Page)current;
			}
			current=current.getParent();
		}
		return null;
	}
	
	
	protected void preElement(Writer pwriter,Element<?> pelement) throws Exception
	{
		
	}
	protected void postElement(Writer pwriter,Element<?> pelement) throws Exception
	{		
	}
	
	public void displaySubElements(Writer pwriter,Data pdata) throws Exception
	{
		for(Element<?> element:elements)
		{
			if(element.checkCondition(pdata)){
				preElement(pwriter,element);
				element.display(pwriter,pdata);
				postElement(pwriter,element);
			}
		}
	}
	
	public abstract void display(Writer pstream,Data pdata) throws Exception;
	public abstract String getThemeName();
	
	@SuppressWarnings("unchecked")
	public final void setTheme(Theme ptheme) throws Exception
	{
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
	public final void addElement(Element<?> pelement) throws Exception
	{
	
		Objects.requireNonNull(pelement,"addElement(pelement)");
		if(!checkElement(pelement)){
			throw new Errors.InvalidElement(pelement,this);
		}
		pelement.setId(getPage().newId());

		pelement.setTheme(theme);
		pelement.setParent(this);
		elements.add(pelement);
		Element<?> namespace;
		if(isNamespace){
			namespace=this;			
		} else {
			namespace=namespaceParent;			
		}
		if(namespace.hasByName(pelement)){
			throw new Errors.duplicateElementOnPage(pelement.getJsName());//TODO: Duplicate In namespace
		}
		namespace.addByName(pelement);
	} 
	
	protected String getJsClassName()
	{
		return "TElement";
	}
	
	protected void makeSetupJs(Writer pwriter,Data pdata) throws Exception
	{
		
	}
	
	protected void makeJsObject(Writer pwriter,Data pdata) throws Exception
	{
		pwriter.print("var element=new "+getJsClassName()+"("+getWidgetParent().getJsFullname()+","+pwriter.js_toString(getJsName())+","+pwriter.js_toString(name)+","+pwriter.js_toString(getDomId())+");\n");
		if(this.namespaceParent!=null){
			pwriter.print("element.namespaceParent="+this.namespaceParent.getNamespaceName()+";\n");
		}

		pwriter.print("element.config=function(){");
		makeSetupJs(pwriter,pdata); 
		for(JSPlug plug:jsPlugs){
			plug.display(pwriter);
		}		
		pwriter.print("}\n");
		if(byName != null){
			StringBuilder conditionJs=new StringBuilder("");
			Element<?> element;
			for(Entry<String, Element<?>> entry:byName.entrySet()){
				element=entry.getValue();
				if(element.getJSCondition().length()>0){
					conditionJs.append("this.names.")
					.append(element.getJsName())
					.append(".display(")
					.append(element.getJSCondition())
					.append(")\n");
				}
			}
		
			if(conditionJs.length()>0){
				pwriter.print("element.checkCondition=function(){\n "+conditionJs.toString()+";\n}\n");
			}
		}
		pwriter.print("element.setup();\n");
	}
	
	
	protected void preSubJs(Writer pwriter,Data pdata) throws Exception
	{
		
	}
	
	protected void postSubJs(Writer pwriter,Data pdata) throws Exception
	{
		
	}
	
	protected final void generateJs(Writer pwriter,Data pdata) throws Exception
	{
		Data data=getData(pdata);
		makeJsObject(pwriter,data);
		preSubJs(pwriter,data);
		for(Element<?> element:elements){
			if(this.checkCondition(pdata)){
				element.generateJs(pwriter,data);
			}
		}
		postSubJs(pwriter,data);

	}
	
	public final  List<Element<?>> getElements()
	{
		return elements;
	} 
	
	public boolean checkElement(Element<?> element){
		return false;
	}
}
