package com.viaversion.viaversion.api.platform.providers;

import java.util.*;

public class ViaProviders
{
    private final Map providers;
    private final List lonelyProviders;
    
    public ViaProviders() {
        this.providers = new HashMap();
        this.lonelyProviders = new ArrayList();
    }
    
    public void require(final Class clazz) {
        this.lonelyProviders.add(clazz);
    }
    
    public void register(final Class clazz, final Provider provider) {
        this.providers.put(clazz, provider);
    }
    
    public void use(final Class clazz, final Provider provider) {
        this.lonelyProviders.remove(clazz);
        this.providers.put(clazz, provider);
    }
    
    public Provider get(final Class clazz) {
        final Provider provider = this.providers.get(clazz);
        if (provider != null) {
            return provider;
        }
        if (this.lonelyProviders.contains(clazz)) {
            throw new IllegalStateException("There was no provider for " + clazz + ", one is required!");
        }
        return null;
    }
}
