package net.minecraft.client.renderer;

import com.google.common.collect.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.properties.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockModelShapes
{
    private final Map field_178129_a;
    private final BlockStateMapper blockStateMapper;
    private final ModelManager modelManager;
    private static final String __OBFID;
    
    public BlockModelShapes(final ModelManager modelManager) {
        this.field_178129_a = Maps.newIdentityHashMap();
        this.blockStateMapper = new BlockStateMapper();
        this.modelManager = modelManager;
        this.func_178119_d();
    }
    
    public BlockStateMapper getBlockStateMapper() {
        return this.blockStateMapper;
    }
    
    public TextureAtlasSprite func_178122_a(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        IBakedModel bakedModel = this.func_178125_b(blockState);
        if (bakedModel == null || bakedModel == this.modelManager.getMissingModel()) {
            if (block == Blocks.wall_sign || block == Blocks.standing_sign || block == Blocks.chest || block == Blocks.trapped_chest || block == Blocks.standing_banner || block == Blocks.wall_banner) {
                return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/planks_oak");
            }
            if (block == Blocks.ender_chest) {
                return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/obsidian");
            }
            if (block == Blocks.flowing_lava || block == Blocks.lava) {
                return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/lava_still");
            }
            if (block == Blocks.flowing_water || block == Blocks.water) {
                return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/water_still");
            }
            if (block == Blocks.skull) {
                return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/soul_sand");
            }
            if (block == Blocks.barrier) {
                return this.modelManager.func_174952_b().getAtlasSprite("minecraft:items/barrier");
            }
        }
        if (bakedModel == null) {
            bakedModel = this.modelManager.getMissingModel();
        }
        return bakedModel.getTexture();
    }
    
    public IBakedModel func_178125_b(final IBlockState blockState) {
        IBakedModel missingModel = this.field_178129_a.get(blockState);
        if (missingModel == null) {
            missingModel = this.modelManager.getMissingModel();
        }
        return missingModel;
    }
    
    public ModelManager func_178126_b() {
        return this.modelManager;
    }
    
    public void func_178124_c() {
        this.field_178129_a.clear();
        for (final Map.Entry<Object, V> entry : this.blockStateMapper.func_178446_a().entrySet()) {
            this.field_178129_a.put(entry.getKey(), this.modelManager.getModel((ModelResourceLocation)entry.getValue()));
        }
    }
    
    public void func_178121_a(final Block block, final IStateMapper stateMapper) {
        this.blockStateMapper.func_178447_a(block, stateMapper);
    }
    
    public void func_178123_a(final Block... array) {
        this.blockStateMapper.registerBuiltInBlocks(array);
    }
    
    private void func_178119_d() {
        this.func_178123_a(Blocks.air, Blocks.flowing_water, Blocks.water, Blocks.flowing_lava, Blocks.lava, Blocks.piston_extension, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.standing_sign, Blocks.skull, Blocks.end_portal, Blocks.barrier, Blocks.wall_sign, Blocks.wall_banner, Blocks.standing_banner);
        this.func_178121_a(Blocks.stone, new StateMap.Builder().func_178440_a(BlockStone.VARIANT_PROP).build());
        this.func_178121_a(Blocks.prismarine, new StateMap.Builder().func_178440_a(BlockPrismarine.VARIANTS).build());
        this.func_178121_a(Blocks.leaves, new StateMap.Builder().func_178440_a(BlockOldLeaf.VARIANT_PROP).func_178439_a("_leaves").func_178442_a(BlockLeaves.field_176236_b, BlockLeaves.field_176237_a).build());
        this.func_178121_a(Blocks.leaves2, new StateMap.Builder().func_178440_a(BlockNewLeaf.field_176240_P).func_178439_a("_leaves").func_178442_a(BlockLeaves.field_176236_b, BlockLeaves.field_176237_a).build());
        this.func_178121_a(Blocks.cactus, new StateMap.Builder().func_178442_a(BlockCactus.AGE_PROP).build());
        this.func_178121_a(Blocks.reeds, new StateMap.Builder().func_178442_a(BlockReed.field_176355_a).build());
        this.func_178121_a(Blocks.jukebox, new StateMap.Builder().func_178442_a(BlockJukebox.HAS_RECORD).build());
        this.func_178121_a(Blocks.command_block, new StateMap.Builder().func_178442_a(BlockCommandBlock.TRIGGERED_PROP).build());
        this.func_178121_a(Blocks.cobblestone_wall, new StateMap.Builder().func_178440_a(BlockWall.field_176255_P).func_178439_a("_wall").build());
        this.func_178121_a(Blocks.double_plant, new StateMap.Builder().func_178440_a(BlockDoublePlant.VARIANT_PROP).build());
        this.func_178121_a(Blocks.oak_fence_gate, new StateMap.Builder().func_178442_a(BlockFenceGate.field_176465_b).build());
        this.func_178121_a(Blocks.spruce_fence_gate, new StateMap.Builder().func_178442_a(BlockFenceGate.field_176465_b).build());
        this.func_178121_a(Blocks.birch_fence_gate, new StateMap.Builder().func_178442_a(BlockFenceGate.field_176465_b).build());
        this.func_178121_a(Blocks.jungle_fence_gate, new StateMap.Builder().func_178442_a(BlockFenceGate.field_176465_b).build());
        this.func_178121_a(Blocks.dark_oak_fence_gate, new StateMap.Builder().func_178442_a(BlockFenceGate.field_176465_b).build());
        this.func_178121_a(Blocks.acacia_fence_gate, new StateMap.Builder().func_178442_a(BlockFenceGate.field_176465_b).build());
        this.func_178121_a(Blocks.tripwire, new StateMap.Builder().func_178442_a(BlockTripWire.field_176295_N, BlockTripWire.field_176293_a).build());
        this.func_178121_a(Blocks.double_wooden_slab, new StateMap.Builder().func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_double_slab").build());
        this.func_178121_a(Blocks.wooden_slab, new StateMap.Builder().func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_slab").build());
        this.func_178121_a(Blocks.tnt, new StateMap.Builder().func_178442_a(BlockTNT.field_176246_a).build());
        this.func_178121_a(Blocks.fire, new StateMap.Builder().func_178442_a(BlockFire.field_176543_a).build());
        this.func_178121_a(Blocks.redstone_wire, new StateMap.Builder().func_178442_a(BlockRedstoneWire.POWER).build());
        this.func_178121_a(Blocks.oak_door, new StateMap.Builder().func_178442_a(BlockDoor.POWERED_PROP).build());
        this.func_178121_a(Blocks.spruce_door, new StateMap.Builder().func_178442_a(BlockDoor.POWERED_PROP).build());
        this.func_178121_a(Blocks.birch_door, new StateMap.Builder().func_178442_a(BlockDoor.POWERED_PROP).build());
        this.func_178121_a(Blocks.jungle_door, new StateMap.Builder().func_178442_a(BlockDoor.POWERED_PROP).build());
        this.func_178121_a(Blocks.acacia_door, new StateMap.Builder().func_178442_a(BlockDoor.POWERED_PROP).build());
        this.func_178121_a(Blocks.dark_oak_door, new StateMap.Builder().func_178442_a(BlockDoor.POWERED_PROP).build());
        this.func_178121_a(Blocks.iron_door, new StateMap.Builder().func_178442_a(BlockDoor.POWERED_PROP).build());
        this.func_178121_a(Blocks.wool, new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_wool").build());
        this.func_178121_a(Blocks.carpet, new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_carpet").build());
        this.func_178121_a(Blocks.stained_hardened_clay, new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_stained_hardened_clay").build());
        this.func_178121_a(Blocks.stained_glass_pane, new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_stained_glass_pane").build());
        this.func_178121_a(Blocks.stained_glass, new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_stained_glass").build());
        this.func_178121_a(Blocks.sandstone, new StateMap.Builder().func_178440_a(BlockSandStone.field_176297_a).build());
        this.func_178121_a(Blocks.red_sandstone, new StateMap.Builder().func_178440_a(BlockRedSandstone.TYPE).build());
        this.func_178121_a(Blocks.tallgrass, new StateMap.Builder().func_178440_a(BlockTallGrass.field_176497_a).build());
        this.func_178121_a(Blocks.bed, new StateMap.Builder().func_178442_a(BlockBed.OCCUPIED_PROP).build());
        this.func_178121_a(Blocks.yellow_flower, new StateMap.Builder().func_178440_a(Blocks.yellow_flower.func_176494_l()).build());
        this.func_178121_a(Blocks.red_flower, new StateMap.Builder().func_178440_a(Blocks.red_flower.func_176494_l()).build());
        this.func_178121_a(Blocks.stone_slab, new StateMap.Builder().func_178440_a(BlockStoneSlab.field_176556_M).func_178439_a("_slab").build());
        this.func_178121_a(Blocks.stone_slab2, new StateMap.Builder().func_178440_a(BlockStoneSlabNew.field_176559_M).func_178439_a("_slab").build());
        this.func_178121_a(Blocks.monster_egg, new StateMap.Builder().func_178440_a(BlockSilverfish.VARIANT_PROP).func_178439_a("_monster_egg").build());
        this.func_178121_a(Blocks.stonebrick, new StateMap.Builder().func_178440_a(BlockStoneBrick.VARIANT_PROP).build());
        this.func_178121_a(Blocks.dispenser, new StateMap.Builder().func_178442_a(BlockDispenser.TRIGGERED).build());
        this.func_178121_a(Blocks.dropper, new StateMap.Builder().func_178442_a(BlockDropper.TRIGGERED).build());
        this.func_178121_a(Blocks.log, new StateMap.Builder().func_178440_a(BlockOldLog.VARIANT_PROP).func_178439_a("_log").build());
        this.func_178121_a(Blocks.log2, new StateMap.Builder().func_178440_a(BlockNewLog.field_176300_b).func_178439_a("_log").build());
        this.func_178121_a(Blocks.planks, new StateMap.Builder().func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_planks").build());
        this.func_178121_a(Blocks.sapling, new StateMap.Builder().func_178440_a(BlockSapling.TYPE_PROP).func_178439_a("_sapling").build());
        this.func_178121_a(Blocks.sand, new StateMap.Builder().func_178440_a(BlockSand.VARIANT_PROP).build());
        this.func_178121_a(Blocks.hopper, new StateMap.Builder().func_178442_a(BlockHopper.field_176429_b).build());
        this.func_178121_a(Blocks.flower_pot, new StateMap.Builder().func_178442_a(BlockFlowerPot.field_176444_a).build());
        this.func_178121_a(Blocks.quartz_block, new StateMapperBase() {
            private static final String __OBFID;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
                switch (SwitchEnumType.field_178257_a[((BlockQuartz.EnumType)blockState.getValue(BlockQuartz.VARIANT_PROP)).ordinal()]) {
                    default: {
                        return new ModelResourceLocation("quartz_block", "normal");
                    }
                    case 2: {
                        return new ModelResourceLocation("chiseled_quartz_block", "normal");
                    }
                    case 3: {
                        return new ModelResourceLocation("quartz_column", "axis=y");
                    }
                    case 4: {
                        return new ModelResourceLocation("quartz_column", "axis=x");
                    }
                    case 5: {
                        return new ModelResourceLocation("quartz_column", "axis=z");
                    }
                }
            }
            
            static {
                __OBFID = "CL_00002528";
            }
        });
        this.func_178121_a(Blocks.deadbush, new StateMapperBase() {
            private static final String __OBFID;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
                return new ModelResourceLocation("dead_bush", "normal");
            }
            
            static {
                __OBFID = "CL_00002527";
            }
        });
        this.func_178121_a(Blocks.pumpkin_stem, new StateMapperBase() {
            private static final String __OBFID;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap(blockState.getProperties());
                if (blockState.getValue(BlockStem.FACING_PROP) != EnumFacing.UP) {
                    linkedHashMap.remove(BlockStem.AGE_PROP);
                }
                return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock()), this.func_178131_a(linkedHashMap));
            }
            
            static {
                __OBFID = "CL_00002526";
            }
        });
        this.func_178121_a(Blocks.melon_stem, new StateMapperBase() {
            private static final String __OBFID;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap(blockState.getProperties());
                if (blockState.getValue(BlockStem.FACING_PROP) != EnumFacing.UP) {
                    linkedHashMap.remove(BlockStem.AGE_PROP);
                }
                return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(blockState.getBlock()), this.func_178131_a(linkedHashMap));
            }
            
            static {
                __OBFID = "CL_00002525";
            }
        });
        this.func_178121_a(Blocks.dirt, new StateMapperBase() {
            private static final String __OBFID;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap(blockState.getProperties());
                final String name = BlockDirt.VARIANT.getName(linkedHashMap.remove(BlockDirt.VARIANT));
                if (BlockDirt.DirtType.PODZOL != blockState.getValue(BlockDirt.VARIANT)) {
                    linkedHashMap.remove(BlockDirt.SNOWY);
                }
                return new ModelResourceLocation(name, this.func_178131_a(linkedHashMap));
            }
            
            static {
                __OBFID = "CL_00002524";
            }
        });
        this.func_178121_a(Blocks.double_stone_slab, new StateMapperBase() {
            private static final String __OBFID;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap(blockState.getProperties());
                final String name = BlockStoneSlab.field_176556_M.getName(linkedHashMap.remove(BlockStoneSlab.field_176556_M));
                linkedHashMap.remove(BlockStoneSlab.field_176555_b);
                return new ModelResourceLocation(String.valueOf(name) + "_double_slab", blockState.getValue(BlockStoneSlab.field_176555_b) ? "all" : "normal");
            }
            
            static {
                __OBFID = "CL_00002523";
            }
        });
        this.func_178121_a(Blocks.double_stone_slab2, new StateMapperBase() {
            private static final String __OBFID;
            final BlockModelShapes this$0;
            
            @Override
            protected ModelResourceLocation func_178132_a(final IBlockState blockState) {
                final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap(blockState.getProperties());
                final String name = BlockStoneSlabNew.field_176559_M.getName(linkedHashMap.remove(BlockStoneSlabNew.field_176559_M));
                linkedHashMap.remove(BlockStoneSlab.field_176555_b);
                return new ModelResourceLocation(String.valueOf(name) + "_double_slab", blockState.getValue(BlockStoneSlabNew.field_176558_b) ? "all" : "normal");
            }
            
            static {
                __OBFID = "CL_00002522";
            }
        });
    }
    
    static {
        __OBFID = "CL_00002529";
    }
    
    static final class SwitchEnumType
    {
        static final int[] field_178257_a;
        private static final String __OBFID;
        private static final String[] lllIlIlllllIllI;
        private static String[] lllIlIlllllIlll;
        
        static {
            lIlIllIllllIIlII();
            lIlIllIllllIIIll();
            __OBFID = SwitchEnumType.lllIlIlllllIllI[0];
            field_178257_a = new int[BlockQuartz.EnumType.values().length];
            try {
                SwitchEnumType.field_178257_a[BlockQuartz.EnumType.DEFAULT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumType.field_178257_a[BlockQuartz.EnumType.CHISELED.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumType.field_178257_a[BlockQuartz.EnumType.LINES_Y.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumType.field_178257_a[BlockQuartz.EnumType.LINES_X.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumType.field_178257_a[BlockQuartz.EnumType.LINES_Z.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
        
        private static void lIlIllIllllIIIll() {
            (lllIlIlllllIllI = new String[1])[0] = lIlIllIllllIIIlI(SwitchEnumType.lllIlIlllllIlll[0], SwitchEnumType.lllIlIlllllIlll[1]);
            SwitchEnumType.lllIlIlllllIlll = null;
        }
        
        private static void lIlIllIllllIIlII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumType.lllIlIlllllIlll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIlIllIllllIIIlI(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s2.getBytes(StandardCharsets.UTF_8)), 8), "DES");
                final Cipher instance = Cipher.getInstance("DES");
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
