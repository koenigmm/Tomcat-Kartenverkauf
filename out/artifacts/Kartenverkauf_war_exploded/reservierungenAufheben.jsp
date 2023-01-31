<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservierungen aufheben</title>
</head>
<body>
<h2>Reservierungen aufheben</h2>

Mit dieser Operation werden
<ul>
    <li>
        alle bestehenden Reservierungen gelöscht
    </li>
    <li>
        ab sofort Reservierungen unterbunden
    </li>
</ul>
<form action="ReservierungenAufhebenServlet" method="get">
    <input type="submit" value="ausführen">
</form>
<a href="index.jsp">zurück</a>
</body>
</html>
