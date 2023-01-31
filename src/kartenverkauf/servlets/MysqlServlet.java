package kartenverkauf.servlets;
import kartenverkauf.Sitzplatz;
import kartenverkauf.Zustand;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.*;

public class MysqlServlet extends HttpServlet {

    public static void contextInitialized(HttpServletResponse resp, HttpServletRequest req) throws SQLException {

        try {
            Context initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            DataSource datasource = (DataSource) context.lookup("jdbc/kartenverkauf");
            req.getServletContext().setAttribute("datasource", datasource);

        } catch (Exception e) {
            System.out.println("Fehler: Web-App start gescheitert: \n" + e);
            throw new RuntimeException(e);
        }

    }

    public static boolean checkReset(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int resetvalue;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT resetdatabase FROM reset");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resetvalue = resultSet.getInt("resetdatabase");

                if (resetvalue == 1) {
                    System.out.println("Reset ist true");
                    return true;
                }
            }
            System.out.println("Reset ist false"); //TODO  Wird beim ersten Start mehrfach ausgeben connection pool? evtl auskomenntieren
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return false;

    }

    public static boolean checkGlobalReservierung(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int resetvalue;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT reservierbar FROM reservierbarglobal");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resetvalue = resultSet.getInt("reservierbar");

                if (resetvalue == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return false;

    }

    public static void processReset(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        clearDatabase(resp, req);
        createDataForDatabase(req, resp);
        setResetVariableToFalse(resp, req);
        System.out.println("Tabelle Sitzplatz zurückgesetzt");
    }

    public static void setResetVariableToFalse(HttpServletResponse resp, HttpServletRequest req) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE reset SET resetdatabase = ? WHERE resetdatabase = ?");
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 1);
            System.out.println("RESET auf false gesetzt");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void setResetVariableToTrue(HttpServletRequest req) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE reset SET resetdatabase = ? WHERE resetdatabase = ?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setBoolean(2, false);
            preparedStatement.execute();
            System.out.println("Reset auf true gesetzt");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void setGlobalReservierungToTrue(HttpServletRequest req) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE reservierbarglobal SET reservierbar = ? WHERE reservierbar = ?");
            preparedStatement.setBoolean(1, true);
            preparedStatement.setBoolean(2, false);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    public static Sitzplatz[] getSitzplatzArrayFromDatabase(HttpServletRequest req) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Sitzplatz[] sitzplatzarray = new Sitzplatz[100];

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("SELECT * FROM sitzplatz");
            rs = ps.executeQuery();
            int index = 0;

            while (rs.next()) {
                int sitzplatznummer = rs.getInt("sitznummer");
                String zustandAsString = rs.getString("zustand");
                boolean verkaufbar = rs.getBoolean("verkaufbar");
                String reservierungsname = rs.getString("reservierungsname");
                Zustand sitzplatzZustand = null;

                switch (zustandAsString) {
                    case "FREI":
                        sitzplatzZustand = Zustand.FREI;
                        break;
                    case "RESERVIERT":
                        sitzplatzZustand = Zustand.RESERVIERT;
                        break;
                    case "VERKAUFT":
                        sitzplatzZustand = Zustand.VERKAUFT;
                        break;
                }
                if (sitzplatzZustand != null) {
                    Sitzplatz sitzplatz = new Sitzplatz(sitzplatzZustand, sitzplatznummer, verkaufbar);
                    sitzplatz.setReservierungsName(reservierungsname);
                    sitzplatzarray[index] = sitzplatz;
                }

                index++;
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return sitzplatzarray;

    }

    public static void createDataForDatabase(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
//        Kartenverkauf kartenverkauf = (Kartenverkauf) req.getServletContext().getAttribute(Kartenverkauf.attributeName);
        int kartenverkaufSize = 100;

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            for (int i = 0; i < kartenverkaufSize; i++) {
                preparedStatement = connection.prepareStatement("INSERT INTO sitzplatz (sitznummer, zustand, verkaufbar, reservierungsname) values (?, ?, ?, ?)");
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, Zustand.FREI.toString());
                preparedStatement.setBoolean(3, true);
                preparedStatement.setString(4, "Nicht reserviert");
                preparedStatement.executeUpdate();
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void clearDatabase(HttpServletResponse resp, HttpServletRequest req) throws SQLException {
        DataSource dataSource = (DataSource) req.getServletContext().getAttribute("datasource");
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("TRUNCATE sitzplatz");
            System.out.println("clear database");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


}
