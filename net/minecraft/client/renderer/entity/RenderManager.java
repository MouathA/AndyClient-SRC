package net.minecraft.client.renderer.entity;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import com.google.common.collect.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.effect.*;
import optifine.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import java.util.*;

public class RenderManager
{
    private Map entityRenderMap;
    private Map field_178636_l;
    private RenderPlayer field_178637_m;
    private FontRenderer textRenderer;
    public static double renderPosX;
    public static double renderPosY;
    public static double renderPosZ;
    public TextureManager renderEngine;
    public World worldObj;
    public Entity livingPlayer;
    public Entity field_147941_i;
    public static float playerViewY;
    public static float playerViewX;
    public GameSettings options;
    public double viewerPosX;
    public double viewerPosY;
    public double viewerPosZ;
    private boolean field_178639_r;
    private boolean field_178638_s;
    private boolean debugBoundingBox;
    private static final String __OBFID;
    private boolean renderShadow;
    
    public RenderManager(final TextureManager renderEngine, final RenderItem renderItem) {
        this.entityRenderMap = Maps.newHashMap();
        this.field_178636_l = Maps.newHashMap();
        this.field_178639_r = false;
        this.field_178638_s = true;
        this.debugBoundingBox = false;
        this.renderShadow = true;
        this.renderEngine = renderEngine;
        this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider(this));
        this.entityRenderMap.put(EntityPig.class, new RenderPig(this, new ModelPig(), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, new ModelSheep2(), 0.7f));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(this, new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, new ModelWolf(), 0.5f));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, new ModelChicken(), 0.3f));
        this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, new ModelOcelot(), 0.4f));
        this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, new ModelRabbit(), 0.3f));
        this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
        this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
        this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
        this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
        this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
        this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
        this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
        this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
        this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, new ModelSlime(16), 0.25f));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
        this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, new ModelSquid(), 0.7f));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
        this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
        this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
        this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
        this.entityRenderMap.put(Entity.class, new RenderEntity(this));
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, renderItem));
        this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(this, Items.snowball, renderItem));
        this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(this, Items.ender_pearl, renderItem));
        this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(this, Items.ender_eye, renderItem));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(this, Items.egg, renderItem));
        this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, renderItem));
        this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(this, Items.experience_bottle, renderItem));
        this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(this, Items.fireworks, renderItem));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0f));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5f));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
        this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, renderItem));
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
        this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
        this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
        this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart(this));
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
        this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75f));
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
        this.field_178637_m = new RenderPlayer(this);
        this.field_178636_l.put("default", this.field_178637_m);
        this.field_178636_l.put("slim", new RenderPlayer(this, true));
        PlayerItemsLayer.register(this.field_178636_l);
        if (Reflector.RenderingRegistry_loadEntityRenderers.exists()) {
            Reflector.call(Reflector.RenderingRegistry_loadEntityRenderers, this, this.entityRenderMap);
        }
    }
    
    public void func_178628_a(final double renderPosX, final double renderPosY, final double renderPosZ) {
        RenderManager.renderPosX = renderPosX;
        RenderManager.renderPosY = renderPosY;
        RenderManager.renderPosZ = renderPosZ;
    }
    
    public Render getEntityClassRenderObject(final Class clazz) {
        Render entityClassRenderObject = this.entityRenderMap.get(clazz);
        if (entityClassRenderObject == null && clazz != Entity.class) {
            entityClassRenderObject = this.getEntityClassRenderObject(clazz.getSuperclass());
            this.entityRenderMap.put(clazz, entityClassRenderObject);
        }
        return entityClassRenderObject;
    }
    
    public Render getEntityRenderObject(final Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            final RenderPlayer renderPlayer = this.field_178636_l.get(((AbstractClientPlayer)entity).func_175154_l());
            return (renderPlayer != null) ? renderPlayer : this.field_178637_m;
        }
        return this.getEntityClassRenderObject(entity.getClass());
    }
    
    public void func_180597_a(final World worldObj, final FontRenderer textRenderer, final Entity livingPlayer, final Entity field_147941_i, final GameSettings options, final float n) {
        this.worldObj = worldObj;
        this.options = options;
        this.livingPlayer = livingPlayer;
        this.field_147941_i = field_147941_i;
        this.textRenderer = textRenderer;
        if (livingPlayer instanceof EntityLivingBase && ((EntityLivingBase)livingPlayer).isPlayerSleeping()) {
            final IBlockState blockState = worldObj.getBlockState(new BlockPos(livingPlayer));
            final Block block = blockState.getBlock();
            if (Reflector.callBoolean(Reflector.ForgeBlock_isBed, worldObj, new BlockPos(livingPlayer), (EntityLivingBase)livingPlayer)) {
                RenderManager.playerViewY = (float)(((EnumFacing)Reflector.call(block, Reflector.ForgeBlock_getBedDirection, worldObj, new BlockPos(livingPlayer))).getHorizontalIndex() * 90 + 180);
                RenderManager.playerViewX = 0.0f;
            }
            else if (block == Blocks.bed) {
                RenderManager.playerViewY = (float)(((EnumFacing)blockState.getValue(BlockBed.AGE)).getHorizontalIndex() * 90 + 180);
                RenderManager.playerViewX = 0.0f;
            }
        }
        else {
            RenderManager.playerViewY = livingPlayer.prevRotationYaw + (livingPlayer.rotationYaw - livingPlayer.prevRotationYaw) * n;
            RenderManager.playerViewX = livingPlayer.prevRotationPitch + (livingPlayer.rotationPitch - livingPlayer.prevRotationPitch) * n;
        }
        if (options.thirdPersonView == 2) {
            RenderManager.playerViewY += 180.0f;
        }
        this.viewerPosX = livingPlayer.lastTickPosX + (livingPlayer.posX - livingPlayer.lastTickPosX) * n;
        this.viewerPosY = livingPlayer.lastTickPosY + (livingPlayer.posY - livingPlayer.lastTickPosY) * n;
        this.viewerPosZ = livingPlayer.lastTickPosZ + (livingPlayer.posZ - livingPlayer.lastTickPosZ) * n;
    }
    
    public void func_178631_a(final float playerViewY) {
        RenderManager.playerViewY = playerViewY;
    }
    
    public boolean func_178627_a() {
        return this.field_178638_s;
    }
    
    public void func_178633_a(final boolean field_178638_s) {
        this.field_178638_s = field_178638_s;
    }
    
    public void func_178629_b(final boolean debugBoundingBox) {
        this.debugBoundingBox = debugBoundingBox;
    }
    
    public boolean func_178634_b() {
        return this.debugBoundingBox;
    }
    
    public boolean renderEntitySimple(final Entity entity, final float n) {
        return this.renderEntityStatic(entity, n, false);
    }
    
    public boolean func_178635_a(final Entity entity, final ICamera camera, final double n, final double n2, final double n3) {
        final Render entityRenderObject = this.getEntityRenderObject(entity);
        return entityRenderObject != null && entityRenderObject.func_177071_a(entity, camera, n, n2, n3);
    }
    
    public boolean renderEntityStatic(final Entity entity, final float n, final boolean b) {
        if (entity.ticksExisted == 0) {
            entity.lastTickPosX = entity.posX;
            entity.lastTickPosY = entity.posY;
            entity.lastTickPosZ = entity.posZ;
        }
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        final float n5 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * n;
        entity.getBrightnessForRender(n);
        if (entity.isBurning()) {}
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240 / 1.0f, 145637 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return this.doRenderEntity(entity, n2 - RenderManager.renderPosX, n3 - RenderManager.renderPosY, n4 - RenderManager.renderPosZ, n5, n, b);
    }
    
    public void func_178630_b(final Entity entity, final float n) {
        final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
        final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
        final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
        final Render entityRenderObject = this.getEntityRenderObject(entity);
        if (entityRenderObject != null && this.renderEngine != null) {
            final int brightnessForRender = entity.getBrightnessForRender(n);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % 65536 / 1.0f, brightnessForRender / 65536 / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            entityRenderObject.func_177067_a(entity, n2 - RenderManager.renderPosX, n3 - RenderManager.renderPosY, n4 - RenderManager.renderPosZ);
        }
    }
    
    public boolean renderEntityWithPosYaw(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        return this.doRenderEntity(entity, n, n2, n3, n4, n5, false);
    }
    
    public boolean doRenderEntity(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5, final boolean b) {
        final Render entityRenderObject = this.getEntityRenderObject(entity);
        if (entityRenderObject != null && this.renderEngine != null) {
            if (entityRenderObject instanceof RendererLivingEntity) {
                ((RendererLivingEntity)entityRenderObject).func_177086_a(this.field_178639_r);
            }
            entityRenderObject.doRender(entity, n, n2, n3, n4, n5);
            if (!this.field_178639_r) {
                entityRenderObject.doRenderShadowAndFire(entity, n, n2, n3, n4, n5);
            }
            if (this.debugBoundingBox && !entity.isInvisible() && !b) {
                this.renderDebugBoundingBox(entity, n, n2, n3, n4, n5);
            }
        }
        else if (this.renderEngine != null) {
            return false;
        }
        return true;
    }
    
    private void renderDebugBoundingBox(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.depthMask(false);
        final float n6 = entity.width / 2.0f;
        final AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox();
        RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(entityBoundingBox.minX - entity.posX + n, entityBoundingBox.minY - entity.posY + n2, entityBoundingBox.minZ - entity.posZ + n3, entityBoundingBox.maxX - entity.posX + n, entityBoundingBox.maxY - entity.posY + n2, entityBoundingBox.maxZ - entity.posZ + n3), 16777215);
        if (entity instanceof EntityLivingBase) {
            RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(n - n6, n2 + entity.getEyeHeight() - 0.009999999776482582, n3 - n6, n + n6, n2 + entity.getEyeHeight() + 0.009999999776482582, n3 + n6), 16711680);
        }
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        final Vec3 look = entity.getLook(n5);
        worldRenderer.startDrawing(3);
        worldRenderer.func_178991_c(255);
        worldRenderer.addVertex(n, n2 + entity.getEyeHeight(), n3);
        worldRenderer.addVertex(n + look.xCoord * 2.0, n2 + entity.getEyeHeight() + look.yCoord * 2.0, n3 + look.zCoord * 2.0);
        instance.draw();
        GlStateManager.depthMask(true);
    }
    
    public void set(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public double getDistanceToCamera(final double n, final double n2, final double n3) {
        final double n4 = n - this.viewerPosX;
        final double n5 = n2 - this.viewerPosY;
        final double n6 = n3 - this.viewerPosZ;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public FontRenderer getFontRenderer() {
        return this.textRenderer;
    }
    
    public void func_178632_c(final boolean field_178639_r) {
        this.field_178639_r = field_178639_r;
    }
    
    public Map getEntityRenderMap() {
        return this.entityRenderMap;
    }
    
    public void setEntityRenderMap(final Map entityRenderMap) {
        this.entityRenderMap = entityRenderMap;
    }
    
    public Map getSkinMap() {
        return Collections.unmodifiableMap((Map<?, ?>)this.field_178636_l);
    }
    
    public void setRenderShadow(final boolean renderShadow) {
        this.renderShadow = renderShadow;
    }
    
    public void setPlayerViewY(final float playerViewY) {
        RenderManager.playerViewY = playerViewY;
    }
    
    static {
        __OBFID = "CL_00000991";
    }
}
