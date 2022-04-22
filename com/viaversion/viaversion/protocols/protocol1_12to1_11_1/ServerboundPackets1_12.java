package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundPackets1_12 implements ServerboundPacketType
{
    TELEPORT_CONFIRM("TELEPORT_CONFIRM", 0), 
    PREPARE_CRAFTING_GRID("PREPARE_CRAFTING_GRID", 1), 
    TAB_COMPLETE("TAB_COMPLETE", 2), 
    CHAT_MESSAGE("CHAT_MESSAGE", 3), 
    CLIENT_STATUS("CLIENT_STATUS", 4), 
    CLIENT_SETTINGS("CLIENT_SETTINGS", 5), 
    WINDOW_CONFIRMATION("WINDOW_CONFIRMATION", 6), 
    CLICK_WINDOW_BUTTON("CLICK_WINDOW_BUTTON", 7), 
    CLICK_WINDOW("CLICK_WINDOW", 8), 
    CLOSE_WINDOW("CLOSE_WINDOW", 9), 
    PLUGIN_MESSAGE("PLUGIN_MESSAGE", 10), 
    INTERACT_ENTITY("INTERACT_ENTITY", 11), 
    KEEP_ALIVE("KEEP_ALIVE", 12), 
    PLAYER_MOVEMENT("PLAYER_MOVEMENT", 13), 
    PLAYER_POSITION("PLAYER_POSITION", 14), 
    PLAYER_POSITION_AND_ROTATION("PLAYER_POSITION_AND_ROTATION", 15), 
    PLAYER_ROTATION("PLAYER_ROTATION", 16), 
    VEHICLE_MOVE("VEHICLE_MOVE", 17), 
    STEER_BOAT("STEER_BOAT", 18), 
    PLAYER_ABILITIES("PLAYER_ABILITIES", 19), 
    PLAYER_DIGGING("PLAYER_DIGGING", 20), 
    ENTITY_ACTION("ENTITY_ACTION", 21), 
    STEER_VEHICLE("STEER_VEHICLE", 22), 
    RECIPE_BOOK_DATA("RECIPE_BOOK_DATA", 23), 
    RESOURCE_PACK_STATUS("RESOURCE_PACK_STATUS", 24), 
    ADVANCEMENT_TAB("ADVANCEMENT_TAB", 25), 
    HELD_ITEM_CHANGE("HELD_ITEM_CHANGE", 26), 
    CREATIVE_INVENTORY_ACTION("CREATIVE_INVENTORY_ACTION", 27), 
    UPDATE_SIGN("UPDATE_SIGN", 28), 
    ANIMATION("ANIMATION", 29), 
    SPECTATE("SPECTATE", 30), 
    PLAYER_BLOCK_PLACEMENT("PLAYER_BLOCK_PLACEMENT", 31), 
    USE_ITEM("USE_ITEM", 32);
    
    private static final ServerboundPackets1_12[] $VALUES;
    
    private ServerboundPackets1_12(final String s, final int n) {
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
        $VALUES = new ServerboundPackets1_12[] { ServerboundPackets1_12.TELEPORT_CONFIRM, ServerboundPackets1_12.PREPARE_CRAFTING_GRID, ServerboundPackets1_12.TAB_COMPLETE, ServerboundPackets1_12.CHAT_MESSAGE, ServerboundPackets1_12.CLIENT_STATUS, ServerboundPackets1_12.CLIENT_SETTINGS, ServerboundPackets1_12.WINDOW_CONFIRMATION, ServerboundPackets1_12.CLICK_WINDOW_BUTTON, ServerboundPackets1_12.CLICK_WINDOW, ServerboundPackets1_12.CLOSE_WINDOW, ServerboundPackets1_12.PLUGIN_MESSAGE, ServerboundPackets1_12.INTERACT_ENTITY, ServerboundPackets1_12.KEEP_ALIVE, ServerboundPackets1_12.PLAYER_MOVEMENT, ServerboundPackets1_12.PLAYER_POSITION, ServerboundPackets1_12.PLAYER_POSITION_AND_ROTATION, ServerboundPackets1_12.PLAYER_ROTATION, ServerboundPackets1_12.VEHICLE_MOVE, ServerboundPackets1_12.STEER_BOAT, ServerboundPackets1_12.PLAYER_ABILITIES, ServerboundPackets1_12.PLAYER_DIGGING, ServerboundPackets1_12.ENTITY_ACTION, ServerboundPackets1_12.STEER_VEHICLE, ServerboundPackets1_12.RECIPE_BOOK_DATA, ServerboundPackets1_12.RESOURCE_PACK_STATUS, ServerboundPackets1_12.ADVANCEMENT_TAB, ServerboundPackets1_12.HELD_ITEM_CHANGE, ServerboundPackets1_12.CREATIVE_INVENTORY_ACTION, ServerboundPackets1_12.UPDATE_SIGN, ServerboundPackets1_12.ANIMATION, ServerboundPackets1_12.SPECTATE, ServerboundPackets1_12.PLAYER_BLOCK_PLACEMENT, ServerboundPackets1_12.USE_ITEM };
    }
}
