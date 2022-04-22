package net.minecraft.entity.item;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.server.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.nio.charset.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.*;
import java.util.*;

public abstract class EntityMinecart extends Entity implements IWorldNameable
{
    private boolean isInReverse;
    private String entityName;
    private static final int[][][] matrix;
    private int turnProgress;
    private double minecartX;
    private double minecartY;
    private double minecartZ;
    private double minecartYaw;
    private double minecartPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001670";
        matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
    }
    
    public EntityMinecart(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(0.98f, 0.7f);
    }
    
    public static EntityMinecart func_180458_a(final World world, final double n, final double n2, final double n3, final EnumMinecartType enumMinecartType) {
        switch (SwitchEnumMinecartType.field_180037_a[enumMinecartType.ordinal()]) {
            case 1: {
                return new EntityMinecartChest(world, n, n2, n3);
            }
            case 2: {
                return new EntityMinecartFurnace(world, n, n2, n3);
            }
            case 3: {
                return new EntityMinecartTNT(world, n, n2, n3);
            }
            case 4: {
                return new EntityMinecartMobSpawner(world, n, n2, n3);
            }
            case 5: {
                return new EntityMinecartHopper(world, n, n2, n3);
            }
            case 6: {
                return new EntityMinecartCommandBlock(world, n, n2, n3);
            }
            default: {
                return new EntityMinecartEmpty(world, n, n2, n3);
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0f));
        this.dataWatcher.addObject(20, new Integer(0));
        this.dataWatcher.addObject(21, new Integer(6));
        this.dataWatcher.addObject(22, 0);
    }
    
    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.canBePushed() ? entity.getEntityBoundingBox() : null;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }
    
    @Override
    public boolean canBePushed() {
        return true;
    }
    
    public EntityMinecart(final World world, final double prevPosX, final double prevPosY, final double prevPosZ) {
        this(world);
        this.setPosition(prevPosX, prevPosY, prevPosZ);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
    }
    
    @Override
    public double getMountedYOffset() {
        return this.height * 0.5 - 0.20000000298023224;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.worldObj.isRemote || this.isDead) {
            return true;
        }
        if (this.func_180431_b(damageSource)) {
            return false;
        }
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setBeenAttacked();
        this.setDamage(this.getDamage() + n * 10.0f);
        final boolean b = damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damageSource.getEntity()).capabilities.isCreativeMode;
        if (b || this.getDamage() > 40.0f) {
            if (this.riddenByEntity != null) {
                this.riddenByEntity.mountEntity(null);
            }
            if (b && !this.hasCustomName()) {
                this.setDead();
            }
            else {
                this.killMinecart(damageSource);
            }
        }
        return true;
    }
    
    public void killMinecart(final DamageSource damageSource) {
        this.setDead();
        final ItemStack itemStack = new ItemStack(Items.minecart, 1);
        if (this.entityName != null) {
            itemStack.setStackDisplayName(this.entityName);
        }
        this.entityDropItem(itemStack, 0.0f);
    }
    
    @Override
    public void performHurtAnimation() {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0f);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }
    
    @Override
    public void setDead() {
        super.setDead();
    }
    
    @Override
    public void onUpdate() {
        if (this.getRollingAmplitude() > 0) {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }
        if (this.getDamage() > 0.0f) {
            this.setDamage(this.getDamage() - 1.0f);
        }
        if (this.posY < -64.0) {
            this.kill();
        }
        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
            this.worldObj.theProfiler.startSection("portal");
            final MinecraftServer func_73046_m = ((WorldServer)this.worldObj).func_73046_m();
            final int maxInPortalTime = this.getMaxInPortalTime();
            if (this.inPortal) {
                if (func_73046_m.getAllowNether()) {
                    if (this.ridingEntity == null && this.portalCounter++ >= maxInPortalTime) {
                        this.portalCounter = maxInPortalTime;
                        this.timeUntilPortal = this.getPortalCooldown();
                        if (this.worldObj.provider.getDimensionId() == -1) {}
                        this.travelToDimension(-1);
                    }
                    this.inPortal = false;
                }
            }
            else {
                if (this.portalCounter > 0) {
                    this.portalCounter -= 4;
                }
                if (this.portalCounter < 0) {
                    this.portalCounter = 0;
                }
            }
            if (this.timeUntilPortal > 0) {
                --this.timeUntilPortal;
            }
            this.worldObj.theProfiler.endSection();
        }
        if (this.worldObj.isRemote) {
            if (this.turnProgress > 0) {
                final double n = this.posX + (this.minecartX - this.posX) / this.turnProgress;
                final double n2 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
                final double n3 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
                this.rotationYaw += (float)(MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw) / this.turnProgress);
                this.rotationPitch += (float)((this.minecartPitch - this.rotationPitch) / this.turnProgress);
                --this.turnProgress;
                this.setPosition(n, n2, n3);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            final int floor_double = MathHelper.floor_double(this.posX);
            int floor_double2 = MathHelper.floor_double(this.posY);
            MathHelper.floor_double(this.posZ);
            if (BlockRailBase.func_176562_d(this.worldObj, new BlockPos(floor_double, floor_double2 - 1, -1))) {
                --floor_double2;
            }
            final BlockPos blockPos = new BlockPos(floor_double, floor_double2, -1);
            final IBlockState blockState = this.worldObj.getBlockState(blockPos);
            if (BlockRailBase.func_176563_d(blockState)) {
                this.func_180460_a(blockPos, blockState);
                if (blockState.getBlock() == Blocks.activator_rail) {
                    this.onActivatorRailPass(floor_double, floor_double2, -1, (boolean)blockState.getValue(BlockRailPowered.field_176569_M));
                }
            }
            else {
                this.func_180459_n();
            }
            this.doBlockCollisions();
            this.rotationPitch = 0.0f;
            final double n4 = this.prevPosX - this.posX;
            final double n5 = this.prevPosZ - this.posZ;
            if (n4 * n4 + n5 * n5 > 0.001) {
                this.rotationYaw = (float)(Math.atan2(n5, n4) * 180.0 / 3.141592653589793);
                if (this.isInReverse) {
                    this.rotationYaw += 180.0f;
                }
            }
            final double n6 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
            if (n6 < -170.0 || n6 >= 170.0) {
                this.rotationYaw += 180.0f;
                this.isInReverse = !this.isInReverse;
            }
            this.setRotation(this.rotationYaw, this.rotationPitch);
            for (final Entity entity : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224, 0.0, 0.20000000298023224))) {
                if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityMinecart) {
                    entity.applyEntityCollision(this);
                }
            }
            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                if (this.riddenByEntity.ridingEntity == this) {
                    this.riddenByEntity.ridingEntity = null;
                }
                this.riddenByEntity = null;
            }
            this.handleWaterMovement();
        }
    }
    
    protected double func_174898_m() {
        return 0.4;
    }
    
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
    }
    
    protected void func_180459_n() {
        final double func_174898_m = this.func_174898_m();
        this.motionX = MathHelper.clamp_double(this.motionX, -func_174898_m, func_174898_m);
        this.motionZ = MathHelper.clamp_double(this.motionZ, -func_174898_m, func_174898_m);
        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (!this.onGround) {
            this.motionX *= 0.949999988079071;
            this.motionY *= 0.949999988079071;
            this.motionZ *= 0.949999988079071;
        }
    }
    
    protected void func_180460_a(final BlockPos blockPos, final IBlockState blockState) {
        this.fallDistance = 0.0f;
        final Vec3 func_70489_a = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = blockPos.getY();
        final BlockRailBase blockRailBase = (BlockRailBase)blockState.getBlock();
        if (blockRailBase == Blocks.golden_rail) {
            (boolean)blockState.getValue(BlockRailPowered.field_176569_M);
            final boolean b = !false;
        }
        final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)blockState.getValue(blockRailBase.func_176560_l());
        switch (SwitchEnumMinecartType.field_180036_b[enumRailDirection.ordinal()]) {
            case 1: {
                this.motionX -= 0.0078125;
                ++this.posY;
                break;
            }
            case 2: {
                this.motionX += 0.0078125;
                ++this.posY;
                break;
            }
            case 3: {
                this.motionZ += 0.0078125;
                ++this.posY;
                break;
            }
            case 4: {
                this.motionZ -= 0.0078125;
                ++this.posY;
                break;
            }
        }
        final int[][] array = EntityMinecart.matrix[enumRailDirection.func_177015_a()];
        double n = array[1][0] - array[0][0];
        double n2 = array[1][2] - array[0][2];
        final double sqrt = Math.sqrt(n * n + n2 * n2);
        if (this.motionX * n + this.motionZ * n2 < 0.0) {
            n = -n;
            n2 = -n2;
        }
        double sqrt2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (sqrt2 > 2.0) {
            sqrt2 = 2.0;
        }
        this.motionX = sqrt2 * n / sqrt;
        this.motionZ = sqrt2 * n2 / sqrt;
        if (this.riddenByEntity instanceof EntityLivingBase && ((EntityLivingBase)this.riddenByEntity).moveForward > 0.0) {
            final double n3 = -Math.sin(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            final double cos = Math.cos(this.riddenByEntity.rotationYaw * 3.1415927f / 180.0f);
            if (this.motionX * this.motionX + this.motionZ * this.motionZ < 0.01) {
                this.motionX += n3 * 0.1;
                this.motionZ += cos * 0.1;
            }
        }
        if (false) {
            if (Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) < 0.03) {
                this.motionX *= 0.0;
                this.motionY *= 0.0;
                this.motionZ *= 0.0;
            }
            else {
                this.motionX *= 0.5;
                this.motionY *= 0.0;
                this.motionZ *= 0.5;
            }
        }
        final double n4 = blockPos.getX() + 0.5 + array[0][0] * 0.5;
        final double n5 = blockPos.getZ() + 0.5 + array[0][2] * 0.5;
        final double n6 = blockPos.getX() + 0.5 + array[1][0] * 0.5;
        final double n7 = blockPos.getZ() + 0.5 + array[1][2] * 0.5;
        final double n8 = n6 - n4;
        final double n9 = n7 - n5;
        double n10;
        if (n8 == 0.0) {
            this.posX = blockPos.getX() + 0.5;
            n10 = this.posZ - blockPos.getZ();
        }
        else if (n9 == 0.0) {
            this.posZ = blockPos.getZ() + 0.5;
            n10 = this.posX - blockPos.getX();
        }
        else {
            n10 = ((this.posX - n4) * n8 + (this.posZ - n5) * n9) * 2.0;
        }
        this.posX = n4 + n8 * n10;
        this.posZ = n5 + n9 * n10;
        this.setPosition(this.posX, this.posY, this.posZ);
        double motionX = this.motionX;
        double motionZ = this.motionZ;
        if (this.riddenByEntity != null) {
            motionX *= 0.75;
            motionZ *= 0.75;
        }
        final double func_174898_m = this.func_174898_m();
        this.moveEntity(MathHelper.clamp_double(motionX, -func_174898_m, func_174898_m), 0.0, MathHelper.clamp_double(motionZ, -func_174898_m, func_174898_m));
        if (array[0][1] != 0 && MathHelper.floor_double(this.posX) - blockPos.getX() == array[0][0] && MathHelper.floor_double(this.posZ) - blockPos.getZ() == array[0][2]) {
            this.setPosition(this.posX, this.posY + array[0][1], this.posZ);
        }
        else if (array[1][1] != 0 && MathHelper.floor_double(this.posX) - blockPos.getX() == array[1][0] && MathHelper.floor_double(this.posZ) - blockPos.getZ() == array[1][2]) {
            this.setPosition(this.posX, this.posY + array[1][1], this.posZ);
        }
        this.applyDrag();
        final Vec3 func_70489_a2 = this.func_70489_a(this.posX, this.posY, this.posZ);
        if (func_70489_a2 != null && func_70489_a != null) {
            final double n11 = (func_70489_a.yCoord - func_70489_a2.yCoord) * 0.05;
            final double sqrt3 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (sqrt3 > 0.0) {
                this.motionX = this.motionX / sqrt3 * (sqrt3 + n11);
                this.motionZ = this.motionZ / sqrt3 * (sqrt3 + n11);
            }
            this.setPosition(this.posX, func_70489_a2.yCoord, this.posZ);
        }
        final int floor_double = MathHelper.floor_double(this.posX);
        final int floor_double2 = MathHelper.floor_double(this.posZ);
        if (floor_double != blockPos.getX() || floor_double2 != blockPos.getZ()) {
            final double sqrt4 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = sqrt4 * (floor_double - blockPos.getX());
            this.motionZ = sqrt4 * (floor_double2 - blockPos.getZ());
        }
        if (false) {
            final double sqrt5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (sqrt5 > 0.01) {
                final double n12 = 0.06;
                this.motionX += this.motionX / sqrt5 * n12;
                this.motionZ += this.motionZ / sqrt5 * n12;
            }
            else if (enumRailDirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
                if (this.worldObj.getBlockState(blockPos.offsetWest()).getBlock().isNormalCube()) {
                    this.motionX = 0.02;
                }
                else if (this.worldObj.getBlockState(blockPos.offsetEast()).getBlock().isNormalCube()) {
                    this.motionX = -0.02;
                }
            }
            else if (enumRailDirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
                if (this.worldObj.getBlockState(blockPos.offsetNorth()).getBlock().isNormalCube()) {
                    this.motionZ = 0.02;
                }
                else if (this.worldObj.getBlockState(blockPos.offsetSouth()).getBlock().isNormalCube()) {
                    this.motionZ = -0.02;
                }
            }
        }
    }
    
    protected void applyDrag() {
        if (this.riddenByEntity != null) {
            this.motionX *= 0.996999979019165;
            this.motionY *= 0.0;
            this.motionZ *= 0.996999979019165;
        }
        else {
            this.motionX *= 0.9599999785423279;
            this.motionY *= 0.0;
            this.motionZ *= 0.9599999785423279;
        }
    }
    
    @Override
    public void setPosition(final double posX, final double posY, final double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        final float n = this.width / 2.0f;
        this.func_174826_a(new AxisAlignedBB(posX - n, posY, posZ - n, posX + n, posY + this.height, posZ + n));
    }
    
    public Vec3 func_70495_a(double n, double n2, double n3, final double n4) {
        final int floor_double = MathHelper.floor_double(n);
        int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        if (BlockRailBase.func_176562_d(this.worldObj, new BlockPos(floor_double, floor_double2 - 1, floor_double3))) {
            --floor_double2;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3));
        if (BlockRailBase.func_176563_d(blockState)) {
            final BlockRailBase.EnumRailDirection enumRailDirection = (BlockRailBase.EnumRailDirection)blockState.getValue(((BlockRailBase)blockState.getBlock()).func_176560_l());
            n2 = floor_double2;
            if (enumRailDirection.func_177018_c()) {
                n2 = floor_double2 + 1;
            }
            final int[][] array = EntityMinecart.matrix[enumRailDirection.func_177015_a()];
            final double n5 = array[1][0] - array[0][0];
            final double n6 = array[1][2] - array[0][2];
            final double sqrt = Math.sqrt(n5 * n5 + n6 * n6);
            final double n7 = n5 / sqrt;
            final double n8 = n6 / sqrt;
            n += n7 * n4;
            n3 += n8 * n4;
            if (array[0][1] != 0 && MathHelper.floor_double(n) - floor_double == array[0][0] && MathHelper.floor_double(n3) - floor_double3 == array[0][2]) {
                n2 += array[0][1];
            }
            else if (array[1][1] != 0 && MathHelper.floor_double(n) - floor_double == array[1][0] && MathHelper.floor_double(n3) - floor_double3 == array[1][2]) {
                n2 += array[1][1];
            }
            return this.func_70489_a(n, n2, n3);
        }
        return null;
    }
    
    public Vec3 func_70489_a(double n, double n2, double n3) {
        final int floor_double = MathHelper.floor_double(n);
        int floor_double2 = MathHelper.floor_double(n2);
        final int floor_double3 = MathHelper.floor_double(n3);
        if (BlockRailBase.func_176562_d(this.worldObj, new BlockPos(floor_double, floor_double2 - 1, floor_double3))) {
            --floor_double2;
        }
        final IBlockState blockState = this.worldObj.getBlockState(new BlockPos(floor_double, floor_double2, floor_double3));
        if (BlockRailBase.func_176563_d(blockState)) {
            final int[][] array = EntityMinecart.matrix[((BlockRailBase.EnumRailDirection)blockState.getValue(((BlockRailBase)blockState.getBlock()).func_176560_l())).func_177015_a()];
            final double n4 = floor_double + 0.5 + array[0][0] * 0.5;
            final double n5 = floor_double2 + 0.0625 + array[0][1] * 0.5;
            final double n6 = floor_double3 + 0.5 + array[0][2] * 0.5;
            final double n7 = floor_double + 0.5 + array[1][0] * 0.5;
            final double n8 = floor_double2 + 0.0625 + array[1][1] * 0.5;
            final double n9 = floor_double3 + 0.5 + array[1][2] * 0.5;
            final double n10 = n7 - n4;
            final double n11 = (n8 - n5) * 2.0;
            final double n12 = n9 - n6;
            double n13;
            if (n10 == 0.0) {
                n = floor_double + 0.5;
                n13 = n3 - floor_double3;
            }
            else if (n12 == 0.0) {
                n3 = floor_double3 + 0.5;
                n13 = n - floor_double;
            }
            else {
                n13 = ((n - n4) * n10 + (n3 - n6) * n12) * 2.0;
            }
            n = n4 + n10 * n13;
            n2 = n5 + n11 * n13;
            n3 = n6 + n12 * n13;
            if (n11 < 0.0) {
                ++n2;
            }
            if (n11 > 0.0) {
                n2 += 0.5;
            }
            return new Vec3(n, n2, n3);
        }
        return null;
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.getBoolean("CustomDisplayTile")) {
            final int integer = nbtTagCompound.getInteger("DisplayData");
            if (nbtTagCompound.hasKey("DisplayTile", 8)) {
                final Block blockFromName = Block.getBlockFromName(nbtTagCompound.getString("DisplayTile"));
                if (blockFromName == null) {
                    this.func_174899_a(Blocks.air.getDefaultState());
                }
                else {
                    this.func_174899_a(blockFromName.getStateFromMeta(integer));
                }
            }
            else {
                final Block blockById = Block.getBlockById(nbtTagCompound.getInteger("DisplayTile"));
                if (blockById == null) {
                    this.func_174899_a(Blocks.air.getDefaultState());
                }
                else {
                    this.func_174899_a(blockById.getStateFromMeta(integer));
                }
            }
            this.setDisplayTileOffset(nbtTagCompound.getInteger("DisplayOffset"));
        }
        if (nbtTagCompound.hasKey("CustomName", 8) && nbtTagCompound.getString("CustomName").length() > 0) {
            this.entityName = nbtTagCompound.getString("CustomName");
        }
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.hasDisplayTile()) {
            nbtTagCompound.setBoolean("CustomDisplayTile", true);
            final IBlockState func_174897_t = this.func_174897_t();
            final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(func_174897_t.getBlock());
            nbtTagCompound.setString("DisplayTile", (resourceLocation == null) ? "" : resourceLocation.toString());
            nbtTagCompound.setInteger("DisplayData", func_174897_t.getBlock().getMetaFromState(func_174897_t));
            nbtTagCompound.setInteger("DisplayOffset", this.getDisplayTileOffset());
        }
        if (this.entityName != null && this.entityName.length() > 0) {
            nbtTagCompound.setString("CustomName", this.entityName);
        }
    }
    
    @Override
    public void applyEntityCollision(final Entity entity) {
        if (!this.worldObj.isRemote && !entity.noClip && !this.noClip && entity != this.riddenByEntity) {
            if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityIronGolem) && this.func_180456_s() == EnumMinecartType.RIDEABLE && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01 && this.riddenByEntity == null && entity.ridingEntity == null) {
                entity.mountEntity(this);
            }
            final double n = entity.posX - this.posX;
            final double n2 = entity.posZ - this.posZ;
            final double n3 = n * n + n2 * n2;
            if (n3 >= 9.999999747378752E-5) {
                final double n4 = MathHelper.sqrt_double(n3);
                final double n5 = n / n4;
                final double n6 = n2 / n4;
                double n7 = 1.0 / n4;
                if (n7 > 1.0) {
                    n7 = 1.0;
                }
                final double n8 = n5 * n7;
                final double n9 = n6 * n7;
                final double n10 = n8 * 0.10000000149011612;
                final double n11 = n9 * 0.10000000149011612;
                final double n12 = n10 * (1.0f - this.entityCollisionReduction);
                final double n13 = n11 * (1.0f - this.entityCollisionReduction);
                final double n14 = n12 * 0.5;
                final double n15 = n13 * 0.5;
                if (entity instanceof EntityMinecart) {
                    if (Math.abs(new Vec3(entity.posX - this.posX, 0.0, entity.posZ - this.posZ).normalize().dotProduct(new Vec3(MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f), 0.0, MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f)).normalize())) < 0.800000011920929) {
                        return;
                    }
                    final double n16 = entity.motionX + this.motionX;
                    final double n17 = entity.motionZ + this.motionZ;
                    if (((EntityMinecart)entity).func_180456_s() == EnumMinecartType.FURNACE && this.func_180456_s() != EnumMinecartType.FURNACE) {
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(entity.motionX - n14, 0.0, entity.motionZ - n15);
                        entity.motionX *= 0.949999988079071;
                        entity.motionZ *= 0.949999988079071;
                    }
                    else if (((EntityMinecart)entity).func_180456_s() != EnumMinecartType.FURNACE && this.func_180456_s() == EnumMinecartType.FURNACE) {
                        entity.motionX *= 0.20000000298023224;
                        entity.motionZ *= 0.20000000298023224;
                        entity.addVelocity(this.motionX + n14, 0.0, this.motionZ + n15);
                        this.motionX *= 0.949999988079071;
                        this.motionZ *= 0.949999988079071;
                    }
                    else {
                        final double n18 = n16 / 2.0;
                        final double n19 = n17 / 2.0;
                        this.motionX *= 0.20000000298023224;
                        this.motionZ *= 0.20000000298023224;
                        this.addVelocity(n18 - n14, 0.0, n19 - n15);
                        entity.motionX *= 0.20000000298023224;
                        entity.motionZ *= 0.20000000298023224;
                        entity.addVelocity(n18 + n14, 0.0, n19 + n15);
                    }
                }
                else {
                    this.addVelocity(-n14, 0.0, -n15);
                    entity.addVelocity(n14 / 4.0, 0.0, n15 / 4.0);
                }
            }
        }
    }
    
    @Override
    public void func_180426_a(final double minecartX, final double minecartY, final double minecartZ, final float n, final float n2, final int n3, final boolean b) {
        this.minecartX = minecartX;
        this.minecartY = minecartY;
        this.minecartZ = minecartZ;
        this.minecartYaw = n;
        this.minecartPitch = n2;
        this.turnProgress = n3 + 2;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.velocityX = n;
        this.motionY = n2;
        this.velocityY = n2;
        this.motionZ = n3;
        this.velocityZ = n3;
    }
    
    public void setDamage(final float n) {
        this.dataWatcher.updateObject(19, n);
    }
    
    public float getDamage() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }
    
    public void setRollingAmplitude(final int n) {
        this.dataWatcher.updateObject(17, n);
    }
    
    public int getRollingAmplitude() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }
    
    public void setRollingDirection(final int n) {
        this.dataWatcher.updateObject(18, n);
    }
    
    public int getRollingDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public abstract EnumMinecartType func_180456_s();
    
    public IBlockState func_174897_t() {
        return this.hasDisplayTile() ? Block.getStateById(this.getDataWatcher().getWatchableObjectInt(20)) : this.func_180457_u();
    }
    
    public IBlockState func_180457_u() {
        return Blocks.air.getDefaultState();
    }
    
    public int getDisplayTileOffset() {
        return this.hasDisplayTile() ? this.getDataWatcher().getWatchableObjectInt(21) : this.getDefaultDisplayTileOffset();
    }
    
    public int getDefaultDisplayTileOffset() {
        return 6;
    }
    
    public void func_174899_a(final IBlockState blockState) {
        this.getDataWatcher().updateObject(20, Block.getStateId(blockState));
        this.setHasDisplayTile(true);
    }
    
    public void setDisplayTileOffset(final int n) {
        this.getDataWatcher().updateObject(21, n);
        this.setHasDisplayTile(true);
    }
    
    public boolean hasDisplayTile() {
        return this.getDataWatcher().getWatchableObjectByte(22) == 1;
    }
    
    public void setHasDisplayTile(final boolean b) {
        this.getDataWatcher().updateObject(22, (byte)(byte)(b ? 1 : 0));
    }
    
    @Override
    public void setCustomNameTag(final String entityName) {
        this.entityName = entityName;
    }
    
    @Override
    public String getName() {
        return (this.entityName != null) ? this.entityName : super.getName();
    }
    
    @Override
    public boolean hasCustomName() {
        return this.entityName != null;
    }
    
    @Override
    public String getCustomNameTag() {
        return this.entityName;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        if (this.hasCustomName()) {
            final ChatComponentText chatComponentText = new ChatComponentText(this.entityName);
            chatComponentText.getChatStyle().setChatHoverEvent(this.func_174823_aP());
            chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentText;
        }
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(this.getName(), new Object[0]);
        chatComponentTranslation.getChatStyle().setChatHoverEvent(this.func_174823_aP());
        chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
        return chatComponentTranslation;
    }
    
    public enum EnumMinecartType
    {
        RIDEABLE("RIDEABLE", 0, "RIDEABLE", 0, 0, "MinecartRideable"), 
        CHEST("CHEST", 1, "CHEST", 1, 1, "MinecartChest"), 
        FURNACE("FURNACE", 2, "FURNACE", 2, 2, "MinecartFurnace"), 
        TNT("TNT", 3, "TNT", 3, 3, "MinecartTNT"), 
        SPAWNER("SPAWNER", 4, "SPAWNER", 4, 4, "MinecartSpawner"), 
        HOPPER("HOPPER", 5, "HOPPER", 5, 5, "MinecartHopper"), 
        COMMAND_BLOCK("COMMAND_BLOCK", 6, "COMMAND_BLOCK", 6, 6, "MinecartCommandBlock");
        
        private static final Map field_180051_h;
        private final int field_180052_i;
        private final String field_180049_j;
        private static final EnumMinecartType[] $VALUES;
        private static final String __OBFID;
        private static final EnumMinecartType[] ENUM$VALUES;
        
        static {
            __OBFID = "CL_00002226";
            ENUM$VALUES = new EnumMinecartType[] { EnumMinecartType.RIDEABLE, EnumMinecartType.CHEST, EnumMinecartType.FURNACE, EnumMinecartType.TNT, EnumMinecartType.SPAWNER, EnumMinecartType.HOPPER, EnumMinecartType.COMMAND_BLOCK };
            field_180051_h = Maps.newHashMap();
            $VALUES = new EnumMinecartType[] { EnumMinecartType.RIDEABLE, EnumMinecartType.CHEST, EnumMinecartType.FURNACE, EnumMinecartType.TNT, EnumMinecartType.SPAWNER, EnumMinecartType.HOPPER, EnumMinecartType.COMMAND_BLOCK };
            final EnumMinecartType[] values = values();
            while (0 < values.length) {
                final EnumMinecartType enumMinecartType = values[0];
                EnumMinecartType.field_180051_h.put(enumMinecartType.func_180039_a(), enumMinecartType);
                int n = 0;
                ++n;
            }
        }
        
        private EnumMinecartType(final String s, final int n, final String s2, final int n2, final int field_180052_i, final String field_180049_j) {
            this.field_180052_i = field_180052_i;
            this.field_180049_j = field_180049_j;
        }
        
        public int func_180039_a() {
            return this.field_180052_i;
        }
        
        public String func_180040_b() {
            return this.field_180049_j;
        }
        
        public static EnumMinecartType func_180038_a(final int n) {
            final EnumMinecartType enumMinecartType = EnumMinecartType.field_180051_h.get(n);
            return (enumMinecartType == null) ? EnumMinecartType.RIDEABLE : enumMinecartType;
        }
    }
    
    static final class SwitchEnumMinecartType
    {
        static final int[] field_180037_a;
        static final int[] field_180036_b;
        private static final String __OBFID;
        private static final String[] llllIlllIlllllI;
        private static String[] llllIlllIllllll;
        
        static {
            lIllIllIIlllIlll();
            lIllIllIIlllIllI();
            __OBFID = SwitchEnumMinecartType.llllIlllIlllllI[0];
            field_180036_b = new int[BlockRailBase.EnumRailDirection.values().length];
            try {
                SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                SwitchEnumMinecartType.field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            field_180037_a = new int[EnumMinecartType.values().length];
            try {
                SwitchEnumMinecartType.field_180037_a[EnumMinecartType.CHEST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                SwitchEnumMinecartType.field_180037_a[EnumMinecartType.FURNACE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                SwitchEnumMinecartType.field_180037_a[EnumMinecartType.TNT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                SwitchEnumMinecartType.field_180037_a[EnumMinecartType.SPAWNER.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                SwitchEnumMinecartType.field_180037_a[EnumMinecartType.HOPPER.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                SwitchEnumMinecartType.field_180037_a[EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
        }
        
        private static void lIllIllIIlllIllI() {
            (llllIlllIlllllI = new String[1])[0] = lIllIllIIlllIlIl(SwitchEnumMinecartType.llllIlllIllllll[0], SwitchEnumMinecartType.llllIlllIllllll[1]);
            SwitchEnumMinecartType.llllIlllIllllll = null;
        }
        
        private static void lIllIllIIlllIlll() {
            final String fileName = new Exception().getStackTrace()[0].getFileName();
            SwitchEnumMinecartType.llllIlllIllllll = fileName.substring(fileName.indexOf("\u00e4") + 1, fileName.lastIndexOf("\u00fc")).split("\u00f6");
        }
        
        private static String lIllIllIIlllIlIl(final String s, final String s2) {
            try {
                final SecretKeySpec secretKeySpec = new SecretKeySpec(MessageDigest.getInstance("SHA-256").digest(s2.getBytes(StandardCharsets.UTF_8)), "AES");
                final Cipher instance = Cipher.getInstance("AES");
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
