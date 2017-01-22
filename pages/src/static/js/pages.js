"use strict"
var pages={		
		page:{
			elements:{},
			names:{},
			parent:null,
			isNamespace:true,
			getNamespaceParent:function(){
				return this;
			},
			_getNamespaceParent:function(){
				return this;
			},
			addElement:function(p_name,p_object){
				this.names[p_name]=p_object;
				this.elements[p_name]=p_object;
			},
			getByName:function(p_name){
				if(p_name in this.byName){
					return this.byName[p_name];
				}
				return null;
			},
			addByName:function(p_object){
				this.names[p_object.jsName]=p_object;
			},
			_initToWindowSize:function(){
				var l_container=$$("pageContainer");
				core.toWindowSize(l_container);
			},
			initToWindowSize:function(){
				core.ev(window,"resize",	function(){
						this._initToWindowSize();
				},this);
				this._initToWindowSize();
			},

			lock:function()
			{
				core.create("div",{
					"id":"locker"
				,	"style":{
						"position":"absolute"
					,	"top":"0px"
					,	"left":"0px"
					,	"opacity":"0.5"
					,	"z-index":999999
					,	"width":"100%"
					,	"height":"100%"
					,	"background-color":"#333"
					}
				}
				,document.body
				)	
			},
			unlock:function(){
				var l_element=$$("locker");
				if(l_element)core.remove(l_element);				
			}
		}
};

//------------( TAbstractElement )-------------------------------
function TAbstractElement(p_parent,p_jsName,p_name)
{
	this.parent=p_parent;
	this.elements={};
	this.names={};
	this.name=p_name;
	this.jsName=p_jsName;
	this.checkCondition=false;	
	this.isNamespace=false;
	this.customEvents={};
}

TAbstractElement.prototype.on=function(p_event,p_js)
{
	if(p_event in this.customEvents){
		this.customEvents[p_event].push(p_js);
	} else {		
		this.customEvents[p_event]=[p_js];
	}
}

TAbstractElement.prototype.isCustomEvent=function(p_event)
{
	return true;
}

TAbstractElement.prototype.customEvent=function(p_event,p_data)
{
	if(p_event in this.customEvents){
		var l_events=this.customEvents[p_event];
		var l_cnt;
		for(l_cnt=0;l_cnt<l_events.length;l_cnt++){
			l_events[l_cnt].call(this,p_data);
		}
	}
}

TAbstractElement.prototype.isInputElement=function()
{
	return false;
}
//TElement.prototype.display=function(p_flag) :Abstract


TAbstractElement.prototype._getNamespaceParent=function()
{
	if(this.isNamespace) return this;
	if(this.parent!=null){
		return this.parent.getNamespaceParent();
	}
	return null;
}

TAbstractElement.prototype.getNamespaceParent=function()
{
	if(this.parent != null){
		return this.parent._getNamespaceParent();
	}
	return null;
}



TAbstractElement.prototype.fillThisData=function(p_data)
{
	if("getValue" in this){
		p_data[this.name]=this.getValue();
	}
}

TAbstractElement.prototype.fillData=function(p_data)
{
	this.fillThisData(p_data);
	for(var l_name in this.names){
		if(this.names[l_name].jsName==this.jsName){			
			throw new Error("Element is added in it's own namespace "+this.jsName);
		} else if(l_name in p_data){
			throw new Error("Duplicate data in data set:"+this.names); 
		}else {
			this.names[l_name].fillData(p_data);
		}
	}
}

TAbstractElement.prototype.addByName=function(p_element)
{
	this.names[p_element.jsName]=p_element;
}

TAbstractElement.prototype.getByName=function(p_element)
{
	if(p_element in this.names){
		return this.names[p_element];
	}
	return null;
}

TAbstractElement.prototype.addElement=function(p_jsName,p_element)
{
	this.elements[p_jsName]=p_element;
}

TAbstractElement.prototype.setup=function()
{
	if("config" in this){
		this.config();
	}
	this.parent.addElement(this.jsName,this);
	
	var l_namespaceParent=this.getNamespaceParent();
	if(l_namespaceParent != null){
		l_namespaceParent.addByName(this);
	}	
}

TAbstractElement.prototype.handleCheckCondition=function()
{
	var l_namespaceParent=this.getNamespaceParent();
	if(this.checkCondition && l_namespaceParent){
		this.checkCondition(l_namespaceParent.names);		
	}
	for(var l_name in this.elements){
		this.elements[l_name].handleCheckCondition();		
	}
}

