package com.mojang.realmsclient.dto;

import com.google.gson.*;
import java.util.*;

public class Ops
{
    public Set ops;
    
    public Ops() {
        this.ops = new HashSet();
    }
    
    public static Ops parse(final String s) {
        final Ops ops = new Ops();
        final JsonElement value = new JsonParser().parse(s).getAsJsonObject().get("ops");
        if (value.isJsonArray()) {
            final Iterator iterator = value.getAsJsonArray().iterator();
            while (iterator.hasNext()) {
                ops.ops.add(iterator.next().getAsString());
            }
        }
        return ops;
    }
}
