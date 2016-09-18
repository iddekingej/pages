/**
 * 
 */

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

TForm.prototype.save=function()
{
	if(this.submitType=="json"){
		this.sendData();
	} else {
		this.element.submit();
	}
}

TForm.prototype.success=function(p_data)
{
	pages.page.unlock();
	if("errors" in p_data){
		var l_errors="";
		pages.page.addErrors(p_data.errors);
	} else {
		if(this.nextUrl) window.location=this.nextUrl;
	}
}
TForm.prototype.sendData=function()
{
	try{
	
		pages.page.lock();
		var l_element;
		var l_data={};
		for(var l_key in this.elements){
			l_data[l_key]=this.elements[l_key].getValue();
		}

		var l_message={
				cmd:this.cmd
				,	form:this.name
				,	data:l_data
		};
		l_this=this;
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

TFormElement.prototype.getValue=function()
{
	return this.element.value;
}

function TCheckboxElement(p_form,p_jsName,p_name,p_id)
{
	TFormElement.call(this,p_form,p_jsName,p_name,p_id);
}

TCheckboxElement.prototype=Object.create(TFormElement.prototype);

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

TTextEditElement.prototype=TFormElement.prototype;
TTextEditElement.prototype.getValue=function(){ return this.element.val();}
TTextEditElement.prototype.dummy=function(){};

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
	this.config();
	var l_element=this.element.datepicker({
		showOn:this.showOn,
		buttonText:this.buttonText
	});

}