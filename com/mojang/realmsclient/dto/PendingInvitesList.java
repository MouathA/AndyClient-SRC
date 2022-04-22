package com.mojang.realmsclient.dto;

import com.google.common.collect.*;
import com.google.gson.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class PendingInvitesList extends ValueObject
{
    private static final Logger LOGGER;
    public List pendingInvites;
    
    public PendingInvitesList() {
        this.pendingInvites = Lists.newArrayList();
    }
    
    public static PendingInvitesList parse(final String s) {
        final PendingInvitesList list = new PendingInvitesList();
        final JsonObject asJsonObject = new JsonParser().parse(s).getAsJsonObject();
        if (asJsonObject.get("invites").isJsonArray()) {
            final Iterator iterator = asJsonObject.get("invites").getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                list.pendingInvites.add(PendingInvite.parse(iterator.next().getAsJsonObject()));
            }
        }
        return list;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
