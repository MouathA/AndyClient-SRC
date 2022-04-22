package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class IntTagConverter implements TagConverter
{
    public Integer convert(final IntTag intTag) {
        return intTag.getValue();
    }
    
    public IntTag convert(final Integer n) {
        return new IntTag(n);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((Integer)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((IntTag)tag);
    }
}
