package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.spectator.categories.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.spectator.*;
import net.minecraft.client.settings.*;

public class GuiSpectator extends Gui implements ISpectatorMenuReciepient
{
    private static final ResourceLocation field_175267_f;
    public static final ResourceLocation field_175269_a;
    private final Minecraft field_175268_g;
    private long field_175270_h;
    private SpectatorMenu field_175271_i;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001940";
        field_175267_f = new ResourceLocation("textures/gui/widgets.png");
        field_175269_a = new ResourceLocation("textures/gui/spectator_widgets.png");
    }
    
    public GuiSpectator(final Minecraft field_175268_g) {
        this.field_175268_g = field_175268_g;
    }
    
    public void func_175260_a(final int n) {
        this.field_175270_h = Minecraft.getSystemTime();
        if (this.field_175271_i != null) {
            this.field_175271_i.func_178644_b(n);
        }
        else {
            this.field_175271_i = new SpectatorMenu(this);
        }
    }
    
    private float func_175265_c() {
        return MathHelper.clamp_float((this.field_175270_h - Minecraft.getSystemTime() + 5000L) / 2000.0f, 0.0f, 1.0f);
    }
    
    public void func_175264_a(final ScaledResolution scaledResolution, final float n) {
        if (this.field_175271_i != null) {
            final float func_175265_c = this.func_175265_c();
            if (func_175265_c <= 0.0f) {
                this.field_175271_i.func_178641_d();
            }
            else {
                final int n2 = ScaledResolution.getScaledWidth() / 2;
                final float zLevel = this.zLevel;
                this.zLevel = -90.0f;
                this.func_175258_a(scaledResolution, func_175265_c, n2, ScaledResolution.getScaledHeight() - 22.0f * func_175265_c, this.field_175271_i.func_178646_f());
                this.zLevel = zLevel;
            }
        }
    }
    
    protected void func_175258_a(final ScaledResolution scaledResolution, final float n, final int n2, final float n3, final SpectatorDetails spectatorDetails) {
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
        this.field_175268_g.getTextureManager().bindTexture(GuiSpectator.field_175267_f);
        this.func_175174_a((float)(n2 - 91), n3, 0, 0, 182, 22);
        if (spectatorDetails.func_178681_b() >= 0) {
            this.func_175174_a((float)(n2 - 91 - 1 + spectatorDetails.func_178681_b() * 20), n3 - 1.0f, 0, 22, 24, 22);
        }
        while (0 < 9) {
            this.func_175266_a(0, ScaledResolution.getScaledWidth() / 2 - 90 + 0 + 2, n3 + 3.0f, n, spectatorDetails.func_178680_a(0));
            int n4 = 0;
            ++n4;
        }
    }
    
    private void func_175266_a(final int n, final int n2, final float n3, final float n4, final ISpectatorMenuObject spectatorMenuObject) {
        this.field_175268_g.getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        if (spectatorMenuObject != SpectatorMenu.field_178657_a) {
            final int n5 = (int)(n4 * 255.0f);
            GlStateManager.translate((float)n2, n3, 0.0f);
            final float n6 = spectatorMenuObject.func_178662_A_() ? 1.0f : 0.25f;
            GlStateManager.color(n6, n6, n6, n4);
            spectatorMenuObject.func_178663_a(n6, n5);
            final String value = String.valueOf(GameSettings.getKeyDisplayString(this.field_175268_g.gameSettings.keyBindsHotbar[n].getKeyCode()));
            if (n5 > 3 && spectatorMenuObject.func_178662_A_()) {
                Minecraft.fontRendererObj.func_175063_a(value, (float)(n2 + 19 - 2 - Minecraft.fontRendererObj.getStringWidth(value)), n3 + 6.0f + 3.0f, 16777215 + (n5 << 24));
            }
        }
    }
    
    public void func_175263_a(final ScaledResolution scaledResolution) {
        final int n = (int)(this.func_175265_c() * 255.0f);
        if (n > 3 && this.field_175271_i != null) {
            final ISpectatorMenuObject func_178645_b = this.field_175271_i.func_178645_b();
            final String s = (func_178645_b != SpectatorMenu.field_178657_a) ? func_178645_b.func_178664_z_().getFormattedText() : this.field_175271_i.func_178650_c().func_178670_b().getFormattedText();
            if (s != null) {
                final int n2 = (ScaledResolution.getScaledWidth() - Minecraft.fontRendererObj.getStringWidth(s)) / 2;
                final int n3 = ScaledResolution.getScaledHeight() - 35;
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                Minecraft.fontRendererObj.func_175063_a(s, (float)n2, (float)n3, 16777215 + (n << 24));
            }
        }
    }
    
    @Override
    public void func_175257_a(final SpectatorMenu spectatorMenu) {
        this.field_175271_i = null;
        this.field_175270_h = 0L;
    }
    
    public boolean func_175262_a() {
        return this.field_175271_i != null;
    }
    
    public void func_175259_b(final int n) {
        int n2;
        for (n2 = this.field_175271_i.func_178648_e() + n; n2 >= 0 && n2 <= 8 && (this.field_175271_i.func_178643_a(n2) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(n2).func_178662_A_()); n2 += n) {}
        if (n2 >= 0 && n2 <= 8) {
            this.field_175271_i.func_178644_b(n2);
            this.field_175270_h = Minecraft.getSystemTime();
        }
    }
    
    public void func_175261_b() {
        this.field_175270_h = Minecraft.getSystemTime();
        if (this.func_175262_a()) {
            final int func_178648_e = this.field_175271_i.func_178648_e();
            if (func_178648_e != -1) {
                this.field_175271_i.func_178644_b(func_178648_e);
            }
        }
        else {
            this.field_175271_i = new SpectatorMenu(this);
        }
    }
}
