package net.minecraft.server.management;

import java.util.*;
import com.google.gson.*;

public class IPBanEntry extends BanEntry
{
    private static final String __OBFID;
    
    public IPBanEntry(final String s) {
        this(s, null, null, null, null);
    }
    
    public IPBanEntry(final String s, final Date date, final String s2, final Date date2, final String s3) {
        super(s, date, s2, date2, s3);
    }
    
    public IPBanEntry(final JsonObject jsonObject) {
        super(func_152647_b(jsonObject), jsonObject);
    }
    
    private static String func_152647_b(final JsonObject jsonObject) {
        return jsonObject.has("ip") ? jsonObject.get("ip").getAsString() : null;
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("ip", (String)this.getValue());
            super.onSerialization(jsonObject);
        }
    }
    
    static {
        __OBFID = "CL_00001883";
    }
}
