package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;

public abstract class GuiContainer extends GuiScreen
{
    protected static final ResourceLocation inventoryBackground;
    protected int xSize;
    protected int ySize;
    public Container inventorySlots;
    public int guiLeft;
    public int guiTop;
    private Slot theSlot;
    private Slot clickedSlot;
    private boolean isRightMouseClick;
    private ItemStack draggedStack;
    private int touchUpX;
    private int touchUpY;
    private Slot returningStackDestSlot;
    private long returningStackTime;
    private ItemStack returningStack;
    private Slot currentDragTargetSlot;
    private long dragItemDropDelay;
    protected final Set dragSplittingSlots;
    protected boolean dragSplitting;
    private int dragSplittingLimit;
    private int dragSplittingButton;
    private boolean ignoreMouseUp;
    private int dragSplittingRemnant;
    private long lastClickTime;
    private Slot lastClickSlot;
    private int lastClickButton;
    private boolean doubleClick;
    private ItemStack shiftClickedSlot;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000737";
        inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    }
    
    public GuiContainer(final Container inventorySlots) {
        this.xSize = 176;
        this.ySize = 166;
        this.dragSplittingSlots = Sets.newHashSet();
        this.inventorySlots = inventorySlots;
        this.ignoreMouseUp = true;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final Minecraft mc = GuiContainer.mc;
        Minecraft.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (GuiContainer.width - this.xSize) / 2;
        this.guiTop = (GuiContainer.height - this.ySize) / 2;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        this.drawGuiContainerBackgroundLayer(n3, n, n2);
        super.drawScreen(n, n2, n3);
        GlStateManager.translate((float)guiLeft, (float)guiTop, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.theSlot = null;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240 / 1.0f, 240 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        while (0 < this.inventorySlots.inventorySlots.size()) {
            final Slot theSlot = this.inventorySlots.inventorySlots.get(0);
            this.drawSlot(theSlot);
            if (this.isMouseOverSlot(theSlot, n, n2) && theSlot.canBeHovered()) {
                this.theSlot = theSlot;
                final int xDisplayPosition = theSlot.xDisplayPosition;
                final int yDisplayPosition = theSlot.yDisplayPosition;
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(8, yDisplayPosition, 24, yDisplayPosition + 16, -2130706433, -2130706433);
                GlStateManager.colorMask(true, true, true, true);
            }
            int n4 = 0;
            ++n4;
        }
        this.drawGuiContainerForegroundLayer(n, n2);
        final Minecraft mc = GuiContainer.mc;
        final InventoryPlayer inventory = Minecraft.thePlayer.inventory;
        ItemStack itemStack = (this.draggedStack == null) ? inventory.getItemStack() : this.draggedStack;
        if (itemStack != null) {
            final int n5 = (this.draggedStack == null) ? 8 : 16;
            String string = null;
            if (this.draggedStack != null && this.isRightMouseClick) {
                itemStack = itemStack.copy();
                itemStack.stackSize = MathHelper.ceiling_float_int(itemStack.stackSize / 2.0f);
            }
            else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                itemStack = itemStack.copy();
                itemStack.stackSize = this.dragSplittingRemnant;
                if (itemStack.stackSize == 0) {
                    string = EnumChatFormatting.YELLOW + "0";
                }
            }
            this.drawItemStack(itemStack, n - guiLeft - 8, n2 - guiTop - n5, string);
        }
        if (this.returningStack != null) {
            float n6 = (Minecraft.getSystemTime() - this.returningStackTime) / 100.0f;
            if (n6 >= 1.0f) {
                n6 = 1.0f;
                this.returningStack = null;
            }
            this.drawItemStack(this.returningStack, this.touchUpX + (int)((this.returningStackDestSlot.xDisplayPosition - this.touchUpX) * n6), this.touchUpY + (int)((this.returningStackDestSlot.yDisplayPosition - this.touchUpY) * n6), null);
        }
        if (inventory.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            this.renderToolTip(this.theSlot.getStack(), n, n2);
        }
    }
    
    private void drawItemStack(final ItemStack itemStack, final int n, final int n2, final String s) {
        GlStateManager.translate(0.0f, 0.0f, 32.0f);
        this.zLevel = 200.0f;
        this.itemRender.zLevel = 200.0f;
        this.itemRender.func_180450_b(itemStack, n, n2);
        this.itemRender.func_180453_a(this.fontRendererObj, itemStack, n, n2 - ((this.draggedStack == null) ? 0 : 8), s);
        this.zLevel = 0.0f;
        this.itemRender.zLevel = 0.0f;
    }
    
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
    }
    
    protected abstract void drawGuiContainerBackgroundLayer(final float p0, final int p1, final int p2);
    
    private void drawSlot(final Slot slot) {
        final int xDisplayPosition = slot.xDisplayPosition;
        final int yDisplayPosition = slot.yDisplayPosition;
        ItemStack itemStack = slot.getStack();
        final boolean b = slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        final Minecraft mc = GuiContainer.mc;
        final ItemStack itemStack2 = Minecraft.thePlayer.inventory.getItemStack();
        if (slot == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemStack != null) {
            final ItemStack copy;
            itemStack = (copy = itemStack.copy());
            copy.stackSize /= 2;
        }
        else if (this.dragSplitting && this.dragSplittingSlots.contains(slot) && itemStack2 != null) {
            if (this.dragSplittingSlots.size() == 1) {
                return;
            }
            if (Container.canAddItemToSlot(slot, itemStack2, true) && this.inventorySlots.canDragIntoSlot(slot)) {
                itemStack = itemStack2.copy();
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemStack, (slot.getStack() == null) ? 0 : slot.getStack().stackSize);
                if (itemStack.stackSize > itemStack.getMaxStackSize()) {
                    new StringBuilder().append(EnumChatFormatting.YELLOW).append(itemStack.getMaxStackSize()).toString();
                    itemStack.stackSize = itemStack.getMaxStackSize();
                }
                if (itemStack.stackSize > slot.func_178170_b(itemStack)) {
                    new StringBuilder().append(EnumChatFormatting.YELLOW).append(slot.func_178170_b(itemStack)).toString();
                    itemStack.stackSize = slot.func_178170_b(itemStack);
                }
            }
            else {
                this.dragSplittingSlots.remove(slot);
                this.updateDragSplitting();
            }
        }
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        if (itemStack == null) {
            final String func_178171_c = slot.func_178171_c();
            if (func_178171_c != null) {
                final TextureAtlasSprite atlasSprite = GuiContainer.mc.getTextureMapBlocks().getAtlasSprite(func_178171_c);
                GuiContainer.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.func_175175_a(xDisplayPosition, yDisplayPosition, atlasSprite, 16, 16);
            }
        }
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    private void updateDragSplitting() {
        final Minecraft mc = GuiContainer.mc;
        final ItemStack itemStack = Minecraft.thePlayer.inventory.getItemStack();
        if (itemStack != null && this.dragSplitting) {
            this.dragSplittingRemnant = itemStack.stackSize;
            for (final Slot slot : this.dragSplittingSlots) {
                final ItemStack copy = itemStack.copy();
                final int n = (slot.getStack() == null) ? 0 : slot.getStack().stackSize;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, copy, n);
                if (copy.stackSize > copy.getMaxStackSize()) {
                    copy.stackSize = copy.getMaxStackSize();
                }
                if (copy.stackSize > slot.func_178170_b(copy)) {
                    copy.stackSize = slot.func_178170_b(copy);
                }
                this.dragSplittingRemnant -= copy.stackSize - n;
            }
        }
    }
    
    private Slot getSlotAtPosition(final int n, final int n2) {
        while (0 < this.inventorySlots.inventorySlots.size()) {
            final Slot slot = this.inventorySlots.inventorySlots.get(0);
            if (this.isMouseOverSlot(slot, n, n2)) {
                return slot;
            }
            int n3 = 0;
            ++n3;
        }
        return null;
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        final boolean b = n3 == GuiContainer.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final long systemTime = Minecraft.getSystemTime();
        this.doubleClick = (this.lastClickSlot == slotAtPosition && systemTime - this.lastClickTime < 250L && this.lastClickButton == n3);
        this.ignoreMouseUp = false;
        if (n3 == 0 || n3 == 1 || b) {
            final int guiLeft = this.guiLeft;
            final int guiTop = this.guiTop;
            final boolean b2 = n < guiLeft || n2 < guiTop || n >= guiLeft + this.xSize || n2 >= guiTop + this.ySize;
            if (slotAtPosition != null) {
                final int slotNumber = slotAtPosition.slotNumber;
            }
            if (b2) {}
            if (GuiContainer.mc.gameSettings.touchscreen && b2) {
                final Minecraft mc = GuiContainer.mc;
                if (Minecraft.thePlayer.inventory.getItemStack() == null) {
                    GuiContainer.mc.displayGuiScreen(null);
                    return;
                }
            }
            if (GuiContainer.mc.gameSettings.touchscreen) {
                if (slotAtPosition != null && slotAtPosition.getHasStack()) {
                    this.clickedSlot = slotAtPosition;
                    this.draggedStack = null;
                    this.isRightMouseClick = (n3 == 1);
                }
                else {
                    this.clickedSlot = null;
                }
            }
            else if (!this.dragSplitting) {
                final Minecraft mc2 = GuiContainer.mc;
                if (Minecraft.thePlayer.inventory.getItemStack() == null) {
                    if (n3 == GuiContainer.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.handleMouseClick(slotAtPosition, -999, n3, 3);
                    }
                    else {
                        if (false) {
                            this.shiftClickedSlot = ((slotAtPosition != null && slotAtPosition.getHasStack()) ? slotAtPosition.getStack() : null);
                        }
                        this.handleMouseClick(slotAtPosition, -999, n3, 4);
                    }
                    this.ignoreMouseUp = true;
                }
                else {
                    this.dragSplitting = true;
                    this.dragSplittingButton = n3;
                    this.dragSplittingSlots.clear();
                    if (n3 == 0) {
                        this.dragSplittingLimit = 0;
                    }
                    else if (n3 == 1) {
                        this.dragSplittingLimit = 1;
                    }
                    else if (n3 == GuiContainer.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.dragSplittingLimit = 2;
                    }
                }
            }
        }
        this.lastClickSlot = slotAtPosition;
        this.lastClickTime = systemTime;
        this.lastClickButton = n3;
    }
    
    @Override
    protected void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final Minecraft mc = GuiContainer.mc;
        final ItemStack itemStack = Minecraft.thePlayer.inventory.getItemStack();
        if (this.clickedSlot != null && GuiContainer.mc.gameSettings.touchscreen) {
            if (n3 == 0 || n3 == 1) {
                if (this.draggedStack == null) {
                    if (slotAtPosition != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack().copy();
                    }
                }
                else if (this.draggedStack.stackSize > 1 && slotAtPosition != null && Container.canAddItemToSlot(slotAtPosition, this.draggedStack, false)) {
                    final long systemTime = Minecraft.getSystemTime();
                    if (this.currentDragTargetSlot == slotAtPosition) {
                        if (systemTime - this.dragItemDropDelay > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.handleMouseClick(slotAtPosition, slotAtPosition.slotNumber, 1, 0);
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.dragItemDropDelay = systemTime + 750L;
                            final ItemStack draggedStack = this.draggedStack;
                            --draggedStack.stackSize;
                        }
                    }
                    else {
                        this.currentDragTargetSlot = slotAtPosition;
                        this.dragItemDropDelay = systemTime;
                    }
                }
            }
        }
        else if (this.dragSplitting && slotAtPosition != null && itemStack != null && itemStack.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(slotAtPosition, itemStack, true) && slotAtPosition.isItemValid(itemStack) && this.inventorySlots.canDragIntoSlot(slotAtPosition)) {
            this.dragSplittingSlots.add(slotAtPosition);
            this.updateDragSplitting();
        }
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        final boolean b = n < guiLeft || n2 < guiTop || n >= guiLeft + this.xSize || n2 >= guiTop + this.ySize;
        if (slotAtPosition != null) {
            final int slotNumber = slotAtPosition.slotNumber;
        }
        if (b) {}
        if (this.doubleClick && slotAtPosition != null && n3 == 0 && this.inventorySlots.func_94530_a(null, slotAtPosition)) {
            if (isShiftKeyDown()) {
                if (slotAtPosition != null && slotAtPosition.inventory != null && this.shiftClickedSlot != null) {
                    for (final Slot slot : this.inventorySlots.inventorySlots) {
                        if (slot != null) {
                            final Slot slot2 = slot;
                            final Minecraft mc = GuiContainer.mc;
                            if (!slot2.canTakeStack(Minecraft.thePlayer) || !slot.getHasStack() || slot.inventory != slotAtPosition.inventory || !Container.canAddItemToSlot(slot, this.shiftClickedSlot, true)) {
                                continue;
                            }
                            this.handleMouseClick(slot, slot.slotNumber, n3, 1);
                        }
                    }
                }
            }
            else {
                this.handleMouseClick(slotAtPosition, -999, n3, 6);
            }
            this.doubleClick = false;
            this.lastClickTime = 0L;
        }
        else {
            if (this.dragSplitting && this.dragSplittingButton != n3) {
                this.dragSplitting = false;
                this.dragSplittingSlots.clear();
                this.ignoreMouseUp = true;
                return;
            }
            if (this.ignoreMouseUp) {
                this.ignoreMouseUp = false;
                return;
            }
            if (this.clickedSlot != null && GuiContainer.mc.gameSettings.touchscreen) {
                if (n3 == 0 || n3 == 1) {
                    if (this.draggedStack == null && slotAtPosition != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack();
                    }
                    final boolean canAddItemToSlot = Container.canAddItemToSlot(slotAtPosition, this.draggedStack, false);
                    if (this.draggedStack != null && canAddItemToSlot) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n3, 0);
                        this.handleMouseClick(slotAtPosition, -999, 0, 0);
                        final Minecraft mc2 = GuiContainer.mc;
                        if (Minecraft.thePlayer.inventory.getItemStack() != null) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n3, 0);
                            this.touchUpX = n - guiLeft;
                            this.touchUpY = n2 - guiTop;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = Minecraft.getSystemTime();
                        }
                        else {
                            this.returningStack = null;
                        }
                    }
                    else if (this.draggedStack != null) {
                        this.touchUpX = n - guiLeft;
                        this.touchUpY = n2 - guiTop;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    }
                    this.draggedStack = null;
                    this.clickedSlot = null;
                }
            }
            else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick(null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);
                for (final Slot slot3 : this.dragSplittingSlots) {
                    this.handleMouseClick(slot3, slot3.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
                }
                this.handleMouseClick(null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
            }
            else {
                final Minecraft mc3 = GuiContainer.mc;
                if (Minecraft.thePlayer.inventory.getItemStack() != null) {
                    if (n3 == GuiContainer.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                        this.handleMouseClick(slotAtPosition, -999, n3, 3);
                    }
                    else {
                        final int n4 = 0;
                        if (n4 != 0) {
                            this.shiftClickedSlot = ((slotAtPosition != null && slotAtPosition.getHasStack()) ? slotAtPosition.getStack() : null);
                        }
                        this.handleMouseClick(slotAtPosition, -999, n3, n4);
                    }
                }
            }
        }
        final Minecraft mc4 = GuiContainer.mc;
        if (Minecraft.thePlayer.inventory.getItemStack() == null) {
            this.lastClickTime = 0L;
        }
        this.dragSplitting = false;
    }
    
    private boolean isMouseOverSlot(final Slot slot, final int n, final int n2) {
        return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, n, n2);
    }
    
    protected boolean isPointInRegion(final int n, final int n2, final int n3, final int n4, int n5, int n6) {
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        n5 -= guiLeft;
        n6 -= guiTop;
        return n5 >= n - 1 && n5 < n + n3 + 1 && n6 >= n2 - 1 && n6 < n2 + n4 + 1;
    }
    
    protected void handleMouseClick(final Slot slot, int slotNumber, final int n, final int n2) {
        if (slot != null) {
            slotNumber = slot.slotNumber;
        }
        final Minecraft mc = GuiContainer.mc;
        final PlayerControllerMP playerController = Minecraft.playerController;
        final int windowId = this.inventorySlots.windowId;
        final int n3 = slotNumber;
        final Minecraft mc2 = GuiContainer.mc;
        playerController.windowClick(windowId, n3, n, n2, Minecraft.thePlayer);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1 || n == GuiContainer.mc.gameSettings.keyBindInventory.getKeyCode()) {
            final Minecraft mc = GuiContainer.mc;
            Minecraft.thePlayer.closeScreen();
        }
        this.checkHotbarKeys(n);
        if (this.theSlot != null && this.theSlot.getHasStack()) {
            if (n == GuiContainer.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
            }
            else if (n == GuiContainer.mc.gameSettings.keyBindDrop.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, GuiScreen.isCtrlKeyDown() ? 1 : 0, 4);
            }
        }
    }
    
    protected boolean checkHotbarKeys(final int n) {
        final Minecraft mc = GuiContainer.mc;
        if (Minecraft.thePlayer.inventory.getItemStack() != null || this.theSlot != null) {}
        return false;
    }
    
    @Override
    public void onGuiClosed() {
        final Minecraft mc = GuiContainer.mc;
        if (Minecraft.thePlayer != null) {
            final Container inventorySlots = this.inventorySlots;
            final Minecraft mc2 = GuiContainer.mc;
            inventorySlots.onContainerClosed(Minecraft.thePlayer);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final Minecraft mc = GuiContainer.mc;
        if (Minecraft.thePlayer.isEntityAlive()) {
            final Minecraft mc2 = GuiContainer.mc;
            if (!Minecraft.thePlayer.isDead) {
                return;
            }
        }
        final Minecraft mc3 = GuiContainer.mc;
        Minecraft.thePlayer.closeScreen();
    }
}
