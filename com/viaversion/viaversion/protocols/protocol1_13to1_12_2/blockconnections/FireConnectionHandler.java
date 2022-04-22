package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

public class FireConnectionHandler extends ConnectionHandler
{
    private static final Map connectedBlocks;
    private static final Set flammableBlocks;
    
    private static void addWoodTypes(final Set set, final String s) {
        final String[] wood_TYPES = FireConnectionHandler.WOOD_TYPES;
        while (0 < wood_TYPES.length) {
            set.add("minecraft:" + wood_TYPES[0] + s);
            int n = 0;
            ++n;
        }
    }
    
    static ConnectionData.ConnectorInitAction init() {
        final HashSet<String> set = new HashSet<String>();
        set.add("minecraft:tnt");
        set.add("minecraft:vine");
        set.add("minecraft:bookshelf");
        set.add("minecraft:hay_block");
        set.add("minecraft:deadbush");
        addWoodTypes(set, "_slab");
        addWoodTypes(set, "_log");
        addWoodTypes(set, "_planks");
        addWoodTypes(set, "_leaves");
        addWoodTypes(set, "_fence");
        addWoodTypes(set, "_fence_gate");
        addWoodTypes(set, "_stairs");
        return FireConnectionHandler::lambda$init$0;
    }
    
    private static byte getStates(final WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getValue("east").equals("true")) {
            final byte b = 1;
        }
        if (wrappedBlockData.getValue("north").equals("true")) {
            final byte b2 = 2;
        }
        if (wrappedBlockData.getValue("south").equals("true")) {
            final byte b3 = 4;
        }
        if (wrappedBlockData.getValue("up").equals("true")) {
            final byte b4 = 8;
        }
        if (wrappedBlockData.getValue("west").equals("true")) {
            final byte b5 = 16;
        }
        return 0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        if (FireConnectionHandler.flammableBlocks.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.EAST)))) {
            final byte b = 1;
        }
        if (FireConnectionHandler.flammableBlocks.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.NORTH)))) {
            final byte b2 = 2;
        }
        if (FireConnectionHandler.flammableBlocks.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.SOUTH)))) {
            final byte b3 = 4;
        }
        if (FireConnectionHandler.flammableBlocks.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)))) {
            final byte b4 = 8;
        }
        if (FireConnectionHandler.flammableBlocks.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.WEST)))) {
            final byte b5 = 16;
        }
        return FireConnectionHandler.connectedBlocks.get(0);
    }
    
    private static void lambda$init$0(final Set set, final FireConnectionHandler fireConnectionHandler, final WrappedBlockData wrappedBlockData) {
        final String minecraftKey = wrappedBlockData.getMinecraftKey();
        if (minecraftKey.contains("_wool") || minecraftKey.contains("_carpet") || set.contains(minecraftKey)) {
            FireConnectionHandler.flammableBlocks.add(wrappedBlockData.getSavedBlockStateId());
        }
        else if (minecraftKey.equals("minecraft:fire")) {
            final int savedBlockStateId = wrappedBlockData.getSavedBlockStateId();
            FireConnectionHandler.connectedBlocks.put(getStates(wrappedBlockData), savedBlockStateId);
            ConnectionData.connectionHandlerMap.put(savedBlockStateId, fireConnectionHandler);
        }
    }
    
    static {
        FireConnectionHandler.WOOD_TYPES = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "dark_oak" };
        connectedBlocks = new HashMap();
        flammableBlocks = new HashSet();
    }
}
