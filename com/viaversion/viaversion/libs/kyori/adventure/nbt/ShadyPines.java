package com.viaversion.viaversion.libs.kyori.adventure.nbt;

final class ShadyPines
{
    private ShadyPines() {
    }
    
    static int floor(final double dv) {
        final int n = (int)dv;
        return (dv < n) ? (n - 1) : n;
    }
    
    static int floor(final float fv) {
        final int n = (int)fv;
        return (fv < n) ? (n - 1) : n;
    }
}
