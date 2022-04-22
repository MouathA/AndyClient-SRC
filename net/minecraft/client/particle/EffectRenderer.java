package net.minecraft.client.particle;

import com.google.common.collect.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.state.*;
import optifine.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class EffectRenderer
{
    private static final ResourceLocation particleTextures;
    protected World worldObj;
    private List[][] fxLayers;
    private List field_178933_d;
    private TextureManager renderer;
    private Random rand;
    private Map field_178932_g;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000915";
        particleTextures = new ResourceLocation("textures/particle/particles.png");
    }
    
    public EffectRenderer(final World worldObj, final TextureManager renderer) {
        this.fxLayers = new List[4][];
        this.field_178933_d = Lists.newArrayList();
        this.rand = new Random();
        this.field_178932_g = Maps.newHashMap();
        this.worldObj = worldObj;
        this.renderer = renderer;
        while (0 < 4) {
            this.fxLayers[0] = new List[2];
            while (0 < 2) {
                this.fxLayers[0][0] = Lists.newArrayList();
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.func_178930_c();
    }
    
    private void func_178930_c() {
        this.func_178929_a(EnumParticleTypes.EXPLOSION_NORMAL.func_179348_c(), new EntityExplodeFX.Factory());
        this.func_178929_a(EnumParticleTypes.WATER_BUBBLE.func_179348_c(), new EntityBubbleFX.Factory());
        this.func_178929_a(EnumParticleTypes.WATER_SPLASH.func_179348_c(), new EntitySplashFX.Factory());
        this.func_178929_a(EnumParticleTypes.WATER_WAKE.func_179348_c(), new EntityFishWakeFX.Factory());
        this.func_178929_a(EnumParticleTypes.WATER_DROP.func_179348_c(), new EntityRainFX.Factory());
        this.func_178929_a(EnumParticleTypes.SUSPENDED.func_179348_c(), new EntitySuspendFX.Factory());
        this.func_178929_a(EnumParticleTypes.SUSPENDED_DEPTH.func_179348_c(), new EntityAuraFX.Factory());
        this.func_178929_a(EnumParticleTypes.CRIT.func_179348_c(), new EntityCrit2FX.Factory());
        this.func_178929_a(EnumParticleTypes.CRIT_MAGIC.func_179348_c(), new EntityCrit2FX.MagicFactory());
        this.func_178929_a(EnumParticleTypes.SMOKE_NORMAL.func_179348_c(), new EntitySmokeFX.Factory());
        this.func_178929_a(EnumParticleTypes.SMOKE_LARGE.func_179348_c(), new EntityCritFX.Factory());
        this.func_178929_a(EnumParticleTypes.SPELL.func_179348_c(), new EntitySpellParticleFX.Factory());
        this.func_178929_a(EnumParticleTypes.SPELL_INSTANT.func_179348_c(), new EntitySpellParticleFX.InstantFactory());
        this.func_178929_a(EnumParticleTypes.SPELL_MOB.func_179348_c(), new EntitySpellParticleFX.MobFactory());
        this.func_178929_a(EnumParticleTypes.SPELL_MOB_AMBIENT.func_179348_c(), new EntitySpellParticleFX.AmbientMobFactory());
        this.func_178929_a(EnumParticleTypes.SPELL_WITCH.func_179348_c(), new EntitySpellParticleFX.WitchFactory());
        this.func_178929_a(EnumParticleTypes.DRIP_WATER.func_179348_c(), new EntityDropParticleFX.WaterFactory());
        this.func_178929_a(EnumParticleTypes.DRIP_LAVA.func_179348_c(), new EntityDropParticleFX.LavaFactory());
        this.func_178929_a(EnumParticleTypes.VILLAGER_ANGRY.func_179348_c(), new EntityHeartFX.AngryVillagerFactory());
        this.func_178929_a(EnumParticleTypes.VILLAGER_HAPPY.func_179348_c(), new EntityAuraFX.HappyVillagerFactory());
        this.func_178929_a(EnumParticleTypes.TOWN_AURA.func_179348_c(), new EntityAuraFX.Factory());
        this.func_178929_a(EnumParticleTypes.NOTE.func_179348_c(), new EntityNoteFX.Factory());
        this.func_178929_a(EnumParticleTypes.PORTAL.func_179348_c(), new EntityPortalFX.Factory());
        this.func_178929_a(EnumParticleTypes.ENCHANTMENT_TABLE.func_179348_c(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
        this.func_178929_a(EnumParticleTypes.FLAME.func_179348_c(), new EntityFlameFX.Factory());
        this.func_178929_a(EnumParticleTypes.LAVA.func_179348_c(), new EntityLavaFX.Factory());
        this.func_178929_a(EnumParticleTypes.FOOTSTEP.func_179348_c(), new EntityFootStepFX.Factory());
        this.func_178929_a(EnumParticleTypes.CLOUD.func_179348_c(), new EntityCloudFX.Factory());
        this.func_178929_a(EnumParticleTypes.REDSTONE.func_179348_c(), new EntityReddustFX.Factory());
        this.func_178929_a(EnumParticleTypes.SNOWBALL.func_179348_c(), new EntityBreakingFX.SnowballFactory());
        this.func_178929_a(EnumParticleTypes.SNOW_SHOVEL.func_179348_c(), new EntitySnowShovelFX.Factory());
        this.func_178929_a(EnumParticleTypes.SLIME.func_179348_c(), new EntityBreakingFX.SlimeFactory());
        this.func_178929_a(EnumParticleTypes.HEART.func_179348_c(), new EntityHeartFX.Factory());
        this.func_178929_a(EnumParticleTypes.BARRIER.func_179348_c(), new Barrier.Factory());
        this.func_178929_a(EnumParticleTypes.ITEM_CRACK.func_179348_c(), new EntityBreakingFX.Factory());
        this.func_178929_a(EnumParticleTypes.BLOCK_CRACK.func_179348_c(), new EntityDiggingFX.Factory());
        this.func_178929_a(EnumParticleTypes.BLOCK_DUST.func_179348_c(), new EntityBlockDustFX.Factory());
        this.func_178929_a(EnumParticleTypes.EXPLOSION_HUGE.func_179348_c(), new EntityHugeExplodeFX.Factory());
        this.func_178929_a(EnumParticleTypes.EXPLOSION_LARGE.func_179348_c(), new EntityLargeExplodeFX.Factory());
        this.func_178929_a(EnumParticleTypes.FIREWORKS_SPARK.func_179348_c(), new EntityFireworkStarterFX_Factory());
        this.func_178929_a(EnumParticleTypes.MOB_APPEARANCE.func_179348_c(), new MobAppearance.Factory());
    }
    
    public void func_178929_a(final int n, final IParticleFactory particleFactory) {
        this.field_178932_g.put(n, particleFactory);
    }
    
    public void func_178926_a(final Entity entity, final EnumParticleTypes enumParticleTypes) {
        this.field_178933_d.add(new EntityParticleEmitter(this.worldObj, entity, enumParticleTypes));
    }
    
    public EntityFX func_178927_a(final int n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        final IParticleFactory particleFactory = this.field_178932_g.get(n);
        if (particleFactory != null) {
            final EntityFX func_178902_a = particleFactory.func_178902_a(n, this.worldObj, n2, n3, n4, n5, n6, n7, array);
            if (func_178902_a != null) {
                this.addEffect(func_178902_a);
                return func_178902_a;
            }
        }
        return null;
    }
    
    public void addEffect(final EntityFX entityFX) {
        if (entityFX != null && (!(entityFX instanceof EntityFireworkSparkFX) || Config.isFireworkParticles())) {
            final int fxLayer = entityFX.getFXLayer();
            final int n = (entityFX.func_174838_j() == 1.0f) ? 1 : 0;
            if (this.fxLayers[fxLayer][n].size() >= 4000) {
                this.fxLayers[fxLayer][n].remove(0);
            }
            if (!(entityFX instanceof Barrier) || !this.reuseBarrierParticle(entityFX, this.fxLayers[fxLayer][n])) {
                this.fxLayers[fxLayer][n].add(entityFX);
            }
        }
    }
    
    public void updateEffects() {
        while (0 < 4) {
            this.func_178922_a(0);
            int n = 0;
            ++n;
        }
        final ArrayList arrayList = Lists.newArrayList();
        for (final EntityParticleEmitter entityParticleEmitter : this.field_178933_d) {
            entityParticleEmitter.onUpdate();
            if (entityParticleEmitter.isDead) {
                arrayList.add(entityParticleEmitter);
            }
        }
        this.field_178933_d.removeAll(arrayList);
    }
    
    private void func_178922_a(final int n) {
        while (0 < 2) {
            this.func_178925_a(this.fxLayers[n][0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    private void func_178925_a(final List list) {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < list.size()) {
            final EntityFX entityFX = list.get(0);
            this.func_178923_d(entityFX);
            if (entityFX.isDead) {
                arrayList.add(entityFX);
            }
            int n = 0;
            ++n;
        }
        list.removeAll(arrayList);
    }
    
    private void func_178923_d(final EntityFX entityFX) {
        entityFX.onUpdate();
    }
    
    public void renderParticles(final Entity entity, final float n) {
        final float func_178808_b = ActiveRenderInfo.func_178808_b();
        final float func_178803_d = ActiveRenderInfo.func_178803_d();
        final float func_178805_e = ActiveRenderInfo.func_178805_e();
        final float func_178807_f = ActiveRenderInfo.func_178807_f();
        final float func_178809_c = ActiveRenderInfo.func_178809_c();
        EntityFX.interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        EntityFX.interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        EntityFX.interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.003921569f);
        while (0 < 3) {
            while (0 < 2) {
                if (!this.fxLayers[0][0].isEmpty()) {
                    switch (false) {
                        case 0: {
                            GlStateManager.depthMask(false);
                            break;
                        }
                        case 1: {
                            GlStateManager.depthMask(true);
                            break;
                        }
                    }
                    switch (false) {
                        default: {
                            this.renderer.bindTexture(EffectRenderer.particleTextures);
                            break;
                        }
                        case 1: {
                            this.renderer.bindTexture(TextureMap.locationBlocksTexture);
                            break;
                        }
                    }
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    final Tessellator instance = Tessellator.getInstance();
                    final WorldRenderer worldRenderer = instance.getWorldRenderer();
                    worldRenderer.startDrawingQuads();
                    while (0 < this.fxLayers[0][0].size()) {
                        final EntityFX entityFX = this.fxLayers[0][0].get(0);
                        worldRenderer.func_178963_b(entityFX.getBrightnessForRender(n));
                        entityFX.func_180434_a(worldRenderer, entity, n, func_178808_b, func_178809_c, func_178803_d, func_178805_e, func_178807_f);
                        int n2 = 0;
                        ++n2;
                    }
                    instance.draw();
                }
                int n3 = 0;
                ++n3;
            }
            int n4 = 0;
            ++n4;
        }
        GlStateManager.depthMask(true);
        GlStateManager.alphaFunc(516, 0.1f);
    }
    
    public void renderLitParticles(final Entity entity, final float n) {
        final float cos = MathHelper.cos(entity.rotationYaw * 0.017453292f);
        final float sin = MathHelper.sin(entity.rotationYaw * 0.017453292f);
        final float n2 = -sin * MathHelper.sin(entity.rotationPitch * 0.017453292f);
        final float n3 = cos * MathHelper.sin(entity.rotationPitch * 0.017453292f);
        final float cos2 = MathHelper.cos(entity.rotationPitch * 0.017453292f);
        while (0 < 2) {
            final List list = this.fxLayers[3][0];
            if (!list.isEmpty()) {
                final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
                while (0 < list.size()) {
                    final EntityFX entityFX = list.get(0);
                    worldRenderer.func_178963_b(entityFX.getBrightnessForRender(n));
                    entityFX.func_180434_a(worldRenderer, entity, n, cos, cos2, sin, n2, n3);
                    int n4 = 0;
                    ++n4;
                }
            }
            int n5 = 0;
            ++n5;
        }
    }
    
    public void clearEffects(final World worldObj) {
        this.worldObj = worldObj;
        while (0 < 4) {
            while (0 < 2) {
                this.fxLayers[0][0].clear();
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        this.field_178933_d.clear();
    }
    
    public void func_180533_a(final BlockPos blockPos, IBlockState actualState) {
        boolean b;
        if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
            final Block block = actualState.getBlock();
            Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, this.worldObj, blockPos);
            b = (!Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, this.worldObj, blockPos) && !Reflector.callBoolean(block, Reflector.ForgeBlock_addDestroyEffects, this.worldObj, blockPos, this));
        }
        else {
            b = (actualState.getBlock().getMaterial() != Material.air);
        }
        if (b) {
            actualState = actualState.getBlock().getActualState(actualState, this.worldObj, blockPos);
            while (0 < 4) {
                while (0 < 4) {
                    while (0 < 4) {
                        final double n = blockPos.getX() + (0 + 0.5) / 4;
                        final double n2 = blockPos.getY() + (0 + 0.5) / 4;
                        final double n3 = blockPos.getZ() + (0 + 0.5) / 4;
                        this.addEffect(new EntityDiggingFX(this.worldObj, n, n2, n3, n - blockPos.getX() - 0.5, n2 - blockPos.getY() - 0.5, n3 - blockPos.getZ() - 0.5, actualState).func_174846_a(blockPos));
                        int n4 = 0;
                        ++n4;
                    }
                    int n5 = 0;
                    ++n5;
                }
                int n6 = 0;
                ++n6;
            }
        }
    }
    
    public void func_180532_a(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.worldObj.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getRenderType() != -1) {
            final int x = blockPos.getX();
            final int y = blockPos.getY();
            final int z = blockPos.getZ();
            final float n = 0.1f;
            double n2 = x + this.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - n * 2.0f) + n + block.getBlockBoundsMinX();
            double n3 = y + this.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - n * 2.0f) + n + block.getBlockBoundsMinY();
            double n4 = z + this.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - n * 2.0f) + n + block.getBlockBoundsMinZ();
            if (enumFacing == EnumFacing.DOWN) {
                n3 = y + block.getBlockBoundsMinY() - n;
            }
            if (enumFacing == EnumFacing.UP) {
                n3 = y + block.getBlockBoundsMaxY() + n;
            }
            if (enumFacing == EnumFacing.NORTH) {
                n4 = z + block.getBlockBoundsMinZ() - n;
            }
            if (enumFacing == EnumFacing.SOUTH) {
                n4 = z + block.getBlockBoundsMaxZ() + n;
            }
            if (enumFacing == EnumFacing.WEST) {
                n2 = x + block.getBlockBoundsMinX() - n;
            }
            if (enumFacing == EnumFacing.EAST) {
                n2 = x + block.getBlockBoundsMaxX() + n;
            }
            this.addEffect(new EntityDiggingFX(this.worldObj, n2, n3, n4, 0.0, 0.0, 0.0, blockState).func_174846_a(blockPos).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6f));
        }
    }
    
    public void func_178928_b(final EntityFX entityFX) {
        this.func_178924_a(entityFX, 1, 0);
    }
    
    public void func_178931_c(final EntityFX entityFX) {
        this.func_178924_a(entityFX, 0, 1);
    }
    
    private void func_178924_a(final EntityFX entityFX, final int n, final int n2) {
        while (0 < 4) {
            if (this.fxLayers[0][n].contains(entityFX)) {
                this.fxLayers[0][n].remove(entityFX);
                this.fxLayers[0][n2].add(entityFX);
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    public String getStatistics() {
        while (0 < 4) {
            while (0 < 2) {
                final int n = 0 + this.fxLayers[0][0].size();
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return new StringBuilder().append(0).toString();
    }
    
    private boolean reuseBarrierParticle(final EntityFX entityFX, final List list) {
        for (final EntityFX entityFX2 : list) {
            if (entityFX2 instanceof Barrier && entityFX.posX == entityFX2.posX && entityFX.posY == entityFX2.posY && entityFX.posZ == entityFX2.posZ) {
                entityFX2.particleAge = 0;
                return true;
            }
        }
        return false;
    }
    
    public void addBlockHitEffects(final BlockPos blockPos, final MovingObjectPosition movingObjectPosition) {
        final Block block = this.worldObj.getBlockState(blockPos).getBlock();
        final boolean callBoolean = Reflector.callBoolean(block, Reflector.ForgeBlock_addHitEffects, this.worldObj, movingObjectPosition, this);
        if (block != null && !callBoolean) {
            this.func_180532_a(blockPos, movingObjectPosition.field_178784_b);
        }
    }
}
