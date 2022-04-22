package net.minecraft.client.gui.spectator;

import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class PlayerMenuObject implements ISpectatorMenuObject
{
    private final GameProfile field_178668_a;
    private final ResourceLocation field_178667_b;
    private static final String __OBFID;
    
    public PlayerMenuObject(final GameProfile field_178668_a) {
        this.field_178668_a = field_178668_a;
        AbstractClientPlayer.getDownloadImageSkin(this.field_178667_b = AbstractClientPlayer.getLocationSkin(field_178668_a.getName()), field_178668_a.getName());
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C18PacketSpectate(this.field_178668_a.getId()));
    }
    
    @Override
    public IChatComponent func_178664_z_() {
        return new ChatComponentText(this.field_178668_a.getName());
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178667_b);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n2 / 255.0f);
        Gui.drawScaledCustomSizeModalRect(2, 2, 8.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(2, 2, 40.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        return true;
    }
    
    static {
        __OBFID = "CL_00001929";
    }
}
