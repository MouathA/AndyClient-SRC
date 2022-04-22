package DTool.util;

import com.google.gson.*;

public class JsonUtils
{
    public static Gson gson;
    public static Gson prettyGson;
    public static JsonParser jsonParser;
    
    static {
        JsonUtils.gson = new Gson();
        JsonUtils.prettyGson = new GsonBuilder().setPrettyPrinting().create();
        JsonUtils.jsonParser = new JsonParser();
    }
}
