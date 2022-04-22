package tv.twitch.chat;

import java.util.*;

public enum ChatTokenizationOption
{
    TTV_CHAT_TOKENIZATION_OPTION_NONE("TTV_CHAT_TOKENIZATION_OPTION_NONE", 0, 0), 
    TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS("TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS", 1, 1), 
    TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES("TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES", 2, 2);
    
    private static Map s_Map;
    private int m_Value;
    private static final ChatTokenizationOption[] $VALUES;
    
    public static ChatTokenizationOption lookupValue(final int n) {
        return ChatTokenizationOption.s_Map.get(n);
    }
    
    public static int getNativeValue(final HashSet set) {
        if (set == null) {
            return ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE.getValue();
        }
        int value = ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE.getValue();
        for (final ChatTokenizationOption chatTokenizationOption : set) {
            if (chatTokenizationOption != null) {
                value |= chatTokenizationOption.getValue();
            }
        }
        return value;
    }
    
    private ChatTokenizationOption(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new ChatTokenizationOption[] { ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE, ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS, ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES };
        ChatTokenizationOption.s_Map = new HashMap();
        for (final ChatTokenizationOption chatTokenizationOption : EnumSet.allOf(ChatTokenizationOption.class)) {
            ChatTokenizationOption.s_Map.put(chatTokenizationOption.getValue(), chatTokenizationOption);
        }
    }
}
