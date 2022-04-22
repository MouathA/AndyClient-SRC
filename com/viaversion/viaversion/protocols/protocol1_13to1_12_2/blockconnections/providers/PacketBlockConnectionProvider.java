package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;

import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.*;

public class PacketBlockConnectionProvider extends BlockConnectionProvider
{
    @Override
    public void storeBlock(final UserConnection userConnection, final int n, final int n2, final int n3, final int n4) {
        ((BlockConnectionStorage)userConnection.get(BlockConnectionStorage.class)).store(n, n2, n3, n4);
    }
    
    @Override
    public void removeBlock(final UserConnection userConnection, final int n, final int n2, final int n3) {
        ((BlockConnectionStorage)userConnection.get(BlockConnectionStorage.class)).remove(n, n2, n3);
    }
    
    @Override
    public int getBlockData(final UserConnection userConnection, final int n, final int n2, final int n3) {
        return ((BlockConnectionStorage)userConnection.get(BlockConnectionStorage.class)).get(n, n2, n3);
    }
    
    @Override
    public void clearStorage(final UserConnection userConnection) {
        ((BlockConnectionStorage)userConnection.get(BlockConnectionStorage.class)).clear();
    }
    
    @Override
    public void unloadChunk(final UserConnection userConnection, final int n, final int n2) {
        ((BlockConnectionStorage)userConnection.get(BlockConnectionStorage.class)).unloadChunk(n, n2);
    }
    
    @Override
    public boolean storesBlocks() {
        return true;
    }
}
