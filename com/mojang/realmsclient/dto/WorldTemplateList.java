package com.mojang.realmsclient.dto;

import com.google.gson.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class WorldTemplateList extends ValueObject
{
    private static final Logger LOGGER;
    public List templates;
    
    public static WorldTemplateList parse(final String s) {
        final WorldTemplateList list = new WorldTemplateList();
        list.templates = new ArrayList();
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        if (asJsonObject.get("templates").isJsonArray()) {
            final Iterator iterator = asJsonObject.get("templates").getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                list.templates.add(WorldTemplate.parse(iterator.next().getAsJsonObject()));
            }
        }
        return list;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
