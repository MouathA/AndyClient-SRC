package com.google.common.escape;

import com.google.common.annotations.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
public abstract class Escaper
{
    private final Function asFunction;
    
    protected Escaper() {
        this.asFunction = new Function() {
            final Escaper this$0;
            
            public String apply(final String s) {
                return this.this$0.escape(s);
            }
            
            @Override
            public Object apply(final Object o) {
                return this.apply((String)o);
            }
        };
    }
    
    public abstract String escape(final String p0);
    
    public final Function asFunction() {
        return this.asFunction;
    }
}
