package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.util.*;
import java.util.*;

public class SnowyGrassConnectionHandler extends ConnectionHandler
{
    private static final Map grassBlocks;
    private static final Set snows;
    
    static ConnectionData.ConnectorInitAction init() {
        final HashSet<String> set = new HashSet<String>();
        set.add("minecraft:grass_block");
        set.add("minecraft:podzol");
        set.add("minecraft:mycelium");
        return SnowyGrassConnectionHandler::lambda$init$0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final Integer n2 = SnowyGrassConnectionHandler.grassBlocks.get(new Pair(n, SnowyGrassConnectionHandler.snows.contains(this.getBlockData(userConnection, position.getRelative(BlockFace.TOP)))));
        if (n2 != null) {
            return n2;
        }
        return n;
    }
    
    private static void lambda$init$0(final Set set, final SnowyGrassConnectionHandler snowyGrassConnectionHandler, final WrappedBlockData wrappedBlockData) {
        if (set.contains(wrappedBlockData.getMinecraftKey())) {
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), snowyGrassConnectionHandler);
            wrappedBlockData.set("snowy", "true");
            SnowyGrassConnectionHandler.grassBlocks.put(new Pair(wrappedBlockData.getSavedBlockStateId(), true), wrappedBlockData.getBlockStateId());
            wrappedBlockData.set("snowy", "false");
            SnowyGrassConnectionHandler.grassBlocks.put(new Pair(wrappedBlockData.getSavedBlockStateId(), false), wrappedBlockData.getBlockStateId());
        }
        if (wrappedBlockData.getMinecraftKey().equals("minecraft:snow") || wrappedBlockData.getMinecraftKey().equals("minecraft:snow_block")) {
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), snowyGrassConnectionHandler);
            SnowyGrassConnectionHandler.snows.add(wrappedBlockData.getSavedBlockStateId());
        }
    }
    
    static {
        grassBlocks = new HashMap();
        snows = new HashSet();
    }
}
