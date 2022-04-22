package com.mojang.realmsclient.util;

import org.apache.logging.log4j.*;
import com.mojang.realmsclient.gui.*;
import com.mojang.realmsclient.client.*;
import net.minecraft.realms.*;
import javax.annotation.*;
import com.google.common.util.concurrent.*;
import com.mojang.realmsclient.dto.*;
import com.mojang.realmsclient.gui.screens.*;
import com.mojang.realmsclient.*;

public class RealmsTasks
{
    private static final Logger LOGGER;
    private static final int NUMBER_OF_RETRIES = 25;
    
    private static void pause(final int n) {
        Thread.sleep(n * 1000);
    }
    
    static void access$000(final int n) {
        pause(n);
    }
    
    static Logger access$100() {
        return RealmsTasks.LOGGER;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public static class WorldCreationTask extends LongRunningTask
    {
        private final String name;
        private final String motd;
        private final long worldId;
        private final RealmsScreen lastScreen;
        
        public WorldCreationTask(final long worldId, final String name, final String motd, final RealmsScreen lastScreen) {
            this.worldId = worldId;
            this.name = name;
            this.motd = motd;
            this.lastScreen = lastScreen;
        }
        
        @Override
        public void run() {
            this.setTitle(RealmsScreen.getLocalizedString("mco.create.world.wait"));
            RealmsClient.createRealmsClient().initializeWorld(this.worldId, this.name, this.motd);
            Realms.setScreen(this.lastScreen);
        }
    }
    
    public static class RealmsConnectTask extends LongRunningTask
    {
        private final RealmsConnect realmsConnect;
        private final RealmsServer data;
        private final RealmsScreen onlineScreen;
        
        public RealmsConnectTask(final RealmsScreen onlineScreen, final RealmsServer data) {
            this.onlineScreen = onlineScreen;
            this.realmsConnect = new RealmsConnect(onlineScreen);
            this.data = data;
        }
        
        @Override
        public void run() {
            this.setTitle(RealmsScreen.getLocalizedString("mco.connect.connecting"));
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            RealmsServerAddress join = null;
            while (0 < 20) {
                if (this.aborted()) {
                    break;
                }
                join = realmsClient.join(this.data.id);
                if (true) {
                    break;
                }
                this.sleep(5);
                int n = 0;
                ++n;
            }
            if (true) {
                Realms.setScreen(new RealmsTermsScreen(this.onlineScreen, this.data));
            }
            else if (!this.aborted() && !true) {
                if (true) {
                    if (this.data.resourcePackUrl != null && this.data.resourcePackHash != null) {
                        Futures.addCallback(Realms.downloadResourcePack(this.data.resourcePackUrl, this.data.resourcePackHash), new FutureCallback(join) {
                            final RealmsServerAddress val$finalA;
                            final RealmsConnectTask this$0;
                            
                            @Override
                            public void onSuccess(@Nullable final Object o) {
                                final net.minecraft.realms.RealmsServerAddress string = net.minecraft.realms.RealmsServerAddress.parseString(this.val$finalA.address);
                                RealmsConnectTask.access$200(this.this$0).connect(string.getHost(), string.getPort());
                            }
                            
                            @Override
                            public void onFailure(final Throwable t) {
                                RealmsTasks.access$100().error(t);
                                this.this$0.error("Failed to download resource pack!");
                            }
                        });
                    }
                    else {
                        final net.minecraft.realms.RealmsServerAddress string = net.minecraft.realms.RealmsServerAddress.parseString(join.address);
                        this.realmsConnect.connect(string.getHost(), string.getPort());
                    }
                }
                else {
                    this.error(RealmsScreen.getLocalizedString("mco.errorMessage.connectionFailure"));
                }
            }
        }
        
        private void sleep(final int n) {
            Thread.sleep(n * 1000);
        }
        
        @Override
        public void abortTask() {
            this.realmsConnect.abort();
        }
        
        @Override
        public void tick() {
            this.realmsConnect.tick();
        }
        
        static RealmsConnect access$200(final RealmsConnectTask realmsConnectTask) {
            return realmsConnectTask.realmsConnect;
        }
    }
    
    public static class ResettingWorldTask extends LongRunningTask
    {
        private final String seed;
        private final WorldTemplate worldTemplate;
        private final int levelType;
        private final boolean generateStructures;
        private final long serverId;
        private final RealmsScreen lastScreen;
        private String title;
        
        public ResettingWorldTask(final long serverId, final RealmsScreen lastScreen, final WorldTemplate worldTemplate) {
            this.title = RealmsScreen.getLocalizedString("mco.reset.world.resetting.screen.title");
            this.seed = null;
            this.worldTemplate = worldTemplate;
            this.levelType = -1;
            this.generateStructures = true;
            this.serverId = serverId;
            this.lastScreen = lastScreen;
        }
        
        public ResettingWorldTask(final long serverId, final RealmsScreen lastScreen, final String seed, final int levelType, final boolean generateStructures) {
            this.title = RealmsScreen.getLocalizedString("mco.reset.world.resetting.screen.title");
            this.seed = seed;
            this.worldTemplate = null;
            this.levelType = levelType;
            this.generateStructures = generateStructures;
            this.serverId = serverId;
            this.lastScreen = lastScreen;
        }
        
        public void setResetTitle(final String title) {
            this.title = title;
        }
        
        @Override
        public void run() {
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            this.setTitle(this.title);
            if (this.aborted()) {
                return;
            }
            if (this.worldTemplate != null) {
                realmsClient.resetWorldWithTemplate(this.serverId, this.worldTemplate.id);
            }
            else {
                realmsClient.resetWorldWithSeed(this.serverId, this.seed, this.levelType, this.generateStructures);
            }
            if (this.aborted()) {
                return;
            }
            Realms.setScreen(this.lastScreen);
        }
    }
    
    public static class SwitchMinigameTask extends LongRunningTask
    {
        private final long worldId;
        private final WorldTemplate worldTemplate;
        private final RealmsConfigureWorldScreen lastScreen;
        
        public SwitchMinigameTask(final long worldId, final WorldTemplate worldTemplate, final RealmsConfigureWorldScreen lastScreen) {
            this.worldId = worldId;
            this.worldTemplate = worldTemplate;
            this.lastScreen = lastScreen;
        }
        
        @Override
        public void run() {
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            this.setTitle("\u5616\u5618\u5614\u5655\u5616\u5612\u5615\u5612\u561c\u561a\u5616\u561e\u5655\u560c\u5614\u5609\u5617\u561f\u5655\u5608\u560f\u561a\u5609\u560f\u5612\u5615\u561c\u5655\u5608\u5618\u5609\u561e\u561e\u5615\u5655\u560f\u5612\u560f\u5617\u561e");
            while (!this.aborted()) {
                if (realmsClient.putIntoMinigameMode(this.worldId, this.worldTemplate.id)) {
                    Realms.setScreen(this.lastScreen);
                    return;
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class SwitchSlotTask extends LongRunningTask
    {
        private final long worldId;
        private final int slot;
        private final RealmsScreen lastScreen;
        private final int confirmId;
        
        public SwitchSlotTask(final long worldId, final int slot, final RealmsScreen lastScreen, final int confirmId) {
            this.worldId = worldId;
            this.slot = slot;
            this.lastScreen = lastScreen;
            this.confirmId = confirmId;
        }
        
        @Override
        public void run() {
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            this.setTitle(RealmsScreen.getLocalizedString("mco.minigame.world.slot.screen.title"));
            while (0 < 25) {
                if (this.aborted()) {
                    return;
                }
                if (realmsClient.switchSlot(this.worldId, this.slot)) {
                    this.lastScreen.confirmResult(true, this.confirmId);
                    break;
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class CloseServerTask extends LongRunningTask
    {
        private final RealmsServer serverData;
        private final RealmsConfigureWorldScreen configureScreen;
        
        public CloseServerTask(final RealmsServer serverData, final RealmsConfigureWorldScreen configureScreen) {
            this.serverData = serverData;
            this.configureScreen = configureScreen;
        }
        
        @Override
        public void run() {
            this.setTitle(RealmsScreen.getLocalizedString("mco.configure.world.closing"));
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            while (0 < 25) {
                if (this.aborted()) {
                    return;
                }
                if (realmsClient.close(this.serverData.id)) {
                    this.configureScreen.stateChanged();
                    this.serverData.state = RealmsServer.State.CLOSED;
                    Realms.setScreen(this.configureScreen);
                    break;
                }
                int n = 0;
                ++n;
            }
        }
    }
    
    public static class OpenServerTask extends LongRunningTask
    {
        private final RealmsServer serverData;
        private final RealmsConfigureWorldScreen configureScreen;
        private final boolean join;
        private final RealmsScreen lastScreen;
        
        public OpenServerTask(final RealmsServer serverData, final RealmsConfigureWorldScreen configureScreen, final RealmsScreen lastScreen, final boolean join) {
            this.serverData = serverData;
            this.configureScreen = configureScreen;
            this.join = join;
            this.lastScreen = lastScreen;
        }
        
        @Override
        public void run() {
            this.setTitle(RealmsScreen.getLocalizedString("mco.configure.world.opening"));
            final RealmsClient realmsClient = RealmsClient.createRealmsClient();
            while (0 < 25) {
                if (this.aborted()) {
                    return;
                }
                if (realmsClient.open(this.serverData.id)) {
                    this.configureScreen.stateChanged();
                    this.serverData.state = RealmsServer.State.OPEN;
                    if (this.join) {
                        ((RealmsMainScreen)this.lastScreen).play(this.serverData);
                        break;
                    }
                    Realms.setScreen(this.configureScreen);
                    break;
                }
                else {
                    int n = 0;
                    ++n;
                }
            }
        }
    }
}
