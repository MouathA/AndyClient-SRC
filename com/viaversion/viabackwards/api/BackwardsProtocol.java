package com.viaversion.viabackwards.api;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viabackwards.api.rewriters.*;
import com.viaversion.viaversion.api.data.*;

public abstract class BackwardsProtocol extends AbstractProtocol
{
    protected BackwardsProtocol() {
    }
    
    protected BackwardsProtocol(final Class clazz, final Class clazz2, final Class clazz3, final Class clazz4) {
        super(clazz, clazz2, clazz3, clazz4);
    }
    
    protected void executeAsyncAfterLoaded(final Class clazz, final Runnable runnable) {
        Via.getManager().getProtocolManager().addMappingLoaderFuture(this.getClass(), clazz, runnable);
    }
    
    @Override
    public boolean hasMappingDataToLoad() {
        return false;
    }
    
    @Override
    public BackwardsMappings getMappingData() {
        return null;
    }
    
    public TranslatableRewriter getTranslatableRewriter() {
        return null;
    }
    
    @Override
    public MappingData getMappingData() {
        return this.getMappingData();
    }
}
