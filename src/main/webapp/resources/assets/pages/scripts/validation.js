



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



