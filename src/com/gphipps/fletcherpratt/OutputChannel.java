package com.gphipps.fletcherpratt;

public interface OutputChannel {

  void header( Ship s);
  void closeMajorTable(String klassName);
  void closePage();
  void title( String s );
  void record (String field1, String field2, String field3 );
  void record (long field1, String field2, String field3 );

  /**
   * The place where players record their movement and firing orders
   */
  void createOrdersLog ();
}
