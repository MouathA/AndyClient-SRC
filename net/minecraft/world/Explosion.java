package net.minecraft.world;

import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class Explosion
{
    private final boolean isFlaming;
    private final boolean isSmoking;
    private final Random explosionRNG;
    private final World worldObj;
    private final double explosionX;
    private final double explosionY;
    private final double explosionZ;
    private final Entity exploder;
    private final float explosionSize;
    private final List affectedBlockPositions;
    private final Map field_77288_k;
    private static final String __OBFID;
    
    public Explosion(final World world, final Entity entity, final double n, final double n2, final double n3, final float n4, final List list) {
        this(world, entity, n, n2, n3, n4, false, true, list);
    }
    
    public Explosion(final World world, final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2, final List list) {
        this(world, entity, n, n2, n3, n4, b, b2);
        this.affectedBlockPositions.addAll(list);
    }
    
    public Explosion(final World worldObj, final Entity exploder, final double explosionX, final double explosionY, final double explosionZ, final float explosionSize, final boolean isFlaming, final boolean isSmoking) {
        this.explosionRNG = new Random();
        this.affectedBlockPositions = Lists.newArrayList();
        this.field_77288_k = Maps.newHashMap();
        this.worldObj = worldObj;
        this.exploder = exploder;
        this.explosionSize = explosionSize;
        this.explosionX = explosionX;
        this.explosionY = explosionY;
        this.explosionZ = explosionZ;
        this.isFlaming = isFlaming;
        this.isSmoking = isSmoking;
    }
    
    public void doExplosionA() {
        this.affectedBlockPositions.addAll(Sets.newHashSet());
        final float n = this.explosionSize * 2.0f;
        MathHelper.floor_double(this.explosionX - n - 1.0);
        MathHelper.floor_double(this.explosionX + n + 1.0);
        final List entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(0, MathHelper.floor_double(this.explosionY - n - 1.0), MathHelper.floor_double(this.explosionZ - n - 1.0), 0, MathHelper.floor_double(this.explosionY + n + 1.0), MathHelper.floor_double(this.explosionZ + n + 1.0)));
        final Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);
        while (0 < entitiesWithinAABBExcludingEntity.size()) {
            final Entity entity = entitiesWithinAABBExcludingEntity.get(0);
            if (!entity.func_180427_aV()) {
                final double n2 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / n;
                if (n2 <= 1.0) {
                    final double n3 = entity.posX - this.explosionX;
                    final double n4 = entity.posY + entity.getEyeHeight() - this.explosionY;
                    final double n5 = entity.posZ - this.explosionZ;
                    final double n6 = MathHelper.sqrt_double(n3 * n3 + n4 * n4 + n5 * n5);
                    if (n6 != 0.0) {
                        final double n7 = n3 / n6;
                        final double n8 = n4 / n6;
                        final double n9 = n5 / n6;
                        final double n10 = (1.0 - n2) * this.worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
                        entity.attackEntityFrom(DamageSource.setExplosionSource(this), (float)(int)((n10 * n10 + n10) / 2.0 * 8.0 * n + 1.0));
                        final double func_92092_a = EnchantmentProtection.func_92092_a(entity, n10);
                        final Entity entity2 = entity;
                        entity2.motionX += n7 * func_92092_a;
                        final Entity entity3 = entity;
                        entity3.motionY += n8 * func_92092_a;
                        final Entity entity4 = entity;
                        entity4.motionZ += n9 * func_92092_a;
                        if (entity instanceof EntityPlayer) {
                            this.field_77288_k.put(entity, new Vec3(n7 * n10, n8 * n10, n9 * n10));
                        }
                    }
                }
            }
            int n11 = 0;
            ++n11;
        }
    }
    
    public void doExplosionB(final boolean b) {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (this.explosionSize >= 2.0f && this.isSmoking) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0, new int[0]);
        }
        else {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0, 0.0, 0.0, new int[0]);
        }
        if (this.isSmoking) {
            for (final BlockPos blockPos : this.affectedBlockPositions) {
                final Block block = this.worldObj.getBlockState(blockPos).getBlock();
                if (b) {
                    final double n = blockPos.getX() + this.worldObj.rand.nextFloat();
                    final double n2 = blockPos.getY() + this.worldObj.rand.nextFloat();
                    final double n3 = blockPos.getZ() + this.worldObj.rand.nextFloat();
                    final double n4 = n - this.explosionX;
                    final double n5 = n2 - this.explosionY;
                    final double n6 = n3 - this.explosionZ;
                    final double n7 = MathHelper.sqrt_double(n4 * n4 + n5 * n5 + n6 * n6);
                    final double n8 = n4 / n7;
                    final double n9 = n5 / n7;
                    final double n10 = n6 / n7;
                    final double n11 = 0.5 / (n7 / this.explosionSize + 0.1) * (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f);
                    final double n12 = n8 * n11;
                    final double n13 = n9 * n11;
                    final double n14 = n10 * n11;
                    this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (n + this.explosionX * 1.0) / 2.0, (n2 + this.explosionY * 1.0) / 2.0, (n3 + this.explosionZ * 1.0) / 2.0, n12, n13, n14, new int[0]);
                    this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, n, n2, n3, n12, n13, n14, new int[0]);
                }
                if (block.getMaterial() != Material.air) {
                    if (block.canDropFromExplosion(this)) {
                        block.dropBlockAsItemWithChance(this.worldObj, blockPos, this.worldObj.getBlockState(blockPos), 1.0f / this.explosionSize, 0);
                    }
                    this.worldObj.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
                    block.onBlockDestroyedByExplosion(this.worldObj, blockPos, this);
                }
            }
        }
        if (this.isFlaming) {
            for (final BlockPos blockPos2 : this.affectedBlockPositions) {
                if (this.worldObj.getBlockState(blockPos2).getBlock().getMaterial() == Material.air && this.worldObj.getBlockState(blockPos2.offsetDown()).getBlock().isFullBlock() && this.explosionRNG.nextInt(3) == 0) {
                    this.worldObj.setBlockState(blockPos2, Blocks.fire.getDefaultState());
                }
            }
        }
    }
    
    public Map func_77277_b() {
        return this.field_77288_k;
    }
    
    public EntityLivingBase getExplosivePlacedBy() {
        return (this.exploder == null) ? null : ((this.exploder instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : ((this.exploder instanceof EntityLivingBase) ? ((EntityLivingBase)this.exploder) : null));
    }
    
    public void func_180342_d() {
        this.affectedBlockPositions.clear();
    }
    
    public List func_180343_e() {
        return this.affectedBlockPositions;
    }
    
    static {
        __OBFID = "CL_00000134";
    }
}
