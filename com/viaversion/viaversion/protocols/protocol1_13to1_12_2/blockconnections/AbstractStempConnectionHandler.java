package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

public abstract class AbstractStempConnectionHandler extends ConnectionHandler
{
    private static final BlockFace[] BLOCK_FACES;
    private final int baseStateId;
    private final Set blockId;
    private final Map stemps;
    
    protected AbstractStempConnectionHandler(final String s) {
        this.blockId = new HashSet();
        this.stemps = new HashMap();
        this.baseStateId = ConnectionData.getId(s);
    }
    
    public ConnectionData.ConnectorInitAction getInitAction(final String s, final String s2) {
        return this::lambda$getInitAction$0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        if (n != this.baseStateId) {
            return n;
        }
        final BlockFace[] block_FACES = AbstractStempConnectionHandler.BLOCK_FACES;
        while (0 < block_FACES.length) {
            final BlockFace blockFace = block_FACES[0];
            if (this.blockId.contains(this.getBlockData(userConnection, position.getRelative(blockFace)))) {
                return (int)this.stemps.get(blockFace);
            }
            int n2 = 0;
            ++n2;
        }
        return this.baseStateId;
    }
    
    private void lambda$getInitAction$0(final String s, final AbstractStempConnectionHandler abstractStempConnectionHandler, final String s2, final WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getSavedBlockStateId() == this.baseStateId || s.equals(wrappedBlockData.getMinecraftKey())) {
            if (wrappedBlockData.getSavedBlockStateId() != this.baseStateId) {
                abstractStempConnectionHandler.blockId.add(wrappedBlockData.getSavedBlockStateId());
            }
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), abstractStempConnectionHandler);
        }
        if (wrappedBlockData.getMinecraftKey().equals(s2)) {
            this.stemps.put(BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)), wrappedBlockData.getSavedBlockStateId());
        }
    }
    
    static {
        BLOCK_FACES = new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };
    }
}
