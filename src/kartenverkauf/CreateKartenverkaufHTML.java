package kartenverkauf;
public class CreateKartenverkaufHTML {
    private int tableSize = 100;
    private String kartenverkaufTableHTML;

    public int getTableSize() {
        return tableSize;
    }

    public String getKartenverkaufTableHTML() {
        return kartenverkaufTableHTML;
    }

   public void setKartenverkaufTableHTML(int newLineAtModulo, int indexStart) {
       StringBuffer sb = new StringBuffer();
       sb.append("<!doctype html> \n <html> \n <body> \n <head> \n <title> Create Kartenverkauf</title> \n");
       sb.append("<meta charset=\"utf-8\" />");
       sb.append("</head>");
       sb.append(createTable(newLineAtModulo,indexStart));
       sb.append("</body>\n </html>");
       kartenverkaufTableHTML = sb.toString();
   }

    private String createTable(int tr_breakAtModulo, int index) {
        StringBuffer sb = new StringBuffer();
        sb.append("<table> \n");
        sb.append("<tr> \n");
        sb.append("<td> \n");
        sb.append("<table rules=\"all\" cellpadding=\"5\" border=\"2\" style=\"\"> \n");
        sb.append("<tr> \n");
        sb.append("<td with=\"20\">" + index);
        index++;
        //sb.append("<td> \n");
        for (int j = index; j <= this.tableSize; j++) {
            sb.append("<td>" + j);
            sb.append("</td> \n");
            if (j % tr_breakAtModulo == 0) {
                sb.append("</tr \n");
                sb.append("<tr> \n");
            }
        }
        sb.append("</tr> \n");
        sb.append(createInfobox());
        return sb.toString();
    }

    private String createInfobox() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table rules=\"all\" cellpadding=\"5\" border=\"2\"");
        sb.append("<td align=\"right\" valign=\"bottom\" width=\"200\"");
        sb.append("<tr>\n <td> frei </td \n </tr>");
        sb.append("<tr>\n <td> resserviert </u> </td \n </tr>");
        sb.append("<tr>\n <td bgcolor=\"grey\">verkauft</td \n </tr>");
        sb.append("</table \n </td> \n </tr> \n </table>");
        return sb.toString();
    }
}
