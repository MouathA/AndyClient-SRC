package io.netty.util;

public interface Timeout
{
    Timer timer();
    
    TimerTask task();
    
    boolean isExpired();
    
    boolean isCancelled();
    
    boolean cancel();
}
