package tv.twitch.broadcast;

import java.util.*;

public enum RTMPState
{
    Invalid("Invalid", 0, -1), 
    Idle("Idle", 1, 0), 
    Initialize("Initialize", 2, 1), 
    Handshake("Handshake", 3, 2), 
    Connect("Connect", 4, 3), 
    CreateStream("CreateStream", 5, 4), 
    Publish("Publish", 6, 5), 
    SendVideo("SendVideo", 7, 6), 
    Shutdown("Shutdown", 8, 7), 
    Error("Error", 9, 8);
    
    private static Map s_Map;
    private int m_Value;
    private static final RTMPState[] $VALUES;
    
    public static RTMPState lookupValue(final int n) {
        return RTMPState.s_Map.get(n);
    }
    
    private RTMPState(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new RTMPState[] { RTMPState.Invalid, RTMPState.Idle, RTMPState.Initialize, RTMPState.Handshake, RTMPState.Connect, RTMPState.CreateStream, RTMPState.Publish, RTMPState.SendVideo, RTMPState.Shutdown, RTMPState.Error };
        RTMPState.s_Map = new HashMap();
        for (final RTMPState rtmpState : EnumSet.allOf(RTMPState.class)) {
            RTMPState.s_Map.put(rtmpState.getValue(), rtmpState);
        }
    }
}
