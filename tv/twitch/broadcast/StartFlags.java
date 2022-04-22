package tv.twitch.broadcast;

import java.util.*;

public enum StartFlags
{
    None("None", 0, 0), 
    TTV_Start_BandwidthTest("TTV_Start_BandwidthTest", 1, 1);
    
    private static Map s_Map;
    private int m_Value;
    private static final StartFlags[] $VALUES;
    
    public static StartFlags lookupValue(final int n) {
        return StartFlags.s_Map.get(n);
    }
    
    private StartFlags(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new StartFlags[] { StartFlags.None, StartFlags.TTV_Start_BandwidthTest };
        StartFlags.s_Map = new HashMap();
        for (final StartFlags startFlags : EnumSet.allOf(StartFlags.class)) {
            StartFlags.s_Map.put(startFlags.getValue(), startFlags);
        }
    }
}
