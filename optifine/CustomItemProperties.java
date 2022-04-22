package optifine;

import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.resources.model.*;
import java.util.*;

public class CustomItemProperties
{
    public String name;
    public String basePath;
    public int type;
    public int[] items;
    public String texture;
    public Map mapTextures;
    public RangeListInt damage;
    public boolean damagePercent;
    public int damageMask;
    public RangeListInt stackSize;
    public RangeListInt enchantmentIds;
    public RangeListInt enchantmentLevels;
    public NbtTagValue[] nbtTagValues;
    public int blend;
    public float speed;
    public float rotation;
    public int layer;
    public float duration;
    public int weight;
    public ResourceLocation textureLocation;
    public Map mapTextureLocations;
    public TextureAtlasSprite sprite;
    public Map mapSprites;
    public IBakedModel model;
    public Map mapModels;
    private int textureWidth;
    private int textureHeight;
    
    public CustomItemProperties(final Properties properties, final String s) {
        this.name = null;
        this.basePath = null;
        this.type = 1;
        this.items = null;
        this.texture = null;
        this.mapTextures = null;
        this.damage = null;
        this.damagePercent = false;
        this.damageMask = 0;
        this.stackSize = null;
        this.enchantmentIds = null;
        this.enchantmentLevels = null;
        this.nbtTagValues = null;
        this.blend = 1;
        this.speed = 0.0f;
        this.rotation = 0.0f;
        this.layer = 0;
        this.duration = 1.0f;
        this.weight = 0;
        this.textureLocation = null;
        this.mapTextureLocations = null;
        this.sprite = null;
        this.mapSprites = null;
        this.model = null;
        this.mapModels = null;
        this.textureWidth = 0;
        this.textureHeight = 0;
        this.name = parseName(s);
        this.basePath = parseBasePath(s);
        this.type = this.parseType(properties.getProperty("type"));
        this.items = this.parseItems(properties.getProperty("items"), properties.getProperty("matchItems"));
        this.mapTextures = parseTextures(properties, this.basePath);
        this.texture = parseTexture(properties.getProperty("texture"), properties.getProperty("tile"), properties.getProperty("source"), s, this.basePath, this.type, this.mapTextures);
        final String property = properties.getProperty("damage");
        if (property != null) {
            this.damagePercent = property.contains("%");
            property.replace("%", "");
            this.damage = this.parseRangeListInt(property);
            this.damageMask = this.parseInt(properties.getProperty("damageMask"), 0);
        }
        this.stackSize = this.parseRangeListInt(properties.getProperty("stackSize"));
        this.enchantmentIds = this.parseRangeListInt(properties.getProperty("enchantmentIDs"));
        this.enchantmentLevels = this.parseRangeListInt(properties.getProperty("enchantmentLevels"));
        this.nbtTagValues = this.parseNbtTagValues(properties);
        this.blend = Blender.parseBlend(properties.getProperty("blend"));
        this.speed = this.parseFloat(properties.getProperty("speed"), 0.0f);
        this.rotation = this.parseFloat(properties.getProperty("rotation"), 0.0f);
        this.layer = this.parseInt(properties.getProperty("layer"), 0);
        this.weight = this.parseInt(properties.getProperty("weight"), 0);
        this.duration = this.parseFloat(properties.getProperty("duration"), 1.0f);
    }
    
    private static String parseName(final String s) {
        String s2 = s;
        final int lastIndex = s.lastIndexOf(47);
        if (lastIndex >= 0) {
            s2 = s.substring(lastIndex + 1);
        }
        final int lastIndex2 = s2.lastIndexOf(46);
        if (lastIndex2 >= 0) {
            s2 = s2.substring(0, lastIndex2);
        }
        return s2;
    }
    
