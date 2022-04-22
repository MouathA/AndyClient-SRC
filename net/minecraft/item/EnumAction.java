package net.minecraft.item;

public enum EnumAction
{
    NONE("NONE", 0, "NONE", 0), 
    EAT("EAT", 1, "EAT", 1), 
    DRINK("DRINK", 2, "DRINK", 2), 
    BLOCK("BLOCK", 3, "BLOCK", 3), 
    BOW("BOW", 4, "BOW", 4);
    
    private static final EnumAction[] $VALUES;
    private static final String __OBFID;
    private static final EnumAction[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00000073";
        ENUM$VALUES = new EnumAction[] { EnumAction.NONE, EnumAction.EAT, EnumAction.DRINK, EnumAction.BLOCK, EnumAction.BOW };
        $VALUES = new EnumAction[] { EnumAction.NONE, EnumAction.EAT, EnumAction.DRINK, EnumAction.BLOCK, EnumAction.BOW };
    }
    
    private EnumAction(final String s, final int n, final String s2, final int n2) {
    }
}
