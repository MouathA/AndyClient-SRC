package com.darkmagician6.eventapi.events;

public interface Cancellable
{
    boolean isCancelled();
    
    void setCancelled(final boolean p0);
}
