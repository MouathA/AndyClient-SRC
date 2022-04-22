package tv.twitch.broadcast;

import java.util.*;

public enum PixelFormat
{
    TTV_PF_BGRA("TTV_PF_BGRA", 0, 66051), 
    TTV_PF_ABGR("TTV_PF_ABGR", 1, 16909056), 
    TTV_PF_RGBA("TTV_PF_RGBA", 2, 33619971), 
    TTV_PF_ARGB("TTV_PF_ARGB", 3, 50462976);
    
    private static Map s_Map;
    private int m_Value;
    private static final PixelFormat[] $VALUES;
    
    public static PixelFormat lookupValue(final int n) {
        return PixelFormat.s_Map.get(n);
    }
    
    private PixelFormat(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new PixelFormat[] { PixelFormat.TTV_PF_BGRA, PixelFormat.TTV_PF_ABGR, PixelFormat.TTV_PF_RGBA, PixelFormat.TTV_PF_ARGB };
        PixelFormat.s_Map = new HashMap();
        for (final PixelFormat pixelFormat : EnumSet.allOf(PixelFormat.class)) {
            PixelFormat.s_Map.put(pixelFormat.getValue(), pixelFormat);
        }
    }
}
