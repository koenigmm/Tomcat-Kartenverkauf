<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stornierung eines Tickets</title>
</head>
<body>
<h2>Stornierung eines Tickets</h2>

<form action="StornierungServlet" method="get">
    Sitzplatznummer <input name="nummer"/>
    <p/>
    <input type="submit" value="ausführen">
</form>
<a href="index.jsp">zurück</a>
</body>
</html>
