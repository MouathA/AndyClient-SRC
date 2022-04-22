package com.google.common.util.concurrent;

import javax.annotation.*;

public class UncheckedTimeoutException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    public UncheckedTimeoutException() {
    }
    
    public UncheckedTimeoutException(@Nullable final String s) {
        super(s);
    }
    
    public UncheckedTimeoutException(@Nullable final Throwable t) {
        super(t);
    }
    
    public UncheckedTimeoutException(@Nullable final String s, @Nullable final Throwable t) {
        super(s, t);
    }
}
