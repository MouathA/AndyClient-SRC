package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viaversion.api.protocol.packet.*;

public enum ServerboundPackets1_7 implements ServerboundPacketType
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
    PLUGIN_MESSAGE("PLUGIN_MESSAGE", 23);
    
    private static final ServerboundPackets1_7[] $VALUES;
    
    private ServerboundPackets1_7(final String s, final int n) {
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
        $VALUES = new ServerboundPackets1_7[] { ServerboundPackets1_7.KEEP_ALIVE, ServerboundPackets1_7.CHAT_MESSAGE, ServerboundPackets1_7.INTERACT_ENTITY, ServerboundPackets1_7.PLAYER_MOVEMENT, ServerboundPackets1_7.PLAYER_POSITION, ServerboundPackets1_7.PLAYER_ROTATION, ServerboundPackets1_7.PLAYER_POSITION_AND_ROTATION, ServerboundPackets1_7.PLAYER_DIGGING, ServerboundPackets1_7.PLAYER_BLOCK_PLACEMENT, ServerboundPackets1_7.HELD_ITEM_CHANGE, ServerboundPackets1_7.ANIMATION, ServerboundPackets1_7.ENTITY_ACTION, ServerboundPackets1_7.STEER_VEHICLE, ServerboundPackets1_7.CLOSE_WINDOW, ServerboundPackets1_7.CLICK_WINDOW, ServerboundPackets1_7.WINDOW_CONFIRMATION, ServerboundPackets1_7.CREATIVE_INVENTORY_ACTION, ServerboundPackets1_7.CLICK_WINDOW_BUTTON, ServerboundPackets1_7.UPDATE_SIGN, ServerboundPackets1_7.PLAYER_ABILITIES, ServerboundPackets1_7.TAB_COMPLETE, ServerboundPackets1_7.CLIENT_SETTINGS, ServerboundPackets1_7.CLIENT_STATUS, ServerboundPackets1_7.PLUGIN_MESSAGE };
    }
}