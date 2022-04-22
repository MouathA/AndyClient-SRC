package wdl;

import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import com.google.common.collect.*;
import wdl.api.*;
import java.util.*;
import wdl.chan.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;

public class EntityUtils
{
    private static final Logger logger;
    public static final Map stringToClassMapping;
    public static final Map classToStringMapping;
    
    static {
        logger = LogManager.getLogger();
        Map stringToClassMapping2 = null;
        Map classToStringMapping2 = null;
        Field[] declaredFields;
        while (0 < (declaredFields = EntityList.class.getDeclaredFields()).length) {
            final Field field = declaredFields[0];
            if (field.getType().equals(Map.class)) {
                field.setAccessible(true);
                final Map map = (Map)field.get(null);
                final Map.Entry entry = (Map.Entry)map.entrySet().toArray()[0];
                if (entry.getKey() instanceof String && entry.getValue() instanceof Class) {
                    stringToClassMapping2 = map;
                }
                if (entry.getKey() instanceof Class && entry.getValue() instanceof String) {
                    classToStringMapping2 = map;
                }
            }
            int n = 0;
            ++n;
        }
        if (stringToClassMapping2 == null) {
            throw new Exception("WDL: Failed to find stringToClassMapping!");
        }
        if (classToStringMapping2 == null) {
            throw new Exception("WDL: Failed to find classToStringMapping!");
        }
        stringToClassMapping = stringToClassMapping2;
        classToStringMapping = classToStringMapping2;
    }
    
    public static Set getEntityTypes() {
        final HashSet<Object> set = new HashSet<Object>();
        for (final Map.Entry<K, Class> entry : EntityUtils.stringToClassMapping.entrySet()) {
            if (Modifier.isAbstract(entry.getValue().getModifiers())) {
                continue;
            }
            set.add(entry.getKey());
        }
        final Iterator<WDLApi.ModInfo> iterator2 = (Iterator<WDLApi.ModInfo>)WDLApi.getImplementingExtensions(ISpecialEntityHandler.class).iterator();
        while (iterator2.hasNext()) {
            set.addAll(((ISpecialEntityHandler)iterator2.next().mod).getSpecialEntities().values());
        }
        return set;
    }
    
    public static Multimap getEntitiesByGroup() {
        final HashMultimap create = HashMultimap.create();
        for (final String s : getEntityTypes()) {
            create.put(getEntityGroup(s), s);
        }
        return create;
    }
    
