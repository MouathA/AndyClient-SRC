package net.minecraft.client.renderer.block.model;

import javax.vecmath.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class ItemTransformVec3f
{
    public static final ItemTransformVec3f field_178366_a;
    public final Vector3f field_178364_b;
    public final Vector3f field_178365_c;
    public final Vector3f field_178363_d;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002484";
        field_178366_a = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    public ItemTransformVec3f(final Vector3f vector3f, final Vector3f vector3f2, final Vector3f vector3f3) {
        this.field_178364_b = new Vector3f(vector3f);
        this.field_178365_c = new Vector3f(vector3f2);
        this.field_178363_d = new Vector3f(vector3f3);
    }
    
    static class Deserializer implements JsonDeserializer
    {
        private static final Vector3f field_178362_a;
        private static final Vector3f field_178360_b;
        private static final Vector3f field_178361_c;
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002483";
            field_178362_a = new Vector3f(0.0f, 0.0f, 0.0f);
            field_178360_b = new Vector3f(0.0f, 0.0f, 0.0f);
            field_178361_c = new Vector3f(1.0f, 1.0f, 1.0f);
        }
        
        public ItemTransformVec3f func_178359_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Vector3f func_178358_a = this.func_178358_a(asJsonObject, "rotation", Deserializer.field_178362_a);
            final Vector3f func_178358_a2 = this.func_178358_a(asJsonObject, "translation", Deserializer.field_178360_b);
            func_178358_a2.scale(0.0625f);
            MathHelper.clamp_double(func_178358_a2.x, -1.5, 1.5);
            MathHelper.clamp_double(func_178358_a2.y, -1.5, 1.5);
            MathHelper.clamp_double(func_178358_a2.z, -1.5, 1.5);
            final Vector3f func_178358_a3 = this.func_178358_a(asJsonObject, "scale", Deserializer.field_178361_c);
            MathHelper.clamp_double(func_178358_a3.x, -1.5, 1.5);
            MathHelper.clamp_double(func_178358_a3.y, -1.5, 1.5);
            MathHelper.clamp_double(func_178358_a3.z, -1.5, 1.5);
            return new ItemTransformVec3f(func_178358_a, func_178358_a2, func_178358_a3);
        }
        
        private Vector3f func_178358_a(final JsonObject jsonObject, final String s, final Vector3f vector3f) {
            if (!jsonObject.has(s)) {
                return vector3f;
            }
            final JsonArray jsonObjectJsonArrayField = JsonUtils.getJsonObjectJsonArrayField(jsonObject, s);
            if (jsonObjectJsonArrayField.size() != 3) {
                throw new JsonParseException("Expected 3 " + s + " values, found: " + jsonObjectJsonArrayField.size());
            }
            final float[] array = new float[3];
            while (0 < array.length) {
                array[0] = JsonUtils.getJsonElementFloatValue(jsonObjectJsonArrayField.get(0), String.valueOf(s) + "[" + 0 + "]");
                int n = 0;
                ++n;
            }
            return new Vector3f(array);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.func_178359_a(jsonElement, type, jsonDeserializationContext);
        }
    }
}
