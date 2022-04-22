package com.darkmagician6.eventapi.events.callables;

import com.darkmagician6.eventapi.events.*;

public abstract class EventTyped implements Event, Typed
{
    private final byte type;
    
    protected EventTyped(final byte type) {
        this.type = type;
    }
    
    @Override
    public byte getType() {
        return this.type;
    }
}
