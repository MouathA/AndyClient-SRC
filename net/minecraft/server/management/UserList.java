package net.minecraft.server.management;

import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.common.base.*;
import com.google.common.io.*;
import org.apache.commons.io.*;
import java.io.*;
import com.google.gson.*;

public class UserList
{
    protected static final Logger logger;
    protected final Gson gson;
    private final File saveFile;
    private final Map values;
    private boolean lanServer;
    private static final ParameterizedType saveFileFormat;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001876";
        logger = LogManager.getLogger();
        saveFileFormat = new ParameterizedType() {
            private static final String __OBFID;
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { UserListEntry.class };
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
                __OBFID = "CL_00001875";
            }
        };
    }
    
    public UserList(final File saveFile) {
        this.values = Maps.newHashMap();
        this.lanServer = true;
        this.saveFile = saveFile;
        final GsonBuilder setPrettyPrinting = new GsonBuilder().setPrettyPrinting();
        setPrettyPrinting.registerTypeHierarchyAdapter(UserListEntry.class, new Serializer(null));
        this.gson = setPrettyPrinting.create();
    }
    
    public boolean isLanServer() {
        return this.lanServer;
    }
    
    public void setLanServer(final boolean lanServer) {
        this.lanServer = lanServer;
    }
    
    public void addEntry(final UserListEntry userListEntry) {
        this.values.put(this.getObjectKey(userListEntry.getValue()), userListEntry);
        this.writeChanges();
    }
    
    public UserListEntry getEntry(final Object o) {
        this.removeExpired();
        return this.values.get(this.getObjectKey(o));
    }
    
    public void removeEntry(final Object o) {
        this.values.remove(this.getObjectKey(o));
        this.writeChanges();
    }
    
    public String[] getKeys() {
        return (String[])this.values.keySet().toArray(new String[this.values.size()]);
    }
    
    protected String getObjectKey(final Object o) {
        return o.toString();
    }
    
    protected boolean hasEntry(final Object o) {
        return this.values.containsKey(this.getObjectKey(o));
    }
    
    private void removeExpired() {
        final ArrayList arrayList = Lists.newArrayList();
        for (final UserListEntry userListEntry : this.values.values()) {
            if (userListEntry.hasBanExpired()) {
                arrayList.add(userListEntry.getValue());
            }
        }
        final Iterator<Object> iterator2 = arrayList.iterator();
        while (iterator2.hasNext()) {
            this.values.remove(iterator2.next());
        }
    }
    
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListEntry(null, jsonObject);
    }
    
    protected Map getValues() {
        return this.values;
    }
    
    public void writeChanges() throws IOException {
        final String json = this.gson.toJson(this.values.values());
        final BufferedWriter writer = Files.newWriter(this.saveFile, Charsets.UTF_8);
        writer.write(json);
        IOUtils.closeQuietly(writer);
    }
    
    class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final String __OBFID;
        final UserList this$0;
        
        private Serializer(final UserList this$0) {
            this.this$0 = this$0;
        }
        
        public JsonElement serializeEntry(final UserListEntry userListEntry, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            userListEntry.onSerialization(jsonObject);
            return jsonObject;
        }
        
        public UserListEntry deserializeEntry(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            if (jsonElement.isJsonObject()) {
                return this.this$0.createEntry(jsonElement.getAsJsonObject());
            }
            return null;
        }
        
        @Override
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serializeEntry((UserListEntry)o, type, jsonSerializationContext);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.deserializeEntry(jsonElement, type, jsonDeserializationContext);
        }
        
        Serializer(final UserList list, final Object o) {
            this(list);
        }
        
        static {
            __OBFID = "CL_00001874";
        }
    }
}
