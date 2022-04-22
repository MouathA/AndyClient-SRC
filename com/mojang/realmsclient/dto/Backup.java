package com.mojang.realmsclient.dto;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class Backup extends ValueObject
{
    private static final Logger LOGGER;
    public String backupId;
    public Date lastModifiedDate;
    public long size;
    private boolean uploadedVersion;
    public Map metadata;
    public Map changeList;
    
    public Backup() {
        this.uploadedVersion = false;
        this.metadata = new HashMap();
        this.changeList = new HashMap();
    }
    
    public static Backup parse(final JsonElement jsonElement) {
        final JsonObject asJsonObject = jsonElement.getAsJsonObject();
        final Backup backup = new Backup();
        backup.backupId = JsonUtils.getStringOr("backupId", asJsonObject, "");
        backup.lastModifiedDate = JsonUtils.getDateOr("lastModifiedDate", asJsonObject);
        backup.size = JsonUtils.getLongOr("size", asJsonObject, 0L);
        if (asJsonObject.has("metadata")) {
            for (final Map.Entry<K, JsonElement> entry : asJsonObject.getAsJsonObject("metadata").entrySet()) {
                if (!entry.getValue().isJsonNull()) {
                    backup.metadata.put(format((String)entry.getKey()), entry.getValue().getAsString());
                }
            }
        }
        return backup;
    }
    
    private static String format(final String s) {
        final String[] split = s.split("_");
        final StringBuilder sb = new StringBuilder();
        final String[] array = split;
        while (0 < array.length) {
            final String s2 = array[0];
            if (s2 != null && s2.length() >= 1) {
                if (s2.equals("of")) {
                    sb.append(s2).append(" ");
                }
                else {
                    sb.append(Character.toUpperCase(s2.charAt(0))).append(s2.substring(1, s2.length())).append(" ");
                }
            }
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public boolean isUploadedVersion() {
        return this.uploadedVersion;
    }
    
    public void setUploadedVersion(final boolean uploadedVersion) {
        this.uploadedVersion = uploadedVersion;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
