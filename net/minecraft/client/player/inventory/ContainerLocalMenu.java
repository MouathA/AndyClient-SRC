package net.minecraft.client.player.inventory;

import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer
{
    private String field_174896_a;
    private Map field_174895_b;
    private static final String __OBFID;
    
    public ContainerLocalMenu(final String field_174896_a, final IChatComponent chatComponent, final int n) {
        super(chatComponent, n);
        this.field_174895_b = Maps.newHashMap();
        this.field_174896_a = field_174896_a;
    }
    
    @Override
    public int getField(final int n) {
        return this.field_174895_b.containsKey(n) ? this.field_174895_b.get(n) : 0;
    }
    
    @Override
    public void setField(final int n, final int n2) {
        this.field_174895_b.put(n, n2);
    }
    
    @Override
    public int getFieldCount() {
        return this.field_174895_b.size();
    }
    
    @Override
    public boolean isLocked() {
        return false;
    }
    
    @Override
    public void setLockCode(final LockCode lockCode) {
    }
    
    @Override
    public LockCode getLockCode() {
        return LockCode.EMPTY_CODE;
    }
    
    @Override
    public String getGuiID() {
        return this.field_174896_a;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        throw new UnsupportedOperationException();
    }
    
    static {
        __OBFID = "CL_00002570";
    }
}
