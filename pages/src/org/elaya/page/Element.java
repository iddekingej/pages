package org.elaya.page;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import org.elaya.page.Errors;
import org.elaya.page.Errors.ValueNotFound;
import org.elaya.page.data.*;
import org.elaya.page.jsplug.JSPlug;
import org.elaya.page.jsplug.JSPlug.InvalidJsPlugType;

public abstract class Element<themeType extends ThemeItemBase> extends DynamicMethod {
	protected themeType themeItem;
	protected Theme theme;
	protected LinkedList<Element<?>> elements=new LinkedList<>();
	protected LinkedList<JSPlug> jsPlugs=new LinkedList<>();
	private   Element<?> parent=null;

	private int id=-1;
	private String name="";
	private HorizontalAlign horizontalAlign=HorizontalAlign.left;
	private VerticalAlign  verticalAlign=VerticalAlign.top;
	private String layoutWidth;
	private String layoutHeight;
	private DataModel dataModel;
	private String condition="";
	
	public void setClassPrefix(String p_classPrefix)
	{
		themeItem.setClassPrefix(p_classPrefix);		
	}
	
	public String getClassPrefix()
	{
		return themeItem.getClassPrefix();
	}
	
	public void setCondition(String p_condition)
	{
		condition=p_condition;
	}
	
	public String getConidition()
	{
		return condition;
	}
	
	public boolean checkCondition(Data p_data) throws ValueNotFound, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{ 
		if(condition.length()==0) return true;
		if(!p_data.containsKey(condition)){
			throw new Errors.ValueNotFound(condition);
		}
		Object l_value=p_data.get(condition);
		return l_value.equals(true);
	}
	
	public void calculateData(MapData p_data) throws UnsupportedEncodingException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, ValueNotFound{
		MapData l_data=p_data;
		if(dataModel != null){
			l_data=dataModel.processData(p_data);
		}
		for(Element<?>l_element:getElements()){
			if(l_element.checkCondition(l_data)){
				l_element.calculateData(l_data);
			}
		}
	}
	
	protected String replaceVariables(Data p_data,String p_string) throws Exception
	{
		int l_pos=0;
		int l_newPos;
		StringBuilder l_return=new StringBuilder();
		String l_varName;
		Object l_value;
		String l_string=(p_string==null?"":p_string);		
		while(true){
			l_newPos=l_string.indexOf("${",l_pos);
			if(l_newPos==-1){
				l_return.append(l_string.substring(l_pos));
				break;
			}
			l_return.append(l_string.substring(l_pos,l_newPos));
			l_pos=l_string.indexOf("}",l_newPos);
			if(l_pos==-1){
				throw new Exception("Missing end }"); //TODO: Error Location				
			}
			l_varName=l_string.substring(l_newPos+2,l_pos);	
			if(!p_data.containsKey(l_varName)){
				throw new Exception("Variable '"+l_varName+"' not found in data:"+p_data.getClass().getName());
			} else {
				l_value=p_data.get(l_varName);				
				if(l_value != null)	l_return.append(l_value.toString());
			}
			l_pos++;
		}
		return l_return.toString();
	}
	
	public void setDataModel(DataModel p_dataModel)
	{
		dataModel=p_dataModel;
	}
	
	public DataModel getDataModel()
	{
		return dataModel;
	}
	
	public Data getData(Data p_data)
	{
		Data l_data=null;
		if(dataModel != null){
			l_data=p_data.getChild(dataModel);			
		}
		if(l_data != null) return l_data;
		return p_data;
	}
	
