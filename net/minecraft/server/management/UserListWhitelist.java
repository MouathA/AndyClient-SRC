package net.minecraft.server.management;

import java.io.*;
import com.google.gson.*;
import com.mojang.authlib.*;
import java.util.*;

public class UserListWhitelist extends UserList
{
    private static final String __OBFID;
    
    public UserListWhitelist(final File file) {
        super(file);
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListWhitelistEntry(jsonObject);
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[this.getValues().size()];
        for (final UserListWhitelistEntry userListWhitelistEntry : this.getValues().values()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = ((GameProfile)userListWhitelistEntry.getValue()).getName();
        }
        return array;
    }
    
    protected String func_152704_b(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    public GameProfile func_152706_a(final String s) {
        for (final UserListWhitelistEntry userListWhitelistEntry : this.getValues().values()) {
            if (s.equalsIgnoreCase(((GameProfile)userListWhitelistEntry.getValue()).getName())) {
                return (GameProfile)userListWhitelistEntry.getValue();
            }
        }
        return null;
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.func_152704_b((GameProfile)o);
    }
    
    static {
        __OBFID = "CL_00001871";
    }
}
