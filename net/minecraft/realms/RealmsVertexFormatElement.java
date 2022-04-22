package net.minecraft.realms;

public class RealmsVertexFormatElement
{
    private bmv v;
    
    public RealmsVertexFormatElement(final bmv v) {
        this.v = v;
    }
    
    public bmv getVertexFormatElement() {
        return this.v;
    }
    
    public boolean isPosition() {
        return this.v.f();
    }
    
    public int getIndex() {
        return this.v.d();
    }
    
    public int getByteSize() {
        return this.v.e();
    }
    
    public int getCount() {
        return this.v.c();
    }
    
    @Override
    public int hashCode() {
        return this.v.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.v.equals(o);
    }
    
    @Override
    public String toString() {
        return this.v.toString();
    }
}
