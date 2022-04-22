package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import org.apache.logging.log4j.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import java.util.*;
import java.io.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.client.multiplayer.*;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback
{
    private static final Logger logger;
    private final OldServerPinger oldServerPinger;
    private GuiScreen parentScreen;
    private ServerSelectionList serverListSelector;
    private ServerList savedServerList;
    private GuiButton btnEditServer;
    private GuiButton btnSelectServer;
    private GuiButton btnDeleteServer;
    private boolean deletingServer;
    public static boolean serverlisttwo;
    private boolean addingServer;
    private boolean editingServer;
    private boolean directConnect;
    private String field_146812_y;
    private ServerData selectedServer;
    private LanServerDetector.LanServerList lanServerList;
    private LanServerDetector.ThreadLanServerFind lanServerDetector;
    private boolean initialized;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000814";
        logger = LogManager.getLogger();
        GuiMultiplayer.serverlisttwo = false;
    }
    
    public GuiMultiplayer(final GuiScreen parentScreen) {
        this.oldServerPinger = new OldServerPinger();
        this.parentScreen = parentScreen;
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        if (!this.initialized) {
            this.initialized = true;
            (this.savedServerList = new ServerList(GuiMultiplayer.mc)).loadServerList();
            this.lanServerList = new LanServerDetector.LanServerList();
            (this.lanServerDetector = new LanServerDetector.ThreadLanServerFind(this.lanServerList)).start();
            (this.serverListSelector = new ServerSelectionList(this, GuiMultiplayer.mc, GuiMultiplayer.width, GuiMultiplayer.height, 32, GuiMultiplayer.height - 64, 36)).func_148195_a(this.savedServerList);
        }
        else {
            this.serverListSelector.setDimensions(GuiMultiplayer.width, GuiMultiplayer.height, 32, GuiMultiplayer.height - 64);
        }
        this.createButtons();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.serverListSelector.func_178039_p();
    }
    
    public void createButtons() {
        this.buttonList.add(new GuiButton(12, GuiMultiplayer.width / 2 - 233, GuiMultiplayer.height - 28, 75, 20, "Premium"));
        this.buttonList.add(new GuiButton(10, GuiMultiplayer.width / 2 + 4 + 155, GuiMultiplayer.height - 28, 75, 20, "Premium"));
        this.buttonList.add(this.btnEditServer = new GuiButton(7, GuiMultiplayer.width / 2 - 154, GuiMultiplayer.height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
        this.buttonList.add(this.btnDeleteServer = new GuiButton(2, GuiMultiplayer.width / 2 - 74, GuiMultiplayer.height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
        this.buttonList.add(this.btnSelectServer = new GuiButton(1, GuiMultiplayer.width / 2 - 154, GuiMultiplayer.height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(new GuiButton(4, GuiMultiplayer.width / 2 - 50, GuiMultiplayer.height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
        this.buttonList.add(new GuiButton(3, GuiMultiplayer.width / 2 + 4 + 50, GuiMultiplayer.height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
        this.buttonList.add(new GuiButton(8, GuiMultiplayer.width / 2 + 4, GuiMultiplayer.height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
        this.buttonList.add(new GuiButton(0, GuiMultiplayer.width / 2 + 4 + 76, GuiMultiplayer.height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiButton(16, GuiMultiplayer.width / 2 - 233, GuiMultiplayer.height - 50, 75, 20, this.BungeeSpoofBtn()));
        this.selectServer(this.serverListSelector.func_148193_k());
    }
    
    private String BungeeSpoofBtn() {
        return Minecraft.BungeeHack ? "         BungeeSpoof: Y" : "         BungeeSpoof: N";
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.lanServerList.getWasUpdated()) {
            final List lanServers = this.lanServerList.getLanServers();
            this.lanServerList.setWasNotUpdated();
            this.serverListSelector.func_148194_a(lanServers);
        }
        this.oldServerPinger.pingPendingNetworks();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        if (this.lanServerDetector != null) {
            this.lanServerDetector.interrupt();
            this.lanServerDetector = null;
        }
        this.oldServerPinger.clearPendingNetworks();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            final GuiListExtended.IGuiListEntry guiListEntry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
            if (guiButton.id == 2 && guiListEntry instanceof ServerListEntryNormal) {
                final String serverName = ((ServerListEntryNormal)guiListEntry).getServerData().serverName;
                if (serverName != null) {
                    this.deletingServer = true;
                    GuiMultiplayer.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectServer.deleteQuestion", new Object[0]), "'" + serverName + "' " + I18n.format("selectServer.deleteWarning", new Object[0]), I18n.format("selectServer.deleteButton", new Object[0]), I18n.format("gui.cancel", new Object[0]), this.serverListSelector.func_148193_k()));
                }
            }
            else if (guiButton.id == 1) {
                this.connectToSelected();
            }
            else if (guiButton.id == 4) {
                this.directConnect = true;
                GuiMultiplayer.mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
            }
            else if (guiButton.id == 3) {
                this.addingServer = true;
                GuiMultiplayer.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
            }
            else if (guiButton.id == 7 && guiListEntry instanceof ServerListEntryNormal) {
                this.editingServer = true;
                final ServerData serverData = ((ServerListEntryNormal)guiListEntry).getServerData();
                (this.selectedServer = new ServerData(serverData.serverName, serverData.serverIP)).copyFrom(serverData);
                GuiMultiplayer.mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer));
            }
            else if (guiButton.id == 0) {
                GuiMultiplayer.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton.id == 8) {
                this.refreshServerList();
            }
            else if (guiButton.id == 16) {
                Minecraft.BungeeHack = !Minecraft.BungeeHack;
                guiButton.displayString = this.BungeeSpoofBtn();
            }
            else if (guiButton.id == 70 && !GuiMultiplayer.serverlisttwo) {
                new File("servers.dat").delete();
                this.refreshServerList();
            }
        }
    }
    
    private void refreshServerList() {
        GuiMultiplayer.mc.displayGuiScreen(new GuiMultiplayer(this.parentScreen));
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        final GuiListExtended.IGuiListEntry guiListEntry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
        if (this.deletingServer) {
            this.deletingServer = false;
            if (b && guiListEntry instanceof ServerListEntryNormal) {
                this.savedServerList.removeServerData(this.serverListSelector.func_148193_k());
                this.savedServerList.saveServerList();
                this.serverListSelector.func_148192_c(-1);
                this.serverListSelector.func_148195_a(this.savedServerList);
            }
            GuiMultiplayer.mc.displayGuiScreen(this);
        }
        else if (this.directConnect) {
            this.directConnect = false;
            if (b) {
                this.connectToServer(this.selectedServer);
            }
            else {
                GuiMultiplayer.mc.displayGuiScreen(this);
            }
        }
        else if (this.addingServer) {
            this.addingServer = false;
            if (b) {
                this.savedServerList.addServerData(this.selectedServer);
                this.savedServerList.saveServerList();
                this.serverListSelector.func_148192_c(-1);
                this.serverListSelector.func_148195_a(this.savedServerList);
            }
            GuiMultiplayer.mc.displayGuiScreen(this);
        }
        else if (this.editingServer) {
            this.editingServer = false;
            if (b && guiListEntry instanceof ServerListEntryNormal) {
                final ServerData serverData = ((ServerListEntryNormal)guiListEntry).getServerData();
                serverData.serverName = this.selectedServer.serverName;
                serverData.serverIP = this.selectedServer.serverIP;
                serverData.copyFrom(this.selectedServer);
                this.savedServerList.saveServerList();
                this.serverListSelector.func_148195_a(this.savedServerList);
            }
            GuiMultiplayer.mc.displayGuiScreen(this);
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        final int func_148193_k = this.serverListSelector.func_148193_k();
        final GuiListExtended.IGuiListEntry guiListEntry = (func_148193_k < 0) ? null : this.serverListSelector.getListEntry(func_148193_k);
        if (n == 63) {
            this.refreshServerList();
        }
        else if (func_148193_k >= 0) {
            if (n == 200) {
                if (isShiftKeyDown()) {
                    if (func_148193_k > 0 && guiListEntry instanceof ServerListEntryNormal) {
                        this.savedServerList.swapServers(func_148193_k, func_148193_k - 1);
                        this.selectServer(this.serverListSelector.func_148193_k() - 1);
                        this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                        this.serverListSelector.func_148195_a(this.savedServerList);
                    }
                }
                else if (func_148193_k > 0) {
                    this.selectServer(this.serverListSelector.func_148193_k() - 1);
                    this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                    if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
                        if (this.serverListSelector.func_148193_k() > 0) {
                            this.selectServer(this.serverListSelector.getSize() - 1);
                            this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                        }
                        else {
                            this.selectServer(-1);
                        }
                    }
                }
                else {
                    this.selectServer(-1);
                }
            }
            else if (n == 208) {
                if (isShiftKeyDown()) {
                    if (func_148193_k < this.savedServerList.countServers() - 1) {
                        this.savedServerList.swapServers(func_148193_k, func_148193_k + 1);
                        this.selectServer(func_148193_k + 1);
                        this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                        this.serverListSelector.func_148195_a(this.savedServerList);
                    }
                }
                else if (func_148193_k < this.serverListSelector.getSize()) {
                    this.selectServer(this.serverListSelector.func_148193_k() + 1);
                    this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                    if (this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan) {
                        if (this.serverListSelector.func_148193_k() < this.serverListSelector.getSize() - 1) {
                            this.selectServer(this.serverListSelector.getSize() + 1);
                            this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                        }
                        else {
                            this.selectServer(-1);
                        }
                    }
                }
                else {
                    this.selectServer(-1);
                }
            }
            else if (n != 28 && n != 156) {
                super.keyTyped(c, n);
            }
            else {
                this.actionPerformed(this.buttonList.get(2));
            }
        }
        else {
            super.keyTyped(c, n);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.field_146812_y = null;
        this.drawDefaultBackground();
        this.serverListSelector.drawScreen(n, n2, n3);
        Gui.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), GuiMultiplayer.width / 2, 20, 16777215);
        super.drawScreen(n, n2, n3);
        if (this.field_146812_y != null) {
            this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.field_146812_y)), n, n2);
        }
    }
    
    public void connectToSelected() {
        final GuiListExtended.IGuiListEntry guiListEntry = (this.serverListSelector.func_148193_k() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.func_148193_k());
        if (guiListEntry instanceof ServerListEntryNormal) {
            this.connectToServer(((ServerListEntryNormal)guiListEntry).getServerData());
        }
        else if (guiListEntry instanceof ServerListEntryLanDetected) {
            final LanServerDetector.LanServer lanServer = ((ServerListEntryLanDetected)guiListEntry).getLanServer();
            this.connectToServer(new ServerData(lanServer.getServerMotd(), lanServer.getServerIpPort()));
        }
    }
    
    private void connectToServer(final ServerData serverData) {
        GuiMultiplayer.mc.displayGuiScreen(new GuiConnecting(this, GuiMultiplayer.mc, serverData));
    }
    
    public void selectServer(final int n) {
        this.serverListSelector.func_148192_c(n);
        final GuiListExtended.IGuiListEntry guiListEntry = (n < 0) ? null : this.serverListSelector.getListEntry(n);
        this.btnSelectServer.enabled = false;
        this.btnEditServer.enabled = false;
        this.btnDeleteServer.enabled = false;
        if (guiListEntry != null && !(guiListEntry instanceof ServerListEntryLanScan)) {
            this.btnSelectServer.enabled = true;
            if (guiListEntry instanceof ServerListEntryNormal) {
                this.btnEditServer.enabled = true;
                this.btnDeleteServer.enabled = true;
            }
        }
    }
    
    public OldServerPinger getOldServerPinger() {
        return this.oldServerPinger;
    }
    
    public void func_146793_a(final String field_146812_y) {
        this.field_146812_y = field_146812_y;
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.serverListSelector.func_148179_a(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
        this.serverListSelector.func_148181_b(n, n2, n3);
    }
    
    public ServerList getServerList() {
        return this.savedServerList;
    }
    
    public boolean func_175392_a(final ServerListEntryNormal serverListEntryNormal, final int n) {
        return n > 0;
    }
    
    public boolean func_175394_b(final ServerListEntryNormal serverListEntryNormal, final int n) {
        return n < this.savedServerList.countServers() - 1;
    }
    
    public void func_175391_a(final ServerListEntryNormal serverListEntryNormal, final int n, final boolean b) {
        final int n2 = b ? 0 : (n - 1);
        this.savedServerList.swapServers(n, n2);
        if (this.serverListSelector.func_148193_k() == n) {
            this.selectServer(n2);
        }
        this.serverListSelector.func_148195_a(this.savedServerList);
    }
    
    public void func_175393_b(final ServerListEntryNormal serverListEntryNormal, final int n, final boolean b) {
        final int n2 = b ? (this.savedServerList.countServers() - 1) : (n + 1);
        this.savedServerList.swapServers(n, n2);
        if (this.serverListSelector.func_148193_k() == n) {
            this.selectServer(n2);
        }
        this.serverListSelector.func_148195_a(this.savedServerList);
    }
}
