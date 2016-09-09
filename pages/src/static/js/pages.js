var pages={		
		page:{
			elements:{},
			parent:null,
			addElement:function(p_name,p_object){
				this.elements[p_name]=p_object;
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
				var l_element=$("locker");
				if(l_element)l_element.remove();				
			}
		}
};

function TDummy(p_parent,p_jsName,p_name,p_id)
{
	this.parent=p_parent;
	this.elements={};
	this.parent.addElement(p_jsName,this);
}

TDummy.prototype.addElement=function(p_jsName,p_element)
{
	this.elements[p_jsName]=p_element;
}