package com.viaversion.viaversion.exception;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.*;

public class CancelDecoderException extends DecoderException implements CancelCodecException
{
    public static final CancelDecoderException CACHED;
    
    public CancelDecoderException() {
    }
    
    public CancelDecoderException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CancelDecoderException(final String s) {
        super(s);
    }
    
    public CancelDecoderException(final Throwable t) {
        super(t);
    }
    
    public static CancelDecoderException generate(final Throwable t) {
        return Via.getManager().isDebug() ? new CancelDecoderException(t) : CancelDecoderException.CACHED;
    }
    
    static {
        CACHED = new CancelDecoderException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these") {
            @Override
            public Throwable fillInStackTrace() {
                return this;
            }
        };
    }
}
