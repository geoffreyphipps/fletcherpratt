package com.gphipps.fletcherpratt;

import java.io.*;

/**
 * Reads all the CSVs in the first argument.
 * Second output is the output directory.
 * For each CSV, create a subdirectory of the matching name (with .csv stripped) in the output directory
 * and write all the ship logs into that subdirectory.
 */
public class ProcessAll {

    public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        CSVReader reader = new CSVReader();
        File inputDir = new File(args[0]);
        File[] inputCSVFiles = inputDir.listFiles();
        // Find all csv files in dir
        GunType.initGunTypes(inputDir.getPath() + "/weapons/gunTypes0_5_inch.csv");

        File outputRootDir = new File(args[1]);
        outputRootDir.mkdir();
        File summaryOutputFile = new File(outputRootDir.getPath() +  File.separatorChar +  "fletcher_pratt_summary.csv");
        PrintWriter summaryPrintWriter = new PrintWriter(summaryOutputFile, "UTF8");

        File shipLabelsFile = new File(outputRootDir.getPath() +  File.separatorChar +  "fletcher_pratt_ship_labels.csv");
        PrintWriter shipLabelsPrintWriter = new PrintWriter(shipLabelsFile, "UTF8");
        summaryPrintWriter.println(
                "Name, Class, Nationality, Type, Number of Miniatures, " +
                        "Primary Number, Primary Bore, Secondary Number, Secondary Bore, Torpedo Tubes," +
                        "Belt, Deck, Turret, " +
                        "Speed, Displacement, " +
                        "Classic Points, New Defensive Points, Std Primary Damage, Torpedo Damage, Scenario Points, Name");
        shipLabelsPrintWriter.println("class, name, class, name, class, name, class, name, class, name, class, name");
        for( File CSVFile : inputCSVFiles ) {
            if( CSVFile.getPath().endsWith(".csv")) {
                File outputDir = new File(outputRootDir.getPath() + File.separatorChar + CSVFile.getName().replace(".csv", ""));
                outputDir.mkdir();
                reader.processFile(CSVFile.getPath(), outputDir.getPath(), summaryPrintWriter, shipLabelsPrintWriter);
                shipLabelsPrintWriter.println();
            }
        }
        summaryPrintWriter.close();
        shipLabelsPrintWriter.close();
    }

}
