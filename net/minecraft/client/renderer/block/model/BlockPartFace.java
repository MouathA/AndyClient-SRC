package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class BlockPartFace
{
    public static final EnumFacing field_178246_a;
    public final EnumFacing field_178244_b;
    public final int field_178245_c;
    public final String field_178242_d;
    public final BlockFaceUV field_178243_e;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002508";
        field_178246_a = null;
    }
    
    public BlockPartFace(final EnumFacing field_178244_b, final int field_178245_c, final String field_178242_d, final BlockFaceUV field_178243_e) {
        this.field_178244_b = field_178244_b;
        this.field_178245_c = field_178245_c;
        this.field_178242_d = field_178242_d;
        this.field_178243_e = field_178243_e;
    }
    
    static class Deserializer implements JsonDeserializer
    {
        private static final String __OBFID;
        
        public BlockPartFace func_178338_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new BlockPartFace(this.func_178339_c(asJsonObject), this.func_178337_a(asJsonObject), this.func_178340_b(asJsonObject), (BlockFaceUV)jsonDeserializationContext.deserialize(asJsonObject, BlockFaceUV.class));
        }
        
        protected int func_178337_a(final JsonObject jsonObject) {
            return JsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonObject, "tintindex", -1);
        }
        
        private String func_178340_b(final JsonObject jsonObject) {
            return JsonUtils.getJsonObjectStringFieldValue(jsonObject, "texture");
        }
        
        private EnumFacing func_178339_c(final JsonObject jsonObject) {
            return EnumFacing.byName(JsonUtils.getJsonObjectStringFieldValueOrDefault(jsonObject, "cullface", ""));
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.func_178338_a(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00002507";
        }
    }
}
