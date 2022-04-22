package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

class ChestConnectionHandler extends ConnectionHandler
{
    private static final Map chestFacings;
    private static final Map connectedStates;
    private static final Set trappedChests;
    
    static ConnectionData.ConnectorInitAction init() {
        return ChestConnectionHandler::lambda$init$0;
    }
    
    private static Byte getStates(final WrappedBlockData wrappedBlockData) {
        final String value = wrappedBlockData.getValue("type");
        if (value.equals("left")) {
            final byte b = 1;
        }
        if (value.equals("right")) {
            final byte b2 = 2;
        }
        final byte b3 = (byte)(0x0 | BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)).ordinal() << 2);
        if (wrappedBlockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
            final byte b4 = 16;
        }
        return 0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final BlockFace blockFace = ChestConnectionHandler.chestFacings.get(n);
        final byte b = (byte)(0x0 | blockFace.ordinal() << 2);
        final boolean contains = ChestConnectionHandler.trappedChests.contains(n);
        if (contains) {
            final byte b2 = 16;
        }
        final int blockData;
        if (ChestConnectionHandler.chestFacings.containsKey(blockData = this.getBlockData(userConnection, position.getRelative(BlockFace.NORTH))) && contains == ChestConnectionHandler.trappedChests.contains(blockData)) {
            final byte b3 = (byte)(0x0 | ((blockFace == BlockFace.WEST) ? 1 : 2));
        }
        else {
            final int blockData2;
            if (ChestConnectionHandler.chestFacings.containsKey(blockData2 = this.getBlockData(userConnection, position.getRelative(BlockFace.SOUTH))) && contains == ChestConnectionHandler.trappedChests.contains(blockData2)) {
                final byte b4 = (byte)(0x0 | ((blockFace == BlockFace.EAST) ? 1 : 2));
            }
            else {
                final int blockData3;
                if (ChestConnectionHandler.chestFacings.containsKey(blockData3 = this.getBlockData(userConnection, position.getRelative(BlockFace.WEST))) && contains == ChestConnectionHandler.trappedChests.contains(blockData3)) {
                    final byte b5 = (byte)(0x0 | ((blockFace == BlockFace.NORTH) ? 2 : 1));
                }
                else {
                    final int blockData4;
                    if (ChestConnectionHandler.chestFacings.containsKey(blockData4 = this.getBlockData(userConnection, position.getRelative(BlockFace.EAST))) && contains == ChestConnectionHandler.trappedChests.contains(blockData4)) {
                        final byte b6 = (byte)(0x0 | ((blockFace == BlockFace.SOUTH) ? 2 : 1));
                    }
                }
            }
        }
        final Integer n2 = ChestConnectionHandler.connectedStates.get(0);
        return (n2 == null) ? n : n2;
    }
    
    private static void lambda$init$0(final ChestConnectionHandler chestConnectionHandler, final WrappedBlockData wrappedBlockData) {
        if (!wrappedBlockData.getMinecraftKey().equals("minecraft:chest") && !wrappedBlockData.getMinecraftKey().equals("minecraft:trapped_chest")) {
            return;
        }
        if (wrappedBlockData.getValue("waterlogged").equals("true")) {
            return;
        }
        ChestConnectionHandler.chestFacings.put(wrappedBlockData.getSavedBlockStateId(), BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)));
        if (wrappedBlockData.getMinecraftKey().equalsIgnoreCase("minecraft:trapped_chest")) {
            ChestConnectionHandler.trappedChests.add(wrappedBlockData.getSavedBlockStateId());
        }
        ChestConnectionHandler.connectedStates.put(getStates(wrappedBlockData), wrappedBlockData.getSavedBlockStateId());
        ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), chestConnectionHandler);
    }
    
    static {
        chestFacings = new HashMap();
        connectedStates = new HashMap();
        trappedChests = new HashSet();
    }
}
