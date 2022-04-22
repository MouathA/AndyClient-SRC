package com.viaversion.viaversion.protocols.protocol1_18to1_17_1;

import com.viaversion.viaversion.api.*;
import java.util.*;

public final class BlockEntityIds
{
    public static int newId(final int n) {
        final int n2;
        if (n < 0 || n > BlockEntityIds.IDS.length || (n2 = BlockEntityIds.IDS[n]) == -1) {
            Via.getPlatform().getLogger().warning("Received out of bounds block entity id: " + n);
            return -1;
        }
        return n2;
    }
    
    public static int[] getIds() {
        return BlockEntityIds.IDS;
    }
    
    static {
        Arrays.fill(BlockEntityIds.IDS = new int[14], -1);
    }
}
