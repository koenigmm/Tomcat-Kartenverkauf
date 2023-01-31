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

public class VerkaufServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        Kartenverkauf kartenverkauf = (Kartenverkauf) req.getServletContext().getAttribute(Kartenverkauf.attributeName);
        int parameterFromJSP = (int)Integer.parseInt(req.getParameter("nummer"));
        parameterFromJSP --; //Die Tabelle beginnt bei 1 das Array bei 0
        if(kartenverkauf.verkaufen(parameterFromJSP)) {
            try {
                updateDatabase(req, parameterFromJSP, "Verkauft");
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
            preparedStatement = connection.prepareStatement("UPDATE sitzplatz SET zustand=? WHERE sitznummer=?");

            preparedStatement.setString(1,"VERKAUFT");
            preparedStatement.setInt(2, sitznummer);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE sitzplatz SET reservierungsname=? WHERE sitznummer=?");
            preparedStatement.setString(1, reservierungsname);
            preparedStatement.setInt(2, sitznummer);
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
