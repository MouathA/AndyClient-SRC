package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.util.*;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry
{
    private static final ResourceLocation field_148316_c;
    protected final Minecraft field_148317_a;
    protected final GuiScreenResourcePacks field_148315_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000821";
        field_148316_c = new ResourceLocation("textures/gui/resource_packs.png");
    }
    
    public ResourcePackListEntry(final GuiScreenResourcePacks field_148315_b) {
        this.field_148315_b = field_148315_b;
        this.field_148317_a = Minecraft.getMinecraft();
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        this.func_148313_c();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
        if ((this.field_148317_a.gameSettings.touchscreen || b) && this.func_148310_d()) {
            this.field_148317_a.getTextureManager().bindTexture(ResourcePackListEntry.field_148316_c);
            Gui.drawRect(n2, n3, n2 + 32, n3 + 32, -1601138544);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final int n8 = n6 - n2;
            final int n9 = n7 - n3;
            if (this != 0) {
                if (n8 < 32) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            else {
                if (this.func_148308_f()) {
                    if (n8 < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 32.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 32.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this > 0) {
                    if (n8 < 32 && n8 > 16 && n9 < 16) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
                if (this >= 0) {
                    if (n8 < 32 && n8 > 16 && n9 > 16) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                    }
                }
            }
        }
        String s = this.func_148312_b();
        if (Minecraft.fontRendererObj.getStringWidth(s) > 157) {
            s = String.valueOf(Minecraft.fontRendererObj.trimStringToWidth(s, 157 - Minecraft.fontRendererObj.getStringWidth("..."))) + "...";
        }
        Minecraft.fontRendererObj.func_175063_a(s, (float)(n2 + 32 + 2), (float)(n3 + 1), 16777215);
        final List listFormattedStringToWidth = Minecraft.fontRendererObj.listFormattedStringToWidth(this.func_148311_a(), 157);
        while (0 < listFormattedStringToWidth.size()) {
            Minecraft.fontRendererObj.func_175063_a(listFormattedStringToWidth.get(0), (float)(n2 + 32 + 2), (float)(n3 + 12 + 0), 8421504);
            int n10 = 0;
            ++n10;
        }
    }
    
    protected abstract String func_148311_a();
    
    protected abstract String func_148312_b();
    
    protected abstract void func_148313_c();
    
    protected boolean func_148310_d() {
        return true;
    }
    
    protected boolean func_148308_f() {
        return this.field_148315_b.hasResourcePackEntry(this);
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (this.func_148310_d() && n5 <= 32) {
            if (this != 0) {
                this.field_148315_b.func_146962_b(this).remove(this);
                this.field_148315_b.func_146963_h().add(0, this);
                this.field_148315_b.func_175288_g();
                return true;
            }
            if (n5 < 16 && this.func_148308_f()) {
                this.field_148315_b.func_146962_b(this).remove(this);
                this.field_148315_b.func_146964_g().add(0, this);
                this.field_148315_b.func_175288_g();
                return true;
            }
            if (n5 > 16 && n6 < 16 && this > 0) {
                final List func_146962_b = this.field_148315_b.func_146962_b(this);
                final int index = func_146962_b.indexOf(this);
                func_146962_b.remove(this);
                func_146962_b.add(index - 1, this);
                this.field_148315_b.func_175288_g();
                return true;
            }
            if (n5 > 16 && n6 > 16 && this >= 0) {
                final List func_146962_b2 = this.field_148315_b.func_146962_b(this);
                final int index2 = func_146962_b2.indexOf(this);
                func_146962_b2.remove(this);
                func_146962_b2.add(index2 + 1, this);
                this.field_148315_b.func_175288_g();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
}
