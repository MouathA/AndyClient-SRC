package net.minecraft.client.gui.inventory;

import net.minecraft.creativetab.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import Mood.UIButtons.*;
import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import net.minecraft.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    private static final ResourceLocation creativeInventoryTabs;
    private static InventoryBasic field_147060_v;
    private static int selectedTabIndex;
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasClicking;
    private GuiTextField searchField;
    private List field_147063_B;
    private Slot field_147064_C;
    private boolean field_147057_D;
    private CreativeCrafting field_147059_E;
    private int maxPages;
    
    static {
        creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
        GuiContainerCreative.field_147060_v = new InventoryBasic("tmp", true, 45);
        GuiContainerCreative.selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
    }
    
    public GuiContainerCreative(final EntityPlayer entityPlayer) {
        super(new ContainerCreative(entityPlayer));
        this.maxPages = 0;
        entityPlayer.openContainer = this.inventorySlots;
        this.allowUserInput = true;
        this.ySize = 136;
        this.xSize = 195;
    }
    
    @Override
    public void updateScreen() {
        final Minecraft mc = GuiContainerCreative.mc;
        if (!Minecraft.playerController.isInCreativeMode()) {
            GuiContainerCreative.mc.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
        }
        this.updateActivePotionEffects();
    }
    
    @Override
    protected void handleMouseClick(final Slot slot, final int n, final int n2, int n3) {
        this.field_147057_D = true;
        final boolean b = n3 == 1;
        n3 = ((n == -999 && n3 == 0) ? 4 : n3);
        if (slot == null && GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && n3 != 5) {
            final InventoryPlayer inventory = Minecraft.thePlayer.inventory;
            if (inventory.getItemStack() != null) {
                if (n2 == 0) {
                    Minecraft.thePlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), true);
                    final Minecraft mc = GuiContainerCreative.mc;
                    Minecraft.playerController.sendPacketDropItem(inventory.getItemStack());
                    inventory.setItemStack(null);
                }
                if (n2 == 1) {
                    final ItemStack splitStack = inventory.getItemStack().splitStack(1);
                    Minecraft.thePlayer.dropPlayerItemWithRandomChoice(splitStack, true);
                    final Minecraft mc2 = GuiContainerCreative.mc;
                    Minecraft.playerController.sendPacketDropItem(splitStack);
                    if (inventory.getItemStack().stackSize == 0) {
                        inventory.setItemStack(null);
                    }
                }
            }
        }
        else if (slot == this.field_147064_C && b) {
            while (0 < Minecraft.thePlayer.inventoryContainer.getInventory().size()) {
                final Minecraft mc3 = GuiContainerCreative.mc;
                Minecraft.playerController.sendSlotPacket(null, 0);
                int n4 = 0;
                ++n4;
            }
        }
        else if (GuiContainerCreative.selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
            if (slot == this.field_147064_C) {
                Minecraft.thePlayer.inventory.setItemStack(null);
            }
            else if (n3 == 4 && slot != null && slot.getHasStack()) {
                final ItemStack decrStackSize = slot.decrStackSize((n2 == 0) ? 1 : slot.getStack().getMaxStackSize());
                Minecraft.thePlayer.dropPlayerItemWithRandomChoice(decrStackSize, true);
                final Minecraft mc4 = GuiContainerCreative.mc;
                Minecraft.playerController.sendPacketDropItem(decrStackSize);
            }
            else {
                if (n3 == 4 && Minecraft.thePlayer.inventory.getItemStack() != null) {
                    Minecraft.thePlayer.dropPlayerItemWithRandomChoice(Minecraft.thePlayer.inventory.getItemStack(), true);
                    final Minecraft mc5 = GuiContainerCreative.mc;
                    Minecraft.playerController.sendPacketDropItem(Minecraft.thePlayer.inventory.getItemStack());
                    Minecraft.thePlayer.inventory.setItemStack(null);
                    return;
                }
                Minecraft.thePlayer.inventoryContainer.slotClick((slot == null) ? n : CreativeSlot.access$0((CreativeSlot)slot).slotNumber, n2, n3, Minecraft.thePlayer);
                Minecraft.thePlayer.inventoryContainer.detectAndSendChanges();
            }
        }
        else if (n3 != 5 && slot.inventory == GuiContainerCreative.field_147060_v) {
            final InventoryPlayer inventory2 = Minecraft.thePlayer.inventory;
            final ItemStack itemStack = inventory2.getItemStack();
            final ItemStack stack = slot.getStack();
            if (n3 == 2) {
                if (stack != null && n2 >= 0 && n2 < 9) {
                    final ItemStack copy = stack.copy();
                    copy.stackSize = copy.getMaxStackSize();
                    Minecraft.thePlayer.inventory.setInventorySlotContents(n2, copy);
                    Minecraft.thePlayer.inventoryContainer.detectAndSendChanges();
                }
                return;
            }
            if (n3 == 3) {
                if (inventory2.getItemStack() == null && slot.getHasStack()) {
                    final ItemStack copy2 = slot.getStack().copy();
                    copy2.stackSize = copy2.getMaxStackSize();
                    inventory2.setItemStack(copy2);
                }
                return;
            }
            if (n3 == 4) {
                if (stack != null) {
                    final ItemStack copy3 = stack.copy();
                    copy3.stackSize = ((n2 == 0) ? 1 : copy3.getMaxStackSize());
                    Minecraft.thePlayer.dropPlayerItemWithRandomChoice(copy3, true);
                    final Minecraft mc6 = GuiContainerCreative.mc;
                    Minecraft.playerController.sendPacketDropItem(copy3);
                }
                return;
            }
            if (itemStack != null && stack != null && itemStack.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(itemStack, stack)) {
                if (n2 == 0) {
                    if (b) {
                        itemStack.stackSize = itemStack.getMaxStackSize();
                    }
                    else if (itemStack.stackSize < itemStack.getMaxStackSize()) {
                        final ItemStack itemStack2 = itemStack;
                        ++itemStack2.stackSize;
                    }
                }
                else if (itemStack.stackSize <= 1) {
                    inventory2.setItemStack(null);
                }
                else {
                    final ItemStack itemStack3 = itemStack;
                    --itemStack3.stackSize;
                }
            }
            else if (stack != null && itemStack == null) {
                inventory2.setItemStack(ItemStack.copyItemStack(stack));
                final ItemStack itemStack4 = inventory2.getItemStack();
                if (b) {
                    itemStack4.stackSize = itemStack4.getMaxStackSize();
                }
            }
            else {
                inventory2.setItemStack(null);
            }
        }
        else {
            this.inventorySlots.slotClick((slot == null) ? n : slot.slotNumber, n2, n3, Minecraft.thePlayer);
            if (Container.getDragEvent(n2) == 2) {
                while (0 < 9) {
                    final Minecraft mc7 = GuiContainerCreative.mc;
                    Minecraft.playerController.sendSlotPacket(this.inventorySlots.getSlot(45).getStack(), 36);
                    int n4 = 0;
                    ++n4;
                }
            }
            else if (slot != null) {
                final ItemStack stack2 = this.inventorySlots.getSlot(slot.slotNumber).getStack();
                final Minecraft mc8 = GuiContainerCreative.mc;
                Minecraft.playerController.sendSlotPacket(stack2, slot.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
            }
        }
    }
    
    protected void updateActivePotionEffects() {
        final int guiLeft = this.guiLeft;
        super.drawActivePotionEffects();
        if (this.searchField != null && this.guiLeft != guiLeft) {
            this.searchField.xPosition = this.guiLeft + 82;
        }
    }
    
    @Override
    public void initGui() {
        final Minecraft mc = GuiContainerCreative.mc;
        if (Minecraft.playerController.isInCreativeMode()) {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents(true);
            (this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT)).setMaxStringLength(15);
            this.searchField.setEnableBackgroundDrawing(false);
            this.searchField.setVisible(false);
            this.searchField.setTextColor(16777215);
            final int selectedTabIndex = GuiContainerCreative.selectedTabIndex;
            GuiContainerCreative.selectedTabIndex = -1;
            this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[selectedTabIndex]);
            this.field_147059_E = new CreativeCrafting(GuiContainerCreative.mc);
            Minecraft.thePlayer.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
            final int length = CreativeTabs.creativeTabArray.length;
            if (length > 12) {
                this.buttonList.add(new UIButtons(101, this.guiLeft, this.guiTop - 50, 20, 20, "§l<"));
                this.buttonList.add(new UIButtons(102, this.guiLeft + this.xSize - 20, this.guiTop - 50, 20, 20, "§l>"));
                this.maxPages = (length - 12) / 10 + 1;
            }
        }
        else {
            GuiContainerCreative.mc.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
        }
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (Minecraft.thePlayer != null && Minecraft.thePlayer.inventory != null) {
            Minecraft.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
        }
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (!CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex].hasSearchBar()) {
            if (GameSettings.isKeyDown(GuiContainerCreative.mc.gameSettings.keyBindChat)) {
                this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
            }
            else {
                super.keyTyped(c, n);
            }
        }
        else {
            if (this.field_147057_D) {
                this.field_147057_D = false;
                this.searchField.setText("");
            }
            if (!this.checkHotbarKeys(n)) {
                if (this.searchField.textboxKeyTyped(c, n)) {
                    this.updateCreativeSearch();
                }
                else {
                    super.keyTyped(c, n);
                }
            }
        }
    }
    
    private void updateCreativeSearch() {
        final ContainerCreative containerCreative = (ContainerCreative)this.inventorySlots;
        containerCreative.itemList.clear();
        final CreativeTabs creativeTabs = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        if (creativeTabs.hasSearchBar() && creativeTabs != CreativeTabs.tabAllSearch) {
            creativeTabs.displayAllReleventItems(containerCreative.itemList);
            this.updateFilteredItems(containerCreative);
        }
        else {
            for (final Item item : Item.itemRegistry) {
                if (item != null && item.getCreativeTab() != null) {
                    item.getSubItems(item, null, containerCreative.itemList);
                }
            }
            this.updateFilteredItems(containerCreative);
        }
    }
    
    private void updateFilteredItems(final ContainerCreative containerCreative) {
        if (CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex] == CreativeTabs.tabAllSearch) {
            final Enchantment[] enchantmentsList = Enchantment.enchantmentsList;
            while (0 < Enchantment.enchantmentsList.length) {
                final Enchantment enchantment = enchantmentsList[0];
                if (enchantment != null && enchantment.type != null) {
                    Items.enchanted_book.getAll(enchantment, containerCreative.itemList);
                }
                int n = 0;
                ++n;
            }
        }
        final Iterator iterator = containerCreative.itemList.iterator();
        final String lowerCase = this.searchField.getText().toLowerCase();
        while (iterator.hasNext()) {
            final ItemStack itemStack = iterator.next();
            final Minecraft mc = GuiContainerCreative.mc;
            final Iterator iterator2 = itemStack.getTooltip(Minecraft.thePlayer, GuiContainerCreative.mc.gameSettings.advancedItemTooltips).iterator();
            while (iterator2.hasNext() && !EnumChatFormatting.getTextWithoutFormattingCodes(iterator2.next()).toLowerCase().contains(lowerCase)) {}
            if (!true) {
                iterator.remove();
            }
        }
        containerCreative.scrollTo(this.currentScroll = 0.0f);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final CreativeTabs creativeTabs = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        if (creativeTabs != null && creativeTabs.drawInForegroundOfTab()) {
            this.fontRendererObj.drawString(I18n.format(creativeTabs.getTranslatedTabLabel(), new Object[0]), 8.0, 6.0, 4210752);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0) {
            final int n4 = n - this.guiLeft;
            final int n5 = n2 - this.guiTop;
            CreativeTabs[] creativeTabArray;
            while (0 < (creativeTabArray = CreativeTabs.creativeTabArray).length) {
                if (this.func_147049_a(creativeTabArray[0], n4, n5)) {
                    return;
                }
                final byte b = 1;
            }
        }
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (n3 == 0) {
            final int n4 = n - this.guiLeft;
            final int n5 = n2 - this.guiTop;
            CreativeTabs[] creativeTabArray;
            while (0 < (creativeTabArray = CreativeTabs.creativeTabArray).length) {
                final CreativeTabs currentCreativeTab = creativeTabArray[0];
                if (currentCreativeTab != null && this.func_147049_a(currentCreativeTab, n4, n5)) {
                    this.setCurrentCreativeTab(currentCreativeTab);
                    return;
                }
                final byte b = 1;
            }
        }
        super.mouseReleased(n, n2, n3);
    }
    
    private boolean needsScrollBars() {
        return CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex] != null && (GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.inventorySlots).func_148328_e());
    }
    
    private void setCurrentCreativeTab(final CreativeTabs creativeTabs) {
        if (creativeTabs != null) {
            final int selectedTabIndex = GuiContainerCreative.selectedTabIndex;
            GuiContainerCreative.selectedTabIndex = creativeTabs.getTabIndex();
            final ContainerCreative containerCreative = (ContainerCreative)this.inventorySlots;
            this.dragSplittingSlots.clear();
            containerCreative.itemList.clear();
            creativeTabs.displayAllReleventItems(containerCreative.itemList);
            if (creativeTabs == CreativeTabs.tabInventory) {
                final Minecraft mc = GuiContainerCreative.mc;
                final Container inventoryContainer = Minecraft.thePlayer.inventoryContainer;
                if (this.field_147063_B == null) {
                    this.field_147063_B = containerCreative.inventorySlots;
                }
                containerCreative.inventorySlots = Lists.newArrayList();
                while (0 < inventoryContainer.inventorySlots.size()) {
                    final CreativeSlot creativeSlot = new CreativeSlot(inventoryContainer.inventorySlots.get(0), 0);
                    containerCreative.inventorySlots.add(creativeSlot);
                    if (0 >= 5 && 0 < 9) {
                        creativeSlot.xDisplayPosition = 9;
                        creativeSlot.yDisplayPosition = 6;
                    }
                    else if (0 >= 0 && 0 < 5) {
                        creativeSlot.yDisplayPosition = -2000;
                        creativeSlot.xDisplayPosition = -2000;
                    }
                    else if (0 < inventoryContainer.inventorySlots.size()) {
                        creativeSlot.xDisplayPosition = 9;
                        if (0 >= 36) {
                            creativeSlot.yDisplayPosition = 112;
                        }
                        else {
                            creativeSlot.yDisplayPosition = 54;
                        }
                    }
                    int n = 0;
                    ++n;
                }
                this.field_147064_C = new Slot(GuiContainerCreative.field_147060_v, 0, 173, 112);
                containerCreative.inventorySlots.add(this.field_147064_C);
            }
            else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
                containerCreative.inventorySlots = this.field_147063_B;
                this.field_147063_B = null;
            }
            if (this.searchField != null) {
                if (creativeTabs.hasSearchBar()) {
                    this.searchField.setVisible(true);
                    this.searchField.setCanLoseFocus(false);
                    this.searchField.setFocused(true);
                    this.searchField.setText("");
                    GuiTextField.width = creativeTabs.getSearchbarWidth();
                    this.searchField.xPosition = this.guiLeft + 171 - GuiTextField.width;
                    this.updateCreativeSearch();
                }
                else {
                    this.searchField.setVisible(false);
                    this.searchField.setCanLoseFocus(true);
                    this.searchField.setFocused(false);
                }
            }
            containerCreative.scrollTo(this.currentScroll = 0.0f);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        Mouse.getEventDWheel();
        if (-1 != 0 && this.needsScrollBars()) {
            final int n = ((ContainerCreative)this.inventorySlots).itemList.size() / 9 - 5 + 1;
            if (-1 > 0) {}
            if (-1 < 0) {}
            this.currentScroll -= (float)(-1 / (double)n);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final boolean buttonDown = Mouse.isButtonDown(0);
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        final int n4 = guiLeft + 175;
        final int n5 = guiTop + 18;
        final int n6 = n4 + 14;
        final int n7 = n5 + 112;
        if (!this.wasClicking && buttonDown && n >= n4 && n2 >= n5 && n < n6 && n2 < n7) {
            this.isScrolling = this.needsScrollBars();
        }
        if (!buttonDown) {
            this.isScrolling = false;
        }
        this.wasClicking = buttonDown;
        if (this.isScrolling) {
            this.currentScroll = (n2 - n5 - 7.5f) / (n7 - n5 - 15.0f);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
        super.drawScreen(n, n2, n3);
        final int min = Math.min(CreativeTabs.creativeTabArray.length, 12);
        if (false) {
            final int n8;
            n8 += 2;
        }
        CreativeTabs[] array;
        while (0 < (array = Arrays.copyOfRange(CreativeTabs.creativeTabArray, 0, min)).length) {
            final CreativeTabs creativeTabs = array[0];
            if (creativeTabs != null && this.renderCreativeInventoryHoveringText(creativeTabs, n, n2)) {
                break;
            }
            final byte b = 1;
        }
        if (!true && this.renderCreativeInventoryHoveringText(CreativeTabs.tabAllSearch, n, n2)) {
            this.renderCreativeInventoryHoveringText(CreativeTabs.tabInventory, n, n2);
        }
        if (this.field_147064_C != null && GuiContainerCreative.selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && this.isPointInRegion(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, n, n2)) {
            this.drawCreativeTabHoveringText(I18n.format("inventory.binSlot", new Object[0]), n, n2);
        }
        if (this.maxPages != 0) {
            final String format = String.format("%d / %d", 1, this.maxPages + 1);
            final int stringWidth = this.fontRendererObj.getStringWidth(format);
            this.zLevel = 300.0f;
            this.itemRender.zLevel = 300.0f;
            this.fontRendererObj.drawString(format, this.guiLeft + this.xSize / 2 - stringWidth / 2, this.guiTop - 44, -1);
            this.zLevel = 0.0f;
            this.itemRender.zLevel = 0.0f;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected void renderToolTip(final ItemStack p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: getstatic       net/minecraft/creativetab/CreativeTabs.tabAllSearch:Lnet/minecraft/creativetab/CreativeTabs;
        //     6: invokevirtual   net/minecraft/creativetab/CreativeTabs.getTabIndex:()I
        //     9: if_icmpne       318
        //    12: aload_1        
        //    13: getstatic       net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //    16: pop            
        //    17: getstatic       net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    20: getstatic       net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //    23: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    26: getfield        net/minecraft/client/settings/GameSettings.advancedItemTooltips:Z
        //    29: invokevirtual   net/minecraft/item/ItemStack.getTooltip:(Lnet/minecraft/entity/player/EntityPlayer;Z)Ljava/util/List;
        //    32: astore          4
        //    34: aload_1        
        //    35: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    38: invokevirtual   net/minecraft/item/Item.getCreativeTab:()Lnet/minecraft/creativetab/CreativeTabs;
        //    41: astore          5
        //    43: aload           5
        //    45: ifnonnull       151
        //    48: aload_1        
        //    49: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    52: getstatic       net/minecraft/init/Items.enchanted_book:Lnet/minecraft/item/ItemEnchantedBook;
        //    55: if_acmpne       151
        //    58: aload_1        
        //    59: invokestatic    net/minecraft/enchantment/EnchantmentHelper.getEnchantments:(Lnet/minecraft/item/ItemStack;)Ljava/util/Map;
        //    62: astore          6
        //    64: aload           6
        //    66: invokeinterface java/util/Map.size:()I
        //    71: iconst_1       
        //    72: if_icmpne       151
        //    75: aload           6
        //    77: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //    82: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    87: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    92: checkcast       Ljava/lang/Integer;
        //    95: invokevirtual   java/lang/Integer.intValue:()I
        //    98: invokestatic    net/minecraft/enchantment/Enchantment.func_180306_c:(I)Lnet/minecraft/enchantment/Enchantment;
        //   101: astore          7
        //   103: getstatic       net/minecraft/creativetab/CreativeTabs.creativeTabArray:[Lnet/minecraft/creativetab/CreativeTabs;
        //   106: astore          8
        //   108: aload           8
        //   110: arraylength    
        //   111: istore          9
        //   113: goto            145
        //   116: aload           8
        //   118: iconst_0       
        //   119: aaload         
        //   120: astore          11
        //   122: aload           11
        //   124: aload           7
        //   126: getfield        net/minecraft/enchantment/Enchantment.type:Lnet/minecraft/enchantment/EnumEnchantmentType;
        //   129: invokevirtual   net/minecraft/creativetab/CreativeTabs.hasRelevantEnchantmentType:(Lnet/minecraft/enchantment/EnumEnchantmentType;)Z
        //   132: ifeq            142
        //   135: aload           11
        //   137: astore          5
        //   139: goto            151
        //   142: iinc            10, 1
        //   145: iconst_0       
        //   146: iload           9
        //   148: if_icmplt       116
        //   151: aload           5
        //   153: ifnull          201
        //   156: aload           4
        //   158: iconst_1       
        //   159: new             Ljava/lang/StringBuilder;
        //   162: dup            
        //   163: invokespecial   java/lang/StringBuilder.<init>:()V
        //   166: getstatic       net/minecraft/util/EnumChatFormatting.BOLD:Lnet/minecraft/util/EnumChatFormatting;
        //   169: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   172: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   175: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   178: aload           5
        //   180: invokevirtual   net/minecraft/creativetab/CreativeTabs.getTranslatedTabLabel:()Ljava/lang/String;
        //   183: iconst_0       
        //   184: anewarray       Ljava/lang/Object;
        //   187: invokestatic    net/minecraft/client/resources/I18n.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   190: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   193: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   196: invokeinterface java/util/List.add:(ILjava/lang/Object;)V
        //   201: goto            296
        //   204: iconst_0       
        //   205: ifne            254
        //   208: aload           4
        //   210: iconst_0       
        //   211: new             Ljava/lang/StringBuilder;
        //   214: dup            
        //   215: invokespecial   java/lang/StringBuilder.<init>:()V
        //   218: aload_1        
        //   219: invokevirtual   net/minecraft/item/ItemStack.getRarity:()Lnet/minecraft/item/EnumRarity;
        //   222: getfield        net/minecraft/item/EnumRarity.rarityColor:Lnet/minecraft/util/EnumChatFormatting;
        //   225: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   228: aload           4
        //   230: iconst_0       
        //   231: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   236: checkcast       Ljava/lang/String;
        //   239: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   242: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   245: invokeinterface java/util/List.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   250: pop            
        //   251: goto            293
        //   254: aload           4
        //   256: iconst_0       
        //   257: new             Ljava/lang/StringBuilder;
        //   260: dup            
        //   261: invokespecial   java/lang/StringBuilder.<init>:()V
        //   264: getstatic       net/minecraft/util/EnumChatFormatting.GRAY:Lnet/minecraft/util/EnumChatFormatting;
        //   267: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   270: aload           4
        //   272: iconst_0       
        //   273: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   278: checkcast       Ljava/lang/String;
        //   281: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   284: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   287: invokeinterface java/util/List.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   292: pop            
        //   293: iinc            6, 1
        //   296: iconst_0       
        //   297: aload           4
        //   299: invokeinterface java/util/List.size:()I
        //   304: if_icmplt       204
        //   307: aload_0        
        //   308: aload           4
        //   310: iload_2        
        //   311: iload_3        
        //   312: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.drawHoveringText:(Ljava/util/List;II)V
        //   315: goto            325
        //   318: aload_0        
        //   319: aload_1        
        //   320: iload_2        
        //   321: iload_3        
        //   322: invokespecial   net/minecraft/client/renderer/InventoryEffectRenderer.renderToolTip:(Lnet/minecraft/item/ItemStack;II)V
        //   325: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final CreativeTabs creativeTabs = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        final int min = Math.min(CreativeTabs.creativeTabArray.length, 12);
        if (false) {
            final int n4;
            n4 += 2;
        }
        CreativeTabs[] array;
        while (0 < (array = Arrays.copyOfRange(CreativeTabs.creativeTabArray, 0, min)).length) {
            final CreativeTabs creativeTabs2 = array[0];
            GuiContainerCreative.mc.getTextureManager().bindTexture(GuiContainerCreative.creativeInventoryTabs);
            if (creativeTabs2 != null && creativeTabs2.getTabIndex() != GuiContainerCreative.selectedTabIndex) {
                this.func_147051_a(creativeTabs2);
            }
            final byte b = 1;
        }
        if (false) {
            if (creativeTabs != CreativeTabs.tabAllSearch) {
                GuiContainerCreative.mc.getTextureManager().bindTexture(GuiContainerCreative.creativeInventoryTabs);
                this.func_147051_a(CreativeTabs.tabAllSearch);
            }
            if (creativeTabs != CreativeTabs.tabInventory) {
                GuiContainerCreative.mc.getTextureManager().bindTexture(GuiContainerCreative.creativeInventoryTabs);
                this.func_147051_a(CreativeTabs.tabInventory);
            }
        }
        GuiContainerCreative.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + creativeTabs.getBackgroundImageName()));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.searchField.drawTextBox();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int n5 = this.guiLeft + 175;
        final int n6 = this.guiTop + 18;
        final int n7 = n6 + 112;
        GuiContainerCreative.mc.getTextureManager().bindTexture(GuiContainerCreative.creativeInventoryTabs);
        if (creativeTabs.shouldHidePlayerInventory()) {
            this.drawTexturedModalRect(n5, n6 + (int)((n7 - n6 - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        }
        if ((creativeTabs == null || creativeTabs.getTabPage() != 0) && creativeTabs != CreativeTabs.tabAllSearch && creativeTabs != CreativeTabs.tabInventory) {
            return;
        }
        this.func_147051_a(creativeTabs);
        if (creativeTabs == CreativeTabs.tabInventory) {
            GuiInventory.drawEntityOnScreen(this.guiLeft + 43, this.guiTop + 45, 20, (float)(this.guiLeft + 43 - n2), (float)(this.guiTop + 45 - 30 - n3), Minecraft.thePlayer);
        }
    }
    
    protected boolean func_147049_a(final CreativeTabs creativeTabs, final int n, final int n2) {
        if (creativeTabs.getTabPage() != 0 && creativeTabs != CreativeTabs.tabAllSearch && creativeTabs != CreativeTabs.tabInventory) {
            return false;
        }
        final int tabColumn = creativeTabs.getTabColumn();
        int n3 = 28 * tabColumn;
        if (tabColumn == 5) {
            n3 = this.xSize - 28 + 2;
        }
        else if (tabColumn > 0) {
            n3 += tabColumn;
        }
        if (creativeTabs.isTabInFirstRow()) {
            final int n4;
            n4 -= 32;
        }
        else {
            final int n4 = 0 + this.ySize;
        }
        return n >= n3 && n <= n3 + 28 && n2 >= 0 && n2 <= 32;
    }
    
    protected boolean renderCreativeInventoryHoveringText(final CreativeTabs creativeTabs, final int n, final int n2) {
        final int tabColumn = creativeTabs.getTabColumn();
        int n3 = 28 * tabColumn;
        if (tabColumn == 5) {
            n3 = this.xSize - 28 + 2;
        }
        else if (tabColumn > 0) {
            n3 += tabColumn;
        }
        if (creativeTabs.isTabInFirstRow()) {
            final int n4;
            n4 -= 32;
        }
        else {
            final int n4 = 0 + this.ySize;
        }
        if (this.isPointInRegion(n3 + 3, 3, 23, 27, n, n2)) {
            this.drawCreativeTabHoveringText(I18n.format(creativeTabs.getTranslatedTabLabel(), new Object[0]), n, n2);
            return true;
        }
        return false;
    }
    
    protected void func_147051_a(final CreativeTabs creativeTabs) {
        final boolean b = creativeTabs.getTabIndex() == GuiContainerCreative.selectedTabIndex;
        final boolean tabInFirstRow = creativeTabs.isTabInFirstRow();
        final int tabColumn = creativeTabs.getTabColumn();
        final int n = tabColumn * 28;
        int n2 = this.guiLeft + 28 * tabColumn;
        int guiTop = this.guiTop;
        int n3 = 0;
        if (b) {
            n3 += 32;
        }
        if (tabColumn == 5) {
            n2 = this.guiLeft + this.xSize - 28;
        }
        else if (tabColumn > 0) {
            n2 += tabColumn;
        }
        if (tabInFirstRow) {
            guiTop -= 28;
        }
        else {
            n3 += 64;
            guiTop += this.ySize - 4;
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(n2, guiTop, n, 0, 28, 32);
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        n2 += 6;
        final int n4 = guiTop + 8 + (tabInFirstRow ? 1 : -1);
        final ItemStack iconItemStack = creativeTabs.getIconItemStack();
        this.itemRender.func_180450_b(iconItemStack, n2, n4);
        this.itemRender.renderItemOverlays(this.fontRendererObj, iconItemStack, n2, n4);
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            GuiContainerCreative.mc.displayGuiScreen(new GuiAchievements(this, Minecraft.thePlayer.getStatFileWriter()));
        }
        if (guiButton.id == 1) {
            GuiContainerCreative.mc.displayGuiScreen(new GuiStats(this, Minecraft.thePlayer.getStatFileWriter()));
        }
        if (guiButton.id == 100) {
            GuiContainerCreative.tabPage = (false ? 0 : 1);
        }
        guiButton.id;
        101;
        if (guiButton.id == 101) {
            GuiContainerCreative.tabPage = Math.max(-1, 0);
        }
        else if (guiButton.id == 102) {
            GuiContainerCreative.tabPage = Math.min(1, this.maxPages);
        }
    }
    
    public int getSelectedTabIndex() {
        return GuiContainerCreative.selectedTabIndex;
    }
    
    public int func_147056_g() {
        return GuiContainerCreative.selectedTabIndex;
    }
    
    static InventoryBasic access$0() {
        return GuiContainerCreative.field_147060_v;
    }
    
    static class ContainerCreative extends Container
    {
        public List itemList;
        
        public ContainerCreative(final EntityPlayer entityPlayer) {
            this.itemList = Lists.newArrayList();
            final InventoryPlayer inventory = entityPlayer.inventory;
            this.scrollTo(0.0f);
        }
        
        @Override
        public boolean canInteractWith(final EntityPlayer entityPlayer) {
            return true;
        }
        
        public void scrollTo(final float n) {
            final int n2 = (int)(n * ((this.itemList.size() + 9 - 1) / 9 - 5) + 0.5);
        }
        
        public boolean func_148328_e() {
            return this.itemList.size() > 45;
        }
        
        @Override
        protected void retrySlotClick(final int n, final int n2, final boolean b, final EntityPlayer entityPlayer) {
        }
        
        @Override
        public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
            if (n >= this.inventorySlots.size() - 9 && n < this.inventorySlots.size()) {
                final Slot slot = this.inventorySlots.get(n);
                if (slot != null && slot.getHasStack()) {
                    slot.putStack(null);
                }
            }
            return null;
        }
        
        public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
            return slot.yDisplayPosition > 90;
        }
        
        @Override
        public boolean canDragIntoSlot(final Slot slot) {
            return slot.inventory instanceof InventoryPlayer || (slot.yDisplayPosition > 90 && slot.xDisplayPosition <= 162);
        }
    }
    
    class CreativeSlot extends Slot
    {
        private final Slot slot;
        final GuiContainerCreative this$0;
        
        public CreativeSlot(final GuiContainerCreative this$0, final Slot slot, final int n) {
            this.this$0 = this$0;
            super(slot.inventory, n, 0, 0);
            this.slot = slot;
        }
        
        @Override
        public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
            this.slot.onPickupFromSlot(entityPlayer, itemStack);
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return this.slot.isItemValid(itemStack);
        }
        
        @Override
        public ItemStack getStack() {
            return this.slot.getStack();
        }
        
        @Override
        public boolean getHasStack() {
            return this.slot.getHasStack();
        }
        
        @Override
        public void putStack(final ItemStack itemStack) {
            this.slot.putStack(itemStack);
        }
        
        @Override
        public void onSlotChanged() {
            this.slot.onSlotChanged();
        }
        
        @Override
        public int getSlotStackLimit() {
            return this.slot.getSlotStackLimit();
        }
        
        public int getItemStackLimit(final ItemStack itemStack) {
            return this.slot.func_178170_b(itemStack);
        }
        
        public String getSlotTexture() {
            return this.slot.func_178171_c();
        }
        
        @Override
        public ItemStack decrStackSize(final int n) {
            return this.slot.decrStackSize(n);
        }
        
        @Override
        public boolean isHere(final IInventory inventory, final int n) {
            return this.slot.isHere(inventory, n);
        }
        
        static Slot access$0(final CreativeSlot creativeSlot) {
            return creativeSlot.slot;
        }
    }
}
