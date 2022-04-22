package com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata;

import com.viaversion.viaversion.api.minecraft.metadata.types.*;
import com.viaversion.viaversion.api.minecraft.entities.*;
import java.util.*;
import com.viaversion.viaversion.util.*;

public enum MetaIndex
{
    ENTITY_STATUS("ENTITY_STATUS", 0, Entity1_10Types.EntityType.ENTITY, 0, MetaType1_8.Byte, MetaType1_9.Byte), 
    ENTITY_AIR("ENTITY_AIR", 1, Entity1_10Types.EntityType.ENTITY, 1, MetaType1_8.Short, MetaType1_9.VarInt), 
    ENTITY_NAMETAG("ENTITY_NAMETAG", 2, Entity1_10Types.EntityType.ENTITY, 2, MetaType1_8.String, MetaType1_9.String), 
    ENTITY_ALWAYS_SHOW_NAMETAG("ENTITY_ALWAYS_SHOW_NAMETAG", 3, Entity1_10Types.EntityType.ENTITY, 3, MetaType1_8.Byte, MetaType1_9.Boolean), 
    ENTITY_SILENT("ENTITY_SILENT", 4, Entity1_10Types.EntityType.ENTITY, 4, MetaType1_8.Byte, MetaType1_9.Boolean), 
    LIVINGENTITY_HEALTH("LIVINGENTITY_HEALTH", 5, Entity1_10Types.EntityType.ENTITY_LIVING, 6, MetaType1_8.Float, MetaType1_9.Float), 
    LIVINGENTITY_POTION_EFFECT_COLOR("LIVINGENTITY_POTION_EFFECT_COLOR", 6, Entity1_10Types.EntityType.ENTITY_LIVING, 7, MetaType1_8.Int, MetaType1_9.VarInt), 
    LIVINGENTITY_IS_POTION_AMBIENT("LIVINGENTITY_IS_POTION_AMBIENT", 7, Entity1_10Types.EntityType.ENTITY_LIVING, 8, MetaType1_8.Byte, MetaType1_9.Boolean), 
    LIVINGENTITY_NUMBER_OF_ARROWS_IN("LIVINGENTITY_NUMBER_OF_ARROWS_IN", 8, Entity1_10Types.EntityType.ENTITY_LIVING, 9, MetaType1_8.Byte, MetaType1_9.VarInt), 
    LIVINGENTITY_NO_AI("LIVINGENTITY_NO_AI", 9, Entity1_10Types.EntityType.ENTITY_LIVING, 15, MetaType1_8.Byte, 10, MetaType1_9.Byte), 
    AGEABLE_AGE("AGEABLE_AGE", 10, Entity1_10Types.EntityType.ENTITY_AGEABLE, 12, MetaType1_8.Byte, 11, MetaType1_9.Boolean), 
    STAND_INFO("STAND_INFO", 11, Entity1_10Types.EntityType.ARMOR_STAND, 10, MetaType1_8.Byte, MetaType1_9.Byte), 
    STAND_HEAD_POS("STAND_HEAD_POS", 12, Entity1_10Types.EntityType.ARMOR_STAND, 11, MetaType1_8.Rotation, MetaType1_9.Vector3F), 
    STAND_BODY_POS("STAND_BODY_POS", 13, Entity1_10Types.EntityType.ARMOR_STAND, 12, MetaType1_8.Rotation, MetaType1_9.Vector3F), 
    STAND_LA_POS("STAND_LA_POS", 14, Entity1_10Types.EntityType.ARMOR_STAND, 13, MetaType1_8.Rotation, MetaType1_9.Vector3F), 
    STAND_RA_POS("STAND_RA_POS", 15, Entity1_10Types.EntityType.ARMOR_STAND, 14, MetaType1_8.Rotation, MetaType1_9.Vector3F), 
    STAND_LL_POS("STAND_LL_POS", 16, Entity1_10Types.EntityType.ARMOR_STAND, 15, MetaType1_8.Rotation, MetaType1_9.Vector3F), 
    STAND_RL_POS("STAND_RL_POS", 17, Entity1_10Types.EntityType.ARMOR_STAND, 16, MetaType1_8.Rotation, MetaType1_9.Vector3F), 
    PLAYER_SKIN_FLAGS("PLAYER_SKIN_FLAGS", 18, Entity1_10Types.EntityType.ENTITY_HUMAN, 10, MetaType1_8.Byte, 12, MetaType1_9.Byte), 
    PLAYER_HUMAN_BYTE("PLAYER_HUMAN_BYTE", 19, Entity1_10Types.EntityType.ENTITY_HUMAN, 16, MetaType1_8.Byte, (MetaType1_9)null), 
    PLAYER_ADDITIONAL_HEARTS("PLAYER_ADDITIONAL_HEARTS", 20, Entity1_10Types.EntityType.ENTITY_HUMAN, 17, MetaType1_8.Float, 10, MetaType1_9.Float), 
    PLAYER_SCORE("PLAYER_SCORE", 21, Entity1_10Types.EntityType.ENTITY_HUMAN, 18, MetaType1_8.Int, 11, MetaType1_9.VarInt), 
    PLAYER_HAND("PLAYER_HAND", 22, Entity1_10Types.EntityType.ENTITY_HUMAN, -1, MetaType1_8.NonExistent, 5, MetaType1_9.Byte), 
    SOMETHING_ANTICHEAT_PLUGINS_FOR_SOME_REASON_USE("SOMETHING_ANTICHEAT_PLUGINS_FOR_SOME_REASON_USE", 23, Entity1_10Types.EntityType.ENTITY_HUMAN, 11, MetaType1_8.Byte, (MetaType1_9)null), 
    HORSE_INFO("HORSE_INFO", 24, Entity1_10Types.EntityType.HORSE, 16, MetaType1_8.Int, 12, MetaType1_9.Byte), 
    HORSE_TYPE("HORSE_TYPE", 25, Entity1_10Types.EntityType.HORSE, 19, MetaType1_8.Byte, 13, MetaType1_9.VarInt), 
    HORSE_SUBTYPE("HORSE_SUBTYPE", 26, Entity1_10Types.EntityType.HORSE, 20, MetaType1_8.Int, 14, MetaType1_9.VarInt), 
    HORSE_OWNER("HORSE_OWNER", 27, Entity1_10Types.EntityType.HORSE, 21, MetaType1_8.String, 15, MetaType1_9.OptUUID), 
    HORSE_ARMOR("HORSE_ARMOR", 28, Entity1_10Types.EntityType.HORSE, 22, MetaType1_8.Int, 16, MetaType1_9.VarInt), 
    BAT_ISHANGING("BAT_ISHANGING", 29, Entity1_10Types.EntityType.BAT, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte), 
    TAMING_INFO("TAMING_INFO", 30, Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 16, MetaType1_8.Byte, 12, MetaType1_9.Byte), 
    TAMING_OWNER("TAMING_OWNER", 31, Entity1_10Types.EntityType.ENTITY_TAMEABLE_ANIMAL, 17, MetaType1_8.String, 13, MetaType1_9.OptUUID), 
    OCELOT_TYPE("OCELOT_TYPE", 32, Entity1_10Types.EntityType.OCELOT, 18, MetaType1_8.Byte, 14, MetaType1_9.VarInt), 
    WOLF_HEALTH("WOLF_HEALTH", 33, Entity1_10Types.EntityType.WOLF, 18, MetaType1_8.Float, 14, MetaType1_9.Float), 
    WOLF_BEGGING("WOLF_BEGGING", 34, Entity1_10Types.EntityType.WOLF, 19, MetaType1_8.Byte, 15, MetaType1_9.Boolean), 
    WOLF_COLLAR("WOLF_COLLAR", 35, Entity1_10Types.EntityType.WOLF, 20, MetaType1_8.Byte, 16, MetaType1_9.VarInt), 
    PIG_SADDLE("PIG_SADDLE", 36, Entity1_10Types.EntityType.PIG, 16, MetaType1_8.Byte, 12, MetaType1_9.Boolean), 
    RABBIT_TYPE("RABBIT_TYPE", 37, Entity1_10Types.EntityType.RABBIT, 18, MetaType1_8.Byte, 12, MetaType1_9.VarInt), 
    SHEEP_COLOR("SHEEP_COLOR", 38, Entity1_10Types.EntityType.SHEEP, 16, MetaType1_8.Byte, 12, MetaType1_9.Byte), 
    VILLAGER_PROFESSION("VILLAGER_PROFESSION", 39, Entity1_10Types.EntityType.VILLAGER, 16, MetaType1_8.Int, 12, MetaType1_9.VarInt), 
    ENDERMAN_BLOCKSTATE("ENDERMAN_BLOCKSTATE", 40, Entity1_10Types.EntityType.ENDERMAN, 16, MetaType1_8.Short, 11, MetaType1_9.BlockID), 
    ENDERMAN_BLOCKDATA("ENDERMAN_BLOCKDATA", 41, Entity1_10Types.EntityType.ENDERMAN, 17, MetaType1_8.Byte, (MetaType1_9)null), 
    ENDERMAN_ISSCREAMING("ENDERMAN_ISSCREAMING", 42, Entity1_10Types.EntityType.ENDERMAN, 18, MetaType1_8.Byte, 12, MetaType1_9.Boolean), 
    ZOMBIE_ISCHILD("ZOMBIE_ISCHILD", 43, Entity1_10Types.EntityType.ZOMBIE, 12, MetaType1_8.Byte, 11, MetaType1_9.Boolean), 
    ZOMBIE_ISVILLAGER("ZOMBIE_ISVILLAGER", 44, Entity1_10Types.EntityType.ZOMBIE, 13, MetaType1_8.Byte, 12, MetaType1_9.VarInt), 
    ZOMBIE_ISCONVERTING("ZOMBIE_ISCONVERTING", 45, Entity1_10Types.EntityType.ZOMBIE, 14, MetaType1_8.Byte, 13, MetaType1_9.Boolean), 
    BLAZE_ONFIRE("BLAZE_ONFIRE", 46, Entity1_10Types.EntityType.BLAZE, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte), 
    SPIDER_CIMBING("SPIDER_CIMBING", 47, Entity1_10Types.EntityType.SPIDER, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte), 
    CREEPER_FUSE("CREEPER_FUSE", 48, Entity1_10Types.EntityType.CREEPER, 16, MetaType1_8.Byte, 11, MetaType1_9.VarInt), 
    CREEPER_ISPOWERED("CREEPER_ISPOWERED", 49, Entity1_10Types.EntityType.CREEPER, 17, MetaType1_8.Byte, 12, MetaType1_9.Boolean), 
    CREEPER_ISIGNITED("CREEPER_ISIGNITED", 50, Entity1_10Types.EntityType.CREEPER, 18, MetaType1_8.Byte, 13, MetaType1_9.Boolean), 
    GHAST_ISATTACKING("GHAST_ISATTACKING", 51, Entity1_10Types.EntityType.GHAST, 16, MetaType1_8.Byte, 11, MetaType1_9.Boolean), 
    SLIME_SIZE("SLIME_SIZE", 52, Entity1_10Types.EntityType.SLIME, 16, MetaType1_8.Byte, 11, MetaType1_9.VarInt), 
    SKELETON_TYPE("SKELETON_TYPE", 53, Entity1_10Types.EntityType.SKELETON, 13, MetaType1_8.Byte, 11, MetaType1_9.VarInt), 
    WITCH_AGGRO("WITCH_AGGRO", 54, Entity1_10Types.EntityType.WITCH, 21, MetaType1_8.Byte, 11, MetaType1_9.Boolean), 
    IRON_PLAYERMADE("IRON_PLAYERMADE", 55, Entity1_10Types.EntityType.IRON_GOLEM, 16, MetaType1_8.Byte, 11, MetaType1_9.Byte), 
    WITHER_TARGET1("WITHER_TARGET1", 56, Entity1_10Types.EntityType.WITHER, 17, MetaType1_8.Int, 11, MetaType1_9.VarInt), 
    WITHER_TARGET2("WITHER_TARGET2", 57, Entity1_10Types.EntityType.WITHER, 18, MetaType1_8.Int, 12, MetaType1_9.VarInt), 
    WITHER_TARGET3("WITHER_TARGET3", 58, Entity1_10Types.EntityType.WITHER, 19, MetaType1_8.Int, 13, MetaType1_9.VarInt), 
    WITHER_INVULN_TIME("WITHER_INVULN_TIME", 59, Entity1_10Types.EntityType.WITHER, 20, MetaType1_8.Int, 14, MetaType1_9.VarInt), 
    WITHER_PROPERTIES("WITHER_PROPERTIES", 60, Entity1_10Types.EntityType.WITHER, 10, MetaType1_8.Byte, (MetaType1_9)null), 
    WITHER_UNKNOWN("WITHER_UNKNOWN", 61, Entity1_10Types.EntityType.WITHER, 11, MetaType1_8.NonExistent, (MetaType1_9)null), 
    WITHERSKULL_INVULN("WITHERSKULL_INVULN", 62, Entity1_10Types.EntityType.WITHER_SKULL, 10, MetaType1_8.Byte, 5, MetaType1_9.Boolean), 
    GUARDIAN_INFO("GUARDIAN_INFO", 63, Entity1_10Types.EntityType.GUARDIAN, 16, MetaType1_8.Int, 11, MetaType1_9.Byte), 
    GUARDIAN_TARGET("GUARDIAN_TARGET", 64, Entity1_10Types.EntityType.GUARDIAN, 17, MetaType1_8.Int, 12, MetaType1_9.VarInt), 
    BOAT_SINCEHIT("BOAT_SINCEHIT", 65, Entity1_10Types.EntityType.BOAT, 17, MetaType1_8.Int, 5, MetaType1_9.VarInt), 
    BOAT_FORWARDDIR("BOAT_FORWARDDIR", 66, Entity1_10Types.EntityType.BOAT, 18, MetaType1_8.Int, 6, MetaType1_9.VarInt), 
    BOAT_DMGTAKEN("BOAT_DMGTAKEN", 67, Entity1_10Types.EntityType.BOAT, 19, MetaType1_8.Float, 7, MetaType1_9.Float), 
    MINECART_SHAKINGPOWER("MINECART_SHAKINGPOWER", 68, Entity1_10Types.EntityType.MINECART_ABSTRACT, 17, MetaType1_8.Int, 5, MetaType1_9.VarInt), 
    MINECART_SHAKINGDIRECTION("MINECART_SHAKINGDIRECTION", 69, Entity1_10Types.EntityType.MINECART_ABSTRACT, 18, MetaType1_8.Int, 6, MetaType1_9.VarInt), 
    MINECART_DAMAGETAKEN("MINECART_DAMAGETAKEN", 70, Entity1_10Types.EntityType.MINECART_ABSTRACT, 19, MetaType1_8.Float, 7, MetaType1_9.Float), 
    MINECART_BLOCK("MINECART_BLOCK", 71, Entity1_10Types.EntityType.MINECART_ABSTRACT, 20, MetaType1_8.Int, 8, MetaType1_9.VarInt), 
    MINECART_BLOCK_Y("MINECART_BLOCK_Y", 72, Entity1_10Types.EntityType.MINECART_ABSTRACT, 21, MetaType1_8.Int, 9, MetaType1_9.VarInt), 
    MINECART_SHOWBLOCK("MINECART_SHOWBLOCK", 73, Entity1_10Types.EntityType.MINECART_ABSTRACT, 22, MetaType1_8.Byte, 10, MetaType1_9.Boolean), 
    MINECART_COMMANDBLOCK_COMMAND("MINECART_COMMANDBLOCK_COMMAND", 74, Entity1_10Types.EntityType.MINECART_ABSTRACT, 23, MetaType1_8.String, 11, MetaType1_9.String), 
    MINECART_COMMANDBLOCK_OUTPUT("MINECART_COMMANDBLOCK_OUTPUT", 75, Entity1_10Types.EntityType.MINECART_ABSTRACT, 24, MetaType1_8.String, 12, MetaType1_9.Chat), 
    FURNACECART_ISPOWERED("FURNACECART_ISPOWERED", 76, Entity1_10Types.EntityType.MINECART_ABSTRACT, 16, MetaType1_8.Byte, 11, MetaType1_9.Boolean), 
    ITEM_ITEM("ITEM_ITEM", 77, Entity1_10Types.EntityType.DROPPED_ITEM, 10, MetaType1_8.Slot, 5, MetaType1_9.Slot), 
    ARROW_ISCRIT("ARROW_ISCRIT", 78, Entity1_10Types.EntityType.ARROW, 16, MetaType1_8.Byte, 5, MetaType1_9.Byte), 
    FIREWORK_INFO("FIREWORK_INFO", 79, Entity1_10Types.EntityType.FIREWORK, 8, MetaType1_8.Slot, 5, MetaType1_9.Slot), 
    ITEMFRAME_ITEM("ITEMFRAME_ITEM", 80, Entity1_10Types.EntityType.ITEM_FRAME, 8, MetaType1_8.Slot, 5, MetaType1_9.Slot), 
    ITEMFRAME_ROTATION("ITEMFRAME_ROTATION", 81, Entity1_10Types.EntityType.ITEM_FRAME, 9, MetaType1_8.Byte, 6, MetaType1_9.VarInt), 
    ENDERCRYSTAL_HEALTH("ENDERCRYSTAL_HEALTH", 82, Entity1_10Types.EntityType.ENDER_CRYSTAL, 8, MetaType1_8.Int, (MetaType1_9)null), 
    ENDERDRAGON_UNKNOWN("ENDERDRAGON_UNKNOWN", 83, Entity1_10Types.EntityType.ENDER_DRAGON, 5, MetaType1_8.Byte, (MetaType1_9)null), 
    ENDERDRAGON_NAME("ENDERDRAGON_NAME", 84, Entity1_10Types.EntityType.ENDER_DRAGON, 10, MetaType1_8.String, (MetaType1_9)null), 
    ENDERDRAGON_FLAG("ENDERDRAGON_FLAG", 85, Entity1_10Types.EntityType.ENDER_DRAGON, 15, MetaType1_8.Byte, (MetaType1_9)null), 
    ENDERDRAGON_PHASE("ENDERDRAGON_PHASE", 86, Entity1_10Types.EntityType.ENDER_DRAGON, 11, MetaType1_8.Byte, MetaType1_9.VarInt);
    
