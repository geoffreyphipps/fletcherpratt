package com.gphipps.fletcherpratt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeSet;

public class Ship {

  private String name;
  private String klass;
  private int launched;
  private int rebuilt;
  private String nationality;
  private String type;
  private String torpedoClass;
  private String primaryTurretLayout;
  private String notes;

  private GunBattery primary = GunBattery.nullGun;
  private GunBattery secondary = GunBattery.nullGun;
  private TorpedoTubes torpedoTubes = TorpedoTubes.nullTorpedoTubes;
  private Speed speed = Speed.nullSpeed;
  private Armour belt = new Armour(0, "belt");
  private Armour deck = new Armour(0, "deck");
  private Armour turret = new Armour(0, "turret");
  private int standardDisplacement = 0;
  private Aircraft aircraft = Aircraft.nullAircraft;
  private Mines mines = Mines.nullMines;

  private TreeSet<Reducing> featuresToReduce = new TreeSet<Reducing>();

  public Ship(String name, String klass, int launched, int rebuilt) {
    super();
    this.setName(name);
    this.setKlass( klass );
    this.setLaunched(launched);
    this.setRebuilt(rebuilt);
  }

  public int getPoints() {
    return (int) Math.floor(speed.getPoints() *
            ( getPrimary().getPoints() + getSecondary().getPoints() + getTorpedoTubes().getPoints() +
              getBelt().getPoints() + getTurret().getPoints() + getDeck().getPoints() +
              getAircraft().getPoints() + getMines().getPoints() )) + getStandardDisplacement();
  }

  public void createShipLog(OutputChannel channel) {
    int totalPoints = getPoints();
    primary.setTotalPoints(totalPoints);
    secondary.setTotalPoints(totalPoints);
    torpedoTubes.setTotalPoints(totalPoints);
    speed.setTotalPoints(totalPoints);

   
    featuresToReduce.add(primary);
    featuresToReduce.add(secondary);
    featuresToReduce.add(torpedoTubes);
    featuresToReduce.add(speed);

    channel.header(this);
    channel.title(getName());
    

    int max = 17;

//    for (Reducing r : featuresToReduce) {
//      System.out.println(" XX " + r);
//    }
    for (Reducing r : featuresToReduce) {
      r.loseOne();
    }
    //channel.record(getPoints(), "", getStatusString());
    featuresToReduce.clear();
    featuresToReduce.add(primary);
    featuresToReduce.add(secondary);
    featuresToReduce.add(torpedoTubes);
    featuresToReduce.add(speed);
    do {
      max = featuresToReduce.first().getCurrentBreakPoint();
      Reducing r = featuresToReduce.first();
      
      String lostString = "";
      boolean first = true;
      while (r.getCurrentBreakPoint() == max) {
        lostString += (first ? " ": ", ") + r.getDescription();
        featuresToReduce.remove(r);
        r.loseOne();
        featuresToReduce.add(r);
        r = featuresToReduce.first();
        first = false;
      }
      String status = getStatusString();

      String s = String.format("%,d",totalPoints-max);
      channel.record( s, lostString, status);
      
    } while (featuresToReduce.first().getCurrentBreakPoint() > 0);

    String s = String.format("%,d",totalPoints);
    channel.record( s, "SUNK", "");

    channel.footer();
  }

  public String getStatusString() {
    String s = "";
    s += primary.getStatusString(true);
    s += secondary.getStatusString();
    s += torpedoTubes.getStatusString();
    s += speed.getStatusString();
    return s;
  }

  public String getInitialStatus() {
    String s = "";
    s += primary.getInitialStatus(true);
    s += secondary.getInitialStatus();
    s += torpedoTubes.getInitialStatus();
    s += speed.getInitialStatus();
    return s;
  }

  public static final void main(String args[]) throws FileNotFoundException, IOException {
    Ship laArgentina = new Ship("La Argentina","Example", 1912, 1923);
    laArgentina.setPrimary(new GunBattery(9, 6));
    laArgentina.setSecondary(new GunBattery(4, 4));
    laArgentina.setTorpedoTubes(new TorpedoTubes(6));
    laArgentina.setBelt( new Armour(3, "belt"));
    laArgentina.setDeck( new Armour(2, "deck"));
    laArgentina.setTurret( new Armour(2, "turret"));
    laArgentina.setAircraft( new Aircraft(2));
    laArgentina.setSpeed(new Speed(31));
    laArgentina.setStandardDisplacement(6000);
    laArgentina.createShipLog(new CSVOutput("test"));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GunBattery getPrimary() {
    return primary;
  }

  public void setPrimary(GunBattery primary) {
    this.primary = primary;
  }

  public GunBattery getSecondary() {
    return secondary;
  }

  public void setSecondary(GunBattery secondary) {
    this.secondary = secondary;
  }

  public TorpedoTubes getTorpedoTubes() {
    return torpedoTubes;
  }

  public void setTorpedoTubes(TorpedoTubes torpedoTubes) {
    this.torpedoTubes = torpedoTubes;
  }

  public Speed getSpeed() {
    return speed;
  }

  public void setSpeed(Speed speed) {
    this.speed = speed;
  }

  public Armour getBelt() {
    return belt;
  }

  public void setBelt(Armour belt) {
    this.belt = belt;
  }

  public Armour getDeck() {
    return deck;
  }

  public void setDeck(Armour deck) {
    this.deck = deck;
  }

  public Armour getTurret() {
    return turret;
  }

  public void setTurret(Armour turret) {
    this.turret = turret;
  }

  public int getStandardDisplacement() {
    return standardDisplacement;
  }

  public void setStandardDisplacement(int standardDisplacement) {
    this.standardDisplacement = standardDisplacement;
  }

  public Aircraft getAircraft() {
    return aircraft;
  }

  public void setAircraft(Aircraft aircraft) {
    this.aircraft = aircraft;
  }

  public Mines getMines() {
    return mines;
  }

  public void setMines(Mines mines) {
    this.mines = mines;
  }

  public String getKlass() {
    return klass;
  }

  public void setKlass(String klass) {
    this.klass = klass;
  }

  public int getLaunched() {
    return launched;
  }

  public void setLaunched(int launched) {
    this.launched = launched;
  }

  public int getRebuilt() {
    return rebuilt;
  }

  public void setRebuilt(int rebuilt) {
    this.rebuilt = rebuilt;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTorpedoClass() {
    return torpedoClass;
  }

  public void setTorpedoClass(String torpedoClass) {
    this.torpedoClass = torpedoClass;
  }

  public String getPrimaryTurretLayout() {
    return primaryTurretLayout;
  }

  public void setPrimaryTurretLayout(String primaryTurretLayout) {
    this.primaryTurretLayout = primaryTurretLayout;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}