    private static String parseBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    private int parseType(final String s) {
        if (s == null) {
            return 1;
        }
        if (s.equals("item")) {
            return 1;
        }
        if (s.equals("enchantment")) {
            return 2;
        }
        if (s.equals("armor")) {
            return 3;
        }
        Config.warn("Unknown method: " + s);
        return 0;
    }
    
    private int[] parseItems(String trim, final String s) {
        if (trim == null) {
            trim = s;
        }
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        final TreeSet<Integer> set = new TreeSet<Integer>();
        final String[] tokenize = Config.tokenize(trim, " ");
        int int1 = 0;
        while (0 < tokenize.length) {
            int1 = Config.parseInt(tokenize[0], -1);
            set.add(new Integer(0));
            int n = 0;
            ++n;
        }
        final Integer[] array = set.toArray(new Integer[set.size()]);
        final int[] array2 = new int[array.length];
        while (0 < array2.length) {
            array2[0] = array[0];
            ++int1;
        }
        return array2;
    }
    
    private static String parseTexture(String s, final String s2, final String s3, final String s4, final String s5, final int n, final Map map) {
        if (s == null) {
            s = s2;
        }
        if (s == null) {
            s = s3;
        }
        if (s != null) {
            final String s6 = ".png";
            if (s.endsWith(s6)) {
                s = s.substring(0, s.length() - s6.length());
            }
            s = fixTextureName(s, s5);
            return s;
        }
        if (n == 3) {
            return null;
        }
        if (map != null) {
            final String s7 = map.get("texture.bow_standby");
            if (s7 != null) {
                return s7;
            }
        }
        String s8 = s4;
        final int lastIndex = s4.lastIndexOf(47);
        if (lastIndex >= 0) {
            s8 = s4.substring(lastIndex + 1);
        }
        final int lastIndex2 = s8.lastIndexOf(46);
        if (lastIndex2 >= 0) {
            s8 = s8.substring(0, lastIndex2);
        }
        return fixTextureName(s8, s5);
    }
    
    private static Map parseTextures(final Properties properties, final String s) {
        final Map matchingProperties = getMatchingProperties(properties, "texture.");
        if (matchingProperties.size() <= 0) {
            return null;
        }
        final Set<String> keySet = matchingProperties.keySet();
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        for (final String s2 : keySet) {
            linkedHashMap.put(s2, fixTextureName((String)matchingProperties.get(s2), s));
        }
        return linkedHashMap;
    }
    
    private static String fixTextureName(String s, final String s2) {
        s = TextureUtils.fixResourcePath(s, s2);
        if (!s.startsWith(s2) && !s.startsWith("textures/") && !s.startsWith("mcpatcher/")) {
            s = String.valueOf(s2) + "/" + s;
        }
        if (s.endsWith(".png")) {
            s = s.substring(0, s.length() - 4);
        }
        final String s3 = "textures/blocks/";
        if (s.startsWith(s3)) {
            s = s.substring(s3.length());
        }
        if (s.startsWith("/")) {
            s = s.substring(1);
        }
        return s;
    }
    
    private int parseInt(String trim, final int n) {
        if (trim == null) {
            return n;
        }
        trim = trim.trim();
        final int int1 = Config.parseInt(trim, Integer.MIN_VALUE);
        if (int1 == Integer.MIN_VALUE) {
            Config.warn("Invalid integer: " + trim);
            return n;
        }
        return int1;
    }
    
    private float parseFloat(String trim, final float n) {
        if (trim == null) {
            return n;
        }
        trim = trim.trim();
        final float float1 = Config.parseFloat(trim, Float.MIN_VALUE);
        if (float1 == Float.MIN_VALUE) {
            Config.warn("Invalid float: " + trim);
            return n;
        }
        return float1;
    }
    
