/**
 * 
 */

function TForm(p_parent,p_jsName,p_name,p_id){
	this.form=document.getElementById(p_id);
	this.parent=p_parent;
	this.name=p_name;
	this.cmd=p_cmd;
	this.parent.addElement(p_jsName,this);
	this.elements={};
	this.submitButton=document.getElementById(p_id+"_submit");
	if(this.submitButton){
		var l_this=this;
		this.submitButton.onclick=function(){
				l_this.sendData();
		}
	}
}

TForm.prototype.sendData=function()
{
	var l_element;
	var l_data={};
	for(var l_key in this.elements){
		l_data[l_key]=this.elements[l_key].getValue();
	}
	var l_int=new XMLHttpRequest();
	l_int.open("POST","",false);
	var l_message={
		cmd:""
	,	form:name
	,	data:l_data
	};
	l_int.send(JSON.stringify(l_message));
	console.log(JSON.parse(l_int.responseText));
	return false;
}

TForm.prototype.addElement=function(p_jsName,p_element)
{
	this.elements[p_jsName]=p_element;
}

function TFormElement(p_form,p_name,p_id)
{
	this.form=p_form;
	this.name=p_name;
	this.element=document.getElementById(p_id);
}

TFormElement.prototype.getValue=function()
{
	return this.element.value;
}

function TCheckboxElement(p_form,p_name,p_id)
{
	TFormElement.call(this,p_form,p_name,p_id);
}

TCheckboxElement.prototype=Object.create(TFormElement.prototype);

function TRadioElement(p_form,p_name,p_id)
{
	TFormElement.call(this,p_form,p_name,p_id);
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


function TSelectListElement(p_form,p_name,p_id)
{
	TFormElement.call(this,p_form,p_name,p_id);
}

TSelectListElement.prototype=Object.create(TFormElement.prototype);

function TStaticElement(p_form,p_name,p_id)
{
	TFormElement.call(this,p_form,p_name,p_id);
}

TStaticElement.prototype=Object.create(TFormElement.prototype);

function TTextEditElement(p_form,p_name,p_id)
{
	TFormElement.call(this,p_form,p_name,p_id);
}

TTextEditElement.prototype=Object.create(TFormElement.prototype);