package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.conversion.*;
import java.util.*;

public class CompoundTagConverter implements TagConverter
{
    public Map convert(final CompoundTag compoundTag) {
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        for (final Map.Entry<Object, V> entry : compoundTag.getValue().entrySet()) {
            hashMap.put(entry.getKey(), ConverterRegistry.convertToValue((Tag)entry.getValue()));
        }
        return hashMap;
    }
    
    public CompoundTag convert(final Map map) {
        final HashMap<String, Tag> hashMap = new HashMap<String, Tag>();
        for (final String s : map.keySet()) {
            hashMap.put(s, ConverterRegistry.convertToTag(map.get(s)));
        }
        return new CompoundTag(hashMap);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((Map)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((CompoundTag)tag);
    }
}
