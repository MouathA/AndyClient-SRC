package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.*;
import java.util.*;

public class Entity1_10Types
{
    public static EntityType getTypeFromId(final int n, final boolean b) {
        Optional optional;
        if (b) {
            optional = ObjectType.getPCEntity(n);
        }
        else {
            optional = EntityType.findById(n);
        }
        if (!optional.isPresent()) {
            Via.getPlatform().getLogger().severe("Could not find 1.10 type id " + n + " isObject=" + b);
            return EntityType.ENTITY;
        }
        return optional.get();
    }
    
    public enum ObjectType implements com.viaversion.viaversion.api.minecraft.entities.ObjectType
    {
        BOAT("BOAT", 0, 1, EntityType.BOAT), 
        ITEM("ITEM", 1, 2, EntityType.DROPPED_ITEM), 
        AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 2, 3, EntityType.AREA_EFFECT_CLOUD), 
        MINECART("MINECART", 3, 10, EntityType.MINECART_RIDEABLE), 
        TNT_PRIMED("TNT_PRIMED", 4, 50, EntityType.PRIMED_TNT), 
        ENDER_CRYSTAL("ENDER_CRYSTAL", 5, 51, EntityType.ENDER_CRYSTAL), 
        TIPPED_ARROW("TIPPED_ARROW", 6, 60, EntityType.TIPPED_ARROW), 
        SNOWBALL("SNOWBALL", 7, 61, EntityType.SNOWBALL), 
        EGG("EGG", 8, 62, EntityType.EGG), 
        FIREBALL("FIREBALL", 9, 63, EntityType.FIREBALL), 
        SMALL_FIREBALL("SMALL_FIREBALL", 10, 64, EntityType.SMALL_FIREBALL), 
        ENDER_PEARL("ENDER_PEARL", 11, 65, EntityType.ENDER_PEARL), 
        WITHER_SKULL("WITHER_SKULL", 12, 66, EntityType.WITHER_SKULL), 
        SHULKER_BULLET("SHULKER_BULLET", 13, 67, EntityType.SHULKER_BULLET), 
        FALLING_BLOCK("FALLING_BLOCK", 14, 70, EntityType.FALLING_BLOCK), 
        ITEM_FRAME("ITEM_FRAME", 15, 71, EntityType.ITEM_FRAME), 
        ENDER_SIGNAL("ENDER_SIGNAL", 16, 72, EntityType.ENDER_SIGNAL), 
        POTION("POTION", 17, 73, EntityType.SPLASH_POTION), 
        THROWN_EXP_BOTTLE("THROWN_EXP_BOTTLE", 18, 75, EntityType.THROWN_EXP_BOTTLE), 
        FIREWORK("FIREWORK", 19, 76, EntityType.FIREWORK), 
        LEASH("LEASH", 20, 77, EntityType.LEASH_HITCH), 
        ARMOR_STAND("ARMOR_STAND", 21, 78, EntityType.ARMOR_STAND), 
        FISHIHNG_HOOK("FISHIHNG_HOOK", 22, 90, EntityType.FISHING_HOOK), 
        SPECTRAL_ARROW("SPECTRAL_ARROW", 23, 91, EntityType.SPECTRAL_ARROW), 
        DRAGON_FIREBALL("DRAGON_FIREBALL", 24, 93, EntityType.DRAGON_FIREBALL);
        
        private static final Map TYPES;
        private final int id;
        private final EntityType type;
        private static final ObjectType[] $VALUES;
        
        private ObjectType(final String s, final int n, final int id, final EntityType type) {
            this.id = id;
            this.type = type;
        }
        
        @Override
        public int getId() {
            return this.id;
        }
        
        @Override
        public EntityType getType() {
            return this.type;
        }
        
        public static Optional findById(final int n) {
            if (n == -1) {
                return Optional.empty();
            }
            return Optional.ofNullable(ObjectType.TYPES.get(n));
        }
        
        public static Optional getPCEntity(final int n) {
            final Optional byId = findById(n);
            if (!byId.isPresent()) {
                return Optional.empty();
            }
            return Optional.of(byId.get().type);
        }
        
        @Override
        public com.viaversion.viaversion.api.minecraft.entities.EntityType getType() {
            return this.getType();
        }
        
