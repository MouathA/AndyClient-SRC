package de.gerrygames.viarewind.protocol.protocol1_8to1_7_6_10.metadata;

import com.viaversion.viaversion.api.minecraft.entities.*;
import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types.*;
import java.util.*;
import com.viaversion.viaversion.util.*;

public enum MetaIndex1_8to1_7_6_10
{
    ENTITY_FLAGS("ENTITY_FLAGS", 0, Entity1_10Types.EntityType.ENTITY, 0, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    ENTITY_AIR("ENTITY_AIR", 1, Entity1_10Types.EntityType.ENTITY, 1, MetaType1_7_6_10.Short, MetaType1_8.Short), 
    ENTITY_NAME_TAG("ENTITY_NAME_TAG", 2, Entity1_10Types.EntityType.ENTITY, -1, MetaType1_7_6_10.NonExistent, 2, MetaType1_8.String), 
    ENTITY_NAME_TAG_VISIBILITY("ENTITY_NAME_TAG_VISIBILITY", 3, Entity1_10Types.EntityType.ENTITY, -1, MetaType1_7_6_10.NonExistent, 3, MetaType1_8.Byte), 
    ENTITY_SILENT("ENTITY_SILENT", 4, Entity1_10Types.EntityType.ENTITY, -1, MetaType1_7_6_10.NonExistent, 4, MetaType1_8.Byte), 
    ENTITY_LIVING_HEALTH("ENTITY_LIVING_HEALTH", 5, Entity1_10Types.EntityType.ENTITY_LIVING, 6, MetaType1_7_6_10.Float, MetaType1_8.Float), 
    ENTITY_LIVING_POTION_EFFECT_COLOR("ENTITY_LIVING_POTION_EFFECT_COLOR", 6, Entity1_10Types.EntityType.ENTITY_LIVING, 7, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    ENTITY_LIVING_IS_POTION_EFFECT_AMBIENT("ENTITY_LIVING_IS_POTION_EFFECT_AMBIENT", 7, Entity1_10Types.EntityType.ENTITY_LIVING, 8, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    ENTITY_LIVING_ARROWS("ENTITY_LIVING_ARROWS", 8, Entity1_10Types.EntityType.ENTITY_LIVING, 9, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    ENTITY_LIVING_NAME_TAG("ENTITY_LIVING_NAME_TAG", 9, Entity1_10Types.EntityType.ENTITY_LIVING, 10, MetaType1_7_6_10.String, 2, MetaType1_8.String), 
    ENTITY_LIVING_NAME_TAG_VISIBILITY("ENTITY_LIVING_NAME_TAG_VISIBILITY", 10, Entity1_10Types.EntityType.ENTITY_LIVING, 11, MetaType1_7_6_10.Byte, 3, MetaType1_8.Byte), 
    ENTITY_LIVING_AI("ENTITY_LIVING_AI", 11, Entity1_10Types.EntityType.ENTITY_LIVING, -1, MetaType1_7_6_10.NonExistent, 15, MetaType1_8.Byte), 
    ENTITY_AGEABLE_AGE("ENTITY_AGEABLE_AGE", 12, Entity1_10Types.EntityType.ENTITY_AGEABLE, 12, MetaType1_7_6_10.Int, MetaType1_8.Byte), 
    ARMOR_STAND_FLAGS("ARMOR_STAND_FLAGS", 13, Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 10, MetaType1_8.Byte), 
    ARMOR_STAND_HEAD_POSITION("ARMOR_STAND_HEAD_POSITION", 14, Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 11, MetaType1_8.Rotation), 
    ARMOR_STAND_BODY_POSITION("ARMOR_STAND_BODY_POSITION", 15, Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 12, MetaType1_8.Rotation), 
    ARMOR_STAND_LEFT_ARM_POSITION("ARMOR_STAND_LEFT_ARM_POSITION", 16, Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 13, MetaType1_8.Rotation), 
    ARMOR_STAND_RIGHT_ARM_POSITION("ARMOR_STAND_RIGHT_ARM_POSITION", 17, Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 14, MetaType1_8.Rotation), 
    ARMOR_STAND_LEFT_LEG_POSITION("ARMOR_STAND_LEFT_LEG_POSITION", 18, Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 15, MetaType1_8.Rotation), 
    ARMOR_STAND_RIGHT_LEG_POSITION("ARMOR_STAND_RIGHT_LEG_POSITION", 19, Entity1_10Types.EntityType.ARMOR_STAND, -1, MetaType1_7_6_10.NonExistent, 16, MetaType1_8.Rotation), 
    HUMAN_SKIN_FLAGS("HUMAN_SKIN_FLAGS", 20, Entity1_10Types.EntityType.ENTITY_HUMAN, 16, MetaType1_7_6_10.Byte, 10, MetaType1_8.Byte), 
    HUMAN_UNUSED("HUMAN_UNUSED", 21, Entity1_10Types.EntityType.ENTITY_HUMAN, -1, MetaType1_7_6_10.NonExistent, 16, MetaType1_8.Byte), 
    HUMAN_ABSORPTION_HEATS("HUMAN_ABSORPTION_HEATS", 22, Entity1_10Types.EntityType.ENTITY_HUMAN, 17, MetaType1_7_6_10.Float, MetaType1_8.Float), 
    HUMAN_SCORE("HUMAN_SCORE", 23, Entity1_10Types.EntityType.ENTITY_HUMAN, 18, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    HORSE_FLAGS("HORSE_FLAGS", 24, Entity1_10Types.EntityType.HORSE, 16, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    HORSE_TYPE("HORSE_TYPE", 25, Entity1_10Types.EntityType.HORSE, 19, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    HORSE_COLOR("HORSE_COLOR", 26, Entity1_10Types.EntityType.HORSE, 20, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    HORSE_OWNER("HORSE_OWNER", 27, Entity1_10Types.EntityType.HORSE, 21, MetaType1_7_6_10.String, MetaType1_8.String), 
    HORSE_ARMOR("HORSE_ARMOR", 28, Entity1_10Types.EntityType.HORSE, 22, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    BAT_HANGING("BAT_HANGING", 29, Entity1_10Types.EntityType.BAT, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    TAMEABLE_FLAGS("TAMEABLE_FLAGS", 30, Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    TAMEABLE_OWNER("TAMEABLE_OWNER", 31, Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 17, MetaType1_7_6_10.String, MetaType1_8.String), 
    OCELOT_TYPE("OCELOT_TYPE", 32, Entity1_10Types.EntityType.OCELOT, 18, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    WOLF_FLAGS("WOLF_FLAGS", 33, Entity1_10Types.EntityType.WOLF, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    WOLF_HEALTH("WOLF_HEALTH", 34, Entity1_10Types.EntityType.WOLF, 18, MetaType1_7_6_10.Float, MetaType1_8.Float), 
    WOLF_BEGGING("WOLF_BEGGING", 35, Entity1_10Types.EntityType.WOLF, 19, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    WOLF_COLLAR_COLOR("WOLF_COLLAR_COLOR", 36, Entity1_10Types.EntityType.WOLF, 20, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    PIG_SADDLE("PIG_SADDLE", 37, Entity1_10Types.EntityType.PIG, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    SHEEP_COLOR_OR_SHEARED("SHEEP_COLOR_OR_SHEARED", 38, Entity1_10Types.EntityType.SHEEP, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    VILLAGER_TYPE("VILLAGER_TYPE", 39, Entity1_10Types.EntityType.VILLAGER, 16, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    ENDERMAN_CARRIED_BLOCK("ENDERMAN_CARRIED_BLOCK", 40, Entity1_10Types.EntityType.ENDERMAN, 16, MetaType1_7_6_10.NonExistent, MetaType1_8.Short), 
    ENDERMAN_CARRIED_BLOCK_DATA("ENDERMAN_CARRIED_BLOCK_DATA", 41, Entity1_10Types.EntityType.ENDERMAN, 17, MetaType1_7_6_10.NonExistent, MetaType1_8.Byte), 
    ENDERMAN_IS_SCREAMING("ENDERMAN_IS_SCREAMING", 42, Entity1_10Types.EntityType.ENDERMAN, 18, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    ZOMBIE_CHILD("ZOMBIE_CHILD", 43, Entity1_10Types.EntityType.ZOMBIE, 12, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    ZOMBIE_VILLAGER("ZOMBIE_VILLAGER", 44, Entity1_10Types.EntityType.ZOMBIE, 13, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    ZOMBIE_CONVERTING("ZOMBIE_CONVERTING", 45, Entity1_10Types.EntityType.ZOMBIE, 14, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    BLAZE_ON_FIRE("BLAZE_ON_FIRE", 46, Entity1_10Types.EntityType.BLAZE, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    SPIDER_CLIMBING("SPIDER_CLIMBING", 47, Entity1_10Types.EntityType.SPIDER, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    CREEPER_STATE("CREEPER_STATE", 48, Entity1_10Types.EntityType.CREEPER, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    CREEPER_POWERED("CREEPER_POWERED", 49, Entity1_10Types.EntityType.CREEPER, 17, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    GHAST_STATE("GHAST_STATE", 50, Entity1_10Types.EntityType.GHAST, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    GHAST_IS_POWERED("GHAST_IS_POWERED", 51, Entity1_10Types.EntityType.GHAST, 17, MetaType1_7_6_10.NonExistent, MetaType1_8.Byte), 
    SLIME_SIZE("SLIME_SIZE", 52, Entity1_10Types.EntityType.SLIME, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    SKELETON_TYPE("SKELETON_TYPE", 53, Entity1_10Types.EntityType.SKELETON, 13, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    WITCH_AGRESSIVE("WITCH_AGRESSIVE", 54, Entity1_10Types.EntityType.WITCH, 21, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    IRON_GOLEM_IS_PLAYER_CREATED("IRON_GOLEM_IS_PLAYER_CREATED", 55, Entity1_10Types.EntityType.IRON_GOLEM, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    WITHER_WATCHED_TAGRET_1("WITHER_WATCHED_TAGRET_1", 56, Entity1_10Types.EntityType.WITHER, 17, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    WITHER_WATCHED_TAGRET_2("WITHER_WATCHED_TAGRET_2", 57, Entity1_10Types.EntityType.WITHER, 18, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    WITHER_WATCHED_TAGRET_3("WITHER_WATCHED_TAGRET_3", 58, Entity1_10Types.EntityType.WITHER, 19, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    WITHER_INVULNERABLE_TIME("WITHER_INVULNERABLE_TIME", 59, Entity1_10Types.EntityType.WITHER, 20, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    GUARDIAN_FLAGS("GUARDIAN_FLAGS", 60, Entity1_10Types.EntityType.GUARDIAN, 16, MetaType1_7_6_10.NonExistent, MetaType1_8.Byte), 
    GUARDIAN_TARGET("GUARDIAN_TARGET", 61, Entity1_10Types.EntityType.GUARDIAN, 17, MetaType1_7_6_10.NonExistent, MetaType1_8.Int), 
    BOAT_TIME_SINCE_HIT("BOAT_TIME_SINCE_HIT", 62, Entity1_10Types.EntityType.BOAT, 17, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    BOAT_FORWARD_DIRECTION("BOAT_FORWARD_DIRECTION", 63, Entity1_10Types.EntityType.BOAT, 18, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    BOAT_DAMAGE_TAKEN("BOAT_DAMAGE_TAKEN", 64, Entity1_10Types.EntityType.BOAT, 19, MetaType1_7_6_10.Float, MetaType1_8.Float), 
    MINECART_SHAKING_POWER("MINECART_SHAKING_POWER", 65, Entity1_10Types.EntityType.MINECART_ABSTRACT, 17, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    MINECART_SHAKING_DIRECTION("MINECART_SHAKING_DIRECTION", 66, Entity1_10Types.EntityType.MINECART_ABSTRACT, 18, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    MINECART_DAMAGE_TAKEN("MINECART_DAMAGE_TAKEN", 67, Entity1_10Types.EntityType.MINECART_ABSTRACT, 19, MetaType1_7_6_10.Float, MetaType1_8.Float), 
    MINECART_BLOCK_INSIDE("MINECART_BLOCK_INSIDE", 68, Entity1_10Types.EntityType.MINECART_ABSTRACT, 20, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    MINECART_BLOCK_Y("MINECART_BLOCK_Y", 69, Entity1_10Types.EntityType.MINECART_ABSTRACT, 21, MetaType1_7_6_10.Int, MetaType1_8.Int), 
    MINECART_SHOW_BLOCK("MINECART_SHOW_BLOCK", 70, Entity1_10Types.EntityType.MINECART_ABSTRACT, 22, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    FURNACE_MINECART_IS_POWERED("FURNACE_MINECART_IS_POWERED", 71, Entity1_10Types.EntityType.MINECART_FURNACE, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    ITEM_ITEM("ITEM_ITEM", 72, Entity1_10Types.EntityType.DROPPED_ITEM, 10, MetaType1_7_6_10.Slot, MetaType1_8.Slot), 
    ARROW_IS_CRITICAL("ARROW_IS_CRITICAL", 73, Entity1_10Types.EntityType.ARROW, 16, MetaType1_7_6_10.Byte, MetaType1_8.Byte), 
    FIREWORK_INFO("FIREWORK_INFO", 74, Entity1_10Types.EntityType.FIREWORK, 8, MetaType1_7_6_10.Slot, MetaType1_8.Slot), 
    ITEM_FRAME_ITEM("ITEM_FRAME_ITEM", 75, Entity1_10Types.EntityType.ITEM_FRAME, 2, MetaType1_7_6_10.Slot, 8, MetaType1_8.Slot), 
    ITEM_FRAME_ROTATION("ITEM_FRAME_ROTATION", 76, Entity1_10Types.EntityType.ITEM_FRAME, 3, MetaType1_7_6_10.Byte, 9, MetaType1_8.Byte), 
    ENDER_CRYSTAL_HEALTH("ENDER_CRYSTAL_HEALTH", 77, Entity1_10Types.EntityType.ENDER_CRYSTAL, 8, MetaType1_7_6_10.Int, 9, MetaType1_8.Int);
    
    private static final HashMap metadataRewrites;
    private Entity1_10Types.EntityType clazz;
    private int newIndex;
    private MetaType1_8 newType;
    private MetaType1_7_6_10 oldType;
    private int index;
    private static final MetaIndex1_8to1_7_6_10[] $VALUES;
    
    private MetaIndex1_8to1_7_6_10(final String s, final int n, final Entity1_10Types.EntityType clazz, final int n2, final MetaType1_7_6_10 oldType, final MetaType1_8 newType) {
        this.clazz = clazz;
        this.index = n2;
        this.newIndex = n2;
        this.oldType = oldType;
        this.newType = newType;
    }
    
    private MetaIndex1_8to1_7_6_10(final String s, final int n, final Entity1_10Types.EntityType clazz, final int index, final MetaType1_7_6_10 oldType, final int newIndex, final MetaType1_8 newType) {
        this.clazz = clazz;
        this.index = index;
        this.oldType = oldType;
        this.newIndex = newIndex;
        this.newType = newType;
    }
    
    private static Optional getIndex(final Entity1_10Types.EntityType entityType, final int n) {
        final Pair pair = new Pair(entityType, n);
        if (MetaIndex1_8to1_7_6_10.metadataRewrites.containsKey(pair)) {
            return Optional.of(MetaIndex1_8to1_7_6_10.metadataRewrites.get(pair));
        }
        return Optional.empty();
    }
    
    public Entity1_10Types.EntityType getClazz() {
        return this.clazz;
    }
    
    public int getNewIndex() {
        return this.newIndex;
    }
    
    public MetaType1_8 getNewType() {
        return this.newType;
    }
    
    public MetaType1_7_6_10 getOldType() {
        return this.oldType;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public static MetaIndex1_8to1_7_6_10 searchIndex(final Entity1_10Types.EntityType entityType, final int n) {
        Entity1_10Types.EntityType parent = entityType;
        do {
            final Optional index = getIndex(parent, n);
            if (index.isPresent()) {
                return index.get();
            }
            parent = parent.getParent();
        } while (parent != null);
        return null;
    }
    
    static {
        $VALUES = new MetaIndex1_8to1_7_6_10[] { MetaIndex1_8to1_7_6_10.ENTITY_FLAGS, MetaIndex1_8to1_7_6_10.ENTITY_AIR, MetaIndex1_8to1_7_6_10.ENTITY_NAME_TAG, MetaIndex1_8to1_7_6_10.ENTITY_NAME_TAG_VISIBILITY, MetaIndex1_8to1_7_6_10.ENTITY_SILENT, MetaIndex1_8to1_7_6_10.ENTITY_LIVING_HEALTH, MetaIndex1_8to1_7_6_10.ENTITY_LIVING_POTION_EFFECT_COLOR, MetaIndex1_8to1_7_6_10.ENTITY_LIVING_IS_POTION_EFFECT_AMBIENT, MetaIndex1_8to1_7_6_10.ENTITY_LIVING_ARROWS, MetaIndex1_8to1_7_6_10.ENTITY_LIVING_NAME_TAG, MetaIndex1_8to1_7_6_10.ENTITY_LIVING_NAME_TAG_VISIBILITY, MetaIndex1_8to1_7_6_10.ENTITY_LIVING_AI, MetaIndex1_8to1_7_6_10.ENTITY_AGEABLE_AGE, MetaIndex1_8to1_7_6_10.ARMOR_STAND_FLAGS, MetaIndex1_8to1_7_6_10.ARMOR_STAND_HEAD_POSITION, MetaIndex1_8to1_7_6_10.ARMOR_STAND_BODY_POSITION, MetaIndex1_8to1_7_6_10.ARMOR_STAND_LEFT_ARM_POSITION, MetaIndex1_8to1_7_6_10.ARMOR_STAND_RIGHT_ARM_POSITION, MetaIndex1_8to1_7_6_10.ARMOR_STAND_LEFT_LEG_POSITION, MetaIndex1_8to1_7_6_10.ARMOR_STAND_RIGHT_LEG_POSITION, MetaIndex1_8to1_7_6_10.HUMAN_SKIN_FLAGS, MetaIndex1_8to1_7_6_10.HUMAN_UNUSED, MetaIndex1_8to1_7_6_10.HUMAN_ABSORPTION_HEATS, MetaIndex1_8to1_7_6_10.HUMAN_SCORE, MetaIndex1_8to1_7_6_10.HORSE_FLAGS, MetaIndex1_8to1_7_6_10.HORSE_TYPE, MetaIndex1_8to1_7_6_10.HORSE_COLOR, MetaIndex1_8to1_7_6_10.HORSE_OWNER, MetaIndex1_8to1_7_6_10.HORSE_ARMOR, MetaIndex1_8to1_7_6_10.BAT_HANGING, MetaIndex1_8to1_7_6_10.TAMEABLE_FLAGS, MetaIndex1_8to1_7_6_10.TAMEABLE_OWNER, MetaIndex1_8to1_7_6_10.OCELOT_TYPE, MetaIndex1_8to1_7_6_10.WOLF_FLAGS, MetaIndex1_8to1_7_6_10.WOLF_HEALTH, MetaIndex1_8to1_7_6_10.WOLF_BEGGING, MetaIndex1_8to1_7_6_10.WOLF_COLLAR_COLOR, MetaIndex1_8to1_7_6_10.PIG_SADDLE, MetaIndex1_8to1_7_6_10.SHEEP_COLOR_OR_SHEARED, MetaIndex1_8to1_7_6_10.VILLAGER_TYPE, MetaIndex1_8to1_7_6_10.ENDERMAN_CARRIED_BLOCK, MetaIndex1_8to1_7_6_10.ENDERMAN_CARRIED_BLOCK_DATA, MetaIndex1_8to1_7_6_10.ENDERMAN_IS_SCREAMING, MetaIndex1_8to1_7_6_10.ZOMBIE_CHILD, MetaIndex1_8to1_7_6_10.ZOMBIE_VILLAGER, MetaIndex1_8to1_7_6_10.ZOMBIE_CONVERTING, MetaIndex1_8to1_7_6_10.BLAZE_ON_FIRE, MetaIndex1_8to1_7_6_10.SPIDER_CLIMBING, MetaIndex1_8to1_7_6_10.CREEPER_STATE, MetaIndex1_8to1_7_6_10.CREEPER_POWERED, MetaIndex1_8to1_7_6_10.GHAST_STATE, MetaIndex1_8to1_7_6_10.GHAST_IS_POWERED, MetaIndex1_8to1_7_6_10.SLIME_SIZE, MetaIndex1_8to1_7_6_10.SKELETON_TYPE, MetaIndex1_8to1_7_6_10.WITCH_AGRESSIVE, MetaIndex1_8to1_7_6_10.IRON_GOLEM_IS_PLAYER_CREATED, MetaIndex1_8to1_7_6_10.WITHER_WATCHED_TAGRET_1, MetaIndex1_8to1_7_6_10.WITHER_WATCHED_TAGRET_2, MetaIndex1_8to1_7_6_10.WITHER_WATCHED_TAGRET_3, MetaIndex1_8to1_7_6_10.WITHER_INVULNERABLE_TIME, MetaIndex1_8to1_7_6_10.GUARDIAN_FLAGS, MetaIndex1_8to1_7_6_10.GUARDIAN_TARGET, MetaIndex1_8to1_7_6_10.BOAT_TIME_SINCE_HIT, MetaIndex1_8to1_7_6_10.BOAT_FORWARD_DIRECTION, MetaIndex1_8to1_7_6_10.BOAT_DAMAGE_TAKEN, MetaIndex1_8to1_7_6_10.MINECART_SHAKING_POWER, MetaIndex1_8to1_7_6_10.MINECART_SHAKING_DIRECTION, MetaIndex1_8to1_7_6_10.MINECART_DAMAGE_TAKEN, MetaIndex1_8to1_7_6_10.MINECART_BLOCK_INSIDE, MetaIndex1_8to1_7_6_10.MINECART_BLOCK_Y, MetaIndex1_8to1_7_6_10.MINECART_SHOW_BLOCK, MetaIndex1_8to1_7_6_10.FURNACE_MINECART_IS_POWERED, MetaIndex1_8to1_7_6_10.ITEM_ITEM, MetaIndex1_8to1_7_6_10.ARROW_IS_CRITICAL, MetaIndex1_8to1_7_6_10.FIREWORK_INFO, MetaIndex1_8to1_7_6_10.ITEM_FRAME_ITEM, MetaIndex1_8to1_7_6_10.ITEM_FRAME_ROTATION, MetaIndex1_8to1_7_6_10.ENDER_CRYSTAL_HEALTH };
        metadataRewrites = new HashMap();
        final MetaIndex1_8to1_7_6_10[] values = values();
        while (0 < values.length) {
            final MetaIndex1_8to1_7_6_10 metaIndex1_8to1_7_6_10 = values[0];
            MetaIndex1_8to1_7_6_10.metadataRewrites.put(new Pair(metaIndex1_8to1_7_6_10.getClazz(), metaIndex1_8to1_7_6_10.getIndex()), metaIndex1_8to1_7_6_10);
            int n = 0;
            ++n;
        }
    }
}
