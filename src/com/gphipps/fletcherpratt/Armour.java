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
      return 10*thickness*thickness;
    }

    public String toString() {
        return new DecimalFormat("##.#").format(thickness) + '"';
    }
    
}
