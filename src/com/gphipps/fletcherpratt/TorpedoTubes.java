/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gphipps.fletcherpratt;


public class TorpedoTubes extends Reducing {

  public static final TorpedoTubes nullTorpedoTubes = new TorpedoTubes(0);

  public TorpedoTubes(int count) {
    super(count, "TT");
  }

  public double getPoints() {
    return 10 * getTotalCount();
  }

  protected String getStatusStringInternal(int i) {
    if (this == nullTorpedoTubes) {
      return "";
    } else {
      return String.valueOf(i) + " x " + getDescription();
    }
  }
}
