package wdl.gui;

import wdl.*;
import wdl.chan.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.client.audio.*;
import com.google.common.collect.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;

public class GuiWDLChunkOverrides extends GuiScreen
{
    private static final ResourceLocation WIDGET_TEXTURES;
    private final GuiScreen parent;
    private GuiButton startDownloadButton;
    private float scrollX;
    private float scrollZ;
    private Mode mode;
    private boolean partiallyRequested;
    private int requestStartX;
    private int requestStartZ;
    private int requestEndX;
    private int requestEndZ;
    private boolean dragging;
    private int lastTickX;
    private int lastTickY;
    private static int[] $SWITCH_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode;
    
    static {
        WIDGET_TEXTURES = new ResourceLocation("wdl:textures/permission_widgets.png");
    }
    
    public GuiWDLChunkOverrides(final GuiScreen parent) {
        this.mode = Mode.PANNING;
        this.parent = parent;
        if (WDL.thePlayer != null) {
            this.scrollX = (float)WDL.thePlayer.chunkCoordX;
            this.scrollZ = (float)WDL.thePlayer.chunkCoordZ;
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new RequestModeButton(0, GuiWDLChunkOverrides.width / 2 - 155, 18, Mode.PANNING));
        this.buttonList.add(new RequestModeButton(1, GuiWDLChunkOverrides.width / 2 - 130, 18, Mode.REQUESTING));
        this.buttonList.add(new RequestModeButton(this, 2, GuiWDLChunkOverrides.width / 2 - 105, 18, Mode.ERASING) {
            final GuiWDLChunkOverrides this$0;
            
            {
                this.enabled = false;
            }
        });
        this.buttonList.add(new RequestModeButton(this, 3, GuiWDLChunkOverrides.width / 2 - 80, 18, Mode.MOVING) {
            final GuiWDLChunkOverrides this$0;
            
            {
                this.enabled = false;
            }
        });
        this.buttonList.add(new GuiButton(4, GuiWDLChunkOverrides.width / 2 - 80, 18, 80, 20, "Send request"));
        this.startDownloadButton = new GuiButton(6, GuiWDLChunkOverrides.width / 2 + 5, 18, 150, 20, "Start download in these ranges");
        this.startDownloadButton.enabled = WDLPluginChannels.canDownloadAtAll();
        this.buttonList.add(this.startDownloadButton);
        this.buttonList.add(new GuiButton(100, GuiWDLChunkOverrides.width / 2 - 100, GuiWDLChunkOverrides.height - 29, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiWDLChunkOverrides.width / 2 - 155, 39, 100, 20, I18n.format("wdl.gui.permissions.current", new Object[0])));
        this.buttonList.add(new GuiButton(201, GuiWDLChunkOverrides.width / 2 - 50, 39, 100, 20, I18n.format("wdl.gui.permissions.request", new Object[0])));
        this.buttonList.add(new GuiButton(202, GuiWDLChunkOverrides.width / 2 + 55, 39, 100, 20, I18n.format("wdl.gui.permissions.overrides", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mode = Mode.PANNING;
        }
        if (guiButton.id == 1) {
            this.mode = Mode.REQUESTING;
            this.partiallyRequested = false;
        }
        if (guiButton.id == 4) {
            WDLPluginChannels.sendRequests("Request reason not yet implemented clientside");
        }
        if (guiButton.id == 6 && !WDLPluginChannels.canDownloadAtAll()) {
            guiButton.enabled = false;
            return;
        }
        if (guiButton.id == 100) {
            GuiWDLChunkOverrides.mc.displayGuiScreen(this.parent);
        }
        if (guiButton.id == 200) {
            GuiWDLChunkOverrides.mc.displayGuiScreen(new GuiWDLPermissions(this.parent));
        }
        if (guiButton.id == 201) {
            GuiWDLChunkOverrides.mc.displayGuiScreen(new GuiWDLPermissionRequest(this.parent));
        }
        final int id = guiButton.id;
    }
    
    @Override
    protected void mouseClicked(final int lastTickX, final int lastTickY, final int n) throws IOException {
        super.mouseClicked(lastTickX, lastTickY, n);
        if (lastTickY > 61 && lastTickY < GuiWDLChunkOverrides.height - 32 && n == 0) {
            switch ($SWITCH_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode()[this.mode.ordinal()]) {
                case 1: {
                    this.dragging = true;
                    this.lastTickX = lastTickX;
                    this.lastTickY = lastTickY;
                    break;
                }
                case 2: {
                    if (this.partiallyRequested) {
                        this.requestEndX = this.displayXToChunkX(lastTickX);
                        this.requestEndZ = this.displayZToChunkZ(lastTickY);
                        WDLPluginChannels.addChunkOverrideRequest(new WDLPluginChannels.ChunkRange("", this.requestStartX, this.requestStartZ, this.requestEndX, this.requestEndZ));
                        this.partiallyRequested = false;
                    }
                    else {
                        this.requestStartX = this.displayXToChunkX(lastTickX);
                        this.requestStartZ = this.displayZToChunkZ(lastTickY);
                        this.partiallyRequested = true;
                    }
                    final Minecraft mc = GuiWDLChunkOverrides.mc;
                    Minecraft.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0f));
                    break;
                }
            }
        }
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
        if (n3 == 0) {
            this.dragging = false;
        }
    }
    
