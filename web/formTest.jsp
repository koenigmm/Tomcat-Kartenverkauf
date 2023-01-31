<%@ page import="kartenverkauf.CreateKartenverkaufHTML" %>
<%@ page import="kartenverkauf.Kartenverkauf" %>
<%--
  Created by IntelliJ IDEA.
  User: marius
  Date: 30.11.19
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Kartenverkauf attributeFromApplicationScope = (Kartenverkauf) request.getServletContext().getAttribute(Kartenverkauf.attributeName);
%>
<html>
<head>
    <title>Form Test</title>
</head>
<body>
<form action="sitzplatzerzeugen.jsp" method="get">
    First Name: <input type="text" name="first_name">
    <br/>
    Last Name: <input type="text" name="last_name">
    <input type="submit" value="Submit">
</form>

<p>
    <%=attributeFromApplicationScope.getTableHTML()%>
</p>

<p>
    <a href="index.jsp">zurück</a>
</p>

</body>
</html>
