package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;

public class ChorusPlantConnectionHandler extends AbstractFenceConnectionHandler
{
    private final int endstone;
    
    static List init() {
        final ArrayList<ConnectionData.ConnectorInitAction> list = new ArrayList<ConnectionData.ConnectorInitAction>(2);
        final ChorusPlantConnectionHandler chorusPlantConnectionHandler = new ChorusPlantConnectionHandler();
        list.add(chorusPlantConnectionHandler.getInitAction("minecraft:chorus_plant"));
        list.add(chorusPlantConnectionHandler.getExtraAction());
        return list;
    }
    
    public ChorusPlantConnectionHandler() {
        super(null);
        this.endstone = ConnectionData.getId("minecraft:end_stone");
    }
    
    public ConnectionData.ConnectorInitAction getExtraAction() {
        return this::lambda$getExtraAction$0;
    }
    
    @Override
    protected byte getStates(final WrappedBlockData wrappedBlockData) {
        byte states = super.getStates(wrappedBlockData);
        if (wrappedBlockData.getValue("up").equals("true")) {
            states |= 0x10;
        }
        if (wrappedBlockData.getValue("down").equals("true")) {
            states |= 0x20;
        }
        return states;
    }
    
    @Override
    protected byte getStates(final UserConnection userConnection, final Position position, final int n) {
        byte states = super.getStates(userConnection, position, n);
        if (this.connects(BlockFace.TOP, this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)), false)) {
            states |= 0x10;
        }
        if (this.connects(BlockFace.BOTTOM, this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM)), false)) {
            states |= 0x20;
        }
        return states;
    }
    
    @Override
    protected boolean connects(final BlockFace blockFace, final int n, final boolean b) {
        return this.getBlockStates().contains(n) || (blockFace == BlockFace.BOTTOM && n == this.endstone);
    }
    
    private void lambda$getExtraAction$0(final WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getMinecraftKey().equals("minecraft:chorus_flower")) {
            this.getBlockStates().add(wrappedBlockData.getSavedBlockStateId());
        }
    }
}
