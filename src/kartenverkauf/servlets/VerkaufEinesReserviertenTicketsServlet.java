package kartenverkauf.servlets;

import kartenverkauf.Kartenverkauf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VerkaufEinesReserviertenTicketsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Kartenverkauf kartenverkauf = (Kartenverkauf) req.getServletContext().getAttribute(Kartenverkauf.attributeName);
        int parameterIntFromJSP = Integer.parseInt(req.getParameter("nummer"));
        //int parameterIntFromJSP = 100;
        String parameterStringFromJSP = req.getParameter("name");
        parameterIntFromJSP --; //indexTable != indexArray

        if (kartenverkauf.verkaufReserviertesTicket(parameterIntFromJSP,parameterStringFromJSP)) {
            try {
                updateDatabase(req,parameterIntFromJSP,parameterStringFromJSP);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.sendRedirect("/");
        }

    }

    private void updateDatabase(HttpServletRequest req, int sitznummer, String reservierungsname) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE sitzplatz SET zustand=? WHERE sitznummer=? AND reservierungsname=?");

            preparedStatement.setString(1,"VERKAUFT");
            preparedStatement.setInt(2, sitznummer);
            preparedStatement.setString(3, reservierungsname);
            preparedStatement.execute();


            connection.commit();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
