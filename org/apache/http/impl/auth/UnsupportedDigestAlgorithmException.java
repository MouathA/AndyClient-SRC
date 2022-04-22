package org.apache.http.impl.auth;

import org.apache.http.annotation.*;

@Immutable
public class UnsupportedDigestAlgorithmException extends RuntimeException
{
    private static final long serialVersionUID = 319558534317118022L;
    
    public UnsupportedDigestAlgorithmException() {
    }
    
    public UnsupportedDigestAlgorithmException(final String s) {
        super(s);
    }
    
    public UnsupportedDigestAlgorithmException(final String s, final Throwable t) {
        super(s, t);
    }
}
