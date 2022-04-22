package Mood.AndyConnection;

import com.google.gson.*;

public class AndyPlayer
{
    private static final Gson gson;
    private static final Gson PRETTY_PRINTING;
    
    static {
        gson = new Gson();
        PRETTY_PRINTING = new GsonBuilder().setPrettyPrinting().create();
    }
    
    public String toJson() {
        return AndyPlayer.gson.toJson(this);
    }
    
    public String toJson(final boolean b) {
        return b ? AndyPlayer.PRETTY_PRINTING.toJson(this) : AndyPlayer.gson.toJson(this);
    }
}
