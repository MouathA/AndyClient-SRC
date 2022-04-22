package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public class BlockFlowerPot extends BlockContainer
{
    public static final PropertyInteger field_176444_a;
    public static final PropertyEnum field_176443_b;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000247";
        field_176444_a = PropertyInteger.create("legacy_data", 0, 15);
        field_176443_b = PropertyEnum.create("contents", EnumFlowerType.class);
    }
    
    public BlockFlowerPot() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFlowerPot.field_176443_b, EnumFlowerType.EMPTY).withProperty(BlockFlowerPot.field_176444_a, 0));
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.375f;
        final float n2 = n / 2.0f;
        this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, n, 0.5f + n2);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            final Item flowerPotItem = ((TileEntityFlowerPot)tileEntity).getFlowerPotItem();
            if (flowerPotItem instanceof ItemBlock) {
                return Block.getBlockFromItem(flowerPotItem).colorMultiplier(blockAccess, blockPos, n);
            }
        }
        return 16777215;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem == null || !(currentItem.getItem() instanceof ItemBlock)) {
            return false;
        }
        final TileEntityFlowerPot func_176442_d = this.func_176442_d(world, blockPos);
        if (func_176442_d == null) {
            return false;
        }
        if (func_176442_d.getFlowerPotItem() != null) {
            return false;
        }
        if (Block.getBlockFromItem(currentItem.getItem()) != currentItem.getMetadata()) {
            return false;
        }
        func_176442_d.func_145964_a(currentItem.getItem(), currentItem.getMetadata());
        func_176442_d.markDirty();
        world.markBlockForUpdate(blockPos);
        if (!entityPlayer.capabilities.isCreativeMode) {
            final ItemStack itemStack = currentItem;
            if (--itemStack.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
        }
        return true;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        final TileEntityFlowerPot func_176442_d = this.func_176442_d(world, blockPos);
        return (func_176442_d != null && func_176442_d.getFlowerPotItem() != null) ? func_176442_d.getFlowerPotItem() : Items.flower_pot;
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final TileEntityFlowerPot func_176442_d = this.func_176442_d(world, blockPos);
        return (func_176442_d != null && func_176442_d.getFlowerPotItem() != null) ? func_176442_d.getFlowerPotData() : 0;
    }
    
    @Override
    public boolean isFlowerPot() {
        return true;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return super.canPlaceBlockAt(world, blockPos) && World.doesBlockHaveSolidTopSurface(world, blockPos.offsetDown());
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.offsetDown())) {
            this.dropBlockAsItem(world, blockToAir, blockState, 0);
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntityFlowerPot func_176442_d = this.func_176442_d(world, blockPos);
        if (func_176442_d != null && func_176442_d.getFlowerPotItem() != null) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(func_176442_d.getFlowerPotItem(), 1, func_176442_d.getFlowerPotData()));
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
        if (entityPlayer.capabilities.isCreativeMode) {
            final TileEntityFlowerPot func_176442_d = this.func_176442_d(world, blockPos);
            if (func_176442_d != null) {
                func_176442_d.func_145964_a(null, 0);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.flower_pot;
    }
    
    private TileEntityFlowerPot func_176442_d(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityFlowerPot) ? ((TileEntityFlowerPot)tileEntity) : null;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        Block block = null;
        switch (n) {
            case 1: {
                block = Blocks.red_flower;
                BlockFlower.EnumFlowerType.POPPY.func_176968_b();
                break;
            }
            case 2: {
                block = Blocks.yellow_flower;
                break;
            }
            case 3: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.OAK.func_176839_a();
                break;
            }
            case 4: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.SPRUCE.func_176839_a();
                break;
            }
            case 5: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.BIRCH.func_176839_a();
                break;
            }
            case 6: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.JUNGLE.func_176839_a();
                break;
            }
            case 7: {
                block = Blocks.red_mushroom;
                break;
            }
            case 8: {
                block = Blocks.brown_mushroom;
                break;
            }
            case 9: {
                block = Blocks.cactus;
                break;
            }
            case 10: {
                block = Blocks.deadbush;
                break;
            }
            case 11: {
                block = Blocks.tallgrass;
                BlockTallGrass.EnumType.FERN.func_177044_a();
                break;
            }
            case 12: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.ACACIA.func_176839_a();
                break;
            }
            case 13: {
                block = Blocks.sapling;
                BlockPlanks.EnumType.DARK_OAK.func_176839_a();
                break;
            }
        }
        return new TileEntityFlowerPot(Item.getItemFromBlock(block), 0);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockFlowerPot.field_176443_b, BlockFlowerPot.field_176444_a });
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return (int)blockState.getValue(BlockFlowerPot.field_176444_a);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        EnumFlowerType enumFlowerType = EnumFlowerType.EMPTY;
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            final TileEntityFlowerPot tileEntityFlowerPot = (TileEntityFlowerPot)tileEntity;
            final Item flowerPotItem = tileEntityFlowerPot.getFlowerPotItem();
            if (flowerPotItem instanceof ItemBlock) {
                final int flowerPotData = tileEntityFlowerPot.getFlowerPotData();
                final Block blockFromItem = Block.getBlockFromItem(flowerPotItem);
                if (blockFromItem == Blocks.sapling) {
                    switch (SwitchEnumType.field_180353_a[BlockPlanks.EnumType.func_176837_a(flowerPotData).ordinal()]) {
                        case 1: {
                            enumFlowerType = EnumFlowerType.OAK_SAPLING;
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.SPRUCE_SAPLING;
                            break;
                        }
                        case 3: {
                            enumFlowerType = EnumFlowerType.BIRCH_SAPLING;
                            break;
                        }
                        case 4: {
                            enumFlowerType = EnumFlowerType.JUNGLE_SAPLING;
                            break;
                        }
                        case 5: {
                            enumFlowerType = EnumFlowerType.ACACIA_SAPLING;
                            break;
                        }
                        case 6: {
                            enumFlowerType = EnumFlowerType.DARK_OAK_SAPLING;
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.tallgrass) {
                    switch (flowerPotData) {
                        case 0: {
                            enumFlowerType = EnumFlowerType.DEAD_BUSH;
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.FERN;
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.yellow_flower) {
                    enumFlowerType = EnumFlowerType.DANDELION;
                }
                else if (blockFromItem == Blocks.red_flower) {
                    switch (SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.func_176967_a(BlockFlower.EnumFlowerColor.RED, flowerPotData).ordinal()]) {
                        case 1: {
                            enumFlowerType = EnumFlowerType.POPPY;
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.BLUE_ORCHID;
                            break;
                        }
                        case 3: {
                            enumFlowerType = EnumFlowerType.ALLIUM;
                            break;
                        }
                        case 4: {
                            enumFlowerType = EnumFlowerType.HOUSTONIA;
                            break;
                        }
                        case 5: {
                            enumFlowerType = EnumFlowerType.RED_TULIP;
                            break;
                        }
                        case 6: {
                            enumFlowerType = EnumFlowerType.ORANGE_TULIP;
                            break;
                        }
                        case 7: {
                            enumFlowerType = EnumFlowerType.WHITE_TULIP;
                            break;
                        }
                        case 8: {
                            enumFlowerType = EnumFlowerType.PINK_TULIP;
                            break;
                        }
                        case 9: {
                            enumFlowerType = EnumFlowerType.OXEYE_DAISY;
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.red_mushroom) {
                    enumFlowerType = EnumFlowerType.MUSHROOM_RED;
                }
                else if (blockFromItem == Blocks.brown_mushroom) {
                    enumFlowerType = EnumFlowerType.MUSHROOM_BROWN;
                }
                else if (blockFromItem == Blocks.deadbush) {
                    enumFlowerType = EnumFlowerType.DEAD_BUSH;
                }
                else if (blockFromItem == Blocks.cactus) {
                    enumFlowerType = EnumFlowerType.CACTUS;
                }
            }
        }
        return blockState.withProperty(BlockFlowerPot.field_176443_b, enumFlowerType);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    public enum EnumFlowerType implements IStringSerializable
    {
        EMPTY("EMPTY", 0, "EMPTY", 0, "empty"), 
        POPPY("POPPY", 1, "POPPY", 1, "rose"), 
        BLUE_ORCHID("BLUE_ORCHID", 2, "BLUE_ORCHID", 2, "blue_orchid"), 
        ALLIUM("ALLIUM", 3, "ALLIUM", 3, "allium"), 
        HOUSTONIA("HOUSTONIA", 4, "HOUSTONIA", 4, "houstonia"), 
        RED_TULIP("RED_TULIP", 5, "RED_TULIP", 5, "red_tulip"), 
        ORANGE_TULIP("ORANGE_TULIP", 6, "ORANGE_TULIP", 6, "orange_tulip"), 
        WHITE_TULIP("WHITE_TULIP", 7, "WHITE_TULIP", 7, "white_tulip"), 
        PINK_TULIP("PINK_TULIP", 8, "PINK_TULIP", 8, "pink_tulip"), 
        OXEYE_DAISY("OXEYE_DAISY", 9, "OXEYE_DAISY", 9, "oxeye_daisy"), 
        DANDELION("DANDELION", 10, "DANDELION", 10, "dandelion"), 
        OAK_SAPLING("OAK_SAPLING", 11, "OAK_SAPLING", 11, "oak_sapling"), 
        SPRUCE_SAPLING("SPRUCE_SAPLING", 12, "SPRUCE_SAPLING", 12, "spruce_sapling"), 
        BIRCH_SAPLING("BIRCH_SAPLING", 13, "BIRCH_SAPLING", 13, "birch_sapling"), 
        JUNGLE_SAPLING("JUNGLE_SAPLING", 14, "JUNGLE_SAPLING", 14, "jungle_sapling"), 
        ACACIA_SAPLING("ACACIA_SAPLING", 15, "ACACIA_SAPLING", 15, "acacia_sapling"), 
        DARK_OAK_SAPLING("DARK_OAK_SAPLING", 16, "DARK_OAK_SAPLING", 16, "dark_oak_sapling"), 
        MUSHROOM_RED("MUSHROOM_RED", 17, "MUSHROOM_RED", 17, "mushroom_red"), 
        MUSHROOM_BROWN("MUSHROOM_BROWN", 18, "MUSHROOM_BROWN", 18, "mushroom_brown"), 
        DEAD_BUSH("DEAD_BUSH", 19, "DEAD_BUSH", 19, "dead_bush"), 
        FERN("FERN", 20, "FERN", 20, "fern"), 
        CACTUS("CACTUS", 21, "CACTUS", 21, "cactus");
        
        private final String field_177006_w;
        private static final EnumFlowerType[] $VALUES;
        private static final String __OBFID;
        private static final EnumFlowerType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002115";
            ENUM$VALUES = new EnumFlowerType[] { EnumFlowerType.EMPTY, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY, EnumFlowerType.DANDELION, EnumFlowerType.OAK_SAPLING, EnumFlowerType.SPRUCE_SAPLING, EnumFlowerType.BIRCH_SAPLING, EnumFlowerType.JUNGLE_SAPLING, EnumFlowerType.ACACIA_SAPLING, EnumFlowerType.DARK_OAK_SAPLING, EnumFlowerType.MUSHROOM_RED, EnumFlowerType.MUSHROOM_BROWN, EnumFlowerType.DEAD_BUSH, EnumFlowerType.FERN, EnumFlowerType.CACTUS };
            $VALUES = new EnumFlowerType[] { EnumFlowerType.EMPTY, EnumFlowerType.POPPY, EnumFlowerType.BLUE_ORCHID, EnumFlowerType.ALLIUM, EnumFlowerType.HOUSTONIA, EnumFlowerType.RED_TULIP, EnumFlowerType.ORANGE_TULIP, EnumFlowerType.WHITE_TULIP, EnumFlowerType.PINK_TULIP, EnumFlowerType.OXEYE_DAISY, EnumFlowerType.DANDELION, EnumFlowerType.OAK_SAPLING, EnumFlowerType.SPRUCE_SAPLING, EnumFlowerType.BIRCH_SAPLING, EnumFlowerType.JUNGLE_SAPLING, EnumFlowerType.ACACIA_SAPLING, EnumFlowerType.DARK_OAK_SAPLING, EnumFlowerType.MUSHROOM_RED, EnumFlowerType.MUSHROOM_BROWN, EnumFlowerType.DEAD_BUSH, EnumFlowerType.FERN, EnumFlowerType.CACTUS };
        }
        
        private EnumFlowerType(final String s, final int n, final String s2, final int n2, final String field_177006_w) {
            this.field_177006_w = field_177006_w;
        }
        
        @Override
        public String toString() {
            return this.field_177006_w;
        }
        
        @Override
        public String getName() {
            return this.field_177006_w;
        }
    }
    
    static final class SwitchEnumType
    {
        static final int[] field_180353_a;
        static final int[] field_180352_b;
        private static final String __OBFID;
        private static final String[] lIlIIllIIlIIlIlI;
        private static String[] lIlIIllIIlIIlIll;
        
        static {
            llllIlIlIIIlllII();
            llllIlIlIIIllIll();
            __OBFID = SwitchEnumType.lIlIIllIIlIIlIlI[0];
            field_180352_b = new int[BlockFlower.EnumFlowerType.values().length];
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.POPPY.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.BLUE_ORCHID.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.ALLIUM.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.HOUSTONIA.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.RED_TULIP.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.ORANGE_TULIP.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.WHITE_TULIP.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.PINK_TULIP.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumType.field_180352_b[BlockFlower.EnumFlowerType.OXEYE_DAISY.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            field_180353_a = new int[BlockPlanks.EnumType.values().length];
            try {
                SwitchEnumType.field_180353_a[BlockPlanks.EnumType.OAK.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                SwitchEnumType.field_180353_a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                SwitchEnumType.field_180353_a[BlockPlanks.EnumType.BIRCH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                SwitchEnumType.field_180353_a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                SwitchEnumType.field_180353_a[BlockPlanks.EnumType.ACACIA.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                SwitchEnumType.field_180353_a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
        }
        
        private static void llllIlIlIIIllIll() {
            (lIlIIllIIlIIlIlI = new String[1])[0] = llllIlIlIIIllIlI(SwitchEnumType.lIlIIllIIlIIlIll[0], SwitchEnumType.lIlIIllIIlIIlIll[1]);
            SwitchEnumType.lIlIIllIIlIIlIll = null;
        }
        
        private static void llllIlIlIIIlllII() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumType.lIlIIllIIlIIlIll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String llllIlIlIIIllIlI(final String s, final String s2) {
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
