package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class ShortTagConverter implements TagConverter
{
    public Short convert(final ShortTag shortTag) {
        return shortTag.getValue();
    }
    
    public ShortTag convert(final Short n) {
        return new ShortTag(n);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((Short)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((ShortTag)tag);
    }
}
