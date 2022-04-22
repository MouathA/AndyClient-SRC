package com.google.common.util.concurrent;

import javax.annotation.*;
import java.util.concurrent.*;

public class ListenableFutureTask extends FutureTask implements ListenableFuture
{
    private final ExecutionList executionList;
    
    public static ListenableFutureTask create(final Callable callable) {
        return new ListenableFutureTask(callable);
    }
    
    public static ListenableFutureTask create(final Runnable runnable, @Nullable final Object o) {
        return new ListenableFutureTask(runnable, o);
    }
    
    ListenableFutureTask(final Callable callable) {
        super(callable);
        this.executionList = new ExecutionList();
    }
    
    ListenableFutureTask(final Runnable runnable, @Nullable final Object o) {
        super(runnable, o);
        this.executionList = new ExecutionList();
    }
    
    @Override
    public void addListener(final Runnable runnable, final Executor executor) {
        this.executionList.add(runnable, executor);
    }
    
    @Override
    protected void done() {
        this.executionList.execute();
    }
}
