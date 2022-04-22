package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.*;
import java.io.*;
import io.netty.util.*;
import io.netty.buffer.*;

public class DiskAttribute extends AbstractDiskHttpData implements Attribute
{
    public static String baseDirectory;
    public static boolean deleteOnExitTemporaryFile;
    public static final String prefix = "Attr_";
    public static final String postfix = ".att";
    
    public DiskAttribute(final String s) {
        super(s, HttpConstants.DEFAULT_CHARSET, 0L);
    }
    
    public DiskAttribute(final String s, final String value) throws IOException {
        super(s, HttpConstants.DEFAULT_CHARSET, 0L);
        this.setValue(value);
    }
    
    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.Attribute;
    }
    
    @Override
    public String getValue() throws IOException {
        return new String(this.get(), this.charset.name());
    }
    
    @Override
    public void setValue(final String s) throws IOException {
        if (s == null) {
            throw new NullPointerException("value");
        }
        final ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(s.getBytes(this.charset.name()));
        if (this.definedSize > 0L) {
            this.definedSize = wrappedBuffer.readableBytes();
        }
        this.setContent(wrappedBuffer);
    }
    
    @Override
    public void addContent(final ByteBuf byteBuf, final boolean b) throws IOException {
        final int readableBytes = byteBuf.readableBytes();
        if (this.definedSize > 0L && this.definedSize < this.size + readableBytes) {
            this.definedSize = this.size + readableBytes;
        }
        super.addContent(byteBuf, b);
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
        if (!(interfaceHttpData instanceof Attribute)) {
            throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + interfaceHttpData.getHttpDataType());
        }
        return this.compareTo((Attribute)interfaceHttpData);
    }
    
    public int compareTo(final Attribute attribute) {
        return this.getName().compareToIgnoreCase(attribute.getName());
    }
    
    @Override
    public String toString() {
        return this.getName() + '=' + this.getValue();
    }
    
    @Override
    protected boolean deleteOnExit() {
        return DiskAttribute.deleteOnExitTemporaryFile;
    }
    
    @Override
    protected String getBaseDirectory() {
        return DiskAttribute.baseDirectory;
    }
    
    @Override
    protected String getDiskFilename() {
        return this.getName() + ".att";
    }
    
    @Override
    protected String getPostfix() {
        return ".att";
    }
    
    @Override
    protected String getPrefix() {
        return "Attr_";
    }
    
    @Override
    public Attribute copy() {
        final DiskAttribute diskAttribute = new DiskAttribute(this.getName());
        diskAttribute.setCharset(this.getCharset());
        final ByteBuf content = this.content();
        if (content != null) {
            diskAttribute.setContent(content.copy());
        }
        return diskAttribute;
    }
    
    @Override
    public Attribute duplicate() {
        final DiskAttribute diskAttribute = new DiskAttribute(this.getName());
        diskAttribute.setCharset(this.getCharset());
        final ByteBuf content = this.content();
        if (content != null) {
            diskAttribute.setContent(content.duplicate());
        }
        return diskAttribute;
    }
    
    @Override
    public Attribute retain(final int n) {
        super.retain(n);
        return this;
    }
    
    @Override
    public Attribute retain() {
        super.retain();
        return this;
    }
    
    @Override
    public HttpData retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public HttpData retain() {
        return this.retain();
    }
    
    @Override
    public HttpData duplicate() {
        return this.duplicate();
    }
    
    @Override
    public HttpData copy() {
        return this.copy();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((InterfaceHttpData)o);
    }
    
    @Override
    public ReferenceCounted retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
    
    @Override
    public ByteBufHolder retain(final int n) {
        return this.retain(n);
    }
    
    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }
    
    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }
    
    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }
    
    static {
        DiskAttribute.deleteOnExitTemporaryFile = true;
    }
}
