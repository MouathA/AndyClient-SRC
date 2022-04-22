package org.lwjgl.openal;

import java.util.*;

public final class ALCdevice
{
    final long device;
    private boolean valid;
    private final HashMap contexts;
    
    ALCdevice(final long device) {
        this.contexts = new HashMap();
        this.device = device;
        this.valid = true;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ALCdevice) {
            return ((ALCdevice)o).device == this.device;
        }
        return super.equals(o);
    }
    
    void addContext(final ALCcontext alCcontext) {
        // monitorenter(contexts = this.contexts)
        this.contexts.put(alCcontext.context, alCcontext);
    }
    // monitorexit(contexts)
    
    void removeContext(final ALCcontext alCcontext) {
        // monitorenter(contexts = this.contexts)
        this.contexts.remove(alCcontext.context);
    }
    // monitorexit(contexts)
    
    void setInvalid() {
        this.valid = false;
        // monitorenter(contexts = this.contexts)
        final Iterator<ALCcontext> iterator = this.contexts.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setInvalid();
        }
        // monitorexit(contexts)
        this.contexts.clear();
    }
    
    public boolean isValid() {
        return this.valid;
    }
}
