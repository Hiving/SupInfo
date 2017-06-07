<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
</head>
<body>
<div class="row">
	<div class="col-md-4"> </div>
	<div class="col-md-4">
		<h1 class="text-center">Create Account</h1>
	
		<form action="register" method="post"  >
			<div class="row text-center">
				<label for="username" class="lead" >Username </label><br>
				<input class="form-control" type="text" value="${username}" name="username" id="username" required>	<span	class="lead"  id="corUsername"></span> <span class="lead" id="white1"  style="color : red;">  <c:out value="${UsernameExiste }"/></span>
			</div>
		
			<div class="row text-center">
				<label for="password" class="lead" >Password </label> <br>
				<input class="form-control" type="password" name="password" id="password" required> <span	class="lead"  id="corPassword"></span>
			</div>
			
			<div class="row text-center" id="maill">
				<label for="email" class="lead" >Email </label> <br> 
				<input class="form-control" type="email" name="email" value="${email}" id="email" required>      <span	class="lead"  id="corEmail"></span>  <span class="lead" id="white"  style="color : red;">  <c:out value="${emailExiste }"/></span>   
			</div>
			
			<div class="row text-center">
				<label for="firstname" class="lead"  >Firstname </label> <br>
				<input class="form-control" value="${firstname}" type="text" name="firstname" id="firstname" required ><span	class="lead"  id="corFirstname"></span>
			</div>
			
			<div class="row text-center">
				<label for="lastname"  class="lead">Lastname </label> <br>
				<input class="form-control" value="${lastname}" type="text" name="lastname" id="lastname" required><span	class="lead"  id="corLastname"></span>
			</div>
			
			<div class="row text-center">
				<label for="telephone"  class="lead">Phone Number </label> <br>
				<input class="form-control" value="${telephone}" type="number" name="telephone" id="telephone" required><span	class="lead"  id="corTelephone"></span>
			</div>
			<br><br>
			<div class="row text-center">
				<input type="submit" value="Register" class="btn btn-primary btn-lg">
			</div>
		</form>
		<div class="row text-center">
			<a href="login">Already a member ?</a>
		</div>
	</div>
	
  <div class="col-md-4"> </div>
</div>
	<script src="/Supinfo/JavaScript/Register.js"></script>
</body>
</html>