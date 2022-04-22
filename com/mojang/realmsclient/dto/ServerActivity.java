package com.mojang.realmsclient.dto;

import com.google.gson.*;
import com.mojang.realmsclient.util.*;

public class ServerActivity
{
    public String profileUuid;
    public long joinTime;
    public long leaveTime;
    
    public static ServerActivity parse(final JsonObject jsonObject) {
        final ServerActivity serverActivity = new ServerActivity();
        serverActivity.profileUuid = JsonUtils.getStringOr("profileUuid", jsonObject, null);
        serverActivity.joinTime = JsonUtils.getLongOr("joinTime", jsonObject, Long.MIN_VALUE);
        serverActivity.leaveTime = JsonUtils.getLongOr("leaveTime", jsonObject, Long.MIN_VALUE);
        return serverActivity;
    }
}
