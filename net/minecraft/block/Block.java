package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import java.util.*;
import com.google.common.collect.*;

public class Block
{
    private static final ResourceLocation AIR_ID;
    public static final RegistryNamespacedDefaultedByKey blockRegistry;
    public static final ObjectIntIdentityMap BLOCK_STATE_IDS;
    private CreativeTabs displayOnCreativeTab;
    public static final SoundType soundTypeStone;
    public static final SoundType soundTypeWood;
    public static final SoundType soundTypeGravel;
    public static final SoundType soundTypeGrass;
    public static final SoundType soundTypePiston;
    public static final SoundType soundTypeMetal;
    public static final SoundType soundTypeGlass;
    public static final SoundType soundTypeCloth;
    public static final SoundType soundTypeSand;
    public static final SoundType soundTypeSnow;
    public static final SoundType soundTypeLadder;
    public static final SoundType soundTypeAnvil;
    public static final SoundType SLIME_SOUND;
    protected boolean fullBlock;
    protected int lightOpacity;
    protected boolean translucent;
    protected int lightValue;
    protected boolean useNeighborBrightness;
    protected float blockHardness;
    protected float blockResistance;
    protected boolean enableStats;
    protected boolean needsRandomTick;
    protected boolean isBlockContainer;
    protected double minX;
    protected double minY;
    protected double minZ;
    protected double maxX;
    protected double maxY;
    protected double maxZ;
    public SoundType stepSound;
    public float blockParticleGravity;
    protected final Material blockMaterial;
    public float slipperiness;
    protected final BlockState blockState;
    private IBlockState defaultBlockState;
    private String unlocalizedName;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000199";
        AIR_ID = new ResourceLocation("air");
        blockRegistry = new RegistryNamespacedDefaultedByKey(Block.AIR_ID);
        BLOCK_STATE_IDS = new ObjectIntIdentityMap();
        soundTypeStone = new SoundType("stone", 1.0f, 1.0f);
        soundTypeWood = new SoundType("wood", 1.0f, 1.0f);
        soundTypeGravel = new SoundType("gravel", 1.0f, 1.0f);
        soundTypeGrass = new SoundType("grass", 1.0f, 1.0f);
        soundTypePiston = new SoundType("stone", 1.0f, 1.0f);
        soundTypeMetal = new SoundType("stone", 1.0f, 1.5f);
        soundTypeGlass = new SoundType(1.0f, 1.0f) {
            private static final String __OBFID;
            
            @Override
            public String getBreakSound() {
                return "dig.glass";
            }
            
            @Override
            public String getPlaceSound() {
                return "step.stone";
            }
            
            static {
                __OBFID = "CL_00000200";
            }
        };
        soundTypeCloth = new SoundType("cloth", 1.0f, 1.0f);
        soundTypeSand = new SoundType("sand", 1.0f, 1.0f);
        soundTypeSnow = new SoundType("snow", 1.0f, 1.0f);
        soundTypeLadder = new SoundType(1.0f, 1.0f) {
            private static final String __OBFID;
            
            @Override
            public String getBreakSound() {
                return "dig.wood";
            }
            
            static {
                __OBFID = "CL_00000201";
            }
        };
        soundTypeAnvil = new SoundType(0.3f, 1.0f) {
            private static final String __OBFID;
            
            @Override
            public String getBreakSound() {
                return "dig.stone";
            }
            
            @Override
            public String getPlaceSound() {
                return "random.anvil_land";
            }
            
            static {
                __OBFID = "CL_00000202";
            }
        };
        SLIME_SOUND = new SoundType(1.0f, 1.0f) {
            private static final String __OBFID;
            
            @Override
            public String getBreakSound() {
                return "mob.slime.big";
            }
            
            @Override
            public String getPlaceSound() {
                return "mob.slime.big";
            }
            
            @Override
            public String getStepSound() {
                return "mob.slime.small";
            }
            
            static {
                __OBFID = "CL_00002133";
            }
        };
    }
    
    public static int getIdFromBlock(final Block block) {
        return Block.blockRegistry.getIDForObject(block);
    }
    
    public static int getStateId(final IBlockState blockState) {
        return getIdFromBlock(blockState.getBlock()) + (blockState.getBlock().getMetaFromState(blockState) << 12);
    }
    
    public static Block getBlockById(final int n) {
        return (Block)Block.blockRegistry.getObjectById(n);
    }
    
    public static IBlockState getStateById(final int n) {
        return getBlockById(n & 0xFFF).getStateFromMeta(n >> 12 & 0xF);
    }
    
    public static Block getBlockFromItem(final Item item) {
        return (item instanceof ItemBlock) ? ((ItemBlock)item).getBlock() : null;
    }
    
    public static Block getBlockFromName(final String s) {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        if (Block.blockRegistry.containsKey(resourceLocation)) {
            return (Block)Block.blockRegistry.getObject(resourceLocation);
        }
        return (Block)Block.blockRegistry.getObjectById(Integer.parseInt(s));
    }
    
    public boolean isFullBlock() {
        return this.fullBlock;
    }
    
    public int getLightOpacity() {
        return this.lightOpacity;
    }
    
    public boolean isTranslucent() {
        return this.translucent;
    }
    
    public int getLightValue() {
        return this.lightValue;
    }
    
    public boolean getUseNeighborBrightness() {
        return this.useNeighborBrightness;
    }
    
    public Material getMaterial() {
        return this.blockMaterial;
    }
    
    public MapColor getMapColor(final IBlockState blockState) {
        return this.getMaterial().getMaterialMapColor();
    }
    
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState();
    }
    
    public int getMetaFromState(final IBlockState blockState) {
        if (blockState != null && !blockState.getPropertyNames().isEmpty()) {
            throw new IllegalArgumentException("Don't know how to convert " + blockState + " back into data...");
        }
        return 0;
    }
    
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState;
    }
    
    protected Block(final Material blockMaterial) {
        this.enableStats = true;
        this.stepSound = Block.soundTypeStone;
        this.blockParticleGravity = 1.0f;
        this.slipperiness = 0.6f;
        this.blockMaterial = blockMaterial;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.fullBlock = this.isOpaqueCube();
        this.lightOpacity = (this.isOpaqueCube() ? 255 : 0);
        this.translucent = !blockMaterial.blocksLight();
        this.blockState = this.createBlockState();
        this.setDefaultState(this.blockState.getBaseState());
    }
    
    protected Block setStepSound(final SoundType stepSound) {
        this.stepSound = stepSound;
        return this;
    }
    
    protected Block setLightOpacity(final int lightOpacity) {
        this.lightOpacity = lightOpacity;
        return this;
    }
    
    protected Block setLightLevel(final float n) {
        this.lightValue = (int)(15.0f * n);
        return this;
    }
    
    protected Block setResistance(final float n) {
        this.blockResistance = n * 3.0f;
        return this;
    }
    
    public boolean isNormalCube() {
        return this.blockMaterial.isOpaque() && this.isFullCube() && !this.canProvidePower();
    }
    
    public boolean isVisuallyOpaque() {
        return this.blockMaterial.blocksMovement() && this.isFullCube();
    }
    
    public boolean isFullCube() {
        return true;
    }
    
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return !this.blockMaterial.blocksMovement();
    }
    
    public int getRenderType() {
        return 3;
    }
    
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return false;
    }
    
    protected Block setHardness(final float blockHardness) {
        this.blockHardness = blockHardness;
        if (this.blockResistance < blockHardness * 5.0f) {
            this.blockResistance = blockHardness * 5.0f;
        }
        return this;
    }
    
    protected Block setBlockUnbreakable() {
        this.setHardness(-1.0f);
        return this;
    }
    
    public float getBlockHardness(final World world, final BlockPos blockPos) {
        return this.blockHardness;
    }
    
    protected Block setTickRandomly(final boolean needsRandomTick) {
        this.needsRandomTick = needsRandomTick;
        return this;
    }
    
    public boolean getTickRandomly() {
        return this.needsRandomTick;
    }
    
    public boolean hasTileEntity() {
        return this.isBlockContainer;
    }
    
    protected final void setBlockBounds(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.minX = n;
        this.minY = n2;
        this.minZ = n3;
        this.maxX = n4;
        this.maxY = n5;
        this.maxZ = n6;
    }
    
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, BlockPos offsetDown) {
        final Block block = blockAccess.getBlockState(offsetDown).getBlock();
        final int combinedLight = blockAccess.getCombinedLight(offsetDown, block.getLightValue());
        if (combinedLight == 0 && block instanceof BlockSlab) {
            offsetDown = offsetDown.offsetDown();
            return blockAccess.getCombinedLight(offsetDown, blockAccess.getBlockState(offsetDown).getBlock().getLightValue());
        }
        return combinedLight;
    }
    
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.DOWN && this.minY > 0.0) || (enumFacing == EnumFacing.UP && this.maxY < 1.0) || (enumFacing == EnumFacing.NORTH && this.minZ > 0.0) || (enumFacing == EnumFacing.SOUTH && this.maxZ < 1.0) || (enumFacing == EnumFacing.WEST && this.minX > 0.0) || (enumFacing == EnumFacing.EAST && this.maxX < 1.0) || !blockAccess.getBlockState(blockPos).getBlock().isOpaqueCube();
    }
    
    public boolean isBlockSolid(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return blockAccess.getBlockState(blockPos).getBlock().getMaterial().isSolid();
    }
    
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ);
    }
    
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List list, final Entity entity) {
        final AxisAlignedBB collisionBoundingBox = this.getCollisionBoundingBox(world, blockPos, blockState);
        if (collisionBoundingBox != null && axisAlignedBB.intersectsWith(collisionBoundingBox)) {
            list.add(collisionBoundingBox);
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ);
    }
    
    public boolean isOpaqueCube() {
        return true;
    }
    
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return this.isCollidable();
    }
    
    public boolean isCollidable() {
        return true;
    }
    
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.updateTick(world, blockPos, blockState, random);
    }
    
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
    }
    
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
    }
    
    public int tickRate(final World world) {
        return 10;
    }
    
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
    }
    
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
    }
    
    public int quantityDropped(final Random random) {
        return 1;
    }
    
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(this);
    }
    
    public float getPlayerRelativeBlockHardness(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos) {
        final float blockHardness = this.getBlockHardness(world, blockPos);
        return (blockHardness < 0.0f) ? 0.0f : (entityPlayer.canHarvestBlock(this) ? (entityPlayer.func_180471_a(this) / blockHardness / 30.0f) : (entityPlayer.func_180471_a(this) / blockHardness / 100.0f));
    }
    
    public final void dropBlockAsItem(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
        this.dropBlockAsItemWithChance(world, blockPos, blockState, 1.0f, n);
    }
    
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            while (0 < this.quantityDroppedWithBonus(n2, world.rand)) {
                if (world.rand.nextFloat() <= n) {
                    final Item itemDropped = this.getItemDropped(blockState, world.rand, n2);
                    if (itemDropped != null) {
                        spawnAsEntity(world, blockPos, new ItemStack(itemDropped, 1, this.damageDropped(blockState)));
                    }
                }
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    public static void spawnAsEntity(final World world, final BlockPos blockPos, final ItemStack itemStack) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            final float n = 0.5f;
            final EntityItem entityItem = new EntityItem(world, blockPos.getX() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), blockPos.getY() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), blockPos.getZ() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), itemStack);
            entityItem.setDefaultPickupDelay();
            world.spawnEntityInWorld(entityItem);
        }
    }
    
    protected void dropXpOnBlockBreak(final World world, final BlockPos blockPos, int i) {
        if (!world.isRemote) {
            while (i > 0) {
                final int xpSplit = EntityXPOrb.getXPSplit(i);
                i -= xpSplit;
                world.spawnEntityInWorld(new EntityXPOrb(world, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, xpSplit));
            }
        }
    }
    
    public int damageDropped(final IBlockState blockState) {
        return 0;
    }
    
    public float getExplosionResistance(final Entity entity) {
        return this.blockResistance / 5.0f;
    }
    
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, Vec3 addVector, Vec3 addVector2) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        addVector = addVector.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        addVector2 = addVector2.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        Vec3 intermediateWithXValue = addVector.getIntermediateWithXValue(addVector2, this.minX);
        Vec3 intermediateWithXValue2 = addVector.getIntermediateWithXValue(addVector2, this.maxX);
        Vec3 intermediateWithYValue = addVector.getIntermediateWithYValue(addVector2, this.minY);
        Vec3 intermediateWithYValue2 = addVector.getIntermediateWithYValue(addVector2, this.maxY);
        Vec3 intermediateWithZValue = addVector.getIntermediateWithZValue(addVector2, this.minZ);
        Vec3 intermediateWithZValue2 = addVector.getIntermediateWithZValue(addVector2, this.maxZ);
        if (intermediateWithXValue == null) {
            intermediateWithXValue = null;
        }
        if (intermediateWithXValue2 == null) {
            intermediateWithXValue2 = null;
        }
        if (intermediateWithYValue == null) {
            intermediateWithYValue = null;
        }
        if (intermediateWithYValue2 == null) {
            intermediateWithYValue2 = null;
        }
        if (intermediateWithZValue == null) {
            intermediateWithZValue = null;
        }
        if (intermediateWithZValue2 == null) {
            intermediateWithZValue2 = null;
        }
        Vec3 vec3 = null;
        if (intermediateWithXValue != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithXValue) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithXValue;
        }
        if (intermediateWithXValue2 != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithXValue2) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithXValue2;
        }
        if (intermediateWithYValue != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithYValue) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithYValue;
        }
        if (intermediateWithYValue2 != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithYValue2) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithYValue2;
        }
        if (intermediateWithZValue != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithZValue) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithZValue;
        }
        if (intermediateWithZValue2 != null && (vec3 == null || addVector.squareDistanceTo(intermediateWithZValue2) < addVector.squareDistanceTo(vec3))) {
            vec3 = intermediateWithZValue2;
        }
        if (vec3 == null) {
            return null;
        }
        EnumFacing enumFacing = null;
        if (vec3 == intermediateWithXValue) {
            enumFacing = EnumFacing.WEST;
        }
        if (vec3 == intermediateWithXValue2) {
            enumFacing = EnumFacing.EAST;
        }
        if (vec3 == intermediateWithYValue) {
            enumFacing = EnumFacing.DOWN;
        }
        if (vec3 == intermediateWithYValue2) {
            enumFacing = EnumFacing.UP;
        }
        if (vec3 == intermediateWithZValue) {
            enumFacing = EnumFacing.NORTH;
        }
        if (vec3 == intermediateWithZValue2) {
            enumFacing = EnumFacing.SOUTH;
        }
        return new MovingObjectPosition(vec3.addVector(blockPos.getX(), blockPos.getY(), blockPos.getZ()), enumFacing, blockPos);
    }
    
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
    }
    
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.SOLID;
    }
    
    public boolean canReplace(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final ItemStack itemStack) {
        return this.canPlaceBlockOnSide(world, blockPos, enumFacing);
    }
    
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return this.canPlaceBlockAt(world, blockPos);
    }
    
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable();
    }
    
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return false;
    }
    
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
    }
    
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getStateFromMeta(n4);
    }
    
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
    }
    
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return vec3;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
    }
    
    public final double getBlockBoundsMinX() {
        return this.minX;
    }
    
    public final double getBlockBoundsMaxX() {
        return this.maxX;
    }
    
    public final double getBlockBoundsMinY() {
        return this.minY;
    }
    
    public final double getBlockBoundsMaxY() {
        return this.maxY;
    }
    
    public final double getBlockBoundsMinZ() {
        return this.minZ;
    }
    
    public final double getBlockBoundsMaxZ() {
        return this.maxZ;
    }
    
    public int getBlockColor() {
        return 16777215;
    }
    
    public int getRenderColor(final IBlockState blockState) {
        return 16777215;
    }
    
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return 16777215;
    }
    
    public final int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.colorMultiplier(blockAccess, blockPos, 0);
    }
    
    public int isProvidingWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return 0;
    }
    
    public boolean canProvidePower() {
        return false;
    }
    
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
    }
    
    public int isProvidingStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return 0;
    }
    
    public void setBlockBoundsForItemRender() {
    }
    
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
        entityPlayer.addExhaustion(0.025f);
        if (this != 0 && EnchantmentHelper.getSilkTouchModifier(entityPlayer)) {
            final ItemStack stackedBlock = this.createStackedBlock(blockState);
            if (stackedBlock != null) {
                spawnAsEntity(world, blockPos, stackedBlock);
            }
        }
        else {
            this.dropBlockAsItem(world, blockPos, blockState, EnchantmentHelper.getFortuneModifier(entityPlayer));
        }
    }
    
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        final Item itemFromBlock = Item.getItemFromBlock(this);
        if (itemFromBlock != null && itemFromBlock.getHasSubtypes()) {
            this.getMetaFromState(blockState);
        }
        return new ItemStack(itemFromBlock, 1, 0);
    }
    
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return this.quantityDropped(random);
    }
    
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
    }
    
    public Block setUnlocalizedName(final String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }
    
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + ".name");
    }
    
    public String getUnlocalizedName() {
        return "tile." + this.unlocalizedName;
    }
    
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        return false;
    }
    
    public boolean getEnableStats() {
        return this.enableStats;
    }
    
    protected Block disableStats() {
        this.enableStats = false;
        return this;
    }
    
    public int getMobilityFlag() {
        return this.blockMaterial.getMaterialMobility();
    }
    
    public float getAmbientOcclusionLightValue() {
        return (this != 0) ? 0.2f : 1.0f;
    }
    
    public void onFallenUpon(final World world, final BlockPos blockPos, final Entity entity, final float n) {
        entity.fall(n, 1.0f);
    }
    
    public void onLanded(final World world, final Entity entity) {
        entity.motionY = 0.0;
    }
    
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(this);
    }
    
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return this.damageDropped(world.getBlockState(blockPos));
    }
    
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List list) {
        list.add(new ItemStack(item, 1, 0));
    }
    
    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.displayOnCreativeTab;
    }
    
    public Block setCreativeTab(final CreativeTabs displayOnCreativeTab) {
        this.displayOnCreativeTab = displayOnCreativeTab;
        return this;
    }
    
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
    }
    
    public void fillWithRain(final World world, final BlockPos blockPos) {
    }
    
    public boolean isFlowerPot() {
        return false;
    }
    
    public boolean requiresUpdates() {
        return true;
    }
    
    public boolean canDropFromExplosion(final Explosion explosion) {
        return true;
    }
    
    public boolean isAssociatedBlock(final Block block) {
        return this == block;
    }
    
    public static boolean isEqualTo(final Block block, final Block block2) {
        return block != null && block2 != null && (block == block2 || block.isAssociatedBlock(block2));
    }
    
    public boolean hasComparatorInputOverride() {
        return false;
    }
    
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return 0;
    }
    
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return blockState;
    }
    
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[0]);
    }
    
    public BlockState getBlockState() {
        return this.blockState;
    }
    
    protected final void setDefaultState(final IBlockState defaultBlockState) {
        this.defaultBlockState = defaultBlockState;
    }
    
    public final IBlockState getDefaultState() {
        return this.defaultBlockState;
    }
    
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.NONE;
    }
    
    public static void registerBlocks() {
        registerBlock(0, Block.AIR_ID, new BlockAir().setUnlocalizedName("air"));
        registerBlock(1, "stone", new BlockStone().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stone"));
        registerBlock(2, "grass", new BlockGrass().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("grass"));
        registerBlock(3, "dirt", new BlockDirt().setHardness(0.5f).setStepSound(Block.soundTypeGravel).setUnlocalizedName("dirt"));
        final Block setCreativeTab = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(4, "cobblestone", setCreativeTab);
        final Block setUnlocalizedName = new BlockPlanks().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("wood");
        registerBlock(5, "planks", setUnlocalizedName);
        registerBlock(6, "sapling", new BlockSapling().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("sapling"));
        registerBlock(7, "bedrock", new Block(Material.rock).setBlockUnbreakable().setResistance(6000000.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(8, "flowing_water", new BlockDynamicLiquid(Material.water).setHardness(100.0f).setLightOpacity(3).setUnlocalizedName("water").disableStats());
        registerBlock(9, "water", new BlockStaticLiquid(Material.water).setHardness(100.0f).setLightOpacity(3).setUnlocalizedName("water").disableStats());
        registerBlock(10, "flowing_lava", new BlockDynamicLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setUnlocalizedName("lava").disableStats());
        registerBlock(11, "lava", new BlockStaticLiquid(Material.lava).setHardness(100.0f).setLightLevel(1.0f).setUnlocalizedName("lava").disableStats());
        registerBlock(12, "sand", new BlockSand().setHardness(0.5f).setStepSound(Block.soundTypeSand).setUnlocalizedName("sand"));
        registerBlock(13, "gravel", new BlockGravel().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setUnlocalizedName("gravel"));
        registerBlock(14, "gold_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreGold"));
        registerBlock(15, "iron_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreIron"));
        registerBlock(16, "coal_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreCoal"));
        registerBlock(17, "log", new BlockOldLog().setUnlocalizedName("log"));
        registerBlock(18, "leaves", new BlockOldLeaf().setUnlocalizedName("leaves"));
        registerBlock(19, "sponge", new BlockSponge().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("sponge"));
        registerBlock(20, "glass", new BlockGlass(Material.glass, false).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName("glass"));
        registerBlock(21, "lapis_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreLapis"));
        registerBlock(22, "lapis_block", new BlockCompressed(MapColor.lapisColor).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(23, "dispenser", new BlockDispenser().setHardness(3.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName("dispenser"));
        final Block setUnlocalizedName2 = new BlockSandStone().setStepSound(Block.soundTypePiston).setHardness(0.8f).setUnlocalizedName("sandStone");
        registerBlock(24, "sandstone", setUnlocalizedName2);
        registerBlock(25, "noteblock", new BlockNote().setHardness(0.8f).setUnlocalizedName("musicBlock"));
        registerBlock(26, "bed", new BlockBed().setStepSound(Block.soundTypeWood).setHardness(0.2f).setUnlocalizedName("bed").disableStats());
        registerBlock(27, "golden_rail", new BlockRailPowered().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("goldenRail"));
        registerBlock(28, "detector_rail", new BlockRailDetector().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("detectorRail"));
        registerBlock(29, "sticky_piston", new BlockPistonBase(true).setUnlocalizedName("pistonStickyBase"));
        registerBlock(30, "web", new BlockWeb().setLightOpacity(1).setHardness(4.0f).setUnlocalizedName("web"));
        registerBlock(31, "tallgrass", new BlockTallGrass().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("tallgrass"));
        registerBlock(32, "deadbush", new BlockDeadBush().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("deadbush"));
        registerBlock(33, "piston", new BlockPistonBase(false).setUnlocalizedName("pistonBase"));
        registerBlock(34, "piston_head", new BlockPistonExtension());
        registerBlock(35, "wool", new BlockColored(Material.cloth).setHardness(0.8f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("cloth"));
        registerBlock(36, "piston_extension", new BlockPistonMoving());
        registerBlock(37, "yellow_flower", new BlockYellowFlower().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("flower1"));
        registerBlock(38, "red_flower", new BlockRedFlower().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("flower2"));
        final Block setUnlocalizedName3 = new BlockMushroom().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setLightLevel(0.125f).setUnlocalizedName("mushroom");
        registerBlock(39, "brown_mushroom", setUnlocalizedName3);
        final Block setUnlocalizedName4 = new BlockMushroom().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("mushroom");
        registerBlock(40, "red_mushroom", setUnlocalizedName4);
        registerBlock(41, "gold_block", new BlockCompressed(MapColor.goldColor).setHardness(3.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockGold"));
        registerBlock(42, "iron_block", new BlockCompressed(MapColor.ironColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockIron"));
        registerBlock(43, "double_stone_slab", new BlockDoubleStoneSlab().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stoneSlab"));
        registerBlock(44, "stone_slab", new BlockHalfStoneSlab().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stoneSlab"));
        final Block setCreativeTab2 = new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(45, "brick_block", setCreativeTab2);
        registerBlock(46, "tnt", new BlockTNT().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("tnt"));
        registerBlock(47, "bookshelf", new BlockBookshelf().setHardness(1.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("bookshelf"));
        registerBlock(48, "mossy_cobblestone", new Block(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(49, "obsidian", new BlockObsidian().setHardness(50.0f).setResistance(2000.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("obsidian"));
        registerBlock(50, "torch", new BlockTorch().setHardness(0.0f).setLightLevel(0.9375f).setStepSound(Block.soundTypeWood).setUnlocalizedName("torch"));
        registerBlock(51, "fire", new BlockFire().setHardness(0.0f).setLightLevel(1.0f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("fire").disableStats());
        registerBlock(52, "mob_spawner", new BlockMobSpawner().setHardness(5.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("mobSpawner").disableStats());
        registerBlock(53, "oak_stairs", new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.OAK)).setUnlocalizedName("stairsWood"));
        registerBlock(54, "chest", new BlockChest(0).setHardness(2.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("chest"));
        registerBlock(55, "redstone_wire", new BlockRedstoneWire().setHardness(0.0f).setStepSound(Block.soundTypeStone).setUnlocalizedName("redstoneDust").disableStats());
        registerBlock(56, "diamond_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreDiamond"));
        registerBlock(57, "diamond_block", new BlockCompressed(MapColor.diamondColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockDiamond"));
        registerBlock(58, "crafting_table", new BlockWorkbench().setHardness(2.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("workbench"));
        registerBlock(59, "wheat", new BlockCrops().setUnlocalizedName("crops"));
        final Block setUnlocalizedName5 = new BlockFarmland().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setUnlocalizedName("farmland");
        registerBlock(60, "farmland", setUnlocalizedName5);
        registerBlock(61, "furnace", new BlockFurnace(false).setHardness(3.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(62, "lit_furnace", new BlockFurnace(true).setHardness(3.5f).setStepSound(Block.soundTypePiston).setLightLevel(0.875f).setUnlocalizedName("furnace"));
        registerBlock(63, "standing_sign", new BlockStandingSign().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("sign").disableStats());
        registerBlock(64, "wooden_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("doorOak").disableStats());
        registerBlock(65, "ladder", new BlockLadder().setHardness(0.4f).setStepSound(Block.soundTypeLadder).setUnlocalizedName("ladder"));
        registerBlock(66, "rail", new BlockRail().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("rail"));
        registerBlock(67, "stone_stairs", new BlockStairs(setCreativeTab.getDefaultState()).setUnlocalizedName("stairsStone"));
        registerBlock(68, "wall_sign", new BlockWallSign().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("sign").disableStats());
        registerBlock(69, "lever", new BlockLever().setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("lever"));
        registerBlock(70, "stone_pressure_plate", new BlockPressurePlate(Material.rock, BlockPressurePlate.Sensitivity.MOBS).setHardness(0.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName("pressurePlateStone"));
        registerBlock(71, "iron_door", new BlockDoor(Material.iron).setHardness(5.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("doorIron").disableStats());
        registerBlock(72, "wooden_pressure_plate", new BlockPressurePlate(Material.wood, BlockPressurePlate.Sensitivity.EVERYTHING).setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("pressurePlateWood"));
        registerBlock(73, "redstone_ore", new BlockRedstoneOre(false).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(74, "lit_redstone_ore", new BlockRedstoneOre(true).setLightLevel(0.625f).setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreRedstone"));
        registerBlock(75, "unlit_redstone_torch", new BlockRedstoneTorch(false).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("notGate"));
        registerBlock(76, "redstone_torch", new BlockRedstoneTorch(true).setHardness(0.0f).setLightLevel(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(77, "stone_button", new BlockButtonStone().setHardness(0.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName("button"));
        registerBlock(78, "snow_layer", new BlockSnow().setHardness(0.1f).setStepSound(Block.soundTypeSnow).setUnlocalizedName("snow").setLightOpacity(0));
        registerBlock(79, "ice", new BlockIce().setHardness(0.5f).setLightOpacity(3).setStepSound(Block.soundTypeGlass).setUnlocalizedName("ice"));
        registerBlock(80, "snow", new BlockSnowBlock().setHardness(0.2f).setStepSound(Block.soundTypeSnow).setUnlocalizedName("snow"));
        registerBlock(81, "cactus", new BlockCactus().setHardness(0.4f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("cactus"));
        registerBlock(82, "clay", new BlockClay().setHardness(0.6f).setStepSound(Block.soundTypeGravel).setUnlocalizedName("clay"));
        registerBlock(83, "reeds", new BlockReed().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("reeds").disableStats());
        registerBlock(84, "jukebox", new BlockJukebox().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("jukebox"));
        registerBlock(85, "fence", new BlockFence(Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("fence"));
        final Block setUnlocalizedName6 = new BlockPumpkin().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("pumpkin");
        registerBlock(86, "pumpkin", setUnlocalizedName6);
        registerBlock(87, "netherrack", new BlockNetherrack().setHardness(0.4f).setStepSound(Block.soundTypePiston).setUnlocalizedName("hellrock"));
        registerBlock(88, "soul_sand", new BlockSoulSand().setHardness(0.5f).setStepSound(Block.soundTypeSand).setUnlocalizedName("hellsand"));
        registerBlock(89, "glowstone", new BlockGlowstone(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setLightLevel(1.0f).setUnlocalizedName("lightgem"));
        registerBlock(90, "portal", new BlockPortal().setHardness(-1.0f).setStepSound(Block.soundTypeGlass).setLightLevel(0.75f).setUnlocalizedName("portal"));
        registerBlock(91, "lit_pumpkin", new BlockPumpkin().setHardness(1.0f).setStepSound(Block.soundTypeWood).setLightLevel(1.0f).setUnlocalizedName("litpumpkin"));
        registerBlock(92, "cake", new BlockCake().setHardness(0.5f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("cake").disableStats());
        registerBlock(93, "unpowered_repeater", new BlockRedstoneRepeater(false).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("diode").disableStats());
        registerBlock(94, "powered_repeater", new BlockRedstoneRepeater(true).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("diode").disableStats());
        registerBlock(95, "stained_glass", new BlockStainedGlass(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName("stainedGlass"));
        registerBlock(96, "trapdoor", new BlockTrapDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("trapdoor").disableStats());
        registerBlock(97, "monster_egg", new BlockSilverfish().setHardness(0.75f).setUnlocalizedName("monsterStoneEgg"));
        final Block setUnlocalizedName7 = new BlockStoneBrick().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stonebricksmooth");
        registerBlock(98, "stonebrick", setUnlocalizedName7);
        registerBlock(99, "brown_mushroom_block", new BlockHugeMushroom(Material.wood, setUnlocalizedName3).setHardness(0.2f).setStepSound(Block.soundTypeWood).setUnlocalizedName("mushroom"));
        registerBlock(100, "red_mushroom_block", new BlockHugeMushroom(Material.wood, setUnlocalizedName4).setHardness(0.2f).setStepSound(Block.soundTypeWood).setUnlocalizedName("mushroom"));
        registerBlock(101, "iron_bars", new BlockPane(Material.iron, true).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("fenceIron"));
        registerBlock(102, "glass_pane", new BlockPane(Material.glass, false).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName("thinGlass"));
        final Block setUnlocalizedName8 = new BlockMelon().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("melon");
        registerBlock(103, "melon_block", setUnlocalizedName8);
        registerBlock(104, "pumpkin_stem", new BlockStem(setUnlocalizedName6).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("pumpkinStem"));
        registerBlock(105, "melon_stem", new BlockStem(setUnlocalizedName8).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("pumpkinStem"));
        registerBlock(106, "vine", new BlockVine().setHardness(0.2f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("vine"));
        registerBlock(107, "fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("fenceGate"));
        registerBlock(108, "brick_stairs", new BlockStairs(setCreativeTab2.getDefaultState()).setUnlocalizedName("stairsBrick"));
        registerBlock(109, "stone_brick_stairs", new BlockStairs(setUnlocalizedName7.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.DEFAULT)).setUnlocalizedName("stairsStoneBrickSmooth"));
        registerBlock(110, "mycelium", new BlockMycelium().setHardness(0.6f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("mycel"));
        registerBlock(111, "waterlily", new BlockLilyPad().setHardness(0.0f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("waterlily"));
        final Block setCreativeTab3 = new BlockNetherBrick().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(112, "nether_brick", setCreativeTab3);
        registerBlock(113, "nether_brick_fence", new BlockFence(Material.rock).setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("netherFence"));
        registerBlock(114, "nether_brick_stairs", new BlockStairs(setCreativeTab3.getDefaultState()).setUnlocalizedName("stairsNetherBrick"));
        registerBlock(115, "nether_wart", new BlockNetherWart().setUnlocalizedName("netherStalk"));
        registerBlock(116, "enchanting_table", new BlockEnchantmentTable().setHardness(5.0f).setResistance(2000.0f).setUnlocalizedName("enchantmentTable"));
        registerBlock(117, "brewing_stand", new BlockBrewingStand().setHardness(0.5f).setLightLevel(0.125f).setUnlocalizedName("brewingStand"));
        registerBlock(118, "cauldron", new BlockCauldron().setHardness(2.0f).setUnlocalizedName("cauldron"));
        registerBlock(119, "end_portal", new BlockEndPortal(Material.portal).setHardness(-1.0f).setResistance(6000000.0f));
        registerBlock(120, "end_portal_frame", new BlockEndPortalFrame().setStepSound(Block.soundTypeGlass).setLightLevel(0.125f).setHardness(-1.0f).setUnlocalizedName("endPortalFrame").setResistance(6000000.0f).setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(121, "end_stone", new Block(Material.rock).setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(122, "dragon_egg", new BlockDragonEgg().setHardness(3.0f).setResistance(15.0f).setStepSound(Block.soundTypePiston).setLightLevel(0.125f).setUnlocalizedName("dragonEgg"));
        registerBlock(123, "redstone_lamp", new BlockRedstoneLight(false).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(124, "lit_redstone_lamp", new BlockRedstoneLight(true).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName("redstoneLight"));
        registerBlock(125, "double_wooden_slab", new BlockDoubleWoodSlab().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("woodSlab"));
        registerBlock(126, "wooden_slab", new BlockHalfWoodSlab().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("woodSlab"));
        registerBlock(127, "cocoa", new BlockCocoa().setHardness(0.2f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("cocoa"));
        registerBlock(128, "sandstone_stairs", new BlockStairs(setUnlocalizedName2.getDefaultState().withProperty(BlockSandStone.field_176297_a, BlockSandStone.EnumType.SMOOTH)).setUnlocalizedName("stairsSandStone"));
        registerBlock(129, "emerald_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("oreEmerald"));
        registerBlock(130, "ender_chest", new BlockEnderChest().setHardness(22.5f).setResistance(1000.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("enderChest").setLightLevel(0.5f));
        registerBlock(131, "tripwire_hook", new BlockTripWireHook().setUnlocalizedName("tripWireSource"));
        registerBlock(132, "tripwire", new BlockTripWire().setUnlocalizedName("tripWire"));
        registerBlock(133, "emerald_block", new BlockCompressed(MapColor.emeraldColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockEmerald"));
        registerBlock(134, "spruce_stairs", new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.SPRUCE)).setUnlocalizedName("stairsWoodSpruce"));
        registerBlock(135, "birch_stairs", new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.BIRCH)).setUnlocalizedName("stairsWoodBirch"));
        registerBlock(136, "jungle_stairs", new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.JUNGLE)).setUnlocalizedName("stairsWoodJungle"));
        registerBlock(137, "command_block", new BlockCommandBlock().setBlockUnbreakable().setResistance(6000000.0f).setUnlocalizedName("commandBlock"));
        registerBlock(138, "beacon", new BlockBeacon().setUnlocalizedName("beacon").setLightLevel(1.0f));
        registerBlock(139, "cobblestone_wall", new BlockWall(setCreativeTab).setUnlocalizedName("cobbleWall"));
        registerBlock(140, "flower_pot", new BlockFlowerPot().setHardness(0.0f).setStepSound(Block.soundTypeStone).setUnlocalizedName("flowerPot"));
        registerBlock(141, "carrots", new BlockCarrot().setUnlocalizedName("carrots"));
        registerBlock(142, "potatoes", new BlockPotato().setUnlocalizedName("potatoes"));
        registerBlock(143, "wooden_button", new BlockButtonWood().setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("button"));
        registerBlock(144, "skull", new BlockSkull().setHardness(1.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("skull"));
        registerBlock(145, "anvil", new BlockAnvil().setHardness(5.0f).setStepSound(Block.soundTypeAnvil).setResistance(2000.0f).setUnlocalizedName("anvil"));
        registerBlock(146, "trapped_chest", new BlockChest(1).setHardness(2.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("chestTrap"));
        registerBlock(147, "light_weighted_pressure_plate", new BlockPressurePlateWeighted("gold_block", Material.iron, 15).setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("weightedPlate_light"));
        registerBlock(148, "heavy_weighted_pressure_plate", new BlockPressurePlateWeighted("iron_block", Material.iron, 150).setHardness(0.5f).setStepSound(Block.soundTypeWood).setUnlocalizedName("weightedPlate_heavy"));
        registerBlock(149, "unpowered_comparator", new BlockRedstoneComparator(false).setHardness(0.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("comparator").disableStats());
        registerBlock(150, "powered_comparator", new BlockRedstoneComparator(true).setHardness(0.0f).setLightLevel(0.625f).setStepSound(Block.soundTypeWood).setUnlocalizedName("comparator").disableStats());
        registerBlock(151, "daylight_detector", new BlockDaylightDetector(false));
        registerBlock(152, "redstone_block", new BlockCompressedPowered(MapColor.tntColor).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockRedstone"));
        registerBlock(153, "quartz_ore", new BlockOre().setHardness(3.0f).setResistance(5.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("netherquartz"));
        registerBlock(154, "hopper", new BlockHopper().setHardness(3.0f).setResistance(8.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("hopper"));
        final Block setUnlocalizedName9 = new BlockQuartz().setStepSound(Block.soundTypePiston).setHardness(0.8f).setUnlocalizedName("quartzBlock");
        registerBlock(155, "quartz_block", setUnlocalizedName9);
        registerBlock(156, "quartz_stairs", new BlockStairs(setUnlocalizedName9.getDefaultState().withProperty(BlockQuartz.VARIANT_PROP, BlockQuartz.EnumType.DEFAULT)).setUnlocalizedName("stairsQuartz"));
        registerBlock(157, "activator_rail", new BlockRailPowered().setHardness(0.7f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("activatorRail"));
        registerBlock(158, "dropper", new BlockDropper().setHardness(3.5f).setStepSound(Block.soundTypePiston).setUnlocalizedName("dropper"));
        registerBlock(159, "stained_hardened_clay", new BlockColored(Material.rock).setHardness(1.25f).setResistance(7.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("clayHardenedStained"));
        registerBlock(160, "stained_glass_pane", new BlockStainedGlassPane().setHardness(0.3f).setStepSound(Block.soundTypeGlass).setUnlocalizedName("thinStainedGlass"));
        registerBlock(161, "leaves2", new BlockNewLeaf().setUnlocalizedName("leaves"));
        registerBlock(162, "log2", new BlockNewLog().setUnlocalizedName("log"));
        registerBlock(163, "acacia_stairs", new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.ACACIA)).setUnlocalizedName("stairsWoodAcacia"));
        registerBlock(164, "dark_oak_stairs", new BlockStairs(setUnlocalizedName.getDefaultState().withProperty(BlockPlanks.VARIANT_PROP, BlockPlanks.EnumType.DARK_OAK)).setUnlocalizedName("stairsWoodDarkOak"));
        registerBlock(165, "slime", new BlockSlime().setUnlocalizedName("slime").setStepSound(Block.SLIME_SOUND));
        registerBlock(166, "barrier", new BlockBarrier().setUnlocalizedName("barrier"));
        registerBlock(167, "iron_trapdoor", new BlockTrapDoor(Material.iron).setHardness(5.0f).setStepSound(Block.soundTypeMetal).setUnlocalizedName("ironTrapdoor").disableStats());
        registerBlock(168, "prismarine", new BlockPrismarine().setHardness(1.5f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("prismarine"));
        registerBlock(169, "sea_lantern", new BlockSeaLantern(Material.glass).setHardness(0.3f).setStepSound(Block.soundTypeGlass).setLightLevel(1.0f).setUnlocalizedName("seaLantern"));
        registerBlock(170, "hay_block", new BlockHay().setHardness(0.5f).setStepSound(Block.soundTypeGrass).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(171, "carpet", new BlockCarpet().setHardness(0.1f).setStepSound(Block.soundTypeCloth).setUnlocalizedName("woolCarpet").setLightOpacity(0));
        registerBlock(172, "hardened_clay", new BlockHardenedClay().setHardness(1.25f).setResistance(7.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("clayHardened"));
        registerBlock(173, "coal_block", new Block(Material.rock).setHardness(5.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("blockCoal").setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(174, "packed_ice", new BlockPackedIce().setHardness(0.5f).setStepSound(Block.soundTypeGlass).setUnlocalizedName("icePacked"));
        registerBlock(175, "double_plant", new BlockDoublePlant());
        registerBlock(176, "standing_banner", new BlockBanner.BlockBannerStanding().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("banner").disableStats());
        registerBlock(177, "wall_banner", new BlockBanner.BlockBannerHanging().setHardness(1.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("banner").disableStats());
        registerBlock(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
        final Block setUnlocalizedName10 = new BlockRedSandstone().setStepSound(Block.soundTypePiston).setHardness(0.8f).setUnlocalizedName("redSandStone");
        registerBlock(179, "red_sandstone", setUnlocalizedName10);
        registerBlock(180, "red_sandstone_stairs", new BlockStairs(setUnlocalizedName10.getDefaultState().withProperty(BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH)).setUnlocalizedName("stairsRedSandStone"));
        registerBlock(181, "double_stone_slab2", new BlockDoubleStoneSlabNew().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stoneSlab2"));
        registerBlock(182, "stone_slab2", new BlockHalfStoneSlabNew().setHardness(2.0f).setResistance(10.0f).setStepSound(Block.soundTypePiston).setUnlocalizedName("stoneSlab2"));
        registerBlock(183, "spruce_fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("spruceFenceGate"));
        registerBlock(184, "birch_fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("birchFenceGate"));
        registerBlock(185, "jungle_fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("jungleFenceGate"));
        registerBlock(186, "dark_oak_fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("darkOakFenceGate"));
        registerBlock(187, "acacia_fence_gate", new BlockFenceGate().setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("acaciaFenceGate"));
        registerBlock(188, "spruce_fence", new BlockFence(Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("spruceFence"));
        registerBlock(189, "birch_fence", new BlockFence(Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("birchFence"));
        registerBlock(190, "jungle_fence", new BlockFence(Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("jungleFence"));
        registerBlock(191, "dark_oak_fence", new BlockFence(Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("darkOakFence"));
        registerBlock(192, "acacia_fence", new BlockFence(Material.wood).setHardness(2.0f).setResistance(5.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("acaciaFence"));
        registerBlock(193, "spruce_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("doorSpruce").disableStats());
        registerBlock(194, "birch_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("doorBirch").disableStats());
        registerBlock(195, "jungle_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("doorJungle").disableStats());
        registerBlock(196, "acacia_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("doorAcacia").disableStats());
        registerBlock(197, "dark_oak_door", new BlockDoor(Material.wood).setHardness(3.0f).setStepSound(Block.soundTypeWood).setUnlocalizedName("doorDarkOak").disableStats());
        Block.blockRegistry.validateKey();
        for (final Block block : Block.blockRegistry) {
            if (block.blockMaterial == Material.air) {
                block.useNeighborBrightness = false;
            }
            else {
                final boolean b = block instanceof BlockStairs;
                final boolean b2 = block instanceof BlockSlab;
                final boolean b3 = block == setUnlocalizedName5;
                final boolean translucent = block.translucent;
                final boolean b4 = block.lightOpacity == 0;
                if (b || b2 || b3 || translucent || b4) {}
                block.useNeighborBrightness = true;
            }
        }
        for (final Block block2 : Block.blockRegistry) {
            for (final IBlockState blockState : block2.getBlockState().getValidStates()) {
                Block.BLOCK_STATE_IDS.put(blockState, Block.blockRegistry.getIDForObject(block2) << 4 | block2.getMetaFromState(blockState));
            }
        }
    }
    
    private static void registerBlock(final int n, final ResourceLocation resourceLocation, final Block block) {
        Block.blockRegistry.register(n, resourceLocation, block);
    }
    
    private static void registerBlock(final int n, final String s, final Block block) {
        registerBlock(n, new ResourceLocation(s), block);
    }
    
    public enum EnumOffsetType
    {
        NONE("NONE", 0, "NONE", 0), 
        XZ("XZ", 1, "XZ", 1), 
        XYZ("XYZ", 2, "XYZ", 2);
        
        private static final EnumOffsetType[] $VALUES;
        private static final String __OBFID;
        private static final EnumOffsetType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002132";
            ENUM$VALUES = new EnumOffsetType[] { EnumOffsetType.NONE, EnumOffsetType.XZ, EnumOffsetType.XYZ };
            $VALUES = new EnumOffsetType[] { EnumOffsetType.NONE, EnumOffsetType.XZ, EnumOffsetType.XYZ };
        }
        
        private EnumOffsetType(final String s, final int n, final String s2, final int n2) {
        }
    }
    
    public static class SoundType
    {
        public final String soundName;
        public final float volume;
        public final float frequency;
        private static final String __OBFID;
        
        public SoundType(final String soundName, final float volume, final float frequency) {
            this.soundName = soundName;
            this.volume = volume;
            this.frequency = frequency;
        }
        
        public float getVolume() {
            return this.volume;
        }
        
        public float getFrequency() {
            return this.frequency;
        }
        
        public String getBreakSound() {
            return "dig." + this.soundName;
        }
        
        public String getStepSound() {
            return "step." + this.soundName;
        }
        
        public String getPlaceSound() {
            return this.getBreakSound();
        }
        
        static {
            __OBFID = "CL_00000203";
        }
    }
}
