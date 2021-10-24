/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gphipps.fletcherpratt;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TorpedoTubes extends Reducing {

  public static final int MAX_TORPEDO_HITS = 5;
  public static final TorpedoTubes nullTorpedoTubes = new TorpedoTubes(0);
  private static final Map<String, List<Double>> torpedoDamage = new HashMap<>() {
    {
      put("A", Arrays.asList(1.0,  1.0, 1.0, 1.0, 1.0));
      put("B", Arrays.asList(0.70, 1.0, 1.0, 1.0, 1.0));
      put("C", Arrays.asList(0.33, 0.5, 1.0, 1.0, 1.0));
      put("D", Arrays.asList(0.2, 0.25, 0.33, 1.0, 1.0));
      put("E", Arrays.asList(0.125, 0.166, 0.2, 0.25, 0.33));
    }
  };

  public TorpedoTubes(int count) {
    super(count, "TT");
  }

  public static double getPercentageDamage(String torpedoClass, int hitNumber) {
    List<Double> hitList = torpedoDamage.get(torpedoClass);
    if( hitList == null ) {
      System.out.println( "Torpedo Class not Found _" + torpedoClass +"_");
    }
    return hitList.get(hitNumber);
  }

  public double getPoints() {
    return 10 * getTotalCount();
  }

  protected String getStatusStringInternal(int i) {
    if (this == nullTorpedoTubes || this.getTotalCount() == 0) {
      return "";
    } else {
      return i + " x " + getDescription();
    }
  }
}
