package tv.twitch.chat;

import java.util.*;

public enum ChatMessageTokenType
{
    TTV_CHAT_MSGTOKEN_TEXT("TTV_CHAT_MSGTOKEN_TEXT", 0, 0), 
    TTV_CHAT_MSGTOKEN_TEXTURE_IMAGE("TTV_CHAT_MSGTOKEN_TEXTURE_IMAGE", 1, 1), 
    TTV_CHAT_MSGTOKEN_URL_IMAGE("TTV_CHAT_MSGTOKEN_URL_IMAGE", 2, 2);
    
    private static Map s_Map;
    private int m_Value;
    private static final ChatMessageTokenType[] $VALUES;
    
    public static ChatMessageTokenType lookupValue(final int n) {
        return ChatMessageTokenType.s_Map.get(n);
    }
    
    private ChatMessageTokenType(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new ChatMessageTokenType[] { ChatMessageTokenType.TTV_CHAT_MSGTOKEN_TEXT, ChatMessageTokenType.TTV_CHAT_MSGTOKEN_TEXTURE_IMAGE, ChatMessageTokenType.TTV_CHAT_MSGTOKEN_URL_IMAGE };
        ChatMessageTokenType.s_Map = new HashMap();
        for (final ChatMessageTokenType chatMessageTokenType : EnumSet.allOf(ChatMessageTokenType.class)) {
            ChatMessageTokenType.s_Map.put(chatMessageTokenType.getValue(), chatMessageTokenType);
        }
    }
}
