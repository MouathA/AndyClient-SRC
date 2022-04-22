package net.minecraft.village;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.nbt.*;

public class MerchantRecipeList extends ArrayList
{
    private static final String __OBFID;
    
    public MerchantRecipeList() {
    }
    
    public MerchantRecipeList(final NBTTagCompound nbtTagCompound) {
        this.readRecipiesFromTags(nbtTagCompound);
    }
    
    public MerchantRecipe canRecipeBeUsed(final ItemStack itemStack, final ItemStack itemStack2, final int n) {
        if (n > 0 && n < this.size()) {
            final MerchantRecipe merchantRecipe = this.get(n);
            return (ItemStack.areItemsEqual(itemStack, merchantRecipe.getItemToBuy()) && ((itemStack2 == null && !merchantRecipe.hasSecondItemToBuy()) || (merchantRecipe.hasSecondItemToBuy() && ItemStack.areItemsEqual(itemStack2, merchantRecipe.getSecondItemToBuy()))) && itemStack.stackSize >= merchantRecipe.getItemToBuy().stackSize && (!merchantRecipe.hasSecondItemToBuy() || itemStack2.stackSize >= merchantRecipe.getSecondItemToBuy().stackSize)) ? merchantRecipe : null;
        }
        while (0 < this.size()) {
            final MerchantRecipe merchantRecipe2 = this.get(0);
            if (ItemStack.areItemsEqual(itemStack, merchantRecipe2.getItemToBuy()) && itemStack.stackSize >= merchantRecipe2.getItemToBuy().stackSize && ((!merchantRecipe2.hasSecondItemToBuy() && itemStack2 == null) || (merchantRecipe2.hasSecondItemToBuy() && ItemStack.areItemsEqual(itemStack2, merchantRecipe2.getSecondItemToBuy()) && itemStack2.stackSize >= merchantRecipe2.getSecondItemToBuy().stackSize))) {
                return merchantRecipe2;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public void func_151391_a(final PacketBuffer packetBuffer) {
        packetBuffer.writeByte((byte)(this.size() & 0xFF));
        while (0 < this.size()) {
            final MerchantRecipe merchantRecipe = this.get(0);
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToBuy());
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToSell());
            final ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            packetBuffer.writeBoolean(secondItemToBuy != null);
            if (secondItemToBuy != null) {
                packetBuffer.writeItemStackToBuffer(secondItemToBuy);
            }
            packetBuffer.writeBoolean(merchantRecipe.isRecipeDisabled());
            packetBuffer.writeInt(merchantRecipe.func_180321_e());
            packetBuffer.writeInt(merchantRecipe.func_180320_f());
            int n = 0;
            ++n;
        }
    }
    
    public static MerchantRecipeList func_151390_b(final PacketBuffer packetBuffer) throws IOException {
        final MerchantRecipeList list = new MerchantRecipeList();
        while (0 < (packetBuffer.readByte() & 0xFF)) {
            final ItemStack itemStackFromBuffer = packetBuffer.readItemStackFromBuffer();
            final ItemStack itemStackFromBuffer2 = packetBuffer.readItemStackFromBuffer();
            ItemStack itemStackFromBuffer3 = null;
            if (packetBuffer.readBoolean()) {
                itemStackFromBuffer3 = packetBuffer.readItemStackFromBuffer();
            }
            final boolean boolean1 = packetBuffer.readBoolean();
            final MerchantRecipe merchantRecipe = new MerchantRecipe(itemStackFromBuffer, itemStackFromBuffer3, itemStackFromBuffer2, packetBuffer.readInt(), packetBuffer.readInt());
            if (boolean1) {
                merchantRecipe.func_82785_h();
            }
            list.add(merchantRecipe);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public void readRecipiesFromTags(final NBTTagCompound nbtTagCompound) {
        final NBTTagList tagList = nbtTagCompound.getTagList("Recipes", 10);
        while (0 < tagList.tagCount()) {
            this.add(new MerchantRecipe(tagList.getCompoundTagAt(0)));
            int n = 0;
            ++n;
        }
    }
    
    public NBTTagCompound getRecipiesAsTags() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        while (0 < this.size()) {
            list.appendTag(this.get(0).writeToTags());
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Recipes", list);
        return nbtTagCompound;
    }
    
    static {
        __OBFID = "CL_00000127";
    }
}
