package com.darkmagician6.eventapi;

public final class EventAPI
{
    public static final String VERSION;
    
    static {
        VERSION = String.format("%s-%s", "0.7", "beta");
        EventAPI.AUTHORS = new String[] { "DarkMagician6" };
    }
}
