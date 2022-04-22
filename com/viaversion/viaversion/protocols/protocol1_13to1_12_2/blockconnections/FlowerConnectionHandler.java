package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.api.minecraft.*;
import com.viaversion.viaversion.api.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class FlowerConnectionHandler extends ConnectionHandler
{
    private static final Int2IntMap flowers;
    
    static ConnectionData.ConnectorInitAction init() {
        final HashSet<String> set = new HashSet<String>();
        set.add("minecraft:rose_bush");
        set.add("minecraft:sunflower");
        set.add("minecraft:peony");
        set.add("minecraft:tall_grass");
        set.add("minecraft:large_fern");
        set.add("minecraft:lilac");
        return FlowerConnectionHandler::lambda$init$0;
    }
    
    @Override
    public int connect(final UserConnection userConnection, final Position position, final int n) {
        final int value = FlowerConnectionHandler.flowers.get(this.getBlockData(userConnection, position.getRelative(BlockFace.BOTTOM)));
        if (value != 0) {
            final int blockData = this.getBlockData(userConnection, position.getRelative(BlockFace.TOP));
            if (Via.getConfig().isStemWhenBlockAbove()) {
                if (blockData == 0) {
                    return value;
                }
            }
            else if (!FlowerConnectionHandler.flowers.containsKey(blockData)) {
                return value;
            }
        }
        return n;
    }
    
    private static void lambda$init$0(final Set set, final FlowerConnectionHandler flowerConnectionHandler, final WrappedBlockData wrappedBlockData) {
        if (set.contains(wrappedBlockData.getMinecraftKey())) {
            ConnectionData.connectionHandlerMap.put(wrappedBlockData.getSavedBlockStateId(), flowerConnectionHandler);
            if (wrappedBlockData.getValue("half").equals("lower")) {
                wrappedBlockData.set("half", "upper");
                FlowerConnectionHandler.flowers.put(wrappedBlockData.getSavedBlockStateId(), wrappedBlockData.getBlockStateId());
            }
        }
    }
    
    static {
        flowers = new Int2IntOpenHashMap();
    }
}
