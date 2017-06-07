<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">	
	<div class="row">
		<jsp:include page="NavBar.jsp" />
	</div>
	


	<div class="row">
	 



	<div class="col-md-3">
		<h2 class="text-center">Edit Profil</h2>
		<form action="" method="post"  >
			<div class="row text-center">
				<label for="username"  >Username </label><br>
				<input class="form-control" type="text" value="${sessionScope.user.username}" name="username" id="username" required>	<span	class="lead"  id="corUsername"></span> <span class="lead" id="white1"  style="color : red;">  <c:out value="${UsernameExiste }"/></span>
			</div>
		
			<div class="row text-center">
				<label for="password"  >Password </label> <br>
				<input class="form-control" type="password" value="${sessionScope.user.password} name="password" id="password" required> <span	class="lead"  id="corPassword"></span>
			</div>
			
			<div class="row text-center" id="maill">
				<label for="email"   >Email </label> <br> 
				<input class="form-control" type="email" name="email" value="${sessionScope.user.email}" id="email" required>      <span	class="lead"  id="corEmail"></span>  <span class="lead" id="white"  style="color : red;">  <c:out value="${emailExiste }"/></span>   
			</div>
			
			<div class="row text-center">
				<label for="firstname"    >Firstname </label> <br>
				<input class="form-control" value="${sessionScope.user.firstName}" type="text" name="firstname" id="firstname" required ><span	class="lead"  id="corFirstname"></span>
			</div>
			
			<div class="row text-center">
				<label for="lastname"  >Lastname </label> <br>
				<input class="form-control" value="${sessionScope.user.lastName}" type="text" name="lastname" id="lastname" required><span	class="lead"  id="corLastname"></span>
			</div>
			
			<div class="row text-center">
				<label for="telephone"  >Phone Number </label> <br>
				<input class="form-control" value="${sessionScope.user.phoneNumber}" type="number" name="telephone" id="telephone" required><span	class="lead"  id="corTelephone"></span>
			</div>
			<br><br>
			<div class="row text-center">
				<input type="submit" value="valid" class="btn btn-primary btn-lg">
			</div>
			
		</form>
	</div>




</div>
</div>

<script src="/Supinfo/JavaScript/Register.js"></script>

	
</body>
</html>