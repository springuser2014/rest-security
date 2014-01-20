<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="zh">
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>${title}</title>

	<!-- Bootstrap core CSS -->
	<link href="<spring:theme code='bootstrap-css'/>" rel="stylesheet">


	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="<spring:theme code='html5shiv-js'/>"></script>
	<script src="<spring:theme code='respond-js'/>"></script>
	<script src="<spring:theme code='es5-shim-js'/>"></script>
	<![endif]-->
	<style type="text/css">
		body {
		padding-top: 40px;
		padding-bottom: 40px;
		background-color: #eee;
		}

		.form-signin {
		max-width: 330px;
		padding: 15px;
		margin: 0 auto;
		}
		.form-signin .form-signin-heading,
		.form-signin .checkbox {
		margin-bottom: 10px;
		}
		.form-signin .checkbox {
		font-weight: normal;
		}
		.form-signin .form-control {
		position: relative;
		font-size: 16px;
		height: auto;
		padding: 10px;
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		}
		.form-signin .form-control:focus {
		z-index: 2;
		}
		.form-signin input[type="text"] {
		margin-bottom: -1px;
		border-bottom-left-radius: 0;
		border-bottom-right-radius: 0;
		}
		.form-signin input[type="password"] {
		margin-bottom: 10px;
		border-top-left-radius: 0;
		border-top-right-radius: 0;
		}
	</style>
	</head>

	<body>

	<div class="container">
<!-- 
		<form class="form-signin" action="signin" method="post" >
		<h2 class="form-signin-heading">Please sign in</h2>
		<input id="u" name="u" type="text" class="form-control" placeholder="User name/Email address: admin" autofocus>
		<input id="p" name="p" type="password" class="form-control" placeholder="Password: adminpass">
		<label class="checkbox">
			<input type="checkbox" value="remember-me"> Remember me
		</label>
		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		</form>
 -->
 <c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post">       
    <c:if test="${param.error != null}">        
        <p>
            Invalid username and password.
        </p>
    </c:if>
    <c:if test="${param.logout != null}">       
        <p>
            You have been logged out.
        </p>
    </c:if>
    <p>
        <label for="username">Username</label>
        <input type="text" id="username" name="username"/>	
    </p>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password"/>	
    </p>
    <input type="hidden"                        
        name="${_csrf.parameterName}"
        value="${_csrf.token}"/>
    <button type="submit" class="btn">Log in</button>
</form>
	</div> <!-- /container -->

	<script src="<spring:theme code='jquery-js'/>"></script>

	<!-- Bootstrap core JavaScript
	================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	
	<script type="text/javascript">
		$(function() {
			/* 
			$('button[type=submit]').click(function() {
				var passPhrase = +new Date();;
				$.ajax({
					dataType: "json",
					type: "POST",
					url: "signin",
					data: {u: $("#u").val(), p: $.trim($("#p").val()), t: $(".t").val(), k: passPhrase},
					username: u,
					password: p,
					timeout: 60000,     //ajax请求超时时间60秒
					success: function(data){
						if (data.access) {
							window.location.href = data.redirect;
						}
						else
						{
						}
					},
					error: function (XMLHttpRequest, textStatus, errorThrown) {
						if(textStatus == "timeout") {
						}
					}
				});
				return false;
			});
			 */
		});
	</script>
	</body>
</html>