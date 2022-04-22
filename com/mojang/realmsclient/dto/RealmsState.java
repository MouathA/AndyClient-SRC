package com.mojang.realmsclient.dto;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;

public class RealmsState
{
    private static final Logger LOGGER;
    private String statusMessage;
    private String buyLink;
    
    public static RealmsState parse(final String s) {
        final RealmsState realmsState = new RealmsState();
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        realmsState.statusMessage = JsonUtils.getStringOr("statusMessage", asJsonObject, null);
        realmsState.buyLink = JsonUtils.getStringOr("buyLink", asJsonObject, null);
        return realmsState;
    }
    
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    public String getBuyLink() {
        return this.buyLink;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
