package com.gphipps.fletcherpratt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * 
 */
public class HTMLOutput implements OutputChannel {

  private String fieldSeparator = "; ";
  private PrintWriter printWriter;

  public HTMLOutput(String filename) throws FileNotFoundException, IOException {
    printWriter = new PrintWriter(new File(filename + ".html"), "UTF8");
  }

  public void header(Ship ship) {
    String nbsp = "&nbsp;";
    for( int i = 1; i < 25; i++ ) {
      nbsp+= "&nbsp;";
    }
    printWriter.println("<html>");
    printWriter.println("  <head>");
    printWriter.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    printWriter.println("    <style type=\"text/css\">");
    printWriter.println("      body {");
    printWriter.println("        font-family: Verdana, Arial;");
    printWriter.println("        font: Verdana, Helvetica, sans-serif;");
    printWriter.println("        width: 700;"); //881
    printWriter.println("        margin: 0px auto;");
    printWriter.println("        border: 1;");
    printWriter.println("        font-size: 11px;");
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
    printWriter.println("<body>");
    printWriter.println("  <h1>" + ship.getName() + nbsp + ship.getNationality() + " " + ship.getType() + "</h1>");
    printWriter.print("  <p><b>" + ship.getKlass() + "</b> class, Launched " + ship.getLaunched());
    if( ship.getLaunched() == ship.getRebuilt()  ) {
       printWriter.println("</p>");
    } else {
      printWriter.println( ", Rebuilt " + ship.getRebuilt() + "</p>");
    }
    String s = String.format("%,d",ship.getPoints());
    printWriter.print("  <p><b>" + s + "</b> points &nbsp;&nbsp;" + ship.getPrimary().getInitialStatus(true));
    
    printWriter.print("&nbsp;&nbsp; " + ship.getSecondary().getInitialStatus(true));

    if (ship.getTorpedoTubes().getCurrentCount() > 0) {
      printWriter.print("&nbsp;&nbsp;  " + ship.getTorpedoTubes().getInitialStatus(true));
    }
    if (ship.getMines().getCurrentCount() > 0) {
      printWriter.print("&nbsp;&nbsp; " + ship.getMines().getInitialStatus(true));
    }
    if (ship.getAircraft().getCurrentCount() > 0) {
      printWriter.print("&nbsp;&nbsp; " + ship.getAircraft().getInitialStatus(true));
    }

    printWriter.println("&nbsp;&nbsp;  " + ship.getSpeed().getInitialStatus(true));

    printWriter.print("  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Turret Layout: " + ship.getPrimaryTurretLayout());
    printWriter.print("  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + ship.getNotes() + "</p>");


    printWriter.println("  <p>Belt: " + ship.getBelt() + "&nbsp;&nbsp; Deck: " + ship.getDeck() + "&nbsp;&nbsp; Turret: " + ship.getTurret() );

    printWriter.println("&nbsp;&nbsp;&nbsp;&nbsp;Torpedo Class: " + ship.getTorpedoClass() + " </p>");
    printWriter.println("  <table width=\"100%\"cellpadding=\"1\">");
    printWriter.println("    <tr>");
    printWriter.println("      <th width=\"20%\">Damage</th><th width=\"25%\">Losses</th><th width=\"55%\">Status</th>");
    printWriter.println("    </tr>");

  }

  public void footer() {
    printWriter.println("</table>");
    printWriter.println("</body>");
    printWriter.println("</html>");

    printWriter.close();
  }

  public void title(String s) {
  }

  public void record(String field1, String field2, String field3) {
    printWriter.println("    <tr>");
    printWriter.print("      <td class=\"points\"><b>" + field1 + "</b></td>");
    printWriter.print("      <td class=\"points\">" + field2 + "</td>");
    printWriter.print("      <td class=\"points\">" + field3 + "</td>");
    printWriter.println("    </tr>");
  }

  public void record(long field1, String field2, String field3) {
    record(String.valueOf(field1), field2, field3);
  }

  public String getFieldSeparator() {
    return fieldSeparator;
  }

  public void setFieldSeparator(String fieldSeparator) {
    this.fieldSeparator = fieldSeparator;
  }
}
