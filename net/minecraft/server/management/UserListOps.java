package net.minecraft.server.management;

import java.io.*;
import com.google.gson.*;
import com.mojang.authlib.*;
import java.util.*;

public class UserListOps extends UserList
{
    private static final String __OBFID;
    
    public UserListOps(final File file) {
        super(file);
    }
    
    @Override
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListOpsEntry(jsonObject);
    }
    
    @Override
    public String[] getKeys() {
        final String[] array = new String[this.getValues().size()];
        for (final UserListOpsEntry userListOpsEntry : this.getValues().values()) {
            final String[] array2 = array;
            final int n = 0;
            int n2 = 0;
            ++n2;
            array2[n] = ((GameProfile)userListOpsEntry.getValue()).getName();
        }
        return array;
    }
    
    protected String func_152699_b(final GameProfile gameProfile) {
        return gameProfile.getId().toString();
    }
    
    public GameProfile getGameProfileFromName(final String s) {
        for (final UserListOpsEntry userListOpsEntry : this.getValues().values()) {
            if (s.equalsIgnoreCase(((GameProfile)userListOpsEntry.getValue()).getName())) {
                return (GameProfile)userListOpsEntry.getValue();
            }
        }
        return null;
    }
    
    @Override
    protected String getObjectKey(final Object o) {
        return this.func_152699_b((GameProfile)o);
    }
    
    static {
        __OBFID = "CL_00001879";
    }
}
