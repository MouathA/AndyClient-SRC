package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ClientboundPackets1_9 implements ClientboundPacketType
{
    SPAWN_ENTITY("SPAWN_ENTITY", 0), 
    SPAWN_EXPERIENCE_ORB("SPAWN_EXPERIENCE_ORB", 1), 
    SPAWN_GLOBAL_ENTITY("SPAWN_GLOBAL_ENTITY", 2), 
    SPAWN_MOB("SPAWN_MOB", 3), 
    SPAWN_PAINTING("SPAWN_PAINTING", 4), 
    SPAWN_PLAYER("SPAWN_PLAYER", 5), 
    ENTITY_ANIMATION("ENTITY_ANIMATION", 6), 
    STATISTICS("STATISTICS", 7), 
    BLOCK_BREAK_ANIMATION("BLOCK_BREAK_ANIMATION", 8), 
    BLOCK_ENTITY_DATA("BLOCK_ENTITY_DATA", 9), 
    BLOCK_ACTION("BLOCK_ACTION", 10), 
    BLOCK_CHANGE("BLOCK_CHANGE", 11), 
    BOSSBAR("BOSSBAR", 12), 
    SERVER_DIFFICULTY("SERVER_DIFFICULTY", 13), 
    TAB_COMPLETE("TAB_COMPLETE", 14), 
    CHAT_MESSAGE("CHAT_MESSAGE", 15), 
    MULTI_BLOCK_CHANGE("MULTI_BLOCK_CHANGE", 16), 
    WINDOW_CONFIRMATION("WINDOW_CONFIRMATION", 17), 
    CLOSE_WINDOW("CLOSE_WINDOW", 18), 
    OPEN_WINDOW("OPEN_WINDOW", 19), 
    WINDOW_ITEMS("WINDOW_ITEMS", 20), 
    WINDOW_PROPERTY("WINDOW_PROPERTY", 21), 
    SET_SLOT("SET_SLOT", 22), 
    COOLDOWN("COOLDOWN", 23), 
    PLUGIN_MESSAGE("PLUGIN_MESSAGE", 24), 
    NAMED_SOUND("NAMED_SOUND", 25), 
    DISCONNECT("DISCONNECT", 26), 
    ENTITY_STATUS("ENTITY_STATUS", 27), 
    EXPLOSION("EXPLOSION", 28), 
    UNLOAD_CHUNK("UNLOAD_CHUNK", 29), 
    GAME_EVENT("GAME_EVENT", 30), 
    KEEP_ALIVE("KEEP_ALIVE", 31), 
    CHUNK_DATA("CHUNK_DATA", 32), 
    EFFECT("EFFECT", 33), 
    SPAWN_PARTICLE("SPAWN_PARTICLE", 34), 
    JOIN_GAME("JOIN_GAME", 35), 
    MAP_DATA("MAP_DATA", 36), 
    ENTITY_POSITION("ENTITY_POSITION", 37), 
    ENTITY_POSITION_AND_ROTATION("ENTITY_POSITION_AND_ROTATION", 38), 
    ENTITY_ROTATION("ENTITY_ROTATION", 39), 
    ENTITY_MOVEMENT("ENTITY_MOVEMENT", 40), 
    VEHICLE_MOVE("VEHICLE_MOVE", 41), 
    OPEN_SIGN_EDITOR("OPEN_SIGN_EDITOR", 42), 
    PLAYER_ABILITIES("PLAYER_ABILITIES", 43), 
    COMBAT_EVENT("COMBAT_EVENT", 44), 
    PLAYER_INFO("PLAYER_INFO", 45), 
    PLAYER_POSITION("PLAYER_POSITION", 46), 
    USE_BED("USE_BED", 47), 
    DESTROY_ENTITIES("DESTROY_ENTITIES", 48), 
    REMOVE_ENTITY_EFFECT("REMOVE_ENTITY_EFFECT", 49), 
    RESOURCE_PACK("RESOURCE_PACK", 50), 
    RESPAWN("RESPAWN", 51), 
    ENTITY_HEAD_LOOK("ENTITY_HEAD_LOOK", 52), 
    WORLD_BORDER("WORLD_BORDER", 53), 
    CAMERA("CAMERA", 54), 
    HELD_ITEM_CHANGE("HELD_ITEM_CHANGE", 55), 
    DISPLAY_SCOREBOARD("DISPLAY_SCOREBOARD", 56), 
    ENTITY_METADATA("ENTITY_METADATA", 57), 
    ATTACH_ENTITY("ATTACH_ENTITY", 58), 
    ENTITY_VELOCITY("ENTITY_VELOCITY", 59), 
    ENTITY_EQUIPMENT("ENTITY_EQUIPMENT", 60), 
    SET_EXPERIENCE("SET_EXPERIENCE", 61), 
    UPDATE_HEALTH("UPDATE_HEALTH", 62), 
    SCOREBOARD_OBJECTIVE("SCOREBOARD_OBJECTIVE", 63), 
    SET_PASSENGERS("SET_PASSENGERS", 64), 
    TEAMS("TEAMS", 65), 
    UPDATE_SCORE("UPDATE_SCORE", 66), 
    SPAWN_POSITION("SPAWN_POSITION", 67), 
    TIME_UPDATE("TIME_UPDATE", 68), 
    TITLE("TITLE", 69), 
    UPDATE_SIGN("UPDATE_SIGN", 70), 
    SOUND("SOUND", 71), 
    TAB_LIST("TAB_LIST", 72), 
    COLLECT_ITEM("COLLECT_ITEM", 73), 
    ENTITY_TELEPORT("ENTITY_TELEPORT", 74), 
    ENTITY_PROPERTIES("ENTITY_PROPERTIES", 75), 
    ENTITY_EFFECT("ENTITY_EFFECT", 76);
    
    private static final ClientboundPackets1_9[] $VALUES;
    
    private ClientboundPackets1_9(final String s, final int n) {
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
        $VALUES = new ClientboundPackets1_9[] { ClientboundPackets1_9.SPAWN_ENTITY, ClientboundPackets1_9.SPAWN_EXPERIENCE_ORB, ClientboundPackets1_9.SPAWN_GLOBAL_ENTITY, ClientboundPackets1_9.SPAWN_MOB, ClientboundPackets1_9.SPAWN_PAINTING, ClientboundPackets1_9.SPAWN_PLAYER, ClientboundPackets1_9.ENTITY_ANIMATION, ClientboundPackets1_9.STATISTICS, ClientboundPackets1_9.BLOCK_BREAK_ANIMATION, ClientboundPackets1_9.BLOCK_ENTITY_DATA, ClientboundPackets1_9.BLOCK_ACTION, ClientboundPackets1_9.BLOCK_CHANGE, ClientboundPackets1_9.BOSSBAR, ClientboundPackets1_9.SERVER_DIFFICULTY, ClientboundPackets1_9.TAB_COMPLETE, ClientboundPackets1_9.CHAT_MESSAGE, ClientboundPackets1_9.MULTI_BLOCK_CHANGE, ClientboundPackets1_9.WINDOW_CONFIRMATION, ClientboundPackets1_9.CLOSE_WINDOW, ClientboundPackets1_9.OPEN_WINDOW, ClientboundPackets1_9.WINDOW_ITEMS, ClientboundPackets1_9.WINDOW_PROPERTY, ClientboundPackets1_9.SET_SLOT, ClientboundPackets1_9.COOLDOWN, ClientboundPackets1_9.PLUGIN_MESSAGE, ClientboundPackets1_9.NAMED_SOUND, ClientboundPackets1_9.DISCONNECT, ClientboundPackets1_9.ENTITY_STATUS, ClientboundPackets1_9.EXPLOSION, ClientboundPackets1_9.UNLOAD_CHUNK, ClientboundPackets1_9.GAME_EVENT, ClientboundPackets1_9.KEEP_ALIVE, ClientboundPackets1_9.CHUNK_DATA, ClientboundPackets1_9.EFFECT, ClientboundPackets1_9.SPAWN_PARTICLE, ClientboundPackets1_9.JOIN_GAME, ClientboundPackets1_9.MAP_DATA, ClientboundPackets1_9.ENTITY_POSITION, ClientboundPackets1_9.ENTITY_POSITION_AND_ROTATION, ClientboundPackets1_9.ENTITY_ROTATION, ClientboundPackets1_9.ENTITY_MOVEMENT, ClientboundPackets1_9.VEHICLE_MOVE, ClientboundPackets1_9.OPEN_SIGN_EDITOR, ClientboundPackets1_9.PLAYER_ABILITIES, ClientboundPackets1_9.COMBAT_EVENT, ClientboundPackets1_9.PLAYER_INFO, ClientboundPackets1_9.PLAYER_POSITION, ClientboundPackets1_9.USE_BED, ClientboundPackets1_9.DESTROY_ENTITIES, ClientboundPackets1_9.REMOVE_ENTITY_EFFECT, ClientboundPackets1_9.RESOURCE_PACK, ClientboundPackets1_9.RESPAWN, ClientboundPackets1_9.ENTITY_HEAD_LOOK, ClientboundPackets1_9.WORLD_BORDER, ClientboundPackets1_9.CAMERA, ClientboundPackets1_9.HELD_ITEM_CHANGE, ClientboundPackets1_9.DISPLAY_SCOREBOARD, ClientboundPackets1_9.ENTITY_METADATA, ClientboundPackets1_9.ATTACH_ENTITY, ClientboundPackets1_9.ENTITY_VELOCITY, ClientboundPackets1_9.ENTITY_EQUIPMENT, ClientboundPackets1_9.SET_EXPERIENCE, ClientboundPackets1_9.UPDATE_HEALTH, ClientboundPackets1_9.SCOREBOARD_OBJECTIVE, ClientboundPackets1_9.SET_PASSENGERS, ClientboundPackets1_9.TEAMS, ClientboundPackets1_9.UPDATE_SCORE, ClientboundPackets1_9.SPAWN_POSITION, ClientboundPackets1_9.TIME_UPDATE, ClientboundPackets1_9.TITLE, ClientboundPackets1_9.UPDATE_SIGN, ClientboundPackets1_9.SOUND, ClientboundPackets1_9.TAB_LIST, ClientboundPackets1_9.COLLECT_ITEM, ClientboundPackets1_9.ENTITY_TELEPORT, ClientboundPackets1_9.ENTITY_PROPERTIES, ClientboundPackets1_9.ENTITY_EFFECT };
    }
}
