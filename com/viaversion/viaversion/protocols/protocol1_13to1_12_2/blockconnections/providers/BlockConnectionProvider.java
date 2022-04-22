package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.*;

public class BlockConnectionProvider implements Provider
{
    public int getBlockData(final UserConnection userConnection, final int n, final int n2, final int n3) {
        return Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(this.getWorldBlockData(userConnection, n, n2, n3));
    }
    
    public int getWorldBlockData(final UserConnection userConnection, final int n, final int n2, final int n3) {
        return -1;
    }
    
    public void storeBlock(final UserConnection userConnection, final int n, final int n2, final int n3, final int n4) {
    }
    
    public void removeBlock(final UserConnection userConnection, final int n, final int n2, final int n3) {
    }
    
    public void clearStorage(final UserConnection userConnection) {
    }
    
    public void unloadChunk(final UserConnection userConnection, final int n, final int n2) {
    }
    
    public boolean storesBlocks() {
        return false;
    }
}
