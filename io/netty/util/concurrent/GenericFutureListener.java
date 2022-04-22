package io.netty.util.concurrent;

import java.util.*;

public interface GenericFutureListener extends EventListener
{
    void operationComplete(final Future p0) throws Exception;
}
