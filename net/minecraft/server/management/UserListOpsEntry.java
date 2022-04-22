package net.minecraft.server.management;

import com.mojang.authlib.*;
import com.google.gson.*;
import java.util.*;

public class UserListOpsEntry extends UserListEntry
{
    private final int field_152645_a;
    private static final String __OBFID;
    
    public UserListOpsEntry(final GameProfile gameProfile, final int field_152645_a) {
        super(gameProfile);
        this.field_152645_a = field_152645_a;
    }
    
    public UserListOpsEntry(final JsonObject jsonObject) {
        super(func_152643_b(jsonObject), jsonObject);
        this.field_152645_a = (jsonObject.has("level") ? jsonObject.get("level").getAsInt() : 0);
    }
    
    public int func_152644_a() {
        return this.field_152645_a;
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("uuid", (((GameProfile)this.getValue()).getId() == null) ? "" : ((GameProfile)this.getValue()).getId().toString());
            jsonObject.addProperty("name", ((GameProfile)this.getValue()).getName());
            super.onSerialization(jsonObject);
            jsonObject.addProperty("level", this.field_152645_a);
        }
    }
    
    private static GameProfile func_152643_b(final JsonObject jsonObject) {
        if (jsonObject.has("uuid") && jsonObject.has("name")) {
            return new GameProfile(UUID.fromString(jsonObject.get("uuid").getAsString()), jsonObject.get("name").getAsString());
        }
        return null;
    }
    
    static {
        __OBFID = "CL_00001878";
    }
}
