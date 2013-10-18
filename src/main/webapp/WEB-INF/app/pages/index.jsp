<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title}</title>
	<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
	<link rel="stylesheet" href='${path}<spring:theme code='jquery-ui'/>' type="text/css"/>
	<script type="text/javascript" src='<c:url value="/scripts/jquery.js" />'></script>
</head>
<body>
	<%-- <spring:message code="message.header"/> --%>
	<p>theme: ${theme}</p>
</body>
</html>