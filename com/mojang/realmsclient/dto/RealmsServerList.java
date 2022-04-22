package com.mojang.realmsclient.dto;

import com.google.gson.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class RealmsServerList extends ValueObject
{
    private static final Logger LOGGER;
    public List servers;
    
    public static RealmsServerList parse(final String s) {
        final RealmsServerList list = new RealmsServerList();
        list.servers = new ArrayList();
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        if (asJsonObject.get("servers").isJsonArray()) {
            final Iterator iterator = asJsonObject.get("servers").getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                list.servers.add(RealmsServer.parse(iterator.next().getAsJsonObject()));
            }
        }
        return list;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
