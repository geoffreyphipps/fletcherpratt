package com.gphipps.fletcherpratt;

import java.util.HashMap;

public class GunType {
    // Currently scaled for 1/2" knots

    private double bore;
    private int maxRange;
    private int damage;

    static HashMap<String, GunType> gunTypes = new HashMap<>();

    static {
        gunTypes.put("0.0", new GunType(0, 0, 0));

        gunTypes.put("1.5", new GunType(1.5, 1, 6));
        gunTypes.put("1.6", new GunType(1.6, 1, 6));

        gunTypes.put("2.0", new GunType(2, 2, 6));

        gunTypes.put("3.0", new GunType(3.0, 5, 10));
        gunTypes.put("3.4", new GunType(3.4, 6, 20));
        gunTypes.put("3.5", new GunType(3.5, 6, 20));
        gunTypes.put("3.9", new GunType(3.9, 8, 11));

        gunTypes.put("4.0", new GunType(4, 9, 11));
        gunTypes.put("4.1", new GunType(4.1, 10, 11));
        gunTypes.put("4.5", new GunType(4.5, 11, 12));
        gunTypes.put("4.7", new GunType(4.7, 12, 13));

        gunTypes.put("5.0", new GunType(5, 13, 14));
        gunTypes.put("5.1", new GunType(5.1, 18, 14));
        gunTypes.put("5.2", new GunType(5.2, 19, 15));
        gunTypes.put("5.5", new GunType(5.5, 20, 15));
        gunTypes.put("5.9", new GunType(5.9, 24, 18));

        gunTypes.put("6.0", new GunType(6, 25, 18));
        gunTypes.put("6.1", new GunType(6.1, 28, 19));

        gunTypes.put("7.5", new GunType(7.5, 44, 21));

        gunTypes.put("8.0", new GunType(8, 58, 22));

        gunTypes.put("9.2", new GunType(9.2, 76, 23));
        gunTypes.put("9.4", new GunType(9.4, 83, 24));

        gunTypes.put("10.0", new GunType(10, 98, 25));

        gunTypes.put("11.0", new GunType(11, 130, 27));

        gunTypes.put("12.0", new GunType(12, 185, 29));

        gunTypes.put("12.6", new GunType(12.6, 235, 30));

        gunTypes.put("13.0", new GunType(13, 258, 31));

        gunTypes.put("13.5", new GunType(13.5, 260, 31));

        gunTypes.put("14.0", new GunType(14, 313, 32));

        gunTypes.put("15.0", new GunType(15, 428, 33));

        gunTypes.put("16.0", new GunType(16, 528, 34));
    }

    public GunType(double bore, int damage, int maxRange) {
        this.bore = bore;
        this.maxRange = maxRange;
        this.damage = damage;
    }

    public static GunType findByBore(double bore) {
        return gunTypes.get(Double.toString(bore));
    }

    public double getBore() {
        return bore;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getDamage() {
        return damage;
    }
}
