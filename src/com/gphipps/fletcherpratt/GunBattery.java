package com.gphipps.fletcherpratt;

public final class GunBattery extends Reducing {

  private double bore;
  public static final GunBattery nullGun = new GunBattery(0, 0);

  public GunBattery(int count, double bore) {
    super(count, String.valueOf(bore) + "\"");
    this.bore = bore;
  }

  public double getPoints() {
    return Math.floor(getTotalCount() * bore * bore);
  }

  public double getBore() {
    return bore;
  }

  public void setBore(double count) {
    this.bore = count;
  }

  protected String getStatusStringInternal( int i) {
    if (this == nullGun) {
      return "";
    } else {
      return String.valueOf(i) + " x " + getDescription();
    }
  }
}
