package com.viaversion.viaversion.libs.opennbt.conversion;

import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.conversion.builtin.*;

public class ConverterRegistry
{
    private static final Map tagToConverter;
    private static final Map typeToConverter;
    
    public static void register(final Class clazz, final Class clazz2, final TagConverter tagConverter) throws ConverterRegisterException {
        if (ConverterRegistry.tagToConverter.containsKey(clazz)) {
            throw new ConverterRegisterException("Type conversion to tag " + clazz.getName() + " is already registered.");
        }
        if (ConverterRegistry.typeToConverter.containsKey(clazz2)) {
            throw new ConverterRegisterException("Tag conversion to type " + clazz2.getName() + " is already registered.");
        }
        ConverterRegistry.tagToConverter.put(clazz, tagConverter);
        ConverterRegistry.typeToConverter.put(clazz2, tagConverter);
    }
    
    public static void unregister(final Class clazz, final Class clazz2) {
        ConverterRegistry.tagToConverter.remove(clazz);
        ConverterRegistry.typeToConverter.remove(clazz2);
    }
    
    public static Object convertToValue(final Tag tag) throws ConversionException {
        if (tag == null || tag.getValue() == null) {
            return null;
        }
        if (!ConverterRegistry.tagToConverter.containsKey(tag.getClass())) {
            throw new ConversionException("Tag type " + tag.getClass().getName() + " has no converter.");
        }
        return ConverterRegistry.tagToConverter.get(tag.getClass()).convert(tag);
    }
    
    public static Tag convertToTag(final Object o) throws ConversionException {
        if (o == null) {
            return null;
        }
        TagConverter tagConverter = ConverterRegistry.typeToConverter.get(o.getClass());
        if (tagConverter == null) {
            for (final Class clazz : getAllClasses(o.getClass())) {
                if (ConverterRegistry.typeToConverter.containsKey(clazz)) {
                    tagConverter = (TagConverter)ConverterRegistry.typeToConverter.get(clazz);
                    break;
                }
            }
        }
        if (tagConverter == null) {
            throw new ConversionException("Value type " + o.getClass().getName() + " has no converter.");
        }
        return tagConverter.convert(o);
    }
    
    private static Set getAllClasses(final Class clazz) {
        final LinkedHashSet<Class<Serializable>> set = new LinkedHashSet<Class<Serializable>>();
        for (Class<? super Serializable> superclass = (Class<? super Serializable>)clazz; superclass != null; superclass = superclass.getSuperclass()) {
            set.add((Class<Serializable>)superclass);
            set.addAll((Collection<?>)getAllSuperInterfaces(superclass));
        }
        if (set.contains(Serializable.class)) {
            set.remove(Serializable.class);
            set.add(Serializable.class);
        }
        return set;
    }
    
    private static Set getAllSuperInterfaces(final Class clazz) {
        final HashSet<Object> set = new HashSet<Object>();
        final Class[] interfaces = clazz.getInterfaces();
        while (0 < interfaces.length) {
            final Class clazz2 = interfaces[0];
            set.add(clazz2);
            set.addAll(getAllSuperInterfaces(clazz2));
            int n = 0;
            ++n;
        }
        return set;
    }
    
    static {
        tagToConverter = new HashMap();
        typeToConverter = new HashMap();
        register(ByteTag.class, Byte.class, new ByteTagConverter());
        register(ShortTag.class, Short.class, new ShortTagConverter());
        register(IntTag.class, Integer.class, new IntTagConverter());
        register(LongTag.class, Long.class, new LongTagConverter());
        register(FloatTag.class, Float.class, new FloatTagConverter());
        register(DoubleTag.class, Double.class, new DoubleTagConverter());
        register(ByteArrayTag.class, byte[].class, new ByteArrayTagConverter());
        register(StringTag.class, String.class, new StringTagConverter());
        register(ListTag.class, List.class, new ListTagConverter());
        register(CompoundTag.class, Map.class, new CompoundTagConverter());
        register(IntArrayTag.class, int[].class, new IntArrayTagConverter());
        register(LongArrayTag.class, long[].class, new LongArrayTagConverter());
    }
}
