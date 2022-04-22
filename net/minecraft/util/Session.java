package net.minecraft.util;

import com.mojang.authlib.*;
import com.mojang.util.*;
import java.util.*;
import com.google.common.collect.*;

public class Session
{
    private final String username;
    private final String playerID;
    private final String token;
    private final Type sessionType;
    private static final String __OBFID;
    
    public Session(final String username, final String playerID, final String token, final String sessionType) {
        this.username = username;
        this.playerID = playerID;
        this.token = token;
        this.sessionType = Type.setSessionType(sessionType);
    }
    
    public String getSessionID() {
        return "token:" + this.token + ":" + this.playerID;
    }
    
    public String getPlayerID() {
        return this.playerID;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public GameProfile getProfile() {
        return new GameProfile(UUIDTypeAdapter.fromString(this.getPlayerID()), this.getUsername());
    }
    
    public Type getSessionType() {
        return this.sessionType;
    }
    
    static {
        __OBFID = "CL_00000659";
    }
    
    public enum Type
    {
        LEGACY("LEGACY", 0, "LEGACY", 0, "legacy"), 
        MOJANG("MOJANG", 1, "MOJANG", 1, "mojang");
        
        private static final Map field_152425_c;
        private final String sessionType;
        private static final Type[] $VALUES;
        private static final String __OBFID;
        private static final Type[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00001851";
            ENUM$VALUES = new Type[] { Type.LEGACY, Type.MOJANG };
            field_152425_c = Maps.newHashMap();
            $VALUES = new Type[] { Type.LEGACY, Type.MOJANG };
            final Type[] values = values();
            while (0 < values.length) {
                final Type type = values[0];
                Type.field_152425_c.put(type.sessionType, type);
                int n = 0;
                ++n;
            }
        }
        
        private Type(final String s, final int n, final String s2, final int n2, final String sessionType) {
            this.sessionType = sessionType;
        }
        
        public static Type setSessionType(final String s) {
            return Type.field_152425_c.get(s.toLowerCase());
        }
    }
}
