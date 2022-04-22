package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;

public class UserListWhitelistEntry extends UserListEntry
{
    private static final String __OBFID;
    
    public UserListWhitelistEntry(final GameProfile gameProfile) {
        super(gameProfile);
    }
    
    public UserListWhitelistEntry(final JsonObject jsonObject) {
        super(func_152646_b(jsonObject), jsonObject);
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("uuid", (((GameProfile)this.getValue()).getId() == null) ? "" : ((GameProfile)this.getValue()).getId().toString());
            jsonObject.addProperty("name", ((GameProfile)this.getValue()).getName());
            super.onSerialization(jsonObject);
        }
    }
    
    private static GameProfile func_152646_b(final JsonObject jsonObject) {
        if (jsonObject.has("uuid") && jsonObject.has("name")) {
            return new GameProfile(UUID.fromString(jsonObject.get("uuid").getAsString()), jsonObject.get("name").getAsString());
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00001870";
    }
}
