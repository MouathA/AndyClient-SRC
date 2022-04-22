package optifine;

import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public class DynamicLights
{
    private static Map mapDynamicLights;
    private static final double MAX_DIST = 7.5;
    private static final double MAX_DIST_SQ = 56.25;
    
    static {
        DynamicLights.mapDynamicLights = new HashMap();
    }
    
    public static void entityAdded(final Entity entity, final RenderGlobal renderGlobal) {
    }
    
    public static void entityRemoved(final Entity entity, final RenderGlobal renderGlobal) {
        final Map mapDynamicLights = DynamicLights.mapDynamicLights;
        // monitorenter(mapDynamicLights2 = DynamicLights.mapDynamicLights)
        final DynamicLight dynamicLight = DynamicLights.mapDynamicLights.remove(IntegerCache.valueOf(entity.getEntityId()));
        if (dynamicLight != null) {
            dynamicLight.updateLitChunks(renderGlobal);
        }
    }
    // monitorexit(mapDynamicLights2)
    
    public static void update(final RenderGlobal renderGlobal) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis >= 50L) {
            DynamicLights.timeUpdateMs = currentTimeMillis;
            final Map mapDynamicLights = DynamicLights.mapDynamicLights;
            // monitorenter(mapDynamicLights2 = DynamicLights.mapDynamicLights)
            updateMapDynamicLights(renderGlobal);
            if (DynamicLights.mapDynamicLights.size() > 0) {
                final Iterator<DynamicLight> iterator = DynamicLights.mapDynamicLights.values().iterator();
                while (iterator.hasNext()) {
                    iterator.next().update(renderGlobal);
                }
            }
        }
        // monitorexit(mapDynamicLights2)
    }
    
    private static void updateMapDynamicLights(final RenderGlobal renderGlobal) {
        final WorldClient world = renderGlobal.getWorld();
        if (world != null) {
            for (final Entity entity : world.getLoadedEntityList()) {
                if (getLightLevel(entity) > 0) {
                    final Integer value = IntegerCache.valueOf(entity.getEntityId());
                    if (DynamicLights.mapDynamicLights.get(value) != null) {
                        continue;
                    }
                    DynamicLights.mapDynamicLights.put(value, new DynamicLight(entity));
                }
                else {
                    final DynamicLight dynamicLight = DynamicLights.mapDynamicLights.remove(IntegerCache.valueOf(entity.getEntityId()));
                    if (dynamicLight == null) {
                        continue;
                    }
                    dynamicLight.updateLitChunks(renderGlobal);
                }
            }
        }
    }
    
    public static int getCombinedLight(final BlockPos blockPos, int combinedLight) {
        combinedLight = getCombinedLight(getLightLevel(blockPos), combinedLight);
        return combinedLight;
    }
    
    public static int getCombinedLight(final Entity entity, int combinedLight) {
        combinedLight = getCombinedLight(getLightLevel(entity), combinedLight);
        return combinedLight;
    }
    
    public static int getCombinedLight(final double n, int n2) {
        if (n > 0.0) {
            final int n3 = (int)(n * 16.0);
            if (n3 > (n2 & 0xFF)) {
                n2 &= 0xFFFFFF00;
                n2 |= n3;
            }
        }
        return n2;
    }
    
    public static double getLightLevel(final BlockPos blockPos) {
        double n = 0.0;
        final Map mapDynamicLights = DynamicLights.mapDynamicLights;
        // monitorenter(mapDynamicLights2 = DynamicLights.mapDynamicLights)
        for (final DynamicLight dynamicLight : DynamicLights.mapDynamicLights.values()) {
            int n2 = dynamicLight.getLastLightLevel();
            if (n2 > 0) {
                final double lastPosX = dynamicLight.getLastPosX();
                final double lastPosY = dynamicLight.getLastPosY();
                final double lastPosZ = dynamicLight.getLastPosZ();
                final double n3 = blockPos.getX() - lastPosX;
                final double n4 = blockPos.getY() - lastPosY;
                final double n5 = blockPos.getZ() - lastPosZ;
                double n6 = n3 * n3 + n4 * n4 + n5 * n5;
                if (dynamicLight.isUnderwater() && !Config.isClearWater()) {
                    n2 = Config.limit(n2 - 2, 0, 15);
                    n6 *= 2.0;
                }
                if (n6 > 56.25) {
                    continue;
                }
                final double n7 = (1.0 - Math.sqrt(n6) / 7.5) * n2;
                if (n7 <= n) {
                    continue;
                }
                n = n7;
            }
        }
        // monitorexit(mapDynamicLights2)
        return Config.limit(n, 0.0, 15.0);
    }
    
    public static int getLightLevel(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final Item item = itemStack.getItem();
        if (item instanceof ItemBlock) {
            final Block block = ((ItemBlock)item).getBlock();
            if (block != null) {
                return block.getLightValue();
            }
        }
        return (item == Items.lava_bucket) ? Blocks.lava.getLightValue() : ((item != Items.blaze_rod && item != Items.blaze_powder) ? ((item == Items.glowstone_dust) ? 8 : ((item == Items.prismarine_crystals) ? 8 : ((item == Items.magma_cream) ? 8 : ((item == Items.nether_star) ? (Blocks.beacon.getLightValue() / 2) : 0)))) : 10);
    }
    
    public static int getLightLevel(final Entity entity) {
        if (entity == Config.getMinecraft().func_175606_aa() && !Config.isDynamicHandLight()) {
            return 0;
        }
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).func_175149_v()) {
            return 0;
        }
        if (entity.isBurning()) {
            return 15;
        }
        if (entity instanceof EntityFireball) {
            return 15;
        }
        if (entity instanceof EntityTNTPrimed) {
            return 15;
        }
        if (entity instanceof EntityBlaze) {
            return ((EntityBlaze)entity).func_70845_n() ? 15 : 10;
        }
        if (entity instanceof EntityMagmaCube) {
            return (((EntityMagmaCube)entity).squishFactor > 0.6) ? 13 : 8;
        }
        if (entity instanceof EntityCreeper && ((EntityCreeper)entity).getCreeperFlashIntensity(0.0f) > 0.001) {
            return 15;
        }
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            return Math.max(getLightLevel(entityLivingBase.getHeldItem()), getLightLevel(entityLivingBase.getEquipmentInSlot(4)));
        }
        if (entity instanceof EntityItem) {
            return getLightLevel(getItemStack((EntityItem)entity));
        }
        return 0;
    }
    
    public static void removeLights(final RenderGlobal renderGlobal) {
        final Map mapDynamicLights = DynamicLights.mapDynamicLights;
        // monitorenter(mapDynamicLights2 = DynamicLights.mapDynamicLights)
        final Iterator<DynamicLight> iterator = DynamicLights.mapDynamicLights.values().iterator();
        while (iterator.hasNext()) {
            final DynamicLight dynamicLight = iterator.next();
            iterator.remove();
            dynamicLight.updateLitChunks(renderGlobal);
        }
    }
    // monitorexit(mapDynamicLights2)
    
    public static void clear() {
        final Map mapDynamicLights = DynamicLights.mapDynamicLights;
        // monitorenter(mapDynamicLights2 = DynamicLights.mapDynamicLights)
        DynamicLights.mapDynamicLights.clear();
    }
    // monitorexit(mapDynamicLights2)
    
    public static int getCount() {
        final Map mapDynamicLights = DynamicLights.mapDynamicLights;
        // monitorenter(mapDynamicLights2 = DynamicLights.mapDynamicLights)
        // monitorexit(mapDynamicLights2)
        return DynamicLights.mapDynamicLights.size();
    }
    
    public static ItemStack getItemStack(final EntityItem entityItem) {
        return entityItem.getDataWatcher().getWatchableObjectItemStack(10);
    }
}
