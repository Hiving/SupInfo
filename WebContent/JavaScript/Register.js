/**
 * 
 */
var erreurs = new Array();

document.getElementById('username').oninput = function(e){
	var username = (e.target.value);
	document.getElementById('white1').style.color = "white";
	validatorChain(username, "corUsername", "username");
}

document.getElementById('password').oninput = function(e){
	var lastname = (e.target.value);
	validatorChain(lastname, "corPassword", "password");
}

document.getElementById('firstname').oninput = function(e){
	var firstname = (e.target.value);
	validatorChain(firstname, "corFirstname", "firstname");
}

document.getElementById('lastname').oninput = function(e){
	var lastname = (e.target.value);
	validatorChain(lastname, "corLastname", "lastname");
}

document.getElementById('email').oninput = function(e){
	
	var email = e.target.value;
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	document.getElementById('white').style.color = "white";
	
	if(email === ""){
		document.getElementById('corEmail').textContent = "Empty fields";
		document.getElementById('corEmail').style.color = "red";
		document.getElementById('email').style.borderColor = "red";
		 
	}else if (email.match(re)){
		document.getElementById('corEmail').textContent = "";
		document.getElementById('email').style.borderColor = "green";
	}else {
		document.getElementById('corEmail').textContent = "Adress invalid";
		document.getElementById('corEmail').style.color = "red";
		document.getElementById('email').style.borderColor = "red";
		 
	}
}

document.getElementById('telephone').oninput = function (e){
	var telephone = e.target.value;
	var re = "^0+[1,6|7]+\\d{8}$";
	if (telephone === ""){
		document.getElementById('corTelephone').textContent = "Empty fields";
		document.getElementById('corTelephone').style.color = "red";
		document.getElementById('telephone').style.borderColor = "red";
		return "erreur"
	}else if(telephone.match(re)){
		document.getElementById('corTelephone').textContent = "";
		document.getElementById('telephone').style.borderColor = "green";
		return "erreur";
	}else {
		document.getElementById('corTelephone').textContent = "Phone number invalid";
		document.getElementById('corTelephone').style.color = "red";
		document.getElementById('telephone').style.borderColor = "red";
		return "erreur"; 
	}
}

 function validatorChain(chain, idSpan, idInput){
	  
	 if (chain === ""){
		  
			document.getElementById(idSpan).textContent = "Empty fields";
			document.getElementById(idSpan).style.color = "red";
			document.getElementById(idInput).style.borderColor = "red";
			 
			
		}else if(chain.length <= 3){
			document.getElementById(idSpan).textContent = "Too short";
			document.getElementById(idSpan).style.color = "red";
			document.getElementById(idInput).style.borderColor = "red";
			 
		}else if(chain.length >= 15) {
			document.getElementById(idSpan).textContent = "Too long";
			document.getElementById(idSpan).style.color = "red";
			document.getElementById(idInput).style.borderColor = "red";
			 
		}else {
			document.getElementById(idInput).style.borderColor = "green";
			document.getElementById(idSpan).textContent = "";
		}
 }
 
form = document.getElementsByTagName('form')[0];
form.onsubmit = function (e){
	if (document.getElementById('corUsername').textContent === "Empty fields" || document.getElementById('corUsername').textContent === "Too short" || document.getElementById('corUsername').textContent === "Too long" ){
		erreurs.push("erreur");
	}else if (document.getElementById('corPassword').textContent === "Empty fields" || document.getElementById('corPassword').textContent === "Too short" || document.getElementById('corPassword').textContent === "Too long" ){
		erreurs.push("erreur");
	}else if(document.getElementById('corFirstname').textContent === "Empty fields" || document.getElementById('corFirstname').textContent === "Too short" || document.getElementById('corFirstname').textContent === "Too long" ){	
		erreurs.push("erreur");
	}else if (document.getElementById('corLastname').textContent === "Empty fields" || document.getElementById('corLastname').textContent === "Too short" || document.getElementById('corLastname').textContent === "Too long" ) {
		erreurs.push("erreur");
	}else if (document.getElementById('corEmail').textContent === "Empty fields" || document.getElementById('corEmail').textContent === "Adress invalid") {
		erreurs.push("erreur");
	}else if (document.getElementById('corTelephone').textContent === "Empty fields" || document.getElementById('corTelephone').textContent === "Phone number invalid") {
		erreurs.push("erreur");
	}
	
	if (erreurs.length > 0){
		e.preventDefault();
		erreurs = [];
	}
}