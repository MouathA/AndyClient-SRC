package org.apache.http.client.entity;

import org.apache.http.annotation.*;
import java.io.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.entity.*;

@NotThreadSafe
public class EntityBuilder
{
    private String text;
    private byte[] binary;
    private InputStream stream;
    private List parameters;
    private Serializable serializable;
    private File file;
    private ContentType contentType;
    private String contentEncoding;
    private boolean chunked;
    private boolean gzipCompress;
    
    EntityBuilder() {
    }
    
    public static EntityBuilder create() {
        return new EntityBuilder();
    }
    
    private void clearContent() {
        this.text = null;
        this.binary = null;
        this.stream = null;
        this.parameters = null;
        this.serializable = null;
        this.file = null;
    }
    
    public String getText() {
        return this.text;
    }
    
    public EntityBuilder setText(final String text) {
        this.clearContent();
        this.text = text;
        return this;
    }
    
    public byte[] getBinary() {
        return this.binary;
    }
    
    public EntityBuilder setBinary(final byte[] binary) {
        this.clearContent();
        this.binary = binary;
        return this;
    }
    
    public InputStream getStream() {
        return this.stream;
    }
    
    public EntityBuilder setStream(final InputStream stream) {
        this.clearContent();
        this.stream = stream;
        return this;
    }
    
    public List getParameters() {
        return this.parameters;
    }
    
    public EntityBuilder setParameters(final List parameters) {
        this.clearContent();
        this.parameters = parameters;
        return this;
    }
    
    public EntityBuilder setParameters(final NameValuePair... array) {
        return this.setParameters(Arrays.asList(array));
    }
    
    public Serializable getSerializable() {
        return this.serializable;
    }
    
    public EntityBuilder setSerializable(final Serializable serializable) {
        this.clearContent();
        this.serializable = serializable;
        return this;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public EntityBuilder setFile(final File file) {
        this.clearContent();
        this.file = file;
        return this;
    }
    
    public ContentType getContentType() {
        return this.contentType;
    }
    
    public EntityBuilder setContentType(final ContentType contentType) {
        this.contentType = contentType;
        return this;
    }
    
    public String getContentEncoding() {
        return this.contentEncoding;
    }
    
    public EntityBuilder setContentEncoding(final String contentEncoding) {
        this.contentEncoding = contentEncoding;
        return this;
    }
    
    public boolean isChunked() {
        return this.chunked;
    }
    
    public EntityBuilder chunked() {
        this.chunked = true;
        return this;
    }
    
    public boolean isGzipCompress() {
        return this.gzipCompress;
    }
    
    public EntityBuilder gzipCompress() {
        this.gzipCompress = true;
        return this;
    }
    
    private ContentType getContentOrDefault(final ContentType contentType) {
        return (this.contentType != null) ? this.contentType : contentType;
    }
    
    public HttpEntity build() {
        Object o;
        if (this.text != null) {
            o = new StringEntity(this.text, this.getContentOrDefault(ContentType.DEFAULT_TEXT));
        }
        else if (this.binary != null) {
            o = new ByteArrayEntity(this.binary, this.getContentOrDefault(ContentType.DEFAULT_BINARY));
        }
        else if (this.stream != null) {
            o = new InputStreamEntity(this.stream, 1L, this.getContentOrDefault(ContentType.DEFAULT_BINARY));
        }
        else if (this.parameters != null) {
            o = new UrlEncodedFormEntity(this.parameters, (this.contentType != null) ? this.contentType.getCharset() : null);
        }
        else if (this.serializable != null) {
            o = new SerializableEntity(this.serializable);
            ((AbstractHttpEntity)o).setContentType(ContentType.DEFAULT_BINARY.toString());
        }
        else if (this.file != null) {
            o = new FileEntity(this.file, this.getContentOrDefault(ContentType.DEFAULT_BINARY));
        }
        else {
            o = new BasicHttpEntity();
        }
        if (((AbstractHttpEntity)o).getContentType() != null && this.contentType != null) {
            ((AbstractHttpEntity)o).setContentType(this.contentType.toString());
        }
        ((AbstractHttpEntity)o).setContentEncoding(this.contentEncoding);
        ((AbstractHttpEntity)o).setChunked(this.chunked);
        if (this.gzipCompress) {
            return new GzipCompressingEntity((HttpEntity)o);
        }
        return (HttpEntity)o;
    }
}
