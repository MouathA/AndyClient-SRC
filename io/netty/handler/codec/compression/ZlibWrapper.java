package io.netty.handler.codec.compression;

public enum ZlibWrapper
{
    ZLIB("ZLIB", 0), 
    GZIP("GZIP", 1), 
    NONE("NONE", 2), 
    ZLIB_OR_NONE("ZLIB_OR_NONE", 3);
    
    private static final ZlibWrapper[] $VALUES;
    
    private ZlibWrapper(final String s, final int n) {
    }
    
    static {
        $VALUES = new ZlibWrapper[] { ZlibWrapper.ZLIB, ZlibWrapper.GZIP, ZlibWrapper.NONE, ZlibWrapper.ZLIB_OR_NONE };
    }
}
