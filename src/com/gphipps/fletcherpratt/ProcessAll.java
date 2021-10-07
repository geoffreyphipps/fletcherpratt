package com.gphipps.fletcherpratt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Reads all the CSVs in the first argument.
 * Second output is the output directory.
 * For each CSV, create a subdirectory of the matching name (with .csv stripped) in the output directory
 * and write all the ship logs into that subdirectory.
 */
public class ProcessAll {

    public static void main(String args[]) throws FileNotFoundException, UnsupportedEncodingException {
        CSVReader reader = new CSVReader();
        File inputDir = new File(args[0]);
        File[] inputCSVFiles = inputDir.listFiles();
        // Find all csv files in dir

        File outputRootDir = new File(args[1]);
        outputRootDir.mkdir();
        File summaryOutputFile = new File(outputRootDir.getPath() +  File.separatorChar +  "summary.csv");
        PrintWriter summaryPrintWriter = new PrintWriter(summaryOutputFile, "UTF8");
        summaryPrintWriter.println("Name, Class, Class, Nationality, Displacement, Classic Points, New Defensive Points, Std Primary Damage, Scenario Points");

        for( File CSVFile : inputCSVFiles ) {
            if( CSVFile.getPath().endsWith(".csv")) {
                File outputDir = new File(outputRootDir.getPath() + File.separatorChar + CSVFile.getName().replace(".csv", ""));
                outputDir.mkdir();
                reader.processFile(CSVFile.getPath(), outputDir.getPath(), summaryPrintWriter);
            }
        }
    }

}
