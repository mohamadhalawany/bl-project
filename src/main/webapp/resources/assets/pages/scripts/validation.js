
function validateEmail(input) {
   var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
   var x = document.getElementById("emailDiv");	
   if (!input.value.match(validRegex)) { 	
		x.style.display = "block";
		document.getElementById("mail").focus ;
	}else{
		x.style.display = "none";	
		document.getElementById("save").disabled = false ;
	}
}



function validateRequired(){
	var fullNameDiv = document.getElementById("fullNameDiv");	
	var fullName = document.getElementById("fullName").value ;	
	
	var emailDiv = document.getElementById("emailDiv");	
	var email = document.getElementById("email").value ;
	
	var passwordDiv = document.getElementById("passwordDiv");	
	var password = document.getElementById("password").value ;	
	
	if(fullName == ""){
		fullNameDiv.style.display = "block";
		return false ;
	}else if(email == ""){
		emailDiv.style.display = "block";
		return false ;
	}else if(password == ""){
		passwordDiv.style.display = "block";
		return false ;
	}else{
		fullNameDiv.style.display = "none";
		emailDiv.style.display = "none";
		passwordDiv.style.display = "none";
		return true ;
	}
}



function hideValidationMSG(){
	var fullNameDiv = document.getElementById("fullNameDiv");
	fullNameDiv.style.display = "none";
	
	var emailDiv = document.getElementById("emailDiv");
	emailDiv.style.display = "none";
	
	var passwordDiv = document.getElementById("passwordDiv");
	passwordDiv.style.display = "none";
}