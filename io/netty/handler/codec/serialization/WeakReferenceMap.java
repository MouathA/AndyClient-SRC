package io.netty.handler.codec.serialization;

import java.util.*;
import java.lang.ref.*;

final class WeakReferenceMap extends ReferenceMap
{
    WeakReferenceMap(final Map map) {
        super(map);
    }
    
    @Override
    Reference fold(final Object o) {
        return new WeakReference(o);
    }
}
