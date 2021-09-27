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
//          p();
//        printKlassInformation(ship);
//          p_end();

        // Second line
          p();
        printPoints(ship);
        printSpeed(ship);
          p_end();


          p();
        printWeapons(ship);
        printGunTypes(ship);
          p_end();

        // Third line

          p();
        printArmorLine(ship);
          p_end();
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
        printWriter.print(String.format("<b>Points:</b> %,d ", ship.getPoints()));
    }

    private void printWeapons(Ship ship) {
        printWriter.print("<b>Primary: </b>" + ship.getPrimary().getInitialStatus(true));

        printWriter.print(SP_2 + ship.getPrimaryTurretLayout() + ",");

        printWriter.print(SP_2 + "<b>Secondary: </b>" + ship.getSecondary().getInitialStatus(true) + " Broadside,");

        if( ship.getTorpedoTubes().getCurrentCount() > 0 ) {
            printWriter.print(SP_2 + "<b>Torpedoes: </b>" + ship.getTorpedoTubes().getInitialStatus(true));
        }
        if( ship.getMines().getCurrentCount() > 0 ) {
            printWriter.print(SP_2 + ship.getMines().getInitialStatus(true));
        }
        if( ship.getAircraft().getCurrentCount() > 0 ) {
            printWriter.print(SP_2 + ship.getAircraft().getInitialStatus(true));
        }

    }


    private void printGunTypes(Ship ship) {
        p();
        printGunType( ship.getPrimary().getGunType() );
        p_end();
        p();
        printGunType( ship.getSecondary().getGunType());
        p_end();
    }

    private void printGunType(GunType gt) {
        int r = gt.getMaxRange();
        printWriter.print( "<b>"+ SP_2 + Double.toString(gt.getBore()) + "\":</b>" + SP_2 + "Max:  " + r + '"' + SP_2 +
                "Half: " + r/2 + '"' +SP_2 + "Close: " + r/4 + "\", " + SP_2 +
                "Penetrating Damage: " + gt.getDamage() + "," +  SP_2 + "Non-Penetrating: " + gt.getDamage()/2 );
    }

    private void printSpeed(Ship ship) {
        printWriter.println("&nbsp;&nbsp;&nbsp;&nbsp;<b>Speed:</b>&nbsp;&nbsp; " + ship.getSpeed().getInitialStatus(true));
    }

    private void printArmorLine(Ship ship) {
        printWriter.println("  <p><b>Armor: </b>Belt " + ship.getBelt() + ",&nbsp;&nbsp; Deck " + ship.getDeck() + ",&nbsp;&nbsp; Turret: " + ship.getTurret());

        printWriter.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Torpedo Class:</b> " + ship.getTorpedoClass() + " </p>");
        printWriter.println("  <table width=\"100%\"cellpadding=\"1\">");
        printWriter.println("    <tr>");
        printWriter.println("      <th width=\"15%\">Damage</th><th width=\"35%\">Status</th><th width=\"50%\">Loss</th>");
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
