package com.viaversion.viaversion.libs.javassist.bytecode;

final class LongVector
{
    static final int ASIZE = 128;
    static final int ABITS = 7;
    static final int VSIZE = 8;
    private ConstInfo[][] objects;
    private int elements;
    
    public LongVector() {
        this.objects = new ConstInfo[8][];
        this.elements = 0;
    }
    
    public LongVector(final int n) {
        this.objects = new ConstInfo[(n >> 7 & 0xFFFFFFF8) + 8][];
        this.elements = 0;
    }
    
    public int size() {
        return this.elements;
    }
    
    public int capacity() {
        return this.objects.length * 128;
    }
    
    public ConstInfo elementAt(final int n) {
        if (n < 0 || this.elements <= n) {
            return null;
        }
        return this.objects[n >> 7][n & 0x7F];
    }
    
    public void addElement(final ConstInfo constInfo) {
        final int n = this.elements >> 7;
        final int n2 = this.elements & 0x7F;
        final int length = this.objects.length;
        if (n >= length) {
            final ConstInfo[][] objects = new ConstInfo[length + 8][];
            System.arraycopy(this.objects, 0, objects, 0, length);
            this.objects = objects;
        }
        if (this.objects[n] == null) {
            this.objects[n] = new ConstInfo[128];
        }
        this.objects[n][n2] = constInfo;
        ++this.elements;
    }
}
