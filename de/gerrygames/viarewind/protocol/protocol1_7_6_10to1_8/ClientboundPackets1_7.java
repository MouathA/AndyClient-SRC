package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ClientboundPackets1_7 implements ClientboundPacketType
{
    KEEP_ALIVE("KEEP_ALIVE", 0), 
    JOIN_GAME("JOIN_GAME", 1), 
    CHAT_MESSAGE("CHAT_MESSAGE", 2), 
    TIME_UPDATE("TIME_UPDATE", 3), 
    ENTITY_EQUIPMENT("ENTITY_EQUIPMENT", 4), 
    SPAWN_POSITION("SPAWN_POSITION", 5), 
    UPDATE_HEALTH("UPDATE_HEALTH", 6), 
    RESPAWN("RESPAWN", 7), 
    PLAYER_POSITION("PLAYER_POSITION", 8), 
    HELD_ITEM_CHANGE("HELD_ITEM_CHANGE", 9), 
    USE_BED("USE_BED", 10), 
    ENTITY_ANIMATION("ENTITY_ANIMATION", 11), 
    SPAWN_PLAYER("SPAWN_PLAYER", 12), 
    COLLECT_ITEM("COLLECT_ITEM", 13), 
    SPAWN_ENTITY("SPAWN_ENTITY", 14), 
    SPAWN_MOB("SPAWN_MOB", 15), 
    SPAWN_PAINTING("SPAWN_PAINTING", 16), 
    SPAWN_EXPERIENCE_ORB("SPAWN_EXPERIENCE_ORB", 17), 
    ENTITY_VELOCITY("ENTITY_VELOCITY", 18), 
    DESTROY_ENTITIES("DESTROY_ENTITIES", 19), 
    ENTITY_MOVEMENT("ENTITY_MOVEMENT", 20), 
    ENTITY_POSITION("ENTITY_POSITION", 21), 
    ENTITY_ROTATION("ENTITY_ROTATION", 22), 
    ENTITY_POSITION_AND_ROTATION("ENTITY_POSITION_AND_ROTATION", 23), 
    ENTITY_TELEPORT("ENTITY_TELEPORT", 24), 
    ENTITY_HEAD_LOOK("ENTITY_HEAD_LOOK", 25), 
    ENTITY_STATUS("ENTITY_STATUS", 26), 
    ATTACH_ENTITY("ATTACH_ENTITY", 27), 
    ENTITY_METADATA("ENTITY_METADATA", 28), 
    ENTITY_EFFECT("ENTITY_EFFECT", 29), 
    REMOVE_ENTITY_EFFECT("REMOVE_ENTITY_EFFECT", 30), 
    SET_EXPERIENCE("SET_EXPERIENCE", 31), 
    ENTITY_PROPERTIES("ENTITY_PROPERTIES", 32), 
    CHUNK_DATA("CHUNK_DATA", 33), 
    MULTI_BLOCK_CHANGE("MULTI_BLOCK_CHANGE", 34), 
    BLOCK_CHANGE("BLOCK_CHANGE", 35), 
    BLOCK_ACTION("BLOCK_ACTION", 36), 
    BLOCK_BREAK_ANIMATION("BLOCK_BREAK_ANIMATION", 37), 
    MAP_BULK_CHUNK("MAP_BULK_CHUNK", 38), 
    EXPLOSION("EXPLOSION", 39), 
    EFFECT("EFFECT", 40), 
    NAMED_SOUND("NAMED_SOUND", 41), 
    SPAWN_PARTICLE("SPAWN_PARTICLE", 42), 
    GAME_EVENT("GAME_EVENT", 43), 
    SPAWN_GLOBAL_ENTITY("SPAWN_GLOBAL_ENTITY", 44), 
    OPEN_WINDOW("OPEN_WINDOW", 45), 
    CLOSE_WINDOW("CLOSE_WINDOW", 46), 
    SET_SLOT("SET_SLOT", 47), 
    WINDOW_ITEMS("WINDOW_ITEMS", 48), 
    WINDOW_PROPERTY("WINDOW_PROPERTY", 49), 
    WINDOW_CONFIRMATION("WINDOW_CONFIRMATION", 50), 
    UPDATE_SIGN("UPDATE_SIGN", 51), 
    MAP_DATA("MAP_DATA", 52), 
    BLOCK_ENTITY_DATA("BLOCK_ENTITY_DATA", 53), 
    OPEN_SIGN_EDITOR("OPEN_SIGN_EDITOR", 54), 
    STATISTICS("STATISTICS", 55), 
    PLAYER_INFO("PLAYER_INFO", 56), 
    PLAYER_ABILITIES("PLAYER_ABILITIES", 57), 
    TAB_COMPLETE("TAB_COMPLETE", 58), 
    SCOREBOARD_OBJECTIVE("SCOREBOARD_OBJECTIVE", 59), 
    UPDATE_SCORE("UPDATE_SCORE", 60), 
    DISPLAY_SCOREBOARD("DISPLAY_SCOREBOARD", 61), 
    TEAMS("TEAMS", 62), 
    PLUGIN_MESSAGE("PLUGIN_MESSAGE", 63), 
    DISCONNECT("DISCONNECT", 64);
    
    private static final ClientboundPackets1_7[] $VALUES;
    
    private ClientboundPackets1_7(final String s, final int n) {
    }
    
    @Override
    public int getId() {
        return this.ordinal();
    }
    
    @Override
    public String getName() {
        return this.name();
    }
    
    static {
        $VALUES = new ClientboundPackets1_7[] { ClientboundPackets1_7.KEEP_ALIVE, ClientboundPackets1_7.JOIN_GAME, ClientboundPackets1_7.CHAT_MESSAGE, ClientboundPackets1_7.TIME_UPDATE, ClientboundPackets1_7.ENTITY_EQUIPMENT, ClientboundPackets1_7.SPAWN_POSITION, ClientboundPackets1_7.UPDATE_HEALTH, ClientboundPackets1_7.RESPAWN, ClientboundPackets1_7.PLAYER_POSITION, ClientboundPackets1_7.HELD_ITEM_CHANGE, ClientboundPackets1_7.USE_BED, ClientboundPackets1_7.ENTITY_ANIMATION, ClientboundPackets1_7.SPAWN_PLAYER, ClientboundPackets1_7.COLLECT_ITEM, ClientboundPackets1_7.SPAWN_ENTITY, ClientboundPackets1_7.SPAWN_MOB, ClientboundPackets1_7.SPAWN_PAINTING, ClientboundPackets1_7.SPAWN_EXPERIENCE_ORB, ClientboundPackets1_7.ENTITY_VELOCITY, ClientboundPackets1_7.DESTROY_ENTITIES, ClientboundPackets1_7.ENTITY_MOVEMENT, ClientboundPackets1_7.ENTITY_POSITION, ClientboundPackets1_7.ENTITY_ROTATION, ClientboundPackets1_7.ENTITY_POSITION_AND_ROTATION, ClientboundPackets1_7.ENTITY_TELEPORT, ClientboundPackets1_7.ENTITY_HEAD_LOOK, ClientboundPackets1_7.ENTITY_STATUS, ClientboundPackets1_7.ATTACH_ENTITY, ClientboundPackets1_7.ENTITY_METADATA, ClientboundPackets1_7.ENTITY_EFFECT, ClientboundPackets1_7.REMOVE_ENTITY_EFFECT, ClientboundPackets1_7.SET_EXPERIENCE, ClientboundPackets1_7.ENTITY_PROPERTIES, ClientboundPackets1_7.CHUNK_DATA, ClientboundPackets1_7.MULTI_BLOCK_CHANGE, ClientboundPackets1_7.BLOCK_CHANGE, ClientboundPackets1_7.BLOCK_ACTION, ClientboundPackets1_7.BLOCK_BREAK_ANIMATION, ClientboundPackets1_7.MAP_BULK_CHUNK, ClientboundPackets1_7.EXPLOSION, ClientboundPackets1_7.EFFECT, ClientboundPackets1_7.NAMED_SOUND, ClientboundPackets1_7.SPAWN_PARTICLE, ClientboundPackets1_7.GAME_EVENT, ClientboundPackets1_7.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_7.OPEN_WINDOW, ClientboundPackets1_7.CLOSE_WINDOW, ClientboundPackets1_7.SET_SLOT, ClientboundPackets1_7.WINDOW_ITEMS, ClientboundPackets1_7.WINDOW_PROPERTY, ClientboundPackets1_7.WINDOW_CONFIRMATION, ClientboundPackets1_7.UPDATE_SIGN, ClientboundPackets1_7.MAP_DATA, ClientboundPackets1_7.BLOCK_ENTITY_DATA, ClientboundPackets1_7.OPEN_SIGN_EDITOR, ClientboundPackets1_7.STATISTICS, ClientboundPackets1_7.PLAYER_INFO, ClientboundPackets1_7.PLAYER_ABILITIES, ClientboundPackets1_7.TAB_COMPLETE, ClientboundPackets1_7.SCOREBOARD_OBJECTIVE, ClientboundPackets1_7.UPDATE_SCORE, ClientboundPackets1_7.DISPLAY_SCOREBOARD, ClientboundPackets1_7.TEAMS, ClientboundPackets1_7.PLUGIN_MESSAGE, ClientboundPackets1_7.DISCONNECT };
    }
}
