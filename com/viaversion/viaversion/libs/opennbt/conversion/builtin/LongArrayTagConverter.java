package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class LongArrayTagConverter implements TagConverter
{
    public long[] convert(final LongArrayTag longArrayTag) {
        return longArrayTag.getValue();
    }
    
    public LongArrayTag convert(final long[] array) {
        return new LongArrayTag(array);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((long[])o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((LongArrayTag)tag);
    }
}
