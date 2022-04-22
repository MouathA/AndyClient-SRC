package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.*;
import java.util.*;
import java.lang.reflect.*;

public class ReflectionToStringBuilder extends ToStringBuilder
{
    private boolean appendStatics;
    private boolean appendTransients;
    protected String[] excludeFieldNames;
    private Class upToClass;
    
    public static String toString(final Object o) {
        return toString(o, null, false, false, null);
    }
    
    public static String toString(final Object o, final ToStringStyle toStringStyle) {
        return toString(o, toStringStyle, false, false, null);
    }
    
    public static String toString(final Object o, final ToStringStyle toStringStyle, final boolean b) {
        return toString(o, toStringStyle, b, false, null);
    }
    
    public static String toString(final Object o, final ToStringStyle toStringStyle, final boolean b, final boolean b2) {
        return toString(o, toStringStyle, b, b2, null);
    }
    
    public static String toString(final Object o, final ToStringStyle toStringStyle, final boolean b, final boolean b2, final Class clazz) {
        return new ReflectionToStringBuilder(o, toStringStyle, null, clazz, b, b2).toString();
    }
    
    public static String toStringExclude(final Object o, final Collection collection) {
        return toStringExclude(o, toNoNullStringArray(collection));
    }
    
    static String[] toNoNullStringArray(final Collection collection) {
        if (collection == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return toNoNullStringArray(collection.toArray());
    }
    
    static String[] toNoNullStringArray(final Object[] array) {
        final ArrayList<String> list = new ArrayList<String>(array.length);
        while (0 < array.length) {
            final Object o = array[0];
            if (o != null) {
                list.add(o.toString());
            }
            int n = 0;
            ++n;
        }
        return list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }
    
    public static String toStringExclude(final Object o, final String... excludeFieldNames) {
        return new ReflectionToStringBuilder(o).setExcludeFieldNames(excludeFieldNames).toString();
    }
    
    public ReflectionToStringBuilder(final Object o) {
        super(o);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }
    
    public ReflectionToStringBuilder(final Object o, final ToStringStyle toStringStyle) {
        super(o, toStringStyle);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }
    
    public ReflectionToStringBuilder(final Object o, final ToStringStyle toStringStyle, final StringBuffer sb) {
        super(o, toStringStyle, sb);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }
    
    public ReflectionToStringBuilder(final Object o, final ToStringStyle toStringStyle, final StringBuffer sb, final Class upToClass, final boolean appendTransients, final boolean appendStatics) {
        super(o, toStringStyle, sb);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
        this.setUpToClass(upToClass);
        this.setAppendTransients(appendTransients);
        this.setAppendStatics(appendStatics);
    }
    
    protected boolean accept(final Field field) {
        return field.getName().indexOf(36) == -1 && (!Modifier.isTransient(field.getModifiers()) || this.isAppendTransients()) && (!Modifier.isStatic(field.getModifiers()) || this.isAppendStatics()) && (this.excludeFieldNames == null || Arrays.binarySearch(this.excludeFieldNames, field.getName()) < 0);
    }
    
    protected void appendFieldsIn(final Class clazz) {
        if (clazz.isArray()) {
            this.reflectionAppendArray(this.getObject());
            return;
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        final Field[] array = declaredFields;
        while (0 < array.length) {
            final Field field = array[0];
            final String name = field.getName();
            if (this.accept(field)) {
                this.append(name, this.getValue(field));
            }
            int n = 0;
            ++n;
        }
    }
    
    public String[] getExcludeFieldNames() {
        return this.excludeFieldNames.clone();
    }
    
    public Class getUpToClass() {
        return this.upToClass;
    }
    
    protected Object getValue(final Field field) throws IllegalArgumentException, IllegalAccessException {
        return field.get(this.getObject());
    }
    
    public boolean isAppendStatics() {
        return this.appendStatics;
    }
    
    public boolean isAppendTransients() {
        return this.appendTransients;
    }
    
    public ReflectionToStringBuilder reflectionAppendArray(final Object o) {
        this.getStyle().reflectionAppendArrayDetail(this.getStringBuffer(), null, o);
        return this;
    }
    
    public void setAppendStatics(final boolean appendStatics) {
        this.appendStatics = appendStatics;
    }
    
    public void setAppendTransients(final boolean appendTransients) {
        this.appendTransients = appendTransients;
    }
    
    public ReflectionToStringBuilder setExcludeFieldNames(final String... array) {
        if (array == null) {
            this.excludeFieldNames = null;
        }
        else {
            Arrays.sort(this.excludeFieldNames = toNoNullStringArray(array));
        }
        return this;
    }
    
    public void setUpToClass(final Class upToClass) {
        if (upToClass != null) {
            final Object object = this.getObject();
            if (object != null && !upToClass.isInstance(object)) {
                throw new IllegalArgumentException("Specified class is not a superclass of the object");
            }
        }
        this.upToClass = upToClass;
    }
    
    @Override
    public String toString() {
        if (this.getObject() == null) {
            return this.getStyle().getNullText();
        }
        Class<?> clazz = this.getObject().getClass();
        this.appendFieldsIn(clazz);
        while (clazz.getSuperclass() != null && clazz != this.getUpToClass()) {
            clazz = clazz.getSuperclass();
            this.appendFieldsIn(clazz);
        }
        return super.toString();
    }
}
