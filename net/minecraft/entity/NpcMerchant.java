package net.minecraft.entity;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.village.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class NpcMerchant implements IMerchant
{
    private InventoryMerchant theMerchantInventory;
    private EntityPlayer customer;
    private MerchantRecipeList recipeList;
    private IChatComponent field_175548_d;
    private static final String __OBFID;
    
    public NpcMerchant(final EntityPlayer customer, final IChatComponent field_175548_d) {
        this.customer = customer;
        this.field_175548_d = field_175548_d;
        this.theMerchantInventory = new InventoryMerchant(customer, this);
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.customer;
    }
    
    @Override
    public void setCustomer(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer entityPlayer) {
        return this.recipeList;
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList recipeList) {
        this.recipeList = recipeList;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
    }
    
    @Override
    public void verifySellingItem(final ItemStack itemStack) {
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return (this.field_175548_d != null) ? this.field_175548_d : new ChatComponentTranslation("entity.Villager.name", new Object[0]);
    }
    
    static {
        __OBFID = "CL_00001705";
    }
}
