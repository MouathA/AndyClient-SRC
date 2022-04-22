package net.minecraft.util;

import com.google.common.collect.*;
import java.util.*;

public class MapPopulator
{
    private static final String __OBFID;
    
    public static Map createMap(final Iterable iterable, final Iterable iterable2) {
        return populateMap(iterable, iterable2, Maps.newLinkedHashMap());
    }
    
    public static Map populateMap(final Iterable iterable, final Iterable iterable2, final Map map) {
        final Iterator<Object> iterator = iterable2.iterator();
        final Iterator<Object> iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            map.put(iterator2.next(), iterator.next());
        }
        if (iterator.hasNext()) {
            throw new NoSuchElementException();
        }
        return map;
    }
    
    static {
        __OBFID = "CL_00002318";
    }
}
