package com.mojang.authlib.yggdrasil;

import java.lang.reflect.*;
import com.mojang.authlib.properties.*;
import java.util.*;
import com.mojang.util.*;
import com.mojang.authlib.minecraft.*;
import com.mojang.authlib.*;
import java.net.*;
import com.mojang.authlib.yggdrasil.response.*;
import org.apache.commons.lang3.*;
import com.mojang.authlib.exceptions.*;
import com.google.gson.*;

public class YggdrasilAuthenticationService extends HttpAuthenticationService
{
    private final String clientToken;
    private final Gson gson;
    
    public YggdrasilAuthenticationService(final Proxy proxy, final String clientToken) {
        super(proxy);
        this.clientToken = clientToken;
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameProfile.class, new GameProfileSerializer(null));
        gsonBuilder.registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer());
        gsonBuilder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
        gsonBuilder.registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer());
        this.gson = gsonBuilder.create();
    }
    
    @Override
    public UserAuthentication createUserAuthentication(final Agent agent) {
        return new YggdrasilUserAuthentication(this, agent);
    }
    
    @Override
    public MinecraftSessionService createMinecraftSessionService() {
        return new YggdrasilMinecraftSessionService(this);
    }
    
    @Override
    public GameProfileRepository createProfileRepository() {
        return new YggdrasilGameProfileRepository(this);
    }
    
    protected Response makeRequest(final URL url, final Object o, final Class clazz) throws AuthenticationException {
        final Response response = (Response)this.gson.fromJson((o == null) ? this.performGetRequest(url) : this.performPostRequest(url, this.gson.toJson(o), "application/json"), clazz);
        if (response == null) {
            return null;
        }
        if (!StringUtils.isNotBlank(response.getError())) {
            return response;
        }
        if ("UserMigratedException".equals(response.getCause())) {
            throw new UserMigratedException(response.getErrorMessage());
        }
        if (response.getError().equals("ForbiddenOperationException")) {
            throw new InvalidCredentialsException(response.getErrorMessage());
        }
        throw new AuthenticationException(response.getErrorMessage());
    }
    
    public String getClientToken() {
        return this.clientToken;
    }
    
    private static class GameProfileSerializer implements JsonSerializer, JsonDeserializer
    {
        private GameProfileSerializer() {
        }
        
        @Override
        public GameProfile deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject jsonObject = (JsonObject)jsonElement;
            return new GameProfile(jsonObject.has("id") ? ((UUID)jsonDeserializationContext.deserialize(jsonObject.get("id"), UUID.class)) : null, jsonObject.has("name") ? jsonObject.getAsJsonPrimitive("name").getAsString() : null);
        }
        
        public JsonElement serialize(final GameProfile gameProfile, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            if (gameProfile.getId() != null) {
                jsonObject.add("id", jsonSerializationContext.serialize(gameProfile.getId()));
            }
            if (gameProfile.getName() != null) {
                jsonObject.addProperty("name", gameProfile.getName());
            }
            return jsonObject;
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((GameProfile)o, type, jsonSerializationContext);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        GameProfileSerializer(final YggdrasilAuthenticationService$1 object) {
            this();
        }
    }
}
