package net.minecraft.client.gui.inventory;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class CreativeCrafting implements ICrafting
{
    private final Minecraft mc;
    private static final String __OBFID;
    
    public CreativeCrafting(final Minecraft mc) {
        this.mc = mc;
    }
    
    @Override
    public void updateCraftingInventory(final Container container, final List list) {
    }
    
    @Override
    public void sendSlotContents(final Container container, final int n, final ItemStack itemStack) {
        Minecraft.playerController.sendSlotPacket(itemStack, n);
    }
    
    @Override
    public void sendProgressBarUpdate(final Container container, final int n, final int n2) {
    }
    
    @Override
    public void func_175173_a(final Container container, final IInventory inventory) {
    }
    
    static {
        __OBFID = "CL_00000751";
    }
}
