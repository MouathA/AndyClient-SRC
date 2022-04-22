package com.viaversion.viaversion.libs.opennbt.conversion;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public interface TagConverter
{
    Object convert(final Tag p0);
    
    Tag convert(final Object p0);
}
