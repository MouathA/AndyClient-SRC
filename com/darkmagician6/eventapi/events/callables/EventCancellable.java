package com.darkmagician6.eventapi.events.callables;

import com.darkmagician6.eventapi.events.*;

public abstract class EventCancellable implements Event, Cancellable
{
    private boolean cancelled;
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
