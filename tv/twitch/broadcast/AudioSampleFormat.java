package tv.twitch.broadcast;

import java.util.*;

public enum AudioSampleFormat
{
    TTV_ASF_PCM_S16("TTV_ASF_PCM_S16", 0, 0);
    
    private static Map s_Map;
    private int m_Value;
    private static final AudioSampleFormat[] $VALUES;
    
    public static AudioSampleFormat lookupValue(final int n) {
        return AudioSampleFormat.s_Map.get(n);
    }
    
    private AudioSampleFormat(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new AudioSampleFormat[] { AudioSampleFormat.TTV_ASF_PCM_S16 };
        AudioSampleFormat.s_Map = new HashMap();
        for (final AudioSampleFormat audioSampleFormat : EnumSet.allOf(AudioSampleFormat.class)) {
            AudioSampleFormat.s_Map.put(audioSampleFormat.getValue(), audioSampleFormat);
        }
    }
}
