package com.gphipps.fletcherpratt;

import java.text.DecimalFormat;

public final class GunBattery extends Reducing {
  private String bore;
  private double boreAsDouble;
  private GunType gunType;

  public static final GunBattery nullGun = new GunBattery(0, "0");

  public GunBattery(int count, String bore) {
    // Don't have zeros here
    super(count, bore + '"' );
    this.bore = bore;
    this.boreAsDouble = Double.parseDouble(bore);
    this.gunType = GunType.findByBore(bore);
  }

  public double getPoints() {
    return Math.floor(getTotalCount() * boreAsDouble * boreAsDouble);
  }

  public double getFirePowerWeightedPoints() {
    return Math.floor(getTotalCount() *  this.gunType.getDamage());
  }

  public String getBore() {
    return bore;
  }
  public void setBore(String bore) {
    this.bore = bore;
    this.boreAsDouble = Double.parseDouble(bore);
    this.gunType = GunType.findByBore(bore);
  }

  public GunType getGunType() {
    return gunType;
  }
  public void setGunType(GunType gunType) {
    this.gunType = gunType;
  }

  public int getTotalDamage() {
    return getTotalCount() * gunType.getDamage();
  }

  public boolean isNull() {
    return gunType == GunType.NULL_GUN;
  }

  protected String getStatusStringInternal( int i) {
    if (this == nullGun || this.getTotalCount() == 0 ) {
      return "";
    } else {
      return i + " x " + getDescription();
    }
  }
}
