package org.lwjgl.opencl;

import org.lwjgl.*;

class CLObjectRegistry
{
    private FastLongMap registry;
    
    final boolean isEmpty() {
        return this.registry == null || this.registry.isEmpty();
    }
    
    final CLObjectChild getObject(final long n) {
        return (this.registry == null) ? null : ((CLObjectChild)this.registry.get(n));
    }
    
    final boolean hasObject(final long n) {
        return this.registry != null && this.registry.containsKey(n);
    }
    
    final Iterable getAll() {
        return this.registry;
    }
    
    void registerObject(final CLObjectChild clObjectChild) {
        final FastLongMap map = this.getMap();
        final Long value = clObjectChild.getPointer();
        if (LWJGLUtil.DEBUG && map.containsKey(value)) {
            throw new IllegalStateException("Duplicate object found: " + clObjectChild.getClass() + " - " + value);
        }
        this.getMap().put(clObjectChild.getPointer(), clObjectChild);
    }
    
    void unregisterObject(final CLObjectChild clObjectChild) {
        this.getMap().remove(clObjectChild.getPointerUnsafe());
    }
    
    private FastLongMap getMap() {
        if (this.registry == null) {
            this.registry = new FastLongMap();
        }
        return this.registry;
    }
}
