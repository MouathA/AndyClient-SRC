package net.minecraft.server.management;

import java.io.*;
import com.google.gson.*;
import java.net.*;

public class BanList extends UserList
{
    private static final String __OBFID;
    
    public BanList(final File file) {
        super(file);
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new IPBanEntry(jsonObject);
    }
    
    public boolean isBanned(final SocketAddress socketAddress) {
        return this.hasEntry(this.addressToString(socketAddress));
    }
    
    public IPBanEntry getBanEntry(final SocketAddress socketAddress) {
        return (IPBanEntry)this.getEntry(this.addressToString(socketAddress));
    }
    
    private String addressToString(final SocketAddress socketAddress) {
        String s = socketAddress.toString();
        if (s.contains("/")) {
            s = s.substring(s.indexOf(47) + 1);
        }
        if (s.contains(":")) {
            s = s.substring(0, s.indexOf(58));
        }
        return s;
    }
    
    static {
        __OBFID = "CL_00001396";
    }
}
