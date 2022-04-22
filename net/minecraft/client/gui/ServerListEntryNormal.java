package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import com.google.common.base.*;
import io.netty.handler.codec.base64.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.renderer.texture.*;
import io.netty.buffer.*;
import java.awt.image.*;

public class ServerListEntryNormal implements GuiListExtended.IGuiListEntry
{
    private static final Logger logger;
    private static final ThreadPoolExecutor field_148302_b;
    private static final ResourceLocation field_178015_c;
    private static final ResourceLocation field_178014_d;
    private final GuiMultiplayer field_148303_c;
    private final Minecraft field_148300_d;
    private final ServerData field_148301_e;
    private final ResourceLocation field_148306_i;
    private String field_148299_g;
    private final Minecraft mc;
    private DynamicTexture field_148305_h;
    private long field_148298_f;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000817";
        logger = LogManager.getLogger();
        field_148302_b = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
        field_178015_c = new ResourceLocation("textures/misc/unknown_server.png");
        field_178014_d = new ResourceLocation("textures/gui/server_selection.png");
    }
    
    protected ServerListEntryNormal(final GuiMultiplayer field_148303_c, final ServerData field_148301_e) {
        this.field_148303_c = field_148303_c;
        this.mc = Minecraft.getMinecraft();
        this.field_148301_e = field_148301_e;
        this.field_148300_d = Minecraft.getMinecraft();
        this.field_148306_i = new ResourceLocation("servers/" + field_148301_e.serverIP + "/icon");
        this.field_148305_h = (DynamicTexture)this.field_148300_d.getTextureManager().getTexture(this.field_148306_i);
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        if (!this.field_148301_e.field_78841_f) {
            this.field_148301_e.field_78841_f = true;
            this.field_148301_e.pingToServer = -2L;
            this.field_148301_e.serverMOTD = "";
            this.field_148301_e.populationInfo = "";
            ServerListEntryNormal.field_148302_b.submit(new Runnable() {
                private static final String __OBFID;
                final ServerListEntryNormal this$0;
                
                @Override
                public void run() {
                    ServerListEntryNormal.access$0(this.this$0).getOldServerPinger().ping(ServerListEntryNormal.access$1(this.this$0));
                }
                
                static {
                    __OBFID = "CL_00000818";
                }
            });
        }
        final boolean b2 = this.field_148301_e.version > 47;
        final boolean b3 = this.field_148301_e.version < 47;
        final boolean b4 = b2 || b3;
        Minecraft.fontRendererObj.drawString(this.field_148301_e.serverName, n2 + 32 + 3, n3 + 1, 16777215);
        if (this.field_148301_e.pingToServer != -1L && this.field_148301_e.pingToServer != -2L) {
            Minecraft.fontRendererObj.drawStringWithShadow("§6Verzi\u00f3: §7" + this.field_148301_e.gameVersion, n2 + 312, n3 + 12, 16777215);
            Minecraft.fontRendererObj.drawStringWithShadow("§6Ping: §7" + this.field_148301_e.pingToServer + "ms", n2 + 312, n3 + 22, 16777215);
            Minecraft.fontRendererObj.drawStringWithShadow("§6J\u00e1t\u00e9kosok: §7" + this.field_148301_e.populationInfo, n2 + 312, n3 + 2, 16777215);
        }
        Minecraft.fontRendererObj.drawString(this.field_148301_e.serverName, n2 + 32 + 3, n3 + 1, 16777215);
        final List listFormattedStringToWidth = Minecraft.fontRendererObj.listFormattedStringToWidth(this.field_148301_e.serverMOTD, n4 - 32 - 2);
        while (0 < Math.min(listFormattedStringToWidth.size(), 2)) {
            Minecraft.fontRendererObj.drawString(listFormattedStringToWidth.get(0), n2 + 32 + 3, n3 + 12 + Minecraft.fontRendererObj.FONT_HEIGHT * 0, 8421504);
            int n8 = 0;
            ++n8;
        }
        final String s = b4 ? (EnumChatFormatting.DARK_RED + this.field_148301_e.gameVersion) : this.field_148301_e.populationInfo;
        final int stringWidth = Minecraft.fontRendererObj.getStringWidth(s);
        Minecraft.fontRendererObj.drawString(s, n2 + n4 - stringWidth - 15 - 2, n3 + 1, 8421504);
        String s2 = null;
        String string;
        if (b4) {
            string = (b2 ? "Client out of date!" : "Server out of date!");
            s2 = this.field_148301_e.playerList;
        }
        else if (this.field_148301_e.field_78841_f && this.field_148301_e.pingToServer != -2L) {
            if (this.field_148301_e.pingToServer >= 0L) {
                if (this.field_148301_e.pingToServer >= 150L) {
                    if (this.field_148301_e.pingToServer >= 300L) {
                        if (this.field_148301_e.pingToServer >= 600L) {
                            if (this.field_148301_e.pingToServer < 1000L) {}
                        }
                    }
                }
            }
            if (this.field_148301_e.pingToServer < 0L) {
                string = "(no connection)";
            }
            else {
                string = String.valueOf(this.field_148301_e.pingToServer) + "ms";
                s2 = this.field_148301_e.playerList;
            }
        }
        else {
            final int n9 = (int)(Minecraft.getSystemTime() / 100L + n * 2 & 0x7L);
            string = "Pinging...";
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_148300_d.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(n2 + n4 - 15, n3, 10, 208, 10, 8, 256.0f, 256.0f);
        if (this.field_148301_e.getBase64EncodedIconData() != null && !this.field_148301_e.getBase64EncodedIconData().equals(this.field_148299_g)) {
            this.field_148299_g = this.field_148301_e.getBase64EncodedIconData();
            this.prepareServerIcon();
            this.field_148303_c.getServerList().saveServerList();
        }
        if (this.field_148305_h != null) {
            this.func_178012_a(n2, n3, this.field_148306_i);
        }
        else {
            this.func_178012_a(n2, n3, ServerListEntryNormal.field_178015_c);
        }
        final int n10 = n6 - n2;
        final int n11 = n7 - n3;
        if (n10 >= n4 - 15 && n10 <= n4 - 5 && n11 >= 0 && n11 <= 8) {
            this.field_148303_c.func_146793_a(string);
        }
        else if (n10 >= n4 - stringWidth - 15 - 2 && n10 <= n4 - 15 - 2 && n11 >= 0 && n11 <= 8) {
            this.field_148303_c.func_146793_a(s2);
        }
        if (this.field_148300_d.gameSettings.touchscreen || b) {
            this.field_148300_d.getTextureManager().bindTexture(ServerListEntryNormal.field_178014_d);
            Gui.drawRect(n2, n3, n2 + 32, n3 + 32, -1601138544);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final int n12 = n6 - n2;
            final int n13 = n7 - n3;
            if (this.func_178013_b()) {
                if (n12 < 32 && n12 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            if (this.field_148303_c.func_175392_a(this, n)) {
                if (n12 < 16 && n13 < 16) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
            if (this.field_148303_c.func_175394_b(this, n)) {
                if (n12 < 16 && n13 > 16) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 32.0f, 32, 32, 256.0f, 256.0f);
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 0.0f, 32, 32, 256.0f, 256.0f);
                }
            }
        }
    }
    
    protected void func_178012_a(final int n, final int n2, final ResourceLocation resourceLocation) {
        this.field_148300_d.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
    }
    
    private boolean func_178013_b() {
        return true;
    }
    
    private void prepareServerIcon() {
        if (this.field_148301_e.getBase64EncodedIconData() == null) {
            this.field_148300_d.getTextureManager().deleteTexture(this.field_148306_i);
            this.field_148305_h = null;
        }
        else {
            final ByteBuf copiedBuffer = Unpooled.copiedBuffer(this.field_148301_e.getBase64EncodedIconData(), Charsets.UTF_8);
            final ByteBuf decode = Base64.decode(copiedBuffer);
            final BufferedImage func_177053_a = TextureUtil.func_177053_a(new ByteBufInputStream(decode));
            Validate.validState(func_177053_a.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(func_177053_a.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            copiedBuffer.release();
            decode.release();
            if (this.field_148305_h == null) {
                this.field_148305_h = new DynamicTexture(func_177053_a.getWidth(), func_177053_a.getHeight());
                this.field_148300_d.getTextureManager().loadTexture(this.field_148306_i, this.field_148305_h);
            }
            func_177053_a.getRGB(0, 0, func_177053_a.getWidth(), func_177053_a.getHeight(), this.field_148305_h.getTextureData(), 0, func_177053_a.getWidth());
            this.field_148305_h.updateDynamicTexture();
        }
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n5 <= 32) {
            if (n5 < 32 && n5 > 16 && this.func_178013_b()) {
                this.field_148303_c.selectServer(n);
                this.field_148303_c.connectToSelected();
                return true;
            }
            if (n5 < 16 && n6 < 16 && this.field_148303_c.func_175392_a(this, n)) {
                this.field_148303_c.func_175391_a(this, n, GuiScreen.isShiftKeyDown());
                return true;
            }
            if (n5 < 16 && n6 > 16 && this.field_148303_c.func_175394_b(this, n)) {
                this.field_148303_c.func_175393_b(this, n, GuiScreen.isShiftKeyDown());
                return true;
            }
        }
        this.field_148303_c.selectServer(n);
        if (Minecraft.getSystemTime() - this.field_148298_f < 250L) {
            this.field_148303_c.connectToSelected();
        }
        this.field_148298_f = Minecraft.getSystemTime();
        return false;
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    public ServerData getServerData() {
        return this.field_148301_e;
    }
    
    static GuiMultiplayer access$0(final ServerListEntryNormal serverListEntryNormal) {
        return serverListEntryNormal.field_148303_c;
    }
    
    static ServerData access$1(final ServerListEntryNormal serverListEntryNormal) {
        return serverListEntryNormal.field_148301_e;
    }
}
