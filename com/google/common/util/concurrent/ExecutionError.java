package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public class ExecutionError extends Error
{
    private static final long serialVersionUID = 0L;
    
    protected ExecutionError() {
    }
    
    protected ExecutionError(@Nullable final String s) {
        super(s);
    }
    
    public ExecutionError(@Nullable final String s, @Nullable final Error error) {
        super(s, error);
    }
    
    public ExecutionError(@Nullable final Error error) {
        super(error);
    }
}
