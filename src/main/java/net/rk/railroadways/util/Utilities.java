package net.rk.railroadways.util;

import net.minecraft.util.Mth;

public class Utilities{
    /**
     * Converts degrees (e.g. 45 or 180) to radians (e.g. 0.1 or 1.2)
     * @param degrees The degrees (from -360 to 360 is the preferred use-case)
     * @return The radians result from the conversion
     */
    public static float degreesToRadians(float degrees){
       return (degrees * Mth.PI / 180);
    }

    /**
     * Converts radians to degrees (same as degreesToRadians function but opposite)
     * @param radians The radians to convert to degrees
     * @return The degrees result from the conversion
     */
    public static float radiansToDegrees(float radians){
        return (radians * 180 / Mth.PI);
    }

    /**
     * A packed light retriever that takes an index of how bright (out of 3) the lighted object should be
     * Used mostly for convenience for times when a full bright render is needed or gradual fade-out lighting effect
     * @param index The light level out of 3 desired
     * @return The packed light level
     */
    public static int getLightLevel(int index){
        return switch (index) {
            case 0 -> 3932212; // low bright
            case 1 -> 7864425; // medium bright
            case 2 -> 15728850; // full bright
            default -> 0;
        };
    }

    public static final int whiteColor = 16777215;
    public static final String imgFileEnding = ".png";

    /**
     * Converts a value from its old range to a new range (floats)
     * @param value The input float to change its value relative to another range
     * @param oldMin The old range minimum
     * @param oldMax The old range maximum
     * @param newMin The new range minimum
     * @param newMax The new range maximum
     * @return The converted float value
     */
    public static float convertFloatRangeToOther(float value,float oldMin,float oldMax,float newMin,float newMax){
        if(oldMin == oldMax){
            return -1.0f;
        }
        if(newMin == newMax){
            return -1.0f;
        }
        return ((value - oldMin) / (oldMax - oldMin)) * (newMax - newMin) + newMin;
    }
}
