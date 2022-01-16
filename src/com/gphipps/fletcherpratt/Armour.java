package com.gphipps.fletcherpratt;

import java.text.DecimalFormat;

public class Armour extends Feature {
    private double thickness;
    public Armour( double thickness, String description) {
        super(description);
        this.thickness= thickness;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getPoints() {
      return 10 * thickness * thickness;
    }

    public double getNewDefensivePoints() {
        return 10 * thickness * thickness; // Math.pow(thickness, 2);
    }

    public String toString() {
        return new DecimalFormat("##.#").format(thickness) + '"';
    }

    public double round() {
        return Math.round(2 * thickness) /2.0;
    }

}
