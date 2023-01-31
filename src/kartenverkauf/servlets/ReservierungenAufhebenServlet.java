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

public class ReservierungenAufhebenServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Kartenverkauf kartenverkauf = (Kartenverkauf) req.getServletContext().getAttribute(Kartenverkauf.attributeName);
        kartenverkauf.alleReservierungenStornieren();
        try {
            updateDatabase(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/");
    }

    private void updateDatabase(HttpServletRequest req) throws SQLException{
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement("UPDATE sitzplatz SET verkaufbar=? WHERE zustand=?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, "RESERVIERT");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE sitzplatz SET zustand=? WHERE zustand=?");
            preparedStatement.setString(1,"FREI");
            preparedStatement.setString(2, "RESERVIERT");
            preparedStatement.execute();

            //Eigentlich nicht mehr nötig, wegen resetDatabase
            preparedStatement = connection.prepareStatement("UPDATE reservierbarglobal SET reservierbar=? WHERE reservierbar=?");
            preparedStatement.setBoolean(1,false);
            preparedStatement.setBoolean(2, true);
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
