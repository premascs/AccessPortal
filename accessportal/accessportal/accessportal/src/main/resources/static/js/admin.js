$(document).ready(function() {
    $("div.tab-menu>div.list-group>a").click(function(e) {
        e.preventDefault();
        $(this).siblings('a.active').removeClass("active");
        $(this).addClass("active");
        var index = $(this).index();
        $("div.custom-right-tab>div.custom-tab-content").removeClass("active");
        $("div.custom-right-tab>div.custom-tab-content").eq(index).addClass("active");
    });
});

function showEdit(e){
	
	var aLink= document.getElementById('aLink').innerHTML;
	
	var setId= document.getElementById('updateappid').value;
	var searchid= document.getElementById('searchid');
	setId = aLink;
	
	searchid.click();
	
	
}

function login(){
	var email= document.getElementById('email').value;
	var password= document.getElementById('password').value;
	 var atpos = email.value.indexOf('@');
	 var dotpos = email.value.lastIndexOf('.');
	 var form = document.getElementById('login-nav');
	if(!email && !password){
		alert ("Enter email and password");	
	}else if(!email){
		alert("Please enter email");
	}else if(atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
		alert("Please enter valid email address");
	}else if(!password){
		alert("Password is blank");
	}
	else {
		form.action ="/login";
	}
	
}

function approve(reqId){
	var confirmation = confirm("Are you sure you want to approve request? ");
	var actionForm= document.getElementById('actionForm');
	if(confirmation==true){
	var actionSrc = "/approve/" + reqId;
		actionForm.action= actionSrc;
		actionForm.submit();
		}
	else{
		actionForm.action= "/home";
		actionForm.submit();
	}
	
	//alert(actionSrc);
}

function decline(reqId){
	var confirmation = confirm("Are you sure you want to decline request? ");
	var actionForm= document.getElementById('actionForm');
	if(confirmation==true){
	var actionSrc = "/decline/" + reqId;
		actionForm.action= actionSrc;
		actionForm.submit();
	}
	else{
		actionForm.action= "/home";
		actionForm.submit();
	}
	
}

function home(){
	//e.preventDefault();
	var type= document.getElementById('accessType').value;
	//alert(type);
	var actionForm= document.getElementById('homeForm');
	var addname= document.getElementById('addname').value;
	var addlname= document.getElementById('addlname').value;
	var addemail= document.getElementById('addemail').value;
	var actionSrc = "/home/?type=" + type;
	 var atpos = addemail.indexOf("@");
	 var dotpos = addemail.lastIndexOf(".");
	//alert(actionSrc);
	if(!addname){
		alert("Please enter first name");
	}else if(!addlname){
		alert("Please enter last name");
	}else if(!addemail){
		alert("Please enter email");
	}else if(atpos<1 || dotpos<atpos+2 || dotpos+2>=addemail.length){
		alert("Please enter valid email address");
	}else{
	actionForm.action= actionSrc;
	actionForm.submit();
	alert("Added Approver Successfully!!");
	}
	
}

