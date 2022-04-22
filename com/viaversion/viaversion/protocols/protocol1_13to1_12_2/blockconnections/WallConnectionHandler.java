package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;

public class WallConnectionHandler extends AbstractFenceConnectionHandler
{
    private static final BlockFace[] BLOCK_FACES;
    private static final int[] OPPOSITES;
    
    static List init() {
        final ArrayList<ConnectionData.ConnectorInitAction> list = new ArrayList<ConnectionData.ConnectorInitAction>(2);
        list.add(new WallConnectionHandler("cobbleWallConnections").getInitAction("minecraft:cobblestone_wall"));
        list.add(new WallConnectionHandler("cobbleWallConnections").getInitAction("minecraft:mossy_cobblestone_wall"));
        return list;
    }
    
    public WallConnectionHandler(final String s) {
        super(s);
    }
    
    @Override
    protected byte getStates(final WrappedBlockData wrappedBlockData) {
        byte states = super.getStates(wrappedBlockData);
        if (wrappedBlockData.getValue("up").equals("true")) {
            states |= 0x10;
        }
        return states;
    }
    
    @Override
    protected byte getStates(final UserConnection userConnection, final Position position, final int n) {
        byte states = super.getStates(userConnection, position, n);
        if (this.up(userConnection, position)) {
            states |= 0x10;
        }
        return states;
    }
    
    public boolean up(final UserConnection userConnection, final Position position) {
        if (this.isWall(this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM))) || this.isWall(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)))) {
            return true;
        }
        final int blockFaces = this.getBlockFaces(userConnection, position);
        if (blockFaces == 0 || blockFaces == 15) {
            return true;
        }
        for (int i = 0; i < WallConnectionHandler.BLOCK_FACES.length; ++i) {
            if ((blockFaces & 1 << i) != 0x0 && (blockFaces & 1 << WallConnectionHandler.OPPOSITES[i]) == 0x0) {
                return true;
            }
        }
        return false;
    }
    
    private int getBlockFaces(final UserConnection userConnection, final Position position) {
        int n = 0;
        for (int i = 0; i < WallConnectionHandler.BLOCK_FACES.length; ++i) {
            if (this.isWall(this.getBlockData(userConnection, position.getRelative(WallConnectionHandler.BLOCK_FACES[i])))) {
                n |= 1 << i;
            }
        }
        return n;
    }
    
    private boolean isWall(final int n) {
        return this.getBlockStates().contains(n);
    }
    
    static {
        BLOCK_FACES = new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };
        OPPOSITES = new int[] { 3, 2, 1, 0 };
    }
}
