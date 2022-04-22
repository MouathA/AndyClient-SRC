package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import java.util.*;
import com.viaversion.viaversion.api.connection.*;
import com.google.common.collect.*;

public class ClientChunks extends StoredObject
{
    private final Set loadedChunks;
    
    public ClientChunks(final UserConnection userConnection) {
        super(userConnection);
        this.loadedChunks = Sets.newConcurrentHashSet();
    }
    
    public static long toLong(final int n, final int n2) {
        return ((long)n << 32) + n2 + 2147483648L;
    }
    
    public Set getLoadedChunks() {
        return this.loadedChunks;
    }
}
