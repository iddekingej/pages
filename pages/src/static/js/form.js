function TForm(p_parent,p_jsName,p_name,p_id){
	TElement.call(this,p_parent,p_jsName,p_name,p_id);
	this.element[0]._control=this;
	this.cmd="";
	this.url="";	
	this.submitType="";
	this.nextUrl="";
	this.cancelUrl="";
}

TForm.prototype=Object.create(TElement.prototype);

TForm.prototype.cancel=function()
{
	window.location=this.cancelUrl;
}

TForm.prototype.save=function()
{
	if(this.submitType=="json"){
		this.sendData();
	} else {
		this.element.submit();
	}
}

TForm.prototype.removeErrors=function()
{
		$(".page_error").remove();
}


TForm.prototype.addErrors=function(p_data){
	var l_field;
	this.removeErrors();
	for(var l_cnt=0;l_cnt<p_data.length;l_cnt++){
		l_field=p_data[l_cnt].field;
		if(l_field.length>0){
			var l_element=this.getByName(l_field);
			if(l_element){
				var l_node=$('<div>').attr('class','page_error').appendTo(l_element.getElementParent());
				l_node.append(document.createTextNode(p_data[l_cnt].msg));
			}
		}
	}
}

TForm.prototype.success=function(p_data)
{
	pages.page.unlock();
	if("errors" in p_data){
		
		this.addErrors(p_data.errors);
		var l_errors="";
		for(var l_cnt in p_data.errors){
			if(p_data.errors[l_cnt].field=="") l_errors += p_data.errors[l_cnt].msg+"\n";
		}
		if(l_errors != "")alert(l_errors);
	} else {
		if(this.nextUrl) window.location=this.nextUrl;
	}
}

TForm.prototype.intChange=function()
{
	this.handleCheckCondition();
}


TForm.prototype.afterSetup=function()
{
	var l_this=this;
	for(var l_name in this.names){
		var l_element=this.names[l_name];
		if(l_element.isInputElement()){
			l_element.on("change",function(){l_this.intChange();});
		}
	}
	this.handleCheckCondition();
}


TForm.prototype.sendData=function()
{
	
	
		pages.page.lock();
		var l_element;
		var l_data={};
		for(var l_key in this.names){
			if("fillData" in this.names[l_key]){				
				this.elements[l_key].fillData(l_data);
			}
		}

		var l_message={
				    cmd:this.cmd
				,	form:this.name
				,	data:l_data
		};
		l_this=this;
	try{
		$.ajax({
			url:this.url
			,	type:'POST'
			,	contentType:'application/json'
			,	async:false
			,	data:JSON.stringify(l_message)			
			,   success:function(p_data){ l_this.success(p_data);}
			,	complete:function(p_xhr,p_status){ pages.page.unlock();}
		});
	} catch(e){
		pages.page.unlock();
		alert(e.message);
	}
}

function TFormElement(p_parent,p_jsName,p_name,p_id)
{
	TElement.call(this,p_parent,p_jsName,p_name,p_id);	
}

TFormElement.prototype=Object.create(TElement.prototype);

TFormElement.prototype.isInputElement=function()
{
	return true;
}

TFormElement.prototype.getValue=function()
{
	return this.element.val();
}

function TCheckboxElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
}

TCheckboxElement.prototype=Object.create(TFormElement.prototype);
TCheckboxElement.prototype.checked=function()
{
	return this.element[0].checked;
}

TCheckboxElement.prototype.getValue=function()
{
	return (this.checked())?1:0;
}

function TRadioElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
	this.elements=[];
	var l_cnt=0;
	var l_element;
	while(true){
		l_element=document.getElementById(p_id+"_"+l_cnt);
		if(l_element==null) break;
		this.elements.push(l_element);
		l_cnt++;
	}
	this.numElements=l_cnt;
}

TRadioElement.prototype=Object.create(TFormElement.prototype);


TRadioElement.prototype.getValue=function()
{
	for(var l_cnt=0;l_cnt<this.numElements;l_cnt++){
		if(this.elements[l_cnt].checked)return this.elements[l_cnt].value;
	}
	return null;
}


function TSelectListElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
}

TSelectListElement.prototype=Object.create(TFormElement.prototype);

function TStaticElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
}

TStaticElement.prototype=Object.create(TFormElement.prototype);

function TTextEditElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
}

TTextEditElement.prototype=Object.create(TFormElement.prototype);
TTextEditElement.prototype.getValue=function(){ return this.element.val();}

function TTextAreaElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
}

TTextAreaElement.prototype=Object.create(TFormElement.prototype);

function TDateElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
	this.showOn="";
	this.buttonText="";
}
 
TDateElement.prototype=Object.create(TFormElement.prototype);

TDateElement.prototype.setup=function()
{
	TFormElement.prototype.setup.call(this);
	var l_element=this.element.datepicker({
		showOn:this.showOn,
		buttonText:this.buttonText
	});
}

function THiddenElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);	
}

THiddenElement.prototype=Object.create(TFormElement.prototype);
