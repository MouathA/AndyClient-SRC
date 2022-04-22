package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class DoubleTagConverter implements TagConverter
{
    public Double convert(final DoubleTag doubleTag) {
        return doubleTag.getValue();
    }
    
    public DoubleTag convert(final Double n) {
        return new DoubleTag(n);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((Double)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((DoubleTag)tag);
    }
}
