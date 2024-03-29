package com.gphipps.fletcherpratt;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVOutput implements OutputChannel {
  private String fieldSeparator="; ";
  private PrintWriter printWriter;

  public CSVOutput( String filename ) throws IOException {
    printWriter = new PrintWriter( new FileWriter(filename +".csv" ) );
  }

  public void header(Ship ship) {
    record( ship.getClassicPoints(),"",ship.getInitialStatus());
  }

  public void closeMajorTable(String klassName) {
  }
  public void closePage() {
    printWriter.close();
  }

  public void title( String s ) {
    printWriter.println( s );
  }

  public void record( String field1, String field2, String field3 ) {
    printWriter.print( field1 );
    printWriter.print( getFieldSeparator() );
    printWriter.print( field2 );
    printWriter.print( getFieldSeparator() );
    printWriter.println( field3 );
  }
  public void record( long field1, String field2, String field3 ) {
    record( String.valueOf( field1), field2, field3 );
  }

  @Override
  public void createOrdersLog() {
    // TODO This is only used for testing, so do what here?
  }

  public String getFieldSeparator() {
    return fieldSeparator;
  }

  public void setFieldSeparator(String fieldSeparator) {
    this.fieldSeparator = fieldSeparator;
  }
}
