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

  private static final int NAME = 0;
  private static final int CLASS = 1;
  private static final int LAUNCHED = 2;
  private static final int REBUILT = 3;
  private static final int NATIONALITY = 4;
  private static final int TYPE = 5;
  private static final int PRIMARY_NUMBER = 6;
  private static final int PRIMARY_BORE = 7;
  private static final int SECONDARY_NUMBER = 8;
  private static final int SECONDARY_BORE = 9;
  private static final int TORPEDO_TUBES = 10;
  private static final int BELT = 11;
  private static final int DECK =12;
  private static final int TURRET = 13;
  private static final int MINES = 14;
  private static final int AIRCRAFT = 15;
  private static final int SPEED = 16;
  private static final int TONNAGE = 17;
  private static final int TORPEDO_CLASS = 18;
  private static final int PRIMARY_TURRET_LAYOUT = 19;
  private static final int TORPEDO_TUBE_LAYOUT = 20;
  private static final int NOTES = 21;

  private File outputDirectory;

  /**
   *  Reads a CSV file full of ship definitions and prints their logs.
   *  The inner loop that does the work.
   */
  public void processFile(String fleetInputFileName, String outputDirectoryName, PrintWriter summaryPrintWriter) {
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
    try {
      for( CSVRecord csvRecord : parser ) {
        lineNumber++;

        // blank line
        if( csvRecord.get(0).trim().equals("")) {
          continue;
        }

        // The inner loop that does all the work. Read a ship, write out the log
        if (!csvRecord.get(0).equals("Name") && !stripQuotes(csvRecord.get(1)).equals("Example") ) {
          Iterator<String> it =csvRecord.iterator();
          String[] asArray = new String[NOTES+1];
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
              summaryPrintWriter.println(fields);
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

    Ship theShip = new Ship(stripQuotes(sections[NAME]), stripQuotes(sections[CLASS]), sections[LAUNCHED], sections[REBUILT]);

    theShip.setNationality( stripQuotes(sections[NATIONALITY]));
    theShip.setType( stripQuotes(sections[TYPE]));
    theShip.setPrimary(new GunBattery(toInt(sections[PRIMARY_NUMBER]), toDouble(sections[PRIMARY_BORE])));

    // Halve the number of secondaries
    theShip.setSecondary(new GunBattery(toInt(sections[SECONDARY_NUMBER])/2, toDouble(sections[SECONDARY_BORE])));

    theShip.setTorpedoTubes(new TorpedoTubes(toInt(sections[TORPEDO_TUBES])));
    theShip.setBelt(new Armour(toDouble(sections[BELT]), "belt"));
    theShip.setDeck(new Armour(toDouble(sections[DECK]), "deck"));
    theShip.setTurret(new Armour(toDouble(sections[TURRET]), "turret"));
    theShip.setMines(new Mines(toInt(sections[MINES])));
    theShip.setAircraft(new Aircraft(toInt(sections[AIRCRAFT])));
    theShip.setSpeed(new Speed(toInt(sections[SPEED])));
    theShip.setStandardDisplacement(toInt(sections[TONNAGE]));
    theShip.setTorpedoClass(sections[TORPEDO_CLASS]);
    theShip.setPrimaryTurretLayout(sections[PRIMARY_TURRET_LAYOUT].replaceAll("\"", ""));
    if( sections[TORPEDO_TUBE_LAYOUT] != null ) {
      theShip.setTorpedoTubeLayout(sections[TORPEDO_TUBE_LAYOUT].replaceAll("\"", ""));
    }
    if( sections[NOTES] != null ) {
      theShip.setNotes(sections[NOTES].replaceAll("\"", ""));
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
