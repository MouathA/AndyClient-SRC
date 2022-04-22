package tv.twitch.broadcast;

import java.util.*;

public enum EncodingCpuUsage
{
    TTV_ECU_LOW("TTV_ECU_LOW", 0, 0), 
    TTV_ECU_MEDIUM("TTV_ECU_MEDIUM", 1, 1), 
    TTV_ECU_HIGH("TTV_ECU_HIGH", 2, 2);
    
    private static Map s_Map;
    private int m_Value;
    private static final EncodingCpuUsage[] $VALUES;
    
    public static EncodingCpuUsage lookupValue(final int n) {
        return EncodingCpuUsage.s_Map.get(n);
    }
    
    private EncodingCpuUsage(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new EncodingCpuUsage[] { EncodingCpuUsage.TTV_ECU_LOW, EncodingCpuUsage.TTV_ECU_MEDIUM, EncodingCpuUsage.TTV_ECU_HIGH };
        EncodingCpuUsage.s_Map = new HashMap();
        for (final EncodingCpuUsage encodingCpuUsage : EnumSet.allOf(EncodingCpuUsage.class)) {
            EncodingCpuUsage.s_Map.put(encodingCpuUsage.getValue(), encodingCpuUsage);
        }
    }
}
