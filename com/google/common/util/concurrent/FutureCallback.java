package com.google.common.util.concurrent;

import javax.annotation.*;

public interface FutureCallback
{
    void onSuccess(@Nullable final Object p0);
    
    void onFailure(final Throwable p0);
}
