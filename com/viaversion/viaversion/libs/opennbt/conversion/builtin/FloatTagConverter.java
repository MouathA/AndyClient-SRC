package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class FloatTagConverter implements TagConverter
{
    public Float convert(final FloatTag floatTag) {
        return floatTag.getValue();
    }
    
    public FloatTag convert(final Float n) {
        return new FloatTag(n);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((Float)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((FloatTag)tag);
    }
}
