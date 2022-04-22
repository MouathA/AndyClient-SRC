package com.mojang.realmsclient.dto;

import com.google.gson.*;
import com.mojang.realmsclient.util.*;
import org.apache.logging.log4j.*;

public class RealmsServerAddress extends ValueObject
{
    private static final Logger LOGGER;
    public String address;
    
    public static RealmsServerAddress parse(final String s) {
        final JsonParser jsonParser = new JsonParser();
        final RealmsServerAddress realmsServerAddress = new RealmsServerAddress();
        realmsServerAddress.address = JsonUtils.getStringOr("address", jsonParser.parse(s).getAsJsonObject(), null);
        return realmsServerAddress;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
