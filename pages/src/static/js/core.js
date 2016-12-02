"user strict";
function $$(p_id)
{
	return document.getElementById(p_id);
}

var core={
		set:function(p_element,p_data){
				var l_value;
				for(var l_key in p_data){
					l_value=p_data[l_key];
					if(l_value.constructor==Object){
						this.set(p_element[l_key],l_value);
					} else {
						p_element[l_key]=l_value;
					}
				}
		},

		create:function(p_name,p_data,p_parent){
			var l_element=document.createElement(p_name);
			if(p_data){
				this.set(l_element,p_data);
			}
			if(p_parent) p_parent.appendChild(l_element);
			return l_element
		},
		
		text:function(p_text,p_parent){
			var l_element=document.createTextNode(p_text);
			if(p_parent) p_parent.appendChild(l_element);
			return l_element;
		},
		
		display:function(p_element,p_flag){
			p_element.style.display=p_flag?"":"none";
		},
		
		getPosition:function(p_element){
			var l_x=0;
			var l_y=0;
			var l_element=p_element;
			while(l_element && l_element.offsetParent){
				l_x += l_element.offsetLeft;
				l_y += l_element.offsetTop;
				l_element=l_element.offsetParent;
			}
			return {"x":l_x,"y":l_y};
		},
		setPosition:function(p_element,p_position){
			p_element.style.left=p_position.x+"px";
			p_element.style.top=p_position.y+"px";
		},
		ev:function(p_element,p_event,p_function,p_object){
			if(p_object){
				p_element.addEventListener(p_event,function(){p_function.apply(p_object);});
			} else {
				p_element.addEventListener(p_event,p_function);
			}
		},
		remove:function(p_element){
			p_element.parentNode.removeChild(p_element);
		},
		toWindowSize:function(p_element){
			p_element.style.width=window.innerWidth+'px';
			p_element.style.height=window.innerheight+'px';			
		},
		ajax:function(p_method,p_url,p_data,p_config){
			var l_req;
			if (window.ActiveXObject) {
				l_req = new ActiveXObject("Microsoft.XMLHTTP");
				try{
					l_req = new ActiveXObject("Msxml2.XMLHTTP.6.0");					
				} catch(e1){
					try{
						l_req = new ActiveXObject("Msxml2.XMLHTTP.4.0");						
					} catch(e2){
						try{
							l_req = new ActiveXObject("Msxml2.XMLHTTP.3.0");
							
						} catch(e3){
			
							l_req= new ActiveXObject("Microsoft.XMLHTTP");							
						}
					}
				}
			} else if (window.XMLHttpRequest) {
				l_req = new XMLHttpRequest();
		    }
			
			var l_async=("async" in p_config)?p_config.async:false;			
			if("username" in p_config){
				l_req.open(p_method,p_url,l_async,p_data.username,p_data.password);
			}  else {
				l_req.open(p_method,p_url,l_async);
			}
			if(l_async){
				l_req.onreadystatechange=function(){
					if(readState=="DONE"){
						if(l_req.status==200){
							if("success" in p_config){
								p_config.success(l_req);
							}
						} else {
							if("failed" in p_config){
								p_config.failed(l_req);
							}					
						}		
						if("compleet" in p_config) p_config.compleet(l_req);
					}
				}
			}
			if("contentType" in p_config){
				l_req.setRequestHeader("Content-Type",p_config.contentType);
			}
			l_req.send(p_data);
			if(!l_async){
				if(l_req.status==200){
					if("success" in p_config){
						p_config.success(l_req);					
					}
				} else {
					if("failed" in p_config){
						p_config.failed(l_req);
					}					
				}
				if("compleet" in p_config) p_config.compleet(l_req);
				return l_req;
			} else {
				return false;
			}
		}
}