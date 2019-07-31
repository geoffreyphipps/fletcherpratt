/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gphipps.fletcherpratt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * 
 */
public class CSVReader {

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
  private static final int PRIMARY_TURET_LAYOUT = 19;
  private static final int NOTES = 20;

  private File outputDirectory;

  /**
   *  Reads a CSV file full of ship definitions and prints their logs.
   *  The inner loop that does the work.
   */
  public void processFile(String fleetInputFileName, String outputDirectoryName ) {
    InputStreamReader reader = null;

    try {
      reader = new InputStreamReader(new FileInputStream(fleetInputFileName));
    } catch (Exception ex) {
      System.out.println("Failed to read CSV file " + fleetInputFileName +", " + ex);
      return;
    }

    
    try {
      File dir = new File( outputDirectoryName);
      if( !(dir.exists() && dir.isDirectory())) {
        dir.mkdirs();
      }
      setOutputDirectory( dir );
    }  catch (Exception ex) {
      System.out.println("Failed to create output directory, " + outputDirectoryName + ex);
      return;
    }


    BufferedReader br = new BufferedReader(reader);

    System.out.println("Opened file '" + fleetInputFileName + "'");
    System.out.println("Writing to '" + getOutputDirectory() + "'");
    int lineNumber = 0;
    String line = "";
    try {
      while ((line = br.readLine()) != null) {
        //LOGGER.debug( "Line number " + lineNumber +"<" + line +">" );
        lineNumber++;
        String[] sections = line.split(",");
        if(sections.length <= 1 ) {
          continue;
        }
        String s = stripQuotes(sections[0]);
        // The inner loop that does all the work. Read a ship, write out the log
        if (!s.equals("Name")) {
          Ship ship = readShip(sections);
          ship.createShipLog(new HTMLOutput( getOutputDirectory().getPath() + File.separatorChar + ship.getName()));
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
    theShip.setSecondary(new GunBattery(toInt(sections[SECONDARY_NUMBER]), toDouble(sections[SECONDARY_BORE])));
    theShip.setTorpedoTubes(new TorpedoTubes(toInt(sections[TORPEDO_TUBES])));
    theShip.setBelt(new Armour(toDouble(sections[BELT]), "belt"));
    theShip.setDeck(new Armour(toDouble(sections[DECK]), "deck"));
    theShip.setTurret(new Armour(toDouble(sections[TURRET]), "turret"));
    theShip.setMines(new Mines(toInt(sections[MINES])));
    theShip.setAircraft(new Aircraft(toInt(sections[AIRCRAFT])));
    theShip.setSpeed(new Speed(toInt(sections[SPEED])));
    theShip.setStandardDisplacement(toInt(sections[TONNAGE]));
    theShip.setTorpedoClass(sections[TORPEDO_CLASS]);
    theShip.setPrimaryTurretLayout(sections[PRIMARY_TURET_LAYOUT].replaceAll("\"", ""));
    if( sections.length == NOTES+1 ) {
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

  /**
   * This is the main to use.
   * First argument is the path to a fleet data file (CSV file of ships, one per line).
   * Second argument is path to output directory
   * @param args
   */
  public static void main(String args[]) {
    CSVReader reader = new CSVReader();

    reader.processFile(args[0], args[1]);
  }

  public File getOutputDirectory() {
    return outputDirectory;
  }

  public void setOutputDirectory(File outputDirectory) {
    this.outputDirectory = outputDirectory;
  }
}
