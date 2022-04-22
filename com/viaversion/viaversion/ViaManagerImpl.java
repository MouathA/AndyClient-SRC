package com.viaversion.viaversion;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.debug.*;
import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.commands.*;
import com.viaversion.viaversion.connection.*;
import com.viaversion.viaversion.debug.*;
import java.util.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.protocol.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.regex.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.command.*;

public class ViaManagerImpl implements ViaManager
{
    private final ProtocolManagerImpl protocolManager;
    private final ConnectionManager connectionManager;
    private final DebugHandler debugHandler;
    private final ViaProviders providers;
    private final ViaPlatform platform;
    private final ViaInjector injector;
    private final ViaCommandHandler commandHandler;
    private final ViaPlatformLoader loader;
    private final Set subPlatforms;
    private List enableListeners;
    private PlatformTask mappingLoadingTask;
    private boolean debug;
    
    public ViaManagerImpl(final ViaPlatform platform, final ViaInjector injector, final ViaCommandHandler commandHandler, final ViaPlatformLoader loader) {
        this.protocolManager = new ProtocolManagerImpl();
        this.connectionManager = new ConnectionManagerImpl();
        this.debugHandler = new DebugHandlerImpl();
        this.providers = new ViaProviders();
        this.subPlatforms = new HashSet();
        this.enableListeners = new ArrayList();
        this.platform = platform;
        this.injector = injector;
        this.commandHandler = commandHandler;
        this.loader = loader;
    }
    
    public static ViaManagerBuilder builder() {
        return new ViaManagerBuilder();
    }
    
