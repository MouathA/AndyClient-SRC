package net.minecraft.client.renderer.block.model;

import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import java.io.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.*;
import com.google.gson.*;
import com.google.common.collect.*;
import java.util.*;

public class ModelBlock
{
    private static final Logger LOGGER;
    static final Gson SERIALIZER;
    private final List elements;
    private final boolean ambientOcclusion;
    private final boolean field_178322_i;
    private ItemCameraTransforms itemCameraTransforms;
    public String field_178317_b;
    protected final Map textures;
    protected ModelBlock parent;
    protected ResourceLocation parentLocation;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002503";
        LOGGER = LogManager.getLogger();
        SERIALIZER = new GsonBuilder().registerTypeAdapter(ModelBlock.class, new Deserializer()).registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer()).registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer()).registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer()).registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer()).registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer()).create();
    }
    
    public static ModelBlock deserialize(final Reader reader) {
        return (ModelBlock)ModelBlock.SERIALIZER.fromJson(reader, ModelBlock.class);
    }
    
    public static ModelBlock deserialize(final String s) {
        return deserialize(new StringReader(s));
    }
    
    protected ModelBlock(final List list, final Map map, final boolean b, final boolean b2, final ItemCameraTransforms itemCameraTransforms) {
        this(null, list, map, b, b2, itemCameraTransforms);
    }
    
    protected ModelBlock(final ResourceLocation resourceLocation, final Map map, final boolean b, final boolean b2, final ItemCameraTransforms itemCameraTransforms) {
        this(resourceLocation, Collections.emptyList(), map, b, b2, itemCameraTransforms);
    }
    
    private ModelBlock(final ResourceLocation parentLocation, final List elements, final Map textures, final boolean field_178322_i, final boolean ambientOcclusion, final ItemCameraTransforms itemCameraTransforms) {
        this.field_178317_b = "";
        this.elements = elements;
        this.field_178322_i = field_178322_i;
        this.ambientOcclusion = ambientOcclusion;
        this.textures = textures;
        this.parentLocation = parentLocation;
        this.itemCameraTransforms = itemCameraTransforms;
    }
    
    public List getElements() {
        return this.hasParent() ? this.parent.getElements() : this.elements;
    }
    
    private boolean hasParent() {
        return this.parent != null;
    }
    
    public boolean func_178309_b() {
        return this.hasParent() ? this.parent.func_178309_b() : this.field_178322_i;
    }
    
    public boolean isAmbientOcclusionEnabled() {
        return this.ambientOcclusion;
    }
    
    public boolean isResolved() {
        return this.parentLocation == null || (this.parent != null && this.parent.isResolved());
    }
    
    public void getParentFromMap(final Map map) {
        if (this.parentLocation != null) {
            this.parent = map.get(this.parentLocation);
        }
    }
    
    public boolean isTexturePresent(final String s) {
        return !"missingno".equals(this.resolveTextureName(s));
    }
    
    public String resolveTextureName(String string) {
        if (!this.isTextureName(string)) {
            string = String.valueOf('#') + string;
        }
        return this.resolveTextureName(string, new Bookkeep(null));
    }
    
    private String resolveTextureName(final String s, final Bookkeep bookkeep) {
        if (!this.isTextureName(s)) {
            return s;
        }
        if (this == bookkeep.field_178323_b) {
            ModelBlock.LOGGER.warn("Unable to resolve texture due to upward reference: " + s + " in " + this.field_178317_b);
            return "missingno";
        }
        String s2 = this.textures.get(s.substring(1));
        if (s2 == null && this.hasParent()) {
            s2 = this.parent.resolveTextureName(s, bookkeep);
        }
        bookkeep.field_178323_b = this;
        if (s2 != null && this.isTextureName(s2)) {
            s2 = bookkeep.model.resolveTextureName(s2, bookkeep);
        }
        return (s2 != null && !this.isTextureName(s2)) ? s2 : "missingno";
    }
    
    private boolean isTextureName(final String s) {
        return s.charAt(0) == '#';
    }
    
    public ResourceLocation getParentLocation() {
        return this.parentLocation;
    }
    
    public ModelBlock getRootModel() {
        return this.hasParent() ? this.parent.getRootModel() : this;
    }
    
    public ItemTransformVec3f getThirdPersonTransform() {
        return (this.parent != null && this.itemCameraTransforms.field_178355_b == ItemTransformVec3f.field_178366_a) ? this.parent.getThirdPersonTransform() : this.itemCameraTransforms.field_178355_b;
    }
    
    public ItemTransformVec3f getFirstPersonTransform() {
        return (this.parent != null && this.itemCameraTransforms.field_178356_c == ItemTransformVec3f.field_178366_a) ? this.parent.getFirstPersonTransform() : this.itemCameraTransforms.field_178356_c;
    }
    
    public ItemTransformVec3f getHeadTransform() {
        return (this.parent != null && this.itemCameraTransforms.field_178353_d == ItemTransformVec3f.field_178366_a) ? this.parent.getHeadTransform() : this.itemCameraTransforms.field_178353_d;
    }
    
    public ItemTransformVec3f getInGuiTransform() {
        return (this.parent != null && this.itemCameraTransforms.field_178354_e == ItemTransformVec3f.field_178366_a) ? this.parent.getInGuiTransform() : this.itemCameraTransforms.field_178354_e;
    }
    
    public static void func_178312_b(final Map map) {
        final Iterator<ModelBlock> iterator = map.values().iterator();
        if (!iterator.hasNext()) {
            return;
        }
        for (ModelBlock modelBlock = iterator.next().parent, modelBlock2 = modelBlock.parent; modelBlock != modelBlock2; modelBlock = modelBlock.parent, modelBlock2 = modelBlock2.parent.parent) {}
        throw new LoopException();
    }
    
    final class Bookkeep
    {
        public final ModelBlock model;
        public ModelBlock field_178323_b;
        private static final String __OBFID;
        final ModelBlock this$0;
        
        private Bookkeep(final ModelBlock modelBlock) {
            this.this$0 = modelBlock;
            this.model = modelBlock;
        }
        
        Bookkeep(final ModelBlock modelBlock, final Object o) {
            this(modelBlock);
        }
        
        static {
            __OBFID = "CL_00002501";
        }
    }
    
    public static class Deserializer implements JsonDeserializer
    {
        private static final String __OBFID;
        
        public ModelBlock func_178327_a(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final List modelElements = this.getModelElements(jsonDeserializationContext, asJsonObject);
            final String parent = this.getParent(asJsonObject);
            final boolean empty = StringUtils.isEmpty(parent);
            final boolean empty2 = modelElements.isEmpty();
            if (empty2 && empty) {
                throw new JsonParseException("BlockModel requires either elements or parent, found neither");
            }
            if (!empty && !empty2) {
                throw new JsonParseException("BlockModel requires either elements or parent, found both");
            }
            final Map textures = this.getTextures(asJsonObject);
            final boolean ambientOcclusionEnabled = this.getAmbientOcclusionEnabled(asJsonObject);
            ItemCameraTransforms field_178357_a = ItemCameraTransforms.field_178357_a;
            if (asJsonObject.has("display")) {
                field_178357_a = (ItemCameraTransforms)jsonDeserializationContext.deserialize(JsonUtils.getJsonObject(asJsonObject, "display"), ItemCameraTransforms.class);
            }
            return empty2 ? new ModelBlock(new ResourceLocation(parent), textures, ambientOcclusionEnabled, true, field_178357_a) : new ModelBlock(modelElements, textures, ambientOcclusionEnabled, true, field_178357_a);
        }
        
        private Map getTextures(final JsonObject jsonObject) {
            final HashMap hashMap = Maps.newHashMap();
            if (jsonObject.has("textures")) {
                for (final Map.Entry<Object, V> entry : jsonObject.getAsJsonObject("textures").entrySet()) {
                    hashMap.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                }
            }
            return hashMap;
        }
        
        private String getParent(final JsonObject jsonObject) {
            return JsonUtils.getJsonObjectStringFieldValueOrDefault(jsonObject, "parent", "");
        }
        
        protected boolean getAmbientOcclusionEnabled(final JsonObject jsonObject) {
            return JsonUtils.getJsonObjectBooleanFieldValueOrDefault(jsonObject, "ambientocclusion", true);
        }
        
        protected List getModelElements(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final ArrayList arrayList = Lists.newArrayList();
            if (jsonObject.has("elements")) {
                final Iterator iterator = JsonUtils.getJsonObjectJsonArrayField(jsonObject, "elements").iterator();
                while (iterator.hasNext()) {
                    arrayList.add(jsonDeserializationContext.deserialize(iterator.next(), BlockPart.class));
                }
            }
            return arrayList;
        }
        
        @Override
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
            return this.func_178327_a(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            __OBFID = "CL_00002500";
        }
    }
    
    public static class LoopException extends RuntimeException
    {
        private static final String __OBFID;
        
        static {
            __OBFID = "CL_00002499";
        }
    }
}
