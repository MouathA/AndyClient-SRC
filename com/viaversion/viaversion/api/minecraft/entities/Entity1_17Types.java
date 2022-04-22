package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.util.*;

public enum Entity1_17Types implements EntityType
{
    ENTITY("ENTITY", 0, -1), 
    AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 1, 0, (EntityType)Entity1_17Types.ENTITY), 
    END_CRYSTAL("END_CRYSTAL", 2, 19, (EntityType)Entity1_17Types.ENTITY), 
    EVOKER_FANGS("EVOKER_FANGS", 3, 24, (EntityType)Entity1_17Types.ENTITY), 
    EXPERIENCE_ORB("EXPERIENCE_ORB", 4, 25, (EntityType)Entity1_17Types.ENTITY), 
    EYE_OF_ENDER("EYE_OF_ENDER", 5, 26, (EntityType)Entity1_17Types.ENTITY), 
    FALLING_BLOCK("FALLING_BLOCK", 6, 27, (EntityType)Entity1_17Types.ENTITY), 
    FIREWORK_ROCKET("FIREWORK_ROCKET", 7, 28, (EntityType)Entity1_17Types.ENTITY), 
    ITEM("ITEM", 8, 41, (EntityType)Entity1_17Types.ENTITY), 
    LLAMA_SPIT("LLAMA_SPIT", 9, 47, (EntityType)Entity1_17Types.ENTITY), 
    TNT("TNT", 10, 69, (EntityType)Entity1_17Types.ENTITY), 
    SHULKER_BULLET("SHULKER_BULLET", 11, 76, (EntityType)Entity1_17Types.ENTITY), 
    FISHING_BOBBER("FISHING_BOBBER", 12, 112, (EntityType)Entity1_17Types.ENTITY), 
    LIVINGENTITY("LIVINGENTITY", 13, -1, (EntityType)Entity1_17Types.ENTITY), 
    ARMOR_STAND("ARMOR_STAND", 14, 1, (EntityType)Entity1_17Types.LIVINGENTITY), 
    MARKER("MARKER", 15, 49, (EntityType)Entity1_17Types.ENTITY), 
    PLAYER("PLAYER", 16, 111, (EntityType)Entity1_17Types.LIVINGENTITY), 
    ABSTRACT_INSENTIENT("ABSTRACT_INSENTIENT", 17, -1, (EntityType)Entity1_17Types.LIVINGENTITY), 
    ENDER_DRAGON("ENDER_DRAGON", 18, 20, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    BEE("BEE", 19, 5, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_CREATURE("ABSTRACT_CREATURE", 20, -1, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_AGEABLE("ABSTRACT_AGEABLE", 21, -1, (EntityType)Entity1_17Types.ABSTRACT_CREATURE), 
    VILLAGER("VILLAGER", 22, 98, (EntityType)Entity1_17Types.ABSTRACT_AGEABLE), 
    WANDERING_TRADER("WANDERING_TRADER", 23, 100, (EntityType)Entity1_17Types.ABSTRACT_AGEABLE), 
    ABSTRACT_ANIMAL("ABSTRACT_ANIMAL", 24, -1, (EntityType)Entity1_17Types.ABSTRACT_AGEABLE), 
    AXOLOTL("AXOLOTL", 25, 3, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    DOLPHIN("DOLPHIN", 26, 14, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    CHICKEN("CHICKEN", 27, 10, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    COW("COW", 28, 12, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    MOOSHROOM("MOOSHROOM", 29, 58, (EntityType)Entity1_17Types.COW), 
    PANDA("PANDA", 30, 61, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    PIG("PIG", 31, 64, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    POLAR_BEAR("POLAR_BEAR", 32, 68, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    RABBIT("RABBIT", 33, 71, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    SHEEP("SHEEP", 34, 74, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    TURTLE("TURTLE", 35, 96, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    FOX("FOX", 36, 29, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    GOAT("GOAT", 37, 34, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    ABSTRACT_TAMEABLE_ANIMAL("ABSTRACT_TAMEABLE_ANIMAL", 38, -1, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    CAT("CAT", 39, 8, (EntityType)Entity1_17Types.ABSTRACT_TAMEABLE_ANIMAL), 
    OCELOT("OCELOT", 40, 59, (EntityType)Entity1_17Types.ABSTRACT_TAMEABLE_ANIMAL), 
    WOLF("WOLF", 41, 105, (EntityType)Entity1_17Types.ABSTRACT_TAMEABLE_ANIMAL), 
    ABSTRACT_PARROT("ABSTRACT_PARROT", 42, -1, (EntityType)Entity1_17Types.ABSTRACT_TAMEABLE_ANIMAL), 
    PARROT("PARROT", 43, 62, (EntityType)Entity1_17Types.ABSTRACT_PARROT), 
    ABSTRACT_HORSE("ABSTRACT_HORSE", 44, -1, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    CHESTED_HORSE("CHESTED_HORSE", 45, -1, (EntityType)Entity1_17Types.ABSTRACT_HORSE), 
    DONKEY("DONKEY", 46, 15, (EntityType)Entity1_17Types.CHESTED_HORSE), 
    MULE("MULE", 47, 57, (EntityType)Entity1_17Types.CHESTED_HORSE), 
    LLAMA("LLAMA", 48, 46, (EntityType)Entity1_17Types.CHESTED_HORSE), 
    TRADER_LLAMA("TRADER_LLAMA", 49, 94, (EntityType)Entity1_17Types.CHESTED_HORSE), 
    HORSE("HORSE", 50, 37, (EntityType)Entity1_17Types.ABSTRACT_HORSE), 
    SKELETON_HORSE("SKELETON_HORSE", 51, 79, (EntityType)Entity1_17Types.ABSTRACT_HORSE), 
    ZOMBIE_HORSE("ZOMBIE_HORSE", 52, 108, (EntityType)Entity1_17Types.ABSTRACT_HORSE), 
    ABSTRACT_GOLEM("ABSTRACT_GOLEM", 53, -1, (EntityType)Entity1_17Types.ABSTRACT_CREATURE), 
    SNOW_GOLEM("SNOW_GOLEM", 54, 82, (EntityType)Entity1_17Types.ABSTRACT_GOLEM), 
    IRON_GOLEM("IRON_GOLEM", 55, 40, (EntityType)Entity1_17Types.ABSTRACT_GOLEM), 
    SHULKER("SHULKER", 56, 75, (EntityType)Entity1_17Types.ABSTRACT_GOLEM), 
    ABSTRACT_FISHES("ABSTRACT_FISHES", 57, -1, (EntityType)Entity1_17Types.ABSTRACT_CREATURE), 
    COD("COD", 58, 11, (EntityType)Entity1_17Types.ABSTRACT_FISHES), 
    PUFFERFISH("PUFFERFISH", 59, 70, (EntityType)Entity1_17Types.ABSTRACT_FISHES), 
    SALMON("SALMON", 60, 73, (EntityType)Entity1_17Types.ABSTRACT_FISHES), 
    TROPICAL_FISH("TROPICAL_FISH", 61, 95, (EntityType)Entity1_17Types.ABSTRACT_FISHES), 
    ABSTRACT_MONSTER("ABSTRACT_MONSTER", 62, -1, (EntityType)Entity1_17Types.ABSTRACT_CREATURE), 
    BLAZE("BLAZE", 63, 6, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    CREEPER("CREEPER", 64, 13, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    ENDERMITE("ENDERMITE", 65, 22, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    ENDERMAN("ENDERMAN", 66, 21, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    GIANT("GIANT", 67, 31, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    SILVERFISH("SILVERFISH", 68, 77, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    VEX("VEX", 69, 97, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    WITCH("WITCH", 70, 101, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    WITHER("WITHER", 71, 102, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    RAVAGER("RAVAGER", 72, 72, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    ABSTRACT_PIGLIN("ABSTRACT_PIGLIN", 73, -1, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    PIGLIN("PIGLIN", 74, 65, (EntityType)Entity1_17Types.ABSTRACT_PIGLIN), 
    PIGLIN_BRUTE("PIGLIN_BRUTE", 75, 66, (EntityType)Entity1_17Types.ABSTRACT_PIGLIN), 
    HOGLIN("HOGLIN", 76, 36, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    STRIDER("STRIDER", 77, 88, (EntityType)Entity1_17Types.ABSTRACT_ANIMAL), 
    ZOGLIN("ZOGLIN", 78, 106, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    ABSTRACT_ILLAGER_BASE("ABSTRACT_ILLAGER_BASE", 79, -1, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    ABSTRACT_EVO_ILLU_ILLAGER("ABSTRACT_EVO_ILLU_ILLAGER", 80, -1, (EntityType)Entity1_17Types.ABSTRACT_ILLAGER_BASE), 
    EVOKER("EVOKER", 81, 23, (EntityType)Entity1_17Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    ILLUSIONER("ILLUSIONER", 82, 39, (EntityType)Entity1_17Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    VINDICATOR("VINDICATOR", 83, 99, (EntityType)Entity1_17Types.ABSTRACT_ILLAGER_BASE), 
    PILLAGER("PILLAGER", 84, 67, (EntityType)Entity1_17Types.ABSTRACT_ILLAGER_BASE), 
    ABSTRACT_SKELETON("ABSTRACT_SKELETON", 85, -1, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    SKELETON("SKELETON", 86, 78, (EntityType)Entity1_17Types.ABSTRACT_SKELETON), 
    STRAY("STRAY", 87, 87, (EntityType)Entity1_17Types.ABSTRACT_SKELETON), 
    WITHER_SKELETON("WITHER_SKELETON", 88, 103, (EntityType)Entity1_17Types.ABSTRACT_SKELETON), 
    GUARDIAN("GUARDIAN", 89, 35, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    ELDER_GUARDIAN("ELDER_GUARDIAN", 90, 18, (EntityType)Entity1_17Types.GUARDIAN), 
    SPIDER("SPIDER", 91, 85, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    CAVE_SPIDER("CAVE_SPIDER", 92, 9, (EntityType)Entity1_17Types.SPIDER), 
    ZOMBIE("ZOMBIE", 93, 107, (EntityType)Entity1_17Types.ABSTRACT_MONSTER), 
    DROWNED("DROWNED", 94, 17, (EntityType)Entity1_17Types.ZOMBIE), 
    HUSK("HUSK", 95, 38, (EntityType)Entity1_17Types.ZOMBIE), 
    ZOMBIFIED_PIGLIN("ZOMBIFIED_PIGLIN", 96, 110, (EntityType)Entity1_17Types.ZOMBIE), 
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 97, 109, (EntityType)Entity1_17Types.ZOMBIE), 
    ABSTRACT_FLYING("ABSTRACT_FLYING", 98, -1, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    GHAST("GHAST", 99, 30, (EntityType)Entity1_17Types.ABSTRACT_FLYING), 
    PHANTOM("PHANTOM", 100, 63, (EntityType)Entity1_17Types.ABSTRACT_FLYING), 
    ABSTRACT_AMBIENT("ABSTRACT_AMBIENT", 101, -1, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    BAT("BAT", 102, 4, (EntityType)Entity1_17Types.ABSTRACT_AMBIENT), 
    ABSTRACT_WATERMOB("ABSTRACT_WATERMOB", 103, -1, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    SQUID("SQUID", 104, 86, (EntityType)Entity1_17Types.ABSTRACT_WATERMOB), 
    GLOW_SQUID("GLOW_SQUID", 105, 33, (EntityType)Entity1_17Types.SQUID), 
    SLIME("SLIME", 106, 80, (EntityType)Entity1_17Types.ABSTRACT_INSENTIENT), 
    MAGMA_CUBE("MAGMA_CUBE", 107, 48, (EntityType)Entity1_17Types.SLIME), 
    ABSTRACT_HANGING("ABSTRACT_HANGING", 108, -1, (EntityType)Entity1_17Types.ENTITY), 
    LEASH_KNOT("LEASH_KNOT", 109, 44, (EntityType)Entity1_17Types.ABSTRACT_HANGING), 
    ITEM_FRAME("ITEM_FRAME", 110, 42, (EntityType)Entity1_17Types.ABSTRACT_HANGING), 
    GLOW_ITEM_FRAME("GLOW_ITEM_FRAME", 111, 32, (EntityType)Entity1_17Types.ITEM_FRAME), 
    PAINTING("PAINTING", 112, 60, (EntityType)Entity1_17Types.ABSTRACT_HANGING), 
    ABSTRACT_LIGHTNING("ABSTRACT_LIGHTNING", 113, -1, (EntityType)Entity1_17Types.ENTITY), 
    LIGHTNING_BOLT("LIGHTNING_BOLT", 114, 45, (EntityType)Entity1_17Types.ABSTRACT_LIGHTNING), 
    ABSTRACT_ARROW("ABSTRACT_ARROW", 115, -1, (EntityType)Entity1_17Types.ENTITY), 
    ARROW("ARROW", 116, 2, (EntityType)Entity1_17Types.ABSTRACT_ARROW), 
    SPECTRAL_ARROW("SPECTRAL_ARROW", 117, 84, (EntityType)Entity1_17Types.ABSTRACT_ARROW), 
    TRIDENT("TRIDENT", 118, 93, (EntityType)Entity1_17Types.ABSTRACT_ARROW), 
    ABSTRACT_FIREBALL("ABSTRACT_FIREBALL", 119, -1, (EntityType)Entity1_17Types.ENTITY), 
    DRAGON_FIREBALL("DRAGON_FIREBALL", 120, 16, (EntityType)Entity1_17Types.ABSTRACT_FIREBALL), 
    FIREBALL("FIREBALL", 121, 43, (EntityType)Entity1_17Types.ABSTRACT_FIREBALL), 
    SMALL_FIREBALL("SMALL_FIREBALL", 122, 81, (EntityType)Entity1_17Types.ABSTRACT_FIREBALL), 
    WITHER_SKULL("WITHER_SKULL", 123, 104, (EntityType)Entity1_17Types.ABSTRACT_FIREBALL), 
    PROJECTILE_ABSTRACT("PROJECTILE_ABSTRACT", 124, -1, (EntityType)Entity1_17Types.ENTITY), 
    SNOWBALL("SNOWBALL", 125, 83, (EntityType)Entity1_17Types.PROJECTILE_ABSTRACT), 
    ENDER_PEARL("ENDER_PEARL", 126, 90, (EntityType)Entity1_17Types.PROJECTILE_ABSTRACT), 
    EGG("EGG", 127, 89, (EntityType)Entity1_17Types.PROJECTILE_ABSTRACT), 
    POTION("POTION", 128, 92, (EntityType)Entity1_17Types.PROJECTILE_ABSTRACT), 
    EXPERIENCE_BOTTLE("EXPERIENCE_BOTTLE", 129, 91, (EntityType)Entity1_17Types.PROJECTILE_ABSTRACT), 
    MINECART_ABSTRACT("MINECART_ABSTRACT", 130, -1, (EntityType)Entity1_17Types.ENTITY), 
    CHESTED_MINECART_ABSTRACT("CHESTED_MINECART_ABSTRACT", 131, -1, (EntityType)Entity1_17Types.MINECART_ABSTRACT), 
    CHEST_MINECART("CHEST_MINECART", 132, 51, (EntityType)Entity1_17Types.CHESTED_MINECART_ABSTRACT), 
    HOPPER_MINECART("HOPPER_MINECART", 133, 54, (EntityType)Entity1_17Types.CHESTED_MINECART_ABSTRACT), 
    MINECART("MINECART", 134, 50, (EntityType)Entity1_17Types.MINECART_ABSTRACT), 
    FURNACE_MINECART("FURNACE_MINECART", 135, 53, (EntityType)Entity1_17Types.MINECART_ABSTRACT), 
    COMMAND_BLOCK_MINECART("COMMAND_BLOCK_MINECART", 136, 52, (EntityType)Entity1_17Types.MINECART_ABSTRACT), 
    TNT_MINECART("TNT_MINECART", 137, 56, (EntityType)Entity1_17Types.MINECART_ABSTRACT), 
    SPAWNER_MINECART("SPAWNER_MINECART", 138, 55, (EntityType)Entity1_17Types.MINECART_ABSTRACT), 
    BOAT("BOAT", 139, 7, (EntityType)Entity1_17Types.ENTITY);
    
    private static final EntityType[] TYPES;
    private final int id;
    private final EntityType parent;
    private static final Entity1_17Types[] $VALUES;
    
    private Entity1_17Types(final String s, final int n, final int id) {
        this.id = id;
        this.parent = null;
    }
    
    private Entity1_17Types(final String s, final int n, final int id, final EntityType parent) {
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
        return EntityTypeUtil.getTypeFromId(Entity1_17Types.TYPES, n, Entity1_17Types.ENTITY);
    }
    
    static {
        $VALUES = new Entity1_17Types[] { Entity1_17Types.ENTITY, Entity1_17Types.AREA_EFFECT_CLOUD, Entity1_17Types.END_CRYSTAL, Entity1_17Types.EVOKER_FANGS, Entity1_17Types.EXPERIENCE_ORB, Entity1_17Types.EYE_OF_ENDER, Entity1_17Types.FALLING_BLOCK, Entity1_17Types.FIREWORK_ROCKET, Entity1_17Types.ITEM, Entity1_17Types.LLAMA_SPIT, Entity1_17Types.TNT, Entity1_17Types.SHULKER_BULLET, Entity1_17Types.FISHING_BOBBER, Entity1_17Types.LIVINGENTITY, Entity1_17Types.ARMOR_STAND, Entity1_17Types.MARKER, Entity1_17Types.PLAYER, Entity1_17Types.ABSTRACT_INSENTIENT, Entity1_17Types.ENDER_DRAGON, Entity1_17Types.BEE, Entity1_17Types.ABSTRACT_CREATURE, Entity1_17Types.ABSTRACT_AGEABLE, Entity1_17Types.VILLAGER, Entity1_17Types.WANDERING_TRADER, Entity1_17Types.ABSTRACT_ANIMAL, Entity1_17Types.AXOLOTL, Entity1_17Types.DOLPHIN, Entity1_17Types.CHICKEN, Entity1_17Types.COW, Entity1_17Types.MOOSHROOM, Entity1_17Types.PANDA, Entity1_17Types.PIG, Entity1_17Types.POLAR_BEAR, Entity1_17Types.RABBIT, Entity1_17Types.SHEEP, Entity1_17Types.TURTLE, Entity1_17Types.FOX, Entity1_17Types.GOAT, Entity1_17Types.ABSTRACT_TAMEABLE_ANIMAL, Entity1_17Types.CAT, Entity1_17Types.OCELOT, Entity1_17Types.WOLF, Entity1_17Types.ABSTRACT_PARROT, Entity1_17Types.PARROT, Entity1_17Types.ABSTRACT_HORSE, Entity1_17Types.CHESTED_HORSE, Entity1_17Types.DONKEY, Entity1_17Types.MULE, Entity1_17Types.LLAMA, Entity1_17Types.TRADER_LLAMA, Entity1_17Types.HORSE, Entity1_17Types.SKELETON_HORSE, Entity1_17Types.ZOMBIE_HORSE, Entity1_17Types.ABSTRACT_GOLEM, Entity1_17Types.SNOW_GOLEM, Entity1_17Types.IRON_GOLEM, Entity1_17Types.SHULKER, Entity1_17Types.ABSTRACT_FISHES, Entity1_17Types.COD, Entity1_17Types.PUFFERFISH, Entity1_17Types.SALMON, Entity1_17Types.TROPICAL_FISH, Entity1_17Types.ABSTRACT_MONSTER, Entity1_17Types.BLAZE, Entity1_17Types.CREEPER, Entity1_17Types.ENDERMITE, Entity1_17Types.ENDERMAN, Entity1_17Types.GIANT, Entity1_17Types.SILVERFISH, Entity1_17Types.VEX, Entity1_17Types.WITCH, Entity1_17Types.WITHER, Entity1_17Types.RAVAGER, Entity1_17Types.ABSTRACT_PIGLIN, Entity1_17Types.PIGLIN, Entity1_17Types.PIGLIN_BRUTE, Entity1_17Types.HOGLIN, Entity1_17Types.STRIDER, Entity1_17Types.ZOGLIN, Entity1_17Types.ABSTRACT_ILLAGER_BASE, Entity1_17Types.ABSTRACT_EVO_ILLU_ILLAGER, Entity1_17Types.EVOKER, Entity1_17Types.ILLUSIONER, Entity1_17Types.VINDICATOR, Entity1_17Types.PILLAGER, Entity1_17Types.ABSTRACT_SKELETON, Entity1_17Types.SKELETON, Entity1_17Types.STRAY, Entity1_17Types.WITHER_SKELETON, Entity1_17Types.GUARDIAN, Entity1_17Types.ELDER_GUARDIAN, Entity1_17Types.SPIDER, Entity1_17Types.CAVE_SPIDER, Entity1_17Types.ZOMBIE, Entity1_17Types.DROWNED, Entity1_17Types.HUSK, Entity1_17Types.ZOMBIFIED_PIGLIN, Entity1_17Types.ZOMBIE_VILLAGER, Entity1_17Types.ABSTRACT_FLYING, Entity1_17Types.GHAST, Entity1_17Types.PHANTOM, Entity1_17Types.ABSTRACT_AMBIENT, Entity1_17Types.BAT, Entity1_17Types.ABSTRACT_WATERMOB, Entity1_17Types.SQUID, Entity1_17Types.GLOW_SQUID, Entity1_17Types.SLIME, Entity1_17Types.MAGMA_CUBE, Entity1_17Types.ABSTRACT_HANGING, Entity1_17Types.LEASH_KNOT, Entity1_17Types.ITEM_FRAME, Entity1_17Types.GLOW_ITEM_FRAME, Entity1_17Types.PAINTING, Entity1_17Types.ABSTRACT_LIGHTNING, Entity1_17Types.LIGHTNING_BOLT, Entity1_17Types.ABSTRACT_ARROW, Entity1_17Types.ARROW, Entity1_17Types.SPECTRAL_ARROW, Entity1_17Types.TRIDENT, Entity1_17Types.ABSTRACT_FIREBALL, Entity1_17Types.DRAGON_FIREBALL, Entity1_17Types.FIREBALL, Entity1_17Types.SMALL_FIREBALL, Entity1_17Types.WITHER_SKULL, Entity1_17Types.PROJECTILE_ABSTRACT, Entity1_17Types.SNOWBALL, Entity1_17Types.ENDER_PEARL, Entity1_17Types.EGG, Entity1_17Types.POTION, Entity1_17Types.EXPERIENCE_BOTTLE, Entity1_17Types.MINECART_ABSTRACT, Entity1_17Types.CHESTED_MINECART_ABSTRACT, Entity1_17Types.CHEST_MINECART, Entity1_17Types.HOPPER_MINECART, Entity1_17Types.MINECART, Entity1_17Types.FURNACE_MINECART, Entity1_17Types.COMMAND_BLOCK_MINECART, Entity1_17Types.TNT_MINECART, Entity1_17Types.SPAWNER_MINECART, Entity1_17Types.BOAT };
        TYPES = EntityTypeUtil.toOrderedArray(values());
    }
}
