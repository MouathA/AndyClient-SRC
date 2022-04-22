package tv.twitch.chat;

import java.util.*;

public enum ChatUserSubscription
{
    TTV_CHAT_USERSUB_NONE("TTV_CHAT_USERSUB_NONE", 0, 0), 
    TTV_CHAT_USERSUB_SUBSCRIBER("TTV_CHAT_USERSUB_SUBSCRIBER", 1, 1), 
    TTV_CHAT_USERSUB_TURBO("TTV_CHAT_USERSUB_TURBO", 2, 2);
    
    private static Map s_Map;
    private int m_Value;
    private static final ChatUserSubscription[] $VALUES;
    
    public static ChatUserSubscription lookupValue(final int n) {
        return ChatUserSubscription.s_Map.get(n);
    }
    
    private ChatUserSubscription(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new ChatUserSubscription[] { ChatUserSubscription.TTV_CHAT_USERSUB_NONE, ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER, ChatUserSubscription.TTV_CHAT_USERSUB_TURBO };
        ChatUserSubscription.s_Map = new HashMap();
        for (final ChatUserSubscription chatUserSubscription : EnumSet.allOf(ChatUserSubscription.class)) {
            ChatUserSubscription.s_Map.put(chatUserSubscription.getValue(), chatUserSubscription);
        }
    }
}
