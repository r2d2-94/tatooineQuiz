<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script>
function getParameterByName(name,url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

	
function validateForm() {
    var x = document.forms["form"]["name"].value;
    var y = document.forms["form"]["password"].value
    if (x == null || x == "") {
        alert("Name must be filled out");
        return false;
    }
    else if(y == null || y == "") {
        alert("Password must be filled out");
        return false;
    }
}
</script>
<nav><a href="index.html">Home</a></nav>
<body bgcolor="pink">
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<h1> Login as Student</h1><br/><p id="something" style="color:red"></p> 
<form action="LoginServlet" method="post" onsubmit="return validateForm()" name="form">
<%@ include file="LoginIndex.html" %>
<input type="hidden" name="role" value="user">
</form>
<script>
var error=getParameterByName('error');	
if(error==true || error == 'true')
{
var t="Wrong credentials";
document.getElementById("something").innerHTML = t;
}

</script>
</body>
</html>