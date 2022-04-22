package com.google.common.util.concurrent;

import javax.annotation.*;

public final class SettableFuture extends AbstractFuture
{
    public static SettableFuture create() {
        return new SettableFuture();
    }
    
    private SettableFuture() {
    }
    
    public boolean set(@Nullable final Object o) {
        return super.set(o);
    }
    
    public boolean setException(final Throwable exception) {
        return super.setException(exception);
    }
}
