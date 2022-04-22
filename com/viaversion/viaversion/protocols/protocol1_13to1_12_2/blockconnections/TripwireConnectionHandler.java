package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.*;

public class TripwireConnectionHandler extends ConnectionHandler
{
    private static final Map tripwireDataMap;
    private static final Map connectedBlocks;
    private static final Map tripwireHooks;
    
    static ConnectionData.ConnectorInitAction init() {
        return TripwireConnectionHandler::lambda$init$0;
    }
    
    private static byte getStates(final WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getValue("attached").equals("true")) {
            final byte b = 1;
        }
        if (wrappedBlockData.getValue("disarmed").equals("true")) {
            final byte b2 = 2;
        }
        if (wrappedBlockData.getValue("powered").equals("true")) {
            final byte b3 = 4;
        }
        if (wrappedBlockData.getValue("east").equals("true")) {
            final byte b4 = 8;
        }
        if (wrappedBlockData.getValue("north").equals("true")) {
            final byte b5 = 16;
        }
        if (wrappedBlockData.getValue("south").equals("true")) {
            final byte b6 = 32;
        }
        if (wrappedBlockData.getValue("west").equals("true")) {
            final byte b7 = 64;
        }
        return 0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final TripwireData tripwireData = TripwireConnectionHandler.tripwireDataMap.get(n);
        if (tripwireData == null) {
            return n;
        }
        if (tripwireData.isAttached()) {
            final byte b = 1;
        }
        if (tripwireData.isDisarmed()) {
            final byte b2 = 2;
        }
        if (tripwireData.isPowered()) {
            final byte b3 = 4;
        }
        final int blockData = this.getBlockData(userConnection, position.getRelative(BlockFace.EAST));
        final int blockData2 = this.getBlockData(userConnection, position.getRelative(BlockFace.NORTH));
        final int blockData3 = this.getBlockData(userConnection, position.getRelative(BlockFace.SOUTH));
        final int blockData4 = this.getBlockData(userConnection, position.getRelative(BlockFace.WEST));
        if (TripwireConnectionHandler.tripwireDataMap.containsKey(blockData) || TripwireConnectionHandler.tripwireHooks.get(blockData) == BlockFace.WEST) {
            final byte b4 = 8;
        }
        if (TripwireConnectionHandler.tripwireDataMap.containsKey(blockData2) || TripwireConnectionHandler.tripwireHooks.get(blockData2) == BlockFace.SOUTH) {
            final byte b5 = 16;
        }
        if (TripwireConnectionHandler.tripwireDataMap.containsKey(blockData3) || TripwireConnectionHandler.tripwireHooks.get(blockData3) == BlockFace.NORTH) {
            final byte b6 = 32;
        }
        if (TripwireConnectionHandler.tripwireDataMap.containsKey(blockData4) || TripwireConnectionHandler.tripwireHooks.get(blockData4) == BlockFace.EAST) {
            final byte b7 = 64;
        }
        final Integer n2 = TripwireConnectionHandler.connectedBlocks.get(0);
        return (n2 == null) ? n : n2;
    }
    
    private static void lambda$init$0(final TripwireConnectionHandler tripwireConnectionHandler, final WrappedBlockData wrappedBlockData) {
        if (wrappedBlockData.getMinecraftKey().equals("minecraft:tripwire_hook")) {
            TripwireConnectionHandler.tripwireHooks.put(wrappedBlockData.getSavedBlockStateId(), BlockFace.valueOf(wrappedBlockData.getValue("facing").toUpperCase(Locale.ROOT)));
        }
        else if (wrappedBlockData.getMinecraftKey().equals("minecraft:tripwire")) {
            TripwireConnectionHandler.tripwireDataMap.put(wrappedBlockData.getSavedBlockStateId(), new TripwireData(wrappedBlockData.getValue("attached").equals("true"), wrappedBlockData.getValue("disarmed").equals("true"), wrappedBlockData.getValue("powered").equals("true"), null));
            TripwireConnectionHandler.connectedBlocks.put(getStates(wrappedBlockData), wrappedBlockData.getSavedBlockStateId());
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), tripwireConnectionHandler);
        }
    }
    
    static {
        tripwireDataMap = new HashMap();
        connectedBlocks = new HashMap();
        tripwireHooks = new HashMap();
    }
    
    private static final class TripwireData
    {
        private final boolean attached;
        private final boolean disarmed;
        private final boolean powered;
        
        private TripwireData(final boolean attached, final boolean disarmed, final boolean powered) {
            this.attached = attached;
            this.disarmed = disarmed;
            this.powered = powered;
        }
        
        public boolean isAttached() {
            return this.attached;
        }
        
        public boolean isDisarmed() {
            return this.disarmed;
        }
        
        public boolean isPowered() {
            return this.powered;
        }
        
        TripwireData(final boolean b, final boolean b2, final boolean b3, final TripwireConnectionHandler$1 object) {
            this(b, b2, b3);
        }
    }
}
