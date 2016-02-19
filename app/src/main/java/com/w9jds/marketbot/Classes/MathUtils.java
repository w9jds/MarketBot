package com.w9jds.marketbot.classes;

public final class MathUtils {

    private MathUtils() { }

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }
}