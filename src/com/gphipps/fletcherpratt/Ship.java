package com.gphipps.fletcherpratt;

import java.util.TreeSet;

public class Ship {

  private String name;
  private String klass;
  private int numberOfMiniatures;
  private String launched;
  private String rebuilt;
  private String nationality;
  private String type;
  private String torpedoClass;
  private String primaryTurretLayout;
  private String torpedoTubeLayout = "";
  private String notes="";

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

  public Ship(String name, String klass, String launched, String rebuilt) {
    super();
    this.setName(name);
    this.setKlass( klass );
    this.setLaunched(launched);
    this.setRebuilt(rebuilt);
  }

  private static final double DIVISOR = 40.0;

  public int getFirepowerWeightedPoints() {
    double classic = speed.getPoints() *
            ( getPrimary().getFirePowerWeightedPoints()  +
                    getBelt().getNewDefensivePoints() + 0.5 * getDeck().getNewDefensivePoints() + getStandardDisplacement());
    return (int) Math.floor(classic/DIVISOR);
  }

  public int getNewDefensivePoints() {
    double points =// Math.pow(speed.getTotalCount(), 0.3) *
            ( getBelt().getNewDefensivePoints() +
                    0.5 * getDeck().getNewDefensivePoints() +
                    0.2 * getTurret().getNewDefensivePoints() )
            + Math.pow(getStandardDisplacement(), 0.65);
    return (int) Math.floor(40*points/DIVISOR);
  }

  public int getClassicPoints() {
    double classic = speed.getPoints() *
            ( getPrimary().getPoints() + getSecondary().getPoints() + getTorpedoTubes().getPoints() +
              getBelt().getPoints() + getDeck().getPoints() + getTurret().getPoints() +
              getAircraft().getPoints() + getMines().getPoints() ) +
            getStandardDisplacement();
    return (int) Math.floor(classic/DIVISOR);
  }

  public int getPrimaryDamagePoints() {
    return getPrimary().getTotalDamage();
  }

  public int getTorpedoPoints() {
    // TODO Account for submerged vs centerline (worth more if centerline), and size
    return getTorpedoTubes().getTotalCount() * 10;
  }

  public int getBalancePoints() {
    return getPrimaryDamagePoints() + getTorpedoPoints() + getNewDefensivePoints();
  }

  /**
   * Name, Type, Class, Number of Miniatures, Nationality, Displacement, Classic Points, New Defensive Points, Total Primary Damage
   */
  public String getSummaryLine() {
    return name + "," + klass + "," + nationality + "," + type +  ","  + numberOfMiniatures +  ","  +
            primary.getTotalCount() + "," + primary.getBore() + "," +
            secondary.getTotalCount() + "," + secondary.getBore() + "," +
            torpedoTubes.getTotalCount() + "," +
            belt + "," + deck + "," + turret + "," +
            speed.getTotalCount() + "," + standardDisplacement + "," +
            getClassicPoints() + "," +
            getNewDefensivePoints() + "," + getPrimaryDamagePoints() + "," + getTorpedoPoints() + "," + getBalancePoints();
  }

  /**
   * Prints an HTML ship log
   */
  public void createShipLog(OutputChannel channel) {
    int totalPoints = getNewDefensivePoints();
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
      channel.record( s, status, lostString);
    } while (featuresToReduce.first().getCurrentBreakPoint() > 0);

    String s = String.format("%,d",totalPoints);
    channel.record( s, "SUNK", "");

    channel.closeMajorTable(getKlass());

    channel.createOrdersLog();

    channel.closePage();
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

  public String getLaunched() {
    return launched;
  }

  public void setLaunched(String launched) {
    this.launched = launched;
  }

  public String getRebuilt() {
    return rebuilt;
  }

  public void setRebuilt(String rebuilt) {
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

  public String getTorpedoTubeLayout() {
    return torpedoTubeLayout;
  }

  public void setTorpedoTubeLayout(String torpedoTubeLayout) {
    this.torpedoTubeLayout = torpedoTubeLayout;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public boolean isLeadShipInClass() {
    return name.equals(klass);
  }

  public int getNumberOfMiniatures() {
    return numberOfMiniatures;
  }

  public void setNumberOfMiniatures(int numberOfMiniatures) {
    this.numberOfMiniatures = numberOfMiniatures;
  }
}
