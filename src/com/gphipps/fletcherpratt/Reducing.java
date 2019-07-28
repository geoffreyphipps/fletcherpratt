package com.gphipps.fletcherpratt;

public abstract class Reducing extends Feature implements Comparable {

  private int totalCount;
  private int currentCount;
  private int currentBreakPoint;
  private int totalPoints;

  public Reducing(int count, String description) {
    super(description);
    this.totalCount = count;
    this.currentCount = count;
  }

  public String getStatusString() {
    return getStatusString(false);
  }

  public String getStatusString(boolean isFirst) {
    String s = getStatusStringInternal(getCurrentCount() + 1);
    if (isFirst) {
      return s;
    } else {
      if (s.length() > 0) {
        return ", " + getStatusStringInternal(getCurrentCount() + 1);
      } else {
        return "";
      }
    }
  }

  public String getInitialStatus() {
    return getInitialStatus(false);
  }

  public String getInitialStatus(boolean isFirst) {
    String s = getStatusStringInternal(getTotalCount());
    if (isFirst) {
      return s;
    } else {
      if (s.length() > 0) {
        return ", " + getStatusStringInternal(getTotalCount());
      } else {
        return "";
      }
    }
  }

  protected abstract String getStatusStringInternal(int i);

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int count) {
    this.totalCount = count;
  }

  public int getCurrentCount() {
    return currentCount;
  }

  public void loseOne() {
    currentCount--;
    if (totalCount == 0) {
      currentBreakPoint = 0;
    } else {
      currentBreakPoint = getTotalPoints() * currentCount / totalCount;
    }
  }

  /**
   * @return the currentBreakPoint
   */
  public int getCurrentBreakPoint() {
    return currentBreakPoint;
  }

  /**
   * From comparable,
   */
  @Override
  public int compareTo(Object other) {
    Reducing r = (Reducing) other;
    if (getCurrentBreakPoint() == r.getCurrentBreakPoint()) {
      return getDescription().compareTo(r.getDescription());
    } else if (getCurrentBreakPoint() > r.getCurrentBreakPoint()) {
      return -1;
    } else {
      return 1;
    }
  }

  public boolean equals(Object other) {
    Reducing r = (Reducing) other;
    return r.getCurrentBreakPoint() == getCurrentBreakPoint() &&
            r.getDescription().equals(getDescription());
  }

  /**
   * @return the totalPoints
   */
  public int getTotalPoints() {
    return totalPoints;
  }

  /**
   * @param totalPoints the totalPoints to set
   */
  public void setTotalPoints(int totalPoints) {
    this.totalPoints = totalPoints;
  }

  public String toString() {
    return getDescription() + " " + getCurrentBreakPoint() + " " + getCurrentCount();
  }
}
