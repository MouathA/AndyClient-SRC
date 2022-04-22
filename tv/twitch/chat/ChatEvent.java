package tv.twitch.chat;

import java.util.*;

public enum ChatEvent
{
    TTV_CHAT_JOINED_CHANNEL("TTV_CHAT_JOINED_CHANNEL", 0, 0), 
    TTV_CHAT_LEFT_CHANNEL("TTV_CHAT_LEFT_CHANNEL", 1, 1);
    
    private static Map s_Map;
    private int m_Value;
    private static final ChatEvent[] $VALUES;
    
    public static ChatEvent lookupValue(final int n) {
        return ChatEvent.s_Map.get(n);
    }
    
    private ChatEvent(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new ChatEvent[] { ChatEvent.TTV_CHAT_JOINED_CHANNEL, ChatEvent.TTV_CHAT_LEFT_CHANNEL };
        ChatEvent.s_Map = new HashMap();
        for (final ChatEvent chatEvent : EnumSet.allOf(ChatEvent.class)) {
            ChatEvent.s_Map.put(chatEvent.getValue(), chatEvent);
        }
    }
}
