package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtIncompatible("unnecessary")
abstract class ImmutableMapEntry extends ImmutableEntry
{
    ImmutableMapEntry(final Object o, final Object o2) {
        super(o, o2);
        CollectPreconditions.checkEntryNotNull(o, o2);
    }
    
    ImmutableMapEntry(final ImmutableMapEntry immutableMapEntry) {
        super(immutableMapEntry.getKey(), immutableMapEntry.getValue());
    }
    
    @Nullable
    abstract ImmutableMapEntry getNextInKeyBucket();
    
    @Nullable
    abstract ImmutableMapEntry getNextInValueBucket();
    
    static final class TerminalEntry extends ImmutableMapEntry
    {
        TerminalEntry(final ImmutableMapEntry immutableMapEntry) {
            super(immutableMapEntry);
        }
        
        TerminalEntry(final Object o, final Object o2) {
            super(o, o2);
        }
        
        @Nullable
        @Override
        ImmutableMapEntry getNextInKeyBucket() {
            return null;
        }
        
        @Nullable
        @Override
        ImmutableMapEntry getNextInValueBucket() {
            return null;
        }
    }
}
