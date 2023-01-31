<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Verkauf eines Tickets</title>
</head>
<body>
<h2>Verkauf eines freien Tickets</h2>

<form action="VerkaufServlet" method="get">
    Sitzplatznummer <input name="nummer"/>
    <p/>
    <input type="submit" value="ausführen">
</form>
<a href="index.jsp">zurück</a>
</body>
</html>
