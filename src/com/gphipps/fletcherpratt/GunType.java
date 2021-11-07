package com.gphipps.fletcherpratt;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class GunType {
    // Currently scaled for 1/2" knots

    private String bore;
    private int maxRange;
    private int damage;

    static HashMap<String, GunType> gunTypes = new HashMap<>();
    public static final GunType NULL_GUN = new GunType("0", 0, 0);

    public static void initGunTypes( String filePath ) throws IOException {
        File csvFile = new File(filePath);

        CSVParser parser = CSVParser.parse(csvFile, Charset.defaultCharset(), CSVFormat.RFC4180.withDelimiter(','));

        gunTypes.put("0", NULL_GUN);

        for( CSVRecord csvRecord : parser ) {
            String inches = csvRecord.get(0).trim();
            if( inches.toLowerCase().equals("inches") ) {
                // Header Line
                continue;
            }
            if( inches.equals("#") ) {
                // Comment
                continue;
            }
            String mm = csvRecord.get(1);
            String damage = csvRecord.get(2);
            String range = csvRecord.get(3);

            gunTypes.put(inches,
                    new GunType(
                            inches,
                            Integer.parseInt(damage),
                            Integer.parseInt(range)));
        }
    }

    public GunType(String bore, int damage, int maxRange) {
        this.bore = bore;
        this.maxRange = maxRange;
        this.damage = damage;
    }

    public static GunType findByBore(String bore) {
        GunType gt = gunTypes.get(bore);
        if( gt == null) {
            System.out.println( "Gun type not found _" + bore +"_");
        }
        return gt;
    }

    public String getBore() {
        return bore;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getDamage() {
        return damage;
    }

    public String toString() {
        return String.format("[" + bore +"," + damage + "," + maxRange + "]");
    }
}
