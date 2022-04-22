package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class RedstoneConnectionHandler extends ConnectionHandler
{
    private static final Set redstone;
    private static final Int2IntMap connectedBlockStates;
    private static final Int2IntMap powerMappings;
    
    static ConnectionData.ConnectorInitAction init() {
        return RedstoneConnectionHandler::lambda$init$0;
    }
    
    private static short getStates(final WrappedBlockData wrappedBlockData) {
        final short n = (short)(0x0 | getState(wrappedBlockData.getValue("east")));
        final short n2 = (short)(0x0 | getState(wrappedBlockData.getValue("north")) << 2);
        final short n3 = (short)(0x0 | getState(wrappedBlockData.getValue("south")) << 4);
        final short n4 = (short)(0x0 | getState(wrappedBlockData.getValue("west")) << 6);
        final short n5 = (short)(0x0 | Integer.parseInt(wrappedBlockData.getValue("power")) << 8);
        return 0;
    }
    
    private static int getState(final String s) {
        switch (s.hashCode()) {
            case 3387192: {
                if (s.equals("none")) {
                    break;
                }
                break;
            }
            case 3530071: {
                if (s.equals("side")) {
                    break;
                }
                break;
            }
            case 3739: {
                if (s.equals("up")) {}
                break;
            }
        }
        switch (2) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final short n2 = (short)(0x0 | this.connects(userConnection, position, BlockFace.EAST));
        final short n3 = (short)(0x0 | this.connects(userConnection, position, BlockFace.NORTH) << 2);
        final short n4 = (short)(0x0 | this.connects(userConnection, position, BlockFace.SOUTH) << 4);
        final short n5 = (short)(0x0 | this.connects(userConnection, position, BlockFace.WEST) << 6);
        final short n6 = (short)(0x0 | RedstoneConnectionHandler.powerMappings.get(n) << 8);
        return RedstoneConnectionHandler.connectedBlockStates.getOrDefault(0, n);
    }
    
    private int connects(final UserConnection userConnection, final Position position, final BlockFace blockFace) {
        final Position relative = position.getRelative(blockFace);
        if (this.getBlockData(userConnection, relative) != null) {
            return 1;
        }
        if (RedstoneConnectionHandler.redstone.contains(this.getBlockData(userConnection, relative.getRelative(BlockFace.TOP))) && !ConnectionData.occludingStates.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)))) {
            return 2;
        }
        if (RedstoneConnectionHandler.redstone.contains(this.getBlockData(userConnection, relative.getRelative(BlockFace.BOTTOM))) && !ConnectionData.occludingStates.contains(this.getBlockData(userConnection, relative))) {
            return 1;
        }
        return 0;
    }
    
    private static void lambda$init$0(final RedstoneConnectionHandler redstoneConnectionHandler, final WrappedBlockData wrappedBlockData) {
        if (!"minecraft:redstone_wire".equals(wrappedBlockData.getMinecraftKey())) {
            return;
        }
        RedstoneConnectionHandler.redstone.add(wrappedBlockData.getSavedBlockStateId());
        ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), redstoneConnectionHandler);
        RedstoneConnectionHandler.connectedBlockStates.put(getStates(wrappedBlockData), wrappedBlockData.getSavedBlockStateId());
        RedstoneConnectionHandler.powerMappings.put(wrappedBlockData.getSavedBlockStateId(), Integer.parseInt(wrappedBlockData.getValue("power")));
    }
    
    static {
        redstone = new HashSet();
        connectedBlockStates = new Int2IntOpenHashMap(1296);
        powerMappings = new Int2IntOpenHashMap(1296);
    }
}
