package net.minecraft.realms;

import java.nio.*;

public class RealmsBufferBuilder
{
    private bfd b;
    
    public RealmsBufferBuilder(final bfd b) {
        this.b = b;
    }
    
    public RealmsBufferBuilder from(final bfd b) {
        this.b = b;
        return this;
    }
    
    public void sortQuads(final float n, final float n2, final float n3) {
        this.b.a(n, n2, n3);
    }
    
    public void fixupQuadColor(final int n) {
        this.b.a(n);
    }
    
    public ByteBuffer getBuffer() {
        return this.b.f();
    }
    
    public void postNormal(final float n, final float n2, final float n3) {
        this.b.b(n, n2, n3);
    }
    
    public int getDrawMode() {
        return this.b.i();
    }
    
    public void offset(final double n, final double n2, final double n3) {
        this.b.c(n, n2, n3);
    }
    
    public void restoreState(final bfd.a a) {
        this.b.a(a);
    }
    
    public void endVertex() {
        this.b.d();
    }
    
    public RealmsBufferBuilder normal(final float n, final float n2, final float n3) {
        return this.from(this.b.c(n, n2, n3));
    }
    
    public void end() {
        this.b.e();
    }
    
    public void begin(final int n, final bmu bmu) {
        this.b.a(n, bmu);
    }
    
    public RealmsBufferBuilder color(final int n, final int n2, final int n3, final int n4) {
        return this.from(this.b.b(n, n2, n3, n4));
    }
    
    public void faceTex2(final int n, final int n2, final int n3, final int n4) {
        this.b.a(n, n2, n3, n4);
    }
    
    public void postProcessFacePosition(final double n, final double n2, final double n3) {
        this.b.a(n, n2, n3);
    }
    
    public void fixupVertexColor(final float n, final float n2, final float n3, final int n4) {
        this.b.b(n, n2, n3, n4);
    }
    
    public RealmsBufferBuilder color(final float n, final float n2, final float n3, final float n4) {
        return this.from(this.b.a(n, n2, n3, n4));
    }
    
    public RealmsVertexFormat getVertexFormat() {
        return new RealmsVertexFormat(this.b.g());
    }
    
    public void faceTint(final float n, final float n2, final float n3, final int n4) {
        this.b.a(n, n2, n3, n4);
    }
    
    public RealmsBufferBuilder tex2(final int n, final int n2) {
        return this.from(this.b.a(n, n2));
    }
    
    public void putBulkData(final int[] array) {
        this.b.a(array);
    }
    
    public RealmsBufferBuilder tex(final double n, final double n2) {
        return this.from(this.b.a(n, n2));
    }
    
    public int getVertexCount() {
        return this.b.h();
    }
    
    public void clear() {
        this.b.b();
    }
    
    public RealmsBufferBuilder vertex(final double n, final double n2, final double n3) {
        return this.from(this.b.b(n, n2, n3));
    }
    
    public void fixupQuadColor(final float n, final float n2, final float n3) {
        this.b.d(n, n2, n3);
    }
    
    public void noColor() {
        this.b.c();
    }
}
