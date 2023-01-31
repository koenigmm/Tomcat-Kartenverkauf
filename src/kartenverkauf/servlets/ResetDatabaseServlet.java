package kartenverkauf.servlets;

import kartenverkauf.Kartenverkauf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ResetDatabaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Kartenverkauf kartenverkauf = (Kartenverkauf)req.getServletContext().getAttribute(Kartenverkauf.attributeName);
        try {
            MysqlServlet.setResetVariableToTrue(req);
            MysqlServlet.setGlobalReservierungToTrue(req);
            kartenverkauf.updateSitzplatzArray(req);

            resp.sendRedirect("/");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
