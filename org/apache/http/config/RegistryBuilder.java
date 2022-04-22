package org.apache.http.config;

import org.apache.http.annotation.*;
import org.apache.http.util.*;
import java.util.*;

@NotThreadSafe
public final class RegistryBuilder
{
    private final Map items;
    
    public static RegistryBuilder create() {
        return new RegistryBuilder();
    }
    
    RegistryBuilder() {
        this.items = new HashMap();
    }
    
    public RegistryBuilder register(final String s, final Object o) {
        Args.notEmpty(s, "ID");
        Args.notNull(o, "Item");
        this.items.put(s.toLowerCase(Locale.US), o);
        return this;
    }
    
    public Registry build() {
        return new Registry(this.items);
    }
    
    @Override
    public String toString() {
        return this.items.toString();
    }
}
