<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reset Database</title>
</head>
<body>
<h2>Datenbank zurücksetzten</h2>

Mit dieser Operation wird
<ul>
    <li>
        die Datenbank gelöscht
    </li>
    <li>
        und die Tabelle Sitzplatz mit Standardwerten gefüllt
    </li>
</ul>
<form action="ResetDatabaseServlet" method="get">
    <input type="submit" value="ausführen">
</form>
<a href="index.jsp">zurück</a>
</body>
</html>
