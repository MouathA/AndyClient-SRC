package com.google.common.cache;

import com.google.common.annotations.*;

@Beta
@GwtCompatible
public interface RemovalListener
{
    void onRemoval(final RemovalNotification p0);
}
