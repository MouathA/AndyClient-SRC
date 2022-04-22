package com.google.common.cache;

import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.base.*;

@Beta
public final class RemovalListeners
{
    private RemovalListeners() {
    }
    
    public static RemovalListener asynchronous(final RemovalListener removalListener, final Executor executor) {
        Preconditions.checkNotNull(removalListener);
        Preconditions.checkNotNull(executor);
        return new RemovalListener(executor, removalListener) {
            final Executor val$executor;
            final RemovalListener val$listener;
            
            @Override
            public void onRemoval(final RemovalNotification removalNotification) {
                this.val$executor.execute(new Runnable(removalNotification) {
                    final RemovalNotification val$notification;
                    final RemovalListeners$1 this$0;
                    
                    @Override
                    public void run() {
                        this.this$0.val$listener.onRemoval(this.val$notification);
                    }
                });
            }
        };
    }
}
