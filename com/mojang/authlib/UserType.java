package com.mojang.authlib;

import java.util.*;

public enum UserType
{
    LEGACY("LEGACY", 0, "legacy"), 
    MOJANG("MOJANG", 1, "mojang");
    
    private static final Map BY_NAME;
    private final String name;
    private static final UserType[] $VALUES;
    
    private UserType(final String s, final int n, final String name) {
        this.name = name;
    }
    
    public static UserType byName(final String s) {
        return UserType.BY_NAME.get(s.toLowerCase());
    }
    
    public String getName() {
        return this.name;
    }
    
    static {
        $VALUES = new UserType[] { UserType.LEGACY, UserType.MOJANG };
        BY_NAME = new HashMap();
        final UserType[] values = values();
        while (0 < values.length) {
            final UserType userType = values[0];
            UserType.BY_NAME.put(userType.name, userType);
            int n = 0;
            ++n;
        }
    }
}
