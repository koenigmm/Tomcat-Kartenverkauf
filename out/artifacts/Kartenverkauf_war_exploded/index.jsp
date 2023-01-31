<%@ page import="kartenverkauf.Kartenverkauf" %>
<%@ page import="kartenverkauf.servlets.MysqlServlet" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kartenverkauf Startseite</title>
</head>
<p>
<h2>Kartenverkauf Startseite</h2>
<%
    Kartenverkauf kartenverkauf = (Kartenverkauf) request.getServletContext().getAttribute(Kartenverkauf.attributeName);
    DataSource dataSource = (DataSource) request.getServletContext().getAttribute("datasource");

    if (dataSource == null) {
        try {
            MysqlServlet.contextInitialized(response, request); //atributename = datasource
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    try {
        if (MysqlServlet.checkReset(request, response)) {
            MysqlServlet.processReset(request, response);
            kartenverkauf.updateSitzplatzArray(request);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }


    if (kartenverkauf == null) {
        try {
            kartenverkauf = new Kartenverkauf(request);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getServletContext().setAttribute(Kartenverkauf.attributeName, kartenverkauf);
    }

    try {
        if(MysqlServlet.checkGlobalReservierung(request,response)) {
            kartenverkauf.reservierbarGlobal = true;
        }
        else {
            kartenverkauf.reservierbarGlobal = false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }


%>

<div id="kartenverkaufTable"><%= kartenverkauf.getTableHTML()%>
</div>

<p id="ReservierungsStatuts"><%= kartenverkauf.getReservierbarMessage()%>
</p>

<p/>
<a href="./verkaufTicket.jsp">Verkauf eines freien Tickets</a>
<p/>
<a href="./reservierungEinesTickets.jsp">Reservierung eines Tickets</a>
<p/>
<a href="./verkaufReserviertesTicket.jsp">Verkauf eines reservierten Tickets</a>
<p/>
<a href="./stornierungEinesTickets.jsp">Stornierung Ticket</a>
<p/>
<a href="./reservierungenAufheben.jsp">Reservierungen aufheben</a>
</p>
<a href="resetDatabase.jsp">ResetDatabase und ermögliche Reservierungen</a>
</p>
</body>
</html>
