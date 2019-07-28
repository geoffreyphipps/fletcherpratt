/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gphipps.fletcherpratt;


public class Mines extends Reducing {

  public static final Mines nullMines = new Mines(0);

  public Mines(int count) {
    super(count, "Mines");
  }

  public double getPoints() {
    return getTotalCount()/2;
  }

  protected String getStatusStringInternal(int i) {
    if (this == nullMines) {
      return "";
    } else {
      return String.valueOf(i) + " x " + getDescription();
    }
  }
}
