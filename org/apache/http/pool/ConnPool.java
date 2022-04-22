package org.apache.http.pool;

import org.apache.http.concurrent.*;
import java.util.concurrent.*;

public interface ConnPool
{
    Future lease(final Object p0, final Object p1, final FutureCallback p2);
    
    void release(final Object p0, final boolean p1);
}
