package net.minecraft.village;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class MerchantRecipe
{
    private ItemStack itemToBuy;
    private ItemStack secondItemToBuy;
    private ItemStack itemToSell;
    private int toolUses;
    private int maxTradeUses;
    private boolean field_180323_f;
    private static final String __OBFID;
    
    public MerchantRecipe(final NBTTagCompound nbtTagCompound) {
        this.readFromTags(nbtTagCompound);
    }
    
    public MerchantRecipe(final ItemStack itemStack, final ItemStack itemStack2, final ItemStack itemStack3) {
        this(itemStack, itemStack2, itemStack3, 0, 7);
    }
    
    public MerchantRecipe(final ItemStack itemToBuy, final ItemStack secondItemToBuy, final ItemStack itemToSell, final int toolUses, final int maxTradeUses) {
        this.itemToBuy = itemToBuy;
        this.secondItemToBuy = secondItemToBuy;
        this.itemToSell = itemToSell;
        this.toolUses = toolUses;
        this.maxTradeUses = maxTradeUses;
        this.field_180323_f = true;
    }
    
    public MerchantRecipe(final ItemStack itemStack, final ItemStack itemStack2) {
        this(itemStack, null, itemStack2);
    }
    
    public MerchantRecipe(final ItemStack itemStack, final Item item) {
        this(itemStack, new ItemStack(item));
    }
    
    public ItemStack getItemToBuy() {
        return this.itemToBuy;
    }
    
    public ItemStack getSecondItemToBuy() {
        return this.secondItemToBuy;
    }
    
    public boolean hasSecondItemToBuy() {
        return this.secondItemToBuy != null;
    }
    
    public ItemStack getItemToSell() {
        return this.itemToSell;
    }
    
    public int func_180321_e() {
        return this.toolUses;
    }
    
    public int func_180320_f() {
        return this.maxTradeUses;
    }
    
    public void incrementToolUses() {
        ++this.toolUses;
    }
    
    public void func_82783_a(final int n) {
        this.maxTradeUses += n;
    }
    
    public boolean isRecipeDisabled() {
        return this.toolUses >= this.maxTradeUses;
    }
    
    public void func_82785_h() {
        this.toolUses = this.maxTradeUses;
    }
    
    public boolean func_180322_j() {
        return this.field_180323_f;
    }
    
    public void readFromTags(final NBTTagCompound nbtTagCompound) {
        this.itemToBuy = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("buy"));
        this.itemToSell = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("sell"));
        if (nbtTagCompound.hasKey("buyB", 10)) {
            this.secondItemToBuy = ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag("buyB"));
        }
        if (nbtTagCompound.hasKey("uses", 99)) {
            this.toolUses = nbtTagCompound.getInteger("uses");
        }
        if (nbtTagCompound.hasKey("maxUses", 99)) {
            this.maxTradeUses = nbtTagCompound.getInteger("maxUses");
        }
        else {
            this.maxTradeUses = 7;
        }
        if (nbtTagCompound.hasKey("rewardExp", 1)) {
            this.field_180323_f = nbtTagCompound.getBoolean("rewardExp");
        }
        else {
            this.field_180323_f = true;
        }
    }
    
    public NBTTagCompound writeToTags() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setTag("buy", this.itemToBuy.writeToNBT(new NBTTagCompound()));
        nbtTagCompound.setTag("sell", this.itemToSell.writeToNBT(new NBTTagCompound()));
        if (this.secondItemToBuy != null) {
            nbtTagCompound.setTag("buyB", this.secondItemToBuy.writeToNBT(new NBTTagCompound()));
        }
        nbtTagCompound.setInteger("uses", this.toolUses);
        nbtTagCompound.setInteger("maxUses", this.maxTradeUses);
        nbtTagCompound.setBoolean("rewardExp", this.field_180323_f);
        return nbtTagCompound;
    }
    
    static {
        __OBFID = "CL_00000126";
    }
}
