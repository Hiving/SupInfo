<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="row">
		<div class="col-md-4"> </div>
		<div class="col-md-4"> 
			<h1 class="text-center">Login</h1>
			<form action="login" method="post" >
				<div class="row text-center">
					<label for="email" class="lead" >Email </label> <br>
					<input class="form-control" type="email" name="email" id="email" value="${email }" required> <span	class="lead"  id="corEmail"></span>
				</div>
				<div class="row text-center">
					<label for="password" class="lead" >Password </label> <br>
					<input class="form-control" type="password" name="password" id="password"   required> <span	class="lead"  id="corPassword"></span>
				</div>
				<br><br>
				<div class="row text-center" >
					<span class="lead"  style="color: red;"  > <c:out value="${erreurs }"/>  </span>
				</div>
				<div class="row text-center">
					<input type="submit" value="Login" class="btn btn-primary btn-lg">
				</div>
			</form>
			<div class="row text-center">
				<a href="register">Register</a>
			</div>
		</div>
	</div>
	<script src="/Supinfo/JavaScript/Login.js"></script>
	
</body>
</html>