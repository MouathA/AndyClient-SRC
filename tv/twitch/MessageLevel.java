package tv.twitch;

import java.util.*;

public enum MessageLevel
{
    TTV_ML_DEBUG("TTV_ML_DEBUG", 0, 0), 
    TTV_ML_INFO("TTV_ML_INFO", 1, 1), 
    TTV_ML_WARNING("TTV_ML_WARNING", 2, 2), 
    TTV_ML_ERROR("TTV_ML_ERROR", 3, 3), 
    TTV_ML_CHAT("TTV_ML_CHAT", 4, 4), 
    TTV_ML_NONE("TTV_ML_NONE", 5, 5);
    
    private static Map s_Map;
    private int m_Value;
    private static final MessageLevel[] $VALUES;
    
    public static MessageLevel lookupValue(final int n) {
        return MessageLevel.s_Map.get(n);
    }
    
    private MessageLevel(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new MessageLevel[] { MessageLevel.TTV_ML_DEBUG, MessageLevel.TTV_ML_INFO, MessageLevel.TTV_ML_WARNING, MessageLevel.TTV_ML_ERROR, MessageLevel.TTV_ML_CHAT, MessageLevel.TTV_ML_NONE };
        MessageLevel.s_Map = new HashMap();
        for (final MessageLevel messageLevel : EnumSet.allOf(MessageLevel.class)) {
            MessageLevel.s_Map.put(messageLevel.getValue(), messageLevel);
        }
    }
}
