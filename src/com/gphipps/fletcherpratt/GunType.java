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

        gunTypes.put("2.0", new GunType(2, 1, 6));

        gunTypes.put("3.0", new GunType(3.0, 2, 10));
        gunTypes.put("3.4", new GunType(3.4, 3, 20));
        gunTypes.put("3.5", new GunType(3.5, 3, 20));
        gunTypes.put("3.9", new GunType(3.9, 4, 11));

        gunTypes.put("4.0", new GunType(4, 4, 11));
        gunTypes.put("4.1", new GunType(4.1, 5, 11));
        gunTypes.put("4.5", new GunType(4.5, 5, 12));
        gunTypes.put("4.7", new GunType(4.7, 6, 13));

        gunTypes.put("5.0", new GunType(5, 7, 14));
        gunTypes.put("5.1", new GunType(5.1, 9, 14));
        gunTypes.put("5.2", new GunType(5.2, 9, 15));
        gunTypes.put("5.5", new GunType(5.5, 10, 15));
        gunTypes.put("5.9", new GunType(5.9, 12, 18));

        gunTypes.put("6.0", new GunType(6, 13, 18));
        gunTypes.put("6.1", new GunType(6.1, 14, 19));

        gunTypes.put("7.5", new GunType(7.5, 22, 21));

        gunTypes.put("8.0", new GunType(8, 29, 22));

        gunTypes.put("9.2", new GunType(9.2, 38, 23));
        gunTypes.put("9.4", new GunType(9.4, 41, 24));

        gunTypes.put("10.0", new GunType(10, 49, 25));

        gunTypes.put("11.0", new GunType(11, 65, 27));

        gunTypes.put("12.0", new GunType(12, 93, 29));

        gunTypes.put("12.6", new GunType(12.6, 118, 30));

        gunTypes.put("13.0", new GunType(13, 129, 31));

        gunTypes.put("13.5", new GunType(13.5, 130, 31));

        gunTypes.put("14.0", new GunType(14, 156, 32));

        gunTypes.put("15.0", new GunType(15, 214, 33));

        gunTypes.put("16.0", new GunType(16, 264, 34));
    }

    public GunType(double bore, int damage, int maxRange) {
        this.bore = bore;
        this.maxRange = maxRange;
        this.damage = damage;
    }

    public static GunType findByBore(double bore) {
        return gunTypes.get(Double.toString(bore));
//        return gunTypes.get(Double.toString(13.5));
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
