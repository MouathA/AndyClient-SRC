package com.sun.jna;

import java.lang.ref.*;
import java.util.*;

public class NativeMappedConverter implements TypeConverter
{
    private static final Map converters;
    private final Class type;
    private final Class nativeType;
    private final NativeMapped instance;
    static Class class$com$sun$jna$NativeMapped;
    static Class class$com$sun$jna$Pointer;
    
    public static NativeMappedConverter getInstance(final Class clazz) {
        // monitorenter(converters = NativeMappedConverter.converters)
        final Reference<NativeMappedConverter> reference = NativeMappedConverter.converters.get(clazz);
        NativeMappedConverter nativeMappedConverter = (reference != null) ? reference.get() : null;
        if (nativeMappedConverter == null) {
            nativeMappedConverter = new NativeMappedConverter(clazz);
            NativeMappedConverter.converters.put(clazz, new SoftReference<Object>(nativeMappedConverter));
        }
        // monitorexit(converters)
        return nativeMappedConverter;
    }
    
    public NativeMappedConverter(final Class type) {
        if (!((NativeMappedConverter.class$com$sun$jna$NativeMapped == null) ? (NativeMappedConverter.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : NativeMappedConverter.class$com$sun$jna$NativeMapped).isAssignableFrom(type)) {
            throw new IllegalArgumentException("Type must derive from " + ((NativeMappedConverter.class$com$sun$jna$NativeMapped == null) ? (NativeMappedConverter.class$com$sun$jna$NativeMapped = class$("com.sun.jna.NativeMapped")) : NativeMappedConverter.class$com$sun$jna$NativeMapped));
        }
        this.type = type;
        this.instance = this.defaultValue();
        this.nativeType = this.instance.nativeType();
    }
    
    public NativeMapped defaultValue() {
        return this.type.newInstance();
    }
    
    public Object fromNative(final Object o, final FromNativeContext fromNativeContext) {
        return this.instance.fromNative(o, fromNativeContext);
    }
    
    public Class nativeType() {
        return this.nativeType;
    }
    
    public Object toNative(Object defaultValue, final ToNativeContext toNativeContext) {
        if (defaultValue == null) {
            if (((NativeMappedConverter.class$com$sun$jna$Pointer == null) ? (NativeMappedConverter.class$com$sun$jna$Pointer = class$("com.sun.jna.Pointer")) : NativeMappedConverter.class$com$sun$jna$Pointer).isAssignableFrom(this.nativeType)) {
                return null;
            }
            defaultValue = this.defaultValue();
        }
        return ((NativeMapped)defaultValue).toNative();
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    static {
        converters = new WeakHashMap();
    }
}
