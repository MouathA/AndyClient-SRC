package io.netty.handler.codec.http.multipart;

import java.nio.charset.*;
import java.io.*;
import io.netty.util.*;
import io.netty.buffer.*;

public class DiskFileUpload extends AbstractDiskHttpData implements FileUpload
{
    public static String baseDirectory;
    public static boolean deleteOnExitTemporaryFile;
    public static final String prefix = "FUp_";
    public static final String postfix = ".tmp";
    private String filename;
    private String contentType;
    private String contentTransferEncoding;
    
    public DiskFileUpload(final String s, final String filename, final String contentType, final String contentTransferEncoding, final Charset charset, final long n) {
        super(s, charset, n);
        this.setFilename(filename);
        this.setContentType(contentType);
        this.setContentTransferEncoding(contentTransferEncoding);
    }
    
    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.FileUpload;
    }
    
    @Override
    public String getFilename() {
        return this.filename;
    }
    
    @Override
    public void setFilename(final String filename) {
        if (filename == null) {
            throw new NullPointerException("filename");
        }
        this.filename = filename;
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
        if (!(interfaceHttpData instanceof FileUpload)) {
            throw new ClassCastException("Cannot compare " + this.getHttpDataType() + " with " + interfaceHttpData.getHttpDataType());
        }
        return this.compareTo((FileUpload)interfaceHttpData);
    }
    
    public int compareTo(final FileUpload fileUpload) {
        final int compareToIgnoreCase = this.getName().compareToIgnoreCase(fileUpload.getName());
        if (compareToIgnoreCase != 0) {
            return compareToIgnoreCase;
        }
        return compareToIgnoreCase;
    }
    
    @Override
    public void setContentType(final String contentType) {
        if (contentType == null) {
            throw new NullPointerException("contentType");
        }
        this.contentType = contentType;
    }
    
    @Override
    public String getContentType() {
        return this.contentType;
    }
    
    @Override
    public String getContentTransferEncoding() {
        return this.contentTransferEncoding;
    }
    
    @Override
    public void setContentTransferEncoding(final String contentTransferEncoding) {
        this.contentTransferEncoding = contentTransferEncoding;
    }
    
    @Override
    public String toString() {
        return "Content-Disposition: form-data; name=\"" + this.getName() + "\"; " + "filename" + "=\"" + this.filename + "\"\r\n" + "Content-Type" + ": " + this.contentType + ((this.charset != null) ? ("; charset=" + this.charset + "\r\n") : "\r\n") + "Content-Length" + ": " + this.length() + "\r\n" + "Completed: " + this.isCompleted() + "\r\nIsInMemory: " + this.isInMemory() + "\r\nRealFile: " + ((this.file != null) ? this.file.getAbsolutePath() : "null") + " DefaultDeleteAfter: " + DiskFileUpload.deleteOnExitTemporaryFile;
    }
    
    @Override
    protected boolean deleteOnExit() {
        return DiskFileUpload.deleteOnExitTemporaryFile;
    }
    
    @Override
    protected String getBaseDirectory() {
        return DiskFileUpload.baseDirectory;
    }
    
    @Override
    protected String getDiskFilename() {
        return new File(this.filename).getName();
    }
    
    @Override
    protected String getPostfix() {
        return ".tmp";
    }
    
    @Override
    protected String getPrefix() {
        return "FUp_";
    }
    
    @Override
    public FileUpload copy() {
        final DiskFileUpload diskFileUpload = new DiskFileUpload(this.getName(), this.getFilename(), this.getContentType(), this.getContentTransferEncoding(), this.getCharset(), this.size);
        final ByteBuf content = this.content();
        if (content != null) {
            diskFileUpload.setContent(content.copy());
        }
        return diskFileUpload;
    }
    
    @Override
    public FileUpload duplicate() {
        final DiskFileUpload diskFileUpload = new DiskFileUpload(this.getName(), this.getFilename(), this.getContentType(), this.getContentTransferEncoding(), this.getCharset(), this.size);
        final ByteBuf content = this.content();
        if (content != null) {
            diskFileUpload.setContent(content.duplicate());
        }
        return diskFileUpload;
    }
    
    @Override
    public FileUpload retain(final int n) {
        super.retain(n);
        return this;
    }
    
    @Override
    public FileUpload retain() {
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
        DiskFileUpload.deleteOnExitTemporaryFile = true;
    }
}
