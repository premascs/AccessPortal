function forgotForm(){
	var form = document.getElementById('forgotForm');
	var email= document.getElementById('email');
	 var atpos = email.value.indexOf('@');
	 var dotpos = email.value.lastIndexOf('.');
	
	if(!email.value){
		alert("Please enter email");
	}else if(atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
		alert("Please enter valid email address");
	}else{
		form.method="POST";
		form.action="/forgot";
	}
}

function submitForm(){
	
	var type= document.getElementById('accessType');
	var id= document.getElementById('userId');
	var email= document.getElementById('userEmail');
	var orgApprover=document.getElementById('orgApprover');
	 var domain=document.getElementById('domain');
	var add = document.getElementById('add');
	var remove = document.getElementById('remove');
	var change = document.getElementById('change');
	
	var accessForm = document.getElementById('accessForm');
	 var atpos = email.value.indexOf('@');
	 var dotpos = email.value.lastIndexOf('.');
	
	
	if(type.value==="0"){
		alert("Please select Access Type");	
	}
	else if(!id.value){
		alert("Please enter user id");
	}else if(!email.value){
		alert("Please enter email");
	}else if(atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
		alert("Please enter valid email address");
	}else if(domain.value==="0"){
		alert("Please select domain");	
	}	
	else if(add.checked || remove.checked ||change.checked ){
	
	if(change.checked){
		if(!orgApprover.value){
			alert("Please enter original approver");
		}
	}
		accessForm.action="/register";
		alert("Request submitted successfully!!!");
	}else{
		alert("Action cannot be empty");
	}
	
	/*if(add.checked || remove.checked ||change.checked){
		accessForm.action="/register";
	}else{
		alert("Action cannot be empty");
	}
	alert("Request submitted successfully!!!");*/	
	
}

function defaultValues(){
	var change = document.getElementById('change');
	var hideChangeDiv = document.getElementById('hideChange');
	var x = document.getElementById('second');
	var temp = document.getElementById('temporary');
	hideChangeDiv.style.display='none';
	 x.style.display = 'none';
	 temp.checked=false;
	 
	 
}
function verify(access){
	var change = document.getElementById('change');
	var hideChangeDiv = document.getElementById('hideChange');
	var x = document.getElementById('second');
	x.style.display = 'none';
	
	if(access == "WO Approver"){
		hideChangeDiv.style.display='block';
	}
	else {
		
		hideChangeDiv.style.display='none';
	}
	if(access != "WO Approver"){
		hideChangeDiv.style.display='none';
	}
}


function enableChange(){
	var change = document.getElementById('change');
	var x = document.getElementById('second');
	var sDate = document.getElementById('sDate');
	var eDate = document.getElementById('eDate');

	
	if(change.checked){
		x.style.display='block';
		sDate.style.display='none';
		eDate.style.display='none';
		}
	else{
		x.style.display='none';
	}
	
	
}

function disableChange(){
	var add = document.getElementById('add');
	var remove = document.getElementById('remove');
	var x = document.getElementById('second')
	var hideChangeDiv = document.getElementById('hideChange');
	
	if(add.checked || remove.checked){
		x.style.display='none';
		}
	else{
		x.style.display='block';
	}
	
}


function enableDisable()
{
	var temp = document.getElementById('temporary');
	var sDate = document.getElementById('sDate');
	var eDate = document.getElementById('eDate');
	if(temp.checked){
	sDate.style.display='block';
	eDate.style.display='block';
	}
	else{
		sDate.style.display='none';
		eDate.style.display='none';
	}
}