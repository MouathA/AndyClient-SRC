package net.minecraft.client.gui;

import net.minecraft.util.*;

public class ChatLine
{
    private final int updateCounterCreated;
    private final IChatComponent lineString;
    private final int chatLineID;
    private static final String __OBFID;
    
    public ChatLine(final int updateCounterCreated, final IChatComponent lineString, final int chatLineID) {
        this.lineString = lineString;
        this.updateCounterCreated = updateCounterCreated;
        this.chatLineID = chatLineID;
    }
    
    public IChatComponent getChatComponent() {
        return this.lineString;
    }
    
    public int getUpdatedCounter() {
        return this.updateCounterCreated;
    }
    
    public int getChatLineID() {
        return this.chatLineID;
    }
    
    static {
        __OBFID = "CL_00000627";
    }
}
