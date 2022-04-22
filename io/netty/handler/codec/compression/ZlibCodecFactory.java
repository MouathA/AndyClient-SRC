package io.netty.handler.codec.compression;

import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;

public final class ZlibCodecFactory
{
    private static final InternalLogger logger;
    private static final boolean noJdkZlibDecoder;
    
    public static ZlibEncoder newZlibEncoder(final int n) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(n);
        }
        return new JdkZlibEncoder(n);
    }
    
    public static ZlibEncoder newZlibEncoder(final ZlibWrapper zlibWrapper) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(zlibWrapper);
        }
        return new JdkZlibEncoder(zlibWrapper);
    }
    
    public static ZlibEncoder newZlibEncoder(final ZlibWrapper zlibWrapper, final int n) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(zlibWrapper, n);
        }
        return new JdkZlibEncoder(zlibWrapper, n);
    }
    
    public static ZlibEncoder newZlibEncoder(final ZlibWrapper zlibWrapper, final int n, final int n2, final int n3) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(zlibWrapper, n, n2, n3);
        }
        return new JdkZlibEncoder(zlibWrapper, n);
    }
    
    public static ZlibEncoder newZlibEncoder(final byte[] array) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(array);
        }
        return new JdkZlibEncoder(array);
    }
    
    public static ZlibEncoder newZlibEncoder(final int n, final byte[] array) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(n, array);
        }
        return new JdkZlibEncoder(n, array);
    }
    
    public static ZlibEncoder newZlibEncoder(final int n, final int n2, final int n3, final byte[] array) {
        if (PlatformDependent.javaVersion() < 7) {
            return new JZlibEncoder(n, n2, n3, array);
        }
        return new JdkZlibEncoder(n, array);
    }
    
    public static ZlibDecoder newZlibDecoder() {
        if (PlatformDependent.javaVersion() < 7 || ZlibCodecFactory.noJdkZlibDecoder) {
            return new JZlibDecoder();
        }
        return new JdkZlibDecoder();
    }
    
    public static ZlibDecoder newZlibDecoder(final ZlibWrapper zlibWrapper) {
        if (PlatformDependent.javaVersion() < 7 || ZlibCodecFactory.noJdkZlibDecoder) {
            return new JZlibDecoder(zlibWrapper);
        }
        return new JdkZlibDecoder(zlibWrapper);
    }
    
    public static ZlibDecoder newZlibDecoder(final byte[] array) {
        if (PlatformDependent.javaVersion() < 7 || ZlibCodecFactory.noJdkZlibDecoder) {
            return new JZlibDecoder(array);
        }
        return new JdkZlibDecoder(array);
    }
    
    private ZlibCodecFactory() {
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
        noJdkZlibDecoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibDecoder", true);
        ZlibCodecFactory.logger.debug("-Dio.netty.noJdkZlibDecoder: {}", (Object)ZlibCodecFactory.noJdkZlibDecoder);
    }
}
