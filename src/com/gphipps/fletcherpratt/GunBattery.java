package com.gphipps.fletcherpratt;

import java.text.DecimalFormat;

public final class GunBattery extends Reducing {
  private double bore;
  private GunType gunType;

  public static final GunBattery nullGun = new GunBattery(0, 0);

  public GunBattery(int count, double bore) {
    // Don't have zeros here
    super(count, new DecimalFormat("##.#").format(bore) + '"' );
    this.bore = bore;
    this.gunType = GunType.findByBore(bore);
  }

  public double getPoints() {
    return Math.floor(getTotalCount() * bore * bore);
  }

  public double getFirePowerWeightedPoints() {
    return Math.floor(getTotalCount() *  this.gunType.getDamage());
  }

  public double getBore() {
    return bore;
  }
  public void setBore(double bore) {
    this.bore = bore;
    this.gunType = GunType.findByBore(bore);
  }

  public GunType getGunType() {
    return gunType;
  }
  public void setGunType(GunType gunType) {
    this.gunType = gunType;
  }

  protected String getStatusStringInternal( int i) {
    if (this == nullGun || this.getTotalCount() == 0 ) {
      return "";
    } else {
      return i + " x " + getDescription();
    }
  }
}