        static {
            $VALUES = new ObjectType[] { ObjectType.BOAT, ObjectType.ITEM, ObjectType.AREA_EFFECT_CLOUD, ObjectType.MINECART, ObjectType.TNT_PRIMED, ObjectType.ENDER_CRYSTAL, ObjectType.TIPPED_ARROW, ObjectType.SNOWBALL, ObjectType.EGG, ObjectType.FIREBALL, ObjectType.SMALL_FIREBALL, ObjectType.ENDER_PEARL, ObjectType.WITHER_SKULL, ObjectType.SHULKER_BULLET, ObjectType.FALLING_BLOCK, ObjectType.ITEM_FRAME, ObjectType.ENDER_SIGNAL, ObjectType.POTION, ObjectType.THROWN_EXP_BOTTLE, ObjectType.FIREWORK, ObjectType.LEASH, ObjectType.ARMOR_STAND, ObjectType.FISHIHNG_HOOK, ObjectType.SPECTRAL_ARROW, ObjectType.DRAGON_FIREBALL };
            TYPES = new HashMap();
            final ObjectType[] values = values();
            while (0 < values.length) {
                final ObjectType objectType = values[0];
                ObjectType.TYPES.put(objectType.id, objectType);
                int n = 0;
                ++n;
            }
        }
    }
    
    public enum EntityType implements com.viaversion.viaversion.api.minecraft.entities.EntityType
    {
        ENTITY("ENTITY", 0, -1), 
        DROPPED_ITEM("DROPPED_ITEM", 1, 1, EntityType.ENTITY), 
        EXPERIENCE_ORB("EXPERIENCE_ORB", 2, 2, EntityType.ENTITY), 
        LEASH_HITCH("LEASH_HITCH", 3, 8, EntityType.ENTITY), 
        PAINTING("PAINTING", 4, 9, EntityType.ENTITY), 
        ARROW("ARROW", 5, 10, EntityType.ENTITY), 
        SNOWBALL("SNOWBALL", 6, 11, EntityType.ENTITY), 
        FIREBALL("FIREBALL", 7, 12, EntityType.ENTITY), 
        SMALL_FIREBALL("SMALL_FIREBALL", 8, 13, EntityType.ENTITY), 
        ENDER_PEARL("ENDER_PEARL", 9, 14, EntityType.ENTITY), 
        ENDER_SIGNAL("ENDER_SIGNAL", 10, 15, EntityType.ENTITY), 
        THROWN_EXP_BOTTLE("THROWN_EXP_BOTTLE", 11, 17, EntityType.ENTITY), 
        ITEM_FRAME("ITEM_FRAME", 12, 18, EntityType.ENTITY), 
        WITHER_SKULL("WITHER_SKULL", 13, 19, EntityType.ENTITY), 
        PRIMED_TNT("PRIMED_TNT", 14, 20, EntityType.ENTITY), 
        FALLING_BLOCK("FALLING_BLOCK", 15, 21, EntityType.ENTITY), 
        FIREWORK("FIREWORK", 16, 22, EntityType.ENTITY), 
        TIPPED_ARROW("TIPPED_ARROW", 17, 23, EntityType.ARROW), 
        SPECTRAL_ARROW("SPECTRAL_ARROW", 18, 24, EntityType.ARROW), 
        SHULKER_BULLET("SHULKER_BULLET", 19, 25, EntityType.ENTITY), 
        DRAGON_FIREBALL("DRAGON_FIREBALL", 20, 26, EntityType.FIREBALL), 
        ENTITY_LIVING("ENTITY_LIVING", 21, -1, EntityType.ENTITY), 
        ENTITY_INSENTIENT("ENTITY_INSENTIENT", 22, -1, EntityType.ENTITY_LIVING), 
        ENTITY_AGEABLE("ENTITY_AGEABLE", 23, -1, EntityType.ENTITY_INSENTIENT), 
        ENTITY_TAMEABLE_ANIMAL("ENTITY_TAMEABLE_ANIMAL", 24, -1, EntityType.ENTITY_AGEABLE), 
        ENTITY_HUMAN("ENTITY_HUMAN", 25, -1, EntityType.ENTITY_LIVING), 
        ARMOR_STAND("ARMOR_STAND", 26, 30, EntityType.ENTITY_LIVING), 
        MINECART_ABSTRACT("MINECART_ABSTRACT", 27, -1, EntityType.ENTITY), 
        MINECART_COMMAND("MINECART_COMMAND", 28, 40, EntityType.MINECART_ABSTRACT), 
        BOAT("BOAT", 29, 41, EntityType.ENTITY), 
        MINECART_RIDEABLE("MINECART_RIDEABLE", 30, 42, EntityType.MINECART_ABSTRACT), 
        MINECART_CHEST("MINECART_CHEST", 31, 43, EntityType.MINECART_ABSTRACT), 
        MINECART_FURNACE("MINECART_FURNACE", 32, 44, EntityType.MINECART_ABSTRACT), 
        MINECART_TNT("MINECART_TNT", 33, 45, EntityType.MINECART_ABSTRACT), 
        MINECART_HOPPER("MINECART_HOPPER", 34, 46, EntityType.MINECART_ABSTRACT), 
        MINECART_MOB_SPAWNER("MINECART_MOB_SPAWNER", 35, 47, EntityType.MINECART_ABSTRACT), 
        CREEPER("CREEPER", 36, 50, EntityType.ENTITY_INSENTIENT), 
        SKELETON("SKELETON", 37, 51, EntityType.ENTITY_INSENTIENT), 
        SPIDER("SPIDER", 38, 52, EntityType.ENTITY_INSENTIENT), 
        GIANT("GIANT", 39, 53, EntityType.ENTITY_INSENTIENT), 
        ZOMBIE("ZOMBIE", 40, 54, EntityType.ENTITY_INSENTIENT), 
        SLIME("SLIME", 41, 55, EntityType.ENTITY_INSENTIENT), 
        GHAST("GHAST", 42, 56, EntityType.ENTITY_INSENTIENT), 
        PIG_ZOMBIE("PIG_ZOMBIE", 43, 57, EntityType.ZOMBIE), 
        ENDERMAN("ENDERMAN", 44, 58, EntityType.ENTITY_INSENTIENT), 
        CAVE_SPIDER("CAVE_SPIDER", 45, 59, EntityType.SPIDER), 
        SILVERFISH("SILVERFISH", 46, 60, EntityType.ENTITY_INSENTIENT), 
        BLAZE("BLAZE", 47, 61, EntityType.ENTITY_INSENTIENT), 
        MAGMA_CUBE("MAGMA_CUBE", 48, 62, EntityType.SLIME), 
        ENDER_DRAGON("ENDER_DRAGON", 49, 63, EntityType.ENTITY_INSENTIENT), 
        WITHER("WITHER", 50, 64, EntityType.ENTITY_INSENTIENT), 
        BAT("BAT", 51, 65, EntityType.ENTITY_INSENTIENT), 
        WITCH("WITCH", 52, 66, EntityType.ENTITY_INSENTIENT), 
        ENDERMITE("ENDERMITE", 53, 67, EntityType.ENTITY_INSENTIENT), 
        GUARDIAN("GUARDIAN", 54, 68, EntityType.ENTITY_INSENTIENT), 
        IRON_GOLEM("IRON_GOLEM", 55, 99, EntityType.ENTITY_INSENTIENT), 
        SHULKER("SHULKER", 56, 69, EntityType.IRON_GOLEM), 
        PIG("PIG", 57, 90, EntityType.ENTITY_AGEABLE), 
        SHEEP("SHEEP", 58, 91, EntityType.ENTITY_AGEABLE), 
        COW("COW", 59, 92, EntityType.ENTITY_AGEABLE), 
        CHICKEN("CHICKEN", 60, 93, EntityType.ENTITY_AGEABLE), 
        SQUID("SQUID", 61, 94, EntityType.ENTITY_INSENTIENT), 
        WOLF("WOLF", 62, 95, EntityType.ENTITY_TAMEABLE_ANIMAL), 
        MUSHROOM_COW("MUSHROOM_COW", 63, 96, EntityType.COW), 
        SNOWMAN("SNOWMAN", 64, 97, EntityType.IRON_GOLEM), 
        OCELOT("OCELOT", 65, 98, EntityType.ENTITY_TAMEABLE_ANIMAL), 
        HORSE("HORSE", 66, 100, EntityType.ENTITY_AGEABLE), 
        RABBIT("RABBIT", 67, 101, EntityType.ENTITY_AGEABLE), 
        POLAR_BEAR("POLAR_BEAR", 68, 102, EntityType.ENTITY_AGEABLE), 
        VILLAGER("VILLAGER", 69, 120, EntityType.ENTITY_AGEABLE), 
        ENDER_CRYSTAL("ENDER_CRYSTAL", 70, 200, EntityType.ENTITY), 
        SPLASH_POTION("SPLASH_POTION", 71, -1, EntityType.ENTITY), 
        LINGERING_POTION("LINGERING_POTION", 72, -1, EntityType.SPLASH_POTION), 
        AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 73, -1, EntityType.ENTITY), 
        EGG("EGG", 74, -1, EntityType.ENTITY), 
        FISHING_HOOK("FISHING_HOOK", 75, -1, EntityType.ENTITY), 
        LIGHTNING("LIGHTNING", 76, -1, EntityType.ENTITY), 
        WEATHER("WEATHER", 77, -1, EntityType.ENTITY), 
        PLAYER("PLAYER", 78, -1, EntityType.ENTITY_HUMAN), 
        COMPLEX_PART("COMPLEX_PART", 79, -1, EntityType.ENTITY);
        
        private static final Map TYPES;
        private final int id;
        private final EntityType parent;
        private static final EntityType[] $VALUES;
        
        private EntityType(final String s, final int n, final int id) {
            this.id = id;
            this.parent = null;
        }
        
        private EntityType(final String s, final int n, final int id, final EntityType parent) {
            this.id = id;
            this.parent = parent;
        }
        
        public static Optional findById(final int n) {
            if (n == -1) {
                return Optional.empty();
            }
            return Optional.ofNullable(EntityType.TYPES.get(n));
        }
        
        @Override
        public int getId() {
            return this.id;
        }
        
        @Override
        public EntityType getParent() {
            return this.parent;
        }
        
        @Override
        public com.viaversion.viaversion.api.minecraft.entities.EntityType getParent() {
            return this.getParent();
        }
        
        static {
            $VALUES = new EntityType[] { EntityType.ENTITY, EntityType.DROPPED_ITEM, EntityType.EXPERIENCE_ORB, EntityType.LEASH_HITCH, EntityType.PAINTING, EntityType.ARROW, EntityType.SNOWBALL, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.ENDER_PEARL, EntityType.ENDER_SIGNAL, EntityType.THROWN_EXP_BOTTLE, EntityType.ITEM_FRAME, EntityType.WITHER_SKULL, EntityType.PRIMED_TNT, EntityType.FALLING_BLOCK, EntityType.FIREWORK, EntityType.TIPPED_ARROW, EntityType.SPECTRAL_ARROW, EntityType.SHULKER_BULLET, EntityType.DRAGON_FIREBALL, EntityType.ENTITY_LIVING, EntityType.ENTITY_INSENTIENT, EntityType.ENTITY_AGEABLE, EntityType.ENTITY_TAMEABLE_ANIMAL, EntityType.ENTITY_HUMAN, EntityType.ARMOR_STAND, EntityType.MINECART_ABSTRACT, EntityType.MINECART_COMMAND, EntityType.BOAT, EntityType.MINECART_RIDEABLE, EntityType.MINECART_CHEST, EntityType.MINECART_FURNACE, EntityType.MINECART_TNT, EntityType.MINECART_HOPPER, EntityType.MINECART_MOB_SPAWNER, EntityType.CREEPER, EntityType.SKELETON, EntityType.SPIDER, EntityType.GIANT, EntityType.ZOMBIE, EntityType.SLIME, EntityType.GHAST, EntityType.PIG_ZOMBIE, EntityType.ENDERMAN, EntityType.CAVE_SPIDER, EntityType.SILVERFISH, EntityType.BLAZE, EntityType.MAGMA_CUBE, EntityType.ENDER_DRAGON, EntityType.WITHER, EntityType.BAT, EntityType.WITCH, EntityType.ENDERMITE, EntityType.GUARDIAN, EntityType.IRON_GOLEM, EntityType.SHULKER, EntityType.PIG, EntityType.SHEEP, EntityType.COW, EntityType.CHICKEN, EntityType.SQUID, EntityType.WOLF, EntityType.MUSHROOM_COW, EntityType.SNOWMAN, EntityType.OCELOT, EntityType.HORSE, EntityType.RABBIT, EntityType.POLAR_BEAR, EntityType.VILLAGER, EntityType.ENDER_CRYSTAL, EntityType.SPLASH_POTION, EntityType.LINGERING_POTION, EntityType.AREA_EFFECT_CLOUD, EntityType.EGG, EntityType.FISHING_HOOK, EntityType.LIGHTNING, EntityType.WEATHER, EntityType.PLAYER, EntityType.COMPLEX_PART };
            TYPES = new HashMap();
            final EntityType[] values = values();
            while (0 < values.length) {
                final EntityType entityType = values[0];
                EntityType.TYPES.put(entityType.id, entityType);
                int n = 0;
                ++n;
            }
        }
    }
}
