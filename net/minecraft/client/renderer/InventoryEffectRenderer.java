package net.minecraft.client.renderer;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import net.minecraft.potion.*;
import net.minecraft.client.resources.*;
import java.util.*;

public abstract class InventoryEffectRenderer extends GuiContainer
{
    private boolean hasActivePotionEffects;
    private static final String __OBFID;
    
    public InventoryEffectRenderer(final Container container) {
        super(container);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.func_175378_g();
    }
    
    protected void func_175378_g() {
        final Minecraft mc = InventoryEffectRenderer.mc;
        if (!Minecraft.thePlayer.getActivePotionEffects().isEmpty()) {
            this.guiLeft = 160 + (InventoryEffectRenderer.width - this.xSize - 200) / 2;
            this.hasActivePotionEffects = true;
        }
        else {
            this.guiLeft = (InventoryEffectRenderer.width - this.xSize) / 2;
            this.hasActivePotionEffects = false;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        if (this.hasActivePotionEffects) {
            this.drawActivePotionEffects();
        }
    }
    
    protected void drawActivePotionEffects() {
        final int n = this.guiLeft - 124;
        int guiTop = this.guiTop;
        final Minecraft mc = InventoryEffectRenderer.mc;
        final Collection activePotionEffects = Minecraft.thePlayer.getActivePotionEffects();
        if (!activePotionEffects.isEmpty()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (activePotionEffects.size() > 5) {
                final int n2 = 132 / (activePotionEffects.size() - 1);
            }
            final Minecraft mc2 = InventoryEffectRenderer.mc;
            for (final PotionEffect potionEffect : Minecraft.thePlayer.getActivePotionEffects()) {
                final Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                InventoryEffectRenderer.mc.getTextureManager().bindTexture(InventoryEffectRenderer.inventoryBackground);
                this.drawTexturedModalRect(n, guiTop, 0, 166, 140, 32);
                if (potion.hasStatusIcon()) {
                    final int statusIconIndex = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(n + 6, guiTop + 7, 0 + statusIconIndex % 8 * 18, 198 + statusIconIndex / 8 * 18, 18, 18);
                }
                String s = I18n.format(potion.getName(), new Object[0]);
                if (potionEffect.getAmplifier() == 1) {
                    s = String.valueOf(s) + " " + I18n.format("enchantment.level.2", new Object[0]);
                }
                else if (potionEffect.getAmplifier() == 2) {
                    s = String.valueOf(s) + " " + I18n.format("enchantment.level.3", new Object[0]);
                }
                else if (potionEffect.getAmplifier() == 3) {
                    s = String.valueOf(s) + " " + I18n.format("enchantment.level.4", new Object[0]);
                }
                this.fontRendererObj.func_175063_a(s, (float)(n + 10 + 18), (float)(guiTop + 6), 16777215);
                this.fontRendererObj.func_175063_a(Potion.getDurationString(potionEffect), (float)(n + 10 + 18), (float)(guiTop + 6 + 10), 8355711);
                guiTop += 33;
            }
        }
    }
    
    static {
        __OBFID = "CL_00000755";
    }
}
