package com.viaversion.viaversion.libs.opennbt.conversion.builtin;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.conversion.*;
import java.util.*;

public class ListTagConverter implements TagConverter
{
    public List convert(final ListTag listTag) {
        final ArrayList<Object> list = new ArrayList<Object>();
        final Iterator<Tag> iterator = listTag.getValue().iterator();
        while (iterator.hasNext()) {
            list.add(ConverterRegistry.convertToValue(iterator.next()));
        }
        return list;
    }
    
    public ListTag convert(final List list) {
        final ArrayList<Tag> list2 = new ArrayList<Tag>();
        final Iterator<Object> iterator = list.iterator();
        while (iterator.hasNext()) {
            list2.add(ConverterRegistry.convertToTag(iterator.next()));
        }
        return new ListTag(list2);
    }
    
    @Override
    public Tag convert(final Object o) {
        return this.convert((List)o);
    }
    
    @Override
    public Object convert(final Tag tag) {
        return this.convert((ListTag)tag);
    }
}
