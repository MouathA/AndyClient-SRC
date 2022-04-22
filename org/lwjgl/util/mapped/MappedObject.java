package org.lwjgl.util.mapped;

import java.nio.*;
import org.lwjgl.*;

public abstract class MappedObject
{
    static final boolean CHECKS;
    public long baseAddress;
    public long viewAddress;
    ByteBuffer preventGC;
    public int view;
    
    protected MappedObject() {
    }
    
    protected final long getViewAddress(final int n) {
        throw new InternalError("type not registered");
    }
    
    public final void setViewAddress(final long viewAddress) {
        if (MappedObject.CHECKS) {
            this.checkAddress(viewAddress);
        }
        this.viewAddress = viewAddress;
    }
    
    final void checkAddress(final long n) {
        final long address0 = MemoryUtil.getAddress0(this.preventGC);
        final int n2 = (int)(n - address0);
        if (n < address0 || this.preventGC.capacity() < n2 + this.getSizeof()) {
            throw new IndexOutOfBoundsException(Integer.toString(n2 / this.getSizeof()));
        }
    }
    
    final void checkRange(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        if (this.preventGC.capacity() < this.viewAddress - MemoryUtil.getAddress0(this.preventGC) + n) {
            throw new BufferOverflowException();
        }
    }
    
    public final int getAlign() {
        throw new InternalError("type not registered");
    }
    
    public final int getSizeof() {
        throw new InternalError("type not registered");
    }
    
    public final int capacity() {
        throw new InternalError("type not registered");
    }
    
    public static MappedObject map(final ByteBuffer byteBuffer) {
        throw new InternalError("type not registered");
    }
    
    public static MappedObject map(final long n, final int n2) {
        throw new InternalError("type not registered");
    }
    
    public static MappedObject malloc(final int n) {
        throw new InternalError("type not registered");
    }
    
    public final MappedObject dup() {
        throw new InternalError("type not registered");
    }
    
    public final MappedObject slice() {
        throw new InternalError("type not registered");
    }
    
    public final void runViewConstructor() {
        throw new InternalError("type not registered");
    }
    
    public final void next() {
        throw new InternalError("type not registered");
    }
    
    public final void copyTo(final MappedObject mappedObject) {
        throw new InternalError("type not registered");
    }
    
    public final void copyRange(final MappedObject mappedObject, final int n) {
        throw new InternalError("type not registered");
    }
    
    public static Iterable foreach(final MappedObject mappedObject) {
        return foreach(mappedObject, mappedObject.capacity());
    }
    
    public static Iterable foreach(final MappedObject mappedObject, final int n) {
        return new MappedForeach(mappedObject, n);
    }
    
    public final MappedObject[] asArray() {
        throw new InternalError("type not registered");
    }
    
    public final ByteBuffer backingByteBuffer() {
        return this.preventGC;
    }
    
    static {
        CHECKS = LWJGLUtil.getPrivilegedBoolean("org.lwjgl.util.mapped.Checks");
    }
}
