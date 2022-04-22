package io.netty.handler.codec.http.multipart;

import java.nio.charset.*;
import java.io.*;
import io.netty.util.*;
import io.netty.buffer.*;

public class MixedFileUpload implements FileUpload
{
    private FileUpload fileUpload;
    private final long limitSize;
    private final long definedSize;
    
    public MixedFileUpload(final String s, final String s2, final String s3, final String s4, final Charset charset, final long definedSize, final long limitSize) {
        this.limitSize = limitSize;
        if (definedSize > this.limitSize) {
            this.fileUpload = new DiskFileUpload(s, s2, s3, s4, charset, definedSize);
        }
        else {
            this.fileUpload = new MemoryFileUpload(s, s2, s3, s4, charset, definedSize);
        }
        this.definedSize = definedSize;
    }
    
    @Override
    public void addContent(final ByteBuf byteBuf, final boolean b) throws IOException {
        if (this.fileUpload instanceof MemoryFileUpload && this.fileUpload.length() + byteBuf.readableBytes() > this.limitSize) {
            final DiskFileUpload fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
            final ByteBuf byteBuf2 = this.fileUpload.getByteBuf();
            if (byteBuf2 != null && byteBuf2.isReadable()) {
                fileUpload.addContent(byteBuf2.retain(), false);
            }
            this.fileUpload.release();
            this.fileUpload = fileUpload;
        }
        this.fileUpload.addContent(byteBuf, b);
    }
    
    @Override
    public void delete() {
        this.fileUpload.delete();
    }
    
    @Override
    public byte[] get() throws IOException {
        return this.fileUpload.get();
    }
    
    @Override
    public ByteBuf getByteBuf() throws IOException {
        return this.fileUpload.getByteBuf();
    }
    
    @Override
    public Charset getCharset() {
        return this.fileUpload.getCharset();
    }
    
    @Override
    public String getContentType() {
        return this.fileUpload.getContentType();
    }
    
    @Override
    public String getContentTransferEncoding() {
        return this.fileUpload.getContentTransferEncoding();
    }
    
    @Override
    public String getFilename() {
        return this.fileUpload.getFilename();
    }
    
    @Override
    public String getString() throws IOException {
        return this.fileUpload.getString();
    }
    
    @Override
    public String getString(final Charset charset) throws IOException {
        return this.fileUpload.getString(charset);
    }
    
    @Override
    public boolean isCompleted() {
        return this.fileUpload.isCompleted();
    }
    
    @Override
    public boolean isInMemory() {
        return this.fileUpload.isInMemory();
    }
    
    @Override
    public long length() {
        return this.fileUpload.length();
    }
    
    @Override
    public boolean renameTo(final File file) throws IOException {
        return this.fileUpload.renameTo(file);
    }
    
    @Override
    public void setCharset(final Charset charset) {
        this.fileUpload.setCharset(charset);
    }
    
    @Override
    public void setContent(final ByteBuf content) throws IOException {
        if (content.readableBytes() > this.limitSize && this.fileUpload instanceof MemoryFileUpload) {
            final FileUpload fileUpload = this.fileUpload;
            this.fileUpload = new DiskFileUpload(fileUpload.getName(), fileUpload.getFilename(), fileUpload.getContentType(), fileUpload.getContentTransferEncoding(), fileUpload.getCharset(), this.definedSize);
            fileUpload.release();
        }
        this.fileUpload.setContent(content);
    }
    
    @Override
    public void setContent(final File content) throws IOException {
        if (content.length() > this.limitSize && this.fileUpload instanceof MemoryFileUpload) {
            final FileUpload fileUpload = this.fileUpload;
            this.fileUpload = new DiskFileUpload(fileUpload.getName(), fileUpload.getFilename(), fileUpload.getContentType(), fileUpload.getContentTransferEncoding(), fileUpload.getCharset(), this.definedSize);
            fileUpload.release();
        }
        this.fileUpload.setContent(content);
    }
    
    @Override
    public void setContent(final InputStream content) throws IOException {
        if (this.fileUpload instanceof MemoryFileUpload) {
            final FileUpload fileUpload = this.fileUpload;
            this.fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
            fileUpload.release();
        }
        this.fileUpload.setContent(content);
    }
    
    @Override
    public void setContentType(final String contentType) {
        this.fileUpload.setContentType(contentType);
    }
    
    @Override
    public void setContentTransferEncoding(final String contentTransferEncoding) {
        this.fileUpload.setContentTransferEncoding(contentTransferEncoding);
    }
    
    @Override
    public void setFilename(final String filename) {
        this.fileUpload.setFilename(filename);
    }
    
    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return this.fileUpload.getHttpDataType();
    }
    
    @Override
    public String getName() {
        return this.fileUpload.getName();
    }
    
    public int compareTo(final InterfaceHttpData interfaceHttpData) {
        return this.fileUpload.compareTo(interfaceHttpData);
    }
    
    @Override
    public String toString() {
        return "Mixed: " + this.fileUpload.toString();
    }
    
    @Override
    public ByteBuf getChunk(final int n) throws IOException {
        return this.fileUpload.getChunk(n);
    }
    
    @Override
    public File getFile() throws IOException {
        return this.fileUpload.getFile();
    }
    
    @Override
    public FileUpload copy() {
        return this.fileUpload.copy();
    }
    
    @Override
    public FileUpload duplicate() {
        return this.fileUpload.duplicate();
    }
    
    @Override
    public ByteBuf content() {
        return this.fileUpload.content();
    }
    
    @Override
    public int refCnt() {
        return this.fileUpload.refCnt();
    }
    
    @Override
    public FileUpload retain() {
        this.fileUpload.retain();
        return this;
    }
    
    @Override
    public FileUpload retain(final int n) {
        this.fileUpload.retain(n);
        return this;
    }
    
    @Override
    public boolean release() {
        return this.fileUpload.release();
    }
    
    @Override
    public boolean release(final int n) {
        return this.fileUpload.release(n);
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
