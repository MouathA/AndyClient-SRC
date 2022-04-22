package net.minecraft.client.player.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class LocalBlockIntercommunication implements IInteractionObject
{
    private String field_175126_a;
    private IChatComponent field_175125_b;
    private static final String __OBFID;
    
    public LocalBlockIntercommunication(final String field_175126_a, final IChatComponent field_175125_b) {
        this.field_175126_a = field_175126_a;
        this.field_175125_b = field_175125_b;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String getName() {
        return this.field_175125_b.getUnformattedText();
    }
    
    @Override
    public boolean hasCustomName() {
        return true;
    }
    
    @Override
    public String getGuiID() {
        return this.field_175126_a;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.field_175125_b;
    }
    
    static {
        __OBFID = "CL_00002571";
    }
}
