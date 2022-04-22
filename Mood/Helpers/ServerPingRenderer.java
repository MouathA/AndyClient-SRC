package Mood.Helpers;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;
import com.google.common.base.*;
import io.netty.handler.codec.base64.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.renderer.texture.*;
import io.netty.buffer.*;
import java.awt.image.*;
import net.minecraft.client.multiplayer.*;
import java.net.*;

public class ServerPingRenderer implements MinecraftHook
{
    private boolean directConnectAutoPing;
    private final ResourceLocation serverIcon;
    private final ArrayList players;
    private final ArrayList maxPlayers;
    public ServerInfos serverInfos;
    public String hoveringText;
    private final OldServerPinger serverPinger;
    private ServerData serverData;
    private DynamicTexture field_148305_h;
    private String field_148299_g;
    private String lastServer;
    private int highestPlayers;
    private int lowestPlayers;
    public int time;
    public int clientSideVersion;
    
    public ServerPingRenderer() {
        this.directConnectAutoPing = true;
        this.clientSideVersion = 47;
        this.players = new ArrayList();
        this.maxPlayers = new ArrayList();
        this.serverPinger = new OldServerPinger();
        this.highestPlayers = 0;
        this.lowestPlayers = Integer.MAX_VALUE;
        this.time = 549;
        this.serverIcon = new ResourceLocation("servers/icon");
        this.field_148305_h = (DynamicTexture)ServerPingRenderer.MC.getTextureManager().getTexture(this.serverIcon);
    }
    
    public void render(final int n, final int n2, final int n3, final boolean b, final String s) {
        new ScaledResolution();
        final int n4 = ScaledResolution.getScaledWidth() / 2 - 150;
        Gui.drawRect(n4 - 2, n3 - 2, n4 + 295, n3 + 34, Integer.MIN_VALUE);
        Gui.drawRect(n4 - 2, n3 - 7, n4 + 295, n3 - 3, Integer.MIN_VALUE);
        RenderUtils.renderRainbowRect(n4 + (550 - this.time - 50) / 2 + 23, n3 - 7, n4 + 275 + 20, n3 - 3, 0.5, 10L, 1L, RenderUtils.RainbowDirection.RIGHT);
        int time2;
        if (this.time < 550) {
            final int time = this.time;
            final Minecraft mc = ServerPingRenderer.MC;
            time2 = (this.time = time + ((Minecraft.theWorld == null) ? 3 : 1));
        }
        else {
            time2 = 0;
        }
        this.time = time2;
        if (this.time >= 550) {
            new Thread(this::lambda$0).start();
            this.time = 0;
        }
        else {
            this.time = 550;
        }
        final boolean b2 = this.serverInfos.getVersion() > this.clientSideVersion;
        final boolean b3 = this.serverInfos.getVersion() < this.clientSideVersion;
        final boolean b4 = b2 || b3;
        final Minecraft mc2 = ServerPingRenderer.MC;
        while (0 < Math.min(Minecraft.fontRendererObj.listFormattedStringToWidth(this.serverInfos.getServerMotd(), 271).size(), 2)) {
            int n5 = 0;
            ++n5;
        }
        if (this.serverData.pingToServer != -1L && this.serverData.pingToServer != -2L) {
            final Minecraft mc3 = ServerPingRenderer.MC;
            Minecraft.fontRendererObj.drawString("§6Protocol:§7 " + this.serverData.version, n4 + 298, n3 + 2, 16777215);
            final Minecraft mc4 = ServerPingRenderer.MC;
            Minecraft.fontRendererObj.drawString("§6Verzi\u00f3:§7 " + this.serverData.gameVersion, n4 + 298, n3 + 12, 16777215);
            final Minecraft mc5 = ServerPingRenderer.MC;
            Minecraft.fontRendererObj.drawString("§6Ping:§7 " + this.serverData.pingToServer + "ms", n4 + 298, n3 + 22, 16777215);
        }
        if (b4) {
            new StringBuilder().append(EnumChatFormatting.DARK_RED).append(this.serverInfos.getGameVersion()).toString();
        }
        else {
            this.serverInfos.getPopulationInfo();
        }
        this.hoveringText = ((n > n4 + 280 + 15 && n < n4 + 280 - 54 && n2 > n3 - 3 && n2 < n3 + 10) ? this.serverInfos.getPlayerList() : null);
        if (b4) {
            final String s2 = b2 ? "Client out of date!" : "Server out of date!";
            this.serverInfos.getPlayerList();
        }
        else if (this.serverInfos.getPingToServer() != -2L) {
            final int n6 = (this.serverInfos.getPingToServer() < 150L) ? 0 : ((this.serverInfos.getPingToServer() < 300L) ? 1 : ((this.serverInfos.getPingToServer() < 600L) ? 2 : ((this.serverInfos.getPingToServer() < 1000L) ? 3 : 4)));
            if (this.serverInfos.getPingToServer() >= 0L) {
                new StringBuilder().append(this.serverInfos.getPingToServer()).append("ms").toString();
                this.serverInfos.getPlayerList();
            }
        }
        else {
            final int n7 = (int)(Minecraft.getSystemTime() / 100L + 2L & 0x7L);
            if (5 > 4) {}
        }
        if (this.serverInfos.getServerMotd().equals(EnumChatFormatting.DARK_RED + "Can't resolve hostname") || this.serverInfos.getServerMotd().equals(EnumChatFormatting.DARK_RED + "Can't connect to server.")) {}
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        ServerPingRenderer.MC.getTextureManager().bindTexture(Gui.icons);
        Gui.drawModalRectWithCustomSizedTexture(n4 + 305 - 23, n3, 10, 216, 10, 8, 256.0f, 256.0f);
        if (this.serverInfos.getServerIcon() != null && !this.serverInfos.getServerIcon().equals(this.field_148299_g)) {
            this.field_148299_g = this.serverInfos.getServerIcon();
            this.prepareServerIcon();
        }
        if (this.field_148305_h == null) {
            this.drawTextureAt(n4, n3, new ResourceLocation("textures/misc/unknown_server.png"));
        }
        this.renderHosting();
        this.renderPlayer();
    }
    
