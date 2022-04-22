package com.sun.jna;

public abstract class PointerType implements NativeMapped
{
    private Pointer pointer;
    static Class class$com$sun$jna$Pointer;
    
    protected PointerType() {
        this.pointer = Pointer.NULL;
    }
    
    protected PointerType(final Pointer pointer) {
        this.pointer = pointer;
    }
    
    public Class nativeType() {
        return (PointerType.class$com$sun$jna$Pointer == null) ? (PointerType.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : PointerType.class$com$sun$jna$Pointer;
    }
    
    public Object toNative() {
        return this.getPointer();
    }
    
    public Pointer getPointer() {
        return this.pointer;
    }
    
    public void setPointer(final Pointer pointer) {
        this.pointer = pointer;
    }
    
    public Object fromNative(final Object o, final FromNativeContext fromNativeContext) {
        if (o == null) {
            return null;
        }
        final PointerType pointerType = (PointerType)this.getClass().newInstance();
        pointerType.pointer = (Pointer)o;
        return pointerType;
    }
    
    public int hashCode() {
        return (this.pointer != null) ? this.pointer.hashCode() : 0;
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PointerType)) {
            return false;
        }
        final Pointer pointer = ((PointerType)o).getPointer();
        if (this.pointer == null) {
            return pointer == null;
        }
        return this.pointer.equals(pointer);
    }
    
    public String toString() {
        return (this.pointer == null) ? "NULL" : this.pointer.toString();
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
}
