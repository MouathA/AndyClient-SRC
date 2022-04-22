package net.minecraft.client.multiplayer;

import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class ServerData
{
    public String serverName;
    public String serverIP;
    private boolean lanServer;
    public String populationInfo;
    public String serverMOTD;
    public long pingToServer;
    public int version;
    public String gameVersion;
    public boolean field_78841_f;
    public String playerList;
    private ServerResourceMode resourceMode;
    private String serverIcon;
    private static final String __OBFID;
    
    public ServerData(final String serverName, final String serverIP) {
        this.version = 47;
        this.gameVersion = "1.8";
        this.resourceMode = ServerResourceMode.PROMPT;
        this.serverName = serverName;
        this.serverIP = serverIP;
    }
    
    public ServerData(final String serverName, final String serverIP, final boolean lanServer) {
        this.version = 47;
        this.gameVersion = "1.8";
        this.serverName = serverName;
        this.serverIP = serverIP;
        this.lanServer = lanServer;
    }
    
    public NBTTagCompound getNBTCompound() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString("name", this.serverName);
        nbtTagCompound.setString("ip", this.serverIP);
        if (this.serverIcon != null) {
            nbtTagCompound.setString("icon", this.serverIcon);
        }
        if (this.resourceMode == ServerResourceMode.ENABLED) {
            nbtTagCompound.setBoolean("acceptTextures", true);
        }
        else if (this.resourceMode == ServerResourceMode.DISABLED) {
            nbtTagCompound.setBoolean("acceptTextures", false);
        }
        return nbtTagCompound;
    }
    
    public ServerResourceMode getResourceMode() {
        return this.resourceMode;
    }
    
    public void setResourceMode(final ServerResourceMode resourceMode) {
        this.resourceMode = resourceMode;
    }
    
    public static ServerData getServerDataFromNBTCompound(final NBTTagCompound nbtTagCompound) {
        final ServerData serverData = new ServerData(nbtTagCompound.getString("name"), nbtTagCompound.getString("ip"));
        if (nbtTagCompound.hasKey("icon", 8)) {
            serverData.setBase64EncodedIconData(nbtTagCompound.getString("icon"));
        }
        if (nbtTagCompound.hasKey("acceptTextures", 1)) {
            if (nbtTagCompound.getBoolean("acceptTextures")) {
                serverData.setResourceMode(ServerResourceMode.ENABLED);
            }
            else {
                serverData.setResourceMode(ServerResourceMode.DISABLED);
            }
        }
        else {
            serverData.setResourceMode(ServerResourceMode.PROMPT);
        }
        return serverData;
    }
    
    public String getBase64EncodedIconData() {
        return this.serverIcon;
    }
    
    public void setBase64EncodedIconData(final String serverIcon) {
        this.serverIcon = serverIcon;
    }
    
    public void copyFrom(final ServerData serverData) {
        this.serverIP = serverData.serverIP;
        this.serverName = serverData.serverName;
        this.setResourceMode(serverData.getResourceMode());
        this.serverIcon = serverData.serverIcon;
    }
    
    static {
        __OBFID = "CL_00000890";
    }
    
    public enum ServerResourceMode
    {
        ENABLED("ENABLED", 0, "ENABLED", 0, "enabled"), 
        DISABLED("DISABLED", 1, "DISABLED", 1, "disabled"), 
        PROMPT("PROMPT", 2, "PROMPT", 2, "prompt");
        
        private final IChatComponent motd;
        private static final ServerResourceMode[] $VALUES;
        private static final String __OBFID;
        private static final ServerResourceMode[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001833";
            ENUM$VALUES = new ServerResourceMode[] { ServerResourceMode.ENABLED, ServerResourceMode.DISABLED, ServerResourceMode.PROMPT };
            $VALUES = new ServerResourceMode[] { ServerResourceMode.ENABLED, ServerResourceMode.DISABLED, ServerResourceMode.PROMPT };
        }
        
        private ServerResourceMode(final String s, final int n, final String s2, final int n2, final String s3) {
            this.motd = new ChatComponentTranslation("addServer.resourcePack." + s3, new Object[0]);
        }
        
        public IChatComponent getMotd() {
            return this.motd;
        }
    }
}
