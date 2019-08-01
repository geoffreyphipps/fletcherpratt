package com.gphipps.fletcherpratt;

import java.io.File;

/**
 * Reads all the CSVs in the first argument.
 * For each CSV, create a subdirectory of the matching name (with .csv stripped) in the output directory
 * and write all the ship logs into that subdirectory.
 */
public class ProcessAll {

    public static void main(String args[]) {
        CSVReader reader = new CSVReader();
        File inputDir = new File(args[0]);
        File[] inputFiles = inputDir.listFiles();
        // Find all csv files in dir

        File outputRootDir = new File(args[1]);

        for( File file : inputFiles ) {
            if( file.getPath().endsWith(".csv")) {
                File outputDir = new File(outputRootDir.getPath() + File.separatorChar + file.getName().replace(".csv", ""));
                outputDir.mkdir();
                reader.processFile(file.getPath(), outputDir.getPath());
            }
        }
    }

}
