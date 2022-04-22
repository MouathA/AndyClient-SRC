package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import Mood.Helpers.*;
import java.net.*;
import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger CONNECTION_ID;
    private static final Logger logger;
    private NetworkManager networkManager;
    private boolean cancel;
    private final GuiScreen previousGuiScreen;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000685";
        CONNECTION_ID = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    public GuiConnecting(final GuiScreen previousGuiScreen, final Minecraft mc, final ServerData serverData) {
        GuiConnecting.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
        final ServerAddress func_78860_a = ServerAddress.func_78860_a(serverData.serverIP);
        mc.loadWorld(null);
        mc.setServerData(serverData);
        this.connect(func_78860_a.getIP(), func_78860_a.getPort());
    }
    
    public GuiConnecting(final GuiScreen previousGuiScreen, final Minecraft mc, final String s, final int n) {
        GuiConnecting.mc = mc;
        this.previousGuiScreen = previousGuiScreen;
        mc.loadWorld(null);
        this.connect(s, n);
    }
    
    private void connect(final String s, final int n) {
        ServerPerformanceCalculator.tpsList.clear();
        GuiConnecting.logger.info("Connecting to " + s + ", " + n);
        new Thread("Server Connector #" + GuiConnecting.CONNECTION_ID.incrementAndGet(), s, n) {
            private static final String __OBFID;
            final GuiConnecting this$0;
            private final String val$ip;
            private final int val$port;
            
            @Override
            public void run() {
                if (GuiConnecting.access$0(this.this$0)) {
                    return;
                }
                GuiConnecting.access$1(this.this$0, NetworkManager.provideLanClient(InetAddress.getByName(this.val$ip), this.val$port));
                GuiConnecting.access$2(this.this$0).setNetHandler(new NetHandlerLoginClient(GuiConnecting.access$2(this.this$0), GuiConnecting.mc, GuiConnecting.access$3(this.this$0)));
                GuiConnecting.access$2(this.this$0).sendPacket(new C00Handshake(47, this.val$ip, this.val$port, EnumConnectionState.LOGIN));
                final NetworkManager access$2 = GuiConnecting.access$2(this.this$0);
                final Minecraft mc = GuiConnecting.mc;
                access$2.sendPacket(new C00PacketLoginStart(Minecraft.getSession().getProfile()));
            }
            
            static {
                __OBFID = "CL_00000686";
            }
        }.start();
    }
    
    @Override
    public void updateScreen() {
        if (this.networkManager != null) {
            if (this.networkManager.isChannelOpen()) {
                this.networkManager.processReceivedPackets();
            }
            else {
                this.networkManager.checkDisconnected();
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, GuiConnecting.width / 2 - 100, GuiConnecting.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.cancel = true;
            if (this.networkManager != null) {
                this.networkManager.closeChannel(new ChatComponentText("Aborted"));
            }
            GuiConnecting.mc.displayGuiScreen(this.previousGuiScreen);
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawMateFasza();
        if (this.networkManager == null) {
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]), GuiConnecting.width / 2, GuiConnecting.height / 2 - 50, 16777215);
        }
        else {
            Gui.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]), GuiConnecting.width / 2, GuiConnecting.height / 2 - 50, 16777215);
        }
        super.drawScreen(n, n2, n3);
    }
    
    static boolean access$0(final GuiConnecting guiConnecting) {
        return guiConnecting.cancel;
    }
    
    static void access$1(final GuiConnecting guiConnecting, final NetworkManager networkManager) {
        guiConnecting.networkManager = networkManager;
    }
    
    static NetworkManager access$2(final GuiConnecting guiConnecting) {
        return guiConnecting.networkManager;
    }
    
    static GuiScreen access$3(final GuiConnecting guiConnecting) {
        return guiConnecting.previousGuiScreen;
    }
    
    static Logger access$4() {
        return GuiConnecting.logger;
    }
}
