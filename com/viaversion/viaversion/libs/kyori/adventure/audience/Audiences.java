package com.viaversion.viaversion.libs.kyori.adventure.audience;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

final class Audiences
{
    static final Collector COLLECTOR;
    
    private Audiences() {
    }
    
    private static ForwardingAudience lambda$static$0(final ArrayList list) {
        return Audience.audience(Collections.unmodifiableCollection((Collection<?>)list));
    }
    
    static {
        COLLECTOR = Collectors.collectingAndThen((Collector<Object, ?, ArrayList>)Collectors.toCollection((Supplier<R>)ArrayList::new), Audiences::lambda$static$0);
    }
}