	public Object getValueByName(Data p_data) throws ValueNotFound, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		if(p_data.containsKey(getName())){
			return p_data.get(getName());
		} else {
			throw new Errors.ValueNotFound(getName());
		}
	}
	
	public Element()
	{
	}
	
	public void process()
	{
		id=getPage().newId();//TODO check is getPage =null
		for(Element<?> l_element:getElements()){
			l_element.process();
		}
	}
	
	public void setLayoutWidth(String p_width)
	{
		layoutWidth=p_width;
	}
	
	public String getLayoutWidth()
	{
		return layoutWidth;
	}
	
	
	public void setLayoutHeight(String p_height)
	{
		layoutHeight=p_height;
	}
	
	public String geLayoutHeight()
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
	
	public void setHorizontalAlign(HorizontalAlign p_align)
	{
		horizontalAlign=p_align;
	}
	
	public void setVerticalalign(VerticalAlign p_align)
	{
		verticalAlign=p_align;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getDomId()
	{
		return "element_"+id;
	}
	

	
	public void setName(String p_name)
	{
		name=p_name;
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

	public String getJsFullname() throws IOException
	{
		if(parent==null) return getJsName();
		String l_parent=getWidgetParent().getJsFullname();
		if(l_parent.length()==0){
			return getJsName();
		}
		return l_parent+".elements."+getJsName();
	}
	
	public void getAllCssFiles(Set<String> p_files)
	{
		addCssFile(p_files);
		for(Element<?> l_element:elements){
			l_element.getAllCssFiles(p_files);
		}
	}
	
	public void getAllJsFiles(Set<String> p_files)
	{
		addJsFile(p_files);
		for(Element<?> l_element:elements){
			l_element.getAllJsFiles(p_files);
		}		
	}
	
	public void addJsFile(Set<String> p_set)
	{ 
		
	}
	
	public void addCssFile(Set<String> p_files)
	{
			themeItem.getCssFiles(p_files);
	}
	
	public String getObjectName()
	{
		return "object_"+Integer.toString(id);
	}
	public String getVarname()
	{
		return "var_"+Integer.toString(id);
	}
	
	public Element<?> getParent(){ return parent;}
	
	void setParent(Element<?> p_parent) throws IOException{
		Objects.requireNonNull(p_parent);
		parent=p_parent;
	}
	public Element<?> getFirstWidget()
	{
		return this;
	}
	
	public Element<?> getWidgetParent()
	{
		return getParent().getFirstWidget();
	}
	
	public Page getPage()
	{
		Element<?> l_current=this;
		while(l_current != null){
			if(l_current instanceof Page){
				return (Page)l_current;
			}
			l_current=l_current.getParent();
		}
		return null;
	}
	
	
	protected void preElement(Writer p_writer,Element<?> p_element) throws IOException, Exception
	{
		
	}
	protected void postElement(Writer p_writer,Element<?> p_element) throws IOException, Exception
	{		
	}
	
	public void displaySubElements(Writer p_writer,Data p_data) throws Exception
	{
		for(Element<?> l_element:getElements())
		{
			if(l_element.checkCondition(p_data)){
				preElement(p_writer,l_element);
				l_element.display(p_writer,p_data);
				postElement(p_writer,l_element);
			}
		}
	}
	
	public abstract void display(Writer p_stream,Data p_data) throws Exception;
	public abstract String getThemeName();
	
	@SuppressWarnings("unchecked")
	final public void setTheme(Theme p_theme) throws Exception
	{
		theme=p_theme;
		ThemeItemBase l_theme=p_theme.getTheme(getThemeName());
		Objects.requireNonNull(l_theme,"themeItem=>setTheme");
		themeItem=(themeType)p_theme.getTheme(getThemeName());
	}
	
	protected void checkSubElement(Element<ThemeItemBase> p_element)
	{
	}
	
	final public void addJsPlug(JSPlug p_plug) throws InvalidJsPlugType{
		Objects.requireNonNull(p_plug);
		p_plug.setParent(this);
		jsPlugs.add(p_plug);
	}
	final public void addElement(Element<?> p_element) throws Exception
	{
	
		Objects.requireNonNull(p_element,"addElement(p_element)");
		if(!checkElement(p_element)){
			throw new Errors.InvalidElement(p_element,this);
		}
		p_element.setTheme(theme);
		p_element.setParent(this);
		elements.add(p_element);
		if(p_element.getName().length()>0){
			getPage().addToNameIndex(p_element);
		}

	}
	
	protected String getJsClassName()
	{
		return "TElement";
	}
	
	protected void makeSetupJs(Writer p_writer,Data p_data) throws Exception
	{
		
	}
	
	protected void makeJsObject(Writer p_writer,Data p_data) throws Exception
	{
		p_writer.print("var l_element=new "+getJsClassName()+"("+getWidgetParent().getJsFullname()+","+p_writer.js_toString(getJsName())+","+p_writer.js_toString(getName())+","+p_writer.js_toString(getDomId())+");\n");
		p_writer.print("l_element.config=function(){");
		makeSetupJs(p_writer,p_data);
		for(JSPlug l_plug:jsPlugs){
			l_plug.display(p_writer);
		}		
		p_writer.print("}\n l_element.setup();\n");
	}
	
	
	protected void preSubJs(Data p_data) throws Exception
	{
		
	}
	
	protected void postSubJs(Data p_data) throws Exception
	{
		
	}
	
	final protected void generateJs(Writer p_writer,Data p_data) throws IOException, Exception
	{
		Data l_data=getData(p_data);
		makeJsObject(p_writer,l_data);
		preSubJs(l_data);
		for(Element<?> l_element:getElements()){
			if(this.checkCondition(p_data)){
				l_element.generateJs(p_writer,l_data);
			}
		}
		postSubJs(l_data);

	}
	
	final public LinkedList<Element<?>> getElements()
	{
		return elements;
	} 
	
	public boolean checkElement(Element<?> p_element){
		return false;
	}
}
