package com.mojang.realmsclient.util;

import java.util.*;

public class UploadTokenCache
{
    private static Map tokenCache;
    
    public static String get(final long n) {
        return UploadTokenCache.tokenCache.get(n);
    }
    
    public static void invalidate(final long n) {
        UploadTokenCache.tokenCache.remove(n);
    }
    
    public static void put(final long n, final String s) {
        UploadTokenCache.tokenCache.put(n, s);
    }
    
    static {
        UploadTokenCache.tokenCache = new HashMap();
    }
}
