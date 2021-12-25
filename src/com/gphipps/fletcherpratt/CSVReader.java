package com.gphipps.fletcherpratt;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class CSVReader {

  private static final char DELIMITER = ',';

  enum Fields {
//    BASE_1,
    NAME,
    CLASS,
    NUMBER_MINIATURES,
    LAUNCHED,
    REBUILT,
    NATIONALITY,
    TYPE,
    PRIMARY_NUMBER,
    PRIMARY_BORE,
    SECONDARY_NUMBER,
    SECONDARY_BORE,
    TORPEDO_TUBES,
    BELT,
    DECK,
    TURRET,
    MINES,
    AIRCRAFT,
    SPEED,
    TONNAGE,
    TORPEDO_CLASS,
    PRIMARY_TURRET_LAYOUT,
    TORPEDO_TUBE_LAYOUT,
    NOTES,
  }

  private File outputDirectory;

  /**
   *  Reads a CSV file full of ship definitions and prints their logs.
   *  The inner loop that does the work.
   */
  public void processFile(String fleetInputFileName, String outputDirectoryName,
                          PrintWriter summaryPrintWriter, PrintWriter shipLabelsPrintWriter) {
    CSVParser parser;
    try {
      parser = CSVParser.parse(new File(fleetInputFileName), Charset.defaultCharset(), CSVFormat.RFC4180.withDelimiter(DELIMITER));
    } catch (Exception ex) {
      System.out.println("Failed to read CSV file " + fleetInputFileName +", " + ex);
      return;
    }

    try {
      File dir = new File(outputDirectoryName);
      if( !(dir.exists() && dir.isDirectory())) {
        dir.mkdirs();
      }
      setOutputDirectory( dir );
    }  catch (Exception ex) {
      System.out.println("Failed to create output directory, " + outputDirectoryName + ex);
      return;
    }

    System.out.println("Opened file '" + fleetInputFileName + "'");
    System.out.println("Writing to '" + getOutputDirectory() + "'");
    int lineNumber = 0;
    String line = "";
    Set<String> klassesSummarised = new HashSet<String>(50);
    int shipLabelsCounter = 0;
    try {
      for( CSVRecord csvRecord : parser ) {
        lineNumber++;

        // blank line
        if( csvRecord.get(0).trim().equals("")) {
          continue;
        }

        // The inner loop that does all the work. Read a ship, write out the log
        // Ignore comment lines that start with #
        if (!csvRecord.get(0).equals("Name") && !csvRecord.get(0).startsWith("#") && !stripQuotes(csvRecord.get(1)).equals("Example") ) {
          Iterator<String> it =csvRecord.iterator();
          String[] asArray = new String[Fields.NOTES.ordinal()+1];
          int i =0;
          while( it.hasNext()) {
            asArray[i] = it.next();
            i++;
          }
          String[] shipNames = asArray[0].split(",");
          for( String shipName : shipNames ) {
            asArray[0]= shipName.trim();
            Ship ship = readShip(asArray);
            ship.createShipLog(new HTMLOutput(getOutputDirectory().getPath() + File.separatorChar + ship.getName()));
            if( ! klassesSummarised.contains(ship.getKlass()) ) {
              klassesSummarised.add(ship.getKlass());
              String fields = ship.getSummaryLine();
              summaryPrintWriter.print(fields);
              summaryPrintWriter.println( "," + ship.getName());
            }
            shipLabelsPrintWriter.print( ship.getKlass() +"," + ship.getName() + "," );
            if( shipLabelsCounter > 3 ) {
              shipLabelsCounter = 0;
              shipLabelsPrintWriter.println();
            } else {
              shipLabelsCounter++;
            }
          }
        }
      }
    } catch (Exception ex) {
      System.err.println( "Error on line " + lineNumber + " of file " + fleetInputFileName );
      ex.printStackTrace();
    }

    System.out.println("Load finished, number lines processed: " + lineNumber);
  }

  /**
   * Reads a ship, creates it.
   *
   * @param sections
   * @throws IOException
   */
  private Ship readShip( String[] sections) throws IOException {

    Ship theShip = new Ship(stripQuotes(sections[Fields.NAME.ordinal()]), stripQuotes(sections[Fields.CLASS.ordinal()]),
            sections[Fields.LAUNCHED.ordinal()], sections[Fields.REBUILT.ordinal()]);

    theShip.setNationality( stripQuotes(sections[Fields.NATIONALITY.ordinal()]));
    theShip.setNumberOfMiniatures( toInt(sections[Fields.NUMBER_MINIATURES.ordinal()]));
    theShip.setType( stripQuotes(sections[Fields.TYPE.ordinal()]));
    theShip.setPrimary(new GunBattery(toInt(sections[Fields.PRIMARY_NUMBER.ordinal()]),sections[Fields.PRIMARY_BORE.ordinal()]));

    // Halve the number of secondaries
    theShip.setSecondary(new GunBattery(toInt(sections[Fields.SECONDARY_NUMBER.ordinal()])/2, sections[Fields.SECONDARY_BORE.ordinal()]));

    theShip.setTorpedoTubes(new TorpedoTubes(toInt(sections[Fields.TORPEDO_TUBES.ordinal()])));
    theShip.setBelt(new Armour(toDouble(sections[Fields.BELT.ordinal()]), "belt"));
    theShip.setDeck(new Armour(toDouble(sections[Fields.DECK.ordinal()]), "deck"));
    theShip.setTurret(new Armour(toDouble(sections[Fields.TURRET.ordinal()]), "turret"));
    theShip.setMines(new Mines(toInt(sections[Fields.MINES.ordinal()])));
    theShip.setAircraft(new Aircraft(toInt(sections[Fields.AIRCRAFT.ordinal()])));
    theShip.setSpeed(new Speed(toInt(sections[Fields.SPEED.ordinal()])));
    theShip.setStandardDisplacement(toInt(sections[Fields.TONNAGE.ordinal()]));
    theShip.setTorpedoClass(sections[Fields.TORPEDO_CLASS.ordinal()]);
    int d = Fields.PRIMARY_TURRET_LAYOUT.ordinal();
    String s = sections[Fields.PRIMARY_TURRET_LAYOUT.ordinal()];
    theShip.setPrimaryTurretLayout(sections[Fields.PRIMARY_TURRET_LAYOUT.ordinal()].replaceAll("\"", ""));
    if( sections[Fields.TORPEDO_TUBE_LAYOUT.ordinal()] != null ) {
      theShip.setTorpedoTubeLayout(sections[Fields.TORPEDO_TUBE_LAYOUT.ordinal()].replaceAll("\"", ""));
    }
    if( sections[Fields.NOTES.ordinal()] != null ) {
      theShip.setNotes(sections[Fields.NOTES.ordinal()].replaceAll("\"", ""));
    }

    return theShip;
  }

  private String stripQuotes( String s ) {
    return s.replaceAll("\"", "");
  }

  private int toInt(String s) {
    return Integer.parseInt(s);
  }

  private double toDouble(String s) {
    return Double.parseDouble(s);
  }

  public File getOutputDirectory() {
    return outputDirectory;
  }

  public void setOutputDirectory(File outputDirectory) {
    this.outputDirectory = outputDirectory;
  }
}
