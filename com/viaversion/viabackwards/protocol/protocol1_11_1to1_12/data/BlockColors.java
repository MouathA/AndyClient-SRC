package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data;

public class BlockColors
{
    public static String get(final int n) {
        return (n >= 0 && n < BlockColors.COLORS.length) ? BlockColors.COLORS[n] : "Unknown color";
    }
    
    static {
        BlockColors.COLORS = new String[16];
    }
}
