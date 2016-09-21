var pages={		
		page:{
			elements:{},
			byName:{},
			parent:null,
			addElement:function(p_name,p_object){
				this.elements[p_name]=p_object;
			},
			getByName:function(p_name){
				if(p_name in this.byName){
					return this.byName[p_name];
				}
				return null;
			},
			addByName:function(p_name,p_object){
				this.byName[p_name]=p_object;
			},
			initToWindowSize:function(){
				$(window).resize(function(){
						var l_container=$("pageContainer");
						l_container.height($(window).height());
						l_container.width($(window).width());
				});
			},
			removeErrors()
			{
					$(".page_error").remove();
			},
			addErrors:function(p_data){
				this.removeErrors();
				for(var l_cnt=0;l_cnt<p_data.length;l_cnt++){
					var l_element=this.getByName(p_data[l_cnt].field);
					if(l_element){
						var l_node=$('<div>').attr('class','page_error').appendTo(l_element.element.parent());
						l_node.append(document.createTextNode(p_data[l_cnt].msg));
					}
				}
			}
			,
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
	this.id=p_id;
	this.name=p_name;
	this.parent.addElement(p_jsName,this);
	this.element=$("#"+p_id);
	if(this.name!= ""){
		pages.page.addByName(this.name,this);
	}
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

TElement.prototype.addElement=function(p_jsName,p_element)
{
	this.elements[p_jsName]=p_element;
}

TElement.prototype.setup=function()
{
	this.config();
}