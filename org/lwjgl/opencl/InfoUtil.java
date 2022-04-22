package org.lwjgl.opencl;

interface InfoUtil
{
    int getInfoInt(final CLObject p0, final int p1);
    
    long getInfoSize(final CLObject p0, final int p1);
    
    long[] getInfoSizeArray(final CLObject p0, final int p1);
    
    long getInfoLong(final CLObject p0, final int p1);
    
    String getInfoString(final CLObject p0, final int p1);
}
