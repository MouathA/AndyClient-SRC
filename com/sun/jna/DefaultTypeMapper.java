package com.sun.jna;

import java.util.*;

public class DefaultTypeMapper implements TypeMapper
{
    private List toNativeConverters;
    private List fromNativeConverters;
    static Class class$java$lang$Boolean;
    static Class class$java$lang$Byte;
    static Class class$java$lang$Character;
    static Class class$java$lang$Short;
    static Class class$java$lang$Integer;
    static Class class$java$lang$Long;
    static Class class$java$lang$Float;
    static Class class$java$lang$Double;
    
    public DefaultTypeMapper() {
        this.toNativeConverters = new ArrayList();
        this.fromNativeConverters = new ArrayList();
    }
    
    private Class getAltClass(final Class clazz) {
        if (clazz == ((DefaultTypeMapper.class$java$lang$Boolean == null) ? (DefaultTypeMapper.class$java$lang$Boolean = class$("java.lang.Boolean")) : DefaultTypeMapper.class$java$lang$Boolean)) {
            return Boolean.TYPE;
        }
        if (clazz == Boolean.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Boolean == null) ? (DefaultTypeMapper.class$java$lang$Boolean = class$("java.lang.Boolean")) : DefaultTypeMapper.class$java$lang$Boolean;
        }
        if (clazz == ((DefaultTypeMapper.class$java$lang$Byte == null) ? (DefaultTypeMapper.class$java$lang$Byte = class$("java.lang.Byte")) : DefaultTypeMapper.class$java$lang$Byte)) {
            return Byte.TYPE;
        }
        if (clazz == Byte.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Byte == null) ? (DefaultTypeMapper.class$java$lang$Byte = class$("java.lang.Byte")) : DefaultTypeMapper.class$java$lang$Byte;
        }
        if (clazz == ((DefaultTypeMapper.class$java$lang$Character == null) ? (DefaultTypeMapper.class$java$lang$Character = class$("java.lang.Character")) : DefaultTypeMapper.class$java$lang$Character)) {
            return Character.TYPE;
        }
        if (clazz == Character.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Character == null) ? (DefaultTypeMapper.class$java$lang$Character = class$("java.lang.Character")) : DefaultTypeMapper.class$java$lang$Character;
        }
        if (clazz == ((DefaultTypeMapper.class$java$lang$Short == null) ? (DefaultTypeMapper.class$java$lang$Short = class$("java.lang.Short")) : DefaultTypeMapper.class$java$lang$Short)) {
            return Short.TYPE;
        }
        if (clazz == Short.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Short == null) ? (DefaultTypeMapper.class$java$lang$Short = class$("java.lang.Short")) : DefaultTypeMapper.class$java$lang$Short;
        }
        if (clazz == ((DefaultTypeMapper.class$java$lang$Integer == null) ? (DefaultTypeMapper.class$java$lang$Integer = class$("java.lang.Integer")) : DefaultTypeMapper.class$java$lang$Integer)) {
            return Integer.TYPE;
        }
        if (clazz == Integer.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Integer == null) ? (DefaultTypeMapper.class$java$lang$Integer = class$("java.lang.Integer")) : DefaultTypeMapper.class$java$lang$Integer;
        }
        if (clazz == ((DefaultTypeMapper.class$java$lang$Long == null) ? (DefaultTypeMapper.class$java$lang$Long = class$("java.lang.Long")) : DefaultTypeMapper.class$java$lang$Long)) {
            return Long.TYPE;
        }
        if (clazz == Long.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Long == null) ? (DefaultTypeMapper.class$java$lang$Long = class$("java.lang.Long")) : DefaultTypeMapper.class$java$lang$Long;
        }
        if (clazz == ((DefaultTypeMapper.class$java$lang$Float == null) ? (DefaultTypeMapper.class$java$lang$Float = class$("java.lang.Float")) : DefaultTypeMapper.class$java$lang$Float)) {
            return Float.TYPE;
        }
        if (clazz == Float.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Float == null) ? (DefaultTypeMapper.class$java$lang$Float = class$("java.lang.Float")) : DefaultTypeMapper.class$java$lang$Float;
        }
        if (clazz == ((DefaultTypeMapper.class$java$lang$Double == null) ? (DefaultTypeMapper.class$java$lang$Double = class$("java.lang.Double")) : DefaultTypeMapper.class$java$lang$Double)) {
            return Double.TYPE;
        }
        if (clazz == Double.TYPE) {
            return (DefaultTypeMapper.class$java$lang$Double == null) ? (DefaultTypeMapper.class$java$lang$Double = class$("java.lang.Double")) : DefaultTypeMapper.class$java$lang$Double;
        }
        return null;
    }
    
    public void addToNativeConverter(final Class clazz, final ToNativeConverter toNativeConverter) {
        this.toNativeConverters.add(new Entry(clazz, toNativeConverter));
        final Class altClass = this.getAltClass(clazz);
        if (altClass != null) {
            this.toNativeConverters.add(new Entry(altClass, toNativeConverter));
        }
    }
    
    public void addFromNativeConverter(final Class clazz, final FromNativeConverter fromNativeConverter) {
        this.fromNativeConverters.add(new Entry(clazz, fromNativeConverter));
        final Class altClass = this.getAltClass(clazz);
        if (altClass != null) {
            this.fromNativeConverters.add(new Entry(altClass, fromNativeConverter));
        }
    }
    
    protected void addTypeConverter(final Class clazz, final TypeConverter typeConverter) {
        this.addFromNativeConverter(clazz, typeConverter);
        this.addToNativeConverter(clazz, typeConverter);
    }
    
    private Object lookupConverter(final Class clazz, final List list) {
        for (final Entry entry : list) {
            if (entry.type.isAssignableFrom(clazz)) {
                return entry.converter;
            }
        }
        return null;
    }
    
    public FromNativeConverter getFromNativeConverter(final Class clazz) {
        return (FromNativeConverter)this.lookupConverter(clazz, this.fromNativeConverters);
    }
    
    public ToNativeConverter getToNativeConverter(final Class clazz) {
        return (ToNativeConverter)this.lookupConverter(clazz, this.toNativeConverters);
    }
    
    static Class class$(final String s) {
        return Class.forName(s);
    }
    
    private static class Entry
    {
        public Class type;
        public Object converter;
        
        public Entry(final Class type, final Object converter) {
            this.type = type;
            this.converter = converter;
        }
    }
}
