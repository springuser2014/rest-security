<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title}</title>
	<link rel="stylesheet" href='<spring:theme code='jquery-ui'/>' type="text/css"/>
</head>
<body>
	<%-- <spring:message code="message.header"/> --%>
	<p>theme: ${theme}</p>
	<p>Login: ${msg}</p>
</body>
</html>