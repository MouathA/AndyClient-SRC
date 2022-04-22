package net.minecraft.client.gui;

import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.renderer.*;

public class GuiWinGame extends GuiScreen
{
    private static final Logger logger;
    private static final ResourceLocation field_146576_f;
    private static final ResourceLocation field_146577_g;
    private int field_146581_h;
    private List field_146582_i;
    private int field_146579_r;
    private float field_146578_s;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000719";
        logger = LogManager.getLogger();
        field_146576_f = new ResourceLocation("textures/gui/title/minecraft.png");
        field_146577_g = new ResourceLocation("textures/misc/vignette.png");
    }
    
    public GuiWinGame() {
        this.field_146578_s = 0.5f;
    }
    
    @Override
    public void updateScreen() {
        ++this.field_146581_h;
        if (this.field_146581_h > (this.field_146579_r + GuiWinGame.height + GuiWinGame.height + 24) / this.field_146578_s) {
            this.sendRespawnPacket();
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == 1) {
            this.sendRespawnPacket();
        }
    }
    
    private void sendRespawnPacket() {
        final Minecraft mc = GuiWinGame.mc;
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
        GuiWinGame.mc.displayGuiScreen(null);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    @Override
    public void initGui() {
        if (this.field_146582_i == null) {
            this.field_146582_i = Lists.newArrayList();
            final String string = new StringBuilder().append(EnumChatFormatting.WHITE).append(EnumChatFormatting.OBFUSCATED).append(EnumChatFormatting.GREEN).append(EnumChatFormatting.AQUA).toString();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(GuiWinGame.mc.getResourceManager().getResource(new ResourceLocation("texts/end.txt")).getInputStream(), Charsets.UTF_8));
            final Random random = new Random(8124371L);
            String line;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                final String s = line;
                final String s2 = "PLAYERNAME";
                final Minecraft mc = GuiWinGame.mc;
                String s3;
                for (s3 = s.replaceAll(s2, Minecraft.getSession().getUsername()); s3.contains(string); s3 = String.valueOf(s3.substring(0, 0)) + EnumChatFormatting.WHITE + EnumChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, random.nextInt(4) + 3) + s3.substring(0 + string.length())) {
                    index = s3.indexOf(string);
                }
                final List field_146582_i = this.field_146582_i;
                final Minecraft mc2 = GuiWinGame.mc;
                field_146582_i.addAll(Minecraft.fontRendererObj.listFormattedStringToWidth(s3, 274));
                this.field_146582_i.add("");
            }
            while (0 < 8) {
                this.field_146582_i.add("");
                ++index;
            }
            String line2;
            while ((line2 = new BufferedReader(new InputStreamReader(GuiWinGame.mc.getResourceManager().getResource(new ResourceLocation("texts/credits.txt")).getInputStream(), Charsets.UTF_8)).readLine()) != null) {
                final String s4 = line2;
                final String s5 = "PLAYERNAME";
                final Minecraft mc3 = GuiWinGame.mc;
                final String replaceAll = s4.replaceAll(s5, Minecraft.getSession().getUsername()).replaceAll("\t", "    ");
                final List field_146582_i2 = this.field_146582_i;
                final Minecraft mc4 = GuiWinGame.mc;
                field_146582_i2.addAll(Minecraft.fontRendererObj.listFormattedStringToWidth(replaceAll, 274));
                this.field_146582_i.add("");
            }
            this.field_146579_r = this.field_146582_i.size() * 12;
        }
    }
    
    private void drawWinGameScreen(final int n, final int n2, final float n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GuiWinGame.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178960_a(1.0f, 1.0f, 1.0f, 1.0f);
        final int width = GuiWinGame.width;
        final float n4 = 0.0f - (this.field_146581_h + n3) * 0.5f * this.field_146578_s;
        final float n5 = GuiWinGame.height - (this.field_146581_h + n3) * 0.5f * this.field_146578_s;
        final float n6 = 0.015625f;
        float n7 = (this.field_146581_h + n3 - 0.0f) * 0.02f;
        final float n8 = ((this.field_146579_r + GuiWinGame.height + GuiWinGame.height + 24) / this.field_146578_s - 20.0f - (this.field_146581_h + n3)) * 0.005f;
        if (n8 < n7) {
            n7 = n8;
        }
        if (n7 > 1.0f) {
            n7 = 1.0f;
        }
        final float n9 = n7 * n7 * 96.0f / 255.0f;
        worldRenderer.func_178986_b(n9, n9, n9);
        worldRenderer.addVertexWithUV(0.0, GuiWinGame.height, this.zLevel, 0.0, n4 * n6);
        worldRenderer.addVertexWithUV(width, GuiWinGame.height, this.zLevel, width * n6, n4 * n6);
        worldRenderer.addVertexWithUV(width, 0.0, this.zLevel, width * n6, n5 * n6);
        worldRenderer.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, n5 * n6);
        instance.draw();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawWinGameScreen(n, n2, n3);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final int n4 = GuiWinGame.width / 2 - 2;
        final int n5 = GuiWinGame.height + 50;
        final float n6 = -(this.field_146581_h + n3) * this.field_146578_s;
        GlStateManager.translate(0.0f, n6, 0.0f);
        GuiWinGame.mc.getTextureManager().bindTexture(GuiWinGame.field_146576_f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawTexturedModalRect(n4, n5, 0, 0, 155, 44);
        this.drawTexturedModalRect(n4 + 155, n5, 0, 45, 155, 44);
        worldRenderer.func_178991_c(16777215);
        int n7 = n5 + 200;
        while (0 < this.field_146582_i.size()) {
            if (0 == this.field_146582_i.size() - 1) {
                final float n8 = n7 + n6 - (GuiWinGame.height / 2 - 6);
                if (n8 < 0.0f) {
                    GlStateManager.translate(0.0f, -n8, 0.0f);
                }
            }
            if (n7 + n6 + 12.0f + 8.0f > 0.0f && n7 + n6 < GuiWinGame.height) {
                final String s = this.field_146582_i.get(0);
                if (s.startsWith("[C]")) {
                    this.fontRendererObj.func_175063_a(s.substring(3), (float)(n4 + (274 - this.fontRendererObj.getStringWidth(s.substring(3))) / 2), (float)n7, 16777215);
                }
                else {
                    this.fontRendererObj.fontRandom.setSeed(0 * 4238972211L + this.field_146581_h / 4);
                    this.fontRendererObj.func_175063_a(s, (float)n4, (float)n7, 16777215);
                }
            }
            n7 += 12;
            int width = 0;
            ++width;
        }
        GuiWinGame.mc.getTextureManager().bindTexture(GuiWinGame.field_146577_g);
        GlStateManager.blendFunc(0, 769);
        worldRenderer.startDrawingQuads();
        worldRenderer.func_178960_a(1.0f, 1.0f, 1.0f, 1.0f);
        int width = GuiWinGame.width;
        final int height = GuiWinGame.height;
        worldRenderer.addVertexWithUV(0.0, height, this.zLevel, 0.0, 1.0);
        worldRenderer.addVertexWithUV(0, height, this.zLevel, 1.0, 1.0);
        worldRenderer.addVertexWithUV(0, 0.0, this.zLevel, 1.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, this.zLevel, 0.0, 0.0);
        instance.draw();
        super.drawScreen(n, n2, n3);
    }
}
