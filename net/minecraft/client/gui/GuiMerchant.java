package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.village.*;
import net.minecraft.item.*;

public class GuiMerchant extends GuiContainer
{
    private static final Logger logger;
    private static final ResourceLocation field_147038_v;
    private IMerchant field_147037_w;
    private MerchantButton field_147043_x;
    private MerchantButton field_147042_y;
    private int field_147041_z;
    private IChatComponent field_147040_A;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000762";
        logger = LogManager.getLogger();
        field_147038_v = new ResourceLocation("textures/gui/container/villager.png");
    }
    
    public GuiMerchant(final InventoryPlayer inventoryPlayer, final IMerchant field_147037_w, final World world) {
        super(new ContainerMerchant(inventoryPlayer, field_147037_w, world));
        this.field_147037_w = field_147037_w;
        this.field_147040_A = field_147037_w.getDisplayName();
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int n = (GuiMerchant.width - this.xSize) / 2;
        final int n2 = (GuiMerchant.height - this.ySize) / 2;
        this.buttonList.add(this.field_147043_x = new MerchantButton(1, n + 120 + 27, n2 + 24 - 1, true));
        this.buttonList.add(this.field_147042_y = new MerchantButton(2, n + 36 - 19, n2 + 24 - 1, false));
        this.field_147043_x.enabled = false;
        this.field_147042_y.enabled = false;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final String unformattedText = this.field_147040_A.getUnformattedText();
        this.fontRendererObj.drawString(unformattedText, this.xSize / 2 - this.fontRendererObj.getStringWidth(unformattedText) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final IMerchant field_147037_w = this.field_147037_w;
        final Minecraft mc = GuiMerchant.mc;
        final MerchantRecipeList recipes = field_147037_w.getRecipes(Minecraft.thePlayer);
        if (recipes != null) {
            this.field_147043_x.enabled = (this.field_147041_z < recipes.size() - 1);
            this.field_147042_y.enabled = (this.field_147041_z > 0);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton == this.field_147043_x) {
            ++this.field_147041_z;
            final IMerchant field_147037_w = this.field_147037_w;
            final Minecraft mc = GuiMerchant.mc;
            final MerchantRecipeList recipes = field_147037_w.getRecipes(Minecraft.thePlayer);
            if (recipes != null && this.field_147041_z >= recipes.size()) {
                this.field_147041_z = recipes.size() - 1;
            }
        }
        else if (guiButton == this.field_147042_y) {
            --this.field_147041_z;
            if (this.field_147041_z < 0) {
                this.field_147041_z = 0;
            }
        }
        ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.field_147041_z);
        final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(this.field_147041_z);
        GuiMerchant.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", packetBuffer));
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiMerchant.mc.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
        this.drawTexturedModalRect((GuiMerchant.width - this.xSize) / 2, (GuiMerchant.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
        final IMerchant field_147037_w = this.field_147037_w;
        final Minecraft mc = GuiMerchant.mc;
        final MerchantRecipeList recipes = field_147037_w.getRecipes(Minecraft.thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            final int field_147041_z = this.field_147041_z;
            if (field_147041_z < 0 || field_147041_z >= recipes.size()) {
                return;
            }
            if (recipes.get(field_147041_z).isRecipeDisabled()) {
                GuiMerchant.mc.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final IMerchant field_147037_w = this.field_147037_w;
        final Minecraft mc = GuiMerchant.mc;
        final MerchantRecipeList recipes = field_147037_w.getRecipes(Minecraft.thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            final int n4 = (GuiMerchant.width - this.xSize) / 2;
            final int n5 = (GuiMerchant.height - this.ySize) / 2;
            final MerchantRecipe merchantRecipe = recipes.get(this.field_147041_z);
            final ItemStack itemToBuy = merchantRecipe.getItemToBuy();
            final ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            final ItemStack itemToSell = merchantRecipe.getItemToSell();
            this.itemRender.zLevel = 100.0f;
            this.itemRender.func_180450_b(itemToBuy, n4 + 36, n5 + 24);
            this.itemRender.func_175030_a(this.fontRendererObj, itemToBuy, n4 + 36, n5 + 24);
            if (secondItemToBuy != null) {
                this.itemRender.func_180450_b(secondItemToBuy, n4 + 62, n5 + 24);
                this.itemRender.func_175030_a(this.fontRendererObj, secondItemToBuy, n4 + 62, n5 + 24);
            }
            this.itemRender.func_180450_b(itemToSell, n4 + 120, n5 + 24);
            this.itemRender.func_175030_a(this.fontRendererObj, itemToSell, n4 + 120, n5 + 24);
            this.itemRender.zLevel = 0.0f;
            if (this.isPointInRegion(36, 24, 16, 16, n, n2) && itemToBuy != null) {
                this.renderToolTip(itemToBuy, n, n2);
            }
            else if (secondItemToBuy != null && this.isPointInRegion(62, 24, 16, 16, n, n2) && secondItemToBuy != null) {
                this.renderToolTip(secondItemToBuy, n, n2);
            }
            else if (itemToSell != null && this.isPointInRegion(120, 24, 16, 16, n, n2) && itemToSell != null) {
                this.renderToolTip(itemToSell, n, n2);
            }
            else if (merchantRecipe.isRecipeDisabled() && (this.isPointInRegion(83, 21, 28, 21, n, n2) || this.isPointInRegion(83, 51, 28, 21, n, n2))) {
                this.drawCreativeTabHoveringText(I18n.format("merchant.deprecated", new Object[0]), n, n2);
            }
        }
    }
    
    public IMerchant getMerchant() {
        return this.field_147037_w;
    }
    
    static ResourceLocation access$0() {
        return GuiMerchant.field_147038_v;
    }
    
    static class MerchantButton extends GuiButton
    {
        private final boolean field_146157_o;
        private static final String __OBFID;
        
        public MerchantButton(final int n, final int n2, final int n3, final boolean field_146157_o) {
            super(n, n2, n3, 12, 19, "");
            this.field_146157_o = field_146157_o;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                minecraft.getTextureManager().bindTexture(GuiMerchant.access$0());
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                final boolean b = n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
                if (!this.enabled) {
                    final int n3 = 176 + this.width * 2;
                }
                else if (b) {
                    final int n4 = 176 + this.width;
                }
                if (!this.field_146157_o) {
                    final int n5 = 0 + this.height;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, 176, 0, this.width, this.height);
            }
        }
        
        static {
            __OBFID = "CL_00000763";
        }
    }
}
