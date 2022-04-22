package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data;

import java.util.*;

public final class BlockEntityIds
{
    private static final int[] IDS;
    
    public static int mappedId(final int n) {
        if (n < 0 || n > BlockEntityIds.IDS.length) {
            return -1;
        }
        return BlockEntityIds.IDS[n];
    }
    
    static {
        final int[] ids = com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds.getIds();
        Arrays.fill(IDS = new int[Arrays.stream(ids).max().getAsInt() + 1], -1);
        for (int i = 0; i < ids.length; ++i) {
            final int n = ids[i];
            if (n != -1) {
                BlockEntityIds.IDS[n] = i;
            }
        }
    }
}