//------------( TCollectionElement )---------------------

function TCollectionElement(p_parent,p_jsName,p_name,p_idBase)
{
	TAbstractElement.call(this,p_parent,p_jsName,p_name);
	this.id=p_idBase;
	this.elements=[];
	var l_cnt=0;
	var l_element;
	while(true){
		l_element=$$(p_idBase+"_"+l_cnt);
		if(l_element==null){
			break;
		}
		l_element._control=this;
		this.elements.push(l_element);		
		l_cnt++;
	}
}

TCollectionElement.prototype=Object.create(TAbstractElement.prototype);


TCollectionElement.prototype.isCustomEvent=function(p_event)
{
	return false;
}

TCollectionElement.prototype.on=function(p_event,p_js)
{
	if(this.isCustomEvent(p_event)){
		TAbstractElement.prototype.on.call(this,p_event,p_js);
	} else {
		var l_cnt;
		for(l_cnt=0;l_cnt<this.elements.length;l_cnt++){
			core.ev(this.elements[l_cnt],p_event,p_js);
		}
	}
}

//------------( TElement )-------------------------------

function TElement(p_parent,p_jsName,p_name,p_id)
{
	TAbstractElement.call(this,p_parent,p_jsName,p_name);
	this.id=p_id;
	this.element=$$(p_id);
	if(this.element != null){
		this.element._control=this;
	}
}

TElement.prototype=Object.create(TAbstractElement.prototype);


TElement.prototype.getElementParent=function()
{
	return this.element.parentNode;
}


TElement.prototype.display=function(p_flag)
{
	core.display(this.element,p_flag);
}

TElement.prototype.isCustomEvent=function(p_event)
{
	return false;
}

TElement.prototype.on=function(p_event,p_js)
{
	if(this.isCustomEvent(p_event)){
		TAbstractElement.prototype.on.call(this,p_event,p_js);
	} else {
		core.ev(this.element,p_event,p_js);	
	}
}

/*----( TMenuBar) ------------------------------------------*/

function TMenuBar(p_parent,p_jsName,p_name,p_id)
{
	TElement.call(this,p_parent,p_jsName,p_name,p_id);
}

TMenuBar.prototype=Object.create(TElement.prototype);


TMenuBar.prototype.setup=function()
{
	TElement.prototype.setup.call(this);
	this.element.puimenubar();
}

/*---( TListMenu )--------------------------------------------------*/

function TListMenu(p_parent,p_jsName,p_name,p_id)
{
	TElement.call(this,p_parent,p_jsName,p_name,p_id);
}

TListMenu.prototype=Object.create(TElement.prototype);
/*---(TDynamicLinkMenuItem )---------------------------------------*/

function TDynamicLinkListMenuItem(p_parent,p_jsName,p_name,p_idBase)
{
	TCollectionElement.call(this,p_parent,p_jsName,p_name,p_idBase);
}

TDynamicLinkListMenuItem.prototype=Object.create(TCollectionElement.prototype);

TDynamicLinkListMenuItem.prototype.isCustomEvent=function(p_event)
{
	if(p_event=="DelButtonPressed"){
		return true;
	}
	return TElement.prototype.isCustomEvent.call(this,p_event);
}

TDynamicLinkListMenuItem.prototype.onDelButtonPressed=function(p_element)
{
	var l_data=null;
	var l_attr=p_element.attributes.getNamedItem("_data");
	if(l_attr != null){
		l_data=l_attr.nodeValue;
	}
	this.customEvent("DelButtonPressed",l_data);
}


/*---(TLinkMenuItem)------------------------------------------------*/

function TLinkListMenuItem(p_parent,p_jsName,p_name,p_id)
{
	TElement.call(this,p_parent,p_jsName,p_name,p_id);
}

TLinkListMenuItem.prototype=Object.create(TElement.prototype);

TLinkListMenuItem.prototype.isCustomEvent=function(p_event)
{
	if(p_event=="DelButtonPressed"){
		return true;
	}
	return TElement.prototype.isCustomEvent.call(this,p_event);
}

TLinkListMenuItem.prototype.onDelButtonPressed=function(p_element)
{
	var l_data=null;
	if("_data" in p_element){
		l_data=p_element._data;
	}
	this.customEvents("DelButtonPressed",l_data);
}

