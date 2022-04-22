package net.minecraft.server.management;

import java.text.*;
import net.minecraft.server.*;
import java.lang.reflect.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import com.google.common.io.*;
import org.apache.commons.io.*;
import java.util.*;
import java.io.*;
import com.google.common.collect.*;
import com.google.gson.*;

public class PlayerProfileCache
{
    public static final SimpleDateFormat dateFormat;
    private final Map field_152661_c;
    private final Map field_152662_d;
    private final LinkedList field_152663_e;
    private final MinecraftServer field_152664_f;
    protected final Gson gson;
    private final File usercacheFile;
    private static final ParameterizedType field_152666_h;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001888";
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        field_152666_h = new ParameterizedType() {
            private static final String __OBFID;
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { ProfileEntry.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            static {
                __OBFID = "CL_00001886";
            }
        };
    }
    
    public PlayerProfileCache(final MinecraftServer field_152664_f, final File usercacheFile) {
        this.field_152661_c = Maps.newHashMap();
        this.field_152662_d = Maps.newHashMap();
        this.field_152663_e = Lists.newLinkedList();
        this.field_152664_f = field_152664_f;
        this.usercacheFile = usercacheFile;
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(ProfileEntry.class, new Serializer(null));
        this.gson = gsonBuilder.create();
        this.func_152657_b();
    }
    
    private static GameProfile func_152650_a(final MinecraftServer minecraftServer, final String s) {
        final GameProfile[] array = { null };
        final ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback() {
            private static final String __OBFID;
            private final GameProfile[] val$var2;
            
            @Override
            public void onProfileLookupSucceeded(final GameProfile gameProfile) {
                this.val$var2[0] = gameProfile;
            }
            
            @Override
            public void onProfileLookupFailed(final GameProfile gameProfile, final Exception ex) {
                this.val$var2[0] = null;
            }
            
            static {
                __OBFID = "CL_00001887";
            }
        };
        minecraftServer.getGameProfileRepository().findProfilesByNames(new String[] { s }, Agent.MINECRAFT, profileLookupCallback);
        if (!minecraftServer.isServerInOnlineMode() && array[0] == null) {
            profileLookupCallback.onProfileLookupSucceeded(new GameProfile(EntityPlayer.getUUID(new GameProfile(null, s)), s));
        }
        return array[0];
    }
    
    public void func_152649_a(final GameProfile gameProfile) {
        this.func_152651_a(gameProfile, null);
    }
    
    private void func_152651_a(final GameProfile gameProfile, Date time) {
        final UUID id = gameProfile.getId();
        if (time == null) {
            final Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            instance.add(2, 1);
            time = instance.getTime();
        }
        final String lowerCase = gameProfile.getName().toLowerCase(Locale.ROOT);
        final ProfileEntry profileEntry = new ProfileEntry(gameProfile, time, null);
        if (this.field_152662_d.containsKey(id)) {
            this.field_152661_c.remove(((ProfileEntry)this.field_152662_d.get(id)).func_152668_a().getName().toLowerCase(Locale.ROOT));
            this.field_152661_c.put(gameProfile.getName().toLowerCase(Locale.ROOT), profileEntry);
            this.field_152663_e.remove(gameProfile);
        }
        else {
            this.field_152662_d.put(id, profileEntry);
            this.field_152661_c.put(lowerCase, profileEntry);
        }
        this.field_152663_e.addFirst(gameProfile);
    }
    
