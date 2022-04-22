package net.minecraft.util;

public enum EnumWorldBlockLayer
{
    SOLID("SOLID", 0, "SOLID", 0, "Solid"), 
    CUTOUT_MIPPED("CUTOUT_MIPPED", 1, "CUTOUT_MIPPED", 1, "Mipped Cutout"), 
    CUTOUT("CUTOUT", 2, "CUTOUT", 2, "Cutout"), 
    TRANSLUCENT("TRANSLUCENT", 3, "TRANSLUCENT", 3, "Translucent");
    
    private final String field_180338_e;
    private static final EnumWorldBlockLayer[] $VALUES;
    private static final String __OBFID;
    private static final EnumWorldBlockLayer[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00002152";
        ENUM$VALUES = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.SOLID, EnumWorldBlockLayer.CUTOUT_MIPPED, EnumWorldBlockLayer.CUTOUT, EnumWorldBlockLayer.TRANSLUCENT };
        $VALUES = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.SOLID, EnumWorldBlockLayer.CUTOUT_MIPPED, EnumWorldBlockLayer.CUTOUT, EnumWorldBlockLayer.TRANSLUCENT };
    }
    
    private EnumWorldBlockLayer(final String s, final int n, final String s2, final int n2, final String field_180338_e) {
        this.field_180338_e = field_180338_e;
    }
    
    @Override
    public String toString() {
        return this.field_180338_e;
    }
}
