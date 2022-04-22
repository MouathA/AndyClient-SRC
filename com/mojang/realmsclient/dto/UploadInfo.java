package com.mojang.realmsclient.dto;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import org.apache.logging.log4j.*;

public class UploadInfo
{
    private static final Logger LOGGER;
    private boolean worldClosed;
    private String token;
    private String uploadEndpoint;
    
    public UploadInfo() {
        this.token = "";
        this.uploadEndpoint = "";
    }
    
    public static UploadInfo parse(final String s) {
        final UploadInfo uploadInfo = new UploadInfo();
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        uploadInfo.worldClosed = JsonUtils.getBooleanOr("worldClosed", asJsonObject, false);
        uploadInfo.token = JsonUtils.getStringOr("token", asJsonObject, null);
        uploadInfo.uploadEndpoint = JsonUtils.getStringOr("uploadEndpoint", asJsonObject, null);
        return uploadInfo;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public String getUploadEndpoint() {
        return this.uploadEndpoint;
    }
    
    public boolean isWorldClosed() {
        return this.worldClosed;
    }
    
    public void setToken(final String token) {
        this.token = token;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
