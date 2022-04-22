package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.util.*;

public enum Entity1_16_2Types implements EntityType
{
    ENTITY("ENTITY", 0, -1), 
    AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 1, 0, (EntityType)Entity1_16_2Types.ENTITY), 
    END_CRYSTAL("END_CRYSTAL", 2, 18, (EntityType)Entity1_16_2Types.ENTITY), 
    EVOKER_FANGS("EVOKER_FANGS", 3, 23, (EntityType)Entity1_16_2Types.ENTITY), 
    EXPERIENCE_ORB("EXPERIENCE_ORB", 4, 24, (EntityType)Entity1_16_2Types.ENTITY), 
    EYE_OF_ENDER("EYE_OF_ENDER", 5, 25, (EntityType)Entity1_16_2Types.ENTITY), 
    FALLING_BLOCK("FALLING_BLOCK", 6, 26, (EntityType)Entity1_16_2Types.ENTITY), 
    FIREWORK_ROCKET("FIREWORK_ROCKET", 7, 27, (EntityType)Entity1_16_2Types.ENTITY), 
    ITEM("ITEM", 8, 37, (EntityType)Entity1_16_2Types.ENTITY), 
    LLAMA_SPIT("LLAMA_SPIT", 9, 43, (EntityType)Entity1_16_2Types.ENTITY), 
    TNT("TNT", 10, 64, (EntityType)Entity1_16_2Types.ENTITY), 
    SHULKER_BULLET("SHULKER_BULLET", 11, 71, (EntityType)Entity1_16_2Types.ENTITY), 
    FISHING_BOBBER("FISHING_BOBBER", 12, 107, (EntityType)Entity1_16_2Types.ENTITY), 
    LIVINGENTITY("LIVINGENTITY", 13, -1, (EntityType)Entity1_16_2Types.ENTITY), 
    ARMOR_STAND("ARMOR_STAND", 14, 1, (EntityType)Entity1_16_2Types.LIVINGENTITY), 
    PLAYER("PLAYER", 15, 106, (EntityType)Entity1_16_2Types.LIVINGENTITY), 
    ABSTRACT_INSENTIENT("ABSTRACT_INSENTIENT", 16, -1, (EntityType)Entity1_16_2Types.LIVINGENTITY), 
    ENDER_DRAGON("ENDER_DRAGON", 17, 19, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    BEE("BEE", 18, 4, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_CREATURE("ABSTRACT_CREATURE", 19, -1, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_AGEABLE("ABSTRACT_AGEABLE", 20, -1, (EntityType)Entity1_16_2Types.ABSTRACT_CREATURE), 
    VILLAGER("VILLAGER", 21, 93, (EntityType)Entity1_16_2Types.ABSTRACT_AGEABLE), 
    WANDERING_TRADER("WANDERING_TRADER", 22, 95, (EntityType)Entity1_16_2Types.ABSTRACT_AGEABLE), 
    ABSTRACT_ANIMAL("ABSTRACT_ANIMAL", 23, -1, (EntityType)Entity1_16_2Types.ABSTRACT_AGEABLE), 
    DOLPHIN("DOLPHIN", 24, 13, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    CHICKEN("CHICKEN", 25, 9, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    COW("COW", 26, 11, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    MOOSHROOM("MOOSHROOM", 27, 53, (EntityType)Entity1_16_2Types.COW), 
    PANDA("PANDA", 28, 56, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    PIG("PIG", 29, 59, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    POLAR_BEAR("POLAR_BEAR", 30, 63, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    RABBIT("RABBIT", 31, 66, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    SHEEP("SHEEP", 32, 69, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    TURTLE("TURTLE", 33, 91, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    FOX("FOX", 34, 28, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    ABSTRACT_TAMEABLE_ANIMAL("ABSTRACT_TAMEABLE_ANIMAL", 35, -1, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    CAT("CAT", 36, 7, (EntityType)Entity1_16_2Types.ABSTRACT_TAMEABLE_ANIMAL), 
    OCELOT("OCELOT", 37, 54, (EntityType)Entity1_16_2Types.ABSTRACT_TAMEABLE_ANIMAL), 
    WOLF("WOLF", 38, 100, (EntityType)Entity1_16_2Types.ABSTRACT_TAMEABLE_ANIMAL), 
    ABSTRACT_PARROT("ABSTRACT_PARROT", 39, -1, (EntityType)Entity1_16_2Types.ABSTRACT_TAMEABLE_ANIMAL), 
    PARROT("PARROT", 40, 57, (EntityType)Entity1_16_2Types.ABSTRACT_PARROT), 
    ABSTRACT_HORSE("ABSTRACT_HORSE", 41, -1, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    CHESTED_HORSE("CHESTED_HORSE", 42, -1, (EntityType)Entity1_16_2Types.ABSTRACT_HORSE), 
    DONKEY("DONKEY", 43, 14, (EntityType)Entity1_16_2Types.CHESTED_HORSE), 
    MULE("MULE", 44, 52, (EntityType)Entity1_16_2Types.CHESTED_HORSE), 
    LLAMA("LLAMA", 45, 42, (EntityType)Entity1_16_2Types.CHESTED_HORSE), 
    TRADER_LLAMA("TRADER_LLAMA", 46, 89, (EntityType)Entity1_16_2Types.CHESTED_HORSE), 
    HORSE("HORSE", 47, 33, (EntityType)Entity1_16_2Types.ABSTRACT_HORSE), 
    SKELETON_HORSE("SKELETON_HORSE", 48, 74, (EntityType)Entity1_16_2Types.ABSTRACT_HORSE), 
    ZOMBIE_HORSE("ZOMBIE_HORSE", 49, 103, (EntityType)Entity1_16_2Types.ABSTRACT_HORSE), 
    ABSTRACT_GOLEM("ABSTRACT_GOLEM", 50, -1, (EntityType)Entity1_16_2Types.ABSTRACT_CREATURE), 
    SNOW_GOLEM("SNOW_GOLEM", 51, 77, (EntityType)Entity1_16_2Types.ABSTRACT_GOLEM), 
    IRON_GOLEM("IRON_GOLEM", 52, 36, (EntityType)Entity1_16_2Types.ABSTRACT_GOLEM), 
    SHULKER("SHULKER", 53, 70, (EntityType)Entity1_16_2Types.ABSTRACT_GOLEM), 
    ABSTRACT_FISHES("ABSTRACT_FISHES", 54, -1, (EntityType)Entity1_16_2Types.ABSTRACT_CREATURE), 
    COD("COD", 55, 10, (EntityType)Entity1_16_2Types.ABSTRACT_FISHES), 
    PUFFERFISH("PUFFERFISH", 56, 65, (EntityType)Entity1_16_2Types.ABSTRACT_FISHES), 
    SALMON("SALMON", 57, 68, (EntityType)Entity1_16_2Types.ABSTRACT_FISHES), 
    TROPICAL_FISH("TROPICAL_FISH", 58, 90, (EntityType)Entity1_16_2Types.ABSTRACT_FISHES), 
    ABSTRACT_MONSTER("ABSTRACT_MONSTER", 59, -1, (EntityType)Entity1_16_2Types.ABSTRACT_CREATURE), 
    BLAZE("BLAZE", 60, 5, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    CREEPER("CREEPER", 61, 12, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    ENDERMITE("ENDERMITE", 62, 21, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    ENDERMAN("ENDERMAN", 63, 20, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    GIANT("GIANT", 64, 30, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    SILVERFISH("SILVERFISH", 65, 72, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    VEX("VEX", 66, 92, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    WITCH("WITCH", 67, 96, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    WITHER("WITHER", 68, 97, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    RAVAGER("RAVAGER", 69, 67, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    ABSTRACT_PIGLIN("ABSTRACT_PIGLIN", 70, -1, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    PIGLIN("PIGLIN", 71, 60, (EntityType)Entity1_16_2Types.ABSTRACT_PIGLIN), 
    PIGLIN_BRUTE("PIGLIN_BRUTE", 72, 61, (EntityType)Entity1_16_2Types.ABSTRACT_PIGLIN), 
    HOGLIN("HOGLIN", 73, 32, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    STRIDER("STRIDER", 74, 83, (EntityType)Entity1_16_2Types.ABSTRACT_ANIMAL), 
    ZOGLIN("ZOGLIN", 75, 101, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    ABSTRACT_ILLAGER_BASE("ABSTRACT_ILLAGER_BASE", 76, -1, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    ABSTRACT_EVO_ILLU_ILLAGER("ABSTRACT_EVO_ILLU_ILLAGER", 77, -1, (EntityType)Entity1_16_2Types.ABSTRACT_ILLAGER_BASE), 
    EVOKER("EVOKER", 78, 22, (EntityType)Entity1_16_2Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    ILLUSIONER("ILLUSIONER", 79, 35, (EntityType)Entity1_16_2Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    VINDICATOR("VINDICATOR", 80, 94, (EntityType)Entity1_16_2Types.ABSTRACT_ILLAGER_BASE), 
    PILLAGER("PILLAGER", 81, 62, (EntityType)Entity1_16_2Types.ABSTRACT_ILLAGER_BASE), 
    ABSTRACT_SKELETON("ABSTRACT_SKELETON", 82, -1, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    SKELETON("SKELETON", 83, 73, (EntityType)Entity1_16_2Types.ABSTRACT_SKELETON), 
    STRAY("STRAY", 84, 82, (EntityType)Entity1_16_2Types.ABSTRACT_SKELETON), 
    WITHER_SKELETON("WITHER_SKELETON", 85, 98, (EntityType)Entity1_16_2Types.ABSTRACT_SKELETON), 
    GUARDIAN("GUARDIAN", 86, 31, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    ELDER_GUARDIAN("ELDER_GUARDIAN", 87, 17, (EntityType)Entity1_16_2Types.GUARDIAN), 
    SPIDER("SPIDER", 88, 80, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    CAVE_SPIDER("CAVE_SPIDER", 89, 8, (EntityType)Entity1_16_2Types.SPIDER), 
    ZOMBIE("ZOMBIE", 90, 102, (EntityType)Entity1_16_2Types.ABSTRACT_MONSTER), 
    DROWNED("DROWNED", 91, 16, (EntityType)Entity1_16_2Types.ZOMBIE), 
    HUSK("HUSK", 92, 34, (EntityType)Entity1_16_2Types.ZOMBIE), 
    ZOMBIFIED_PIGLIN("ZOMBIFIED_PIGLIN", 93, 105, (EntityType)Entity1_16_2Types.ZOMBIE), 
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 94, 104, (EntityType)Entity1_16_2Types.ZOMBIE), 
    ABSTRACT_FLYING("ABSTRACT_FLYING", 95, -1, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    GHAST("GHAST", 96, 29, (EntityType)Entity1_16_2Types.ABSTRACT_FLYING), 
    PHANTOM("PHANTOM", 97, 58, (EntityType)Entity1_16_2Types.ABSTRACT_FLYING), 
    ABSTRACT_AMBIENT("ABSTRACT_AMBIENT", 98, -1, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    BAT("BAT", 99, 3, (EntityType)Entity1_16_2Types.ABSTRACT_AMBIENT), 
    ABSTRACT_WATERMOB("ABSTRACT_WATERMOB", 100, -1, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    SQUID("SQUID", 101, 81, (EntityType)Entity1_16_2Types.ABSTRACT_WATERMOB), 
    SLIME("SLIME", 102, 75, (EntityType)Entity1_16_2Types.ABSTRACT_INSENTIENT), 
    MAGMA_CUBE("MAGMA_CUBE", 103, 44, (EntityType)Entity1_16_2Types.SLIME), 
    ABSTRACT_HANGING("ABSTRACT_HANGING", 104, -1, (EntityType)Entity1_16_2Types.ENTITY), 
    LEASH_KNOT("LEASH_KNOT", 105, 40, (EntityType)Entity1_16_2Types.ABSTRACT_HANGING), 
    ITEM_FRAME("ITEM_FRAME", 106, 38, (EntityType)Entity1_16_2Types.ABSTRACT_HANGING), 
    PAINTING("PAINTING", 107, 55, (EntityType)Entity1_16_2Types.ABSTRACT_HANGING), 
    ABSTRACT_LIGHTNING("ABSTRACT_LIGHTNING", 108, -1, (EntityType)Entity1_16_2Types.ENTITY), 
    LIGHTNING_BOLT("LIGHTNING_BOLT", 109, 41, (EntityType)Entity1_16_2Types.ABSTRACT_LIGHTNING), 
    ABSTRACT_ARROW("ABSTRACT_ARROW", 110, -1, (EntityType)Entity1_16_2Types.ENTITY), 
    ARROW("ARROW", 111, 2, (EntityType)Entity1_16_2Types.ABSTRACT_ARROW), 
    SPECTRAL_ARROW("SPECTRAL_ARROW", 112, 79, (EntityType)Entity1_16_2Types.ABSTRACT_ARROW), 
    TRIDENT("TRIDENT", 113, 88, (EntityType)Entity1_16_2Types.ABSTRACT_ARROW), 
    ABSTRACT_FIREBALL("ABSTRACT_FIREBALL", 114, -1, (EntityType)Entity1_16_2Types.ENTITY), 
    DRAGON_FIREBALL("DRAGON_FIREBALL", 115, 15, (EntityType)Entity1_16_2Types.ABSTRACT_FIREBALL), 
    FIREBALL("FIREBALL", 116, 39, (EntityType)Entity1_16_2Types.ABSTRACT_FIREBALL), 
    SMALL_FIREBALL("SMALL_FIREBALL", 117, 76, (EntityType)Entity1_16_2Types.ABSTRACT_FIREBALL), 
    WITHER_SKULL("WITHER_SKULL", 118, 99, (EntityType)Entity1_16_2Types.ABSTRACT_FIREBALL), 
    PROJECTILE_ABSTRACT("PROJECTILE_ABSTRACT", 119, -1, (EntityType)Entity1_16_2Types.ENTITY), 
    SNOWBALL("SNOWBALL", 120, 78, (EntityType)Entity1_16_2Types.PROJECTILE_ABSTRACT), 
    ENDER_PEARL("ENDER_PEARL", 121, 85, (EntityType)Entity1_16_2Types.PROJECTILE_ABSTRACT), 
    EGG("EGG", 122, 84, (EntityType)Entity1_16_2Types.PROJECTILE_ABSTRACT), 
    POTION("POTION", 123, 87, (EntityType)Entity1_16_2Types.PROJECTILE_ABSTRACT), 
    EXPERIENCE_BOTTLE("EXPERIENCE_BOTTLE", 124, 86, (EntityType)Entity1_16_2Types.PROJECTILE_ABSTRACT), 
    MINECART_ABSTRACT("MINECART_ABSTRACT", 125, -1, (EntityType)Entity1_16_2Types.ENTITY), 
    CHESTED_MINECART_ABSTRACT("CHESTED_MINECART_ABSTRACT", 126, -1, (EntityType)Entity1_16_2Types.MINECART_ABSTRACT), 
    CHEST_MINECART("CHEST_MINECART", 127, 46, (EntityType)Entity1_16_2Types.CHESTED_MINECART_ABSTRACT), 
    HOPPER_MINECART("HOPPER_MINECART", 128, 49, (EntityType)Entity1_16_2Types.CHESTED_MINECART_ABSTRACT), 
    MINECART("MINECART", 129, 45, (EntityType)Entity1_16_2Types.MINECART_ABSTRACT), 
    FURNACE_MINECART("FURNACE_MINECART", 130, 48, (EntityType)Entity1_16_2Types.MINECART_ABSTRACT), 
    COMMAND_BLOCK_MINECART("COMMAND_BLOCK_MINECART", 131, 47, (EntityType)Entity1_16_2Types.MINECART_ABSTRACT), 
    TNT_MINECART("TNT_MINECART", 132, 51, (EntityType)Entity1_16_2Types.MINECART_ABSTRACT), 
    SPAWNER_MINECART("SPAWNER_MINECART", 133, 50, (EntityType)Entity1_16_2Types.MINECART_ABSTRACT), 
    BOAT("BOAT", 134, 6, (EntityType)Entity1_16_2Types.ENTITY);
    
    private static final EntityType[] TYPES;
    private final int id;
    private final EntityType parent;
    private static final Entity1_16_2Types[] $VALUES;
    
    private Entity1_16_2Types(final String s, final int n, final int id) {
        this.id = id;
        this.parent = null;
    }
    
    private Entity1_16_2Types(final String s, final int n, final int id, final EntityType parent) {
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
        return EntityTypeUtil.getTypeFromId(Entity1_16_2Types.TYPES, n, Entity1_16_2Types.ENTITY);
    }
    
    static {
        $VALUES = new Entity1_16_2Types[] { Entity1_16_2Types.ENTITY, Entity1_16_2Types.AREA_EFFECT_CLOUD, Entity1_16_2Types.END_CRYSTAL, Entity1_16_2Types.EVOKER_FANGS, Entity1_16_2Types.EXPERIENCE_ORB, Entity1_16_2Types.EYE_OF_ENDER, Entity1_16_2Types.FALLING_BLOCK, Entity1_16_2Types.FIREWORK_ROCKET, Entity1_16_2Types.ITEM, Entity1_16_2Types.LLAMA_SPIT, Entity1_16_2Types.TNT, Entity1_16_2Types.SHULKER_BULLET, Entity1_16_2Types.FISHING_BOBBER, Entity1_16_2Types.LIVINGENTITY, Entity1_16_2Types.ARMOR_STAND, Entity1_16_2Types.PLAYER, Entity1_16_2Types.ABSTRACT_INSENTIENT, Entity1_16_2Types.ENDER_DRAGON, Entity1_16_2Types.BEE, Entity1_16_2Types.ABSTRACT_CREATURE, Entity1_16_2Types.ABSTRACT_AGEABLE, Entity1_16_2Types.VILLAGER, Entity1_16_2Types.WANDERING_TRADER, Entity1_16_2Types.ABSTRACT_ANIMAL, Entity1_16_2Types.DOLPHIN, Entity1_16_2Types.CHICKEN, Entity1_16_2Types.COW, Entity1_16_2Types.MOOSHROOM, Entity1_16_2Types.PANDA, Entity1_16_2Types.PIG, Entity1_16_2Types.POLAR_BEAR, Entity1_16_2Types.RABBIT, Entity1_16_2Types.SHEEP, Entity1_16_2Types.TURTLE, Entity1_16_2Types.FOX, Entity1_16_2Types.ABSTRACT_TAMEABLE_ANIMAL, Entity1_16_2Types.CAT, Entity1_16_2Types.OCELOT, Entity1_16_2Types.WOLF, Entity1_16_2Types.ABSTRACT_PARROT, Entity1_16_2Types.PARROT, Entity1_16_2Types.ABSTRACT_HORSE, Entity1_16_2Types.CHESTED_HORSE, Entity1_16_2Types.DONKEY, Entity1_16_2Types.MULE, Entity1_16_2Types.LLAMA, Entity1_16_2Types.TRADER_LLAMA, Entity1_16_2Types.HORSE, Entity1_16_2Types.SKELETON_HORSE, Entity1_16_2Types.ZOMBIE_HORSE, Entity1_16_2Types.ABSTRACT_GOLEM, Entity1_16_2Types.SNOW_GOLEM, Entity1_16_2Types.IRON_GOLEM, Entity1_16_2Types.SHULKER, Entity1_16_2Types.ABSTRACT_FISHES, Entity1_16_2Types.COD, Entity1_16_2Types.PUFFERFISH, Entity1_16_2Types.SALMON, Entity1_16_2Types.TROPICAL_FISH, Entity1_16_2Types.ABSTRACT_MONSTER, Entity1_16_2Types.BLAZE, Entity1_16_2Types.CREEPER, Entity1_16_2Types.ENDERMITE, Entity1_16_2Types.ENDERMAN, Entity1_16_2Types.GIANT, Entity1_16_2Types.SILVERFISH, Entity1_16_2Types.VEX, Entity1_16_2Types.WITCH, Entity1_16_2Types.WITHER, Entity1_16_2Types.RAVAGER, Entity1_16_2Types.ABSTRACT_PIGLIN, Entity1_16_2Types.PIGLIN, Entity1_16_2Types.PIGLIN_BRUTE, Entity1_16_2Types.HOGLIN, Entity1_16_2Types.STRIDER, Entity1_16_2Types.ZOGLIN, Entity1_16_2Types.ABSTRACT_ILLAGER_BASE, Entity1_16_2Types.ABSTRACT_EVO_ILLU_ILLAGER, Entity1_16_2Types.EVOKER, Entity1_16_2Types.ILLUSIONER, Entity1_16_2Types.VINDICATOR, Entity1_16_2Types.PILLAGER, Entity1_16_2Types.ABSTRACT_SKELETON, Entity1_16_2Types.SKELETON, Entity1_16_2Types.STRAY, Entity1_16_2Types.WITHER_SKELETON, Entity1_16_2Types.GUARDIAN, Entity1_16_2Types.ELDER_GUARDIAN, Entity1_16_2Types.SPIDER, Entity1_16_2Types.CAVE_SPIDER, Entity1_16_2Types.ZOMBIE, Entity1_16_2Types.DROWNED, Entity1_16_2Types.HUSK, Entity1_16_2Types.ZOMBIFIED_PIGLIN, Entity1_16_2Types.ZOMBIE_VILLAGER, Entity1_16_2Types.ABSTRACT_FLYING, Entity1_16_2Types.GHAST, Entity1_16_2Types.PHANTOM, Entity1_16_2Types.ABSTRACT_AMBIENT, Entity1_16_2Types.BAT, Entity1_16_2Types.ABSTRACT_WATERMOB, Entity1_16_2Types.SQUID, Entity1_16_2Types.SLIME, Entity1_16_2Types.MAGMA_CUBE, Entity1_16_2Types.ABSTRACT_HANGING, Entity1_16_2Types.LEASH_KNOT, Entity1_16_2Types.ITEM_FRAME, Entity1_16_2Types.PAINTING, Entity1_16_2Types.ABSTRACT_LIGHTNING, Entity1_16_2Types.LIGHTNING_BOLT, Entity1_16_2Types.ABSTRACT_ARROW, Entity1_16_2Types.ARROW, Entity1_16_2Types.SPECTRAL_ARROW, Entity1_16_2Types.TRIDENT, Entity1_16_2Types.ABSTRACT_FIREBALL, Entity1_16_2Types.DRAGON_FIREBALL, Entity1_16_2Types.FIREBALL, Entity1_16_2Types.SMALL_FIREBALL, Entity1_16_2Types.WITHER_SKULL, Entity1_16_2Types.PROJECTILE_ABSTRACT, Entity1_16_2Types.SNOWBALL, Entity1_16_2Types.ENDER_PEARL, Entity1_16_2Types.EGG, Entity1_16_2Types.POTION, Entity1_16_2Types.EXPERIENCE_BOTTLE, Entity1_16_2Types.MINECART_ABSTRACT, Entity1_16_2Types.CHESTED_MINECART_ABSTRACT, Entity1_16_2Types.CHEST_MINECART, Entity1_16_2Types.HOPPER_MINECART, Entity1_16_2Types.MINECART, Entity1_16_2Types.FURNACE_MINECART, Entity1_16_2Types.COMMAND_BLOCK_MINECART, Entity1_16_2Types.TNT_MINECART, Entity1_16_2Types.SPAWNER_MINECART, Entity1_16_2Types.BOAT };
        TYPES = EntityTypeUtil.toOrderedArray(values());
    }
}
