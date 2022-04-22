package com.mojang.realmsclient.dto;

import java.util.*;
import com.google.gson.*;
import com.mojang.realmsclient.util.*;
import org.apache.logging.log4j.*;

public class PendingInvite extends ValueObject
{
    private static final Logger LOGGER;
    public String invitationId;
    public String worldName;
    public String worldOwnerName;
    public String worldOwnerUuid;
    public Date date;
    
    public static PendingInvite parse(final JsonObject jsonObject) {
        final PendingInvite pendingInvite = new PendingInvite();
        pendingInvite.invitationId = JsonUtils.getStringOr("invitationId", jsonObject, "");
        pendingInvite.worldName = JsonUtils.getStringOr("worldName", jsonObject, "");
        pendingInvite.worldOwnerName = JsonUtils.getStringOr("worldOwnerName", jsonObject, "");
        pendingInvite.worldOwnerUuid = JsonUtils.getStringOr("worldOwnerUuid", jsonObject, "");
        pendingInvite.date = JsonUtils.getDateOr("date", jsonObject);
        return pendingInvite;
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
