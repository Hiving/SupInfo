/**
 * 
 */
var erreurs = new Array();

document.getElementById('email').oninput = function(e){
	var email = e.target.value;
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
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

document.getElementById('password').oninput = function(e){
	var lastname = (e.target.value);
	
	if (lastname === ""){
			document.getElementById('corPassword').textContent = "Empty fields";
			document.getElementById('corPassword').style.color = "red";
			document.getElementById('password').style.borderColor = "red";
			 
			
		}else if(lastname.length <= 3){
			document.getElementById('corPassword').textContent = "Too short";
			document.getElementById('corPassword').style.color = "red";
			document.getElementById('password').style.borderColor = "red";
			 
		}else if(lastname.length >= 15) {
			document.getElementById('corPassword').textContent = "Too long";
			document.getElementById('corPassword').style.color = "red";
			document.getElementById('password').style.borderColor = "red";
			 
		}else {
			document.getElementById('password').style.borderColor = "green";
			document.getElementById('corPassword').textContent = "";
		}
}


var form = document.getElementsByTagName('form')[0];
form.onsubmit = function(e){
	var email = document.getElementById('corEmail');
	var password = document.getElementById('corPassword');
	if (password.textContent === 'Empty fields' || password.textContent === 'Too short' || password.textContent === 'Too long'){
		erreurs.push("erreur");
	}else if (email.textContent === 'Empty fields' || email.textContent ==='Adress invalid'){
		erreurs.push("erreur");
	}
	if(erreurs.length > 0){
		e.preventDefault();
		erreurs = [];
	}
}








