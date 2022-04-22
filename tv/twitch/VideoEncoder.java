package tv.twitch;

import java.util.*;

public enum VideoEncoder
{
    TTV_VID_ENC_DISABLE("TTV_VID_ENC_DISABLE", 0, -2), 
    TTV_VID_ENC_DEFAULT("TTV_VID_ENC_DEFAULT", 1, -1), 
    TTV_VID_ENC_INTEL("TTV_VID_ENC_INTEL", 2, 0), 
    TTV_VID_ENC_APPLE("TTV_VID_ENC_APPLE", 3, 2), 
    TTV_VID_ENC_PLUGIN("TTV_VID_ENC_PLUGIN", 4, 100);
    
    private static Map s_Map;
    private int m_Value;
    private static final VideoEncoder[] $VALUES;
    
    public static VideoEncoder lookupValue(final int n) {
        return VideoEncoder.s_Map.get(n);
    }
    
    private VideoEncoder(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new VideoEncoder[] { VideoEncoder.TTV_VID_ENC_DISABLE, VideoEncoder.TTV_VID_ENC_DEFAULT, VideoEncoder.TTV_VID_ENC_INTEL, VideoEncoder.TTV_VID_ENC_APPLE, VideoEncoder.TTV_VID_ENC_PLUGIN };
        VideoEncoder.s_Map = new HashMap();
        for (final VideoEncoder videoEncoder : EnumSet.allOf(VideoEncoder.class)) {
            VideoEncoder.s_Map.put(videoEncoder.getValue(), videoEncoder);
        }
    }
}
