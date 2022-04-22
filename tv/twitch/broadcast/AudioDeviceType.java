package tv.twitch.broadcast;

import java.util.*;

public enum AudioDeviceType
{
    TTV_PLAYBACK_DEVICE("TTV_PLAYBACK_DEVICE", 0, 0), 
    TTV_RECORDER_DEVICE("TTV_RECORDER_DEVICE", 1, 1), 
    TTV_PASSTHROUGH_DEVICE("TTV_PASSTHROUGH_DEVICE", 2, 2), 
    TTV_DEVICE_NUM("TTV_DEVICE_NUM", 3, 3);
    
    private static Map s_Map;
    private int m_Value;
    private static final AudioDeviceType[] $VALUES;
    
    public static AudioDeviceType lookupValue(final int n) {
        return AudioDeviceType.s_Map.get(n);
    }
    
    private AudioDeviceType(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new AudioDeviceType[] { AudioDeviceType.TTV_PLAYBACK_DEVICE, AudioDeviceType.TTV_RECORDER_DEVICE, AudioDeviceType.TTV_PASSTHROUGH_DEVICE, AudioDeviceType.TTV_DEVICE_NUM };
        AudioDeviceType.s_Map = new HashMap();
        for (final AudioDeviceType audioDeviceType : EnumSet.allOf(AudioDeviceType.class)) {
            AudioDeviceType.s_Map.put(audioDeviceType.getValue(), audioDeviceType);
        }
    }
}
