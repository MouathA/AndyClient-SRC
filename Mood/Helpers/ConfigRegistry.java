package Mood.Helpers;

import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;
import java.io.*;
import com.google.gson.*;

public class ConfigRegistry
{
    private File file;
    private final List comparators;
    protected final CopyOnWriteArrayList list;
    
    public ConfigRegistry() {
        this.comparators = Collections.synchronizedList(new ArrayList<Object>());
        this.list = new CopyOnWriteArrayList();
    }
    
    public CopyOnWriteArrayList getList() {
        return this.list;
    }
    
    public void save(final JsonElement jsonElement, final JsonObject jsonObject) {
    }
    
    public void save() {
        final JsonObject jsonObject = new JsonObject();
        this.getList().stream().forEach(this::lambda$0);
        final FileWriter fileWriter = new FileWriter(this.file);
        fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
        fileWriter.flush();
        if (fileWriter != null) {
            fileWriter.close();
        }
    }
    
    private void lambda$0(final JsonObject jsonObject, final Object o) {
        this.save(new JsonObject(), jsonObject);
    }
}
