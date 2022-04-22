package Mood.Gui;

import java.util.concurrent.atomic.*;
import net.minecraft.client.multiplayer.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.network.*;

public class Checker
{
    private static final AtomicInteger threadNumber;
    public static final Logger logger;
    public ServerData server;
    private boolean done;
    private boolean failed;
    
    static {
        threadNumber = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    public Checker() {
        this.done = false;
        this.failed = false;
    }
    
    public void ping(final String s, final int n) {
        new Thread("Server Connector #" + Checker.threadNumber.incrementAndGet(), s, n) {
            final Checker this$0;
            private final String val$ip;
            private final int val$port;
            
            @Override
            public void run() {
                final OldServerPinger oldServerPinger = new OldServerPinger();
                Checker.logger.info("Pinging " + this.val$ip + ":" + this.val$port + "...");
                oldServerPinger.ping(this.this$0.server);
                Thread.sleep(250L);
                Checker.logger.info("Ping successful: " + this.val$ip + ":" + this.val$port);
                oldServerPinger.clearPendingNetworks();
                Checker.access$1(this.this$0, true);
            }
        }.start();
    }
    
    public boolean isStillPinging() {
        return !this.done;
    }
    
    public boolean isWorking() {
        return !this.failed;
    }
    
    public boolean isOtherVersion() {
        return this.server.version != 47 && this.server.version != 5 && this.server.version != -1 && this.server.version != 107;
    }
    
    static void access$0(final Checker checker, final boolean failed) {
        checker.failed = failed;
    }
    
    static void access$1(final Checker checker, final boolean done) {
        checker.done = done;
    }
}
