package viamcp;

import java.util.logging.*;
import io.netty.channel.*;
import java.io.*;
import viamcp.gui.*;
import org.apache.logging.log4j.*;
import viamcp.utils.*;
import com.google.common.util.concurrent.*;
import io.netty.channel.local.*;
import com.viaversion.viaversion.*;
import viamcp.platform.*;
import com.viaversion.viaversion.api.platform.*;
import com.viaversion.viaversion.api.*;
import viamcp.loader.*;
import java.util.concurrent.*;

public class ViaMCP
{
    private static final ViaMCP instance;
    private final Logger jLogger;
    private final CompletableFuture INIT_FUTURE;
    private ExecutorService ASYNC_EXEC;
    private EventLoop EVENT_LOOP;
    private File file;
    private int version;
    private String lastServer;
    public AsyncVersionSlider asyncSlider;
    
    static {
        instance = new ViaMCP();
    }
    
    public ViaMCP() {
        this.jLogger = new JLoggerToLog4j(LogManager.getLogger("ViaMCP"));
        this.INIT_FUTURE = new CompletableFuture();
    }
    
    public static ViaMCP getInstance() {
        return ViaMCP.instance;
    }
    
    public void start() {
        final ThreadFactory build = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ViaMCP-%d").build();
        this.ASYNC_EXEC = Executors.newFixedThreadPool(8, build);
        (this.EVENT_LOOP = new LocalEventLoopGroup(1, build).next()).submit((Callable)this.INIT_FUTURE::join);
        this.setVersion(47);
        this.file = new File("ViaMCP");
        if (this.file.mkdir()) {
            this.getjLogger().info("Creating ViaMCP Folder");
        }
        Via.init(ViaManagerImpl.builder().injector(new MCPViaInjector()).loader(new MCPViaLoader()).platform(new MCPViaPlatform(this.file)).build());
        ((ViaManagerImpl)Via.getManager()).init();
        new MCPBackwardsLoader(this.file);
        new MCPRewindLoader(this.file);
        this.INIT_FUTURE.complete(null);
    }
    
    public void initAsyncSlider() {
        this.asyncSlider = new AsyncVersionSlider(-1, 5, 5, 110, 20);
    }
    
    public void initAsyncSlider(final int n, final int n2, final int n3, final int n4) {
        this.asyncSlider = new AsyncVersionSlider(-1, n, n2, Math.max(n3, 110), n4);
    }
    
    public Logger getjLogger() {
        return this.jLogger;
    }
    
    public CompletableFuture getInitFuture() {
        return this.INIT_FUTURE;
    }
    
    public ExecutorService getAsyncExecutor() {
        return this.ASYNC_EXEC;
    }
    
    public EventLoop getEventLoop() {
        return this.EVENT_LOOP;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getLastServer() {
        return this.lastServer;
    }
    
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(final int version) {
        this.version = version;
    }
    
    public void setFile(final File file) {
        this.file = file;
    }
    
    public void setLastServer(final String lastServer) {
        this.lastServer = lastServer;
    }
}
