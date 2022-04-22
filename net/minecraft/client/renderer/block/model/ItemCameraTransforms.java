package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import com.google.gson.*;

public class ItemCameraTransforms
{
    public static final ItemCameraTransforms field_178357_a;
    public final ItemTransformVec3f field_178355_b;
    public final ItemTransformVec3f field_178356_c;
    public final ItemTransformVec3f field_178353_d;
    public final ItemTransformVec3f field_178354_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002482";
        field_178357_a = new ItemCameraTransforms(ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a, ItemTransformVec3f.field_178366_a);
    }
    
    public ItemCameraTransforms(final ItemTransformVec3f field_178355_b, final ItemTransformVec3f field_178356_c, final ItemTransformVec3f field_178353_d, final ItemTransformVec3f field_178354_e) {
        this.field_178355_b = field_178355_b;
        this.field_178356_c = field_178356_c;
        this.field_178353_d = field_178353_d;
        this.field_178354_e = field_178354_e;
    }
    
    static class Deserializer implements JsonDeserializer
    {
        private static final String __OBFID;
        
        public ItemCameraTransforms func_178352_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            ItemTransformVec3f field_178366_a = ItemTransformVec3f.field_178366_a;
            ItemTransformVec3f field_178366_a2 = ItemTransformVec3f.field_178366_a;
            ItemTransformVec3f field_178366_a3 = ItemTransformVec3f.field_178366_a;
            ItemTransformVec3f field_178366_a4 = ItemTransformVec3f.field_178366_a;
            if (asJsonObject.has("thirdperson")) {
                field_178366_a = (ItemTransformVec3f)jsonDeserializationContext.deserialize(asJsonObject.get("thirdperson"), ItemTransformVec3f.class);
            }
            if (asJsonObject.has("firstperson")) {
                field_178366_a2 = (ItemTransformVec3f)jsonDeserializationContext.deserialize(asJsonObject.get("firstperson"), ItemTransformVec3f.class);
            }
            if (asJsonObject.has("head")) {
                field_178366_a3 = (ItemTransformVec3f)jsonDeserializationContext.deserialize(asJsonObject.get("head"), ItemTransformVec3f.class);
            }
            if (asJsonObject.has("gui")) {
                field_178366_a4 = (ItemTransformVec3f)jsonDeserializationContext.deserialize(asJsonObject.get("gui"), ItemTransformVec3f.class);
            }
            return new ItemCameraTransforms(field_178366_a, field_178366_a2, field_178366_a3, field_178366_a4);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.func_178352_a(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00002481";
        }
    }
    
    public enum TransformType
    {
        NONE("NONE", 0, "NONE", 0), 
        THIRD_PERSON("THIRD_PERSON", 1, "THIRD_PERSON", 1), 
        FIRST_PERSON("FIRST_PERSON", 2, "FIRST_PERSON", 2), 
        HEAD("HEAD", 3, "HEAD", 3), 
        GUI("GUI", 4, "GUI", 4);
        
        private static final TransformType[] $VALUES;
        private static final String __OBFID;
        private static final TransformType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002480";
            ENUM$VALUES = new TransformType[] { TransformType.NONE, TransformType.THIRD_PERSON, TransformType.FIRST_PERSON, TransformType.HEAD, TransformType.GUI };
            $VALUES = new TransformType[] { TransformType.NONE, TransformType.THIRD_PERSON, TransformType.FIRST_PERSON, TransformType.HEAD, TransformType.GUI };
        }
        
        private TransformType(final String s, final int n, final String s2, final int n2) {
        }
    }
}
