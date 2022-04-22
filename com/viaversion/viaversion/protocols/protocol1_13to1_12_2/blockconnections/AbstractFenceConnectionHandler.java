package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.api.minecraft.*;

public abstract class AbstractFenceConnectionHandler extends ConnectionHandler
{
    private static final StairConnectionHandler STAIR_CONNECTION_HANDLER;
    private final String blockConnections;
    private final Set blockStates;
    private final Map connectedBlockStates;
    
    protected AbstractFenceConnectionHandler(final String blockConnections) {
        this.blockStates = new HashSet();
        this.connectedBlockStates = new HashMap();
        this.blockConnections = blockConnections;
    }
    
    public ConnectionData.ConnectorInitAction getInitAction(final String s) {
        return this::lambda$getInitAction$0;
    }
    
    protected byte getStates(final WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getValue("east").equals("true")) {
            final byte b = 1;
        }
        if (wrappedBlockData.getValue("north").equals("true")) {
            final byte b2 = 2;
        }
        if (wrappedBlockData.getValue("south").equals("true")) {
            final byte b3 = 4;
        }
        if (wrappedBlockData.getValue("west").equals("true")) {
            final byte b4 = 8;
        }
        return 0;
    }
    
    protected byte getStates(final UserConnection userConnection, final Position position, final int n) {
        final boolean b = userConnection.getProtocolInfo().getServerProtocolVersion() < ProtocolVersion.v1_12.getVersion();
        if (this.connects(BlockFace.EAST, this.getBlockData(userConnection, position.getRelative(BlockFace.EAST)), b)) {
            final byte b2 = 1;
        }
        if (this.connects(BlockFace.NORTH, this.getBlockData(userConnection, position.getRelative(BlockFace.NORTH)), b)) {
            final byte b3 = 2;
        }
        if (this.connects(BlockFace.SOUTH, this.getBlockData(userConnection, position.getRelative(BlockFace.SOUTH)), b)) {
            final byte b4 = 4;
        }
        if (this.connects(BlockFace.WEST, this.getBlockData(userConnection, position.getRelative(BlockFace.WEST)), b)) {
            final byte b5 = 8;
        }
        return 0;
    }
    
    @Override
    public int getBlockData(final UserConnection userConnection, final Position position) {
        return AbstractFenceConnectionHandler.STAIR_CONNECTION_HANDLER.connect(userConnection, position, super.getBlockData(userConnection, position));
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final Integer n2 = this.connectedBlockStates.get(this.getStates(userConnection, position, n));
        return (n2 == null) ? n : n2;
    }
    
    protected boolean connects(final BlockFace blockFace, final int n, final boolean b) {
        if (this.blockStates.contains(n)) {
            return true;
        }
        if (this.blockConnections == null) {
            return false;
        }
        final BlockData blockData = (BlockData)ConnectionData.blockConnectionData.get(n);
        return blockData != null && blockData.connectsTo(this.blockConnections, blockFace.opposite(), b);
    }
    
    public Set getBlockStates() {
        return this.blockStates;
    }
    
    private void lambda$getInitAction$0(final String s, final AbstractFenceConnectionHandler abstractFenceConnectionHandler, final WrappedBlockData wrappedBlockData) {
        if (s.equals(wrappedBlockData.getMinecraftKey())) {
            if (wrappedBlockData.hasData("waterlogged") && wrappedBlockData.getValue("waterlogged").equals("true")) {
                return;
            }
            this.blockStates.add(wrappedBlockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), abstractFenceConnectionHandler);
            this.connectedBlockStates.put(this.getStates(wrappedBlockData), wrappedBlockData.getSavedBlockStateId());
        }
    }
    
    static {
        STAIR_CONNECTION_HANDLER = new StairConnectionHandler();
    }
}
