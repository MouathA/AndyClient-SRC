package net.minecraft.client.resources;

import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import net.minecraft.util.*;
import com.google.gson.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;

public class ResourceIndex
{
    private static final Logger field_152783_a;
    private final Map field_152784_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001831";
        field_152783_a = LogManager.getLogger();
    }
    
    public ResourceIndex(final File file, final String s) {
        this.field_152784_b = Maps.newHashMap();
        if (s != null) {
            final File file2 = new File(file, "objects");
            final BufferedReader reader = Files.newReader(new File(file, "indexes/" + s + ".json"), Charsets.UTF_8);
            final JsonObject jsonObjectFieldOrDefault = JsonUtils.getJsonObjectFieldOrDefault(new JsonParser().parse(reader).getAsJsonObject(), "objects", null);
            if (jsonObjectFieldOrDefault != null) {
                for (final Map.Entry<K, JsonObject> entry : jsonObjectFieldOrDefault.entrySet()) {
                    final JsonObject jsonObject = entry.getValue();
                    final String[] split = ((String)entry.getKey()).split("/", 2);
                    final String s2 = (split.length == 1) ? split[0] : (String.valueOf(split[0]) + ":" + split[1]);
                    final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(jsonObject, "hash");
                    this.field_152784_b.put(s2, new File(file2, String.valueOf(jsonObjectStringFieldValue.substring(0, 2)) + "/" + jsonObjectStringFieldValue));
                }
            }
            IOUtils.closeQuietly(reader);
        }
    }
    
    public Map func_152782_a() {
        return this.field_152784_b;
    }
}
