package com.mojang.realmsclient.dto;

import com.google.gson.*;
import com.mojang.realmsclient.util.*;
import org.apache.logging.log4j.*;

public class WorldTemplate extends ValueObject
{
    private static final Logger LOGGER;
    public String id;
    public String name;
    public String version;
    public String author;
    public String link;
    public boolean minigame;
    public String image;
    public String trailer;
    public String recommendedPlayers;
    
    public WorldTemplate() {
        this.minigame = false;
    }
    
    public static WorldTemplate parse(final JsonObject jsonObject) {
        final WorldTemplate worldTemplate = new WorldTemplate();
        worldTemplate.id = JsonUtils.getStringOr("id", jsonObject, "");
        worldTemplate.name = JsonUtils.getStringOr("name", jsonObject, "");
        worldTemplate.version = JsonUtils.getStringOr("version", jsonObject, "");
        worldTemplate.author = JsonUtils.getStringOr("author", jsonObject, "");
        worldTemplate.link = JsonUtils.getStringOr("link", jsonObject, "");
        worldTemplate.image = JsonUtils.getStringOr("image", jsonObject, null);
        worldTemplate.trailer = JsonUtils.getStringOr("trailer", jsonObject, "");
        worldTemplate.recommendedPlayers = JsonUtils.getStringOr("recommendedPlayers", jsonObject, "");
        return worldTemplate;
    }
    
    public void setMinigame(final boolean minigame) {
        this.minigame = minigame;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
