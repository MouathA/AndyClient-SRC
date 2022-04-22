package io.netty.handler.codec.http.multipart;

import io.netty.util.*;
import java.nio.charset.*;
import io.netty.buffer.*;
import java.util.*;

final class InternalAttribute extends AbstractReferenceCounted implements InterfaceHttpData
{
    private final List value;
    private final Charset charset;
    private int size;
    
    InternalAttribute(final Charset charset) {
        this.value = new ArrayList();
        this.charset = charset;
    }
    
    @Override
    public HttpDataType getHttpDataType() {
        return HttpDataType.InternalAttribute;
    }
    
    public void addValue(final String s) {
        if (s == null) {
            throw new NullPointerException("value");
        }
        final ByteBuf copiedBuffer = Unpooled.copiedBuffer(s, this.charset);
        this.value.add(copiedBuffer);
        this.size += copiedBuffer.readableBytes();
    }
    
    public void addValue(final String s, final int n) {
        if (s == null) {
            throw new NullPointerException("value");
        }
        final ByteBuf copiedBuffer = Unpooled.copiedBuffer(s, this.charset);
        this.value.add(n, copiedBuffer);
        this.size += copiedBuffer.readableBytes();
    }
    
    public void setValue(final String s, final int n) {
        if (s == null) {
            throw new NullPointerException("value");
        }
        final ByteBuf copiedBuffer = Unpooled.copiedBuffer(s, this.charset);
        final ByteBuf byteBuf = this.value.set(n, copiedBuffer);
        if (byteBuf != null) {
            this.size -= byteBuf.readableBytes();
            byteBuf.release();
        }
        this.size += copiedBuffer.readableBytes();
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Attribute && this.getName().equalsIgnoreCase(((Attribute)o).getName());
    }
    
    public int compareTo(final InterfaceHttpData interfaceHttpData) {
        if (!(interfaceHttpData instanceof InternalAttribute)) {
            throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + interfaceHttpData.getHttpDataType());
        }
        return this.compareTo((InternalAttribute)interfaceHttpData);
    }
    
    public int compareTo(final InternalAttribute internalAttribute) {
        return this.getName().compareToIgnoreCase(internalAttribute.getName());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<ByteBuf> iterator = this.value.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().toString(this.charset));
        }
        return sb.toString();
    }
    
    public int size() {
        return this.size;
    }
    
    public ByteBuf toByteBuf() {
        return Unpooled.compositeBuffer().addComponents(this.value).writerIndex(this.size()).readerIndex(0);
    }
    
    @Override
    public String getName() {
        return "InternalAttribute";
    }
    
    @Override
    protected void deallocate() {
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((InterfaceHttpData)o);
    }
}
