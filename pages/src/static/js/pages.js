var pages={		
		page:{
			elements:{},
			names:{},
			parent:null,
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
			initToWindowSize:function(){
				$(window).resize(function(){
						var l_container=$("pageContainer");
						l_container.height($(window).height());
						l_container.width($(window).width());
				});
			},

			lock:function()
			{
				$("<div>&#160;</div>").attr("id","locker").css({
					"position":"absolute"
				,	"top":0
				,   "left":0
				,   "opacity":0.5
				,	"z-index":99999
				,   "width":"100%"
				,	"height":"100%"
				,	"background-color":"#303030"
				}).appendTo('body');
			},
			unlock:function(){
				var l_element=$("#locker");
				if(l_element)l_element.remove();				
			}
		}
};

function TElement(p_parent,p_jsName,p_name,p_id)
{
	this.parent=p_parent;
	this.elements={};
	this.names={};
	this.id=p_id;
	this.name=p_name;
	this.jsName=p_jsName;
	this.element=$("#"+p_id);
	this.checkCondition=false;
	this.namespaceParent=false;
}


TElement.prototype.getElementParent=function()
{
	return this.element.parent();
}
TElement.prototype.isInputElement=function()
{
	return false;
}

TElement.prototype.display=function(p_flag)
{
	if(p_flag){
		this.element.show();
	} else {
		this.element.hide();
	}
}

TElement.prototype.handleCheckCondition=function()
{
	if(this.checkCondition){
		this.checkCondition();
	}
	for(var l_name in this.elements){
		this.elements[l_name].handleCheckCondition();		
	}
}

TElement.prototype.on=function(p_event,p_js)
{
	this.element.on(p_event,p_js);
}
TElement.prototype.fillThisData=function(p_data)
{
	if("getValue" in this){
		p_data[this.name]=this.getValue();
	}
}

TElement.prototype.fillData=function(p_data)
{
	this.fillThisData(p_data);
	for(var l_name in this.elements){
		this.elements[l_name].fillData(p_data);
	}
}

TElement.prototype.addByName=function(p_element)
{
	this.names[p_element.jsName]=p_element;
}

TElement.prototype.getByName=function(p_element)
{
	if(p_element in this.names){
		return this.names[p_element];
	}
	return null;
}

TElement.prototype.addElement=function(p_jsName,p_element)
{
	this.elements[p_jsName]=p_element;
}

TElement.prototype.setup=function()
{
	if("config" in this){
		this.config();
	}
	this.parent.addElement(this.jsName,this);
	if(this.namespaceParent){
		this.namespaceParent.addByName(this);
	}	
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
