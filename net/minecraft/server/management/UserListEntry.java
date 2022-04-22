package net.minecraft.server.management;

import com.google.gson.*;

public class UserListEntry
{
    private final Object value;
    private static final String __OBFID;
    
    public UserListEntry(final Object value) {
        this.value = value;
    }
    
    protected UserListEntry(final Object value, final JsonObject jsonObject) {
        this.value = value;
    }
    
    Object getValue() {
        return this.value;
    }
    
    boolean hasBanExpired() {
        return false;
    }
    
    protected void onSerialization(final JsonObject jsonObject) {
    }
    
    static {
        __OBFID = "CL_00001877";
    }
}
