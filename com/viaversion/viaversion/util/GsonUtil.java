package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.gson.*;

public final class GsonUtil
{
    private static final Gson GSON;
    
    public static Gson getGson() {
        return GsonUtil.GSON;
    }
    
    static {
        GSON = new GsonBuilder().create();
    }
}
