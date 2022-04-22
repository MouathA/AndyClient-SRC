package net.minecraft.client.network;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.client.multiplayer.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class LanServerDetector
{
    private static final AtomicInteger field_148551_a;
    private static final Logger logger;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001133";
        field_148551_a = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    static AtomicInteger access$0() {
        return LanServerDetector.field_148551_a;
    }
    
    static Logger access$1() {
        return LanServerDetector.logger;
    }
    
    public static class LanServer
    {
        private String lanServerMotd;
        private String lanServerIpPort;
        private long timeLastSeen;
        private static final String __OBFID;
        
        public LanServer(final String lanServerMotd, final String lanServerIpPort) {
            this.lanServerMotd = lanServerMotd;
            this.lanServerIpPort = lanServerIpPort;
            this.timeLastSeen = Minecraft.getSystemTime();
        }
        
        public String getServerMotd() {
            return this.lanServerMotd;
        }
        
        public String getServerIpPort() {
            return this.lanServerIpPort;
        }
        
        public void updateLastSeen() {
            this.timeLastSeen = Minecraft.getSystemTime();
        }
        
        static {
            __OBFID = "CL_00001134";
        }
    }
    
    public static class LanServerList
    {
        private List listOfLanServers;
        boolean wasUpdated;
        private static final String __OBFID;
        
        public LanServerList() {
            this.listOfLanServers = Lists.newArrayList();
        }
        
        public synchronized boolean getWasUpdated() {
            return this.wasUpdated;
        }
        
        public synchronized void setWasNotUpdated() {
            this.wasUpdated = false;
        }
        
        public synchronized List getLanServers() {
            return Collections.unmodifiableList((List<?>)this.listOfLanServers);
        }
        
        public synchronized void func_77551_a(final String s, final InetAddress inetAddress) {
            final String motdFromPingResponse = ThreadLanServerPing.getMotdFromPingResponse(s);
            final String adFromPingResponse = ThreadLanServerPing.getAdFromPingResponse(s);
            if (adFromPingResponse != null) {
                final String string = String.valueOf(inetAddress.getHostAddress()) + ":" + adFromPingResponse;
                for (final LanServer lanServer : this.listOfLanServers) {
                    if (lanServer.getServerIpPort().equals(string)) {
                        lanServer.updateLastSeen();
                        break;
                    }
                }
                if (!true) {
                    this.listOfLanServers.add(new LanServer(motdFromPingResponse, string));
                    this.wasUpdated = true;
                }
            }
        }
        
        static {
            __OBFID = "CL_00001136";
        }
    }
    
    public static class ThreadLanServerFind extends Thread
    {
        private final LanServerList localServerList;
        private final InetAddress broadcastAddress;
        private final MulticastSocket socket;
        private static final String __OBFID;
        
        public ThreadLanServerFind(final LanServerList localServerList) throws IOException {
            super("LanServerDetector #" + LanServerDetector.access$0().incrementAndGet());
            this.localServerList = localServerList;
            this.setDaemon(true);
            this.socket = new MulticastSocket(4445);
            this.broadcastAddress = InetAddress.getByName("224.0.2.60");
            this.socket.setSoTimeout(5000);
            this.socket.joinGroup(this.broadcastAddress);
        }
        
        @Override
        public void run() {
            final byte[] array = new byte[1024];
            while (!this.isInterrupted()) {
                final DatagramPacket datagramPacket = new DatagramPacket(array, array.length);
                this.socket.receive(datagramPacket);
                final String s = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
                LanServerDetector.access$1().debug(datagramPacket.getAddress() + ": " + s);
                this.localServerList.func_77551_a(s, datagramPacket.getAddress());
            }
            this.socket.leaveGroup(this.broadcastAddress);
            this.socket.close();
        }
        
        static {
            __OBFID = "CL_00001135";
        }
    }
}
