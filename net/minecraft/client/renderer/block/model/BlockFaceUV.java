package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class BlockFaceUV
{
    public float[] field_178351_a;
    public final int field_178350_b;
    private static final String __OBFID;
    
    public BlockFaceUV(final float[] field_178351_a, final int field_178350_b) {
        this.field_178351_a = field_178351_a;
        this.field_178350_b = field_178350_b;
    }
    
    public float func_178348_a(final int n) {
        if (this.field_178351_a == null) {
            throw new NullPointerException("uvs");
        }
        final int func_178347_d = this.func_178347_d(n);
        return (func_178347_d != 0 && func_178347_d != 1) ? this.field_178351_a[2] : this.field_178351_a[0];
    }
    
    public float func_178346_b(final int n) {
        if (this.field_178351_a == null) {
            throw new NullPointerException("uvs");
        }
        final int func_178347_d = this.func_178347_d(n);
        return (func_178347_d != 0 && func_178347_d != 3) ? this.field_178351_a[3] : this.field_178351_a[1];
    }
    
    private int func_178347_d(final int n) {
        return (n + this.field_178350_b / 90) % 4;
    }
    
    public int func_178345_c(final int n) {
        return (n + (4 - this.field_178350_b / 90)) % 4;
    }
    
    public void func_178349_a(final float[] field_178351_a) {
        if (this.field_178351_a == null) {
            this.field_178351_a = field_178351_a;
        }
    }
    
    static {
        __OBFID = "CL_00002505";
    }
    
    static class Deserializer implements JsonDeserializer
    {
        private static final String __OBFID;
        
        public BlockFaceUV func_178293_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new BlockFaceUV(this.func_178292_b(asJsonObject), this.func_178291_a(asJsonObject));
        }
        
        protected int func_178291_a(final JsonObject jsonObject) {
            final int jsonObjectIntegerFieldValueOrDefault = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonObject, "rotation", 0);
            if (jsonObjectIntegerFieldValueOrDefault >= 0 && jsonObjectIntegerFieldValueOrDefault % 90 == 0 && jsonObjectIntegerFieldValueOrDefault / 90 <= 3) {
                return jsonObjectIntegerFieldValueOrDefault;
            }
            throw new JsonParseException("Invalid rotation " + jsonObjectIntegerFieldValueOrDefault + " found, only 0/90/180/270 allowed");
        }
        
        private float[] func_178292_b(final JsonObject jsonObject) {
            if (!jsonObject.has("uv")) {
                return null;
            }
            final JsonArray jsonObjectJsonArrayField = JsonUtils.getJsonObjectJsonArrayField(jsonObject, "uv");
            if (jsonObjectJsonArrayField.size() != 4) {
                throw new JsonParseException("Expected 4 uv values, found: " + jsonObjectJsonArrayField.size());
            }
            final float[] array = new float[4];
            while (0 < array.length) {
                array[0] = JsonUtils.getJsonElementFloatValue(jsonObjectJsonArrayField.get(0), "uv[" + 0 + "]");
                int n = 0;
                ++n;
            }
            return array;
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.func_178293_a(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00002504";
        }
    }
}
