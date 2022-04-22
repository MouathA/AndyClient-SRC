package io.netty.handler.codec.compression;

import com.jcraft.jzlib.*;

final class ZlibUtil
{
    static void fail(final Inflater inflater, final String s, final int n) {
        throw inflaterException(inflater, s, n);
    }
    
    static void fail(final Deflater deflater, final String s, final int n) {
        throw deflaterException(deflater, s, n);
    }
    
    static DecompressionException inflaterException(final Inflater inflater, final String s, final int n) {
        return new DecompressionException(s + " (" + n + ')' + ((inflater.msg != null) ? (": " + inflater.msg) : ""));
    }
    
    static CompressionException deflaterException(final Deflater deflater, final String s, final int n) {
        return new CompressionException(s + " (" + n + ')' + ((deflater.msg != null) ? (": " + deflater.msg) : ""));
    }
    
    static JZlib.WrapperType convertWrapperType(final ZlibWrapper zlibWrapper) {
        JZlib.WrapperType wrapperType = null;
        switch (zlibWrapper) {
            case NONE: {
                wrapperType = JZlib.W_NONE;
                break;
            }
            case ZLIB: {
                wrapperType = JZlib.W_ZLIB;
                break;
            }
            case GZIP: {
                wrapperType = JZlib.W_GZIP;
                break;
            }
            case ZLIB_OR_NONE: {
                wrapperType = JZlib.W_ANY;
                break;
            }
            default: {
                throw new Error();
            }
        }
        return wrapperType;
    }
    
    static int wrapperOverhead(final ZlibWrapper zlibWrapper) {
        switch (zlibWrapper) {
            case NONE: {
                break;
            }
            case ZLIB:
            case ZLIB_OR_NONE: {
                break;
            }
            case GZIP: {
                break;
            }
            default: {
                throw new Error();
            }
        }
        return 10;
    }
    
    private ZlibUtil() {
    }
}
