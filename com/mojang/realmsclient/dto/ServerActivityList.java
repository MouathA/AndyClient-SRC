package com.mojang.realmsclient.dto;

import com.mojang.realmsclient.util.*;
import com.google.gson.*;
import java.util.*;

public class ServerActivityList
{
    public long periodInMillis;
    public List serverActivities;
    
    public ServerActivityList() {
        this.serverActivities = new ArrayList();
    }
    
    public static ServerActivityList parse(final String s) {
        final ServerActivityList list = new ServerActivityList();
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        list.periodInMillis = JsonUtils.getLongOr("periodInMillis", asJsonObject, -1L);
        final JsonElement value = asJsonObject.get("playerActivityDto");
        if (value != null && value.isJsonArray()) {
            final Iterator iterator = value.getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                list.serverActivities.add(ServerActivity.parse(iterator.next().getAsJsonObject()));
            }
        }
        return list;
    }
}
