package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

class VineConnectionHandler extends ConnectionHandler
{
    private static final Set vines;
    
    static ConnectionData.ConnectorInitAction init() {
        return VineConnectionHandler::lambda$init$0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        if (this.isAttachedToBlock(userConnection, position)) {
            return n;
        }
        final Position relative = position.getRelative(BlockFace.TOP);
        if (VineConnectionHandler.vines.contains(this.getBlockData(userConnection, relative)) && this.isAttachedToBlock(userConnection, relative)) {
            return n;
        }
        return 0;
    }
    
    private boolean isAttachedToBlock(final UserConnection userConnection, final Position position) {
        return this.isAttachedToBlock(userConnection, position, BlockFace.EAST) || this.isAttachedToBlock(userConnection, position, BlockFace.WEST) || this.isAttachedToBlock(userConnection, position, BlockFace.NORTH) || this.isAttachedToBlock(userConnection, position, BlockFace.SOUTH);
    }
    
    private boolean isAttachedToBlock(final UserConnection userConnection, final Position position, final BlockFace blockFace) {
        return ConnectionData.occludingStates.contains(this.getBlockData(userConnection, position.getRelative(blockFace)));
    }
    
    private static void lambda$init$0(final VineConnectionHandler vineConnectionHandler, final WrappedBlockData wrappedBlockData) {
        if (!wrappedBlockData.getMinecraftKey().equals("minecraft:vine")) {
            return;
        }
        VineConnectionHandler.vines.add(wrappedBlockData.getSavedBlockStateId());
        ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), vineConnectionHandler);
    }
    
    static {
        vines = new HashSet();
    }
}
