package com.viaversion.viaversion.api.minecraft.entities;

import com.viaversion.viaversion.util.*;

public enum Entity1_15Types implements EntityType
{
    ENTITY("ENTITY", 0, -1), 
    AREA_EFFECT_CLOUD("AREA_EFFECT_CLOUD", 1, 0, (EntityType)Entity1_15Types.ENTITY), 
    END_CRYSTAL("END_CRYSTAL", 2, 18, (EntityType)Entity1_15Types.ENTITY), 
    EVOKER_FANGS("EVOKER_FANGS", 3, 22, (EntityType)Entity1_15Types.ENTITY), 
    EXPERIENCE_ORB("EXPERIENCE_ORB", 4, 24, (EntityType)Entity1_15Types.ENTITY), 
    EYE_OF_ENDER("EYE_OF_ENDER", 5, 25, (EntityType)Entity1_15Types.ENTITY), 
    FALLING_BLOCK("FALLING_BLOCK", 6, 26, (EntityType)Entity1_15Types.ENTITY), 
    FIREWORK_ROCKET("FIREWORK_ROCKET", 7, 27, (EntityType)Entity1_15Types.ENTITY), 
    ITEM("ITEM", 8, 35, (EntityType)Entity1_15Types.ENTITY), 
    LLAMA_SPIT("LLAMA_SPIT", 9, 40, (EntityType)Entity1_15Types.ENTITY), 
    TNT("TNT", 10, 59, (EntityType)Entity1_15Types.ENTITY), 
    SHULKER_BULLET("SHULKER_BULLET", 11, 64, (EntityType)Entity1_15Types.ENTITY), 
    FISHING_BOBBER("FISHING_BOBBER", 12, 102, (EntityType)Entity1_15Types.ENTITY), 
    LIVINGENTITY("LIVINGENTITY", 13, -1, (EntityType)Entity1_15Types.ENTITY), 
    ARMOR_STAND("ARMOR_STAND", 14, 1, (EntityType)Entity1_15Types.LIVINGENTITY), 
    PLAYER("PLAYER", 15, 101, (EntityType)Entity1_15Types.LIVINGENTITY), 
    ABSTRACT_INSENTIENT("ABSTRACT_INSENTIENT", 16, -1, (EntityType)Entity1_15Types.LIVINGENTITY), 
    ENDER_DRAGON("ENDER_DRAGON", 17, 19, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    BEE("BEE", 18, 4, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_CREATURE("ABSTRACT_CREATURE", 19, -1, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    ABSTRACT_AGEABLE("ABSTRACT_AGEABLE", 20, -1, (EntityType)Entity1_15Types.ABSTRACT_CREATURE), 
    VILLAGER("VILLAGER", 21, 85, (EntityType)Entity1_15Types.ABSTRACT_AGEABLE), 
    WANDERING_TRADER("WANDERING_TRADER", 22, 89, (EntityType)Entity1_15Types.ABSTRACT_AGEABLE), 
    ABSTRACT_ANIMAL("ABSTRACT_ANIMAL", 23, -1, (EntityType)Entity1_15Types.ABSTRACT_AGEABLE), 
    DOLPHIN("DOLPHIN", 24, 14, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    CHICKEN("CHICKEN", 25, 9, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    COW("COW", 26, 11, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    MOOSHROOM("MOOSHROOM", 27, 50, (EntityType)Entity1_15Types.COW), 
    PANDA("PANDA", 28, 53, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    PIG("PIG", 29, 55, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    POLAR_BEAR("POLAR_BEAR", 30, 58, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    RABBIT("RABBIT", 31, 60, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    SHEEP("SHEEP", 32, 62, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    TURTLE("TURTLE", 33, 78, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    FOX("FOX", 34, 28, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    ABSTRACT_TAMEABLE_ANIMAL("ABSTRACT_TAMEABLE_ANIMAL", 35, -1, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    CAT("CAT", 36, 7, (EntityType)Entity1_15Types.ABSTRACT_TAMEABLE_ANIMAL), 
    OCELOT("OCELOT", 37, 51, (EntityType)Entity1_15Types.ABSTRACT_TAMEABLE_ANIMAL), 
    WOLF("WOLF", 38, 94, (EntityType)Entity1_15Types.ABSTRACT_TAMEABLE_ANIMAL), 
    ABSTRACT_PARROT("ABSTRACT_PARROT", 39, -1, (EntityType)Entity1_15Types.ABSTRACT_TAMEABLE_ANIMAL), 
    PARROT("PARROT", 40, 54, (EntityType)Entity1_15Types.ABSTRACT_PARROT), 
    ABSTRACT_HORSE("ABSTRACT_HORSE", 41, -1, (EntityType)Entity1_15Types.ABSTRACT_ANIMAL), 
    CHESTED_HORSE("CHESTED_HORSE", 42, -1, (EntityType)Entity1_15Types.ABSTRACT_HORSE), 
    DONKEY("DONKEY", 43, 13, (EntityType)Entity1_15Types.CHESTED_HORSE), 
    MULE("MULE", 44, 49, (EntityType)Entity1_15Types.CHESTED_HORSE), 
    LLAMA("LLAMA", 45, 39, (EntityType)Entity1_15Types.CHESTED_HORSE), 
    TRADER_LLAMA("TRADER_LLAMA", 46, 76, (EntityType)Entity1_15Types.CHESTED_HORSE), 
    HORSE("HORSE", 47, 32, (EntityType)Entity1_15Types.ABSTRACT_HORSE), 
    SKELETON_HORSE("SKELETON_HORSE", 48, 67, (EntityType)Entity1_15Types.ABSTRACT_HORSE), 
    ZOMBIE_HORSE("ZOMBIE_HORSE", 49, 96, (EntityType)Entity1_15Types.ABSTRACT_HORSE), 
    ABSTRACT_GOLEM("ABSTRACT_GOLEM", 50, -1, (EntityType)Entity1_15Types.ABSTRACT_CREATURE), 
    SNOW_GOLEM("SNOW_GOLEM", 51, 70, (EntityType)Entity1_15Types.ABSTRACT_GOLEM), 
    IRON_GOLEM("IRON_GOLEM", 52, 86, (EntityType)Entity1_15Types.ABSTRACT_GOLEM), 
    SHULKER("SHULKER", 53, 63, (EntityType)Entity1_15Types.ABSTRACT_GOLEM), 
    ABSTRACT_FISHES("ABSTRACT_FISHES", 54, -1, (EntityType)Entity1_15Types.ABSTRACT_CREATURE), 
    COD("COD", 55, 10, (EntityType)Entity1_15Types.ABSTRACT_FISHES), 
    PUFFERFISH("PUFFERFISH", 56, 56, (EntityType)Entity1_15Types.ABSTRACT_FISHES), 
    SALMON("SALMON", 57, 61, (EntityType)Entity1_15Types.ABSTRACT_FISHES), 
    TROPICAL_FISH("TROPICAL_FISH", 58, 77, (EntityType)Entity1_15Types.ABSTRACT_FISHES), 
    ABSTRACT_MONSTER("ABSTRACT_MONSTER", 59, -1, (EntityType)Entity1_15Types.ABSTRACT_CREATURE), 
    BLAZE("BLAZE", 60, 5, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    CREEPER("CREEPER", 61, 12, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    ENDERMITE("ENDERMITE", 62, 21, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    ENDERMAN("ENDERMAN", 63, 20, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    GIANT("GIANT", 64, 30, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    SILVERFISH("SILVERFISH", 65, 65, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    VEX("VEX", 66, 84, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    WITCH("WITCH", 67, 90, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    WITHER("WITHER", 68, 91, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    RAVAGER("RAVAGER", 69, 99, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    ABSTRACT_ILLAGER_BASE("ABSTRACT_ILLAGER_BASE", 70, -1, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    ABSTRACT_EVO_ILLU_ILLAGER("ABSTRACT_EVO_ILLU_ILLAGER", 71, -1, (EntityType)Entity1_15Types.ABSTRACT_ILLAGER_BASE), 
    EVOKER("EVOKER", 72, 23, (EntityType)Entity1_15Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    ILLUSIONER("ILLUSIONER", 73, 34, (EntityType)Entity1_15Types.ABSTRACT_EVO_ILLU_ILLAGER), 
    VINDICATOR("VINDICATOR", 74, 87, (EntityType)Entity1_15Types.ABSTRACT_ILLAGER_BASE), 
    PILLAGER("PILLAGER", 75, 88, (EntityType)Entity1_15Types.ABSTRACT_ILLAGER_BASE), 
    ABSTRACT_SKELETON("ABSTRACT_SKELETON", 76, -1, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    SKELETON("SKELETON", 77, 66, (EntityType)Entity1_15Types.ABSTRACT_SKELETON), 
    STRAY("STRAY", 78, 75, (EntityType)Entity1_15Types.ABSTRACT_SKELETON), 
    WITHER_SKELETON("WITHER_SKELETON", 79, 92, (EntityType)Entity1_15Types.ABSTRACT_SKELETON), 
    GUARDIAN("GUARDIAN", 80, 31, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    ELDER_GUARDIAN("ELDER_GUARDIAN", 81, 17, (EntityType)Entity1_15Types.GUARDIAN), 
    SPIDER("SPIDER", 82, 73, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    CAVE_SPIDER("CAVE_SPIDER", 83, 8, (EntityType)Entity1_15Types.SPIDER), 
    ZOMBIE("ZOMBIE", 84, 95, (EntityType)Entity1_15Types.ABSTRACT_MONSTER), 
    DROWNED("DROWNED", 85, 16, (EntityType)Entity1_15Types.ZOMBIE), 
    HUSK("HUSK", 86, 33, (EntityType)Entity1_15Types.ZOMBIE), 
    ZOMBIE_PIGMAN("ZOMBIE_PIGMAN", 87, 57, (EntityType)Entity1_15Types.ZOMBIE), 
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 88, 97, (EntityType)Entity1_15Types.ZOMBIE), 
    ABSTRACT_FLYING("ABSTRACT_FLYING", 89, -1, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    GHAST("GHAST", 90, 29, (EntityType)Entity1_15Types.ABSTRACT_FLYING), 
    PHANTOM("PHANTOM", 91, 98, (EntityType)Entity1_15Types.ABSTRACT_FLYING), 
    ABSTRACT_AMBIENT("ABSTRACT_AMBIENT", 92, -1, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    BAT("BAT", 93, 3, (EntityType)Entity1_15Types.ABSTRACT_AMBIENT), 
    ABSTRACT_WATERMOB("ABSTRACT_WATERMOB", 94, -1, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    SQUID("SQUID", 95, 74, (EntityType)Entity1_15Types.ABSTRACT_WATERMOB), 
    SLIME("SLIME", 96, 68, (EntityType)Entity1_15Types.ABSTRACT_INSENTIENT), 
    MAGMA_CUBE("MAGMA_CUBE", 97, 41, (EntityType)Entity1_15Types.SLIME), 
    ABSTRACT_HANGING("ABSTRACT_HANGING", 98, -1, (EntityType)Entity1_15Types.ENTITY), 
    LEASH_KNOT("LEASH_KNOT", 99, 38, (EntityType)Entity1_15Types.ABSTRACT_HANGING), 
    ITEM_FRAME("ITEM_FRAME", 100, 36, (EntityType)Entity1_15Types.ABSTRACT_HANGING), 
    PAINTING("PAINTING", 101, 52, (EntityType)Entity1_15Types.ABSTRACT_HANGING), 
    ABSTRACT_LIGHTNING("ABSTRACT_LIGHTNING", 102, -1, (EntityType)Entity1_15Types.ENTITY), 
    LIGHTNING_BOLT("LIGHTNING_BOLT", 103, 100, (EntityType)Entity1_15Types.ABSTRACT_LIGHTNING), 
    ABSTRACT_ARROW("ABSTRACT_ARROW", 104, -1, (EntityType)Entity1_15Types.ENTITY), 
    ARROW("ARROW", 105, 2, (EntityType)Entity1_15Types.ABSTRACT_ARROW), 
    SPECTRAL_ARROW("SPECTRAL_ARROW", 106, 72, (EntityType)Entity1_15Types.ABSTRACT_ARROW), 
    TRIDENT("TRIDENT", 107, 83, (EntityType)Entity1_15Types.ABSTRACT_ARROW), 
    ABSTRACT_FIREBALL("ABSTRACT_FIREBALL", 108, -1, (EntityType)Entity1_15Types.ENTITY), 
    DRAGON_FIREBALL("DRAGON_FIREBALL", 109, 15, (EntityType)Entity1_15Types.ABSTRACT_FIREBALL), 
    FIREBALL("FIREBALL", 110, 37, (EntityType)Entity1_15Types.ABSTRACT_FIREBALL), 
    SMALL_FIREBALL("SMALL_FIREBALL", 111, 69, (EntityType)Entity1_15Types.ABSTRACT_FIREBALL), 
    WITHER_SKULL("WITHER_SKULL", 112, 93, (EntityType)Entity1_15Types.ABSTRACT_FIREBALL), 
    PROJECTILE_ABSTRACT("PROJECTILE_ABSTRACT", 113, -1, (EntityType)Entity1_15Types.ENTITY), 
    SNOWBALL("SNOWBALL", 114, 71, (EntityType)Entity1_15Types.PROJECTILE_ABSTRACT), 
    ENDER_PEARL("ENDER_PEARL", 115, 80, (EntityType)Entity1_15Types.PROJECTILE_ABSTRACT), 
    EGG("EGG", 116, 79, (EntityType)Entity1_15Types.PROJECTILE_ABSTRACT), 
    POTION("POTION", 117, 82, (EntityType)Entity1_15Types.PROJECTILE_ABSTRACT), 
    EXPERIENCE_BOTTLE("EXPERIENCE_BOTTLE", 118, 81, (EntityType)Entity1_15Types.PROJECTILE_ABSTRACT), 
    MINECART_ABSTRACT("MINECART_ABSTRACT", 119, -1, (EntityType)Entity1_15Types.ENTITY), 
    CHESTED_MINECART_ABSTRACT("CHESTED_MINECART_ABSTRACT", 120, -1, (EntityType)Entity1_15Types.MINECART_ABSTRACT), 
    CHEST_MINECART("CHEST_MINECART", 121, 43, (EntityType)Entity1_15Types.CHESTED_MINECART_ABSTRACT), 
    HOPPER_MINECART("HOPPER_MINECART", 122, 46, (EntityType)Entity1_15Types.CHESTED_MINECART_ABSTRACT), 
    MINECART("MINECART", 123, 42, (EntityType)Entity1_15Types.MINECART_ABSTRACT), 
    FURNACE_MINECART("FURNACE_MINECART", 124, 45, (EntityType)Entity1_15Types.MINECART_ABSTRACT), 
    COMMAND_BLOCK_MINECART("COMMAND_BLOCK_MINECART", 125, 44, (EntityType)Entity1_15Types.MINECART_ABSTRACT), 
    TNT_MINECART("TNT_MINECART", 126, 48, (EntityType)Entity1_15Types.MINECART_ABSTRACT), 
    SPAWNER_MINECART("SPAWNER_MINECART", 127, 47, (EntityType)Entity1_15Types.MINECART_ABSTRACT), 
    BOAT("BOAT", 128, 6, (EntityType)Entity1_15Types.ENTITY);
    
    private static final EntityType[] TYPES;
    private final int id;
    private final EntityType parent;
    private static final Entity1_15Types[] $VALUES;
    
    private Entity1_15Types(final String s, final int n, final int id) {
        this.id = id;
        this.parent = null;
    }
    
    private Entity1_15Types(final String s, final int n, final int id, final EntityType parent) {
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
        return EntityTypeUtil.getTypeFromId(Entity1_15Types.TYPES, n, Entity1_15Types.ENTITY);
    }
    
    static {
        $VALUES = new Entity1_15Types[] { Entity1_15Types.ENTITY, Entity1_15Types.AREA_EFFECT_CLOUD, Entity1_15Types.END_CRYSTAL, Entity1_15Types.EVOKER_FANGS, Entity1_15Types.EXPERIENCE_ORB, Entity1_15Types.EYE_OF_ENDER, Entity1_15Types.FALLING_BLOCK, Entity1_15Types.FIREWORK_ROCKET, Entity1_15Types.ITEM, Entity1_15Types.LLAMA_SPIT, Entity1_15Types.TNT, Entity1_15Types.SHULKER_BULLET, Entity1_15Types.FISHING_BOBBER, Entity1_15Types.LIVINGENTITY, Entity1_15Types.ARMOR_STAND, Entity1_15Types.PLAYER, Entity1_15Types.ABSTRACT_INSENTIENT, Entity1_15Types.ENDER_DRAGON, Entity1_15Types.BEE, Entity1_15Types.ABSTRACT_CREATURE, Entity1_15Types.ABSTRACT_AGEABLE, Entity1_15Types.VILLAGER, Entity1_15Types.WANDERING_TRADER, Entity1_15Types.ABSTRACT_ANIMAL, Entity1_15Types.DOLPHIN, Entity1_15Types.CHICKEN, Entity1_15Types.COW, Entity1_15Types.MOOSHROOM, Entity1_15Types.PANDA, Entity1_15Types.PIG, Entity1_15Types.POLAR_BEAR, Entity1_15Types.RABBIT, Entity1_15Types.SHEEP, Entity1_15Types.TURTLE, Entity1_15Types.FOX, Entity1_15Types.ABSTRACT_TAMEABLE_ANIMAL, Entity1_15Types.CAT, Entity1_15Types.OCELOT, Entity1_15Types.WOLF, Entity1_15Types.ABSTRACT_PARROT, Entity1_15Types.PARROT, Entity1_15Types.ABSTRACT_HORSE, Entity1_15Types.CHESTED_HORSE, Entity1_15Types.DONKEY, Entity1_15Types.MULE, Entity1_15Types.LLAMA, Entity1_15Types.TRADER_LLAMA, Entity1_15Types.HORSE, Entity1_15Types.SKELETON_HORSE, Entity1_15Types.ZOMBIE_HORSE, Entity1_15Types.ABSTRACT_GOLEM, Entity1_15Types.SNOW_GOLEM, Entity1_15Types.IRON_GOLEM, Entity1_15Types.SHULKER, Entity1_15Types.ABSTRACT_FISHES, Entity1_15Types.COD, Entity1_15Types.PUFFERFISH, Entity1_15Types.SALMON, Entity1_15Types.TROPICAL_FISH, Entity1_15Types.ABSTRACT_MONSTER, Entity1_15Types.BLAZE, Entity1_15Types.CREEPER, Entity1_15Types.ENDERMITE, Entity1_15Types.ENDERMAN, Entity1_15Types.GIANT, Entity1_15Types.SILVERFISH, Entity1_15Types.VEX, Entity1_15Types.WITCH, Entity1_15Types.WITHER, Entity1_15Types.RAVAGER, Entity1_15Types.ABSTRACT_ILLAGER_BASE, Entity1_15Types.ABSTRACT_EVO_ILLU_ILLAGER, Entity1_15Types.EVOKER, Entity1_15Types.ILLUSIONER, Entity1_15Types.VINDICATOR, Entity1_15Types.PILLAGER, Entity1_15Types.ABSTRACT_SKELETON, Entity1_15Types.SKELETON, Entity1_15Types.STRAY, Entity1_15Types.WITHER_SKELETON, Entity1_15Types.GUARDIAN, Entity1_15Types.ELDER_GUARDIAN, Entity1_15Types.SPIDER, Entity1_15Types.CAVE_SPIDER, Entity1_15Types.ZOMBIE, Entity1_15Types.DROWNED, Entity1_15Types.HUSK, Entity1_15Types.ZOMBIE_PIGMAN, Entity1_15Types.ZOMBIE_VILLAGER, Entity1_15Types.ABSTRACT_FLYING, Entity1_15Types.GHAST, Entity1_15Types.PHANTOM, Entity1_15Types.ABSTRACT_AMBIENT, Entity1_15Types.BAT, Entity1_15Types.ABSTRACT_WATERMOB, Entity1_15Types.SQUID, Entity1_15Types.SLIME, Entity1_15Types.MAGMA_CUBE, Entity1_15Types.ABSTRACT_HANGING, Entity1_15Types.LEASH_KNOT, Entity1_15Types.ITEM_FRAME, Entity1_15Types.PAINTING, Entity1_15Types.ABSTRACT_LIGHTNING, Entity1_15Types.LIGHTNING_BOLT, Entity1_15Types.ABSTRACT_ARROW, Entity1_15Types.ARROW, Entity1_15Types.SPECTRAL_ARROW, Entity1_15Types.TRIDENT, Entity1_15Types.ABSTRACT_FIREBALL, Entity1_15Types.DRAGON_FIREBALL, Entity1_15Types.FIREBALL, Entity1_15Types.SMALL_FIREBALL, Entity1_15Types.WITHER_SKULL, Entity1_15Types.PROJECTILE_ABSTRACT, Entity1_15Types.SNOWBALL, Entity1_15Types.ENDER_PEARL, Entity1_15Types.EGG, Entity1_15Types.POTION, Entity1_15Types.EXPERIENCE_BOTTLE, Entity1_15Types.MINECART_ABSTRACT, Entity1_15Types.CHESTED_MINECART_ABSTRACT, Entity1_15Types.CHEST_MINECART, Entity1_15Types.HOPPER_MINECART, Entity1_15Types.MINECART, Entity1_15Types.FURNACE_MINECART, Entity1_15Types.COMMAND_BLOCK_MINECART, Entity1_15Types.TNT_MINECART, Entity1_15Types.SPAWNER_MINECART, Entity1_15Types.BOAT };
        TYPES = EntityTypeUtil.toOrderedArray(values());
    }
}
