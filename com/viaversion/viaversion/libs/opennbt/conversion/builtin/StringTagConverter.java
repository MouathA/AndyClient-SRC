package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class StringTagConverter implements TagConverter
{
    public String convert(final StringTag stringTag) {
        return stringTag.getValue();
    }
    
    public StringTag convert(final String s) {
        return new StringTag(s);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((String)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((StringTag)tag);
    }
}
