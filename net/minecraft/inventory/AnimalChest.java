package net.minecraft.inventory;

import net.minecraft.util.*;

public class AnimalChest extends InventoryBasic
{
    private static final String __OBFID;
    
    public AnimalChest(final String s, final int n) {
        super(s, false, n);
    }
    
    public AnimalChest(final IChatComponent chatComponent, final int n) {
        super(chatComponent, n);
    }
    
    static {
        __OBFID = "CL_00001731";
    }
}
