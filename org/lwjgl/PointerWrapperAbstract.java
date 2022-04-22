package org.lwjgl;

public abstract class PointerWrapperAbstract implements PointerWrapper
{
    protected final long pointer;
    
    protected PointerWrapperAbstract(final long pointer) {
        this.pointer = pointer;
    }
    
    public final void checkValid() {
        if (LWJGLUtil.DEBUG && this != 0) {
            throw new IllegalStateException("This " + this.getClass().getSimpleName() + " pointer is not valid.");
        }
    }
    
    @Override
    public final long getPointer() {
        this.checkValid();
        return this.pointer;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof PointerWrapperAbstract && this.pointer == ((PointerWrapperAbstract)o).pointer);
    }
    
    @Override
    public int hashCode() {
        return (int)(this.pointer ^ this.pointer >>> 32);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " pointer (0x" + Long.toHexString(this.pointer).toUpperCase() + ")";
    }
}
