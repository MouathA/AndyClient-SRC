package net.minecraft.network;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import java.util.*;
import com.google.gson.*;

public class ServerStatusResponse
{
    private IChatComponent serverMotd;
    private PlayerCountData playerCount;
    private MinecraftProtocolVersionIdentifier protocolVersion;
    private String favicon;
    private static final String __OBFID;
    
    public IChatComponent getServerDescription() {
        return this.serverMotd;
    }
    
    public void setServerDescription(final IChatComponent serverMotd) {
        this.serverMotd = serverMotd;
    }
    
    public PlayerCountData getPlayerCountData() {
        return this.playerCount;
    }
    
    public void setPlayerCountData(final PlayerCountData playerCount) {
        this.playerCount = playerCount;
    }
    
    public MinecraftProtocolVersionIdentifier getProtocolVersionInfo() {
        return this.protocolVersion;
    }
    
    public void setProtocolVersionInfo(final MinecraftProtocolVersionIdentifier protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    public void setFavicon(final String favicon) {
        this.favicon = favicon;
    }
    
    public String getFavicon() {
        return this.favicon;
    }
    
    static {
        __OBFID = "CL_00001385";
    }
    
    public static class MinecraftProtocolVersionIdentifier
    {
        private final String name;
        private final int protocol;
        private static final String __OBFID;
        
        public MinecraftProtocolVersionIdentifier(final String name, final int protocol) {
            this.name = name;
            this.protocol = protocol;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getProtocol() {
            return this.protocol;
        }
        
        static {
            __OBFID = "CL_00001389";
        }
        
        public static class Serializer implements JsonDeserializer, JsonSerializer
        {
            private static final String __OBFID;
            
            public MinecraftProtocolVersionIdentifier deserialize1(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
                final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "version");
                return new MinecraftProtocolVersionIdentifier(JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "name"), JsonUtils.getJsonObjectIntegerFieldValue(elementAsJsonObject, "protocol"));
            }
            
            public JsonElement serialize(final MinecraftProtocolVersionIdentifier minecraftProtocolVersionIdentifier, final Type type, final JsonSerializationContext jsonSerializationContext) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", minecraftProtocolVersionIdentifier.getName());
                jsonObject.addProperty("protocol", minecraftProtocolVersionIdentifier.getProtocol());
                return jsonObject;
            }
            
            @Override
            public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
                return this.serialize((MinecraftProtocolVersionIdentifier)o, type, jsonSerializationContext);
            }
            
