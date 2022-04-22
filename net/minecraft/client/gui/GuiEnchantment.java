package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.glu.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class GuiEnchantment extends GuiContainer
{
    private static final ResourceLocation field_147078_C;
    private static final ResourceLocation field_147070_D;
    private static final ModelBook field_147072_E;
    private final InventoryPlayer field_175379_F;
    private Random field_147074_F;
    private ContainerEnchantment field_147075_G;
    public int field_147073_u;
    public float field_147071_v;
    public float field_147069_w;
    public float field_147082_x;
    public float field_147081_y;
    public float field_147080_z;
    public float field_147076_A;
    ItemStack field_147077_B;
    private final IWorldNameable field_175380_I;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000757";
        field_147078_C = new ResourceLocation("textures/gui/container/enchanting_table.png");
        field_147070_D = new ResourceLocation("textures/entity/enchanting_table_book.png");
        field_147072_E = new ModelBook();
    }
    
    public GuiEnchantment(final InventoryPlayer field_175379_F, final World world, final IWorldNameable field_175380_I) {
        super(new ContainerEnchantment(field_175379_F, world));
        this.field_147074_F = new Random();
        this.field_175379_F = field_175379_F;
        this.field_147075_G = (ContainerEnchantment)this.inventorySlots;
        this.field_175380_I = field_175380_I;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12, 5, 4210752);
        this.fontRendererObj.drawString(this.field_175379_F.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.func_147068_g();
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        final int n4 = (GuiEnchantment.width - this.xSize) / 2;
        final int n5 = (GuiEnchantment.height - this.ySize) / 2;
        while (0 < 3) {
            final int n6 = n - (n4 + 60);
            final int n7 = n2 - (n5 + 14 + 0);
            if (n6 >= 0 && n7 >= 0 && n6 < 108 && n7 < 19) {
                final ContainerEnchantment field_147075_G = this.field_147075_G;
                final Minecraft mc = GuiEnchantment.mc;
                if (field_147075_G.enchantItem(Minecraft.thePlayer, 0)) {
                    final Minecraft mc2 = GuiEnchantment.mc;
                    Minecraft.playerController.sendEnchantPacket(this.field_147075_G.windowId, 0);
                }
            }
            int n8 = 0;
            ++n8;
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GuiEnchantment.mc.getTextureManager().bindTexture(GuiEnchantment.field_147078_C);
        final int n4 = (GuiEnchantment.width - this.xSize) / 2;
        final int n5 = (GuiEnchantment.height - this.ySize) / 2;
        this.drawTexturedModalRect(n4, n5, 0, 0, this.xSize, this.ySize);
        GlStateManager.matrixMode(5889);
        final ScaledResolution scaledResolution = new ScaledResolution(GuiEnchantment.mc, GuiEnchantment.mc.displayWidth, GuiEnchantment.mc.displayHeight);
        GlStateManager.viewport((ScaledResolution.getScaledWidth() - 320) / 2 * scaledResolution.getScaleFactor(), (ScaledResolution.getScaledHeight() - 240) / 2 * scaledResolution.getScaleFactor(), 320 * scaledResolution.getScaleFactor(), 240 * scaledResolution.getScaleFactor());
        GlStateManager.translate(-0.34f, 0.23f, 0.0f);
        Project.gluPerspective(90.0f, 1.3333334f, 9.0f, 80.0f);
        final float n6 = 1.0f;
        GlStateManager.matrixMode(5888);
        GlStateManager.translate(0.0f, 3.3f, -16.0f);
        GlStateManager.scale(n6, n6, n6);
        final float n7 = 5.0f;
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GuiEnchantment.mc.getTextureManager().bindTexture(GuiEnchantment.field_147070_D);
        GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
        final float n8 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * n;
        GlStateManager.translate((1.0f - n8) * 0.2f, (1.0f - n8) * 0.1f, (1.0f - n8) * 0.25f);
        GlStateManager.rotate(-(1.0f - n8) * 90.0f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        final float n9 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * n + 0.25f;
        final float n10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * n + 0.75f;
        float n11 = (n9 - MathHelper.truncateDoubleToInt(n9)) * 1.6f - 0.3f;
        float n12 = (n10 - MathHelper.truncateDoubleToInt(n10)) * 1.6f - 0.3f;
        if (n11 < 0.0f) {
            n11 = 0.0f;
        }
        if (n12 < 0.0f) {
            n12 = 0.0f;
        }
        if (n11 > 1.0f) {
            n11 = 1.0f;
        }
        if (n12 > 1.0f) {
            n12 = 1.0f;
        }
        GuiEnchantment.field_147072_E.render(null, 0.0f, n11, n12, n8, 0.0f, 0.0625f);
        GlStateManager.matrixMode(5889);
        GlStateManager.viewport(0, 0, GuiEnchantment.mc.displayWidth, GuiEnchantment.mc.displayHeight);
        GlStateManager.matrixMode(5888);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNameParts.func_178176_a().reseedRandomGenerator(this.field_147075_G.field_178149_f);
        final int func_178147_e = this.field_147075_G.func_178147_e();
        while (0 < 3) {
            final int n13 = n4 + 60;
            final int n14 = n13 + 20;
            final String generateNewRandomName = EnchantmentNameParts.func_178176_a().generateNewRandomName();
            this.zLevel = 0.0f;
            GuiEnchantment.mc.getTextureManager().bindTexture(GuiEnchantment.field_147078_C);
            final int n15 = this.field_147075_G.enchantLevels[0];
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (n15 == 0) {
                this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 185, 108, 19);
            }
            else {
                final String string = new StringBuilder().append(n15).toString();
                final FontRenderer standardGalacticFontRenderer = GuiEnchantment.mc.standardGalacticFontRenderer;
                Label_0842: {
                    Label_0709: {
                        if (func_178147_e >= 1) {
                            final Minecraft mc = GuiEnchantment.mc;
                            if (Minecraft.thePlayer.experienceLevel >= n15) {
                                break Label_0709;
                            }
                        }
                        final Minecraft mc2 = GuiEnchantment.mc;
                        if (!Minecraft.thePlayer.capabilities.isCreativeMode) {
                            this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 185, 108, 19);
                            this.drawTexturedModalRect(n13 + 1, n5 + 15 + 0, 0, 239, 16, 16);
                            standardGalacticFontRenderer.drawSplitString(generateNewRandomName, n14, n5 + 16 + 0, 86, 4226832);
                            break Label_0842;
                        }
                    }
                    final int n16 = n2 - (n4 + 60);
                    final int n17 = n3 - (n5 + 14 + 0);
                    if (n16 >= 0 && n17 >= 0 && n16 < 108 && n17 < 19) {
                        this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 204, 108, 19);
                    }
                    else {
                        this.drawTexturedModalRect(n13, n5 + 14 + 0, 0, 166, 108, 19);
                    }
                    this.drawTexturedModalRect(n13 + 1, n5 + 15 + 0, 0, 223, 16, 16);
                    standardGalacticFontRenderer.drawSplitString(generateNewRandomName, n14, n5 + 16 + 0, 86, 8453920);
                }
                final Minecraft mc3 = GuiEnchantment.mc;
                final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
                fontRendererObj.func_175063_a(string, (float)(n14 + 86 - fontRendererObj.getStringWidth(string)), (float)(n5 + 16 + 0 + 7), 8453920);
            }
            int n18 = 0;
            ++n18;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final Minecraft mc = GuiEnchantment.mc;
        final boolean isCreativeMode = Minecraft.thePlayer.capabilities.isCreativeMode;
        final int func_178147_e = this.field_147075_G.func_178147_e();
        while (0 < 3) {
            final int n4 = this.field_147075_G.enchantLevels[0];
            final int n5 = this.field_147075_G.field_178151_h[0];
            if (this.isPointInRegion(60, 14, 108, 17, n, n2) && n4 > 0 && n5 >= 0) {
                final ArrayList arrayList = Lists.newArrayList();
                if (n5 >= 0 && Enchantment.func_180306_c(n5 & 0xFF) != null) {
                    arrayList.add(String.valueOf(EnumChatFormatting.WHITE.toString()) + EnumChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", Enchantment.func_180306_c(n5 & 0xFF).getTranslatedName((n5 & 0xFF00) >> 8)));
                }
                if (!isCreativeMode) {
                    if (n5 >= 0) {
                        arrayList.add("");
                    }
                    final Minecraft mc2 = GuiEnchantment.mc;
                    if (Minecraft.thePlayer.experienceLevel < n4) {
                        arrayList.add(String.valueOf(EnumChatFormatting.RED.toString()) + "Level Requirement: " + this.field_147075_G.enchantLevels[0]);
                    }
                    else {
                        String s;
                        if (true == true) {
                            s = I18n.format("container.enchant.lapis.one", new Object[0]);
                        }
                        else {
                            s = I18n.format("container.enchant.lapis.many", 1);
                        }
                        if (func_178147_e >= 1) {
                            arrayList.add(String.valueOf(EnumChatFormatting.GRAY.toString()) + s);
                        }
                        else {
                            arrayList.add(String.valueOf(EnumChatFormatting.RED.toString()) + s);
                        }
                        String s2;
                        if (true == true) {
                            s2 = I18n.format("container.enchant.level.one", new Object[0]);
                        }
                        else {
                            s2 = I18n.format("container.enchant.level.many", 1);
                        }
                        arrayList.add(String.valueOf(EnumChatFormatting.GRAY.toString()) + s2);
                    }
                }
                this.drawHoveringText(arrayList, n, n2);
                break;
            }
            int n6 = 0;
            ++n6;
        }
    }
    
    public void func_147068_g() {
        final ItemStack stack = this.inventorySlots.getSlot(0).getStack();
        if (!ItemStack.areItemStacksEqual(stack, this.field_147077_B)) {
            this.field_147077_B = stack;
            do {
                this.field_147082_x += this.field_147074_F.nextInt(4) - this.field_147074_F.nextInt(4);
            } while (this.field_147071_v <= this.field_147082_x + 1.0f && this.field_147071_v >= this.field_147082_x - 1.0f);
        }
        ++this.field_147073_u;
        this.field_147069_w = this.field_147071_v;
        this.field_147076_A = this.field_147080_z;
        while (0 < 3) {
            if (this.field_147075_G.enchantLevels[0] != 0) {}
            int n = 0;
            ++n;
        }
        if (true) {
            this.field_147080_z += 0.2f;
        }
        else {
            this.field_147080_z -= 0.2f;
        }
        this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0f, 1.0f);
        final float n2 = (this.field_147082_x - this.field_147071_v) * 0.4f;
        final float n3 = 0.2f;
        this.field_147081_y += (MathHelper.clamp_float(n2, -n3, n3) - this.field_147081_y) * 0.9f;
        this.field_147071_v += this.field_147081_y;
    }
}
