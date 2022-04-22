package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.resources.*;

public class ServerListEntryLanDetected implements GuiListExtended.IGuiListEntry
{
    private final GuiMultiplayer field_148292_c;
    protected final Minecraft field_148293_a;
    protected final LanServerDetector.LanServer field_148291_b;
    private long field_148290_d;
    private static final String __OBFID;
    
    protected ServerListEntryLanDetected(final GuiMultiplayer field_148292_c, final LanServerDetector.LanServer field_148291_b) {
        this.field_148290_d = 0L;
        this.field_148292_c = field_148292_c;
        this.field_148291_b = field_148291_b;
        this.field_148293_a = Minecraft.getMinecraft();
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        Minecraft.fontRendererObj.drawString(I18n.format("lanServer.title", new Object[0]), n2 + 32 + 3, n3 + 1, 16777215);
        Minecraft.fontRendererObj.drawString(this.field_148291_b.getServerMotd(), n2 + 32 + 3, n3 + 12, 8421504);
        if (this.field_148293_a.gameSettings.hideServerAddress) {
            Minecraft.fontRendererObj.drawString(I18n.format("selectServer.hiddenAddress", new Object[0]), n2 + 32 + 3, n3 + 12 + 11, 3158064);
        }
        else {
            Minecraft.fontRendererObj.drawString(this.field_148291_b.getServerIpPort(), n2 + 32 + 3, n3 + 12 + 11, 3158064);
        }
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.field_148292_c.selectServer(n);
        if (Minecraft.getSystemTime() - this.field_148290_d < 250L) {
            this.field_148292_c.connectToSelected();
        }
        this.field_148290_d = Minecraft.getSystemTime();
        return false;
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    public LanServerDetector.LanServer getLanServer() {
        return this.field_148291_b;
    }
    
    static {
        __OBFID = "CL_00000816";
    }
}
