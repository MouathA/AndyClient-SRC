package Mood.Helpers;

import com.google.gson.*;
import com.mojang.util.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.net.*;
import java.io.*;

public class UUIDChecker
{
    public static final long FEBRUARY_2015 = 1422748800000L;
    private static Gson gson;
    private static final String UUID_URL;
    private static final String NAME_URL;
    private static Map uuidCache;
    private static Map nameCache;
    private static ExecutorService pool;
    private String name;
    private UUID id;
    
    static {
        UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
        NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
        UUIDChecker.gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
        UUIDChecker.uuidCache = new HashMap();
        UUIDChecker.nameCache = new HashMap();
        UUIDChecker.pool = Executors.newCachedThreadPool();
    }
    
    public static void getUUID(final String s, final Consumer consumer) {
        UUIDChecker.pool.execute(UUIDChecker::lambda$0);
    }
    
    public static UUID getUUID(final String s) {
        return getUUIDAt(s, System.currentTimeMillis());
    }
    
    public static void getUUIDAt(final String s, final long n, final Consumer consumer) {
        UUIDChecker.pool.execute(UUIDChecker::lambda$1);
    }
    
    public static UUID getUUIDAt(String lowerCase, final long n) {
        lowerCase = lowerCase.toLowerCase();
        if (UUIDChecker.uuidCache.containsKey(lowerCase)) {
            return UUIDChecker.uuidCache.get(lowerCase);
        }
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", lowerCase, n / 1000L)).openConnection();
        httpURLConnection.setReadTimeout(5000);
        final UUIDChecker uuidChecker = (UUIDChecker)UUIDChecker.gson.fromJson(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), UUIDChecker.class);
        UUIDChecker.uuidCache.put(lowerCase, uuidChecker.id);
        UUIDChecker.nameCache.put(uuidChecker.id, uuidChecker.name);
        return uuidChecker.id;
    }
    
    public static void getName(final UUID uuid, final Consumer consumer) {
        UUIDChecker.pool.execute(UUIDChecker::lambda$2);
    }
    
    public static String getName(final UUID uuid) {
        if (UUIDChecker.nameCache.containsKey(uuid)) {
            return UUIDChecker.nameCache.get(uuid);
        }
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/user/profiles/%s/names", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
        httpURLConnection.setReadTimeout(5000);
        final UUIDChecker[] array = (UUIDChecker[])UUIDChecker.gson.fromJson(new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())), UUIDChecker[].class);
        final UUIDChecker uuidChecker = array[array.length - 1];
        UUIDChecker.uuidCache.put(uuidChecker.name.toLowerCase(), uuid);
        UUIDChecker.nameCache.put(uuid, uuidChecker.name);
        return uuidChecker.name;
    }
    
    private static void lambda$0(final Consumer consumer, final String s) {
        consumer.accept(getUUID(s));
    }
    
    private static void lambda$1(final Consumer consumer, final String s, final long n) {
        consumer.accept(getUUIDAt(s, n));
    }
    
    private static void lambda$2(final Consumer consumer, final UUID uuid) {
        consumer.accept(getName(uuid));
    }
}
