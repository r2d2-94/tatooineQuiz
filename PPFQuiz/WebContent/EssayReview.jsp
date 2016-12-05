<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.pega.samples.bean.*"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<body bgcolor="pink">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
</script>
<nav><a href='LogoutServlet'>Logout</a></nav>
<p id="demo">Please enter value between 1 to 20 </p>
<table border="3">
<tr><th align="center">ID</th><th>Essay</tr>
<c:forEach items="${sessionScope.EssayMap}" var="E">
<tr><td align="center"><c:out value="${E.key}"></c:out></td>
<td><c:out value="${E.value.getEssays()}"></c:out></td></tr>
<tr><td colspan="2"><form id="${E.key}" action="EssayHandler" method="post" onsubmit=" return myFunction(&quot;${E.key}&quot;)"><input type="hidden" name="CaseID" value=<c:out value="${E.key}"></c:out>><input id="val" type="submit" value="Enter Score" ><input type="text" name="EssayScore"/></form></td></tr>
</c:forEach>
</table>
</body>
<script type="text/javascript">
function myFunction(name) {
    var x;
    var text;
	alert(name);
    x = document.getElementById("name").innerHTML;
    alert(x);
    // If x is Not a Number or less than one or greater than 10
    if (isNaN(x) || x < 1|| x > 20 || x== null || x=="") {
        text = "Score not valid, Please enter value between 1 to 20 ";
        document.getElementById("demo").innerHTML = text;
        return false;
    } 
    
}
</script>
</html>