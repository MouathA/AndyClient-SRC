package io.netty.util.concurrent;

public interface GenericProgressiveFutureListener extends GenericFutureListener
{
    void operationProgressed(final ProgressiveFuture p0, final long p1, final long p2) throws Exception;
}
