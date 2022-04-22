package net.minecraft.realms;

import java.util.*;

public class RealmsVertexFormat
{
    private bmu v;
    
    public RealmsVertexFormat(final bmu v) {
        this.v = v;
    }
    
    public RealmsVertexFormat from(final bmu v) {
        this.v = v;
        return this;
    }
    
    public bmu getVertexFormat() {
        return this.v;
    }
    
    public void clear() {
        this.v.a();
    }
    
    public int getUvOffset(final int n) {
        return this.v.b(n);
    }
    
    public int getElementCount() {
        return this.v.i();
    }
    
    public boolean hasColor() {
        return this.v.d();
    }
    
    public boolean hasUv(final int n) {
        return this.v.a(n);
    }
    
    public RealmsVertexFormatElement getElement(final int n) {
        return new RealmsVertexFormatElement(this.v.c(n));
    }
    
    public RealmsVertexFormat addElement(final RealmsVertexFormatElement realmsVertexFormatElement) {
        return this.from(this.v.a(realmsVertexFormatElement.getVertexFormatElement()));
    }
    
    public int getColorOffset() {
        return this.v.e();
    }
    
    public List getElements() {
        final ArrayList<RealmsVertexFormatElement> list = new ArrayList<RealmsVertexFormatElement>();
        final Iterator<bmv> iterator = this.v.h().iterator();
        while (iterator.hasNext()) {
            list.add(new RealmsVertexFormatElement(iterator.next()));
        }
        return list;
    }
    
    public boolean hasNormal() {
        return this.v.b();
    }
    
    public int getVertexSize() {
        return this.v.g();
    }
    
    public int getOffset(final int n) {
        return this.v.d(n);
    }
    
    public int getNormalOffset() {
        return this.v.c();
    }
    
    public int getIntegerSize() {
        return this.v.f();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.v.equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.v.hashCode();
    }
    
    @Override
    public String toString() {
        return this.v.toString();
    }
}
