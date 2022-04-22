package io.netty.handler.codec.http.multipart;

import java.nio.charset.*;
import java.io.*;
import io.netty.util.*;
import io.netty.buffer.*;

public class MixedAttribute implements Attribute
{
    private Attribute attribute;
    private final long limitSize;
    
    public MixedAttribute(final String s, final long limitSize) {
        this.limitSize = limitSize;
        this.attribute = new MemoryAttribute(s);
    }
    
    public MixedAttribute(final String s, final String s2, final long limitSize) {
        this.limitSize = limitSize;
        if (s2.length() > this.limitSize) {
            this.attribute = new DiskAttribute(s, s2);
        }
        else {
            this.attribute = new MemoryAttribute(s, s2);
        }
    }
    
    @Override
    public void addContent(final ByteBuf byteBuf, final boolean b) throws IOException {
        if (this.attribute instanceof MemoryAttribute && this.attribute.length() + byteBuf.readableBytes() > this.limitSize) {
            final DiskAttribute attribute = new DiskAttribute(this.attribute.getName());
            if (((MemoryAttribute)this.attribute).getByteBuf() != null) {
                attribute.addContent(((MemoryAttribute)this.attribute).getByteBuf(), false);
            }
            this.attribute = attribute;
        }
        this.attribute.addContent(byteBuf, b);
    }
    
    @Override
    public void delete() {
        this.attribute.delete();
    }
    
    @Override
    public byte[] get() throws IOException {
        return this.attribute.get();
    }
    
    @Override
    public ByteBuf getByteBuf() throws IOException {
        return this.attribute.getByteBuf();
    }
    
    @Override
    public Charset getCharset() {
        return this.attribute.getCharset();
    }
    
    @Override
    public String getString() throws IOException {
        return this.attribute.getString();
    }
    
    @Override
    public String getString(final Charset charset) throws IOException {
        return this.attribute.getString(charset);
    }
    
    @Override
    public boolean isCompleted() {
        return this.attribute.isCompleted();
    }
    
    @Override
    public boolean isInMemory() {
        return this.attribute.isInMemory();
    }
    
    @Override
    public long length() {
        return this.attribute.length();
    }
    
    @Override
    public boolean renameTo(final File file) throws IOException {
        return this.attribute.renameTo(file);
    }
    
    @Override
    public void setCharset(final Charset charset) {
        this.attribute.setCharset(charset);
    }
    
    @Override
    public void setContent(final ByteBuf content) throws IOException {
        if (content.readableBytes() > this.limitSize && this.attribute instanceof MemoryAttribute) {
            this.attribute = new DiskAttribute(this.attribute.getName());
        }
        this.attribute.setContent(content);
    }
    
    @Override
    public void setContent(final File content) throws IOException {
        if (content.length() > this.limitSize && this.attribute instanceof MemoryAttribute) {
            this.attribute = new DiskAttribute(this.attribute.getName());
        }
        this.attribute.setContent(content);
    }
    
    @Override
    public void setContent(final InputStream content) throws IOException {
        if (this.attribute instanceof MemoryAttribute) {
            this.attribute = new DiskAttribute(this.attribute.getName());
        }
        this.attribute.setContent(content);
    }
    
    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return this.attribute.getHttpDataType();
    }
    
    @Override
    public String getName() {
        return this.attribute.getName();
    }
    
    public int compareTo(final InterfaceHttpData interfaceHttpData) {
        return this.attribute.compareTo(interfaceHttpData);
    }
    
    @Override
    public String toString() {
        return "Mixed: " + this.attribute.toString();
    }
    
    @Override
    public String getValue() throws IOException {
        return this.attribute.getValue();
    }
    
    @Override
    public void setValue(final String value) throws IOException {
        this.attribute.setValue(value);
    }
    
    @Override
    public ByteBuf getChunk(final int n) throws IOException {
        return this.attribute.getChunk(n);
    }
    
    @Override
    public File getFile() throws IOException {
        return this.attribute.getFile();
    }
    
    @Override
    public Attribute copy() {
        return this.attribute.copy();
    }
    
    @Override
    public Attribute duplicate() {
        return this.attribute.duplicate();
    }
    
    @Override
    public ByteBuf content() {
        return this.attribute.content();
    }
    
    @Override
    public int refCnt() {
        return this.attribute.refCnt();
    }
    
    @Override
    public Attribute retain() {
        this.attribute.retain();
        return this;
    }
    
    @Override
    public Attribute retain(final int n) {
        this.attribute.retain(n);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.attribute.release();
    }
    
    @Override
    public boolean release(final int n) {
        return this.attribute.release(n);
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
}
