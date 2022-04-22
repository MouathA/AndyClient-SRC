package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import java.io.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.model.*;
import com.google.gson.*;

public class ModelBlockDefinition
{
    static final Gson field_178333_a;
    private final Map field_178332_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002498";
        field_178333_a = new GsonBuilder().registerTypeAdapter(ModelBlockDefinition.class, new Deserializer()).registerTypeAdapter(Variant.class, new Variant.Deserializer()).create();
    }
    
    public static ModelBlockDefinition func_178331_a(final Reader reader) {
        return (ModelBlockDefinition)ModelBlockDefinition.field_178333_a.fromJson(reader, ModelBlockDefinition.class);
    }
    
    public ModelBlockDefinition(final Collection collection) {
        this.field_178332_b = Maps.newHashMap();
        for (final Variants variants : collection) {
            this.field_178332_b.put(Variants.access$0(variants), variants);
        }
    }
    
    public ModelBlockDefinition(final List list) {
        this.field_178332_b = Maps.newHashMap();
        final Iterator<ModelBlockDefinition> iterator = list.iterator();
        while (iterator.hasNext()) {
            this.field_178332_b.putAll(iterator.next().field_178332_b);
        }
    }
    
    public Variants func_178330_b(final String s) {
        final Variants variants = this.field_178332_b.get(s);
        if (variants == null) {
            throw new MissingVariantException();
        }
        return variants;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof ModelBlockDefinition && this.field_178332_b.equals(((ModelBlockDefinition)o).field_178332_b));
    }
    
    @Override
    public int hashCode() {
        return this.field_178332_b.hashCode();
    }
    
    public static class Deserializer implements JsonDeserializer
    {
        private static final String __OBFID;
        
        public ModelBlockDefinition func_178336_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return new ModelBlockDefinition((Collection)this.func_178334_a(jsonDeserializationContext, jsonElement.getAsJsonObject()));
        }
        
        protected List func_178334_a(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonObject, "variants");
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator iterator = jsonObject2.entrySet().iterator();
            while (iterator.hasNext()) {
                arrayList.add(this.func_178335_a(jsonDeserializationContext, iterator.next()));
            }
            return arrayList;
        }
        
        protected Variants func_178335_a(final JsonDeserializationContext jsonDeserializationContext, final Map.Entry entry) {
            final String s = entry.getKey();
            final ArrayList arrayList = Lists.newArrayList();
            final JsonElement jsonElement = (JsonElement)entry.getValue();
            if (jsonElement.isJsonArray()) {
                final Iterator iterator = jsonElement.getAsJsonArray().iterator();
                while (iterator.hasNext()) {
                    arrayList.add(jsonDeserializationContext.deserialize(iterator.next(), Variant.class));
                }
            }
            else {
                arrayList.add(jsonDeserializationContext.deserialize(jsonElement, Variant.class));
            }
            return new Variants(s, arrayList);
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.func_178336_a(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00002497";
        }
    }
    
    public static class Variant
    {
        private final ResourceLocation field_178437_a;
        private final ModelRotation field_178435_b;
        private final boolean field_178436_c;
        private final int field_178434_d;
        private static final String __OBFID;
        
        public Variant(final ResourceLocation field_178437_a, final ModelRotation field_178435_b, final boolean field_178436_c, final int field_178434_d) {
            this.field_178437_a = field_178437_a;
            this.field_178435_b = field_178435_b;
            this.field_178436_c = field_178436_c;
            this.field_178434_d = field_178434_d;
        }
        
        public ResourceLocation getModelLocation() {
            return this.field_178437_a;
        }
        
        public ModelRotation getRotation() {
            return this.field_178435_b;
        }
        
        public boolean isUvLocked() {
            return this.field_178436_c;
        }
        
        public int getWeight() {
            return this.field_178434_d;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Variant)) {
                return false;
            }
            final Variant variant = (Variant)o;
            return this.field_178437_a.equals(variant.field_178437_a) && this.field_178435_b == variant.field_178435_b && this.field_178436_c == variant.field_178436_c;
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * this.field_178437_a.hashCode() + ((this.field_178435_b != null) ? this.field_178435_b.hashCode() : 0)) + (this.field_178436_c ? 1 : 0);
        }
        
        static {
            __OBFID = "CL_00002495";
        }
        
        public static class Deserializer implements JsonDeserializer
        {
            private static final String __OBFID;
            
            public Variant func_178425_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
                final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                return new Variant(this.func_178426_a(this.func_178424_b(asJsonObject)), this.func_178428_a(asJsonObject), this.func_178429_d(asJsonObject), this.func_178427_c(asJsonObject));
            }
            
            private ResourceLocation func_178426_a(final String s) {
                final ResourceLocation resourceLocation = new ResourceLocation(s);
                return new ResourceLocation(resourceLocation.getResourceDomain(), "block/" + resourceLocation.getResourcePath());
            }
            
            private boolean func_178429_d(final JsonObject jsonObject) {
                return JsonUtils.getJsonObjectBooleanFieldValueOrDefault(jsonObject, "uvlock", false);
            }
            
            protected ModelRotation func_178428_a(final JsonObject jsonObject) {
                final int jsonObjectIntegerFieldValueOrDefault = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonObject, "x", 0);
                final int jsonObjectIntegerFieldValueOrDefault2 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonObject, "y", 0);
                final ModelRotation func_177524_a = ModelRotation.func_177524_a(jsonObjectIntegerFieldValueOrDefault, jsonObjectIntegerFieldValueOrDefault2);
                if (func_177524_a == null) {
                    throw new JsonParseException("Invalid BlockModelRotation x: " + jsonObjectIntegerFieldValueOrDefault + ", y: " + jsonObjectIntegerFieldValueOrDefault2);
                }
                return func_177524_a;
            }
            
            protected String func_178424_b(final JsonObject jsonObject) {
                return JsonUtils.getJsonObjectStringFieldValue(jsonObject, "model");
            }
            
            protected int func_178427_c(final JsonObject jsonObject) {
                return JsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonObject, "weight", 1);
            }
            
            @Override
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
                return this.func_178425_a(jsonElement, type, jsonDeserializationContext);
            }
            
            static {
                __OBFID = "CL_00002494";
            }
        }
    }
    
    public static class Variants
    {
        private final String field_178423_a;
        private final List field_178422_b;
        private static final String __OBFID;
        
        public Variants(final String field_178423_a, final List field_178422_b) {
            this.field_178423_a = field_178423_a;
            this.field_178422_b = field_178422_b;
        }
        
        public List getVariants() {
            return this.field_178422_b;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Variants)) {
                return false;
            }
            final Variants variants = (Variants)o;
            return this.field_178423_a.equals(variants.field_178423_a) && this.field_178422_b.equals(variants.field_178422_b);
        }
        
        @Override
        public int hashCode() {
            return 31 * this.field_178423_a.hashCode() + this.field_178422_b.hashCode();
        }
        
        static String access$0(final Variants variants) {
            return variants.field_178423_a;
        }
        
        static {
            __OBFID = "CL_00002493";
        }
    }
    
    public class MissingVariantException extends RuntimeException
    {
        private static final String __OBFID;
        final ModelBlockDefinition this$0;
        
        public MissingVariantException(final ModelBlockDefinition this$0) {
            this.this$0 = this$0;
        }
        
        static {
            __OBFID = "CL_00002496";
        }
    }
}