    public GameProfile getGameProfileForUsername(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ROOT);
        ProfileEntry profileEntry = this.field_152661_c.get(lowerCase);
        if (profileEntry != null && new Date().getTime() >= ProfileEntry.access$0(profileEntry).getTime()) {
            this.field_152662_d.remove(profileEntry.func_152668_a().getId());
            this.field_152661_c.remove(profileEntry.func_152668_a().getName().toLowerCase(Locale.ROOT));
            this.field_152663_e.remove(profileEntry.func_152668_a());
            profileEntry = null;
        }
        if (profileEntry != null) {
            final GameProfile func_152668_a = profileEntry.func_152668_a();
            this.field_152663_e.remove(func_152668_a);
            this.field_152663_e.addFirst(func_152668_a);
        }
        else {
            final GameProfile func_152650_a = func_152650_a(this.field_152664_f, lowerCase);
            if (func_152650_a != null) {
                this.func_152649_a(func_152650_a);
                profileEntry = this.field_152661_c.get(lowerCase);
            }
        }
        this.func_152658_c();
        return (profileEntry == null) ? null : profileEntry.func_152668_a();
    }
    
    public String[] func_152654_a() {
        final ArrayList arrayList = Lists.newArrayList(this.field_152661_c.keySet());
        return arrayList.toArray(new String[arrayList.size()]);
    }
    
    public GameProfile func_152652_a(final UUID uuid) {
        final ProfileEntry profileEntry = this.field_152662_d.get(uuid);
        return (profileEntry == null) ? null : profileEntry.func_152668_a();
    }
    
    private ProfileEntry func_152653_b(final UUID uuid) {
        final ProfileEntry profileEntry = this.field_152662_d.get(uuid);
        if (profileEntry != null) {
            final GameProfile func_152668_a = profileEntry.func_152668_a();
            this.field_152663_e.remove(func_152668_a);
            this.field_152663_e.addFirst(func_152668_a);
        }
        return profileEntry;
    }
    
    public void func_152657_b() {
        final BufferedReader reader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
        final List list = (List)this.gson.fromJson(reader, PlayerProfileCache.field_152666_h);
        IOUtils.closeQuietly(reader);
        if (list != null) {
            this.field_152661_c.clear();
            this.field_152662_d.clear();
            this.field_152663_e.clear();
            for (final ProfileEntry profileEntry : Lists.reverse(list)) {
                if (profileEntry != null) {
                    this.func_152651_a(profileEntry.func_152668_a(), profileEntry.func_152670_b());
                }
            }
        }
    }
    
    public void func_152658_c() {
        final String json = this.gson.toJson(this.func_152656_a(1000));
        final BufferedWriter writer = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
        writer.write(json);
        IOUtils.closeQuietly(writer);
    }
    
    private List func_152656_a(final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<GameProfile> iterator = Lists.newArrayList(Iterators.limit(this.field_152663_e.iterator(), n)).iterator();
        while (iterator.hasNext()) {
            final ProfileEntry func_152653_b = this.func_152653_b(iterator.next().getId());
            if (func_152653_b != null) {
                arrayList.add(func_152653_b);
            }
        }
        return arrayList;
    }
    
    class ProfileEntry
    {
        private final GameProfile field_152672_b;
        private final Date field_152673_c;
        private static final String __OBFID;
        final PlayerProfileCache this$0;
        
        private ProfileEntry(final PlayerProfileCache this$0, final GameProfile field_152672_b, final Date field_152673_c) {
            this.this$0 = this$0;
            this.field_152672_b = field_152672_b;
            this.field_152673_c = field_152673_c;
        }
        
        public GameProfile func_152668_a() {
            return this.field_152672_b;
        }
        
        public Date func_152670_b() {
            return this.field_152673_c;
        }
        
        ProfileEntry(final PlayerProfileCache playerProfileCache, final GameProfile gameProfile, final Date date, final Object o) {
            this(playerProfileCache, gameProfile, date);
        }
        
        static Date access$0(final ProfileEntry profileEntry) {
            return profileEntry.field_152673_c;
        }
        
        static {
            __OBFID = "CL_00001885";
        }
    }
    
    class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final String __OBFID;
        final PlayerProfileCache this$0;
        
        private Serializer(final PlayerProfileCache this$0) {
            this.this$0 = this$0;
        }
        
        public JsonElement func_152676_a(final ProfileEntry profileEntry, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", profileEntry.func_152668_a().getName());
            final UUID id = profileEntry.func_152668_a().getId();
            jsonObject.addProperty("uuid", (id == null) ? "" : id.toString());
            jsonObject.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(profileEntry.func_152670_b()));
            return jsonObject;
        }
        
        public ProfileEntry func_152675_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final JsonElement value = asJsonObject.get("name");
            final JsonElement value2 = asJsonObject.get("uuid");
            final JsonElement value3 = asJsonObject.get("expiresOn");
            if (value == null || value2 == null) {
                return null;
            }
            final String asString = value2.getAsString();
            final String asString2 = value.getAsString();
            Date parse = null;
            if (value3 != null) {
                parse = PlayerProfileCache.dateFormat.parse(value3.getAsString());
            }
            if (asString2 != null && asString != null) {
                final UUID fromString = UUID.fromString(asString);
                final PlayerProfileCache this$0 = this.this$0;
                this$0.getClass();
                return this$0.new ProfileEntry(new GameProfile(fromString, asString2), parse, null);
            }
            return null;
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.func_152676_a((ProfileEntry)o, type, jsonSerializationContext);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.func_152675_a(jsonElement, type, jsonDeserializationContext);
        }
        
        Serializer(final PlayerProfileCache playerProfileCache, final Object o) {
            this(playerProfileCache);
        }
        
        static {
            __OBFID = "CL_00001884";
        }
    }
}
