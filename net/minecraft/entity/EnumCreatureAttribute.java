package net.minecraft.entity;

public enum EnumCreatureAttribute
{
    UNDEFINED("UNDEFINED", 0, "UNDEFINED", 0), 
    UNDEAD("UNDEAD", 1, "UNDEAD", 1), 
    ARTHROPOD("ARTHROPOD", 2, "ARTHROPOD", 2);
    
    private static final EnumCreatureAttribute[] $VALUES;
    private static final String __OBFID;
    private static final EnumCreatureAttribute[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00001553";
        ENUM$VALUES = new EnumCreatureAttribute[] { EnumCreatureAttribute.UNDEFINED, EnumCreatureAttribute.UNDEAD, EnumCreatureAttribute.ARTHROPOD };
        $VALUES = new EnumCreatureAttribute[] { EnumCreatureAttribute.UNDEFINED, EnumCreatureAttribute.UNDEAD, EnumCreatureAttribute.ARTHROPOD };
    }
    
    private EnumCreatureAttribute(final String s, final int n, final String s2, final int n2) {
    }
}
