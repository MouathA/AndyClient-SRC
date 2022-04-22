package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;

@Beta
public interface CheckedFuture extends ListenableFuture
{
    Object checkedGet() throws Exception;
    
    Object checkedGet(final long p0, final TimeUnit p1) throws TimeoutException, Exception;
}
