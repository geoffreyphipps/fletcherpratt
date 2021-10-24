package tests.tests.com.gphipps.fletcherpratt;

import com.gphipps.fletcherpratt.*;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * TODO Convert to Junit
 */
public class testShips {

    public void testLaArgentina(String args[]) throws FileNotFoundException, IOException {
        Ship laArgentina = new Ship("La Argentina","Example", "1912", "1923");
        laArgentina.setPrimary(new GunBattery(9, "6"));
        laArgentina.setSecondary(new GunBattery(4, "4"));
        laArgentina.setTorpedoTubes(new TorpedoTubes(6));
        laArgentina.setBelt( new Armour(3, "belt"));
        laArgentina.setDeck( new Armour(2, "deck"));
        laArgentina.setTurret( new Armour(2, "turret"));
        laArgentina.setAircraft( new Aircraft(2));
        laArgentina.setSpeed(new Speed(31));
        laArgentina.setStandardDisplacement(6000);
        laArgentina.createShipLog(new CSVOutput("test"));
    }
}
