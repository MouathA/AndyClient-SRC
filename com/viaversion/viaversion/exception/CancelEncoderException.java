package com.viaversion.viaversion.exception;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.*;

public class CancelEncoderException extends EncoderException implements CancelCodecException
{
    public static final CancelEncoderException CACHED;
    
    public CancelEncoderException() {
    }
    
    public CancelEncoderException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CancelEncoderException(final String s) {
        super(s);
    }
    
    public CancelEncoderException(final Throwable t) {
        super(t);
    }
    
    public static CancelEncoderException generate(final Throwable t) {
        return Via.getManager().isDebug() ? new CancelEncoderException(t) : CancelEncoderException.CACHED;
    }
    
    static {
        CACHED = new CancelEncoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these") {
            @Override
            public Throwable fillInStackTrace() {
                return this;
            }
        };
    }
}
