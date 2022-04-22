package DTool.util;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;

public class InvUtils
{
    private static Minecraft mc;
    
    static {
        InvUtils.mc = Minecraft.getMinecraft();
    }
    
    public static int findEmptySlot() {
        while (0 < 8) {
            if (Minecraft.thePlayer.inventory.mainInventory[0] == null) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return Minecraft.thePlayer.inventory.currentItem + ((Minecraft.thePlayer.inventory.getCurrentItem() == null) ? 0 : ((Minecraft.thePlayer.inventory.currentItem < 8) ? 4 : -1));
    }
    
    public static int findEmptySlot(final int n) {
        if (Minecraft.thePlayer.inventory.mainInventory[n] == null) {
            return n;
        }
        return findEmptySlot();
    }
    
    public static void swapShift(final int n) {
        Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, n, 0, 1, Minecraft.thePlayer);
    }
    
    public static void swap(final int n, final int n2) {
        Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, n, n2, 2, Minecraft.thePlayer);
    }
    
    public static boolean isFull() {
        return !Arrays.asList(Minecraft.thePlayer.inventory.mainInventory).contains(null);
    }
    
    public static int armorSlotToNormalSlot(final int n) {
        return 8 - n;
    }
    
    public static void block() {
        Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getCurrentItem());
    }
    
    public static ItemStack getCurrentItem() {
        return (Minecraft.thePlayer.getCurrentEquippedItem() == null) ? new ItemStack(Blocks.air) : Minecraft.thePlayer.getCurrentEquippedItem();
    }
    
    public static ItemStack getItemBySlot(final int n) {
        return (Minecraft.thePlayer.inventory.mainInventory[n] == null) ? new ItemStack(Blocks.air) : Minecraft.thePlayer.inventory.mainInventory[n];
    }
    
    public static List getHotbarContent() {
        final ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(Minecraft.thePlayer.inventory.mainInventory).subList(0, 9));
        return list;
    }
    
    public static List getAllInventoryContent() {
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.addAll((Collection<?>)Arrays.asList(Minecraft.thePlayer.inventory.mainInventory).subList(0, 35));
        while (0 < 4) {
            list.add(Minecraft.thePlayer.inventory.armorItemInSlot(0));
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public static List getInventoryContent() {
        final ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(Minecraft.thePlayer.inventory.mainInventory).subList(9, 35));
        return list;
    }
    
    public static int getEmptySlotInHotbar() {
        while (0 < 9) {
            if (Minecraft.thePlayer.inventory.mainInventory[0] == null) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public static boolean hasWeapon() {
        return Minecraft.thePlayer.inventory.getCurrentItem() == null && (Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemAxe || Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword);
    }
    
    public static boolean isHeldingSword() {
        return Minecraft.thePlayer.getHeldItem() != null && Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword;
    }
    
    public static boolean isBestPickaxe(final ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemPickaxe)) {
            return false;
        }
        final float toolEffect = getToolEffect(itemStack);
        while (9 < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(9).getHasStack()) {
                final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(9).getStack();
                if (getToolEffect(stack) > toolEffect && stack.getItem() instanceof ItemPickaxe) {
                    return false;
                }
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static boolean isBestShovel(final ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemSpade)) {
            return false;
        }
        final float toolEffect = getToolEffect(itemStack);
        while (9 < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(9).getHasStack()) {
                final ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(9).getStack();
                if (getToolEffect(stack) > toolEffect && stack.getItem() instanceof ItemSpade) {
                    return false;
                }
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static float getToolEffect(final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        final String unlocalizedName = item.getUnlocalizedName();
        final ItemTool itemTool = (ItemTool)item;
        float n;
        if (item instanceof ItemPickaxe) {
            n = itemTool.getStrVsBlock(itemStack, Blocks.stone);
            if (unlocalizedName.toLowerCase().contains("gold")) {
                n -= 5.0f;
            }
        }
        else if (item instanceof ItemSpade) {
            n = itemTool.getStrVsBlock(itemStack, Blocks.dirt);
            if (unlocalizedName.toLowerCase().contains("gold")) {
                n -= 5.0f;
            }
        }
        else {
            if (!(item instanceof ItemAxe)) {
                return 1.0f;
            }
            n = itemTool.getStrVsBlock(itemStack, Blocks.log);
            if (unlocalizedName.toLowerCase().contains("gold")) {
                n -= 5.0f;
            }
        }
        return (float)((float)(n + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) * 0.0075) + EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 100.0);
    }
    
    public static boolean isItemEmpty(final Item item) {
        return item == null || Item.getIdFromItem(item) == 0;
    }
}
