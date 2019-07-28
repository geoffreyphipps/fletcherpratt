/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gphipps.fletcherpratt;

/**
 *
 * 
 */
public class Speed extends Reducing {

  public static final Speed nullSpeed = new Speed( 0);

  public Speed(int count) {
    super(count, "1 kt");
  }

  public double getPoints() {
    return 10 + ((double)getTotalCount())/2.0;
  }

  @Override
  protected String getStatusStringInternal(int i) {
    return (i) + " kt";
  }
}
