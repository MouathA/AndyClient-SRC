package tv.twitch.broadcast;

import java.util.*;

public enum AuthFlag
{
    TTV_AuthOption_None("TTV_AuthOption_None", 0, 0), 
    TTV_AuthOption_Broadcast("TTV_AuthOption_Broadcast", 1, 1), 
    TTV_AuthOption_Chat("TTV_AuthOption_Chat", 2, 2);
    
    private static Map s_Map;
    private int m_Value;
    private static final AuthFlag[] $VALUES;
    
    public static AuthFlag lookupValue(final int n) {
        return AuthFlag.s_Map.get(n);
    }
    
    public static int getNativeValue(final HashSet set) {
        if (set == null) {
            return AuthFlag.TTV_AuthOption_None.getValue();
        }
        for (final AuthFlag authFlag : set) {
            if (authFlag != null) {
                final int n = 0x0 | authFlag.getValue();
            }
        }
        return 0;
    }
    
    private AuthFlag(final String s, final int n, final int value) {
        this.m_Value = value;
    }
    
    public int getValue() {
        return this.m_Value;
    }
    
    static {
        $VALUES = new AuthFlag[] { AuthFlag.TTV_AuthOption_None, AuthFlag.TTV_AuthOption_Broadcast, AuthFlag.TTV_AuthOption_Chat };
        AuthFlag.s_Map = new HashMap();
        for (final AuthFlag authFlag : EnumSet.allOf(AuthFlag.class)) {
            AuthFlag.s_Map.put(authFlag.getValue(), authFlag);
        }
    }
}
