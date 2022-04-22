package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class IntArrayTagConverter implements TagConverter
{
    public int[] convert(final IntArrayTag intArrayTag) {
        return intArrayTag.getValue();
    }
    
    public IntArrayTag convert(final int[] array) {
        return new IntArrayTag(array);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((int[])o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((IntArrayTag)tag);
    }
}
