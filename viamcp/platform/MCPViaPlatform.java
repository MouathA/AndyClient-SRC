package viamcp.platform;

import java.util.logging.*;
import java.io.*;
import com.viaversion.viaversion.api.*;
import org.apache.logging.log4j.*;
import java.nio.file.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import viamcp.utils.*;
import viamcp.*;
import java.util.function.*;
import com.viaversion.viaversion.api.platform.*;
import io.netty.util.concurrent.*;
import com.viaversion.viaversion.api.command.*;
import java.util.*;
import com.viaversion.viaversion.api.configuration.*;
import com.viaversion.viaversion.libs.gson.*;
import java.util.concurrent.*;

public class MCPViaPlatform implements ViaPlatform
{
    private final Logger logger;
    private final MCPViaConfig config;
    private final File dataFolder;
    private final ViaAPI api;
    
    public MCPViaPlatform(final File file) {
        this.logger = new JLoggerToLog4j(LogManager.getLogger("ViaVersion"));
        final Path resolve = file.toPath().resolve("ViaVersion");
        this.config = new MCPViaConfig(resolve.resolve("viaversion.yml").toFile());
        this.dataFolder = resolve.toFile();
        this.api = new MCPViaAPI();
    }
    
    public static String legacyToJson(final String input) {
        return (String)GsonComponentSerializer.gson().serialize(LegacyComponentSerializer.legacySection().deserialize(input));
    }
    
    @Override
    public Logger getLogger() {
        return this.logger;
    }
    
    @Override
    public String getPlatformName() {
        return "ViaMCP";
    }
    
    @Override
    public String getPlatformVersion() {
        return String.valueOf(47);
    }
    
    @Override
    public String getPluginVersion() {
        return "4.1.1";
    }
    
    @Override
    public FutureTaskId runAsync(final Runnable runnable) {
        return new FutureTaskId(CompletableFuture.runAsync(runnable, ViaMCP.getInstance().getAsyncExecutor()).exceptionally((Function<Throwable, ? extends Void>)MCPViaPlatform::lambda$0));
    }
    
    @Override
    public FutureTaskId runSync(final Runnable runnable) {
        return new FutureTaskId(ViaMCP.getInstance().getEventLoop().submit(runnable).addListener(this.errorLogger()));
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable, final long n) {
        return new FutureTaskId(ViaMCP.getInstance().getEventLoop().schedule((Callable)this::lambda$1, n * 50L, TimeUnit.MILLISECONDS).addListener(this.errorLogger()));
    }
    
    @Override
    public PlatformTask runRepeatingSync(final Runnable runnable, final long n) {
        return new FutureTaskId(ViaMCP.getInstance().getEventLoop().scheduleAtFixedRate(this::lambda$2, 0L, n * 50L, TimeUnit.MILLISECONDS).addListener(this.errorLogger()));
    }
    
    private GenericFutureListener errorLogger() {
        return MCPViaPlatform::lambda$3;
    }
    
    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return new ViaCommandSender[1337];
    }
    
    private ViaCommandSender[] getServerPlayers() {
        return new ViaCommandSender[1337];
    }
    
    @Override
    public void sendMessage(final UUID uuid, final String s) {
    }
    
    @Override
    public boolean kickPlayer(final UUID uuid, final String s) {
        return false;
    }
    
    @Override
    public boolean isPluginEnabled() {
        return true;
    }
    
    @Override
    public ViaAPI getApi() {
        return this.api;
    }
    
    @Override
    public ViaVersionConfig getConf() {
        return this.config;
    }
    
    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return this.config;
    }
    
    @Override
    public File getDataFolder() {
        return this.dataFolder;
    }
    
    @Override
    public void onReload() {
        this.logger.info("ViaVersion was reloaded? (How did that happen)");
    }
    
    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }
    
    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }
    
    @Override
    public PlatformTask runSync(final Runnable runnable) {
        return this.runSync(runnable);
    }
    
    @Override
    public PlatformTask runAsync(final Runnable runnable) {
        return this.runAsync(runnable);
    }
    
    private static Void lambda$0(final Throwable t) {
        if (!(t instanceof CancellationException)) {
            t.printStackTrace();
        }
        return null;
    }
    
    private FutureTaskId lambda$1(final Runnable runnable) throws Exception {
        return this.runSync(runnable);
    }
    
    private void lambda$2(final Runnable runnable) {
        this.runSync(runnable);
    }
    
    private static void lambda$3(final io.netty.util.concurrent.Future future) throws Exception {
        if (!future.isCancelled() && future.cause() != null) {
            future.cause().printStackTrace();
        }
    }
}
