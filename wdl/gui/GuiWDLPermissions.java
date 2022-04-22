package wdl.gui;

import net.minecraft.client.resources.*;
import wdl.chan.*;
import java.io.*;
import wdl.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.gui.*;

public class GuiWDLPermissions extends GuiScreen
{
    private GuiButton reloadButton;
    private int refreshTicks;
    private final GuiScreen parent;
    private TextList list;
    
    public GuiWDLPermissions(final GuiScreen parent) {
        this.refreshTicks = -1;
        this.parent = parent;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(100, GuiWDLPermissions.width / 2 - 100, GuiWDLPermissions.height - 29, I18n.format("gui.done", new Object[0])));
        this.buttonList.add(new GuiButton(200, GuiWDLPermissions.width / 2 - 155, 39, 100, 20, I18n.format("wdl.gui.permissions.current", new Object[0])));
        if (WDLPluginChannels.canRequestPermissions()) {
            this.buttonList.add(new GuiButton(201, GuiWDLPermissions.width / 2 - 50, 39, 100, 20, I18n.format("wdl.gui.permissions.request", new Object[0])));
            this.buttonList.add(new GuiButton(202, GuiWDLPermissions.width / 2 + 55, 39, 100, 20, I18n.format("wdl.gui.permissions.overrides", new Object[0])));
        }
        this.reloadButton = new GuiButton(1, GuiWDLPermissions.width / 2 + 5, 18, 150, 20, "Reload permissions");
        this.buttonList.add(this.reloadButton);
        (this.list = new TextList(GuiWDLPermissions.mc, GuiWDLPermissions.width, GuiWDLPermissions.height, 61, 32)).addLine("§c§lThis is a work in progress.");
        if (!WDLPluginChannels.hasPermissions()) {
            return;
        }
        this.list.addBlankLine();
        if (!WDLPluginChannels.canRequestPermissions()) {
            this.list.addLine("§cThe serverside permission plugin is out of date and does support permission requests.  Please go ask a server administrator to update the plugin.");
            this.list.addBlankLine();
        }
        if (WDLPluginChannels.getRequestMessage() != null) {
            this.list.addLine("Note from the server moderators: ");
            this.list.addLine(WDLPluginChannels.getRequestMessage());
            this.list.addBlankLine();
        }
        this.list.addLine("These are your current permissions:");
        this.list.addLine("Can download: " + WDLPluginChannels.canDownloadInGeneral());
        this.list.addLine("Can save chunks as you move: " + WDLPluginChannels.canCacheChunks());
        if (!WDLPluginChannels.canCacheChunks() && WDLPluginChannels.canDownloadInGeneral()) {
            this.list.addLine("Nearby chunk save radius: " + WDLPluginChannels.getSaveRadius());
        }
        this.list.addLine("Can save entities: " + WDLPluginChannels.canSaveEntities());
        this.list.addLine("Can save tile entities: " + WDLPluginChannels.canSaveTileEntities());
        this.list.addLine("Can save containers: " + WDLPluginChannels.canSaveContainers());
        this.list.addLine("Received entity ranges: " + WDLPluginChannels.hasServerEntityRange() + " (" + WDLPluginChannels.getEntityRanges().size() + " total)");
    }
    
    @Override
    public void updateScreen() {
        if (this.refreshTicks > 0) {
            --this.refreshTicks;
        }
        else if (this.refreshTicks == 0) {
            this.initGui();
            this.refreshTicks = -1;
        }
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.list.func_148179_a(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.list.func_178039_p();
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.list.func_148181_b(n, n2, n3)) {
            return;
        }
        super.mouseReleased(n, n2, n3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 1) {
            WDL.minecraft.getNetHandler().addToSendQueue(new C17PacketCustomPayload("WDL|INIT", new PacketBuffer(Unpooled.copiedBuffer("1.8.9a-beta2".getBytes("UTF-8")))));
            guiButton.enabled = false;
            guiButton.displayString = "Refreshing...";
            this.refreshTicks = 50;
        }
        if (guiButton.id == 100) {
            GuiWDLPermissions.mc.displayGuiScreen(this.parent);
        }
        final int id = guiButton.id;
        if (guiButton.id == 201) {
            GuiWDLPermissions.mc.displayGuiScreen(new GuiWDLPermissionRequest(this.parent));
        }
        if (guiButton.id == 202) {
            GuiWDLPermissions.mc.displayGuiScreen(new GuiWDLChunkOverrides(this.parent));
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.list == null) {
            return;
        }
        this.list.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, "Permission info", GuiWDLPermissions.width / 2, 8, 16777215);
        if (!WDLPluginChannels.hasPermissions()) {
            Gui.drawCenteredString(this.fontRendererObj, "No permissions received; defaulting to everything enabled.", GuiWDLPermissions.width / 2, (GuiWDLPermissions.height - 32 - 23) / 2 + 23 - this.fontRendererObj.FONT_HEIGHT / 2, 16777215);
        }
        super.drawScreen(n, n2, n3);
    }
}
