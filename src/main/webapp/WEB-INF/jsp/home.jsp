<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.yogesh.lucene.SearchResult"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result</title>
</head>
<body>
<%
List test = new ArrayList();
String val="";
String totalcount="";
if(session.getAttribute("output")!=null)
{
	test.clear();
	test=(List)session.getAttribute("output");
	if((Integer)session.getAttribute("totalhits")>2){
		totalcount="TotalCount : "+session.getAttribute("totalhits");
		 val="<a href=SearchServlet"+"?field="+session.getAttribute("message")+">Next</a>";
		
	}
	session.setAttribute("EmpList",session.getAttribute("output"));
	
%>

<%
} 
else
{
	test.clear();
	SearchResult s=new SearchResult();
	s.setFilename("");
	s.setSetpath("");
	test.add(s);
	session.setAttribute("EmpList",test);

	 
}%>
<b><%=totalcount%></b>
<center><td colspan="2"><p align="center"><B>Successfully Uploaded</B>    <a href="index.html">HOME</a><center>
<FORM  ACTION="SearchServlet" METHOD=GET>
		<br><br><br>
	  <center><table border="2" >
  <tr><center><td colspan="2"><p align="center"><center></td></tr>
                    <tr><td>Search a field
</td>
                    <td><INPUT NAME="field" TYPE="text"></td></tr>
					<tr><td colspan="2">
<p align="right"><INPUT TYPE="submit" VALUE="submit" ></p></td></tr>
             <table>
     </center>   
     <INPUT NAME="pagenumber" TYPE="hidden" value="1">   
     </FORM>
     
<%=val%>

<jsp:useBean id="EmpList" scope="session" type="java.util.List"></jsp:useBean>
 <table>
   <tr>
     <th>Docuement</th>
     <th>Path</th>
        </tr>
   <c:forEach items="${EmpList}" var="emp" begin="0" end="10">
     <tr>
       <td><c:out value="${emp.filename}"></c:out></td>
       <td><c:out value="${emp.setpath}"></c:out></td>
     </tr>
   </c:forEach>
</table>


</body>
</html>