package net.minecraft.util;

import com.google.gson.*;

public class CombatPosition
{
    private static final Gson gson;
    private static final Gson PRETTY_PRINTING;
    
    static {
        gson = new Gson();
        PRETTY_PRINTING = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public String toJson() {
        return CombatPosition.gson.toJson(this);
    }
    
    public String toJson(final boolean b) {
        return b ? CombatPosition.PRETTY_PRINTING.toJson(this) : CombatPosition.gson.toJson(this);
    }
}