    private static final HashMap metadataRewrites;
    private final Entity1_10Types.EntityType clazz;
    private final int newIndex;
    private final MetaType1_9 newType;
    private final MetaType1_8 oldType;
    private final int index;
    private static final MetaIndex[] $VALUES;
    
    private MetaIndex(final String s, final int n, final Entity1_10Types.EntityType clazz, final int n2, final MetaType1_8 oldType, final MetaType1_9 newType) {
        this.clazz = clazz;
        this.index = n2;
        this.newIndex = n2;
        this.oldType = oldType;
        this.newType = newType;
    }
    
    private MetaIndex(final String s, final int n, final Entity1_10Types.EntityType clazz, final int index, final MetaType1_8 oldType, final int newIndex, final MetaType1_9 newType) {
        this.clazz = clazz;
        this.index = index;
        this.oldType = oldType;
        this.newIndex = newIndex;
        this.newType = newType;
    }
    
    public Entity1_10Types.EntityType getClazz() {
        return this.clazz;
    }
    
    public int getNewIndex() {
        return this.newIndex;
    }
    
    public MetaType1_9 getNewType() {
        return this.newType;
    }
    
    public MetaType1_8 getOldType() {
        return this.oldType;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    private static Optional getIndex(final EntityType entityType, final int n) {
        return Optional.ofNullable(MetaIndex.metadataRewrites.get(new Pair(entityType, n)));
    }
    
    public static MetaIndex searchIndex(final EntityType entityType, final int n) {
        EntityType parent = entityType;
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
        $VALUES = new MetaIndex[] { MetaIndex.ENTITY_STATUS, MetaIndex.ENTITY_AIR, MetaIndex.ENTITY_NAMETAG, MetaIndex.ENTITY_ALWAYS_SHOW_NAMETAG, MetaIndex.ENTITY_SILENT, MetaIndex.LIVINGENTITY_HEALTH, MetaIndex.LIVINGENTITY_POTION_EFFECT_COLOR, MetaIndex.LIVINGENTITY_IS_POTION_AMBIENT, MetaIndex.LIVINGENTITY_NUMBER_OF_ARROWS_IN, MetaIndex.LIVINGENTITY_NO_AI, MetaIndex.AGEABLE_AGE, MetaIndex.STAND_INFO, MetaIndex.STAND_HEAD_POS, MetaIndex.STAND_BODY_POS, MetaIndex.STAND_LA_POS, MetaIndex.STAND_RA_POS, MetaIndex.STAND_LL_POS, MetaIndex.STAND_RL_POS, MetaIndex.PLAYER_SKIN_FLAGS, MetaIndex.PLAYER_HUMAN_BYTE, MetaIndex.PLAYER_ADDITIONAL_HEARTS, MetaIndex.PLAYER_SCORE, MetaIndex.PLAYER_HAND, MetaIndex.SOMETHING_ANTICHEAT_PLUGINS_FOR_SOME_REASON_USE, MetaIndex.HORSE_INFO, MetaIndex.HORSE_TYPE, MetaIndex.HORSE_SUBTYPE, MetaIndex.HORSE_OWNER, MetaIndex.HORSE_ARMOR, MetaIndex.BAT_ISHANGING, MetaIndex.TAMING_INFO, MetaIndex.TAMING_OWNER, MetaIndex.OCELOT_TYPE, MetaIndex.WOLF_HEALTH, MetaIndex.WOLF_BEGGING, MetaIndex.WOLF_COLLAR, MetaIndex.PIG_SADDLE, MetaIndex.RABBIT_TYPE, MetaIndex.SHEEP_COLOR, MetaIndex.VILLAGER_PROFESSION, MetaIndex.ENDERMAN_BLOCKSTATE, MetaIndex.ENDERMAN_BLOCKDATA, MetaIndex.ENDERMAN_ISSCREAMING, MetaIndex.ZOMBIE_ISCHILD, MetaIndex.ZOMBIE_ISVILLAGER, MetaIndex.ZOMBIE_ISCONVERTING, MetaIndex.BLAZE_ONFIRE, MetaIndex.SPIDER_CIMBING, MetaIndex.CREEPER_FUSE, MetaIndex.CREEPER_ISPOWERED, MetaIndex.CREEPER_ISIGNITED, MetaIndex.GHAST_ISATTACKING, MetaIndex.SLIME_SIZE, MetaIndex.SKELETON_TYPE, MetaIndex.WITCH_AGGRO, MetaIndex.IRON_PLAYERMADE, MetaIndex.WITHER_TARGET1, MetaIndex.WITHER_TARGET2, MetaIndex.WITHER_TARGET3, MetaIndex.WITHER_INVULN_TIME, MetaIndex.WITHER_PROPERTIES, MetaIndex.WITHER_UNKNOWN, MetaIndex.WITHERSKULL_INVULN, MetaIndex.GUARDIAN_INFO, MetaIndex.GUARDIAN_TARGET, MetaIndex.BOAT_SINCEHIT, MetaIndex.BOAT_FORWARDDIR, MetaIndex.BOAT_DMGTAKEN, MetaIndex.MINECART_SHAKINGPOWER, MetaIndex.MINECART_SHAKINGDIRECTION, MetaIndex.MINECART_DAMAGETAKEN, MetaIndex.MINECART_BLOCK, MetaIndex.MINECART_BLOCK_Y, MetaIndex.MINECART_SHOWBLOCK, MetaIndex.MINECART_COMMANDBLOCK_COMMAND, MetaIndex.MINECART_COMMANDBLOCK_OUTPUT, MetaIndex.FURNACECART_ISPOWERED, MetaIndex.ITEM_ITEM, MetaIndex.ARROW_ISCRIT, MetaIndex.FIREWORK_INFO, MetaIndex.ITEMFRAME_ITEM, MetaIndex.ITEMFRAME_ROTATION, MetaIndex.ENDERCRYSTAL_HEALTH, MetaIndex.ENDERDRAGON_UNKNOWN, MetaIndex.ENDERDRAGON_NAME, MetaIndex.ENDERDRAGON_FLAG, MetaIndex.ENDERDRAGON_PHASE };
        metadataRewrites = new HashMap();
        final MetaIndex[] values = values();
        while (0 < values.length) {
            final MetaIndex metaIndex = values[0];
            MetaIndex.metadataRewrites.put(new Pair(metaIndex.clazz, metaIndex.index), metaIndex);
            int n = 0;
            ++n;
        }
    }
}
