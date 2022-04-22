package com.mojang.realmsclient.client;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;

public class RealmsError
{
    private static final Logger LOGGER;
    private String errorMessage;
    private int errorCode;
    
    public RealmsError(final String s) {
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        this.errorMessage = JsonUtils.getStringOr("errorMsg", asJsonObject, "");
        this.errorCode = JsonUtils.getIntOr("errorCode", asJsonObject, -1);
    }
    
    public String getErrorMessage() {
        return this.errorMessage;
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
