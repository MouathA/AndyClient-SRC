package net.minecraft.network;

public enum EnumPacketDirection
{
    SERVERBOUND("SERVERBOUND", 0, "SERVERBOUND", 0), 
    CLIENTBOUND("CLIENTBOUND", 1, "CLIENTBOUND", 1);
    
    private static final EnumPacketDirection[] $VALUES;
    private static final String __OBFID;
    private static final EnumPacketDirection[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00002307";
        ENUM$VALUES = new EnumPacketDirection[] { EnumPacketDirection.SERVERBOUND, EnumPacketDirection.CLIENTBOUND };
        $VALUES = new EnumPacketDirection[] { EnumPacketDirection.SERVERBOUND, EnumPacketDirection.CLIENTBOUND };
    }
    
    private EnumPacketDirection(final String s, final int n, final String s2, final int n2) {
    }
}
