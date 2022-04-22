package io.netty.handler.codec.http.multipart;

import java.util.regex.*;
import java.nio.charset.*;
import io.netty.handler.codec.http.*;
import io.netty.util.*;
import io.netty.buffer.*;

public abstract class AbstractHttpData extends AbstractReferenceCounted implements HttpData
{
    private static final Pattern STRIP_PATTERN;
    private static final Pattern REPLACE_PATTERN;
    protected final String name;
    protected long definedSize;
    protected long size;
    protected Charset charset;
    protected boolean completed;
    
    protected AbstractHttpData(String name, final Charset charset, final long definedSize) {
        this.charset = HttpConstants.DEFAULT_CHARSET;
        if (name == null) {
            throw new NullPointerException("name");
        }
        name = AbstractHttpData.REPLACE_PATTERN.matcher(name).replaceAll(" ");
        name = AbstractHttpData.STRIP_PATTERN.matcher(name).replaceAll("");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        this.name = name;
        if (charset != null) {
            this.setCharset(charset);
        }
        this.definedSize = definedSize;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean isCompleted() {
        return this.completed;
    }
    
    @Override
    public Charset getCharset() {
        return this.charset;
    }
    
    @Override
    public void setCharset(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }
    
    @Override
    public long length() {
        return this.size;
    }
    
    @Override
    public ByteBuf content() {
        return this.getByteBuf();
    }
    
    @Override
    protected void deallocate() {
        this.delete();
    }
    
    @Override
    public HttpData retain() {
        super.retain();
        return this;
    }
    
    @Override
    public HttpData retain(final int n) {
        super.retain(n);
        return this;
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
    
    static {
        STRIP_PATTERN = Pattern.compile("(?:^\\s+|\\s+$|\\n)");
        REPLACE_PATTERN = Pattern.compile("[\\r\\t]");
    }
}
