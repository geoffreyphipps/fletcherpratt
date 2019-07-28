package com.gphipps.fletcherpratt;

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
      String s = String.format("%3.1f\"", thickness );
      return s;
    }
    
}