    @Override
    protected void mouseClickMove(final int lastTickX, final int lastTickY, final int n, final long n2) {
        if (this.dragging) {
            final int n3 = this.lastTickX - lastTickX;
            final int n4 = this.lastTickY - lastTickY;
            this.lastTickX = lastTickX;
            this.lastTickY = lastTickY;
            this.scrollX += n3 / 8.0f;
            this.scrollZ += n4 / 8.0f;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Utils.drawDarkBackground(0, 0, GuiWDLChunkOverrides.height, GuiWDLChunkOverrides.width);
        if (this.mode == Mode.REQUESTING) {
            this.drawRange(new WDLPluginChannels.ChunkRange("", this.partiallyRequested ? this.requestStartX : this.displayXToChunkX(n), this.partiallyRequested ? this.requestStartZ : this.displayZToChunkZ(n2), this.displayXToChunkX(n), this.displayZToChunkZ(n2)), 16777215, 127 + (int)(Math.sin(Minecraft.getSystemTime() * 3.141592653589793 / 5000.0) * 64.0));
        }
        final Iterator<Multimap> iterator = WDLPluginChannels.getChunkOverrides().values().iterator();
        while (iterator.hasNext()) {
            final Iterator iterator2 = iterator.next().values().iterator();
            while (iterator2.hasNext()) {
                this.drawRange(iterator2.next(), 769532, 255);
            }
        }
        final Iterator<WDLPluginChannels.ChunkRange> iterator3 = WDLPluginChannels.getChunkOverrideRequests().iterator();
        while (iterator3.hasNext()) {
            this.drawRange(iterator3.next(), 8421504, 127 + (int)(Math.sin(Minecraft.getSystemTime() * 3.141592653589793 / 5000.0) * 64.0));
        }
        final int n4 = (int)((WDL.thePlayer.posX / 16.0 - this.scrollX) * 8.0 + GuiWDLChunkOverrides.width / 2);
        final int n5 = (int)((WDL.thePlayer.posZ / 16.0 - this.scrollZ) * 8.0 + GuiWDLChunkOverrides.height / 2);
        this.drawHorizontalLine(n4 - 3, n4 + 3, n5, -1);
        this.drawVerticalLine(n4, n5 - 4, n5 + 4, -1);
        Utils.drawBorder(61, 32, 0, 0, GuiWDLChunkOverrides.height, GuiWDLChunkOverrides.width);
        Gui.drawCenteredString(this.fontRendererObj, "Chunk overrides", GuiWDLChunkOverrides.width / 2, 8, 16777215);
        super.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, "§c§lThis is a work in progress.", GuiWDLChunkOverrides.width / 2, GuiWDLChunkOverrides.height / 2, 16777215);
    }
    
