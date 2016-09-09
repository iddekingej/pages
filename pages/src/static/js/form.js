/**
 * 
 */

function TForm(p_parent,p_jsName,p_name,p_id){
	this.form=document.getElementById(p_id);
	this.form._control=this;
	this.parent=p_parent;
	this.name=p_name;
	this.cmd="";
	this.url="";
	this.parent.addElement(p_jsName,this);
	this.elements={};
}

TForm.prototype.submit=function()
{
	this.form.submit();
}

TForm.prototype.success=function(p_data)
{
	console.log(p_data);
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
			,	complete(p_xhr,p_status){ pages.page.unlock();}
		});
	} catch(e){
		pages.page.unlock();
		alert(e.message);
	}
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

TTextAreaElement.prototype=Object.create(TFormElement.prototype);

function TTextAreaElement(p_form,p_name,p_id)
{
	TFormElement.call(this,p_form,p_name,p_id);
}