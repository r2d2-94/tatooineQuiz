<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<nav><a href="LogoutServlet">Logout</a>
</nav>
<body bgcolor="pink">
<h1>CaseID: <%=request.getSession().getAttribute("CaseID") %></h1>
<h2> Essay Question </h2>
<p id="msg" style="color:red"></p>
<form action="EssayServlet" method="post" name="form" onsubmit="return validateForm()">
<input type="text" name="essay" size="150"><br/>
<input type="submit" value="Submit Exam"  >
</form>
</body>
<script>
function validateForm() {
    var x = document.forms["form"]["essay"].value;
    if (x == null || x == "") {
    	document.getElementById("msg").innerHTML="Essay cannot be left blank";
        return false;
    }
}
</script>
</html>