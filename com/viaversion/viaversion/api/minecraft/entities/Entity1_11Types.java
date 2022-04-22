package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.*;
import java.util.*;

public class Entity1_11Types
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
            Via.getPlatform().getLogger().severe("Could not find 1.11 type id " + n + " isObject=" + b);
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
        TIPPED_ARROW("TIPPED_ARROW", 6, 60, EntityType.ARROW), 
        SNOWBALL("SNOWBALL", 7, 61, EntityType.SNOWBALL), 
        EGG("EGG", 8, 62, EntityType.EGG), 
        FIREBALL("FIREBALL", 9, 63, EntityType.FIREBALL), 
        SMALL_FIREBALL("SMALL_FIREBALL", 10, 64, EntityType.SMALL_FIREBALL), 
        ENDER_PEARL("ENDER_PEARL", 11, 65, EntityType.ENDER_PEARL), 
        WITHER_SKULL("WITHER_SKULL", 12, 66, EntityType.WITHER_SKULL), 
        SHULKER_BULLET("SHULKER_BULLET", 13, 67, EntityType.SHULKER_BULLET), 
        LIAMA_SPIT("LIAMA_SPIT", 14, 68, EntityType.LIAMA_SPIT), 
        FALLING_BLOCK("FALLING_BLOCK", 15, 70, EntityType.FALLING_BLOCK), 
        ITEM_FRAME("ITEM_FRAME", 16, 71, EntityType.ITEM_FRAME), 
        ENDER_SIGNAL("ENDER_SIGNAL", 17, 72, EntityType.ENDER_SIGNAL), 
        POTION("POTION", 18, 73, EntityType.SPLASH_POTION), 
        THROWN_EXP_BOTTLE("THROWN_EXP_BOTTLE", 19, 75, EntityType.THROWN_EXP_BOTTLE), 
        FIREWORK("FIREWORK", 20, 76, EntityType.FIREWORK), 
        LEASH("LEASH", 21, 77, EntityType.LEASH_HITCH), 
        ARMOR_STAND("ARMOR_STAND", 22, 78, EntityType.ARMOR_STAND), 
        EVOCATION_FANGS("EVOCATION_FANGS", 23, 79, EntityType.EVOCATION_FANGS), 
        FISHIHNG_HOOK("FISHIHNG_HOOK", 24, 90, EntityType.FISHING_HOOK), 
        SPECTRAL_ARROW("SPECTRAL_ARROW", 25, 91, EntityType.SPECTRAL_ARROW), 
        DRAGON_FIREBALL("DRAGON_FIREBALL", 26, 93, EntityType.DRAGON_FIREBALL);
        
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
            $VALUES = new ObjectType[] { ObjectType.BOAT, ObjectType.ITEM, ObjectType.AREA_EFFECT_CLOUD, ObjectType.MINECART, ObjectType.TNT_PRIMED, ObjectType.ENDER_CRYSTAL, ObjectType.TIPPED_ARROW, ObjectType.SNOWBALL, ObjectType.EGG, ObjectType.FIREBALL, ObjectType.SMALL_FIREBALL, ObjectType.ENDER_PEARL, ObjectType.WITHER_SKULL, ObjectType.SHULKER_BULLET, ObjectType.LIAMA_SPIT, ObjectType.FALLING_BLOCK, ObjectType.ITEM_FRAME, ObjectType.ENDER_SIGNAL, ObjectType.POTION, ObjectType.THROWN_EXP_BOTTLE, ObjectType.FIREWORK, ObjectType.LEASH, ObjectType.ARMOR_STAND, ObjectType.EVOCATION_FANGS, ObjectType.FISHIHNG_HOOK, ObjectType.SPECTRAL_ARROW, ObjectType.DRAGON_FIREBALL };
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
        SPECTRAL_ARROW("SPECTRAL_ARROW", 17, 24, EntityType.ARROW), 
        SHULKER_BULLET("SHULKER_BULLET", 18, 25, EntityType.ENTITY), 
        DRAGON_FIREBALL("DRAGON_FIREBALL", 19, 26, EntityType.FIREBALL), 
        EVOCATION_FANGS("EVOCATION_FANGS", 20, 33, EntityType.ENTITY), 
        ENTITY_LIVING("ENTITY_LIVING", 21, -1, EntityType.ENTITY), 
        ENTITY_INSENTIENT("ENTITY_INSENTIENT", 22, -1, EntityType.ENTITY_LIVING), 
        ENTITY_AGEABLE("ENTITY_AGEABLE", 23, -1, EntityType.ENTITY_INSENTIENT), 
        ENTITY_TAMEABLE_ANIMAL("ENTITY_TAMEABLE_ANIMAL", 24, -1, EntityType.ENTITY_AGEABLE), 
        ENTITY_HUMAN("ENTITY_HUMAN", 25, -1, EntityType.ENTITY_LIVING), 
        ARMOR_STAND("ARMOR_STAND", 26, 30, EntityType.ENTITY_LIVING), 
        EVOCATION_ILLAGER("EVOCATION_ILLAGER", 27, 34, EntityType.ENTITY_INSENTIENT), 
        VEX("VEX", 28, 35, EntityType.ENTITY_INSENTIENT), 
        VINDICATION_ILLAGER("VINDICATION_ILLAGER", 29, 36, EntityType.ENTITY_INSENTIENT), 
        MINECART_ABSTRACT("MINECART_ABSTRACT", 30, -1, EntityType.ENTITY), 
        MINECART_COMMAND("MINECART_COMMAND", 31, 40, EntityType.MINECART_ABSTRACT), 
        BOAT("BOAT", 32, 41, EntityType.ENTITY), 
        MINECART_RIDEABLE("MINECART_RIDEABLE", 33, 42, EntityType.MINECART_ABSTRACT), 
        MINECART_CHEST("MINECART_CHEST", 34, 43, EntityType.MINECART_ABSTRACT), 
        MINECART_FURNACE("MINECART_FURNACE", 35, 44, EntityType.MINECART_ABSTRACT), 
        MINECART_TNT("MINECART_TNT", 36, 45, EntityType.MINECART_ABSTRACT), 
        MINECART_HOPPER("MINECART_HOPPER", 37, 46, EntityType.MINECART_ABSTRACT), 
        MINECART_MOB_SPAWNER("MINECART_MOB_SPAWNER", 38, 47, EntityType.MINECART_ABSTRACT), 
        CREEPER("CREEPER", 39, 50, EntityType.ENTITY_INSENTIENT), 
        ABSTRACT_SKELETON("ABSTRACT_SKELETON", 40, -1, EntityType.ENTITY_INSENTIENT), 
        SKELETON("SKELETON", 41, 51, EntityType.ABSTRACT_SKELETON), 
        WITHER_SKELETON("WITHER_SKELETON", 42, 5, EntityType.ABSTRACT_SKELETON), 
        STRAY("STRAY", 43, 6, EntityType.ABSTRACT_SKELETON), 
        SPIDER("SPIDER", 44, 52, EntityType.ENTITY_INSENTIENT), 
        GIANT("GIANT", 45, 53, EntityType.ENTITY_INSENTIENT), 
        ZOMBIE("ZOMBIE", 46, 54, EntityType.ENTITY_INSENTIENT), 
        HUSK("HUSK", 47, 23, EntityType.ZOMBIE), 
        ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 48, 27, EntityType.ZOMBIE), 
        SLIME("SLIME", 49, 55, EntityType.ENTITY_INSENTIENT), 
        GHAST("GHAST", 50, 56, EntityType.ENTITY_INSENTIENT), 
        PIG_ZOMBIE("PIG_ZOMBIE", 51, 57, EntityType.ZOMBIE), 
        ENDERMAN("ENDERMAN", 52, 58, EntityType.ENTITY_INSENTIENT), 
        CAVE_SPIDER("CAVE_SPIDER", 53, 59, EntityType.SPIDER), 
        SILVERFISH("SILVERFISH", 54, 60, EntityType.ENTITY_INSENTIENT), 
        BLAZE("BLAZE", 55, 61, EntityType.ENTITY_INSENTIENT), 
        MAGMA_CUBE("MAGMA_CUBE", 56, 62, EntityType.SLIME), 
        ENDER_DRAGON("ENDER_DRAGON", 57, 63, EntityType.ENTITY_INSENTIENT), 
        WITHER("WITHER", 58, 64, EntityType.ENTITY_INSENTIENT), 
        BAT("BAT", 59, 65, EntityType.ENTITY_INSENTIENT), 
        WITCH("WITCH", 60, 66, EntityType.ENTITY_INSENTIENT), 
        ENDERMITE("ENDERMITE", 61, 67, EntityType.ENTITY_INSENTIENT), 
        GUARDIAN("GUARDIAN", 62, 68, EntityType.ENTITY_INSENTIENT), 
        ELDER_GUARDIAN("ELDER_GUARDIAN", 63, 4, EntityType.GUARDIAN), 
        IRON_GOLEM("IRON_GOLEM", 64, 99, EntityType.ENTITY_INSENTIENT), 
        SHULKER("SHULKER", 65, 69, EntityType.IRON_GOLEM), 
        PIG("PIG", 66, 90, EntityType.ENTITY_AGEABLE), 
        SHEEP("SHEEP", 67, 91, EntityType.ENTITY_AGEABLE), 
        COW("COW", 68, 92, EntityType.ENTITY_AGEABLE), 
        CHICKEN("CHICKEN", 69, 93, EntityType.ENTITY_AGEABLE), 
        SQUID("SQUID", 70, 94, EntityType.ENTITY_INSENTIENT), 
        WOLF("WOLF", 71, 95, EntityType.ENTITY_TAMEABLE_ANIMAL), 
        MUSHROOM_COW("MUSHROOM_COW", 72, 96, EntityType.COW), 
        SNOWMAN("SNOWMAN", 73, 97, EntityType.IRON_GOLEM), 
        OCELOT("OCELOT", 74, 98, EntityType.ENTITY_TAMEABLE_ANIMAL), 
        ABSTRACT_HORSE("ABSTRACT_HORSE", 75, -1, EntityType.ENTITY_AGEABLE), 
        HORSE("HORSE", 76, 100, EntityType.ABSTRACT_HORSE), 
        SKELETON_HORSE("SKELETON_HORSE", 77, 28, EntityType.ABSTRACT_HORSE), 
        ZOMBIE_HORSE("ZOMBIE_HORSE", 78, 29, EntityType.ABSTRACT_HORSE), 
        CHESTED_HORSE("CHESTED_HORSE", 79, -1, EntityType.ABSTRACT_HORSE), 
        DONKEY("DONKEY", 80, 31, EntityType.CHESTED_HORSE), 
        MULE("MULE", 81, 32, EntityType.CHESTED_HORSE), 
        LIAMA("LIAMA", 82, 103, EntityType.CHESTED_HORSE), 
        RABBIT("RABBIT", 83, 101, EntityType.ENTITY_AGEABLE), 
        POLAR_BEAR("POLAR_BEAR", 84, 102, EntityType.ENTITY_AGEABLE), 
        VILLAGER("VILLAGER", 85, 120, EntityType.ENTITY_AGEABLE), 
        ENDER_CRYSTAL("ENDER_CRYSTAL", 86, 200, EntityType.ENTITY), 
        SPLASH_POTION("SPLASH_POTION", 87, -1, EntityType.ENTITY), 
        LINGERING_POTION("LINGERING_POTION", 88, -1, EntityType.SPLASH_POTION), 
        AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 89, -1, EntityType.ENTITY), 
        EGG("EGG", 90, -1, EntityType.ENTITY), 
        FISHING_HOOK("FISHING_HOOK", 91, -1, EntityType.ENTITY), 
        LIGHTNING("LIGHTNING", 92, -1, EntityType.ENTITY), 
        WEATHER("WEATHER", 93, -1, EntityType.ENTITY), 
        PLAYER("PLAYER", 94, -1, EntityType.ENTITY_HUMAN), 
        COMPLEX_PART("COMPLEX_PART", 95, -1, EntityType.ENTITY), 
        LIAMA_SPIT("LIAMA_SPIT", 96, -1, EntityType.ENTITY);
        
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
        
        @Override
        public int getId() {
            return this.id;
        }
        
        @Override
        public EntityType getParent() {
            return this.parent;
        }
        
        public static Optional findById(final int n) {
            if (n == -1) {
                return Optional.empty();
            }
            return Optional.ofNullable(EntityType.TYPES.get(n));
        }
        
        @Override
        public com.viaversion.viaversion.api.minecraft.entities.EntityType getParent() {
            return this.getParent();
        }
        
        static {
            $VALUES = new EntityType[] { EntityType.ENTITY, EntityType.DROPPED_ITEM, EntityType.EXPERIENCE_ORB, EntityType.LEASH_HITCH, EntityType.PAINTING, EntityType.ARROW, EntityType.SNOWBALL, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.ENDER_PEARL, EntityType.ENDER_SIGNAL, EntityType.THROWN_EXP_BOTTLE, EntityType.ITEM_FRAME, EntityType.WITHER_SKULL, EntityType.PRIMED_TNT, EntityType.FALLING_BLOCK, EntityType.FIREWORK, EntityType.SPECTRAL_ARROW, EntityType.SHULKER_BULLET, EntityType.DRAGON_FIREBALL, EntityType.EVOCATION_FANGS, EntityType.ENTITY_LIVING, EntityType.ENTITY_INSENTIENT, EntityType.ENTITY_AGEABLE, EntityType.ENTITY_TAMEABLE_ANIMAL, EntityType.ENTITY_HUMAN, EntityType.ARMOR_STAND, EntityType.EVOCATION_ILLAGER, EntityType.VEX, EntityType.VINDICATION_ILLAGER, EntityType.MINECART_ABSTRACT, EntityType.MINECART_COMMAND, EntityType.BOAT, EntityType.MINECART_RIDEABLE, EntityType.MINECART_CHEST, EntityType.MINECART_FURNACE, EntityType.MINECART_TNT, EntityType.MINECART_HOPPER, EntityType.MINECART_MOB_SPAWNER, EntityType.CREEPER, EntityType.ABSTRACT_SKELETON, EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.STRAY, EntityType.SPIDER, EntityType.GIANT, EntityType.ZOMBIE, EntityType.HUSK, EntityType.ZOMBIE_VILLAGER, EntityType.SLIME, EntityType.GHAST, EntityType.PIG_ZOMBIE, EntityType.ENDERMAN, EntityType.CAVE_SPIDER, EntityType.SILVERFISH, EntityType.BLAZE, EntityType.MAGMA_CUBE, EntityType.ENDER_DRAGON, EntityType.WITHER, EntityType.BAT, EntityType.WITCH, EntityType.ENDERMITE, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN, EntityType.IRON_GOLEM, EntityType.SHULKER, EntityType.PIG, EntityType.SHEEP, EntityType.COW, EntityType.CHICKEN, EntityType.SQUID, EntityType.WOLF, EntityType.MUSHROOM_COW, EntityType.SNOWMAN, EntityType.OCELOT, EntityType.ABSTRACT_HORSE, EntityType.HORSE, EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.CHESTED_HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.LIAMA, EntityType.RABBIT, EntityType.POLAR_BEAR, EntityType.VILLAGER, EntityType.ENDER_CRYSTAL, EntityType.SPLASH_POTION, EntityType.LINGERING_POTION, EntityType.AREA_EFFECT_CLOUD, EntityType.EGG, EntityType.FISHING_HOOK, EntityType.LIGHTNING, EntityType.WEATHER, EntityType.PLAYER, EntityType.COMPLEX_PART, EntityType.LIAMA_SPIT };
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