/*---( TMenu )------------------------------------------------------*/

function TMenu(p_parent,p_jsName,p_name,p_id)
{
	TElement.call(this,p_parent,p_jsName,p_name,p_id);
	this.menuTimeout=false;
	this.state="normal";
}

TMenu.prototype=Object.create(TElement.prototype); 

TMenu.prototype.setState=function(p_state)
{
	this.state=p_state;
	if(p_state=="normal"){
		this.element.className="menu";		
	} else if(p_state=="disabled"){
		this.element.className="menu_disabled";
	} else if(p_state=="hidden"){
		this.element.className="menu_hidden";
	}
}

TMenu.prototype.hideMenu=function()
{
	var l_this=this;
	this.menuTimeout=setTimeout(function(){
		core.display(l_this.menu,false);	
	},1000);
	
}

TMenu.prototype.clearTimeout=function()
{
	if(this.menuTimeout){
		clearTimeout(this.menuTimeout);
	}
}

TMenu.prototype.openMenu=function()
{
	if(this.state=="normal"){
		if(this.menu.style.display != "none"){
			core.display(this.menu,false);
		} else {
			core.display(this.menu,true);
			var l_position=core.getPosition(this.element);
			l_position.y += this.element.offsetHeight;
			core.setPosition(this.menu,l_position);
		}
	}
}


TMenu.prototype.setup=function()
{
	TElement.prototype.setup.call(this);
	this.menu=core.create("div",{"className":"menu_popup","style":{"display":"none"}},document.body);
	core.ev(this.element,"click",function(){this.openMenu();},this);
	core.ev(this.element,"mouseout",function(){ this.hideMenu();},this);
	core.ev(this.element,"mousein",function(){ this.clearTimeout();},this);
	core.ev(this.menu,"mouseout",function(){ this.hideMenu();},this);
	core.ev(this.menu,"mouseover",function(){ this.clearTimeout();},this);
	
}

function TLinkMenuItem(p_parent,p_jsName,p_name,p_id)
{
	TElement.call(this,p_parent,p_jsName,p_name,p_id);
	this.url="";
	this.text="";
	this.iconUrl="";
}
TLinkMenuItem.prototype=Object.create(TElement.prototype);

TLinkMenuItem.prototype.setup=function()
{
	TElement.prototype.setup.call(this);
	this.element=core.create("div",{"className":"menu_linkmenuitem"},this.parent.menu);
	if(this.iconUrl != ""){
		core.create("img",{"src":this.iconUrl},this.element);
	}
	core.text(this.text,this.element);
	core.ev(this.element,"click",function(){window.location=this.url;},this)
}

function TSeperatorMenuItem(p_parent,p_jsName,p_name,p_id)
{
	TElement.call(this,p_parent,p_jsName,p_name,p_id);	
}

TSeperatorMenuItem.prototype=Object.create(TElement.prototype);

TSeperatorMenuItem.prototype.setup=function()
{
	TElement.prototype.setup.call(this);
	this.element=core.create("div",{"className":"menu_seperator"},this.parent.menu);
}

function TRepeatElement(p_parent,p_jsName,p_name)
{
	TAbstractElement.call(this,p_parent,p_jsName,p_name);
	this.elementCnt=0;
}

TRepeatElement.prototype=Object.create(TAbstractElement.prototype);

TRepeatElement.prototype.newItem=function()
{
	var l_jsName=this.jsName+"$"+this.elementCnt;
	var l_name=this.elementCnt;
	var l_item=new TRepeatElementItem(this,l_jsName,l_name);
	this.addElement(l_item.jsName,l_item);
	this.addByName(l_item);
	this.elementCnt++;
	return l_item;
}

TRepeatElement.prototype.fillData=function(p_data)
{
	var l_result=[];
	for(var l_name in this.names){
		var l_data={};
		this.names[l_name].fillData(l_data);
		l_result.push(l_data);
	}
	p_data[this.name]=l_result;
}

function TRepeatElementItem(p_parent,p_jsName,p_name)
{
	TAbstractElement.call(this,p_parent,p_jsName,p_name);
	this.isNamespace=true;
}

TRepeatElementItem.prototype=Object.create(TAbstractElement.prototype);

var widgetParent=null;
