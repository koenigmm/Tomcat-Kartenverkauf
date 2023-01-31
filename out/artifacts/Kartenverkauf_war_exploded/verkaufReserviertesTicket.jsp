<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Verkauf eines Tickets</title>
</head>
<body>
<h2>Verkauf eines reservierten Tickets</h2>

<form action="/VerkaufEinesReserviertenTicketsServlet" method="get">
    <table style="text-align:left">
        <tr><td style="text-align:left">Sitzplatznummer</td><td> <input name="nummer"/> </td></tr>
        <tr><td style="text-align:left">Reservierungsname</td><td> <input name="name"/> </td></tr>
    </table>
    <p/>
    <input type="submit" value="ausführen">
</form>
<a href="index.jsp">zurück</a>
</body>
</html>
