package com.google.common.io;

import com.google.common.annotations.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.io.*;

@Beta
public final class Resources
{
    private Resources() {
    }
    
    @Deprecated
    public static InputSupplier newInputStreamSupplier(final URL url) {
        return ByteStreams.asInputSupplier(asByteSource(url));
    }
    
    public static ByteSource asByteSource(final URL url) {
        return new UrlByteSource(url, null);
    }
    
    @Deprecated
    public static InputSupplier newReaderSupplier(final URL url, final Charset charset) {
        return CharStreams.asInputSupplier(asCharSource(url, charset));
    }
    
    public static CharSource asCharSource(final URL url, final Charset charset) {
        return asByteSource(url).asCharSource(charset);
    }
    
    public static byte[] toByteArray(final URL url) throws IOException {
        return asByteSource(url).read();
    }
    
    public static String toString(final URL url, final Charset charset) throws IOException {
        return asCharSource(url, charset).read();
    }
    
    public static Object readLines(final URL url, final Charset charset, final LineProcessor lineProcessor) throws IOException {
        return CharStreams.readLines(newReaderSupplier(url, charset), lineProcessor);
    }
    
    public static List readLines(final URL url, final Charset charset) throws IOException {
        return (List)readLines(url, charset, new LineProcessor() {
            final List result = Lists.newArrayList();
            
            @Override
            public boolean processLine(final String s) {
                this.result.add(s);
                return true;
            }
            
            @Override
            public List getResult() {
                return this.result;
            }
            
            @Override
            public Object getResult() {
                return this.getResult();
            }
        });
    }
    
    public static void copy(final URL url, final OutputStream outputStream) throws IOException {
        asByteSource(url).copyTo(outputStream);
    }
    
    public static URL getResource(final String s) {
        final URL resource = ((ClassLoader)Objects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader())).getResource(s);
        Preconditions.checkArgument(resource != null, "resource %s not found.", s);
        return resource;
    }
    
    public static URL getResource(final Class clazz, final String s) {
        final URL resource = clazz.getResource(s);
        Preconditions.checkArgument(resource != null, "resource %s relative to %s not found.", s, clazz.getName());
        return resource;
    }
    
    private static final class UrlByteSource extends ByteSource
    {
        private final URL url;
        
        private UrlByteSource(final URL url) {
            this.url = (URL)Preconditions.checkNotNull(url);
        }
        
        @Override
        public InputStream openStream() throws IOException {
            return this.url.openStream();
        }
        
        @Override
        public String toString() {
            return "Resources.asByteSource(" + this.url + ")";
        }
        
        UrlByteSource(final URL url, final Resources$1 lineProcessor) {
            this(url);
        }
    }
}
