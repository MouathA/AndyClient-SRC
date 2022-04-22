package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class LongTagConverter implements TagConverter
{
    public Long convert(final LongTag longTag) {
        return longTag.getValue();
    }
    
    public LongTag convert(final Long n) {
        return new LongTag(n);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((Long)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((LongTag)tag);
    }
}
