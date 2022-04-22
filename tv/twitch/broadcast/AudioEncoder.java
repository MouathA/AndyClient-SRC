package tv.twitch.broadcast;

import java.util.*;

public enum AudioEncoder
{
    TTV_AUD_ENC_DEFAULT("TTV_AUD_ENC_DEFAULT", 0, -1), 
    TTV_AUD_ENC_LAMEMP3("TTV_AUD_ENC_LAMEMP3", 1, 0), 
    TTV_AUD_ENC_APPLEAAC("TTV_AUD_ENC_APPLEAAC", 2, 1);
    
    private static Map s_Map;
    private int m_Value;
    private static final AudioEncoder[] $VALUES;
    
    public static AudioEncoder lookupValue(final int n) {
        return AudioEncoder.s_Map.get(n);
    }
    
    private AudioEncoder(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new AudioEncoder[] { AudioEncoder.TTV_AUD_ENC_DEFAULT, AudioEncoder.TTV_AUD_ENC_LAMEMP3, AudioEncoder.TTV_AUD_ENC_APPLEAAC };
        AudioEncoder.s_Map = new HashMap();
        for (final AudioEncoder audioEncoder : EnumSet.allOf(AudioEncoder.class)) {
            AudioEncoder.s_Map.put(audioEncoder.getValue(), audioEncoder);
        }
    }
}
