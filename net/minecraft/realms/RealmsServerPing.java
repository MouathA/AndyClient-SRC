package net.minecraft.realms;

public class RealmsServerPing
{
    public String nrOfPlayers;
    public long lastPingSnapshot;
    private static final String __OBFID;
    
    public RealmsServerPing() {
        this.nrOfPlayers = "0";
        this.lastPingSnapshot = 0L;
    }
    
    static {
        __OBFID = "CL_00002328";
    }
}
