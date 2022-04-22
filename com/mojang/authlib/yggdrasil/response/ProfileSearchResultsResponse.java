package com.mojang.authlib.yggdrasil.response;

import com.mojang.authlib.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class ProfileSearchResultsResponse extends Response
{
    private GameProfile[] profiles;
    
    public GameProfile[] getProfiles() {
        return this.profiles;
    }
    
    static GameProfile[] access$002(final ProfileSearchResultsResponse profileSearchResultsResponse, final GameProfile[] profiles) {
        return profileSearchResultsResponse.profiles = profiles;
    }
    
    public static class Serializer implements JsonDeserializer
    {
        @Override
        public ProfileSearchResultsResponse deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final ProfileSearchResultsResponse profileSearchResultsResponse = new ProfileSearchResultsResponse();
            if (jsonElement instanceof JsonObject) {
                final JsonObject jsonObject = (JsonObject)jsonElement;
                if (jsonObject.has("error")) {
                    profileSearchResultsResponse.setError(jsonObject.getAsJsonPrimitive("error").getAsString());
                }
                if (jsonObject.has("errorMessage")) {
                    profileSearchResultsResponse.setError(jsonObject.getAsJsonPrimitive("errorMessage").getAsString());
                }
                if (jsonObject.has("cause")) {
                    profileSearchResultsResponse.setError(jsonObject.getAsJsonPrimitive("cause").getAsString());
                }
            }
            else {
                ProfileSearchResultsResponse.access$002(profileSearchResultsResponse, (GameProfile[])jsonDeserializationContext.deserialize(jsonElement, GameProfile[].class));
            }
            return profileSearchResultsResponse;
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}
