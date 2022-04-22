package org.lwjgl.opencl;

abstract class CLObjectRetainable extends CLObject
{
    private int refCount;
    
    protected CLObjectRetainable(final long n) {
        super(n);
        if (super.isValid()) {
            this.refCount = 1;
        }
    }
    
    public final int getReferenceCount() {
        return this.refCount;
    }
    
    public final boolean isValid() {
        return this.refCount > 0;
    }
    
    int retain() {
        this.checkValid();
        return ++this.refCount;
    }
    
    int release() {
        this.checkValid();
        return --this.refCount;
    }
}
