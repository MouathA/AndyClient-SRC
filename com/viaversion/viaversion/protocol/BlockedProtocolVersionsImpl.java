package com.viaversion.viaversion.protocol;

import com.viaversion.viaversion.api.protocol.version.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class BlockedProtocolVersionsImpl implements BlockedProtocolVersions
{
    private final IntSet singleBlockedVersions;
    private final int blocksBelow;
    private final int blocksAbove;
    
    public BlockedProtocolVersionsImpl(final IntSet singleBlockedVersions, final int blocksBelow, final int blocksAbove) {
        this.singleBlockedVersions = singleBlockedVersions;
        this.blocksBelow = blocksBelow;
        this.blocksAbove = blocksAbove;
    }
    
    @Override
    public boolean contains(final int n) {
        return (this.blocksBelow != -1 && n < this.blocksBelow) || (this.blocksAbove != -1 && n > this.blocksAbove) || this.singleBlockedVersions.contains(n);
    }
    
    @Override
    public int blocksBelow() {
        return this.blocksBelow;
    }
    
    @Override
    public int blocksAbove() {
        return this.blocksAbove;
    }
    
    @Override
    public IntSet singleBlockedVersions() {
        return this.singleBlockedVersions;
    }
}
