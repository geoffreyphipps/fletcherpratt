package com.gphipps.fletcherpratt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLOutput implements OutputChannel {

    private static final String SP_2 = "&nbsp;&nbsp; ";

    private String fieldSeparator = "; ";
    private PrintWriter printWriter;

    public HTMLOutput( String filename ) throws FileNotFoundException, IOException {
        printWriter = new PrintWriter(new File(filename + ".html"), "UTF8");
    }

    public void header( Ship ship ) {
        StringBuilder sb = new StringBuilder("&nbsp;");
        for( int i = 1; i < 6; i++ ) {
            sb.append("&nbsp;");
        }
        String nbsps = sb.toString();
        printWriter.println("<html>");
        printHTMLHeader(ship);
        printWriter.println("<body>");

        printHeadingLine(ship, nbsps);

        // First Line
          p();
        printPoints(ship);
        printWriter.println(SP_2+SP_2);
        printSpeed(ship);
          p_end();

        p();
        printArmorLine(ship);
        p_end();

        p();
        printTorpedoDamageLine(ship);
        p_end();

          p();
        printWeapons(ship);
          p_end();
        printGunTypes(ship);
        p(); p_end();

        // Third line

        startDamageTable();
    }

    private void p_end() {
        printWriter.println("</p>");
    }

    private void p() {
        printWriter.println("<p>");
    }

    private void printHTMLHeader(Ship ship) {
        printWriter.println("  <head>");
        printWriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        printWriter.println("    <style type=\"text/css\">");
        printWriter.println("      body {");
        printWriter.println("        font-family: Verdana, Arial;");
        printWriter.println("        font: Verdana, Helvetica, sans-serif;");
        printWriter.println("        width: 700;"); //881
        printWriter.println("        margin: 0px auto;");
        printWriter.println("        border: 1;");
        printWriter.println("        font-size: 12px;");
        printWriter.println("        text-align: left;");
        printWriter.println("      }");
        printWriter.println("      th {");
        printWriter.println("        text-align: right;");
        printWriter.println("      }");
        printWriter.println("      table, th, td {");
        printWriter.println("        border: 1px solid black;");
        printWriter.println("        border-collapse: collapse;");
        printWriter.println("      }");
        printWriter.println("      th.guns-header-multispan {");
        printWriter.println("        text-align: center;");
        printWriter.println("        font-size: 12px;");
        printWriter.println("      }");
        printWriter.println("      th.guns-header {");
        printWriter.println("        text-align: center;");
        printWriter.println("        font-size: 12px;");
        printWriter.println("      }");
        printWriter.println("      td.guns {");
        printWriter.println("        text-align: center;");
        printWriter.println("        font-size: 12px;");
        printWriter.println("      }");
        printWriter.println("      td.points {");
        printWriter.println("        text-align: right;");
        printWriter.println("        font-size: 12px;");
        printWriter.println("      }");
        printWriter.println("    </style>");

        printWriter.println("  <title>" + ship.getName() + "</title>");
        printWriter.println("</head>");
    }

    private void printHeadingLine(Ship ship, String nbsp) {
        printWriter.println("  <h1>" + ship.getName() + nbsp+ ship.getKlass() + " class" + nbsp+ ship.getNationality() + " " + ship.getType() + "</h1>");
    }

    private void printKlassInformation(Ship ship) {
        printWriter.print(SP_2 +" Launched " + ship.getLaunched());
        if(! ship.getLaunched().equals(ship.getRebuilt()) ) {
            printWriter.print(", Rebuilt " + ship.getRebuilt());
        }
        printWriter.println("," +SP_2 +" Standard Displacement " + String.format("%,d", ship.getStandardDisplacement()));
        printWriter.print("  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ship.getNotes());
    }

    private void printPoints(Ship ship) {
        printWriter.print(String.format("<b>Classic Points:</b> %,d ", ship.getClassicPoints()));
        printWriter.print(String.format(SP_2 + "<b>Defensive:</b> %,d ", ship.getNewDefensivePoints()));
        printWriter.print(String.format(SP_2 + "<b>Balance:</b> %,d ", ship.getBalancePoints()));
    }

    private void printWeapons(Ship ship) {
        printWriter.print("<b>Batteries:</p><p>" + SP_2 + "Primary: </b>" + ship.getPrimary().getInitialStatus(true));

        printWriter.print(SP_2 + ship.getPrimaryTurretLayout() );

        if( ! ship.getSecondary().isNull()) {
            printWriter.println(SP_2 + SP_2 + "<b>Secondary: </b>" + ship.getSecondary().getInitialStatus(true) + " Broadside");
        }
        p_end();
        p();

        if( ship.getTorpedoTubes().getCurrentCount() > 0 ) {
            printWriter.print(SP_2 + "<b>Torps: </b>" + ship.getTorpedoTubes().getInitialStatus(true));
            printWriter.print(SP_2  + ship.getTorpedoTubeLayout());
        }
        if( ship.getMines().getCurrentCount() > 0 ) {
            printWriter.print(SP_2 + ship.getMines().getInitialStatus(true));
        }
        if( ship.getAircraft().getCurrentCount() > 0 ) {
            printWriter.print(SP_2 + ship.getAircraft().getInitialStatus(true));
        }
    }


    private void printGunTypes(Ship ship) {
        printWriter.println("  <table width=\"80% \"cellpadding=\"1\">");
        printWriter.println("    <tr>");
        printWriter.println("      <th colspan=\"1\" class=\"guns-header-multispan\" rowspan=\"2\"></th>");
        printWriter.println("      <th colspan=\"6\" class=\"guns-header-multispan\">Range</th>");
        printWriter.println("      <th colspan=\"2\" rowspan=\"2\" class=\"guns-header-multispan\">Damage</th>");
        printWriter.println("    </tr>");
        printWriter.println("    <tr>");
        printWriter.println("      <th class=\"guns-header\"colspan=\"2\">Deck</th>");
        printWriter.println("      <th class=\"guns-header\" colspan=\"4\">Belt</th>");
        printWriter.println("    </tr>");
        printWriter.println("    <tr>");
        printWriter.println("      <th class=\"guns-header\">Bore</th>");
        printWriter.println("      <th class=\"guns-header\">Long</th>");
        printWriter.println("      <th class=\"guns-header\">Over</th>");
        printWriter.println("      <th class=\"guns-header\">Medium</th>");
        printWriter.println("      <th class=\"guns-header\">Over</th>");
        printWriter.println("      <th class=\"guns-header\">Short</th>");
        printWriter.println("      <th class=\"guns-header\">Over</th>");
        printWriter.println("      <th class=\"guns-header\">Penetrating</th>");
        printWriter.println("      <th class=\"guns-header\">Non-Penetrating </th>");
        printWriter.println("    </tr>");

        printGunType( ship.getPrimary().getGunType(), true );
        if( ! ship.getSecondary().isNull()) {
            printGunType(ship.getSecondary().getGunType(), false);
        }
        printWriter.println("  </table>");
    }

    private void printGunType(GunType gt, boolean printOvers) {
        int r = gt.getMaxRange();
        printWriter.println("    <tr>");
        printWriter.println("      <td class=\"guns\">" + gt.getBore() + "\"</th>");
        printWriter.println("      <td class=\"guns\">" + r + "\"" + "</th>");
        if( printOvers) {
            printWriter.println("      <td class=\"guns\">" + "1/4\"" + "</th>");
        } else {
            printWriter.println("      <td class=\"guns\">" + "–" + "</th>");
        }
        printWriter.println("      <td class=\"guns\">" + 3*r/4 + "\"" + "</th>");
        if( printOvers) {
            printWriter.println("      <td class=\"guns\">" + "1/2\"" + "</th>");
        } else {
            printWriter.println("      <td class=\"guns\">" + "–" + "</th>");
        }
        printWriter.println("      <td class=\"guns\">" + r/4 + "\"" + "</th>");
        if( printOvers) {
            printWriter.println("      <td class=\"guns\">" + "1\"" + "</th>");
        } else {
            printWriter.println("      <td class=\"guns\">" + "–" + "</th>");
        }
        printWriter.println("      <td class=\"guns\">" + gt.getDamage() + "</th>");
        printWriter.println("      <td class=\"guns\">" + gt.getDamage()/2 + "</th>");
        printWriter.println("    </tr>");
    }

    private void printSpeed(Ship ship) {
        printWriter.println("&nbsp;&nbsp;&nbsp;&nbsp;<b>Speed:</b>&nbsp;&nbsp; " + ship.getSpeed().getInitialStatus(true));
    }

    private void printArmorLine(Ship ship) {
        printWriter.println("  <b>Armor: </b>Belt " + ship.getBelt() + ",&nbsp;&nbsp; Deck " + ship.getDeck());
    }

    private void  printTorpedoDamageLine(Ship ship) {
        printWriter.println("<b>Torpedo Defense:</b> " + ship.getTorpedoClass() );
        printWriter.println("</p> <p>" );
        printWriter.println("  <table width=\"80% \"cellpadding=\"2\">");
        printWriter.println("    <tr>");
        printWriter.println("      <th width=\"20%\" class=\"guns-header\">1st Hit</th>");
        printWriter.println("      <th width=\"20%\" class=\"guns-header\">2nd Hit</th>");
        printWriter.println("      <th width=\"20%\" class=\"guns-header\">3rd Hit</th>");
        printWriter.println("      <th width=\"20%\" class=\"guns-header\">4th Hit</th>");
        printWriter.println("      <th width=\"20%\" class=\"guns-header\">5th Hit</th>");
        printWriter.println("    </tr>");

        printWriter.println("    <tr>");
        for( int hitHumber = 0; hitHumber < TorpedoTubes.MAX_TORPEDO_HITS; hitHumber++ ) {
            int points = (int) (ship.getNewDefensivePoints() * TorpedoTubes.getPercentageDamage(ship.getTorpedoClass(), hitHumber)) +1;
            if( points >= ship.getNewDefensivePoints() ) {
                printWriter.print("      <td class=\"guns\">" + "Sunk" + "</td>");
            } else {

                printWriter.print("      <td class=\"guns\">" + points + "</td>");
            }
        }
        printWriter.println("    </tr>");
        printWriter.println("  </table>");
    }

    private void startDamageTable() {
        printWriter.println("  <table width=\"80% \"cellpadding=\"2\">");
        printWriter.println("    <tr>");
        printWriter.println("      <th width=\"15%\">Damage</th>");
        printWriter.println("      <th width=\"55%\">Status</th>");
        printWriter.println("      <th width=\"30%\">Loss</th>");
        printWriter.println("    </tr>");
    }

    public void footer() {
        printWriter.println("</table>");
        printWriter.println("</body>");
        printWriter.println("</html>");

        printWriter.close();
    }

    public void title( String s ) {
    }

    /**
     * Writes one HTML row of the ship log, three columns: Damage, Losses, Status
     *
     * @param field1 Damage
     * @param field2 Loss
     * @param field3 Status
     */
    public void record( String field1, String field2, String field3 ) {
        printWriter.println("    <tr>");
        printWriter.print("      <td class=\"points\"><b>" + field1 + "</b></td>");
        printWriter.print("      <td class=\"points\">" + field2 + "</td>");
        printWriter.print("      <td class=\"points\">" + field3 + "</td>");
        printWriter.println("    </tr>");
    }

    public void record( long field1, String field2, String field3 ) {
        record(String.valueOf(field1), field2, field3);
    }

}
