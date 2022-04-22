package net.minecraft.client.renderer.block.model;

import javax.vecmath.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import com.google.gson.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockPart
{
    public final Vector3f field_178241_a;
    public final Vector3f field_178239_b;
    public final Map field_178240_c;
    public final BlockPartRotation field_178237_d;
    public final boolean field_178238_e;
    private static final String __OBFID;
    
    public BlockPart(final Vector3f field_178241_a, final Vector3f field_178239_b, final Map field_178240_c, final BlockPartRotation field_178237_d, final boolean field_178238_e) {
        this.field_178241_a = field_178241_a;
        this.field_178239_b = field_178239_b;
        this.field_178240_c = field_178240_c;
        this.field_178237_d = field_178237_d;
        this.field_178238_e = field_178238_e;
        this.func_178235_a();
    }
    
    private void func_178235_a() {
        for (final Map.Entry<EnumFacing, V> entry : this.field_178240_c.entrySet()) {
            ((BlockPartFace)entry.getValue()).field_178243_e.func_178349_a(this.func_178236_a(entry.getKey()));
        }
    }
    
    private float[] func_178236_a(final EnumFacing enumFacing) {
        float[] array = null;
        switch (SwitchEnumFacing.field_178234_a[enumFacing.ordinal()]) {
            case 1:
            case 2: {
                array = new float[] { this.field_178241_a.x, this.field_178241_a.z, this.field_178239_b.x, this.field_178239_b.z };
                break;
            }
            case 3:
            case 4: {
                array = new float[] { this.field_178241_a.x, 16.0f - this.field_178239_b.y, this.field_178239_b.x, 16.0f - this.field_178241_a.y };
                break;
            }
            case 5:
            case 6: {
                array = new float[] { this.field_178241_a.z, 16.0f - this.field_178239_b.y, this.field_178239_b.z, 16.0f - this.field_178241_a.y };
                break;
            }
            default: {
                throw new NullPointerException();
            }
        }
        return array;
    }
    
    static {
        __OBFID = "CL_00002511";
    }
    
    static class Deserializer implements JsonDeserializer
    {
        private static final String __OBFID;
        
        public BlockPart func_178254_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Vector3f func_178249_e = this.func_178249_e(asJsonObject);
            final Vector3f func_178247_d = this.func_178247_d(asJsonObject);
            final BlockPartRotation func_178256_a = this.func_178256_a(asJsonObject);
            final Map func_178250_a = this.func_178250_a(jsonDeserializationContext, asJsonObject);
            if (asJsonObject.has("shade") && !JsonUtils.func_180199_c(asJsonObject, "shade")) {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            return new BlockPart(func_178249_e, func_178247_d, func_178250_a, func_178256_a, JsonUtils.getJsonObjectBooleanFieldValueOrDefault(asJsonObject, "shade", true));
        }
        
        private BlockPartRotation func_178256_a(final JsonObject jsonObject) {
            BlockPartRotation blockPartRotation = null;
            if (jsonObject.has("rotation")) {
                final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonObject, "rotation");
                final Vector3f func_178251_a = this.func_178251_a(jsonObject2, "origin");
                func_178251_a.scale(0.0625f);
                blockPartRotation = new BlockPartRotation(func_178251_a, this.func_178252_c(jsonObject2), this.func_178255_b(jsonObject2), JsonUtils.getJsonObjectBooleanFieldValueOrDefault(jsonObject2, "rescale", false));
            }
            return blockPartRotation;
        }
        
        private float func_178255_b(final JsonObject jsonObject) {
            final float jsonObjectFloatFieldValue = JsonUtils.getJsonObjectFloatFieldValue(jsonObject, "angle");
            if (jsonObjectFloatFieldValue != 0.0f && MathHelper.abs(jsonObjectFloatFieldValue) != 22.5f && MathHelper.abs(jsonObjectFloatFieldValue) != 45.0f) {
                throw new JsonParseException("Invalid rotation " + jsonObjectFloatFieldValue + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            return jsonObjectFloatFieldValue;
        }
        
        private EnumFacing.Axis func_178252_c(final JsonObject jsonObject) {
            final String jsonObjectStringFieldValue = JsonUtils.getJsonObjectStringFieldValue(jsonObject, "axis");
            final EnumFacing.Axis byName = EnumFacing.Axis.byName(jsonObjectStringFieldValue.toLowerCase());
            if (byName == null) {
                throw new JsonParseException("Invalid rotation axis: " + jsonObjectStringFieldValue);
            }
            return byName;
        }
        
        private Map func_178250_a(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final Map func_178253_b = this.func_178253_b(jsonDeserializationContext, jsonObject);
            if (func_178253_b.isEmpty()) {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            return func_178253_b;
        }
        
        private Map func_178253_b(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final EnumMap enumMap = Maps.newEnumMap(EnumFacing.class);
            for (final Map.Entry<String, V> entry : JsonUtils.getJsonObject(jsonObject, "faces").entrySet()) {
                enumMap.put(this.func_178248_a(entry.getKey()), jsonDeserializationContext.deserialize((JsonElement)entry.getValue(), BlockPartFace.class));
            }
            return enumMap;
        }
        
        private EnumFacing func_178248_a(final String s) {
            final EnumFacing byName = EnumFacing.byName(s);
            if (byName == null) {
                throw new JsonParseException("Unknown facing: " + s);
            }
            return byName;
        }
        
        private Vector3f func_178247_d(final JsonObject jsonObject) {
            final Vector3f func_178251_a = this.func_178251_a(jsonObject, "to");
            if (func_178251_a.x >= -16.0f && func_178251_a.y >= -16.0f && func_178251_a.z >= -16.0f && func_178251_a.x <= 32.0f && func_178251_a.y <= 32.0f && func_178251_a.z <= 32.0f) {
                return func_178251_a;
            }
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + func_178251_a);
        }
        
        private Vector3f func_178249_e(final JsonObject jsonObject) {
            final Vector3f func_178251_a = this.func_178251_a(jsonObject, "from");
            if (func_178251_a.x >= -16.0f && func_178251_a.y >= -16.0f && func_178251_a.z >= -16.0f && func_178251_a.x <= 32.0f && func_178251_a.y <= 32.0f && func_178251_a.z <= 32.0f) {
                return func_178251_a;
            }
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + func_178251_a);
        }
        
        private Vector3f func_178251_a(final JsonObject jsonObject, final String s) {
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
            return this.func_178254_a(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00002509";
        }
    }
    
    static final class SwitchEnumFacing
    {
        static final int[] field_178234_a;
        private static final String __OBFID;
        private static final String[] lIIllIIIIlIlIIll;
        private static String[] lIIllIIIIlIlIlII;
        
        static {
            lllIIIIIIlIlllIl();
            lllIIIIIIlIlllII();
            __OBFID = SwitchEnumFacing.lIIllIIIIlIlIIll[0];
            field_178234_a = new int[EnumFacing.values().length];
            try {
                SwitchEnumFacing.field_178234_a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumFacing.field_178234_a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumFacing.field_178234_a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumFacing.field_178234_a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumFacing.field_178234_a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumFacing.field_178234_a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
        
        private static void lllIIIIIIlIlllII() {
            (lIIllIIIIlIlIIll = new String[1])[0] = lllIIIIIIlIllIll(SwitchEnumFacing.lIIllIIIIlIlIlII[0], SwitchEnumFacing.lIIllIIIIlIlIlII[1]);
            SwitchEnumFacing.lIIllIIIIlIlIlII = null;
        }
        
        private static void lllIIIIIIlIlllIl() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumFacing.lIIllIIIIlIlIlII = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lllIIIIIIlIllIll(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), "Blowfish");
                final Cipher instance = Cipher.getInstance("Blowfish");
                instance.init(2, secretKeySpec);
                return new String(instance.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }
}
