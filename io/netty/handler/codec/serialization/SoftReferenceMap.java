package io.netty.handler.codec.serialization;

import java.util.*;
import java.lang.ref.*;

final class SoftReferenceMap extends ReferenceMap
{
    SoftReferenceMap(final Map map) {
        super(map);
    }
    
    @Override
    Reference fold(final Object o) {
        return new SoftReference(o);
    }
}
