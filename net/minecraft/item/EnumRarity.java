package net.minecraft.item;

import net.minecraft.util.*;

public enum EnumRarity
{
    COMMON("COMMON", 0, "COMMON", 0, EnumChatFormatting.WHITE, "Common"), 
    UNCOMMON("UNCOMMON", 1, "UNCOMMON", 1, EnumChatFormatting.YELLOW, "Uncommon"), 
    RARE("RARE", 2, "RARE", 2, EnumChatFormatting.AQUA, "Rare"), 
    EPIC("EPIC", 3, "EPIC", 3, EnumChatFormatting.LIGHT_PURPLE, "Epic");
    
    public final EnumChatFormatting rarityColor;
    public final String rarityName;
    private static final EnumRarity[] $VALUES;
    private static final String __OBFID;
    private static final EnumRarity[] ENUM$VALUES;
    
    static {
        __OBFID = "CL_00000056";
        ENUM$VALUES = new EnumRarity[] { EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC };
        $VALUES = new EnumRarity[] { EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC };
    }
    
    private EnumRarity(final String s, final int n, final String s2, final int n2, final EnumChatFormatting rarityColor, final String rarityName) {
        this.rarityColor = rarityColor;
        this.rarityName = rarityName;
    }
}
