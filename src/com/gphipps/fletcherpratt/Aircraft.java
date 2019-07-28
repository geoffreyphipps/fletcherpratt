package com.gphipps.fletcherpratt;



public class Aircraft extends Reducing {

  public static final Aircraft nullAircraft = new Aircraft(0);

  public Aircraft(int count) {
    super(count, "Aircraft");
  }

  public double getPoints() {

    return 25 * getTotalCount();
  }

  protected String getStatusStringInternal(int i) {
    if (this == nullAircraft) {
      return "";
    } else {
      return String.valueOf(i) + " x " + getDescription();
    }
  }
}
