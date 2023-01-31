package kartenverkauf;

import kartenverkauf.exceptions.*;
import kartenverkauf.servlets.MysqlServlet;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class Kartenverkauf {
    private Sitzplatz[] sitzplatzArray;
    public boolean reservierbarGlobal = true;
    private String tableHTML;
    public static final String attributeName = "kartenverkauf";
    private static final int moduloBreakAt = 10;
    private static final int indexStart = 1;

    public Kartenverkauf(HttpServletRequest req) throws SQLException {
//        sitzplatzArray = new Sitzplatz[anzahlSitzplätze];
//
//        for (int i = 0; i < sitzplatzArray.length; i++) {
//            sitzplatzArray[i] = new Sitzplatz(Zustand.FREI, i, true);
//        }
        updateSitzplatzArray(req);
    }

    public synchronized void updateSitzplatzArray(HttpServletRequest req) throws SQLException {
        sitzplatzArray = MysqlServlet.getSitzplatzArrayFromDatabase(req);
        setTableHTML();
    }


    public synchronized Sitzplatz[] getSitzplatzArray() {
        return sitzplatzArray;
    }

    public synchronized void setSitzplatzArray(Sitzplatz[] sitzplatzArray) {
        this.sitzplatzArray = sitzplatzArray;
    }

    private synchronized int getTableSize() throws NullPointerException {
        return sitzplatzArray.length;
    }

    public synchronized String getTableHTML() {
        return tableHTML;
    }


    public synchronized void setTableHTML() {
        StringBuilder sbTableHTML = new StringBuilder();
        sbTableHTML.append("<!doctype html> \n <html> \n <body> \n <head> \n <title> Create Kartenverkauf</title> \n");
        sbTableHTML.append("<meta charset=\"utf-8\" />");
        sbTableHTML.append("<link rel=\"stylesheet\" href=tableStyle.css>");
        sbTableHTML.append("</head>");
        sbTableHTML.append(createTable());
        sbTableHTML.append("</body>\n </html>");
        tableHTML = sbTableHTML.toString();
    }

    private synchronized String createTable() {
        StringBuilder sbCreateTable = new StringBuilder();
        sbCreateTable.append("<table> \n");
        sbCreateTable.append("<tr> \n");
        sbCreateTable.append("<td> \n");
        sbCreateTable.append("<table rules=\"all\" cellpadding=\"5\" border=\"2\" style=\"\"> \n");
        sbCreateTable.append("<tr> \n");
        //sbCreateTable.append("<td with=\"20\">" + index);
        //index++;
        for (int i = indexStart; i <= getTableSize(); i++) {
            switch (sitzplatzZustand(i - 1)) {
                case FREI:
                    sbCreateTable.append("<td class=\"frei mytable\">" + (i));
                    break;
                case VERKAUFT:
                    sbCreateTable.append("<td class=\"verkauft mytable\">" + (i));
                    break;
                case RESERVIERT:
                    sbCreateTable.append("<td class=\"reserviert mytable\">" + (i));
                    break;
                default:
                    sbCreateTable.append("Da ist etwas schiefgelaufen: Zustand nicht erfassbar");
            }

            sbCreateTable.append("</td> \n");
            if (i % moduloBreakAt == 0) {
                sbCreateTable.append("</tr \n");
                sbCreateTable.append("<tr> \n");
            }
        }
        sbCreateTable.append("</tr> \n");
        sbCreateTable.append(createInfobox());
        return sbCreateTable.toString();
    }

    private String createInfobox() {
        StringBuilder sbCreateInfobox = new StringBuilder();
        sbCreateInfobox.append("<table rules=\"all\" cellpadding=\"5\" border=\"2\"");
        sbCreateInfobox.append("<td align=\"right\" valign=\"bottom\" width=\"200\"");
        sbCreateInfobox.append("<tr>\n <td> frei </td \n </tr>");
        sbCreateInfobox.append("<tr>\n <td class=\"reserviert\"> reserviert </td \n </tr>");
        sbCreateInfobox.append("<tr>\n <td bgcolor=\"grey\">verkauft</td \n </tr>");
        sbCreateInfobox.append("</table \n </td> \n </tr> \n </table>");
        return sbCreateInfobox.toString();
    }

    public synchronized Zustand sitzplatzZustand(int i) {
        return sitzplatzArray[i].getZustand();
    }

    private synchronized boolean isReservierbarGlobal() {
        return reservierbarGlobal;
    }

    public synchronized String getReservierbarMessage() {
        if (isReservierbarGlobal()) {
            return "Reservierungen können noch angenommen werden";
        } else {
            return "Reservierungen können nicht mehr angenommen werden";
        }
    }

    public synchronized boolean reservieren(String name, int sitzplatz) {
        if (sitzplatzArray[sitzplatz].getZustand() == Zustand.RESERVIERT) {
            throw new BereitsReserviertException();
        }

        if(sitzplatzArray[sitzplatz].getZustand() == Zustand.VERKAUFT)
            throw new BereitsVerkauftException();

        if (!reservierbarGlobal) {
            throw new ReservierbarGlobalFalseException();
        }

        if (sitzplatzArray[sitzplatz].getVerkaufbar() && reservierbarGlobal) {
            sitzplatzArray[sitzplatz].setZustand(Zustand.RESERVIERT);
            sitzplatzArray[sitzplatz].setReservierungsName(name);
            sitzplatzArray[sitzplatz].setVerkaufbar(false);
            setTableHTML();
            return true;
        }
        return  false;
    }

    public synchronized boolean verkaufen(int sitzplatznummer) {
        if (sitzplatzArray[sitzplatznummer].getZustand() == Zustand.VERKAUFT)
            throw new BereitsVerkauftException();

        if (sitzplatzArray[sitzplatznummer].getZustand() == Zustand.RESERVIERT)
            throw new BereitsReserviertException();

        if (sitzplatzArray[sitzplatznummer].getVerkaufbar()) {
            sitzplatzArray[sitzplatznummer].setZustand(Zustand.VERKAUFT);
            sitzplatzArray[sitzplatznummer].setVerkaufbar(false);
            setTableHTML();
            return true;
        }
        return false;
    }

    public synchronized boolean stornieren(int sitzplatznummer) {
        if (sitzplatzArray[sitzplatznummer].getZustand() == Zustand.VERKAUFT)
            throw new BereitsVerkauftException();

        if (sitzplatzArray[sitzplatznummer].getZustand() == Zustand.FREI)
            throw new StornierungException();

        if (sitzplatzZustand(sitzplatznummer) == Zustand.RESERVIERT) {
            sitzplatzArray[sitzplatznummer].setZustand(Zustand.FREI);
            sitzplatzArray[sitzplatznummer].setVerkaufbar(true);
            sitzplatzArray[sitzplatznummer].clearReservierungsName();
            setTableHTML();
            return true;
        }
        return false;
    }

    public synchronized void alleReservierungenStornieren() {
        for (int i = 0; i < sitzplatzArray.length; i++) {
            if (sitzplatzArray[i].getZustand() != Zustand.VERKAUFT) {
                sitzplatzArray[i].setZustand(Zustand.FREI);
                sitzplatzArray[i].setVerkaufbar(true);
                reservierbarGlobal = false;
                setTableHTML();
            }
        }
    }

    public synchronized boolean verkaufReserviertesTicket(int sitzplatzNummer, String name) {
        if (sitzplatzArray[sitzplatzNummer].getZustand() != Zustand.RESERVIERT) {
            throw new VerkaufReserviertesTicketException();
        }
        if (sitzplatzArray[sitzplatzNummer].getReservierungsname().equals(name)) {
            sitzplatzArray[sitzplatzNummer].setZustand(Zustand.VERKAUFT);
            sitzplatzArray[sitzplatzNummer].setVerkaufbar(false);
            setTableHTML();
            return true;
        } else {
            throw new VerkaufReserviertesTicketException();
        }
    }

}
