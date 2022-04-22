package tv.twitch.chat;

import java.util.*;

public enum ChatUserMode
{
    TTV_CHAT_USERMODE_VIEWER("TTV_CHAT_USERMODE_VIEWER", 0, 0), 
    TTV_CHAT_USERMODE_MODERATOR("TTV_CHAT_USERMODE_MODERATOR", 1, 1), 
    TTV_CHAT_USERMODE_BROADCASTER("TTV_CHAT_USERMODE_BROADCASTER", 2, 2), 
    TTV_CHAT_USERMODE_ADMINSTRATOR("TTV_CHAT_USERMODE_ADMINSTRATOR", 3, 4), 
    TTV_CHAT_USERMODE_STAFF("TTV_CHAT_USERMODE_STAFF", 4, 8), 
    TTV_CHAT_USERMODE_BANNED("TTV_CHAT_USERMODE_BANNED", 5, 1073741824);
    
    private static Map s_Map;
    private int m_Value;
    private static final ChatUserMode[] $VALUES;
    
    public static ChatUserMode lookupValue(final int n) {
        return ChatUserMode.s_Map.get(n);
    }
    
    private ChatUserMode(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new ChatUserMode[] { ChatUserMode.TTV_CHAT_USERMODE_VIEWER, ChatUserMode.TTV_CHAT_USERMODE_MODERATOR, ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER, ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR, ChatUserMode.TTV_CHAT_USERMODE_STAFF, ChatUserMode.TTV_CHAT_USERMODE_BANNED };
        ChatUserMode.s_Map = new HashMap();
        for (final ChatUserMode chatUserMode : EnumSet.allOf(ChatUserMode.class)) {
            ChatUserMode.s_Map.put(chatUserMode.getValue(), chatUserMode);
        }
    }
}
