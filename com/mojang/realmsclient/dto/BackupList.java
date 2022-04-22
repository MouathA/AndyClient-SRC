package com.mojang.realmsclient.dto;

import com.google.gson.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class BackupList
{
    private static final Logger LOGGER;
    public List backups;
    
    public static BackupList parse(final String s) {
        final JsonParser jsonParser = new JsonParser();
        final BackupList list = new BackupList();
        list.backups = new ArrayList();
        final JsonElement value = jsonParser.parse(s).getAsJsonObject().get("backups");
        if (value.isJsonArray()) {
            final Iterator iterator = value.getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                list.backups.add(Backup.parse(iterator.next()));
            }
        }
        return list;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
