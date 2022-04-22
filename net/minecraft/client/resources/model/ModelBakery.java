package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.resources.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import java.io.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class ModelBakery
{
    private static final Set field_177602_b;
    private static final Logger LOGGER;
    protected static final ModelResourceLocation MODEL_MISSING;
    private static final Map BUILT_IN_MODELS;
    private static final Joiner field_177601_e;
    private final IResourceManager resourceManager;
    private final Map field_177599_g;
    private final Map models;
    private final Map variants;
    private final TextureMap textureMap;
    private final BlockModelShapes blockModelShapes;
    private final FaceBakery field_177607_l;
    private final ItemModelGenerator itemModelGenerator;
    private RegistrySimple bakedRegistry;
    private static final ModelBlock MODEL_GENERATED;
    private static final ModelBlock MODEL_COMPASS;
    private static final ModelBlock MODEL_CLOCK;
    private static final ModelBlock MODEL_ENTITY;
    private Map itemLocations;
    private final Map field_177614_t;
    private Map variantNames;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00002391";
        field_177602_b = Sets.newHashSet(new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"), new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"), new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"), new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"), new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"), new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"), new ResourceLocation("items/empty_armor_slot_helmet"), new ResourceLocation("items/empty_armor_slot_chestplate"), new ResourceLocation("items/empty_armor_slot_leggings"), new ResourceLocation("items/empty_armor_slot_boots"));
        LOGGER = LogManager.getLogger();
        MODEL_MISSING = new ModelResourceLocation("builtin/missing", "missing");
        (BUILT_IN_MODELS = Maps.newHashMap()).put("missing", "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
        field_177601_e = Joiner.on(" -> ");
        MODEL_GENERATED = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        MODEL_COMPASS = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        MODEL_CLOCK = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        MODEL_ENTITY = ModelBlock.deserialize("{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
        ModelBakery.MODEL_GENERATED.field_178317_b = "generation marker";
        ModelBakery.MODEL_COMPASS.field_178317_b = "compass generation marker";
        ModelBakery.MODEL_CLOCK.field_178317_b = "class generation marker";
        ModelBakery.MODEL_ENTITY.field_178317_b = "block entity marker";
    }
    
    public ModelBakery(final IResourceManager resourceManager, final TextureMap textureMap, final BlockModelShapes blockModelShapes) {
        this.field_177599_g = Maps.newHashMap();
        this.models = Maps.newLinkedHashMap();
        this.variants = Maps.newLinkedHashMap();
        this.field_177607_l = new FaceBakery();
        this.itemModelGenerator = new ItemModelGenerator();
        this.bakedRegistry = new RegistrySimple();
        this.itemLocations = Maps.newLinkedHashMap();
        this.field_177614_t = Maps.newHashMap();
        this.variantNames = Maps.newIdentityHashMap();
        this.resourceManager = resourceManager;
        this.textureMap = textureMap;
        this.blockModelShapes = blockModelShapes;
    }
    
    public IRegistry setupModelRegistry() {
        this.func_177577_b();
        this.func_177597_h();
        this.func_177572_j();
        this.bakeItemModels();
        this.bakeBlockModels();
        return this.bakedRegistry;
    }
    
    private void func_177577_b() {
        this.loadVariants(this.blockModelShapes.getBlockStateMapper().func_178446_a().values());
        this.variants.put(ModelBakery.MODEL_MISSING, new ModelBlockDefinition.Variants(ModelBakery.MODEL_MISSING.func_177518_c(), Lists.newArrayList(new ModelBlockDefinition.Variant(new ResourceLocation(ModelBakery.MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, false, 1))));
        final ResourceLocation resourceLocation = new ResourceLocation("item_frame");
        final ModelBlockDefinition modelBlockDefinition = this.getModelBlockDefinition(resourceLocation);
        this.registerVariant(modelBlockDefinition, new ModelResourceLocation(resourceLocation, "normal"));
        this.registerVariant(modelBlockDefinition, new ModelResourceLocation(resourceLocation, "map"));
        this.func_177595_c();
        this.loadItemModels();
    }
    
    private void loadVariants(final Collection collection) {
        for (final ModelResourceLocation modelResourceLocation : collection) {
            this.registerVariant(this.getModelBlockDefinition(modelResourceLocation), modelResourceLocation);
        }
    }
    
    private void registerVariant(final ModelBlockDefinition modelBlockDefinition, final ModelResourceLocation modelResourceLocation) {
        this.variants.put(modelResourceLocation, modelBlockDefinition.func_178330_b(modelResourceLocation.func_177518_c()));
    }
    
    private ModelBlockDefinition getModelBlockDefinition(final ResourceLocation resourceLocation) {
        final ResourceLocation blockStateLocation = this.getBlockStateLocation(resourceLocation);
        ModelBlockDefinition modelBlockDefinition = this.field_177614_t.get(blockStateLocation);
        if (modelBlockDefinition == null) {
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator iterator = this.resourceManager.getAllResources(blockStateLocation).iterator();
            while (iterator.hasNext()) {
                final InputStream inputStream = iterator.next().getInputStream();
                arrayList.add(ModelBlockDefinition.func_178331_a(new InputStreamReader(inputStream, Charsets.UTF_8)));
                IOUtils.closeQuietly(inputStream);
            }
            modelBlockDefinition = new ModelBlockDefinition(arrayList);
            this.field_177614_t.put(blockStateLocation, modelBlockDefinition);
        }
        return modelBlockDefinition;
    }
    
    private ResourceLocation getBlockStateLocation(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), "blockstates/" + resourceLocation.getResourcePath() + ".json");
    }
    
    private void func_177595_c() {
        final Iterator<ModelResourceLocation> iterator = this.variants.keySet().iterator();
        while (iterator.hasNext()) {
            final Iterator iterator2 = this.variants.get(iterator.next()).getVariants().iterator();
            while (iterator2.hasNext()) {
                final ResourceLocation modelLocation = iterator2.next().getModelLocation();
                if (this.models.get(modelLocation) == null) {
                    this.models.put(modelLocation, this.loadModel(modelLocation));
                }
            }
        }
    }
    
    private ModelBlock loadModel(final ResourceLocation resourceLocation) throws IOException {
        final String resourcePath = resourceLocation.getResourcePath();
        if ("builtin/generated".equals(resourcePath)) {
            return ModelBakery.MODEL_GENERATED;
        }
        if ("builtin/compass".equals(resourcePath)) {
            return ModelBakery.MODEL_COMPASS;
        }
        if ("builtin/clock".equals(resourcePath)) {
            return ModelBakery.MODEL_CLOCK;
        }
        if ("builtin/entity".equals(resourcePath)) {
            return ModelBakery.MODEL_ENTITY;
        }
        Reader reader;
        if (resourcePath.startsWith("builtin/")) {
            final String s = ModelBakery.BUILT_IN_MODELS.get(resourcePath.substring(8));
            if (s == null) {
                throw new FileNotFoundException(resourceLocation.toString());
            }
            reader = new StringReader(s);
        }
        else {
            reader = new InputStreamReader(this.resourceManager.getResource(this.getModelLocation(resourceLocation)).getInputStream(), Charsets.UTF_8);
        }
        final ModelBlock deserialize = ModelBlock.deserialize(reader);
        deserialize.field_178317_b = resourceLocation.toString();
        final ModelBlock modelBlock = deserialize;
        ((StringReader)reader).close();
        return modelBlock;
    }
    
    private ResourceLocation getModelLocation(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), "models/" + resourceLocation.getResourcePath() + ".json");
    }
    
    private void loadItemModels() {
        this.registerVariantNames();
        final Iterator iterator = Item.itemRegistry.iterator();
        while (iterator.hasNext()) {
            for (final String s : this.getVariantNames(iterator.next())) {
                final ResourceLocation itemLocation = this.getItemLocation(s);
                this.itemLocations.put(s, itemLocation);
                if (this.models.get(itemLocation) == null) {
                    this.models.put(itemLocation, this.loadModel(itemLocation));
                }
            }
        }
    }
    
    private void registerVariantNames() {
        this.variantNames.put(Item.getItemFromBlock(Blocks.stone), Lists.newArrayList("stone", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.dirt), Lists.newArrayList("dirt", "coarse_dirt", "podzol"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.planks), Lists.newArrayList("oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sapling), Lists.newArrayList("oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sand), Lists.newArrayList("sand", "red_sand"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.log), Lists.newArrayList("oak_log", "spruce_log", "birch_log", "jungle_log"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.leaves), Lists.newArrayList("oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sponge), Lists.newArrayList("sponge", "sponge_wet"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.sandstone), Lists.newArrayList("sandstone", "chiseled_sandstone", "smooth_sandstone"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.red_sandstone), Lists.newArrayList("red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.tallgrass), Lists.newArrayList("dead_bush", "tall_grass", "fern"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.deadbush), Lists.newArrayList("dead_bush"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.wool), Lists.newArrayList("black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool", "purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool", "light_blue_wool", "magenta_wool", "orange_wool", "white_wool"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.yellow_flower), Lists.newArrayList("dandelion"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.red_flower), Lists.newArrayList("poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stone_slab), Lists.newArrayList("stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stone_slab2), Lists.newArrayList("red_sandstone_slab"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stained_glass), Lists.newArrayList("black_stained_glass", "red_stained_glass", "green_stained_glass", "brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass", "silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass", "yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass", "orange_stained_glass", "white_stained_glass"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.monster_egg), Lists.newArrayList("stone_monster_egg", "cobblestone_monster_egg", "stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg", "chiseled_brick_monster_egg"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stonebrick), Lists.newArrayList("stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.wooden_slab), Lists.newArrayList("oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall), Lists.newArrayList("cobblestone_wall", "mossy_cobblestone_wall"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.anvil), Lists.newArrayList("anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.quartz_block), Lists.newArrayList("quartz_block", "chiseled_quartz_block", "quartz_column"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stained_hardened_clay), Lists.newArrayList("black_stained_hardened_clay", "red_stained_hardened_clay", "green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay", "purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay", "gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay", "yellow_stained_hardened_clay", "light_blue_stained_hardened_clay", "magenta_stained_hardened_clay", "orange_stained_hardened_clay", "white_stained_hardened_clay"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.stained_glass_pane), Lists.newArrayList("black_stained_glass_pane", "red_stained_glass_pane", "green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane", "purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane", "gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane", "yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane", "orange_stained_glass_pane", "white_stained_glass_pane"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.leaves2), Lists.newArrayList("acacia_leaves", "dark_oak_leaves"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.log2), Lists.newArrayList("acacia_log", "dark_oak_log"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.prismarine), Lists.newArrayList("prismarine", "prismarine_bricks", "dark_prismarine"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.carpet), Lists.newArrayList("black_carpet", "red_carpet", "green_carpet", "brown_carpet", "blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet", "lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet", "white_carpet"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.double_plant), Lists.newArrayList("sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia"));
        this.variantNames.put(Items.bow, Lists.newArrayList("bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2"));
        this.variantNames.put(Items.coal, Lists.newArrayList("coal", "charcoal"));
        this.variantNames.put(Items.fishing_rod, Lists.newArrayList("fishing_rod", "fishing_rod_cast"));
        this.variantNames.put(Items.fish, Lists.newArrayList("cod", "salmon", "clownfish", "pufferfish"));
        this.variantNames.put(Items.cooked_fish, Lists.newArrayList("cooked_cod", "cooked_salmon"));
        this.variantNames.put(Items.dye, Lists.newArrayList("dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue", "dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow", "dye_light_blue", "dye_magenta", "dye_orange", "dye_white"));
        this.variantNames.put(Items.potionitem, Lists.newArrayList("bottle_drinkable", "bottle_splash"));
        this.variantNames.put(Items.skull, Lists.newArrayList("skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate), Lists.newArrayList("oak_fence_gate"));
        this.variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList("oak_fence"));
        this.variantNames.put(Items.oak_door, Lists.newArrayList("oak_door"));
    }
    
    private List getVariantNames(final Item item) {
        List<String> singletonList = this.variantNames.get(item);
        if (singletonList == null) {
            singletonList = Collections.singletonList(((ResourceLocation)Item.itemRegistry.getNameForObject(item)).toString());
        }
        return singletonList;
    }
    
    private ResourceLocation getItemLocation(final String s) {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        return new ResourceLocation(resourceLocation.getResourceDomain(), "item/" + resourceLocation.getResourcePath());
    }
    
    private void bakeBlockModels() {
        for (final ModelResourceLocation modelResourceLocation : this.variants.keySet()) {
            final WeightedBakedModel.Builder builder = new WeightedBakedModel.Builder();
            for (final ModelBlockDefinition.Variant variant : this.variants.get(modelResourceLocation).getVariants()) {
                final ModelBlock modelBlock = this.models.get(variant.getModelLocation());
                if (modelBlock != null && modelBlock.isResolved()) {
                    int n = 0;
                    ++n;
                    builder.add(this.bakeModel(modelBlock, variant.getRotation(), variant.isUvLocked()), variant.getWeight());
                }
                else {
                    ModelBakery.LOGGER.warn("Missing model for: " + modelResourceLocation);
                }
            }
            if (!false) {
                ModelBakery.LOGGER.warn("No weighted models for: " + modelResourceLocation);
            }
            else if (false == true) {
                this.bakedRegistry.putObject(modelResourceLocation, builder.first());
            }
            else {
                this.bakedRegistry.putObject(modelResourceLocation, builder.build());
            }
        }
        for (final Map.Entry<K, ResourceLocation> entry : this.itemLocations.entrySet()) {
            final ResourceLocation resourceLocation = entry.getValue();
            final ModelResourceLocation modelResourceLocation2 = new ModelResourceLocation((String)entry.getKey(), "inventory");
            final ModelBlock modelBlock2 = this.models.get(resourceLocation);
            if (modelBlock2 != null && modelBlock2.isResolved()) {
                if (this.isCustomRenderer(modelBlock2)) {
                    this.bakedRegistry.putObject(modelResourceLocation2, new BuiltInModel(new ItemCameraTransforms(modelBlock2.getThirdPersonTransform(), modelBlock2.getFirstPersonTransform(), modelBlock2.getHeadTransform(), modelBlock2.getInGuiTransform())));
                }
                else {
                    this.bakedRegistry.putObject(modelResourceLocation2, this.bakeModel(modelBlock2, ModelRotation.X0_Y0, false));
                }
            }
            else {
                ModelBakery.LOGGER.warn("Missing model for: " + resourceLocation);
            }
        }
    }
    
    private Set func_177575_g() {
        final HashSet hashSet = Sets.newHashSet();
        final ArrayList arrayList = Lists.newArrayList(this.variants.keySet());
        Collections.sort((List<Object>)arrayList, new Comparator() {
            private static final String __OBFID;
            final ModelBakery this$0;
            
            public int func_177505_a(final ModelResourceLocation modelResourceLocation, final ModelResourceLocation modelResourceLocation2) {
                return modelResourceLocation.toString().compareTo(modelResourceLocation2.toString());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.func_177505_a((ModelResourceLocation)o, (ModelResourceLocation)o2);
            }
            
            static {
                __OBFID = "CL_00002390";
            }
        });
        for (final ModelResourceLocation modelResourceLocation : arrayList) {
            final Iterator iterator2 = this.variants.get(modelResourceLocation).getVariants().iterator();
            while (iterator2.hasNext()) {
                final ModelBlock modelBlock = this.models.get(iterator2.next().getModelLocation());
                if (modelBlock == null) {
                    ModelBakery.LOGGER.warn("Missing model for: " + modelResourceLocation);
                }
                else {
                    hashSet.addAll(this.func_177585_a(modelBlock));
                }
            }
        }
        hashSet.addAll(ModelBakery.field_177602_b);
        return hashSet;
    }
    
    private IBakedModel bakeModel(final ModelBlock modelBlock, final ModelRotation modelRotation, final boolean b) {
        final SimpleBakedModel.Builder func_177646_a = new SimpleBakedModel.Builder(modelBlock).func_177646_a(this.field_177599_g.get(new ResourceLocation(modelBlock.resolveTextureName("particle"))));
        for (final BlockPart blockPart : modelBlock.getElements()) {
            for (final EnumFacing enumFacing : blockPart.field_178240_c.keySet()) {
                final BlockPartFace blockPartFace = blockPart.field_178240_c.get(enumFacing);
                final TextureAtlasSprite textureAtlasSprite = this.field_177599_g.get(new ResourceLocation(modelBlock.resolveTextureName(blockPartFace.field_178242_d)));
                if (blockPartFace.field_178244_b == null) {
                    func_177646_a.func_177648_a(this.func_177589_a(blockPart, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, b));
                }
                else {
                    func_177646_a.func_177650_a(modelRotation.func_177523_a(blockPartFace.field_178244_b), this.func_177589_a(blockPart, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, b));
                }
            }
        }
        return func_177646_a.func_177645_b();
    }
    
    private BakedQuad func_177589_a(final BlockPart blockPart, final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ModelRotation modelRotation, final boolean b) {
        return this.field_177607_l.func_178414_a(blockPart.field_178241_a, blockPart.field_178239_b, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.field_178237_d, b, blockPart.field_178238_e);
    }
    
    private void func_177597_h() {
        this.func_177574_i();
        final Iterator<ModelBlock> iterator = this.models.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().getParentFromMap(this.models);
        }
        ModelBlock.func_178312_b(this.models);
    }
    
    private void func_177574_i() {
        final ArrayDeque arrayDeque = Queues.newArrayDeque();
        final HashSet hashSet = Sets.newHashSet();
        for (final ResourceLocation resourceLocation : this.models.keySet()) {
            hashSet.add(resourceLocation);
            final ResourceLocation parentLocation = this.models.get(resourceLocation).getParentLocation();
            if (parentLocation != null) {
                arrayDeque.add(parentLocation);
            }
        }
        while (!arrayDeque.isEmpty()) {
            final ResourceLocation resourceLocation2 = arrayDeque.pop();
            if (this.models.get(resourceLocation2) != null) {
                continue;
            }
            final ModelBlock loadModel = this.loadModel(resourceLocation2);
            this.models.put(resourceLocation2, loadModel);
            final ResourceLocation parentLocation2 = loadModel.getParentLocation();
            if (parentLocation2 != null && !hashSet.contains(parentLocation2)) {
                arrayDeque.add(parentLocation2);
            }
            hashSet.add(resourceLocation2);
        }
    }
    
    private List func_177573_e(final ResourceLocation resourceLocation) {
        final ArrayList arrayList = Lists.newArrayList(resourceLocation);
        ResourceLocation func_177576_f = resourceLocation;
        while ((func_177576_f = this.func_177576_f(func_177576_f)) != null) {
            arrayList.add(0, func_177576_f);
        }
        return arrayList;
    }
    
    private ResourceLocation func_177576_f(final ResourceLocation resourceLocation) {
        for (final Map.Entry<K, ModelBlock> entry : this.models.entrySet()) {
            final ModelBlock modelBlock = entry.getValue();
            if (modelBlock != null && resourceLocation.equals(modelBlock.getParentLocation())) {
                return (ResourceLocation)entry.getKey();
            }
        }
        return null;
    }
    
    private Set func_177585_a(final ModelBlock modelBlock) {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<BlockPart> iterator = modelBlock.getElements().iterator();
        while (iterator.hasNext()) {
            final Iterator<BlockPartFace> iterator2 = iterator.next().field_178240_c.values().iterator();
            while (iterator2.hasNext()) {
                hashSet.add(new ResourceLocation(modelBlock.resolveTextureName(iterator2.next().field_178242_d)));
            }
        }
        hashSet.add(new ResourceLocation(modelBlock.resolveTextureName("particle")));
        return hashSet;
    }
    
    private void func_177572_j() {
        final Set func_177575_g = this.func_177575_g();
        func_177575_g.addAll(this.func_177571_k());
        func_177575_g.remove(TextureMap.field_174945_f);
        this.textureMap.func_174943_a(this.resourceManager, new IIconCreator(func_177575_g) {
            private static final String __OBFID;
            final ModelBakery this$0;
            private final Set val$var1;
            
            @Override
            public void func_177059_a(final TextureMap textureMap) {
                for (final ResourceLocation resourceLocation : this.val$var1) {
                    ModelBakery.access$0(this.this$0).put(resourceLocation, textureMap.func_174942_a(resourceLocation));
                }
            }
            
            static {
                __OBFID = "CL_00002389";
            }
        });
        this.field_177599_g.put(new ResourceLocation("missingno"), this.textureMap.func_174944_f());
    }
    
    private Set func_177571_k() {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<ResourceLocation> iterator = this.itemLocations.values().iterator();
        while (iterator.hasNext()) {
            final ModelBlock modelBlock = this.models.get(iterator.next());
            if (modelBlock != null) {
                hashSet.add(new ResourceLocation(modelBlock.resolveTextureName("particle")));
                if (this.func_177581_b(modelBlock)) {
                    final Iterator<String> iterator2 = (Iterator<String>)ItemModelGenerator.LAYERS.iterator();
                    while (iterator2.hasNext()) {
                        final ResourceLocation resourceLocation = new ResourceLocation(modelBlock.resolveTextureName(iterator2.next()));
                        if (modelBlock.getRootModel() == ModelBakery.MODEL_COMPASS && !TextureMap.field_174945_f.equals(resourceLocation)) {
                            TextureAtlasSprite.func_176603_b(resourceLocation.toString());
                        }
                        else if (modelBlock.getRootModel() == ModelBakery.MODEL_CLOCK && !TextureMap.field_174945_f.equals(resourceLocation)) {
                            TextureAtlasSprite.func_176602_a(resourceLocation.toString());
                        }
                        hashSet.add(resourceLocation);
                    }
                }
                else {
                    if (this.isCustomRenderer(modelBlock)) {
                        continue;
                    }
                    final Iterator iterator3 = modelBlock.getElements().iterator();
                    while (iterator3.hasNext()) {
                        final Iterator<BlockPartFace> iterator4 = iterator3.next().field_178240_c.values().iterator();
                        while (iterator4.hasNext()) {
                            hashSet.add(new ResourceLocation(modelBlock.resolveTextureName(iterator4.next().field_178242_d)));
                        }
                    }
                }
            }
        }
        return hashSet;
    }
    
    private boolean func_177581_b(final ModelBlock modelBlock) {
        if (modelBlock == null) {
            return false;
        }
        final ModelBlock rootModel = modelBlock.getRootModel();
        return rootModel == ModelBakery.MODEL_GENERATED || rootModel == ModelBakery.MODEL_COMPASS || rootModel == ModelBakery.MODEL_CLOCK;
    }
    
    private boolean isCustomRenderer(final ModelBlock modelBlock) {
        return modelBlock != null && modelBlock.getRootModel() == ModelBakery.MODEL_ENTITY;
    }
    
    private void bakeItemModels() {
        for (final ResourceLocation resourceLocation : this.itemLocations.values()) {
            final ModelBlock modelBlock = this.models.get(resourceLocation);
            if (this.func_177581_b(modelBlock)) {
                final ModelBlock func_177582_d = this.func_177582_d(modelBlock);
                if (func_177582_d != null) {
                    func_177582_d.field_178317_b = resourceLocation.toString();
                }
                this.models.put(resourceLocation, func_177582_d);
            }
            else {
                if (!this.isCustomRenderer(modelBlock)) {
                    continue;
                }
                this.models.put(resourceLocation, modelBlock);
            }
        }
        for (final TextureAtlasSprite textureAtlasSprite : this.field_177599_g.values()) {
            if (!textureAtlasSprite.hasAnimationMetadata()) {
                textureAtlasSprite.clearFramesTextureData();
            }
        }
    }
    
    private ModelBlock func_177582_d(final ModelBlock modelBlock) {
        return this.itemModelGenerator.func_178392_a(this.textureMap, modelBlock);
    }
    
    static Map access$0(final ModelBakery modelBakery) {
        return modelBakery.field_177599_g;
    }
}
