<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.pega.samples.bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav><a href='LogoutServlet'>Logout</a></nav>
<body bgcolor="aqua">
<table align="center">
<tr><th width="10%">User</th><th width="10%">CaseID</th><th width="10%">EssayScore</th><th width="10%">Score</th></tr>
<c:forEach items="${sessionScope.Results.getResults()}" var="R">
<tr><td align="center"><c:out value="${R.user}"></c:out></td><td align="center"><c:out value="${R.caseID}"></c:out></td><td align="center"><c:out value="${R.essayScore}"></c:out></td><td align="center"><c:out value="${R.score}"></c:out></td></tr>
</c:forEach>
</table>
<div align="center">
<form action="FirstQuestion"><input type="submit" value="Take new Quiz"></form>
</div>
</body>
</html>