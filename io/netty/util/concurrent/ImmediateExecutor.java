package io.netty.util.concurrent;

import java.util.concurrent.*;

public final class ImmediateExecutor implements Executor
{
    public static final ImmediateExecutor INSTANCE;
    
    private ImmediateExecutor() {
    }
    
    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        runnable.run();
    }
    
    static {
        INSTANCE = new ImmediateExecutor();
    }
}