    private boolean isDirectConnectAutoPing() {
        return false;
    }
    
    private void renderHosting() {
        final int size = this.serverInfos.getHosting().getResults().size();
        double n = 1.0;
        int n2 = 0;
        while (20 < size) {
            n += 10.5;
            ++n2;
        }
        Gui.drawRect(5, 120, 5 + MiscUtils.getLongestString(this.serverInfos.getHosting().getResults()) * 6, 120 + (int)n - 4 + 26, Integer.MIN_VALUE);
        for (final String s : this.serverInfos.getHosting().getResults()) {
            Minecraft.getInstance();
            Minecraft.fontRendererObj.drawString(s, 12, 142, -1);
            n2 += 10;
        }
    }
    
    private void renderPlayer() {
        Minecraft.getInstance();
        final float n = Minecraft.fontRendererObj.getStringWidth("§7" + Math.max(1, this.highestPlayers)) + 2.0f;
        new ScaledResolution();
        final float n2 = (float)(ScaledResolution.getScaledHeight() - 60);
        Gui.drawRect((int)n, (int)n2, (int)(n + 250.0f), (int)(n2 + 55.0f), Integer.MIN_VALUE);
        Gui.drawRect((int)n, (int)(n2 + 27.5f - 1.0f), (int)(n + 250.0f), (int)(n2 + 27.5f), new Color(Color.GRAY.getRed(), Color.GRAY.getGreen(), Color.GRAY.getBlue(), 100).getRGB());
        final Minecraft mc = ServerPingRenderer.MC;
        final FontRenderer fontRendererObj = Minecraft.fontRendererObj;
        final String string = "§a" + Math.max(1, this.highestPlayers);
        final float n3 = n;
        Minecraft.getInstance();
        fontRendererObj.drawStringWithShadow(string, n3 - Minecraft.fontRendererObj.getStringWidth("§7" + Math.max(1, this.highestPlayers)), n2 + 1.0f, Color.WHITE.getRGB());
        final Minecraft mc2 = ServerPingRenderer.MC;
        final FontRenderer fontRendererObj2 = Minecraft.fontRendererObj;
        final String string2 = "§c" + ((Math.max(0, this.lowestPlayers - 20) == 2147483627) ? "0" : Integer.valueOf(Math.max(0, this.lowestPlayers - 20)));
        final int n4 = (int)n;
        Minecraft.getInstance();
        fontRendererObj2.drawStringWithShadow(string2, (float)(n4 - Minecraft.fontRendererObj.getStringWidth("§c" + ((Math.max(0, this.lowestPlayers - 20) == 2147483627) ? "0" : Integer.valueOf(Math.max(0, this.lowestPlayers - 20))))), (float)((int)(n2 + 55.0f - 5.0f) - 4), Color.WHITE.getRGB());
        float n5 = 0.0f;
        int n7 = 0;
        if (!this.players.isEmpty()) {
            float n6 = 0.0f;
            while (0 < this.players.size()) {
                n6 += this.players.get(0);
                ++n7;
            }
            n5 = n6 / this.players.size();
        }
        final String format = String.format("%.2f", n5);
        final Minecraft mc3 = ServerPingRenderer.MC;
        Minecraft.fontRendererObj.drawStringWithShadow("§e\u00f8" + format, (float)((int)(n + 250.0f) + 1), (int)n2 + 27.5f - 5.0f, Color.WHITE.getRGB());
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4d(1.0, 0.0, 0.0, 1.0);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        if (!this.lastServer.equals(this.serverInfos.getServerIP())) {
            this.maxPlayers.clear();
            this.players.clear();
            this.highestPlayers = 0;
            this.lowestPlayers = Integer.MAX_VALUE;
        }
        double n8 = this.players.size() * 2.5;
        while (n8 >= 250.0) {
            this.players.remove(0);
            this.maxPlayers.remove(0);
            n8 = this.players.size() * 2.5;
            this.highestPlayers = 0;
            while (0 < this.players.size()) {
                if (this.players.get(0) > this.highestPlayers) {
                    this.highestPlayers = this.players.get(0);
                }
                if (this.players.get(0) < this.lowestPlayers) {
                    this.lowestPlayers = this.players.get(0);
                }
                int n9 = 0;
                ++n9;
            }
        }
        while (0 < this.players.size()) {
            final float n10 = (float)(Math.max(1, this.highestPlayers) + 20);
            final float min = Math.min(n10, this.players.get(0));
            final float n11 = (float)Math.max(0, this.lowestPlayers - 20);
            GL11.glVertex2d(n + 2.5 + 0 * 2.5, n2 + 55.0f - 2.0 - (min - n11) / (n10 - n11) * 55.0f);
            ++n7;
        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glScaled(0.5, 0.5, 0.5);
        final float n12 = (float)(Math.max(1, this.highestPlayers) + 20);
        final float n13 = (float)Math.max(0, this.lowestPlayers - 20);
    }
    
    protected void drawTextureAt(final int n, final int n2, final ResourceLocation resourceLocation) {
        ServerPingRenderer.MC.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(n, n2, 0.0f, 0.0f, 32, 32, 32.0f, 32.0f);
    }
    
    private void prepareServerIcon() {
        if (this.serverInfos.getServerIcon() == null) {
            ServerPingRenderer.MC.getTextureManager().deleteTexture(this.serverIcon);
            this.field_148305_h = null;
        }
        else {
            final ByteBuf copiedBuffer = Unpooled.copiedBuffer(this.serverInfos.getServerIcon(), Charsets.UTF_8);
            final ByteBuf decode = Base64.decode(copiedBuffer);
            final BufferedImage bufferedImage = TextureUtil.readBufferedImage(new ByteBufInputStream(decode));
            Validate.validState(bufferedImage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
            Validate.validState(bufferedImage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
            copiedBuffer.release();
            decode.release();
            if (this.field_148305_h == null) {
                this.field_148305_h = new DynamicTexture(bufferedImage.getWidth(), bufferedImage.getHeight());
                ServerPingRenderer.MC.getTextureManager().loadTexture(this.serverIcon, this.field_148305_h);
            }
            bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.field_148305_h.getTextureData(), 0, bufferedImage.getWidth());
            this.field_148305_h.updateDynamicTexture();
        }
    }
    
    private void lambda$0(final String s, final boolean b) {
        String hostAddress = s;
        if (b) {
            hostAddress = InetAddress.getByName(ServerAddress.fromString(s).getIP()).getHostAddress();
        }
        this.lastServer = hostAddress;
        this.serverData = new ServerData("DirectConnect", hostAddress, false);
        this.serverPinger.ping(this.serverData, this::lambda$1);
    }
    
    private void lambda$1() {
        this.serverInfos = new ServerInfos(this.serverData.serverIP, this.serverData.serverMOTD, this.serverData.populationInfo, this.serverData.gameVersion, this.serverData.playerList, this.serverData.getBase64EncodedIconData(), this.serverData.field_78841_f, this.serverData.version, this.serverData.pingToServer);
        this.players.add(Integer.valueOf(StringUtils.stripControlCodes(this.serverInfos.getPopulationInfo()).substring(0, StringUtils.stripControlCodes(this.serverInfos.getPopulationInfo()).lastIndexOf("/"))));
        this.maxPlayers.add(Integer.valueOf(StringUtils.stripControlCodes(this.serverInfos.getPopulationInfo()).substring(StringUtils.stripControlCodes(this.serverInfos.getPopulationInfo()).lastIndexOf("/") + 1)));
        while (0 < this.players.size()) {
            if (this.players.get(0) > this.highestPlayers) {
                this.highestPlayers = this.players.get(0);
            }
            if (this.players.get(0) < this.lowestPlayers) {
                this.lowestPlayers = this.players.get(0);
            }
            int n = 0;
            ++n;
        }
    }
}
