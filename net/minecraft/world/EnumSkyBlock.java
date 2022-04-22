package net.minecraft.world;

public enum EnumSkyBlock
{
    SKY("SKY", 0, "SKY", 0, 15), 
    BLOCK("BLOCK", 1, "BLOCK", 1, 0);
    
    public final int defaultLightValue;
    private static final EnumSkyBlock[] $VALUES;
    private static final String __OBFID;
    private static final EnumSkyBlock[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00000151";
        ENUM$VALUES = new EnumSkyBlock[] { EnumSkyBlock.SKY, EnumSkyBlock.BLOCK };
        $VALUES = new EnumSkyBlock[] { EnumSkyBlock.SKY, EnumSkyBlock.BLOCK };
    }
    
    private EnumSkyBlock(final String s, final int n, final String s2, final int n2, final int defaultLightValue) {
        this.defaultLightValue = defaultLightValue;
    }
}
