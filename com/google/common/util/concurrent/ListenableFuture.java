package com.google.common.util.concurrent;

import java.util.concurrent.*;

public interface ListenableFuture extends Future
{
    void addListener(final Runnable p0, final Executor p1);
}
