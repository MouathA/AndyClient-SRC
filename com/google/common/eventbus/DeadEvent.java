package com.google.common.eventbus;

import com.google.common.annotations.*;
import com.google.common.base.*;

@Beta
public class DeadEvent
{
    private final Object source;
    private final Object event;
    
    public DeadEvent(final Object o, final Object o2) {
        this.source = Preconditions.checkNotNull(o);
        this.event = Preconditions.checkNotNull(o2);
    }
    
    public Object getSource() {
        return this.source;
    }
    
    public Object getEvent() {
        return this.event;
    }
}
