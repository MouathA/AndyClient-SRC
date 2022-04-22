package tv.twitch.broadcast;

import java.util.*;

public enum StatType
{
    TTV_ST_RTMPSTATE("TTV_ST_RTMPSTATE", 0, 0), 
    TTV_ST_RTMPDATASENT("TTV_ST_RTMPDATASENT", 1, 1);
    
    private static Map s_Map;
    private int m_Value;
    private static final StatType[] $VALUES;
    
    public static StatType lookupValue(final int n) {
        return StatType.s_Map.get(n);
    }
    
    private StatType(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new StatType[] { StatType.TTV_ST_RTMPSTATE, StatType.TTV_ST_RTMPDATASENT };
        StatType.s_Map = new HashMap();
        for (final StatType statType : EnumSet.allOf(StatType.class)) {
            StatType.s_Map.put(statType.getValue(), statType);
        }
    }
}