    private void drawRange(final WDLPluginChannels.ChunkRange chunkRange, final int n, final int n2) {
        final int n3 = (chunkRange.tag.hashCode() ^ n) & 0xFFFFFF;
        final int chunkXToDisplayX = this.chunkXToDisplayX(chunkRange.x1);
        final int chunkZToDisplayZ = this.chunkZToDisplayZ(chunkRange.z1);
        final int n4 = this.chunkXToDisplayX(chunkRange.x2) + 8 - 1;
        final int n5 = this.chunkZToDisplayZ(chunkRange.z2) + 8 - 1;
        Gui.drawRect(chunkXToDisplayX, chunkZToDisplayZ, n4, n5, n3 + (n2 << 24));
        final int darken = this.darken(n3);
        this.drawVerticalLine(chunkXToDisplayX, chunkZToDisplayZ, n5, darken + (n2 << 24));
        this.drawVerticalLine(n4, chunkZToDisplayZ, n5, darken + (n2 << 24));
        this.drawHorizontalLine(chunkXToDisplayX, n4, chunkZToDisplayZ, darken + (n2 << 24));
        this.drawHorizontalLine(chunkXToDisplayX, n4, n5, darken + (n2 << 24));
    }
    
    private int chunkXToDisplayX(final int n) {
        return (int)((n - this.scrollX) * 8.0f + GuiWDLChunkOverrides.width / 2);
    }
    
    private int chunkZToDisplayZ(final int n) {
        return (int)((n - this.scrollZ) * 8.0f + GuiWDLChunkOverrides.height / 2);
    }
    
    private int displayXToChunkX(final int n) {
        return MathHelper.floor_float((n - (float)(GuiWDLChunkOverrides.width / 2)) / 8.0f + this.scrollX);
    }
    
    private int displayZToChunkZ(final int n) {
        return MathHelper.floor_float((n - (float)(GuiWDLChunkOverrides.height / 2)) / 8.0f + this.scrollZ);
    }
    
    private int darken(final int n) {
        return ((n >> 16 & 0xFF) / 2 << 16) + ((n >> 8 & 0xFF) / 2 << 8) + (n & 0xFF) / 2;
    }
    
    static Mode access$0(final GuiWDLChunkOverrides guiWDLChunkOverrides) {
        return guiWDLChunkOverrides.mode;
    }
    
    static ResourceLocation access$1() {
        return GuiWDLChunkOverrides.WIDGET_TEXTURES;
    }
    
    static int[] $SWITCH_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode() {
        final int[] $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode = GuiWDLChunkOverrides.$SWITCH_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode;
        if ($switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode != null) {
            return $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode;
        }
        final int[] $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode2 = new int[Mode.values().length];
        $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode2[Mode.ERASING.ordinal()] = 3;
        $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode2[Mode.MOVING.ordinal()] = 4;
        $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode2[Mode.PANNING.ordinal()] = 1;
        $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode2[Mode.REQUESTING.ordinal()] = 2;
        return GuiWDLChunkOverrides.$SWITCH_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode = $switch_TABLE$wdl$gui$GuiWDLChunkOverrides$Mode2;
    }
    
    private enum Mode
    {
        PANNING("PANNING", 0, 0, 128), 
        REQUESTING("REQUESTING", 1, 16, 128), 
        ERASING("ERASING", 2, 32, 128), 
        MOVING("MOVING", 3, 48, 128);
        
        public final int overlayU;
        public final int overlayV;
        private static final Mode[] ENUM$VALUES;
        
        static {
            ENUM$VALUES = new Mode[] { Mode.PANNING, Mode.REQUESTING, Mode.ERASING, Mode.MOVING };
        }
        
        private Mode(final String s, final int n, final int overlayU, final int overlayV) {
            this.overlayU = overlayU;
            this.overlayV = overlayV;
        }
    }
    
    private class RequestModeButton extends GuiButton
    {
        public final Mode mode;
        final GuiWDLChunkOverrides this$0;
        
        public RequestModeButton(final GuiWDLChunkOverrides this$0, final int n, final int n2, final int n3, final Mode mode) {
            this.this$0 = this$0;
            super(n, n2, n3, 20, 20, "");
            this.mode = mode;
        }
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (GuiWDLChunkOverrides.access$0(this.this$0) == this.mode) {
                Gui.drawRect(this.xPosition - 2, this.yPosition - 2, this.xPosition + this.width + 2, this.yPosition + this.height + 2, -16744704);
            }
            super.drawButton(minecraft, n, n2);
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            minecraft.getTextureManager().bindTexture(GuiWDLChunkOverrides.access$1());
            this.drawTexturedModalRect(this.xPosition + 2, this.yPosition + 2, this.mode.overlayU, this.mode.overlayV, 16, 16);
        }
    }
}