            @Override
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
                return this.deserialize1(jsonElement, type, jsonDeserializationContext);
            }
            
            static {
                __OBFID = "CL_00001390";
            }
        }
    }
    
    public static class PlayerCountData
    {
        private final int maxPlayers;
        private final int onlinePlayerCount;
        private GameProfile[] players;
        private static final String __OBFID;
        
        public PlayerCountData(final int maxPlayers, final int onlinePlayerCount) {
            this.maxPlayers = maxPlayers;
            this.onlinePlayerCount = onlinePlayerCount;
        }
        
        public int getMaxPlayers() {
            return this.maxPlayers;
        }
        
        public int getOnlinePlayerCount() {
            return this.onlinePlayerCount;
        }
        
        public GameProfile[] getPlayers() {
            return this.players;
        }
        
        public void setPlayers(final GameProfile[] players) {
            this.players = players;
        }
        
        static {
            __OBFID = "CL_00001386";
        }
        
        public static class Serializer implements JsonDeserializer, JsonSerializer
        {
            private static final String __OBFID;
            
            public PlayerCountData deserialize1(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
                final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "players");
                final PlayerCountData playerCountData = new PlayerCountData(JsonUtils.getJsonObjectIntegerFieldValue(elementAsJsonObject, "max"), JsonUtils.getJsonObjectIntegerFieldValue(elementAsJsonObject, "online"));
                if (JsonUtils.jsonObjectFieldTypeIsArray(elementAsJsonObject, "sample")) {
                    final JsonArray jsonObjectJsonArrayField = JsonUtils.getJsonObjectJsonArrayField(elementAsJsonObject, "sample");
                    if (jsonObjectJsonArrayField.size() > 0) {
                        final GameProfile[] players = new GameProfile[jsonObjectJsonArrayField.size()];
                        while (0 < players.length) {
                            final JsonObject elementAsJsonObject2 = JsonUtils.getElementAsJsonObject(jsonObjectJsonArrayField.get(0), "player[" + 0 + "]");
                            players[0] = new GameProfile(UUID.fromString(JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject2, "id")), JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject2, "name"));
                            int n = 0;
                            ++n;
                        }
                        playerCountData.setPlayers(players);
                    }
                }
                return playerCountData;
            }
            
            public JsonElement serialize(final PlayerCountData playerCountData, final Type type, final JsonSerializationContext jsonSerializationContext) {
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("max", playerCountData.getMaxPlayers());
                jsonObject.addProperty("online", playerCountData.getOnlinePlayerCount());
                if (playerCountData.getPlayers() != null && playerCountData.getPlayers().length > 0) {
                    final JsonArray jsonArray = new JsonArray();
                    while (0 < playerCountData.getPlayers().length) {
                        final JsonObject jsonObject2 = new JsonObject();
                        final UUID id = playerCountData.getPlayers()[0].getId();
                        jsonObject2.addProperty("id", (id == null) ? "" : id.toString());
                        jsonObject2.addProperty("name", playerCountData.getPlayers()[0].getName());
                        jsonArray.add(jsonObject2);
                        int n = 0;
                        ++n;
                    }
                    jsonObject.add("sample", jsonArray);
                }
                return jsonObject;
            }
            
            @Override
            public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
                return this.serialize((PlayerCountData)o, type, jsonSerializationContext);
            }
            
            @Override
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
                return this.deserialize1(jsonElement, type, jsonDeserializationContext);
            }
            
            static {
                __OBFID = "CL_00001387";
            }
        }
    }
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final String __OBFID;
        
        public ServerStatusResponse deserialize1(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            final JsonObject elementAsJsonObject = JsonUtils.getElementAsJsonObject(jsonElement, "status");
            final ServerStatusResponse serverStatusResponse = new ServerStatusResponse();
            if (elementAsJsonObject.has("description")) {
                serverStatusResponse.setServerDescription((IChatComponent)jsonDeserializationContext.deserialize(elementAsJsonObject.get("description"), IChatComponent.class));
            }
            if (elementAsJsonObject.has("players")) {
                serverStatusResponse.setPlayerCountData((PlayerCountData)jsonDeserializationContext.deserialize(elementAsJsonObject.get("players"), PlayerCountData.class));
            }
            if (elementAsJsonObject.has("version")) {
                serverStatusResponse.setProtocolVersionInfo((MinecraftProtocolVersionIdentifier)jsonDeserializationContext.deserialize(elementAsJsonObject.get("version"), MinecraftProtocolVersionIdentifier.class));
            }
            if (elementAsJsonObject.has("favicon")) {
                serverStatusResponse.setFavicon(JsonUtils.getJsonObjectStringFieldValue(elementAsJsonObject, "favicon"));
            }
            return serverStatusResponse;
        }
        
        public JsonElement serialize(final ServerStatusResponse serverStatusResponse, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            if (serverStatusResponse.getServerDescription() != null) {
                jsonObject.add("description", jsonSerializationContext.serialize(serverStatusResponse.getServerDescription()));
            }
            if (serverStatusResponse.getPlayerCountData() != null) {
                jsonObject.add("players", jsonSerializationContext.serialize(serverStatusResponse.getPlayerCountData()));
            }
            if (serverStatusResponse.getProtocolVersionInfo() != null) {
                jsonObject.add("version", jsonSerializationContext.serialize(serverStatusResponse.getProtocolVersionInfo()));
            }
            if (serverStatusResponse.getFavicon() != null) {
                jsonObject.addProperty("favicon", serverStatusResponse.getFavicon());
            }
            return jsonObject;
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ServerStatusResponse)o, type, jsonSerializationContext);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize1(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00001388";
        }
    }
}
