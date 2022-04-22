package com.viaversion.viaversion.protocols.protocol1_8;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundPackets1_8 implements ServerboundPacketType
{
    KEEP_ALIVE("KEEP_ALIVE", 0), 
    CHAT_MESSAGE("CHAT_MESSAGE", 1), 
    INTERACT_ENTITY("INTERACT_ENTITY", 2), 
    PLAYER_MOVEMENT("PLAYER_MOVEMENT", 3), 
    PLAYER_POSITION("PLAYER_POSITION", 4), 
    PLAYER_ROTATION("PLAYER_ROTATION", 5), 
    PLAYER_POSITION_AND_ROTATION("PLAYER_POSITION_AND_ROTATION", 6), 
    PLAYER_DIGGING("PLAYER_DIGGING", 7), 
    PLAYER_BLOCK_PLACEMENT("PLAYER_BLOCK_PLACEMENT", 8), 
    HELD_ITEM_CHANGE("HELD_ITEM_CHANGE", 9), 
    ANIMATION("ANIMATION", 10), 
    ENTITY_ACTION("ENTITY_ACTION", 11), 
    STEER_VEHICLE("STEER_VEHICLE", 12), 
    CLOSE_WINDOW("CLOSE_WINDOW", 13), 
    CLICK_WINDOW("CLICK_WINDOW", 14), 
    WINDOW_CONFIRMATION("WINDOW_CONFIRMATION", 15), 
    CREATIVE_INVENTORY_ACTION("CREATIVE_INVENTORY_ACTION", 16), 
    CLICK_WINDOW_BUTTON("CLICK_WINDOW_BUTTON", 17), 
    UPDATE_SIGN("UPDATE_SIGN", 18), 
    PLAYER_ABILITIES("PLAYER_ABILITIES", 19), 
    TAB_COMPLETE("TAB_COMPLETE", 20), 
    CLIENT_SETTINGS("CLIENT_SETTINGS", 21), 
    CLIENT_STATUS("CLIENT_STATUS", 22), 
    PLUGIN_MESSAGE("PLUGIN_MESSAGE", 23), 
    SPECTATE("SPECTATE", 24), 
    RESOURCE_PACK_STATUS("RESOURCE_PACK_STATUS", 25);
    
    private static final ServerboundPackets1_8[] $VALUES;
    
    private ServerboundPackets1_8(final String s, final int n) {
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
        $VALUES = new ServerboundPackets1_8[] { ServerboundPackets1_8.KEEP_ALIVE, ServerboundPackets1_8.CHAT_MESSAGE, ServerboundPackets1_8.INTERACT_ENTITY, ServerboundPackets1_8.PLAYER_MOVEMENT, ServerboundPackets1_8.PLAYER_POSITION, ServerboundPackets1_8.PLAYER_ROTATION, ServerboundPackets1_8.PLAYER_POSITION_AND_ROTATION, ServerboundPackets1_8.PLAYER_DIGGING, ServerboundPackets1_8.PLAYER_BLOCK_PLACEMENT, ServerboundPackets1_8.HELD_ITEM_CHANGE, ServerboundPackets1_8.ANIMATION, ServerboundPackets1_8.ENTITY_ACTION, ServerboundPackets1_8.STEER_VEHICLE, ServerboundPackets1_8.CLOSE_WINDOW, ServerboundPackets1_8.CLICK_WINDOW, ServerboundPackets1_8.WINDOW_CONFIRMATION, ServerboundPackets1_8.CREATIVE_INVENTORY_ACTION, ServerboundPackets1_8.CLICK_WINDOW_BUTTON, ServerboundPackets1_8.UPDATE_SIGN, ServerboundPackets1_8.PLAYER_ABILITIES, ServerboundPackets1_8.TAB_COMPLETE, ServerboundPackets1_8.CLIENT_SETTINGS, ServerboundPackets1_8.CLIENT_STATUS, ServerboundPackets1_8.PLUGIN_MESSAGE, ServerboundPackets1_8.SPECTATE, ServerboundPackets1_8.RESOURCE_PACK_STATUS };
    }
}
