<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.pega.samples.bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>


<nav><a href='LogoutServlet'>Logout</a>
<body bgcolor="pink">	
<h1 align="center">PPF Quiz</h1>
<h2>Case ID:<%=request.getSession().getAttribute("CaseID") %></h2>
<table>
<tr><tr><th align="left">Question No: <ins id="QNo"></ins></th></tr>
<tr><th align="left"><div id="Qu"></div></th></tr>
</table>
<table>
<tr><td  align="right"><input type="radio" name="Answer"  value="Option1"/></td><td><div id="Option1"></div></td></tr>
<tr><td  align="right"><input type="radio" name="Answer"  value="Option2"/></td><td><div id="Option2"></div></td></tr>		
<tr><td  align="right"><input type="radio" name="Answer"  value="Option3"/></td><td><div id="Option3"></div></td></tr>
</table>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
</script>
<jsp:useBean id="step" class="com.pega.samples.bean.Quizstep" scope="session"/>  
<script>
var option = $("input:radio[name='Answer']:checked").val();
	$(document).ready(function() {
		 <% int QNo=step.getQlist().size();
		  String quest=step.getQuestion().getQuestion();
		  String Option1=step.getQuestion().getOptions().get(0);
		  String Option2=step.getQuestion().getOptions().get(1);
		  String Option3=step.getQuestion().getOptions().get(2);
		  System.out.println(step.getQuestion().getQuestion());%>
		   var quest="<%=quest%>";
		   var Q="<%=QNo%>";
		   var Option1="<%=Option1%>";
		   var Option2="<%=Option2%>";
		   var Option3="<%=Option3%>";
		   document.getElementById("QNo").innerHTML = Q;
		  	document.getElementById("Qu").innerHTML = quest;
			document.getElementById("Option1").innerHTML = Option1;
			document.getElementById("Option2").innerHTML = Option2;
			document.getElementById("Option3").innerHTML = Option3;
			
		$("button").click(function() {
			$.post("QuizHandler", {
				option : $("input:radio[name='Answer']:checked").val()
			}, function(data, status) {
				if(data==null || data == "null") 
				{
					document.getElementById("QNo").innerHTML=0;
				window.location.replace("Essay.html");
				}
				else
				{
				document.getElementById("QNo").innerHTML= Number(document.getElementById("QNo").innerHTML) + 1;
				document.getElementById("Qu").innerHTML = data.Question;
				document.getElementById("Option1").innerHTML = data.Options[0];
				document.getElementById("Option2").innerHTML = data.Options[1];
				document.getElementById("Option3").innerHTML = data.Options[2];
				
				}
			}, "json");
		});
		$(this).attr('checked',false);
	});
</script>
<button>Next</button>
</body>

</html>