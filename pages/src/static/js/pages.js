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
}



TAbstractElement.prototype.isInputElement=function()
{
	return false;
}
//TElement.prototype.display=function(p_flag) :Abstract

TAbstractElement.prototype.getNamespaceParent=function()
{
	if(this.isNamespace) return this;
	if(this.parent!=null){
		return this.parent.getNamespaceParent();
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
	for(var l_name in this.elements){
		this.elements[l_name].fillData(p_data);
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
	if(this.checkCondition){
		this.checkCondition();
	}
	for(var l_name in this.elements){
		this.elements[l_name].handleCheckCondition();		
	}
}


//------------( TElement )-------------------------------

function TElement(p_parent,p_jsName,p_name,p_id)
{
	TAbstractElement.call(this,p_parent,p_jsName,p_name);
	this.id=p_id;
	this.element=$$(p_id);
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


TElement.prototype.on=function(p_event,p_js)
{
	core.ev(this.element,p_event,p_js);	
}


function TMenuBar(p_form,p_jsName,p_name,p_id)
{
	TElement.call(this,p_form,p_jsName,p_name,p_id);
}

TMenuBar.prototype=Object.create(TElement.prototype);


TMenuBar.prototype.setup=function()
{
	TElement.prototype.setup.call(this);
	this.element.puimenubar();
}


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
	this.element=core.create("div",{"className":"linkmenuitem"},this.parent.menu);
	if(this.iconUrl != ""){
		core.create("img",{"src":this.iconUrl},this.element);
	}
	core.text(this.text,this.element);
	core.ev(this.element,"click",function(){window.location=this.url;},this)
}

function TRepeatElement(p_parent,p_jsName,p_name)
{
	TAbstractElement(p_parent,p_jsName,p_name);
}

TRepeatElement.prototype=Object.create(TAbstractElement.prototype);

TRepeatElement.prototype.newItem=function()
{
	var l_jsName=this.jsName+"$"+this.elements.length;
	var l_name=this.name+"$"+this.elements.length;
	var l_item=new TRepeatElementItem(this,l_jsName,l_name);
	addElemen(l_item);
}

function TRepeatElementItem(p_parent,p_jsName,p_name)
{
	TAbstractElement(p_parent,p_jsName,p_name);
}

TRepeatElementItem.prototype=Object.create(TAbstractElement.prototype);
