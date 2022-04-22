package com.viaversion.viaversion.libs.kyori.adventure.audience;

public enum MessageType
{
    CHAT, 
    SYSTEM;
    
    private static final MessageType[] $VALUES;
    
    static {
        $VALUES = new MessageType[] { MessageType.CHAT, MessageType.SYSTEM };
    }
}
