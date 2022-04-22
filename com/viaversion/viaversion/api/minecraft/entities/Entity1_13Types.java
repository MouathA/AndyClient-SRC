package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.api.*;
import java.util.*;

public class Entity1_13Types
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
            Via.getPlatform().getLogger().severe("Could not find 1.13 type id " + n + " isObject=" + b);
            return EntityType.ENTITY;
        }
        return optional.get();
    }
    
    public enum ObjectType implements com.viaversion.viaversion.api.minecraft.entities.ObjectType
    {
        BOAT("BOAT", 0, 1, EntityType.BOAT), 
        ITEM("ITEM", 1, 2, EntityType.ITEM), 
        AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 2, 3, EntityType.AREA_EFFECT_CLOUD), 
        MINECART("MINECART", 3, 10, EntityType.MINECART), 
        TNT_PRIMED("TNT_PRIMED", 4, 50, EntityType.TNT), 
        ENDER_CRYSTAL("ENDER_CRYSTAL", 5, 51, EntityType.END_CRYSTAL), 
        TIPPED_ARROW("TIPPED_ARROW", 6, 60, EntityType.ARROW), 
        SNOWBALL("SNOWBALL", 7, 61, EntityType.SNOWBALL), 
        EGG("EGG", 8, 62, EntityType.EGG), 
        FIREBALL("FIREBALL", 9, 63, EntityType.FIREBALL), 
        SMALL_FIREBALL("SMALL_FIREBALL", 10, 64, EntityType.SMALL_FIREBALL), 
        ENDER_PEARL("ENDER_PEARL", 11, 65, EntityType.ENDER_PEARL), 
        WITHER_SKULL("WITHER_SKULL", 12, 66, EntityType.WITHER_SKULL), 
        SHULKER_BULLET("SHULKER_BULLET", 13, 67, EntityType.SHULKER_BULLET), 
        LLAMA_SPIT("LLAMA_SPIT", 14, 68, EntityType.LLAMA_SPIT), 
        FALLING_BLOCK("FALLING_BLOCK", 15, 70, EntityType.FALLING_BLOCK), 
        ITEM_FRAME("ITEM_FRAME", 16, 71, EntityType.ITEM_FRAME), 
        EYE_OF_ENDER("EYE_OF_ENDER", 17, 72, EntityType.EYE_OF_ENDER), 
        POTION("POTION", 18, 73, EntityType.POTION), 
        EXPERIENCE_BOTTLE("EXPERIENCE_BOTTLE", 19, 75, EntityType.EXPERIENCE_BOTTLE), 
        FIREWORK_ROCKET("FIREWORK_ROCKET", 20, 76, EntityType.FIREWORK_ROCKET), 
        LEASH("LEASH", 21, 77, EntityType.LEASH_KNOT), 
        ARMOR_STAND("ARMOR_STAND", 22, 78, EntityType.ARMOR_STAND), 
        EVOKER_FANGS("EVOKER_FANGS", 23, 79, EntityType.EVOKER_FANGS), 
        FISHIHNG_HOOK("FISHIHNG_HOOK", 24, 90, EntityType.FISHING_BOBBER), 
        SPECTRAL_ARROW("SPECTRAL_ARROW", 25, 91, EntityType.SPECTRAL_ARROW), 
        DRAGON_FIREBALL("DRAGON_FIREBALL", 26, 93, EntityType.DRAGON_FIREBALL), 
        TRIDENT("TRIDENT", 27, 94, EntityType.TRIDENT);
        
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
        
        public static Optional fromEntityType(final EntityType entityType) {
            final ObjectType[] values = values();
            while (0 < values.length) {
                final ObjectType objectType = values[0];
                if (objectType.type == entityType) {
                    return Optional.of(objectType);
                }
                int n = 0;
                ++n;
            }
            return Optional.empty();
        }
        
        @Override
        public com.viaversion.viaversion.api.minecraft.entities.EntityType getType() {
            return this.getType();
        }
        
        static {
            $VALUES = new ObjectType[] { ObjectType.BOAT, ObjectType.ITEM, ObjectType.AREA_EFFECT_CLOUD, ObjectType.MINECART, ObjectType.TNT_PRIMED, ObjectType.ENDER_CRYSTAL, ObjectType.TIPPED_ARROW, ObjectType.SNOWBALL, ObjectType.EGG, ObjectType.FIREBALL, ObjectType.SMALL_FIREBALL, ObjectType.ENDER_PEARL, ObjectType.WITHER_SKULL, ObjectType.SHULKER_BULLET, ObjectType.LLAMA_SPIT, ObjectType.FALLING_BLOCK, ObjectType.ITEM_FRAME, ObjectType.EYE_OF_ENDER, ObjectType.POTION, ObjectType.EXPERIENCE_BOTTLE, ObjectType.FIREWORK_ROCKET, ObjectType.LEASH, ObjectType.ARMOR_STAND, ObjectType.EVOKER_FANGS, ObjectType.FISHIHNG_HOOK, ObjectType.SPECTRAL_ARROW, ObjectType.DRAGON_FIREBALL, ObjectType.TRIDENT };
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
        AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 1, 0, EntityType.ENTITY), 
        END_CRYSTAL("END_CRYSTAL", 2, 16, EntityType.ENTITY), 
        EVOKER_FANGS("EVOKER_FANGS", 3, 20, EntityType.ENTITY), 
        EXPERIENCE_ORB("EXPERIENCE_ORB", 4, 22, EntityType.ENTITY), 
        EYE_OF_ENDER("EYE_OF_ENDER", 5, 23, EntityType.ENTITY), 
        FALLING_BLOCK("FALLING_BLOCK", 6, 24, EntityType.ENTITY), 
        FIREWORK_ROCKET("FIREWORK_ROCKET", 7, 25, EntityType.ENTITY), 
        ITEM("ITEM", 8, 32, EntityType.ENTITY), 
        LLAMA_SPIT("LLAMA_SPIT", 9, 37, EntityType.ENTITY), 
        TNT("TNT", 10, 55, EntityType.ENTITY), 
        SHULKER_BULLET("SHULKER_BULLET", 11, 60, EntityType.ENTITY), 
        FISHING_BOBBER("FISHING_BOBBER", 12, 93, EntityType.ENTITY), 
        LIVINGENTITY("LIVINGENTITY", 13, -1, EntityType.ENTITY), 
        ARMOR_STAND("ARMOR_STAND", 14, 1, EntityType.LIVINGENTITY), 
        PLAYER("PLAYER", 15, 92, EntityType.LIVINGENTITY), 
        ABSTRACT_INSENTIENT("ABSTRACT_INSENTIENT", 16, -1, EntityType.LIVINGENTITY), 
        ENDER_DRAGON("ENDER_DRAGON", 17, 17, EntityType.ABSTRACT_INSENTIENT), 
        ABSTRACT_CREATURE("ABSTRACT_CREATURE", 18, -1, EntityType.ABSTRACT_INSENTIENT), 
        ABSTRACT_AGEABLE("ABSTRACT_AGEABLE", 19, -1, EntityType.ABSTRACT_CREATURE), 
        VILLAGER("VILLAGER", 20, 79, EntityType.ABSTRACT_AGEABLE), 
        ABSTRACT_ANIMAL("ABSTRACT_ANIMAL", 21, -1, EntityType.ABSTRACT_AGEABLE), 
        CHICKEN("CHICKEN", 22, 7, EntityType.ABSTRACT_ANIMAL), 
        COW("COW", 23, 9, EntityType.ABSTRACT_ANIMAL), 
        MOOSHROOM("MOOSHROOM", 24, 47, EntityType.COW), 
        PIG("PIG", 25, 51, EntityType.ABSTRACT_ANIMAL), 
        POLAR_BEAR("POLAR_BEAR", 26, 54, EntityType.ABSTRACT_ANIMAL), 
        RABBIT("RABBIT", 27, 56, EntityType.ABSTRACT_ANIMAL), 
        SHEEP("SHEEP", 28, 58, EntityType.ABSTRACT_ANIMAL), 
        TURTLE("TURTLE", 29, 73, EntityType.ABSTRACT_ANIMAL), 
        ABSTRACT_TAMEABLE_ANIMAL("ABSTRACT_TAMEABLE_ANIMAL", 30, -1, EntityType.ABSTRACT_ANIMAL), 
        OCELOT("OCELOT", 31, 48, EntityType.ABSTRACT_TAMEABLE_ANIMAL), 
        WOLF("WOLF", 32, 86, EntityType.ABSTRACT_TAMEABLE_ANIMAL), 
        ABSTRACT_PARROT("ABSTRACT_PARROT", 33, -1, EntityType.ABSTRACT_TAMEABLE_ANIMAL), 
        PARROT("PARROT", 34, 50, EntityType.ABSTRACT_PARROT), 
        ABSTRACT_HORSE("ABSTRACT_HORSE", 35, -1, EntityType.ABSTRACT_ANIMAL), 
        CHESTED_HORSE("CHESTED_HORSE", 36, -1, EntityType.ABSTRACT_HORSE), 
        DONKEY("DONKEY", 37, 11, EntityType.CHESTED_HORSE), 
        MULE("MULE", 38, 46, EntityType.CHESTED_HORSE), 
        LLAMA("LLAMA", 39, 36, EntityType.CHESTED_HORSE), 
        HORSE("HORSE", 40, 29, EntityType.ABSTRACT_HORSE), 
        SKELETON_HORSE("SKELETON_HORSE", 41, 63, EntityType.ABSTRACT_HORSE), 
        ZOMBIE_HORSE("ZOMBIE_HORSE", 42, 88, EntityType.ABSTRACT_HORSE), 
        ABSTRACT_GOLEM("ABSTRACT_GOLEM", 43, -1, EntityType.ABSTRACT_CREATURE), 
        SNOW_GOLEM("SNOW_GOLEM", 44, 66, EntityType.ABSTRACT_GOLEM), 
        IRON_GOLEM("IRON_GOLEM", 45, 80, EntityType.ABSTRACT_GOLEM), 
        SHULKER("SHULKER", 46, 59, EntityType.ABSTRACT_GOLEM), 
        ABSTRACT_FISHES("ABSTRACT_FISHES", 47, -1, EntityType.ABSTRACT_CREATURE), 
        COD("COD", 48, 8, EntityType.ABSTRACT_FISHES), 
        PUFFERFISH("PUFFERFISH", 49, 52, EntityType.ABSTRACT_FISHES), 
        SALMON("SALMON", 50, 57, EntityType.ABSTRACT_FISHES), 
        TROPICAL_FISH("TROPICAL_FISH", 51, 72, EntityType.ABSTRACT_FISHES), 
        ABSTRACT_MONSTER("ABSTRACT_MONSTER", 52, -1, EntityType.ABSTRACT_CREATURE), 
        BLAZE("BLAZE", 53, 4, EntityType.ABSTRACT_MONSTER), 
        CREEPER("CREEPER", 54, 10, EntityType.ABSTRACT_MONSTER), 
        ENDERMITE("ENDERMITE", 55, 19, EntityType.ABSTRACT_MONSTER), 
        ENDERMAN("ENDERMAN", 56, 18, EntityType.ABSTRACT_MONSTER), 
        GIANT("GIANT", 57, 27, EntityType.ABSTRACT_MONSTER), 
        SILVERFISH("SILVERFISH", 58, 61, EntityType.ABSTRACT_MONSTER), 
        VEX("VEX", 59, 78, EntityType.ABSTRACT_MONSTER), 
        WITCH("WITCH", 60, 82, EntityType.ABSTRACT_MONSTER), 
        WITHER("WITHER", 61, 83, EntityType.ABSTRACT_MONSTER), 
        ABSTRACT_ILLAGER_BASE("ABSTRACT_ILLAGER_BASE", 62, -1, EntityType.ABSTRACT_MONSTER), 
        ABSTRACT_EVO_ILLU_ILLAGER("ABSTRACT_EVO_ILLU_ILLAGER", 63, -1, EntityType.ABSTRACT_ILLAGER_BASE), 
        EVOKER("EVOKER", 64, 21, EntityType.ABSTRACT_EVO_ILLU_ILLAGER), 
        ILLUSIONER("ILLUSIONER", 65, 31, EntityType.ABSTRACT_EVO_ILLU_ILLAGER), 
        VINDICATOR("VINDICATOR", 66, 81, EntityType.ABSTRACT_ILLAGER_BASE), 
        ABSTRACT_SKELETON("ABSTRACT_SKELETON", 67, -1, EntityType.ABSTRACT_MONSTER), 
        SKELETON("SKELETON", 68, 62, EntityType.ABSTRACT_SKELETON), 
        STRAY("STRAY", 69, 71, EntityType.ABSTRACT_SKELETON), 
        WITHER_SKELETON("WITHER_SKELETON", 70, 84, EntityType.ABSTRACT_SKELETON), 
        GUARDIAN("GUARDIAN", 71, 28, EntityType.ABSTRACT_MONSTER), 
        ELDER_GUARDIAN("ELDER_GUARDIAN", 72, 15, EntityType.GUARDIAN), 
        SPIDER("SPIDER", 73, 69, EntityType.ABSTRACT_MONSTER), 
        CAVE_SPIDER("CAVE_SPIDER", 74, 6, EntityType.SPIDER), 
        ZOMBIE("ZOMBIE", 75, 87, EntityType.ABSTRACT_MONSTER), 
        DROWNED("DROWNED", 76, 14, EntityType.ZOMBIE), 
        HUSK("HUSK", 77, 30, EntityType.ZOMBIE), 
        ZOMBIE_PIGMAN("ZOMBIE_PIGMAN", 78, 53, EntityType.ZOMBIE), 
        ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 79, 89, EntityType.ZOMBIE), 
        ABSTRACT_FLYING("ABSTRACT_FLYING", 80, -1, EntityType.ABSTRACT_INSENTIENT), 
        GHAST("GHAST", 81, 26, EntityType.ABSTRACT_FLYING), 
        PHANTOM("PHANTOM", 82, 90, EntityType.ABSTRACT_FLYING), 
        ABSTRACT_AMBIENT("ABSTRACT_AMBIENT", 83, -1, EntityType.ABSTRACT_INSENTIENT), 
        BAT("BAT", 84, 3, EntityType.ABSTRACT_AMBIENT), 
        ABSTRACT_WATERMOB("ABSTRACT_WATERMOB", 85, -1, EntityType.ABSTRACT_INSENTIENT), 
        SQUID("SQUID", 86, 70, EntityType.ABSTRACT_WATERMOB), 
        DOLPHIN("DOLPHIN", 87, 12, EntityType.ABSTRACT_WATERMOB), 
        SLIME("SLIME", 88, 64, EntityType.ABSTRACT_INSENTIENT), 
        MAGMA_CUBE("MAGMA_CUBE", 89, 38, EntityType.SLIME), 
        ABSTRACT_HANGING("ABSTRACT_HANGING", 90, -1, EntityType.ENTITY), 
        LEASH_KNOT("LEASH_KNOT", 91, 35, EntityType.ABSTRACT_HANGING), 
        ITEM_FRAME("ITEM_FRAME", 92, 33, EntityType.ABSTRACT_HANGING), 
        PAINTING("PAINTING", 93, 49, EntityType.ABSTRACT_HANGING), 
        ABSTRACT_LIGHTNING("ABSTRACT_LIGHTNING", 94, -1, EntityType.ENTITY), 
        LIGHTNING_BOLT("LIGHTNING_BOLT", 95, 91, EntityType.ABSTRACT_LIGHTNING), 
        ABSTRACT_ARROW("ABSTRACT_ARROW", 96, -1, EntityType.ENTITY), 
        ARROW("ARROW", 97, 2, EntityType.ABSTRACT_ARROW), 
        SPECTRAL_ARROW("SPECTRAL_ARROW", 98, 68, EntityType.ABSTRACT_ARROW), 
        TRIDENT("TRIDENT", 99, 94, EntityType.ABSTRACT_ARROW), 
        ABSTRACT_FIREBALL("ABSTRACT_FIREBALL", 100, -1, EntityType.ENTITY), 
        DRAGON_FIREBALL("DRAGON_FIREBALL", 101, 13, EntityType.ABSTRACT_FIREBALL), 
        FIREBALL("FIREBALL", 102, 34, EntityType.ABSTRACT_FIREBALL), 
        SMALL_FIREBALL("SMALL_FIREBALL", 103, 65, EntityType.ABSTRACT_FIREBALL), 
        WITHER_SKULL("WITHER_SKULL", 104, 85, EntityType.ABSTRACT_FIREBALL), 
        PROJECTILE_ABSTRACT("PROJECTILE_ABSTRACT", 105, -1, EntityType.ENTITY), 
        SNOWBALL("SNOWBALL", 106, 67, EntityType.PROJECTILE_ABSTRACT), 
        ENDER_PEARL("ENDER_PEARL", 107, 75, EntityType.PROJECTILE_ABSTRACT), 
        EGG("EGG", 108, 74, EntityType.PROJECTILE_ABSTRACT), 
        POTION("POTION", 109, 77, EntityType.PROJECTILE_ABSTRACT), 
        EXPERIENCE_BOTTLE("EXPERIENCE_BOTTLE", 110, 76, EntityType.PROJECTILE_ABSTRACT), 
        MINECART_ABSTRACT("MINECART_ABSTRACT", 111, -1, EntityType.ENTITY), 
        CHESTED_MINECART_ABSTRACT("CHESTED_MINECART_ABSTRACT", 112, -1, EntityType.MINECART_ABSTRACT), 
        CHEST_MINECART("CHEST_MINECART", 113, 40, EntityType.CHESTED_MINECART_ABSTRACT), 
        HOPPER_MINECART("HOPPER_MINECART", 114, 43, EntityType.CHESTED_MINECART_ABSTRACT), 
        MINECART("MINECART", 115, 39, EntityType.MINECART_ABSTRACT), 
        FURNACE_MINECART("FURNACE_MINECART", 116, 42, EntityType.MINECART_ABSTRACT), 
        COMMAND_BLOCK_MINECART("COMMAND_BLOCK_MINECART", 117, 41, EntityType.MINECART_ABSTRACT), 
        TNT_MINECART("TNT_MINECART", 118, 45, EntityType.MINECART_ABSTRACT), 
        SPAWNER_MINECART("SPAWNER_MINECART", 119, 44, EntityType.MINECART_ABSTRACT), 
        BOAT("BOAT", 120, 5, EntityType.ENTITY);
        
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
            $VALUES = new EntityType[] { EntityType.ENTITY, EntityType.AREA_EFFECT_CLOUD, EntityType.END_CRYSTAL, EntityType.EVOKER_FANGS, EntityType.EXPERIENCE_ORB, EntityType.EYE_OF_ENDER, EntityType.FALLING_BLOCK, EntityType.FIREWORK_ROCKET, EntityType.ITEM, EntityType.LLAMA_SPIT, EntityType.TNT, EntityType.SHULKER_BULLET, EntityType.FISHING_BOBBER, EntityType.LIVINGENTITY, EntityType.ARMOR_STAND, EntityType.PLAYER, EntityType.ABSTRACT_INSENTIENT, EntityType.ENDER_DRAGON, EntityType.ABSTRACT_CREATURE, EntityType.ABSTRACT_AGEABLE, EntityType.VILLAGER, EntityType.ABSTRACT_ANIMAL, EntityType.CHICKEN, EntityType.COW, EntityType.MOOSHROOM, EntityType.PIG, EntityType.POLAR_BEAR, EntityType.RABBIT, EntityType.SHEEP, EntityType.TURTLE, EntityType.ABSTRACT_TAMEABLE_ANIMAL, EntityType.OCELOT, EntityType.WOLF, EntityType.ABSTRACT_PARROT, EntityType.PARROT, EntityType.ABSTRACT_HORSE, EntityType.CHESTED_HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.LLAMA, EntityType.HORSE, EntityType.SKELETON_HORSE, EntityType.ZOMBIE_HORSE, EntityType.ABSTRACT_GOLEM, EntityType.SNOW_GOLEM, EntityType.IRON_GOLEM, EntityType.SHULKER, EntityType.ABSTRACT_FISHES, EntityType.COD, EntityType.PUFFERFISH, EntityType.SALMON, EntityType.TROPICAL_FISH, EntityType.ABSTRACT_MONSTER, EntityType.BLAZE, EntityType.CREEPER, EntityType.ENDERMITE, EntityType.ENDERMAN, EntityType.GIANT, EntityType.SILVERFISH, EntityType.VEX, EntityType.WITCH, EntityType.WITHER, EntityType.ABSTRACT_ILLAGER_BASE, EntityType.ABSTRACT_EVO_ILLU_ILLAGER, EntityType.EVOKER, EntityType.ILLUSIONER, EntityType.VINDICATOR, EntityType.ABSTRACT_SKELETON, EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN, EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.ZOMBIE, EntityType.DROWNED, EntityType.HUSK, EntityType.ZOMBIE_PIGMAN, EntityType.ZOMBIE_VILLAGER, EntityType.ABSTRACT_FLYING, EntityType.GHAST, EntityType.PHANTOM, EntityType.ABSTRACT_AMBIENT, EntityType.BAT, EntityType.ABSTRACT_WATERMOB, EntityType.SQUID, EntityType.DOLPHIN, EntityType.SLIME, EntityType.MAGMA_CUBE, EntityType.ABSTRACT_HANGING, EntityType.LEASH_KNOT, EntityType.ITEM_FRAME, EntityType.PAINTING, EntityType.ABSTRACT_LIGHTNING, EntityType.LIGHTNING_BOLT, EntityType.ABSTRACT_ARROW, EntityType.ARROW, EntityType.SPECTRAL_ARROW, EntityType.TRIDENT, EntityType.ABSTRACT_FIREBALL, EntityType.DRAGON_FIREBALL, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.WITHER_SKULL, EntityType.PROJECTILE_ABSTRACT, EntityType.SNOWBALL, EntityType.ENDER_PEARL, EntityType.EGG, EntityType.POTION, EntityType.EXPERIENCE_BOTTLE, EntityType.MINECART_ABSTRACT, EntityType.CHESTED_MINECART_ABSTRACT, EntityType.CHEST_MINECART, EntityType.HOPPER_MINECART, EntityType.MINECART, EntityType.FURNACE_MINECART, EntityType.COMMAND_BLOCK_MINECART, EntityType.TNT_MINECART, EntityType.SPAWNER_MINECART, EntityType.BOAT };
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
