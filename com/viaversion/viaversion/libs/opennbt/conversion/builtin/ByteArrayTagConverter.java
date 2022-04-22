package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.conversion.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class ByteArrayTagConverter implements TagConverter
{
    public byte[] convert(final ByteArrayTag byteArrayTag) {
        return byteArrayTag.getValue();
    }
    
    public ByteArrayTag convert(final byte[] array) {
        return new ByteArrayTag(array);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((byte[])o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((ByteArrayTag)tag);
    }
}
