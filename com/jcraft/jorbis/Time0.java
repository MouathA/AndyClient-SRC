package com.jcraft.jorbis;

import com.jcraft.jogg.*;

class Time0 extends FuncTime
{
    @Override
    void pack(final Object o, final Buffer buffer) {
    }
    
    @Override
    Object unpack(final Info info, final Buffer buffer) {
        return "";
    }
    
    @Override
    Object look(final DspState dspState, final InfoMode infoMode, final Object o) {
        return "";
    }
    
    @Override
    void free_info(final Object o) {
    }
    
    @Override
    void free_look(final Object o) {
    }
    
    @Override
    int inverse(final Block block, final Object o, final float[] array, final float[] array2) {
        return 0;
    }
}
