package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.util.*;

public enum Entity1_14Types implements EntityType
{
    ENTITY("ENTITY", 0, -1), 
    AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 1, 0, (EntityType)Entity1_14Types.ENTITY), 
    END_CRYSTAL("END_CRYSTAL", 2, 17, (EntityType)Entity1_14Types.ENTITY), 
    EVOKER_FANGS("EVOKER_FANGS", 3, 21, (EntityType)Entity1_14Types.ENTITY), 
    EXPERIENCE_ORB("EXPERIENCE_ORB", 4, 23, (EntityType)Entity1_14Types.ENTITY), 
    EYE_OF_ENDER("EYE_OF_ENDER", 5, 24, (EntityType)Entity1_14Types.ENTITY), 
    FALLING_BLOCK("FALLING_BLOCK", 6, 25, (EntityType)Entity1_14Types.ENTITY), 
    FIREWORK_ROCKET("FIREWORK_ROCKET", 7, 26, (EntityType)Entity1_14Types.ENTITY), 
    ITEM("ITEM", 8, 34, (EntityType)Entity1_14Types.ENTITY), 
    LLAMA_SPIT("LLAMA_SPIT", 9, 39, (EntityType)Entity1_14Types.ENTITY), 
    TNT("TNT", 10, 58, (EntityType)Entity1_14Types.ENTITY), 
    SHULKER_BULLET("SHULKER_BULLET", 11, 63, (EntityType)Entity1_14Types.ENTITY), 
    FISHING_BOBBER("FISHING_BOBBER", 12, 101, (EntityType)Entity1_14Types.ENTITY), 
    LIVINGENTITY("LIVINGENTITY", 13, -1, (EntityType)Entity1_14Types.ENTITY), 
    ARMOR_STAND("ARMOR_STAND", 14, 1, (EntityType)Entity1_14Types.LIVINGENTITY), 
    PLAYER("PLAYER", 15, 100, (EntityType)Entity1_14Types.LIVINGENTITY), 
    ABSTRACT_INSENTIENT("ABSTRACT_INSENTIENT", 16, -1, (EntityType)Entity1_14Types.LIVINGENTITY), 
    ENDER_DRAGON("ENDER_DRAGON", 17, 18, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_CREATURE("ABSTRACT_CREATURE", 18, -1, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_AGEABLE("ABSTRACT_AGEABLE", 19, -1, (EntityType)Entity1_14Types.ABSTRACT_CREATURE), 
    VILLAGER("VILLAGER", 20, 84, (EntityType)Entity1_14Types.ABSTRACT_AGEABLE), 
    WANDERING_TRADER("WANDERING_TRADER", 21, 88, (EntityType)Entity1_14Types.ABSTRACT_AGEABLE), 
    ABSTRACT_ANIMAL("ABSTRACT_ANIMAL", 22, -1, (EntityType)Entity1_14Types.ABSTRACT_AGEABLE), 
    DOLPHIN("DOLPHIN", 23, 13, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    CHICKEN("CHICKEN", 24, 8, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    COW("COW", 25, 10, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    MOOSHROOM("MOOSHROOM", 26, 49, (EntityType)Entity1_14Types.COW), 
    PANDA("PANDA", 27, 52, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    PIG("PIG", 28, 54, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    POLAR_BEAR("POLAR_BEAR", 29, 57, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    RABBIT("RABBIT", 30, 59, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    SHEEP("SHEEP", 31, 61, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    TURTLE("TURTLE", 32, 77, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    FOX("FOX", 33, 27, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    ABSTRACT_TAMEABLE_ANIMAL("ABSTRACT_TAMEABLE_ANIMAL", 34, -1, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    CAT("CAT", 35, 6, (EntityType)Entity1_14Types.ABSTRACT_TAMEABLE_ANIMAL), 
    OCELOT("OCELOT", 36, 50, (EntityType)Entity1_14Types.ABSTRACT_TAMEABLE_ANIMAL), 
    WOLF("WOLF", 37, 93, (EntityType)Entity1_14Types.ABSTRACT_TAMEABLE_ANIMAL), 
    ABSTRACT_PARROT("ABSTRACT_PARROT", 38, -1, (EntityType)Entity1_14Types.ABSTRACT_TAMEABLE_ANIMAL), 
    PARROT("PARROT", 39, 53, (EntityType)Entity1_14Types.ABSTRACT_PARROT), 
    ABSTRACT_HORSE("ABSTRACT_HORSE", 40, -1, (EntityType)Entity1_14Types.ABSTRACT_ANIMAL), 
    CHESTED_HORSE("CHESTED_HORSE", 41, -1, (EntityType)Entity1_14Types.ABSTRACT_HORSE), 
    DONKEY("DONKEY", 42, 12, (EntityType)Entity1_14Types.CHESTED_HORSE), 
    MULE("MULE", 43, 48, (EntityType)Entity1_14Types.CHESTED_HORSE), 
    LLAMA("LLAMA", 44, 38, (EntityType)Entity1_14Types.CHESTED_HORSE), 
    TRADER_LLAMA("TRADER_LLAMA", 45, 75, (EntityType)Entity1_14Types.CHESTED_HORSE), 
    HORSE("HORSE", 46, 31, (EntityType)Entity1_14Types.ABSTRACT_HORSE), 
    SKELETON_HORSE("SKELETON_HORSE", 47, 66, (EntityType)Entity1_14Types.ABSTRACT_HORSE), 
    ZOMBIE_HORSE("ZOMBIE_HORSE", 48, 95, (EntityType)Entity1_14Types.ABSTRACT_HORSE), 
    ABSTRACT_GOLEM("ABSTRACT_GOLEM", 49, -1, (EntityType)Entity1_14Types.ABSTRACT_CREATURE), 
    SNOW_GOLEM("SNOW_GOLEM", 50, 69, (EntityType)Entity1_14Types.ABSTRACT_GOLEM), 
    IRON_GOLEM("IRON_GOLEM", 51, 85, (EntityType)Entity1_14Types.ABSTRACT_GOLEM), 
    SHULKER("SHULKER", 52, 62, (EntityType)Entity1_14Types.ABSTRACT_GOLEM), 
    ABSTRACT_FISHES("ABSTRACT_FISHES", 53, -1, (EntityType)Entity1_14Types.ABSTRACT_CREATURE), 
    COD("COD", 54, 9, (EntityType)Entity1_14Types.ABSTRACT_FISHES), 
    PUFFERFISH("PUFFERFISH", 55, 55, (EntityType)Entity1_14Types.ABSTRACT_FISHES), 
    SALMON("SALMON", 56, 60, (EntityType)Entity1_14Types.ABSTRACT_FISHES), 
    TROPICAL_FISH("TROPICAL_FISH", 57, 76, (EntityType)Entity1_14Types.ABSTRACT_FISHES), 
    ABSTRACT_MONSTER("ABSTRACT_MONSTER", 58, -1, (EntityType)Entity1_14Types.ABSTRACT_CREATURE), 
    BLAZE("BLAZE", 59, 4, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    CREEPER("CREEPER", 60, 11, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    ENDERMITE("ENDERMITE", 61, 20, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    ENDERMAN("ENDERMAN", 62, 19, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    GIANT("GIANT", 63, 29, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    SILVERFISH("SILVERFISH", 64, 64, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    VEX("VEX", 65, 83, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    WITCH("WITCH", 66, 89, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    WITHER("WITHER", 67, 90, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    RAVAGER("RAVAGER", 68, 98, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    ABSTRACT_ILLAGER_BASE("ABSTRACT_ILLAGER_BASE", 69, -1, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    ABSTRACT_EVO_ILLU_ILLAGER("ABSTRACT_EVO_ILLU_ILLAGER", 70, -1, (EntityType)Entity1_14Types.ABSTRACT_ILLAGER_BASE), 
    EVOKER("EVOKER", 71, 22, (EntityType)Entity1_14Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    ILLUSIONER("ILLUSIONER", 72, 33, (EntityType)Entity1_14Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    VINDICATOR("VINDICATOR", 73, 86, (EntityType)Entity1_14Types.ABSTRACT_ILLAGER_BASE), 
    PILLAGER("PILLAGER", 74, 87, (EntityType)Entity1_14Types.ABSTRACT_ILLAGER_BASE), 
    ABSTRACT_SKELETON("ABSTRACT_SKELETON", 75, -1, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    SKELETON("SKELETON", 76, 65, (EntityType)Entity1_14Types.ABSTRACT_SKELETON), 
    STRAY("STRAY", 77, 74, (EntityType)Entity1_14Types.ABSTRACT_SKELETON), 
    WITHER_SKELETON("WITHER_SKELETON", 78, 91, (EntityType)Entity1_14Types.ABSTRACT_SKELETON), 
    GUARDIAN("GUARDIAN", 79, 30, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    ELDER_GUARDIAN("ELDER_GUARDIAN", 80, 16, (EntityType)Entity1_14Types.GUARDIAN), 
    SPIDER("SPIDER", 81, 72, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    CAVE_SPIDER("CAVE_SPIDER", 82, 7, (EntityType)Entity1_14Types.SPIDER), 
    ZOMBIE("ZOMBIE", 83, 94, (EntityType)Entity1_14Types.ABSTRACT_MONSTER), 
    DROWNED("DROWNED", 84, 15, (EntityType)Entity1_14Types.ZOMBIE), 
    HUSK("HUSK", 85, 32, (EntityType)Entity1_14Types.ZOMBIE), 
    ZOMBIE_PIGMAN("ZOMBIE_PIGMAN", 86, 56, (EntityType)Entity1_14Types.ZOMBIE), 
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 87, 96, (EntityType)Entity1_14Types.ZOMBIE), 
    ABSTRACT_FLYING("ABSTRACT_FLYING", 88, -1, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    GHAST("GHAST", 89, 28, (EntityType)Entity1_14Types.ABSTRACT_FLYING), 
    PHANTOM("PHANTOM", 90, 97, (EntityType)Entity1_14Types.ABSTRACT_FLYING), 
    ABSTRACT_AMBIENT("ABSTRACT_AMBIENT", 91, -1, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    BAT("BAT", 92, 3, (EntityType)Entity1_14Types.ABSTRACT_AMBIENT), 
    ABSTRACT_WATERMOB("ABSTRACT_WATERMOB", 93, -1, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    SQUID("SQUID", 94, 73, (EntityType)Entity1_14Types.ABSTRACT_WATERMOB), 
    SLIME("SLIME", 95, 67, (EntityType)Entity1_14Types.ABSTRACT_INSENTIENT), 
    MAGMA_CUBE("MAGMA_CUBE", 96, 40, (EntityType)Entity1_14Types.SLIME), 
    ABSTRACT_HANGING("ABSTRACT_HANGING", 97, -1, (EntityType)Entity1_14Types.ENTITY), 
    LEASH_KNOT("LEASH_KNOT", 98, 37, (EntityType)Entity1_14Types.ABSTRACT_HANGING), 
    ITEM_FRAME("ITEM_FRAME", 99, 35, (EntityType)Entity1_14Types.ABSTRACT_HANGING), 
    PAINTING("PAINTING", 100, 51, (EntityType)Entity1_14Types.ABSTRACT_HANGING), 
    ABSTRACT_LIGHTNING("ABSTRACT_LIGHTNING", 101, -1, (EntityType)Entity1_14Types.ENTITY), 
    LIGHTNING_BOLT("LIGHTNING_BOLT", 102, 99, (EntityType)Entity1_14Types.ABSTRACT_LIGHTNING), 
    ABSTRACT_ARROW("ABSTRACT_ARROW", 103, -1, (EntityType)Entity1_14Types.ENTITY), 
    ARROW("ARROW", 104, 2, (EntityType)Entity1_14Types.ABSTRACT_ARROW), 
    SPECTRAL_ARROW("SPECTRAL_ARROW", 105, 71, (EntityType)Entity1_14Types.ABSTRACT_ARROW), 
    TRIDENT("TRIDENT", 106, 82, (EntityType)Entity1_14Types.ABSTRACT_ARROW), 
    ABSTRACT_FIREBALL("ABSTRACT_FIREBALL", 107, -1, (EntityType)Entity1_14Types.ENTITY), 
    DRAGON_FIREBALL("DRAGON_FIREBALL", 108, 14, (EntityType)Entity1_14Types.ABSTRACT_FIREBALL), 
    FIREBALL("FIREBALL", 109, 36, (EntityType)Entity1_14Types.ABSTRACT_FIREBALL), 
    SMALL_FIREBALL("SMALL_FIREBALL", 110, 68, (EntityType)Entity1_14Types.ABSTRACT_FIREBALL), 
    WITHER_SKULL("WITHER_SKULL", 111, 92, (EntityType)Entity1_14Types.ABSTRACT_FIREBALL), 
    PROJECTILE_ABSTRACT("PROJECTILE_ABSTRACT", 112, -1, (EntityType)Entity1_14Types.ENTITY), 
    SNOWBALL("SNOWBALL", 113, 70, (EntityType)Entity1_14Types.PROJECTILE_ABSTRACT), 
    ENDER_PEARL("ENDER_PEARL", 114, 79, (EntityType)Entity1_14Types.PROJECTILE_ABSTRACT), 
    EGG("EGG", 115, 78, (EntityType)Entity1_14Types.PROJECTILE_ABSTRACT), 
    POTION("POTION", 116, 81, (EntityType)Entity1_14Types.PROJECTILE_ABSTRACT), 
    EXPERIENCE_BOTTLE("EXPERIENCE_BOTTLE", 117, 80, (EntityType)Entity1_14Types.PROJECTILE_ABSTRACT), 
    MINECART_ABSTRACT("MINECART_ABSTRACT", 118, -1, (EntityType)Entity1_14Types.ENTITY), 
    CHESTED_MINECART_ABSTRACT("CHESTED_MINECART_ABSTRACT", 119, -1, (EntityType)Entity1_14Types.MINECART_ABSTRACT), 
    CHEST_MINECART("CHEST_MINECART", 120, 42, (EntityType)Entity1_14Types.CHESTED_MINECART_ABSTRACT), 
    HOPPER_MINECART("HOPPER_MINECART", 121, 45, (EntityType)Entity1_14Types.CHESTED_MINECART_ABSTRACT), 
    MINECART("MINECART", 122, 41, (EntityType)Entity1_14Types.MINECART_ABSTRACT), 
    FURNACE_MINECART("FURNACE_MINECART", 123, 44, (EntityType)Entity1_14Types.MINECART_ABSTRACT), 
    COMMAND_BLOCK_MINECART("COMMAND_BLOCK_MINECART", 124, 43, (EntityType)Entity1_14Types.MINECART_ABSTRACT), 
    TNT_MINECART("TNT_MINECART", 125, 47, (EntityType)Entity1_14Types.MINECART_ABSTRACT), 
    SPAWNER_MINECART("SPAWNER_MINECART", 126, 46, (EntityType)Entity1_14Types.MINECART_ABSTRACT), 
    BOAT("BOAT", 127, 5, (EntityType)Entity1_14Types.ENTITY);
    
    private static final EntityType[] TYPES;
    private final int id;
    private final EntityType parent;
    private static final Entity1_14Types[] $VALUES;
    
    private Entity1_14Types(final String s, final int n, final int id) {
        this.id = id;
        this.parent = null;
    }
    
    private Entity1_14Types(final String s, final int n, final int id, final EntityType parent) {
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
    
    public static EntityType getTypeFromId(final int n) {
        return EntityTypeUtil.getTypeFromId(Entity1_14Types.TYPES, n, Entity1_14Types.ENTITY);
    }
    
    static {
        $VALUES = new Entity1_14Types[] { Entity1_14Types.ENTITY, Entity1_14Types.AREA_EFFECT_CLOUD, Entity1_14Types.END_CRYSTAL, Entity1_14Types.EVOKER_FANGS, Entity1_14Types.EXPERIENCE_ORB, Entity1_14Types.EYE_OF_ENDER, Entity1_14Types.FALLING_BLOCK, Entity1_14Types.FIREWORK_ROCKET, Entity1_14Types.ITEM, Entity1_14Types.LLAMA_SPIT, Entity1_14Types.TNT, Entity1_14Types.SHULKER_BULLET, Entity1_14Types.FISHING_BOBBER, Entity1_14Types.LIVINGENTITY, Entity1_14Types.ARMOR_STAND, Entity1_14Types.PLAYER, Entity1_14Types.ABSTRACT_INSENTIENT, Entity1_14Types.ENDER_DRAGON, Entity1_14Types.ABSTRACT_CREATURE, Entity1_14Types.ABSTRACT_AGEABLE, Entity1_14Types.VILLAGER, Entity1_14Types.WANDERING_TRADER, Entity1_14Types.ABSTRACT_ANIMAL, Entity1_14Types.DOLPHIN, Entity1_14Types.CHICKEN, Entity1_14Types.COW, Entity1_14Types.MOOSHROOM, Entity1_14Types.PANDA, Entity1_14Types.PIG, Entity1_14Types.POLAR_BEAR, Entity1_14Types.RABBIT, Entity1_14Types.SHEEP, Entity1_14Types.TURTLE, Entity1_14Types.FOX, Entity1_14Types.ABSTRACT_TAMEABLE_ANIMAL, Entity1_14Types.CAT, Entity1_14Types.OCELOT, Entity1_14Types.WOLF, Entity1_14Types.ABSTRACT_PARROT, Entity1_14Types.PARROT, Entity1_14Types.ABSTRACT_HORSE, Entity1_14Types.CHESTED_HORSE, Entity1_14Types.DONKEY, Entity1_14Types.MULE, Entity1_14Types.LLAMA, Entity1_14Types.TRADER_LLAMA, Entity1_14Types.HORSE, Entity1_14Types.SKELETON_HORSE, Entity1_14Types.ZOMBIE_HORSE, Entity1_14Types.ABSTRACT_GOLEM, Entity1_14Types.SNOW_GOLEM, Entity1_14Types.IRON_GOLEM, Entity1_14Types.SHULKER, Entity1_14Types.ABSTRACT_FISHES, Entity1_14Types.COD, Entity1_14Types.PUFFERFISH, Entity1_14Types.SALMON, Entity1_14Types.TROPICAL_FISH, Entity1_14Types.ABSTRACT_MONSTER, Entity1_14Types.BLAZE, Entity1_14Types.CREEPER, Entity1_14Types.ENDERMITE, Entity1_14Types.ENDERMAN, Entity1_14Types.GIANT, Entity1_14Types.SILVERFISH, Entity1_14Types.VEX, Entity1_14Types.WITCH, Entity1_14Types.WITHER, Entity1_14Types.RAVAGER, Entity1_14Types.ABSTRACT_ILLAGER_BASE, Entity1_14Types.ABSTRACT_EVO_ILLU_ILLAGER, Entity1_14Types.EVOKER, Entity1_14Types.ILLUSIONER, Entity1_14Types.VINDICATOR, Entity1_14Types.PILLAGER, Entity1_14Types.ABSTRACT_SKELETON, Entity1_14Types.SKELETON, Entity1_14Types.STRAY, Entity1_14Types.WITHER_SKELETON, Entity1_14Types.GUARDIAN, Entity1_14Types.ELDER_GUARDIAN, Entity1_14Types.SPIDER, Entity1_14Types.CAVE_SPIDER, Entity1_14Types.ZOMBIE, Entity1_14Types.DROWNED, Entity1_14Types.HUSK, Entity1_14Types.ZOMBIE_PIGMAN, Entity1_14Types.ZOMBIE_VILLAGER, Entity1_14Types.ABSTRACT_FLYING, Entity1_14Types.GHAST, Entity1_14Types.PHANTOM, Entity1_14Types.ABSTRACT_AMBIENT, Entity1_14Types.BAT, Entity1_14Types.ABSTRACT_WATERMOB, Entity1_14Types.SQUID, Entity1_14Types.SLIME, Entity1_14Types.MAGMA_CUBE, Entity1_14Types.ABSTRACT_HANGING, Entity1_14Types.LEASH_KNOT, Entity1_14Types.ITEM_FRAME, Entity1_14Types.PAINTING, Entity1_14Types.ABSTRACT_LIGHTNING, Entity1_14Types.LIGHTNING_BOLT, Entity1_14Types.ABSTRACT_ARROW, Entity1_14Types.ARROW, Entity1_14Types.SPECTRAL_ARROW, Entity1_14Types.TRIDENT, Entity1_14Types.ABSTRACT_FIREBALL, Entity1_14Types.DRAGON_FIREBALL, Entity1_14Types.FIREBALL, Entity1_14Types.SMALL_FIREBALL, Entity1_14Types.WITHER_SKULL, Entity1_14Types.PROJECTILE_ABSTRACT, Entity1_14Types.SNOWBALL, Entity1_14Types.ENDER_PEARL, Entity1_14Types.EGG, Entity1_14Types.POTION, Entity1_14Types.EXPERIENCE_BOTTLE, Entity1_14Types.MINECART_ABSTRACT, Entity1_14Types.CHESTED_MINECART_ABSTRACT, Entity1_14Types.CHEST_MINECART, Entity1_14Types.HOPPER_MINECART, Entity1_14Types.MINECART, Entity1_14Types.FURNACE_MINECART, Entity1_14Types.COMMAND_BLOCK_MINECART, Entity1_14Types.TNT_MINECART, Entity1_14Types.SPAWNER_MINECART, Entity1_14Types.BOAT };
        TYPES = EntityTypeUtil.toOrderedArray(values());
    }
}
