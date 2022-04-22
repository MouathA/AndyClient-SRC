package net.minecraft.server.management;

import java.io.*;
import com.google.gson.*;
import com.mojang.authlib.*;
import java.util.*;

public class UserListBans extends UserList
{
    private static final String __OBFID;
    
    public UserListBans(final File file) {
        super(file);
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListBansEntry(jsonObject);
    }
    
    public boolean isBanned(final GameProfile gameProfile) {
        return this.hasEntry(gameProfile);
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[this.getValues().size()];
        for (final UserListBansEntry userListBansEntry : this.getValues().values()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = ((GameProfile)userListBansEntry.getValue()).getName();
        }
        return array;
    }
    
    protected String getProfileId(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    public GameProfile isUsernameBanned(final String s) {
        for (final UserListBansEntry userListBansEntry : this.getValues().values()) {
            if (s.equalsIgnoreCase(((GameProfile)userListBansEntry.getValue()).getName())) {
                return (GameProfile)userListBansEntry.getValue();
            }
        }
        return null;
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.getProfileId((GameProfile)o);
    }
    
    static {
        __OBFID = "CL_00001873";
    }
}
