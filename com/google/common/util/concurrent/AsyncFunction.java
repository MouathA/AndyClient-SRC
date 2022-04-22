package com.google.common.util.concurrent;

public interface AsyncFunction
{
    ListenableFuture apply(final Object p0) throws Exception;
}
