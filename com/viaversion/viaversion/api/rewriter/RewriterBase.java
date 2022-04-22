package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.*;

public abstract class RewriterBase implements Rewriter
{
    protected final Protocol protocol;
    
    protected RewriterBase(final Protocol protocol) {
        this.protocol = protocol;
    }
    
    @Override
    public final void register() {
        this.registerPackets();
        this.registerRewrites();
    }
    
    protected void registerPackets() {
    }
    
    protected void registerRewrites() {
    }
    
    @Override
    public Protocol protocol() {
        return this.protocol;
    }
}