    private RangeListInt parseRangeListInt(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, " ");
        final RangeListInt rangeListInt = new RangeListInt();
        while (0 < tokenize.length) {
            final RangeInt rangeInt = this.parseRangeInt(tokenize[0]);
            if (rangeInt == null) {
                Config.warn("Invalid range list: " + s);
                return null;
            }
            rangeListInt.addRange(rangeInt);
            int n = 0;
            ++n;
        }
        return rangeListInt;
    }
    
    private RangeInt parseRangeInt(String trim) {
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        if (trim.length() - trim.replace("-", "").length() > 1) {
            Config.warn("Invalid range: " + trim);
            return null;
        }
        final String[] tokenize = Config.tokenize(trim, "- ");
        final int[] array = new int[tokenize.length];
        while (0 < tokenize.length) {
            final int int1 = Config.parseInt(tokenize[0], -1);
            if (int1 < 0) {
                Config.warn("Invalid range: " + trim);
                return null;
            }
            array[0] = int1;
            int min = 0;
            ++min;
        }
        if (array.length == 1) {
            final int min = array[0];
            if (trim.startsWith("-")) {
                return new RangeInt(0, 0);
            }
            if (trim.endsWith("-")) {
                return new RangeInt(0, 255);
            }
            return new RangeInt(0, 0);
        }
        else {
            if (array.length == 2) {
                final int min = Math.min(array[0], array[1]);
                return new RangeInt(0, Math.max(array[0], array[1]));
            }
            Config.warn("Invalid range: " + trim);
            return null;
        }
    }
    
    private NbtTagValue[] parseNbtTagValues(final Properties properties) {
        final String s = "nbt.";
        final Map matchingProperties = getMatchingProperties(properties, s);
        if (matchingProperties.size() <= 0) {
            return null;
        }
        final ArrayList<NbtTagValue> list = new ArrayList<NbtTagValue>();
        for (final String s2 : matchingProperties.keySet()) {
            list.add(new NbtTagValue(s2.substring(s.length()), (String)matchingProperties.get(s2)));
        }
        return list.toArray(new NbtTagValue[list.size()]);
    }
    
    private static Map getMatchingProperties(final Properties properties, final String s) {
        final LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        for (final String s2 : ((Hashtable<String, V>)properties).keySet()) {
            final String property = properties.getProperty(s2);
            if (s2.startsWith(s)) {
                linkedHashMap.put(s2, property);
            }
        }
        return linkedHashMap;
    }
    
    public boolean isValid(final String s) {
        if (this.name == null || this.name.length() <= 0) {
            Config.warn("No name found: " + s);
            return false;
        }
        if (this.basePath == null) {
            Config.warn("No base path found: " + s);
            return false;
        }
        if (this.type == 0) {
            Config.warn("No type defined: " + s);
            return false;
        }
        if ((this.type == 1 || this.type == 3) && this.items == null) {
            Config.warn("No items defined: " + s);
            return false;
        }
        if (this.texture == null && this.mapTextures == null) {
            Config.warn("No texture specified: " + s);
            return false;
        }
        if (this.type == 2 && this.enchantmentIds == null) {
            Config.warn("No enchantmentIDs specified: " + s);
            return false;
        }
        return true;
    }
    
    public void updateIcons(final TextureMap textureMap) {
        if (this.texture != null) {
            this.textureLocation = this.getTextureLocation(this.texture);
            if (this.type == 1) {
                this.sprite = textureMap.func_174942_a(this.getSpriteLocation(this.textureLocation));
            }
        }
        if (this.mapTextures != null) {
            this.mapTextureLocations = new HashMap();
            this.mapSprites = new HashMap();
            for (final String s : this.mapTextures.keySet()) {
                final ResourceLocation textureLocation = this.getTextureLocation(this.mapTextures.get(s));
                this.mapTextureLocations.put(s, textureLocation);
                if (this.type == 1) {
                    this.mapSprites.put(s, textureMap.func_174942_a(this.getSpriteLocation(textureLocation)));
                }
            }
        }
    }
    
    private ResourceLocation getTextureLocation(final String s) {
        if (s == null) {
            return null;
        }
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        final String resourceDomain = resourceLocation.getResourceDomain();
        String s2 = resourceLocation.getResourcePath();
        if (!s2.contains("/")) {
            s2 = "textures/blocks/" + s2;
        }
        final String string = String.valueOf(s2) + ".png";
        final ResourceLocation resourceLocation2 = new ResourceLocation(resourceDomain, string);
        if (!Config.hasResource(resourceLocation2)) {
            Config.warn("File not found: " + string);
        }
        return resourceLocation2;
    }
    
    private ResourceLocation getSpriteLocation(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), StrUtils.removeSuffix(StrUtils.removePrefix(resourceLocation.getResourcePath(), "textures/"), ".png"));
    }
    
    public void updateModel(final TextureMap textureMap, final ItemModelGenerator itemModelGenerator) {
        final String[] modelTextures = this.getModelTextures();
        final boolean useTint = this.isUseTint();
        this.model = makeBakedModel(textureMap, itemModelGenerator, modelTextures, useTint);
        if (this.type == 1 && this.mapTextures != null) {
            for (final String s : this.mapTextures.keySet()) {
                final String s2 = this.mapTextures.get(s);
                final String removePrefix = StrUtils.removePrefix(s, "texture.");
                if (removePrefix.startsWith("bow") || removePrefix.startsWith("fishing_rod")) {
                    final IBakedModel bakedModel = makeBakedModel(textureMap, itemModelGenerator, new String[] { s2 }, useTint);
                    if (this.mapModels == null) {
                        this.mapModels = new HashMap();
                    }
                    this.mapModels.put(removePrefix, bakedModel);
                }
            }
        }
    }
    
    private boolean isUseTint() {
        return true;
    }
    
    private static IBakedModel makeBakedModel(final TextureMap textureMap, final ItemModelGenerator itemModelGenerator, final String[] array, final boolean b) {
        return bakeModel(textureMap, itemModelGenerator.func_178392_a(textureMap, makeModelBlock(array)), b);
    }
    
    private String[] getModelTextures() {
        if (this.type == 1 && this.items.length == 1) {
            final Item itemById = Item.getItemById(this.items[0]);
            if (itemById == Items.potionitem && this.damage != null && this.damage.getCountRanges() > 0) {
                final boolean b = (this.damage.getRange(0).getMin() & 0x4000) != 0x0;
                final String mapTexture = this.getMapTexture(this.mapTextures, "texture.potion_overlay", "items/potion_overlay");
                String s;
                if (b) {
                    s = this.getMapTexture(this.mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
                }
                else {
                    s = this.getMapTexture(this.mapTextures, "texture.potion_bottle_drinkable", "items/potion_bottle_drinkable");
                }
                return new String[] { mapTexture, s };
            }
            if (itemById instanceof ItemArmor) {
                final ItemArmor itemArmor = (ItemArmor)itemById;
                if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) {
                    final String s2 = "leather";
                    String s3 = "helmet";
                    if (itemArmor.armorType == 0) {
                        s3 = "helmet";
                    }
                    if (itemArmor.armorType == 1) {
                        s3 = "chestplate";
                    }
                    if (itemArmor.armorType == 2) {
                        s3 = "leggings";
                    }
                    if (itemArmor.armorType == 3) {
                        s3 = "boots";
                    }
                    final String string = String.valueOf(s2) + "_" + s3;
                    return new String[] { this.getMapTexture(this.mapTextures, "texture." + string, "items/" + string), this.getMapTexture(this.mapTextures, "texture." + string + "_overlay", "items/" + string + "_overlay") };
                }
            }
        }
        return new String[] { this.texture };
    }
    
    private String getMapTexture(final Map map, final String s, final String s2) {
        if (map == null) {
            return s2;
        }
        final String s3 = map.get(s);
        return (s3 == null) ? s2 : s3;
    }
    
    private static ModelBlock makeModelBlock(final String[] array) {
        final StringBuffer sb = new StringBuffer();
        sb.append("{\"parent\": \"builtin/generated\",\"textures\": {");
        while (0 < array.length) {
            sb.append("\"layer" + 0 + "\": \"" + array[0] + "\"");
            int n = 0;
            ++n;
        }
        sb.append("}}");
        return ModelBlock.deserialize(sb.toString());
    }
    
    private static IBakedModel bakeModel(final TextureMap textureMap, final ModelBlock modelBlock, final boolean b) {
        final ModelRotation x0_Y0 = ModelRotation.X0_Y0;
        final SimpleBakedModel.Builder func_177646_a = new SimpleBakedModel.Builder(modelBlock).func_177646_a(textureMap.getSpriteSafe(modelBlock.resolveTextureName("particle")));
        for (final BlockPart blockPart : modelBlock.getElements()) {
            for (final EnumFacing enumFacing : blockPart.field_178240_c.keySet()) {
                BlockPartFace blockPartFace = blockPart.field_178240_c.get(enumFacing);
                if (!b) {
                    blockPartFace = new BlockPartFace(blockPartFace.field_178244_b, -1, blockPartFace.field_178242_d, blockPartFace.field_178243_e);
                }
                final BakedQuad bakedQuad = makeBakedQuad(blockPart, blockPartFace, textureMap.getSpriteSafe(modelBlock.resolveTextureName(blockPartFace.field_178242_d)), enumFacing, x0_Y0, false);
                if (blockPartFace.field_178244_b == null) {
                    func_177646_a.func_177648_a(bakedQuad);
                }
                else {
                    func_177646_a.func_177650_a(x0_Y0.func_177523_a(blockPartFace.field_178244_b), bakedQuad);
                }
            }
        }
        return func_177646_a.func_177645_b();
    }
    
    private static BakedQuad makeBakedQuad(final BlockPart blockPart, final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ModelRotation modelRotation, final boolean b) {
        return new FaceBakery().func_178414_a(blockPart.field_178241_a, blockPart.field_178239_b, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.field_178237_d, b, blockPart.field_178238_e);
    }
    
    @Override
    public String toString() {
        return this.basePath + "/" + this.name + ", type: " + this.type + ", items: [" + Config.arrayToString(this.items) + "], textture: " + this.texture;
    }
    
    public float getTextureWidth(final TextureManager textureManager) {
        if (this.textureWidth <= 0) {
            if (this.textureLocation != null) {
                final int glTextureId = textureManager.getTexture(this.textureLocation).getGlTextureId();
                final int boundTexture = GlStateManager.getBoundTexture();
                GlStateManager.func_179144_i(glTextureId);
                this.textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
                GlStateManager.func_179144_i(boundTexture);
            }
            if (this.textureWidth <= 0) {
                this.textureWidth = 16;
            }
        }
        return (float)this.textureWidth;
    }
    
    public float getTextureHeight(final TextureManager textureManager) {
        if (this.textureHeight <= 0) {
            if (this.textureLocation != null) {
                final int glTextureId = textureManager.getTexture(this.textureLocation).getGlTextureId();
                final int boundTexture = GlStateManager.getBoundTexture();
                GlStateManager.func_179144_i(glTextureId);
                this.textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
                GlStateManager.func_179144_i(boundTexture);
            }
            if (this.textureHeight <= 0) {
                this.textureHeight = 16;
            }
        }
        return (float)this.textureHeight;
    }
    
    public IBakedModel getModel(final ModelResourceLocation modelResourceLocation) {
        if (modelResourceLocation != null && this.mapTextures != null) {
            final String resourcePath = modelResourceLocation.getResourcePath();
            if (this.mapModels != null) {
                final IBakedModel bakedModel = this.mapModels.get(resourcePath);
                if (bakedModel != null) {
                    return bakedModel;
                }
            }
        }
        return this.model;
    }
}
