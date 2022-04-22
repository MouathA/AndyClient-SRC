package net.minecraft.client.multiplayer;

import net.minecraft.client.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.io.*;
import net.minecraft.nbt.*;
import java.util.*;

public class ServerList
{
    private static final Logger logger;
    private final Minecraft mc;
    private final List servers;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000891";
        logger = LogManager.getLogger();
    }
    
    public ServerList(final Minecraft mc) {
        this.servers = Lists.newArrayList();
        this.mc = mc;
        this.loadServerList();
    }
    
    public void loadServerList() {
        this.servers.clear();
        final NBTTagCompound read = CompressedStreamTools.read(new File(Minecraft.mcDataDir, "servers.dat"));
        if (read == null) {
            return;
        }
        final NBTTagList tagList = read.getTagList("servers", 10);
        while (0 < tagList.tagCount()) {
            this.servers.add(ServerData.getServerDataFromNBTCompound(tagList.getCompoundTagAt(0)));
            int n = 0;
            ++n;
        }
    }
    
    public void saveServerList() {
        final NBTTagList list = new NBTTagList();
        final Iterator<ServerData> iterator = this.servers.iterator();
        while (iterator.hasNext()) {
            list.appendTag(iterator.next().getNBTCompound());
        }
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setTag("servers", list);
        CompressedStreamTools.safeWrite(nbtTagCompound, new File(Minecraft.mcDataDir, "servers.dat"));
    }
    
    public ServerData getServerData(final int n) {
        return this.servers.get(n);
    }
    
    public void removeServerData(final int n) {
        this.servers.remove(n);
    }
    
    public void addServerData(final ServerData serverData) {
        this.servers.add(serverData);
    }
    
    public int countServers() {
        return this.servers.size();
    }
    
    public void swapServers(final int n, final int n2) {
        final ServerData serverData = this.getServerData(n);
        this.servers.set(n, this.getServerData(n2));
        this.servers.set(n2, serverData);
        this.saveServerList();
    }
    
    public void func_147413_a(final int n, final ServerData serverData) {
        this.servers.set(n, serverData);
    }
    
    public static void func_147414_b(final ServerData serverData) {
        final ServerList list = new ServerList(Minecraft.getMinecraft());
        list.loadServerList();
        while (0 < list.countServers()) {
            final ServerData serverData2 = list.getServerData(0);
            if (serverData2.serverName.equals(serverData.serverName) && serverData2.serverIP.equals(serverData.serverIP)) {
                list.func_147413_a(0, serverData);
                break;
            }
            int n = 0;
            ++n;
        }
        list.saveServerList();
    }
}
