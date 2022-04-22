package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public class UncheckedExecutionException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    protected UncheckedExecutionException() {
    }
    
    protected UncheckedExecutionException(@Nullable final String s) {
        super(s);
    }
    
    public UncheckedExecutionException(@Nullable final String s, @Nullable final Throwable t) {
        super(s, t);
    }
    
    public UncheckedExecutionException(@Nullable final Throwable t) {
        super(t);
    }
}