    public void init() {
        if (System.getProperty("ViaVersion") != null) {
            this.platform.onReload();
        }
        this.platform.getConf().isCheckForUpdates();
        if (!this.injector.lateProtocolVersionSetting()) {
            this.loadServerProtocol();
        }
        this.protocolManager.registerProtocols();
        this.injector.inject();
        System.setProperty("ViaVersion", this.platform.getPluginVersion());
        final Iterator<Runnable> iterator = this.enableListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().run();
        }
        this.enableListeners = null;
        this.platform.runSync(this::onServerLoaded);
    }
    
    public void onServerLoaded() {
        if (!this.protocolManager.getServerProtocolVersion().isKnown()) {
            this.loadServerProtocol();
        }
        final ServerProtocolVersion serverProtocolVersion = this.protocolManager.getServerProtocolVersion();
        if (serverProtocolVersion.isKnown()) {
            if (this.platform.isProxy()) {
                this.platform.getLogger().info("ViaVersion detected lowest supported version by the proxy: " + ProtocolVersion.getProtocol(serverProtocolVersion.lowestSupportedVersion()));
                this.platform.getLogger().info("Highest supported version by the proxy: " + ProtocolVersion.getProtocol(serverProtocolVersion.highestSupportedVersion()));
                if (this.debug) {
                    this.platform.getLogger().info("Supported version range: " + Arrays.toString(serverProtocolVersion.supportedVersions().toArray(new int[0])));
                }
            }
            else {
                this.platform.getLogger().info("ViaVersion detected server version: " + ProtocolVersion.getProtocol(serverProtocolVersion.highestSupportedVersion()));
            }
            if (!this.protocolManager.isWorkingPipe()) {
                this.platform.getLogger().warning("ViaVersion does not have any compatible versions for this server version!");
                this.platform.getLogger().warning("Please remember that ViaVersion only adds support for versions newer than the server version.");
                this.platform.getLogger().warning("If you need support for older versions you may need to use one or more ViaVersion addons too.");
                this.platform.getLogger().warning("In that case please read the ViaVersion resource page carefully or use https://jo0001.github.io/ViaSetup");
                this.platform.getLogger().warning("and if you're still unsure, feel free to join our Discord-Server for further assistance.");
            }
            else if (serverProtocolVersion.highestSupportedVersion() <= ProtocolVersion.v1_12_2.getVersion()) {
                this.platform.getLogger().warning("This version of Minecraft is extremely outdated and support for it has reached its end of life. You will still be able to run Via on this Minecraft version, but we are unlikely to provide any further fixes or help with problems specific to legacy Minecraft versions. Please consider updating to give your players a better experience and to avoid issues that have long been fixed.");
            }
        }
        this.checkJavaVersion();
        this.unsupportedSoftwareWarning();
        this.protocolManager.onServerLoaded();
        this.loader.load();
        this.mappingLoadingTask = Via.getPlatform().runRepeatingSync(this::lambda$onServerLoaded$0, 10L);
        final int lowestSupportedVersion = this.protocolManager.getServerProtocolVersion().lowestSupportedVersion();
        if (lowestSupportedVersion < ProtocolVersion.v1_9.getVersion() && Via.getConfig().isSimulatePlayerTick()) {
            Via.getPlatform().runRepeatingSync(new ViaIdleThread(), 1L);
        }
        if (lowestSupportedVersion < ProtocolVersion.v1_13.getVersion() && Via.getConfig().get1_13TabCompleteDelay() > 0) {
            Via.getPlatform().runRepeatingSync(new TabCompleteThread(), 1L);
        }
        this.protocolManager.refreshVersions();
    }
    
    private void loadServerProtocol() {
        final ProtocolVersion protocol = ProtocolVersion.getProtocol(this.injector.getServerProtocolVersion());
        ServerProtocolVersion serverProtocol;
        if (this.platform.isProxy()) {
            final IntSortedSet serverProtocolVersions = this.injector.getServerProtocolVersions();
            serverProtocol = new ServerProtocolVersionRange(serverProtocolVersions.firstInt(), serverProtocolVersions.lastInt(), serverProtocolVersions);
        }
        else {
            serverProtocol = new ServerProtocolVersionSingleton(protocol.getVersion());
        }
        this.protocolManager.setServerProtocol(serverProtocol);
    }
    
    public void destroy() {
        this.platform.getLogger().info("ViaVersion is disabling, if this is a reload and you experience issues consider rebooting.");
        this.injector.uninject();
        this.loader.unload();
    }
    
    private final void checkJavaVersion() {
        final String property = System.getProperty("java.version");
        final Matcher matcher = Pattern.compile("(?:1\\.)?(\\d+)").matcher(property);
        if (!matcher.find()) {
            this.platform.getLogger().warning("Failed to determine Java version; could not parse: " + property);
            return;
        }
        if (Integer.parseInt(matcher.group(1)) < 17) {
            this.platform.getLogger().warning("You are running an outdated Java version, please consider updating it to at least Java 17 (your version is " + property + "). At some point in the future, ViaVersion will no longer be compatible with this version of Java.");
        }
    }
    
    private final void unsupportedSoftwareWarning() {
        for (final UnsupportedSoftware unsupportedSoftware : this.platform.getUnsupportedSoftwareClasses()) {
            if (!unsupportedSoftware.findMatch()) {
                continue;
            }
            if (!true) {
                this.platform.getLogger().severe("************************************************");
                this.platform.getLogger().severe("You are using unsupported software and may encounter unforeseeable issues.");
                this.platform.getLogger().severe("");
            }
            this.platform.getLogger().severe("We strongly advise against using " + unsupportedSoftware.getName() + ":");
            this.platform.getLogger().severe(unsupportedSoftware.getReason());
            this.platform.getLogger().severe("");
        }
        if (true) {
            this.platform.getLogger().severe("We will not provide support in case you encounter issues possibly related to this software.");
            this.platform.getLogger().severe("************************************************");
        }
    }
    
    @Override
    public ViaPlatform getPlatform() {
        return this.platform;
    }
    
    @Override
    public ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }
    
    @Override
    public ProtocolManager getProtocolManager() {
        return this.protocolManager;
    }
    
    @Override
    public ViaProviders getProviders() {
        return this.providers;
    }
    
    @Override
    public DebugHandler debugHandler() {
        return this.debugHandler;
    }
    
    @Override
    public ViaInjector getInjector() {
        return this.injector;
    }
    
    @Override
    public ViaCommandHandler getCommandHandler() {
        return this.commandHandler;
    }
    
    @Override
    public ViaPlatformLoader getLoader() {
        return this.loader;
    }
    
    @Override
    public Set getSubPlatforms() {
        return this.subPlatforms;
    }
    
    @Override
    public void addEnableListener(final Runnable runnable) {
        this.enableListeners.add(runnable);
    }
    
    @Override
    public ViaVersionCommand getCommandHandler() {
        return this.getCommandHandler();
    }
    
    private void lambda$onServerLoaded$0() {
        if (this.protocolManager.checkForMappingCompletion()) {
            this.mappingLoadingTask.cancel();
            this.mappingLoadingTask = null;
        }
    }
    
    public static final class ViaManagerBuilder
    {
        private ViaPlatform platform;
        private ViaInjector injector;
        private ViaCommandHandler commandHandler;
        private ViaPlatformLoader loader;
        
        public ViaManagerBuilder platform(final ViaPlatform platform) {
            this.platform = platform;
            return this;
        }
        
        public ViaManagerBuilder injector(final ViaInjector injector) {
            this.injector = injector;
            return this;
        }
        
        public ViaManagerBuilder loader(final ViaPlatformLoader loader) {
            this.loader = loader;
            return this;
        }
        
        public ViaManagerBuilder commandHandler(final ViaCommandHandler commandHandler) {
            this.commandHandler = commandHandler;
            return this;
        }
        
        public ViaManagerImpl build() {
            return new ViaManagerImpl(this.platform, this.injector, this.commandHandler, this.loader);
        }
    }
}
