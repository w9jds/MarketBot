package com.w9jds.marketbot.utils;

public class NumberUtils {

    private static char[] c = new char[]{'k', 'M', 'B', 'T', 'P', 'E', 'Z', 'Y'};

    public static String shortened(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) % 10 == 0;
        return (d < 1000 ? ((d > 99.9 || isRound || (!isRound && d > 9.99) ?
                (int) d * 10 / 10 : d + "") + "" + c[iteration]) : shortened(d, iteration + 1));
    }
}
