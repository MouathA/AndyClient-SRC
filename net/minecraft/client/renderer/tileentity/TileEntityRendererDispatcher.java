package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class TileEntityRendererDispatcher
{
    private Map mapSpecialRenderers;
    public static TileEntityRendererDispatcher instance;
    private FontRenderer field_147557_n;
    public static double staticPlayerX;
    public static double staticPlayerY;
    public static double staticPlayerZ;
    public TextureManager renderEngine;
    public World worldObj;
    public Entity field_147551_g;
    public float field_147562_h;
    public float field_147563_i;
    public double field_147560_j;
    public double field_147561_k;
    public double field_147558_l;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000963";
        TileEntityRendererDispatcher.instance = new TileEntityRendererDispatcher();
    }
    
    private TileEntityRendererDispatcher() {
        (this.mapSpecialRenderers = Maps.newHashMap()).put(TileEntitySign.class, new TileEntitySignRenderer());
        this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
        this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
        this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
        this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
        final Iterator<TileEntitySpecialRenderer> iterator = this.mapSpecialRenderers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setRendererDispatcher(this);
        }
    }
    
    public TileEntitySpecialRenderer getSpecialRendererByClass(final Class clazz) {
        TileEntitySpecialRenderer specialRendererByClass = this.mapSpecialRenderers.get(clazz);
        if (specialRendererByClass == null && clazz != TileEntity.class) {
            specialRendererByClass = this.getSpecialRendererByClass(clazz.getSuperclass());
            this.mapSpecialRenderers.put(clazz, specialRendererByClass);
        }
        return specialRendererByClass;
    }
    
    public boolean hasSpecialRenderer(final TileEntity tileEntity) {
        return this.getSpecialRenderer(tileEntity) != null;
    }
    
    public TileEntitySpecialRenderer getSpecialRenderer(final TileEntity tileEntity) {
        return (tileEntity == null) ? null : this.getSpecialRendererByClass(tileEntity.getClass());
    }
    
    public void func_178470_a(final World world, final TextureManager renderEngine, final FontRenderer field_147557_n, final Entity field_147551_g, final float n) {
        if (this.worldObj != world) {
            this.func_147543_a(world);
        }
        this.renderEngine = renderEngine;
        this.field_147551_g = field_147551_g;
        this.field_147557_n = field_147557_n;
        this.field_147562_h = field_147551_g.prevRotationYaw + (field_147551_g.rotationYaw - field_147551_g.prevRotationYaw) * n;
        this.field_147563_i = field_147551_g.prevRotationPitch + (field_147551_g.rotationPitch - field_147551_g.prevRotationPitch) * n;
        this.field_147560_j = field_147551_g.lastTickPosX + (field_147551_g.posX - field_147551_g.lastTickPosX) * n;
        this.field_147561_k = field_147551_g.lastTickPosY + (field_147551_g.posY - field_147551_g.lastTickPosY) * n;
        this.field_147558_l = field_147551_g.lastTickPosZ + (field_147551_g.posZ - field_147551_g.lastTickPosZ) * n;
    }
    
    public void func_180546_a(final TileEntity tileEntity, final float n, final int n2) {
        if (tileEntity.getDistanceSq(this.field_147560_j, this.field_147561_k, this.field_147558_l) < tileEntity.getMaxRenderDistanceSquared()) {
            final int combinedLight = this.worldObj.getCombinedLight(tileEntity.getPos(), 0);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, combinedLight % 65536 / 1.0f, combinedLight / 65536 / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final BlockPos pos = tileEntity.getPos();
            this.func_178469_a(tileEntity, pos.getX() - TileEntityRendererDispatcher.staticPlayerX, pos.getY() - TileEntityRendererDispatcher.staticPlayerY, pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ, n, n2);
        }
    }
    
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4) {
        this.func_178469_a(tileEntity, n, n2, n3, n4, -1);
    }
    
    public void func_178469_a(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        final TileEntitySpecialRenderer specialRenderer = this.getSpecialRenderer(tileEntity);
        if (specialRenderer != null) {
            specialRenderer.renderTileEntityAt(tileEntity, n, n2, n3, n4, n5);
        }
    }
    
    public void func_147543_a(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public FontRenderer getFontRenderer() {
        return this.field_147557_n;
    }
}
