package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundPackets1_9 implements ServerboundPacketType
{
    TELEPORT_CONFIRM("TELEPORT_CONFIRM", 0), 
    TAB_COMPLETE("TAB_COMPLETE", 1), 
    CHAT_MESSAGE("CHAT_MESSAGE", 2), 
    CLIENT_STATUS("CLIENT_STATUS", 3), 
    CLIENT_SETTINGS("CLIENT_SETTINGS", 4), 
    WINDOW_CONFIRMATION("WINDOW_CONFIRMATION", 5), 
    CLICK_WINDOW_BUTTON("CLICK_WINDOW_BUTTON", 6), 
    CLICK_WINDOW("CLICK_WINDOW", 7), 
    CLOSE_WINDOW("CLOSE_WINDOW", 8), 
    PLUGIN_MESSAGE("PLUGIN_MESSAGE", 9), 
    INTERACT_ENTITY("INTERACT_ENTITY", 10), 
    KEEP_ALIVE("KEEP_ALIVE", 11), 
    PLAYER_POSITION("PLAYER_POSITION", 12), 
    PLAYER_POSITION_AND_ROTATION("PLAYER_POSITION_AND_ROTATION", 13), 
    PLAYER_ROTATION("PLAYER_ROTATION", 14), 
    PLAYER_MOVEMENT("PLAYER_MOVEMENT", 15), 
    VEHICLE_MOVE("VEHICLE_MOVE", 16), 
    STEER_BOAT("STEER_BOAT", 17), 
    PLAYER_ABILITIES("PLAYER_ABILITIES", 18), 
    PLAYER_DIGGING("PLAYER_DIGGING", 19), 
    ENTITY_ACTION("ENTITY_ACTION", 20), 
    STEER_VEHICLE("STEER_VEHICLE", 21), 
    RESOURCE_PACK_STATUS("RESOURCE_PACK_STATUS", 22), 
    HELD_ITEM_CHANGE("HELD_ITEM_CHANGE", 23), 
    CREATIVE_INVENTORY_ACTION("CREATIVE_INVENTORY_ACTION", 24), 
    UPDATE_SIGN("UPDATE_SIGN", 25), 
    ANIMATION("ANIMATION", 26), 
    SPECTATE("SPECTATE", 27), 
    PLAYER_BLOCK_PLACEMENT("PLAYER_BLOCK_PLACEMENT", 28), 
    USE_ITEM("USE_ITEM", 29);
    
    private static final ServerboundPackets1_9[] $VALUES;
    
    private ServerboundPackets1_9(final String s, final int n) {
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
        $VALUES = new ServerboundPackets1_9[] { ServerboundPackets1_9.TELEPORT_CONFIRM, ServerboundPackets1_9.TAB_COMPLETE, ServerboundPackets1_9.CHAT_MESSAGE, ServerboundPackets1_9.CLIENT_STATUS, ServerboundPackets1_9.CLIENT_SETTINGS, ServerboundPackets1_9.WINDOW_CONFIRMATION, ServerboundPackets1_9.CLICK_WINDOW_BUTTON, ServerboundPackets1_9.CLICK_WINDOW, ServerboundPackets1_9.CLOSE_WINDOW, ServerboundPackets1_9.PLUGIN_MESSAGE, ServerboundPackets1_9.INTERACT_ENTITY, ServerboundPackets1_9.KEEP_ALIVE, ServerboundPackets1_9.PLAYER_POSITION, ServerboundPackets1_9.PLAYER_POSITION_AND_ROTATION, ServerboundPackets1_9.PLAYER_ROTATION, ServerboundPackets1_9.PLAYER_MOVEMENT, ServerboundPackets1_9.VEHICLE_MOVE, ServerboundPackets1_9.STEER_BOAT, ServerboundPackets1_9.PLAYER_ABILITIES, ServerboundPackets1_9.PLAYER_DIGGING, ServerboundPackets1_9.ENTITY_ACTION, ServerboundPackets1_9.STEER_VEHICLE, ServerboundPackets1_9.RESOURCE_PACK_STATUS, ServerboundPackets1_9.HELD_ITEM_CHANGE, ServerboundPackets1_9.CREATIVE_INVENTORY_ACTION, ServerboundPackets1_9.UPDATE_SIGN, ServerboundPackets1_9.ANIMATION, ServerboundPackets1_9.SPECTATE, ServerboundPackets1_9.PLAYER_BLOCK_PLACEMENT, ServerboundPackets1_9.USE_ITEM };
    }
}