    public static int getDefaultEntityRange(final String s) {
        if (s == null) {
            return -1;
        }
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IEntityAdder.class)) {
            final List modEntities = ((IEntityAdder)modInfo.mod).getModEntities();
            if (modEntities == null) {
                EntityUtils.logger.warn(String.valueOf(modInfo.toString()) + " returned null for getModEntities()!");
            }
            else {
                if (modEntities.contains(s)) {
                    return ((IEntityAdder)modInfo.mod).getDefaultEntityTrackDistance(s);
                }
                continue;
            }
        }
        for (final WDLApi.ModInfo modInfo2 : WDLApi.getImplementingExtensions(ISpecialEntityHandler.class)) {
            final Multimap specialEntities = ((ISpecialEntityHandler)modInfo2.mod).getSpecialEntities();
            if (specialEntities == null) {
                EntityUtils.logger.warn(String.valueOf(modInfo2.toString()) + " returned null for getSpecialEntities()!");
            }
            else {
                for (final Map.Entry<K, String> entry : specialEntities.entries()) {
                    if (entry.getValue().equals(s)) {
                        int n = ((ISpecialEntityHandler)modInfo2.mod).getSpecialEntityTrackDistance(entry.getValue());
                        if (n < 0) {
                            n = getMostLikelyEntityTrackDistance((String)entry.getKey());
                        }
                        return n;
                    }
                }
            }
        }
        return getVanillaEntityRange(EntityUtils.stringToClassMapping.get(s));
    }
    
    public static int getEntityTrackDistance(final Entity entity) {
        return getEntityTrackDistance(getTrackDistanceMode(), entity);
    }
    
    public static int getEntityTrackDistance(final String s, final Entity entity) {
        if ("default".equals(s)) {
            return getMostLikelyEntityTrackDistance(entity);
        }
        if ("server".equals(s)) {
            final int entityRange = WDLPluginChannels.getEntityRange(getEntityType(entity));
            if (entityRange >= 0) {
                return entityRange;
            }
            final int mostLikelyEntityTrackDistance = getMostLikelyEntityTrackDistance(entity);
            if (mostLikelyEntityTrackDistance < 0) {
                return WDLPluginChannels.getEntityRange(EntityList.getEntityString(entity));
            }
            return mostLikelyEntityTrackDistance;
        }
        else {
            if (!"user".equals(s)) {
                throw new IllegalArgumentException("Mode is not a valid mode: " + s);
            }
            final int intValue = Integer.valueOf(WDL.worldProps.getProperty("Entity." + getEntityType(entity) + ".TrackDistance", "-1"));
            if (intValue == -1) {
                return getEntityTrackDistance("server", entity);
            }
            return intValue;
        }
    }
    
    public static int getEntityTrackDistance(final String s) {
        return getEntityTrackDistance(getTrackDistanceMode(), s);
    }
    
    public static int getEntityTrackDistance(final String s, final String s2) {
        if ("default".equals(s)) {
            final int mostLikelyEntityTrackDistance = getMostLikelyEntityTrackDistance(s2);
            if (mostLikelyEntityTrackDistance < 0) {
                final Iterator<WDLApi.ModInfo> iterator = WDLApi.getImplementingExtensions(ISpecialEntityHandler.class).iterator();
                while (iterator.hasNext()) {
                    for (final Map.Entry<K, Object> entry : ((ISpecialEntityHandler)iterator.next().mod).getSpecialEntities().entries()) {
                        if (s2.equals(entry.getValue())) {
                            return getEntityTrackDistance(s, (String)entry.getKey());
                        }
                    }
                }
            }
            return mostLikelyEntityTrackDistance;
        }
        if ("server".equals(s)) {
            final int entityRange = WDLPluginChannels.getEntityRange(s2);
            if (entityRange < 0) {
                final int mostLikelyEntityTrackDistance2 = getMostLikelyEntityTrackDistance(s2);
                if (mostLikelyEntityTrackDistance2 >= 0) {
                    return mostLikelyEntityTrackDistance2;
                }
                final Iterator<WDLApi.ModInfo> iterator3 = WDLApi.getImplementingExtensions(ISpecialEntityHandler.class).iterator();
                while (iterator3.hasNext()) {
                    for (final Map.Entry<K, Object> entry2 : ((ISpecialEntityHandler)iterator3.next().mod).getSpecialEntities().entries()) {
                        if (s2.equals(entry2.getValue())) {
                            return getEntityTrackDistance(s, (String)entry2.getKey());
                        }
                    }
                }
            }
            return entityRange;
        }
        if (!"user".equals(s)) {
            throw new IllegalArgumentException("Mode is not a valid mode: " + s);
        }
        final int intValue = Integer.valueOf(WDL.worldProps.getProperty("Entity." + s2 + ".TrackDistance", "-1"));
        if (intValue == -1) {
            return getEntityTrackDistance("server", s2);
        }
        return intValue;
    }
    
    public static String getEntityGroup(final String s) {
        if (s == null) {
            return null;
        }
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(IEntityAdder.class)) {
            final List modEntities = ((IEntityAdder)modInfo.mod).getModEntities();
            if (modEntities == null) {
                EntityUtils.logger.warn(String.valueOf(modInfo.toString()) + " returned null for getModEntities()!");
            }
            else {
                if (modEntities.contains(s)) {
                    return ((IEntityAdder)modInfo.mod).getEntityCategory(s);
                }
                continue;
            }
        }
        for (final WDLApi.ModInfo modInfo2 : WDLApi.getImplementingExtensions(ISpecialEntityHandler.class)) {
            final Multimap specialEntities = ((ISpecialEntityHandler)modInfo2.mod).getSpecialEntities();
            if (specialEntities == null) {
                EntityUtils.logger.warn(String.valueOf(modInfo2.toString()) + " returned null for getSpecialEntities()!");
            }
            else {
                if (specialEntities.containsValue(s)) {
                    return ((ISpecialEntityHandler)modInfo2.mod).getSpecialEntityCategory(s);
                }
                continue;
            }
        }
        if (!EntityUtils.stringToClassMapping.containsKey(s)) {
            return null;
        }
        final Class<?> clazz = EntityUtils.stringToClassMapping.get(s);
        if (IMob.class.isAssignableFrom(clazz)) {
            return "Hostile";
        }
        if (IAnimals.class.isAssignableFrom(clazz)) {
            return "Passive";
        }
        return "Other";
    }
    
    public static boolean isEntityEnabled(final Entity entity) {
        return isEntityEnabled(getEntityType(entity));
    }
    
    public static boolean isEntityEnabled(final String s) {
        final boolean equals = WDL.worldProps.getProperty("EntityGroup." + getEntityGroup(s) + ".Enabled", "true").equals("true");
        final boolean equals2 = WDL.worldProps.getProperty("Entity." + s + ".Enabled", "true").equals("true");
        return equals && equals2;
    }
    
    public static String getEntityType(final Entity entity) {
        final String entityString = EntityList.getEntityString(entity);
        for (final WDLApi.ModInfo modInfo : WDLApi.getImplementingExtensions(ISpecialEntityHandler.class)) {
            if (((ISpecialEntityHandler)modInfo.mod).getSpecialEntities().containsKey(entityString)) {
                final String specialEntityName = ((ISpecialEntityHandler)modInfo.mod).getSpecialEntityName(entity);
                if (specialEntityName != null) {
                    return specialEntityName;
                }
                continue;
            }
        }
        return entityString;
    }
    
    public static int getMostLikelyEntityTrackDistance(final Entity entity) {
        if (WDL.isSpigot()) {
            return getDefaultSpigotEntityRange(entity.getClass());
        }
        return getDefaultEntityRange(getEntityType(entity));
    }
    
    public static int getMostLikelyEntityTrackDistance(final String s) {
        if (!WDL.isSpigot()) {
            return getDefaultEntityRange(s);
        }
        final Class clazz = EntityUtils.stringToClassMapping.get(s);
        if (clazz != null) {
            return getDefaultSpigotEntityRange(clazz);
        }
        return getDefaultEntityRange(s);
    }
    
    public static int getVanillaEntityRange(final String s) {
        return getVanillaEntityRange(EntityUtils.classToStringMapping.get(s));
    }
    
    public static String getTrackDistanceMode() {
        return WDL.worldProps.getProperty("Entity.TrackDistanceMode", "server");
    }
    
    public static int getVanillaEntityRange(final Class clazz) {
        if (clazz == null) {
            return -1;
        }
        if (EntityFishHook.class.isAssignableFrom(clazz) || EntityArrow.class.isAssignableFrom(clazz) || EntitySmallFireball.class.isAssignableFrom(clazz) || EntityFireball.class.isAssignableFrom(clazz) || EntitySnowball.class.isAssignableFrom(clazz) || EntityEnderPearl.class.isAssignableFrom(clazz) || EntityEnderEye.class.isAssignableFrom(clazz) || EntityEgg.class.isAssignableFrom(clazz) || EntityPotion.class.isAssignableFrom(clazz) || EntityExpBottle.class.isAssignableFrom(clazz) || EntityFireworkRocket.class.isAssignableFrom(clazz) || EntityItem.class.isAssignableFrom(clazz) || EntitySquid.class.isAssignableFrom(clazz)) {
            return 64;
        }
        if (EntityMinecart.class.isAssignableFrom(clazz) || EntityBoat.class.isAssignableFrom(clazz) || EntityWither.class.isAssignableFrom(clazz) || EntityBat.class.isAssignableFrom(clazz) || IAnimals.class.isAssignableFrom(clazz)) {
            return 80;
        }
        if (EntityDragon.class.isAssignableFrom(clazz) || EntityTNTPrimed.class.isAssignableFrom(clazz) || EntityFallingBlock.class.isAssignableFrom(clazz) || EntityHanging.class.isAssignableFrom(clazz) || EntityArmorStand.class.isAssignableFrom(clazz) || EntityXPOrb.class.isAssignableFrom(clazz)) {
            return 160;
        }
        if (EntityEnderCrystal.class.isAssignableFrom(clazz)) {
            return 256;
        }
        return -1;
    }
    
    public static int getDefaultSpigotEntityRange(final Class clazz) {
        if (EntityMob.class.isAssignableFrom(clazz) || EntitySlime.class.isAssignableFrom(clazz)) {
            return 48;
        }
        if (EntityCreature.class.isAssignableFrom(clazz) || EntityAmbientCreature.class.isAssignableFrom(clazz)) {
            return 48;
        }
        if (EntityItemFrame.class.isAssignableFrom(clazz) || EntityPainting.class.isAssignableFrom(clazz) || EntityItem.class.isAssignableFrom(clazz) || EntityXPOrb.class.isAssignableFrom(clazz)) {
            return 32;
        }
        return 64;
    }
}
