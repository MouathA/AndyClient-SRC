package io.netty.util.concurrent;

import java.util.*;

final class DefaultFutureListeners
{
    private GenericFutureListener[] listeners;
    private int size;
    private int progressiveSize;
    
    DefaultFutureListeners(final GenericFutureListener genericFutureListener, final GenericFutureListener genericFutureListener2) {
        (this.listeners = new GenericFutureListener[2])[0] = genericFutureListener;
        this.listeners[1] = genericFutureListener2;
        this.size = 2;
        if (genericFutureListener instanceof GenericProgressiveFutureListener) {
            ++this.progressiveSize;
        }
        if (genericFutureListener2 instanceof GenericProgressiveFutureListener) {
            ++this.progressiveSize;
        }
    }
    
    public void add(final GenericFutureListener genericFutureListener) {
        GenericFutureListener[] listeners = this.listeners;
        final int size = this.size;
        if (size == listeners.length) {
            listeners = (this.listeners = Arrays.copyOf(listeners, size << 1));
        }
        listeners[size] = genericFutureListener;
        this.size = size + 1;
        if (genericFutureListener instanceof GenericProgressiveFutureListener) {
            ++this.progressiveSize;
        }
    }
    
    public void remove(final GenericFutureListener genericFutureListener) {
        final GenericFutureListener[] listeners = this.listeners;
        int size = this.size;
        while (0 < size) {
            if (listeners[0] == genericFutureListener) {
                final int n = size - 0 - 1;
                if (n > 0) {
                    System.arraycopy(listeners, 1, listeners, 0, n);
                }
                listeners[--size] = null;
                this.size = size;
                if (genericFutureListener instanceof GenericProgressiveFutureListener) {
                    --this.progressiveSize;
                }
                return;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public GenericFutureListener[] listeners() {
        return this.listeners;
    }
    
    public int size() {
        return this.size;
    }
    
    public int progressiveSize() {
        return this.progressiveSize;
    }
}
